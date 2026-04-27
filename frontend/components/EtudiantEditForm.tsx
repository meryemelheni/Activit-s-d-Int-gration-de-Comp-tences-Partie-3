"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

interface Departement {
  id: number;
  nom: string;
}

interface Etudiant {
  id: number;
  cin: string;
  nom: string;
  email: string;
  dateNaissance: string;
  anneePremiereInscription: number;
  departement: Departement;
}

interface Props {
  etudiant: Etudiant;
  departements: Departement[];
}

export default function EtudiantEditForm({ etudiant, departements }: Props) {
  const router = useRouter();
  const [formData, setFormData] = useState({
    cin: etudiant.cin,
    nom: etudiant.nom,
    email: etudiant.email,
    dateNaissance: etudiant.dateNaissance,
    anneePremiereInscription: etudiant.anneePremiereInscription,
    departementId: etudiant.departement?.id?.toString() || "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSuccess("");
    try {
      const res = await fetch(`/api/etudiants/${etudiant.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          cin: formData.cin,
          nom: formData.nom,
          email: formData.email,
          dateNaissance: formData.dateNaissance,
          anneePremiereInscription: Number(formData.anneePremiereInscription),
          departement: { id: Number(formData.departementId) },
        }),
      });
      if (!res.ok) throw new Error("Echec modification");
      setSuccess("Étudiant modifié avec succès !");
      setTimeout(() => router.push("/etudiants"), 1000);
    } catch {
      setError("Impossible de modifier l'étudiant");
    }
  };

  return (
    <div className="panel">
      <h2 className="subsection-title">Modifier Etudiant</h2>

      {error && <p className="error-text">{error}</p>}
      {success && <p className="success-text">{success}</p>}

      <form onSubmit={handleSubmit}>
        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16, marginBottom: 16 }}>
          <div>
            <label className="input-label">CIN *</label>
            <input
              className="input"
              required
              value={formData.cin}
              onChange={(e) => setFormData({ ...formData, cin: e.target.value })}
              placeholder="ex: CIN001"
            />
          </div>
          <div>
            <label className="input-label">Nom *</label>
            <input
              className="input"
              required
              value={formData.nom}
              onChange={(e) => setFormData({ ...formData, nom: e.target.value })}
              placeholder="ex: Dupont"
            />
          </div>
          <div>
            <label className="input-label">Email *</label>
            <input
              className="input"
              type="email"
              required
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              placeholder="ex: dupont@example.com"
            />
          </div>
          <div>
            <label className="input-label">Date de Naissance *</label>
            <input
              className="input"
              type="date"
              required
              value={formData.dateNaissance}
              onChange={(e) => setFormData({ ...formData, dateNaissance: e.target.value })}
            />
          </div>
          <div>
            <label className="input-label">Année d'Inscription *</label>
            <input
              className="input"
              type="number"
              required
              value={formData.anneePremiereInscription}
              onChange={(e) => setFormData({ ...formData, anneePremiereInscription: Number(e.target.value) })}
              placeholder="ex: 2023"
            />
          </div>
          <div>
            <label className="input-label">Département *</label>
            <select
              className="input"
              required
              value={formData.departementId}
              onChange={(e) => setFormData({ ...formData, departementId: e.target.value })}
            >
              <option value="">-- Sélectionner un département --</option>
              {departements.map((d) => (
                <option key={d.id} value={d.id}>
                  {d.nom}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div style={{ display: "flex", gap: 12 }}>
          <button className="btn" type="submit">
            Enregistrer
          </button>
          <button
            type="button"
            onClick={() => router.push("/etudiants")}
            style={{
              padding: "8px 20px",
              background: "#e2e8f0",
              color: "#475569",
              border: "none",
              borderRadius: 8,
              cursor: "pointer",
              fontWeight: 500,
            }}
          >
            Annuler
          </button>
        </div>
      </form>
    </div>
  );
}
