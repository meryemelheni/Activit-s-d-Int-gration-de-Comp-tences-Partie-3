# Projet Gestion des Étudiants — Partie 3

## Architecture Microservices
- eureka-server (port 8761)
- api-spring-boot / etudiant-service (port 8081)
- grading-service (port 8082)
- api-gateway (port 8090)
- frontend Next.js (port 3000)
- mobile_app Flutter

## Prérequis
- Java 17+
- Node.js 18+
- Flutter SDK
- Docker & Docker Compose
- Maven

## Lancer le projet

### Option 1 - Manuel (dans l'ordre)
Terminal 1 - Eureka :
cd eureka-server && ./mvnw spring-boot:run

Terminal 2 - Etudiant Service :
cd api-spring-boot && ./mvnw spring-boot:run

Terminal 3 - Grading Service :
cd grading-service && ./mvnw spring-boot:run

Terminal 4 - API Gateway :
cd api-gateway && ./mvnw spring-boot:run

Terminal 5 - Frontend :
cd frontend && npm install && npm run dev

Terminal 6 - Mobile :
cd mobile_app && flutter pub get && flutter run

### Option 2 - Docker Compose
docker compose up --build

## URLs importantes
| Service | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| Etudiant Swagger | http://localhost:8081/swagger-ui/index.html |
| Grading Swagger | http://localhost:8082/swagger-ui/index.html |
| API Gateway | http://localhost:8090 |
| Frontend | http://localhost:3000 |

## Endpoints API

### Etudiants
GET    /api/etudiants
GET    /api/etudiants/{id}
POST   /api/etudiants
PUT    /api/etudiants/{id}
DELETE /api/etudiants/{id}

### Départements
GET    /api/departements
GET    /api/departements/{id}
POST   /api/departements
PUT    /api/departements/{id}
DELETE /api/departements/{id}

### Notes
GET    /api/notes
GET    /api/notes/{id}
POST   /api/notes
PUT    /api/notes/{id}
DELETE /api/notes/{id}

## Workflow GitHub
- Toute PR doit être reliée à un ticket Jira
- Au moins 1 review approuvée avant merge
- Push direct sur main et version-3 interdit
- Les commentaires bloquants doivent être 
  résolus avant merge
- Toute PR doit être relue dans les 48h

## Structure du dépôt
etudiantsapi/
├── api-gateway/
├── api-spring-boot/
├── eureka-server/
├── grading-service/
├── frontend/
├── mobile_app/
├── k8s/
│   ├── etudiant-deployment.yaml
│   └── postgres-deployment.yaml
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   └── bug_report.md
│   └── pull_request_template.md
├── docker-compose.yml
└── README.md
