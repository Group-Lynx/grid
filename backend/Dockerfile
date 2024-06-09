FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Maven & dependencies
COPY .mvn/ .mvn/
COPY mvnw ./
COPY pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:resolve

# Source files
COPY src ./src

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
