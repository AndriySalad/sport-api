FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk wget unzip

RUN wget https://services.gradle.org/distributions/gradle-7.5-bin.zip -P /tmp
RUN unzip -d /opt/gradle /tmp/gradle-7.5-bin.zip
ENV PATH=/opt/gradle/gradle-7.5/bin:$PATH

COPY . /app
WORKDIR /app

RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

RUN ls -la /app/build/libs

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/build/libs/sport-api-17.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "db:5432", "--", "java", "-jar", "app.jar"]
