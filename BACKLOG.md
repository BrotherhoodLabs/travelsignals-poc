# TravelSignals PoC - Backlog de Développement

## 🎯 Épiques

### 1. Infrastructure & Base de Données
- [x] **Infrastructure Docker Compose**
  - [x] Configuration Kafka + Zookeeper
  - [x] Configuration PostgreSQL
  - [x] Configuration Backend Quarkus
  - [x] Configuration Frontend Vue.js
  - [x] Scripts d'initialisation

- [x] **Base de Données PostgreSQL**
  - [x] Schéma de base de données
  - [x] Tables principales (destinations, providers, events)
  - [x] Données de test
  - [x] Configuration Hibernate/Panache

### 2. Backend Quarkus
- [x] **Modèles et Entités**
  - [x] Modèles d'événements (PriceUpdate, WeatherAlert, FlightStatus, VisaReminder)
  - [x] Entités JPA avec Panache
  - [x] Relations entre entités

- [x] **Services Métier**
  - [x] Service d'agrégation d'alertes
  - [x] Simulateurs d'événements
  - [x] Règles de priorité (P1/P2/P3)

- [x] **API REST**
  - [x] Endpoints de consultation des alertes
  - [x] Endpoints de compteurs
  - [x] Health checks
  - [x] Documentation OpenAPI

- [x] **Opérations CRUD Complètes**
  - [x] CRUD Destinations
  - [x] CRUD Providers
  - [x] CRUD Price Events
  - [x] CRUD Weather Events
  - [x] CRUD Flight Events
  - [x] CRUD Visa Events
  - [x] CRUD Alert Aggregates
  - [x] Endpoints de génération d'alertes
  - [x] Endpoints de données de test

### 3. Frontend Vue.js
- [x] **Interface Utilisateur**
  - [x] Dashboard principal
  - [x] Page des alertes avec filtres
  - [x] Page des compteurs
  - [x] Design responsive

- [x] **Intégration API**
  - [x] Service API avec Axios
  - [x] Gestion d'état avec Pinia
  - [x] Gestion des erreurs
  - [x] Configuration CORS
  - [x] Connexion backend-frontend fonctionnelle

- [ ] **Interface CRUD**
  - [ ] Formulaires de création/édition
  - [ ] Tables de données avec actions
  - [ ] Modales de confirmation
  - [ ] Validation côté client

### 4. Architecture Event-Driven
- [x] **Kafka Integration**
  - [x] Configuration des topics
  - [x] Producers et Consumers
  - [x] Sérialisation JSON

- [x] **Traitement des Événements**
  - [x] Simulation d'événements
  - [x] Agrégation en temps réel
  - [x] Génération d'alertes

### 5. Tests & Qualité
- [x] **Tests Backend**
  - [x] Tests unitaires des services
  - [x] Tests d'intégration API

- [ ] **Tests Frontend**
  - [ ] Tests unitaires des composants
  - [ ] Tests d'intégration

- [ ] **Tests End-to-End**
  - [ ] Scénarios complets
  - [ ] Tests de performance

### 6. Documentation & Déploiement
- [x] **Documentation Technique**
  - [x] Architecture du système
  - [x] Schémas d'événements
  - [x] Guide d'intégration
  - [x] Scénarios de démonstration

- [x] **Scripts de Déploiement**
  - [x] Docker Compose
  - [x] Scripts de développement
  - [x] Scripts de démonstration

## 🚀 User Stories

### Backend CRUD
- [ ] **En tant que** développeur, **je veux** créer une destination **afin de** pouvoir générer des alertes pour cette destination
- [ ] **En tant que** développeur, **je veux** lister toutes les destinations **afin de** voir les destinations disponibles
- [ ] **En tant que** développeur, **je veux** modifier une destination **afin de** corriger les informations
- [ ] **En tant que** développeur, **je veux** supprimer une destination **afin de** nettoyer les données obsolètes

### Frontend CRUD
- [ ] **En tant qu'** utilisateur, **je veux** voir un formulaire de création **afin de** ajouter facilement de nouvelles données
- [ ] **En tant qu'** utilisateur, **je veux** éditer les données en ligne **afin de** modifier rapidement les informations
- [ ] **En tant qu'** utilisateur, **je veux** confirmer avant suppression **afin de** éviter les suppressions accidentelles

## 📊 Métriques de Succès

- [x] **Architecture complète** : Tous les services démarrent correctement
- [x] **API fonctionnelle** : Endpoints répondent aux requêtes
- [x] **Base de données** : Connexion et données de test chargées
- [x] **Frontend** : Interface accessible et responsive
- [x] **CRUD complet** : Toutes les opérations CRUD fonctionnelles
- [x] **Intégration** : Backend-frontend connectés avec CORS
- [x] **Stockage persistant** : Alertes sauvegardées en base
- [ ] **Tests** : Couverture de tests > 80%
- [ ] **Performance** : Temps de réponse < 200ms

## 🔄 Prochaines Itérations

### Itération 1 : ✅ Finalisation CRUD Backend (TERMINÉE)
- ✅ Implémentation des repositories Panache
- ✅ Création des endpoints CRUD
- ✅ Tests des opérations CRUD
- ✅ Configuration CORS
- ✅ Endpoints de génération d'alertes

### Itération 2 : Interface CRUD Frontend
- Formulaires de création/édition
- Tables de données interactives
- Gestion des erreurs

### Itération 3 : Tests & Optimisation
- Tests automatisés
- Optimisation des performances
- Documentation finale

## 🎉 Statut Actuel (22/09/2025)

### ✅ Architecture Complète et Fonctionnelle
- **Backend Quarkus** : http://localhost:8080 (API REST + OpenAPI)
- **Frontend Vue.js** : http://localhost:8081 (Interface utilisateur)
- **PostgreSQL** : Base de données avec données de test
- **Kafka** : Message broker pour événements
- **CORS** : Configuration fonctionnelle backend-frontend

### 🔧 Fonctionnalités Opérationnelles
- Dashboard avec compteurs d'alertes
- Affichage des alertes en temps réel
- Filtres par type et priorité
- Stockage persistant en base de données
- Génération d'alertes via API
- Architecture event-driven

### 🚀 Comment Tester
1. Démarrer l'architecture : `docker-compose -f infra/docker-compose.yml up -d`
2. Ouvrir le frontend : http://localhost:8081
3. Voir les alertes s'afficher (plus d'erreur réseau)
4. Tester les APIs : http://localhost:8080/q/swagger-ui

## 📝 Notes Techniques

- **Base de données** : PostgreSQL 15 avec données de test
- **Backend** : Quarkus 3.6.0 avec Panache
- **Frontend** : Vue.js 3 avec TypeScript
- **Message Broker** : Apache Kafka
- **Containerisation** : Docker Compose
