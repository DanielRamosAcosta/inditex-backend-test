FROM eclipse-temurin:17 as build
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /opt/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY ./src ./src
RUN ./mvnw clean install -Dmaven.test.skip=true

# This should be alpine, but there is no alpine version for Mac M1
FROM openjdk:17-slim

WORKDIR /opt/app

COPY --from=build /opt/app/target/backend-test-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-jar", "--enable-preview", "/opt/app/backend-test-0.0.1-SNAPSHOT.jar"]
