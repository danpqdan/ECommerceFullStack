FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install -y openjdk-17-jdk maven
COPY . .

WORKDIR /java/apiecomerce
RUN mvn clean install -DskipTests
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /java/apiecomerce/target/apiecomerce-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
