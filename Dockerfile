FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/promotion-service-0.0.1-SNAPSHOT.jar PromotionService.jar
ENTRYPOINT ["java", "-jar", "PromotionService.jar"]