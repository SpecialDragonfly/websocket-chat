FROM eclipse-temurin:17.0.10_7-jre-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]