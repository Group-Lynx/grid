FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Maven & dependencies
COPY .mvn/ .mvn/
COPY mvnw ./
COPY pom.xml ./

RUN apt-get update && apt-get install dos2unix
RUN chmod +x mvnw

RUN ./mvnw dependency:resolve

# Source files
COPY src ./src

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
