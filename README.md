# Swami 2020
This application will become the foundation for a football pick 'em game amongst friends.

This tag is meant a demo of an API on  http4k, HikariCP, and Jooq.

## Setup The Database
This application requires a PostreSQL instance. A Docker Compose file exists to do this for you. You need
Docker installed on your local. From the project root folder, simply run:

`docker-compose up`

When you are done play, be sure to run?

`docker-compose down`

The database needs to be initialized. I'll get Gradle tasks strung together correctly eventually. For now, you need to run:

`./gradlew generateSwamiJooqSchemaSource` 

This will create the database tables, populate them, and generate the Java classes mapping to them for Jooq

## To Test
The application tests can be executed with:

`./gradlew test`

The tests are located in:

`<root>/src/test/kotlin/swami2020`

## Running The Application
Currently, the application has only been verified running with Gradle:

`./gradlew run

The application exposes two `GET` endpoints:

`http://localhost:9000/teams` will list teams

`http://localhost:9000/teams/<uuid>` will return the specific team tied identified by the UUID, and throw a 404 if not found) 
