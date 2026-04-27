import "./globals.css";
import Link from "next/link";
import { ReactNode } from "react";

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="fr">
      <body>
        <main className="app-shell">
          <section className="hero">
            <h1 className="hero-title">Gestion des Etudiants</h1>
            <p className="hero-subtitle">Plateforme de gestion des etudiants et departements</p>
            <nav className="top-nav">
              <Link href="/etudiants">Etudiants</Link>
              <Link href="/departements">Departements</Link>
            </nav>
          </section>
          <section className="page-content">{children}</section>
        </main>
      </body>
    </html>
  );
}
