FROM maven:3.6.3-jdk-14 AS build

RUN mkdir /tmp/eureka_common
COPY ./eureka_common/pom.xml /tmp/eureka_common
COPY ./eureka_common/src /tmp/eureka_common/src

RUN mkdir /tmp/eureka_api_gateway
COPY ./eureka_api_gateway/pom.xml /tmp/eureka_api_gateway
COPY ./eureka_api_gateway/src /tmp/eureka_api_gateway/src

WORKDIR /tmp/eureka_common
RUN mvn -B install

WORKDIR /tmp/eureka_api_gateway
RUN mvn -B package

FROM openjdk:14-jdk

RUN mkdir /app
COPY --from=build /tmp/eureka_api_gateway/target/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "./app/spring-boot-application.jar"]

EXPOSE 8080