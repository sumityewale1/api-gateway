
FROM eclipse-temurin:17-jdk

WORKDIR /api-gateway

COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
