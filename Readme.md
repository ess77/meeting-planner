# Application Meeting-Planner
Application de programmation de meeting avec réservation de salles et des ressources associées.

Développement : Frameworks utilisés :
    - Spring Boot version 2.5.2
    - Lombock
    - Thymeleaf
    - Mockito
    - Jupiter 5

Base de données in memory : H2

Tests  : Jupiter 5, Mockito


# Déploiement de l'application sur Docker :

### Dans une fenêtre de commande à la racine du projet :

1 : Build de l'image conteneur : 
    <font color="green"><i> docker build -t meeting-planner .</i></font>

2 : Lancer l'exécution de ce conteneur :
    <font color="green"><i>docker run  -dp 8087:8080 meeting-planner</i></font>

3 : Accès à l'interface utilisateur :
    <font><i>http://localhost:8087</i></font>

-----------------------------------------------------

# Rebuild d'un exécutable par Maven :

#### NB: Java version 11 et Maven doivent être installés :
### Dans une fenêtre de commande à la racine du projet :


1 : Build de l'image ess-load-code-tables-0.20.0.jar : 
    <font color="green"><i> mvn clean package</i></font>

2 : Lancer l'exécution de l'application :
    <font color="green"><i>java -jar ./target/meeting-planner-0.0.1-SNAPSHOT.jar</i></font>
    
---------------------------------------------------------

    
####  NB: Projet également disponible sous : `https://github.com/ess77/meeting-planner.git`
