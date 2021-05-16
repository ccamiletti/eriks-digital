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

* Use the idea of Domain Driven Design to separate the business term and infrastruture term.

And the code organize as this:

1. `Config` Everything about the connection to ActiveMQ
2. `Consumer` It is a simple Java class, listening for messages from a queue in ActiveMQ

# Security

Integration with Spring Security to implement Oauth2 using Azure.

Everything about the description of the connection to Azure is stored in the file `application.yml`` and docker-compose.yml`.

# Database

It uses a Mysql database, it is created and executed in a Docker container.

# Getting started

Let's open a terminal, go to your_app_directory/docker and run `docker-compose up -d && docker-compose logs -f` (logs -f to see the log on order-consumer application)

To see the documentation api, open a browser tab at http://localhost:8080/api/ .  
Alternatively, you can run

    curl http://localhost:8080/tags

# Try it out with [Docker](https://www.docker.com/)

You need Docker installed.

	docker-compose up -d

# Try it out with a RealWorld frontend

The entry point address of the backend API is at http://localhost:8080, **not** http://localhost:8080/api as some of the frontend documentation suggests.

# Run test

The repository contains a lot of test cases to cover both api test and repository test.

    ./gradlew test

# Use git pre-commit hook

Follow the instruction from [google-java-format-git-pre-commit-hook](https://github.com/a1exsh/google-java-format-git-pre-commit-hook) to use a `pre-commit` hook to make the code format style stable from different contributors.

# Help

Please fork and PR to improve the code.