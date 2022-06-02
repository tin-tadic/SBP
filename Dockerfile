FROM maven:3.6.3-jdk-11
COPY pom.xml ./
COPY src src

RUN mvn install -DskipTests
RUN rm -rf src
RUN rm -rf .mvn mwnw pom.xml

FROM openjdk:11.0.11
COPY --from=0 target/imenik-0.0.1-SNAPSHOT.jar app.jar

RUN rm -rf ./target

ENTRYPOINT ["java", "-jar", "app.jar"]