FROM openjdk:21-jdk-slim
WORKDIR /app
VOLUME /tmp
COPY target/*.jar ums-Backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "ums-Backend-0.0.1-SNAPSHOT.jar"]