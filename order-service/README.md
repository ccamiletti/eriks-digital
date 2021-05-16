# ![Eriks Digital Order APP using Java and Spring boot](example-logo.png)

[![Actions](https://github.com/gothinkster/spring-boot-realworld-example-app/workflows/Java%20CI/badge.svg)](https://github.com/gothinkster/spring-boot-realworld-example-app/actions)

> ### Spring boot + Hibernate codebase containing eriks-digital order app (CRUD, oAuth2, advanced patterns, docker, ActiveMq, etc) that adheres to the eriks-digital assignment spec. [Eriks-digital](https://github.com/ccamiletti/eriks-digital/) spec and API.

This codebase was created to demonstrate a backend implementation built with Spring boot + Hibernate including CRUD operations, authentication, and more.


For more information on how to this works, head over to the [Eriks-digital](https://github.com/ccamiletti/eriks-digital/) repo.

# How it works

## Order Service
The application order-service uses Spring boot (Rest + Hibernate).

* Use the idea of Domain Driven Design to separate the business term and infrastruture term.
* Use Hibernate to implement the [Mapping Metadata](https://www.sourcecodeexamples.net/2018/04/metadata-mapping-pattern.html) pattern for persistence.

And the code organize as this:

1. `Controller` The request goes to the controller, and the controller maps that request and handles it. After that, it calls the service logic if required.
2. `Service` Handles all the business logic. It consists of service classes and uses services provided by data access layers
3. `Repository` Contains all the storage logic and translates business objects from and to database rows.
4. `infrastructure`  contains all the implementation classes as the technique details

## Order consumer
The application order-consumer uses Spring boot (JMS).

The code organize as this:

1. `Config` Everything about the connection to ActiveMQ
2. `Consumer` It is a simple Java class, listening for messages from a queue in ActiveMQ

# Security

Integration with Spring Security to implement Oauth2 using Azure.

Everything about the description of the connection to Azure is stored in the file `application.yml` and `docker-compose.yml`.

# Getting started

Let's open a terminal, go to your_app_directory/docker and run `docker-compose up -d && docker-compose logs -f` (logs -f to see the log on order-consumer application)

# Database

It uses a Mysql database, it is created and executed in a Docker container.
1. http://localhost:8888/ to see the database scheme.
2. Connection parameters:
    * System: MySQL
    * Server: mysql
    * user: admin
    * password: admin
    * Database: eriks_db

# Apache ActiveMQ

It uses Apache ActiveMQ to send messages between different applications. It is created and executed in a Docker container.
1. http://localhost:8161/admin/
2. Connection parameters:
    * user: admin
    * password: password

# Api Documentation

To see the documentation api, open a browser tab at http://localhost:8080/api/swagger-ui/index.html. then, on the search bar type: `/api/v3/api-docs/`

# Try it out with Postman

The entry point address of the backend API (order services) is at http://localhost:8080/api/order

###Getting Azure token 
please, check docker-compose.yml file to get everything to login (clientId, client-secret etc...)
   for more information, visit: https://docs.microsoft.com/en-us/rest/api/servicebus/get-azure-active-directory-token
   
FYI: Credentials available upon request.

# Help
Please fork and PR to improve the code.