#Simpliest version. Jar file has to be created first.
#mvn clean package -DskipTests

#FROM openjdk:11
#ADD target/*.jar app.jar
#ENTRYPOINT ["sh","-c","java -jar app.jar"]


#More advanced version. Requires maven plugin for
#unpacking the jar file

FROM openjdk:11
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","by/htp/ramanouski/taskmanager/TaskManagerApplication"]


#Advanced version. For production. Splits the project on layers
#you do not need to do anything. just docker build .
#downloads a lot of dependencies and takes almost 20 minutes to create an image, though.

#FROM maven:3-jdk-11 as backend-build
#WORKDIR /fullstack/backend
#
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -B
#
#COPY src src
#RUN mvn install -DskipTests
#
## Unzip
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
#
#FROM openjdk:11
#VOLUME /tmp
#ARG DEPENDENCY=/fullstack/backend/target/dependency
#COPY --from=backend-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=backend-build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=backend-build ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","by.htp.ramanouski.taskmanager.TaskManagerApplication"]

