#Dockerfile from config-server with jdk11 maven
#First Stage
FROM openjdk:11-jdk-slim as builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main-skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY src src
RUN ./mvnw clean package -DskipTest

#Second Stage
FROM openjdk:11-jdk-slim
ARG TARGET_FOLDER=/app/target
COPY --from=builder $TARGET_FOLDER/api-client-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "api-client-0.0.1-SNAPSHOT.jar"]