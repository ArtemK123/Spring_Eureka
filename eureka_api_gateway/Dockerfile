FROM openjdk:14-jdk
COPY ./src/main/resources/* /eureka_api_gateway/
COPY ./target/eureka-api-gateway-1.0.0.jar /eureka_api_gateway/
WORKDIR /eureka_api_gateway/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./eureka-api-gateway-1.0.0.jar"]