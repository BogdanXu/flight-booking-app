FROM maven:3.8.5-openjdk-17
WORKDIR /payments
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run

#FROM openjdk:17-jdk-alpine
#COPY target/payments-0.0.1-SNAPSHOT.jar app-1.0.0.jar
#CMD ["java", "-jar", "app-1.0.0.jar"]
#EXPOSE 8080
