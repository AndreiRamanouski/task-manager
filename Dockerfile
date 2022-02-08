FROM openjdk:15
EXPOSE 8080
ADD target/task-manager-0.0.1-SNAPSHOT.jar task-manager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh","-c", "java -jar task-manager-0.0.1-SNAPSHOT.jar"]