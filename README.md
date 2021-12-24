# Clients API Project

## Objective

The objective with this project is to study and test Spring Boot API's with JPA, [Lombok](https://projectlombok.org
), and [Heroku](https://www.heroku.com) deploys.

# Running locally
### What you will need

* [Maven](https://maven.apache.org/)
* [JDK](https://www.oracle.com/java/technologies/downloads/) 11 or later
* [Docker](https://github.com/docker)
* [docker-compose](https://github.com/docker/compose)

### Building and starting the application:
Inside the project's folder open the terminal and execute the commands bellow  
```sh
cp env.example.properties .env # Copy env example as .env
mvn clean package # Creates JAR file 
docker-compose up # Spin up all necessary containers
```


# Tasks and Status

**Create a client CRUD**
- [X] Create client
- [X] Update client
- [X] Delete client
- [X] List clients
- [X] Find a specific client
- [X] Control all operations above with DTO's pattern
- [X] Use Bean validation to validate data before saving in the DB

**Implement Exception handling**
- [X] Create models and classes for exception handling
- [X] Define exception handling in the service layer

**Database**
- [X] Persist the previous task results with [PostgreSQL](https://github.com/postgres)

**Virtualization** 
- [X] Use [Docker](https://github.com/docker)
- [X] Use [docker-compose](https://github.com/docker/compose) to manage containers spin up/down
- [ ] Use environment files to load the container variables dependencies 

**Third part libs**
- [X] Try [ModelMapper](https://github.com/modelmapper/modelmapper)

**Tests**
- [ ] Try [JUnit](https://github.com/junit-team/junit5)

**Mail**
- [X] Send e-mail after creating a client successfully
- [ ] Create a separate service to send email
- [ ] Use some topic/subscription structure to send emails independently
- [ ] Refactor the ClientService to send emails using messages to a topic or queue

**Docs**
- [X] Add swagger [OpenAPI](https://swagger.io/specification/) specification