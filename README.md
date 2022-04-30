# clients-api

The objective with this project is to study and test Spring Boot API's with JPA, [Lombok](https://projectlombok.org 
), and [Heroku](https://www.heroku.com) deploys 😎.

# Built with

<a href="https://spring.io/" target="_blank"><img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" target="_blank" ></a> 
<a href="https://www.oracle.com/java/technologies/downloads" target="_blank"><img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" target="_blank" ></a> 
<a href="https://github.com/docker" target="_blank"><img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" target="_blank" ></a> 
<a href="https://maven.apache.org/" target="_blank"><img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" target="_blank" ></a> 
<a href="https://www.heroku.com" target="_blank"><img src="https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white" target="_blank" ></a> 
<a href="https://www.postgresql.org/" target="_blank"><img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" target="_blank" ></a> 

# Documentation
[![](https://raw.githubusercontent.com/swagger-api/swagger.io/wordpress/images/assets/SW-logo-clr.png)](https://app.swaggerhub.com/apis-docs/rpedrodasilva10/Clients/1.0.0)



# Running locally
### What you will need

* [Maven](https://maven.apache.org/)
* [Java](https://www.oracle.com/java/technologies/downloads/) 11 or later
* [Docker](https://github.com/docker)
* [docker-compose](https://github.com/docker/compose)

### Building and starting the application:
Clone the repo and navigate to the project's folder open the terminal and execute the commands bellow  
```sh
cp env.example.properties .env # Copy env example as .env
```
The API's sending e-mail feature depends on configuration, so, to test this scenario, you need to configure mail server information
```markdown
MAIL_HOST=
MAIL_USER=
MAIL_PASSWORD=
MAIL_PORT=
```
After that, you're good to go. Run: 
```sh
mvn clean package # Creates JAR file 
docker-compose up # Spin up all necessary containers
```


# Tasks and Status

**Create a client CRUD** ✔
- [X] Create client
- [X] Update client
- [X] Delete client
- [X] List clients
- [X] Find a specific client
- [X] Control all operations above with DTO's pattern
- [X] Use Bean validation to validate data before saving in the DB

**Implement Exception handling** ✔
- [X] Create models and classes for exception handling
- [X] Define exception handling in the service layer

**Database**
- [X] Persist the previous task results with [PostgreSQL](https://github.com/postgres)

**Virtualization** ✔
- [X] Use [Docker](https://github.com/docker)
- [X] Use [docker-compose](https://github.com/docker/compose) to manage containers spin up/down
- [X] Use environment files to load the container variables dependencies 

**Third part libs** ✔
- [X] Try [ModelMapper](https://github.com/modelmapper/modelmapper)

**Tests** 
- [X] Try [JUnit](https://github.com/junit-team/junit5)
- [X] Add ClientService tests
- [X] Add ClientController tests

**Mail**
- [X] Send e-mail after creating a client successfully
- [X] Create a separate service to send email
- [ ] Use some topic/subscription structure to send emails independently
- [ ] Refactor the ClientService to send emails using messages to a topic or queue

**Docs** ✔
- [X] Add swagger [OpenAPI](https://swagger.io/specification/) specification

Made with ❤ by [rpsilva](https://github.com/rpedrodasilva10)
<div>  
  <a href = "mailto:rpedrodasilva10@gmail.com"><img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
  <a href="https://www.linkedin.com/in/renan-silva-06018a104/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>  
</div>
