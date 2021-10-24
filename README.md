# Spring demo app

A hello world Spring Boot application that uses PostgreSQL.
It features saying hello to the world as well as saving dates.
List of endpoints can be viewed on Swagger at http://localhost:8080/swagger-ui/index.html.

Docker images for PostgreSQL and Spring are available at https://hub.docker.com/u/krikki13.

# Instructions
Unfortunately running the images directly (using docker run command) does not work, because Spring cannot connect to Postgres.

It works with docker-compose however.
In order to do that:

-checkout this repository

-execute: mvn clean install

-execute: docker-compose up
