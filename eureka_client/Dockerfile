FROM openjdk:14-jdk
COPY ./src/main/resources/* /eureka_client/
COPY ./target/eureka-client-1.0.0.jar /eureka_client/
WORKDIR /eureka_client/
ENTRYPOINT ["java", "-jar", "./eureka-client-1.0.0.jar"]