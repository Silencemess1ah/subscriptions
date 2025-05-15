FROM openjdk:17-jdk-slim
COPY build/libs/subscriptions-0.0.1-SNAPSHOT.jar subscriptions.jar
CMD ["java", "-jar", "subscriptions.jar"]