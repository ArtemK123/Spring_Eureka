FROM java:8-jdk-alpine
COPY . /EurekaServer/
WORKDIR /EurekaServer/
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "./target/EurekaServer-0.0.1-SNAPSHOT.jar"]