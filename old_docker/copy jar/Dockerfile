#FROM eclipse-temurin:11-jdk-jammy
#FROM eclipse-temurin:8-jdk-focal
FROM openjdk:8

#WORKDIR /app

# Forma original
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:resolve
#COPY src ./src
#RUN chmod +x .mvn

#CMD ["./mvnw", "dependency:resolve"]

#CMD ["./mvnw", "spring-boot:run"]



WORKDIR /app
#COPY src ./src
COPY . .
#RUN mvn clean package
RUN mvn install
#COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]

