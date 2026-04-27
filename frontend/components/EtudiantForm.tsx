"use client";

import { useState } from "react";

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

type Departement = {
  id: number;
  nom: string;
};

export default function EtudiantForm({ departements }: { departements: Departement[] }) {
  const [cin, setCin] = useState("");
  const [nom, setNom] = useState("");
  const [email, setEmail] = useState("");
  const [dateNaissance, setDateNaissance] = useState("");
  const [annee, setAnnee] = useState("");
  const [departementId, setDepartementId] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setMessage("");
    setError("");
    try {
      const response = await fetch(`${API_BASE}/api/etudiants`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          cin,
          nom,
          email,
          dateNaissance,
          anneePremiereInscription: Number(annee),
          departement: { id: Number(departementId) }
        })
      });

      if (!response.ok) {
        const payload = await response.json().catch(() => ({}));
        const messageText = payload?.message || "Echec lors de l'ajout de l'etudiant";
        throw new Error(messageText);
      }

      setCin("");
      setNom("");
      setEmail("");
      setDateNaissance("");
      setAnnee("");
      setDepartementId("");
      setMessage("Etudiant ajoute avec succes");
      window.location.reload();
    } catch (err: any) {
      setError(err?.message || "Impossible d'ajouter l'etudiant");
    }
  }

  return (
    <div className="panel">
      <h2 className="subsection-title">Ajouter un Nouvel Etudiant</h2>
      <form onSubmit={onSubmit}>
        <div className="form-grid">
          <div>
            <label className="input-label">CIN *</label>
            <input className="input" placeholder="ex: CIN001" value={cin} onChange={(e) => setCin(e.target.value)} required />
          </div>
          <div>
            <label className="input-label">Nom *</label>
            <input className="input" placeholder="ex: Dupont" value={nom} onChange={(e) => setNom(e.target.value)} required />
          </div>
          <div>
            <label className="input-label">Email *</label>
            <input className="input" type="email" placeholder="ex: dupont@example.com" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div>
            <label className="input-label">Date de Naissance *</label>
            <input className="input" type="date" value={dateNaissance} onChange={(e) => setDateNaissance(e.target.value)} required />
          </div>
          <div>
            <label className="input-label">Annee d'Inscription *</label>
            <input className="input" type="number" placeholder="ex: 2023" value={annee} onChange={(e) => setAnnee(e.target.value)} required />
          </div>
          <div>
            <label className="input-label">Departement *</label>
            <select className="input" value={departementId} onChange={(e) => setDepartementId(e.target.value)} required>
              <option value="">-- Selectionner un departement --</option>
              {departements.map((d) => (
                <option key={d.id} value={d.id}>
                  {d.nom}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="form-actions">
          <button className="btn" type="submit">
            Ajouter Etudiant
          </button>
        </div>
      </form>
      {message ? <p className="success-text">{message}</p> : null}
      {error ? <p className="error-text">{error}</p> : null}
    </div>
  );
}
