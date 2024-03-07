FROM maven:3.9.6-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ecom-0.0.1-SNAPSHOT.jar ecom.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
