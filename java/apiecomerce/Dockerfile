# Use a imagem base do Ubuntu para instalar o JDK e Maven
FROM ubuntu:latest AS build
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

WORKDIR /java/apiecomerce

COPY java/apiecomerce/pom.xml .

RUN mvn dependency:resolve
COPY java/apiecomerce .

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /java/apiecomerce/target/apiecomerce-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
