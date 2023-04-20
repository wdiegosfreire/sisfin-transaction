FROM openjdk:11
VOLUME /tmp
COPY ./target/*.jar sisfin-transaction.jar
ENTRYPOINT ["java","-jar","/sisfin-transaction.jar"]