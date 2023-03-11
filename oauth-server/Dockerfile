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
COPY --from=builder $TARGET_FOLDER/oauth-server-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=9100
ENV PORT $PORT_APP
EXPOSE $PORT

# Install the wait-for-it script
RUN apt-get update && apt-get install -y wget
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it.sh

CMD ["./wait-for-it.sh", "eureka-server:8761", "--timeout=30", "--", "java", "-jar", "oauth-server-0.0.1-SNAPSHOT.jar"]