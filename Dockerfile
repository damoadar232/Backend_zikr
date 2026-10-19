
# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration and source code
COPY pom.xml .
COPY src ./src

# Build the executable Spring Boot JAR
RUN mvn -B clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the executable JAR produced by Maven
COPY --from=build /app/target/zikrelahi-api-0.0.1-SNAPSHOT.jar app.jar

# Render provides the PORT environment variable
EXPOSE 10000

# Start Spring Boot on Render's assigned port
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar app.jar"]
