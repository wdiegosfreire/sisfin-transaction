FROM eclipse-temurin:11-jdk
VOLUME /tmp
COPY ./target/*.jar sisfin-transaction.jar
ENTRYPOINT ["java","-jar","/sisfin-transaction.jar"]