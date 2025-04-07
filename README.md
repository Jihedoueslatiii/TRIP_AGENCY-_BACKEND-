# Microservice Offres de Voyages (Agence de Voyages)

## Description

Ce microservice a pour objectif de gérer les offres de voyages (programmes) pour une agence de voyages. Il fournit des fonctionnalités CRUD (Créer, Lire, Mettre à jour, Supprimer) pour les programmes de voyages, ainsi que des fonctionnalités avancées pour une gestion optimale. L'API est accessible via un API Gateway, ce qui centralise les requêtes et simplifie l'interaction avec les clients.

## Fonctionnalités Principales

* **CRUD pour les Programmes de Voyages :**
    * Création de nouvelles offres de voyages (programmes).
    * Lecture des détails des offres de voyages existantes.
    * Mise à jour des informations des offres de voyages.
    * Suppression des offres de voyages.
* **Fonctionnalités Avancées :**
    * **Recherche Multi-Critères :** Permet aux utilisateurs de rechercher des offres de voyages en utilisant plusieurs filtres (destination, prix, dates, etc.).
    * **Statistiques des Revenus :** Fournit des rapports et des graphiques sur les revenus générés par les offres de voyages.
    * **Export PDF :** Permet de générer des documents PDF contenant les détails des offres de voyages pour impression ou partage.
    * **Partage d'Offre par Email :** Permet aux utilisateurs de partager des offres de voyages avec leurs contacts par email.

## Technologies Utilisées

* **Backend :** Spring Boot
* **Frontend :** Angular (si applicable)
* **Base de Données :** H2 (ou MySQL, selon votre configuration)
* **API Gateway :** Spring Cloud Gateway

## Comment Utiliser

1.  **Cloner le dépôt :**

    ```bash
    git clone [https://github.com/Jihedoueslatiii/TRIP_AGENCY-_BACKEND-.git](https://github.com/Jihedoueslatiii/TRIP_AGENCY-_BACKEND-.git)
    ```

2.  **Configurer la base de données :**

    * Modifiez `application.properties` ou `application.yml` avec vos informations de connexion à la base de données (H2 ou MySQL).

3.  **Exécuter le microservice (Maven) :**

    ```bash
    mvn spring-boot:run
    ```

4.  **Accéder à l'API via l'API Gateway :**

    * L'API est accessible via l'API Gateway, généralement sur le port 8093.
    * Les endpoints sont préfixés par `/offresvoyage/`. Par exemple : `http://localhost:8093/offresvoyage/add-offre-voyage`.
    * Consultez la documentation de votre API Gateway pour les détails des routes et des endpoints.

5.  **Tester les endpoints :**

    * Utilisez un client API comme Postman ou Insomnia pour tester les endpoints.
    * Vous pouvez également utiliser Swagger UI si vous l'avez activé (via l'API Gateway).
