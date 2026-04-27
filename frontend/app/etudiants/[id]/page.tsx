"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import EtudiantEditForm from "../../../components/EtudiantEditForm";

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

export default function EtudiantDetailsPage({ params }: { params: { id: string } }) {
  const id = params.id;
  const [etudiant, setEtudiant] = useState<Etudiant | null>(null);
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [etudRes, deptRes] = await Promise.all([
          fetch(`/api/etudiants/${id}`),
          fetch(`/api/departements`)
        ]);

        if (!etudRes.ok) throw new Error("Étudiant introuvable");
        if (!deptRes.ok) throw new Error("Erreur chargement départements");

        const etudData = await etudRes.json();
        const deptData = await deptRes.json();

        setEtudiant(etudData);
        setDepartements(deptData);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (loading) return <div className="p-8">Chargement...</div>;
  if (error) return <div className="p-8 text-red-600">Erreur : {error}</div>;
  if (!etudiant) return <div className="p-8">Étudiant introuvable.</div>;

  return (
    <div className="min-h-screen bg-slate-50 p-8">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="flex items-center gap-4 mb-8">
          <Link href="/etudiants" className="text-violet-600 hover:underline">
            ← Retour aux étudiants
          </Link>
          <span className="text-slate-300">|</span>
          <Link href="/departements" className="text-violet-600 hover:underline">
            Gérer Départements
          </Link>
        </div>

        <EtudiantEditForm etudiant={etudiant} departements={departements} />
      </div>
    </div>
  );
}