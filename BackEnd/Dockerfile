# 1) Build stage: compile & package with Maven
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only what Maven needs to download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the source and build the jar
COPY src ./src
RUN mvn clean package -DskipTests

# 2) Run stage: minimal runtime image
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the fat jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
