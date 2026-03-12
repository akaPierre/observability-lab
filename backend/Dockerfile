FROM eclipse-temurin:17-jre-alpine
RUN apk --no-cache add curl
WORKDIR /app
COPY target/observability-lab-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]