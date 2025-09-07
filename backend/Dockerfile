# --- Build stage ---
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# --- Run stage ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# (optional) run as non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# copy the built jar
COPY --from=build /app/target/*.jar app.jar

# Render provides PORT; tell Spring to use it
ENV PORT=8080
ENV JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]
