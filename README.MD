This is a sandbox project for testing microservice architecture.
The Spring Eureka is used

How to run:
1. mvn clean install (in root folder) - it will build all packages in project
2. Run postgres and setup database:
	- create database called Megabit
	- create user with name=MegabitUser and password=Megabit
	- give MegabitUser privileges to use Megabit database
3. Run eureka-server (will run on localhost:8761)
4. Run RabbitMQ with default settings. The easiest way to do this - in docker:
	- docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
5. Run eureka-config-server (localhost:8888)
6. Run eureka-client(may be multiple instances) (internal IP)
7. Run eureka-api-gateway (localhost:8080)

Exposed API:
- eureka-server: localhost:8761
- eureka-api-gateway: localhost:8080