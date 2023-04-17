FROM openjdk:11
VOLUME /tmp
# RUN mvn clean package -DskipTests
COPY ./target/*.jar sisfin-maintenance.jar
ENTRYPOINT ["java","-jar","/sisfin-maintenance.jar"]