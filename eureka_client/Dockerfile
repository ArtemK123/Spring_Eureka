FROM maven:3.6.3-jdk-14 AS build

RUN mkdir /tmp/eureka_common
COPY ./eureka_common/pom.xml /tmp/eureka_common
COPY ./eureka_common/src /tmp/eureka_common/src

RUN mkdir /tmp/eureka_client
COPY ./eureka_client/pom.xml /tmp/eureka_client
COPY ./eureka_client/src /tmp/eureka_client/src

WORKDIR /tmp/eureka_common
RUN mvn -B install

WORKDIR /tmp/eureka_client
RUN mvn -B package

FROM openjdk:14-jdk

RUN mkdir /app
COPY --from=build /tmp/eureka_client/target/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]