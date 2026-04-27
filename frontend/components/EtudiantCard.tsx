import Link from "next/link";

type Etudiant = {
  id: number;
  nom: string;
  email: string;
  cin: string;
};

export default function EtudiantCard({ etudiant }: { etudiant: Etudiant }) {
  return (
    <div style={{ border: "1px solid #cbd5e1", borderRadius: 8, padding: 12, marginBottom: 12 }}>
      <h3>{etudiant.nom}</h3>
      <p>CIN: {etudiant.cin}</p>
      <p>Email: {etudiant.email}</p>
      <Link href={`/etudiants/${etudiant.id}`}>Voir detail</Link>
    </div>
  );
}
