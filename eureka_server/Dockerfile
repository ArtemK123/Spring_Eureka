FROM openjdk:14-jdk
COPY ./src/main/resources/* /eureka_server/
COPY ./target/eureka-server-1.0.0.jar /eureka_server/
WORKDIR /eureka_server/
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "./eureka-server-1.0.0.jar"]