"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

type Etudiant = {
  id: number;
  cin: string;
  nom: string;
  email: string;
  dateNaissance: string;
  anneePremiereInscription: number;
  departement?: { nom?: string };
};

export default function EtudiantsTable({ etudiants }: { etudiants: Etudiant[] }) {
  const router = useRouter();
  const [busyId, setBusyId] = useState<number | null>(null);
  const [error, setError] = useState("");

  async function onDelete(id: number) {
    setError("");
    const ok = window.confirm("Confirmer la suppression de cet etudiant ?");
    if (!ok) return;

    try {
      setBusyId(id);
      const response = await fetch(`${API_BASE}/api/etudiants/${id}`, { method: "DELETE" });
      if (!response.ok) {
        throw new Error("Suppression impossible");
      }
      router.refresh();
    } catch {
      setError("Impossible de supprimer l'etudiant");
    } finally {
      setBusyId(null);
    }
  }

  return (
    <>
      <div className="table-wrap">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>CIN</th>
              <th>Nom</th>
              <th>Email</th>
              <th>Date de Naissance</th>
              <th>Annee</th>
              <th>Departement</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {etudiants.map((e) => (
              <tr key={e.id}>
                <td>{e.id}</td>
                <td>{e.cin}</td>
                <td>{e.nom}</td>
                <td>{e.email}</td>
                <td>{e.dateNaissance}</td>
                <td>{e.anneePremiereInscription}</td>
                <td>{e?.departement?.nom || "-"}</td>
                <td>
                  <div className="btn-stack">
                    <Link className="btn-sm btn-edit" href={`/etudiants/${e.id}`}>
                      Modifier
                    </Link>
                    <button className="btn-sm btn-delete" type="button" onClick={() => onDelete(e.id)} disabled={busyId === e.id}>
                      {busyId === e.id ? "..." : "Supprimer"}
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {error ? <p className="error-text">{error}</p> : null}
    </>
  );
}
