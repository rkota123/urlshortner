FROM openjdk
ADD spring-boot-urlshorner.jar spring-boot-urlshorner.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "spring-boot-urlshorner.jar"]
