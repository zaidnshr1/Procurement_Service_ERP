FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY ./src src

RUN ./mvnw package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/procurement-app-0.0.1-SNAPSHOT.jar"]