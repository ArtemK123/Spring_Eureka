FROM java:8-jdk-alpine
COPY . /EurekaClient/
WORKDIR /EurekaClient/
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "./target/EurekaClient-0.0.1-SNAPSHOT.jar"]