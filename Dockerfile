FROM maven:3.8.6-openjdk-18-slim as build
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /opt/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM eclipse-temurin:18-alpine

WORKDIR /opt/app

COPY --from=build /opt/app/target/backend-test-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-jar", "/opt/app/backend-test-0.0.1-SNAPSHOT.jar"]
