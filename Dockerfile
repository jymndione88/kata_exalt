FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/account-0.0.1-SNAPSHOT.jar /app/account-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/account-0.0.1-SNAPSHOT.jar"]