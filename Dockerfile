FROM maven:latest AS builder

WORKDIR /app

COPY pom.xml pom.xml
RUN mvn dependency:go-offline
COPY src src
RUN mvn package

FROM openjdk:latest

WORKDIR /etc/app

COPY --from=builder /app/target/mindgesment-*.jar mindgesment.jar

ENTRYPOINT ["java"]
CMD ["-jar", "mindgesment.jar"]
