FROM openjdk:11
WORKDIR /
ADD target/meeting-planner-0.0.1-SNAPSHOT.jar meeting-planner.jar
EXPOSE 8080
CMD ["java","-jar","meeting-planner.jar"] 

