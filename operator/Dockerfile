#FROM maven:3.8.6-amazoncorretto-17
#
## create app folder for sources
#WORKDIR /build
#COPY pom.xml /build
##Download all required dependencies into one layer
#RUN mvn -B dependency:resolve dependency:resolve-plugins
##Copy source code
#COPY src /build/src
## Build application
#RUN mvn package -DskipTests=true
#
#ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/build/target/operator-0.0.1-SNAPSHOT.jar"]
################

FROM openjdk:17-jdk-alpine
##ARG JAR_FILE=target/*.jar
##COPY ./target/SpringAPI-0.0.1.jar app.jar
COPY target/operator-0.0.1-SNAPSHOT.jar app-1.0.0.jar
ENTRYPOINT ["java","-jar","/app-1.0.0.jar"]

