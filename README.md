#Spring labs README
=============================

### Technologies

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/) (optional)
* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Hibernate](http://hibernate.org/)
* [Swagger](http://swagger.io/)
* [MariaDB] (https://mariadb.org/)

### Initialize DB once time
    DROP DATABASE IF EXISTS springlabs;
    CREATE DATABASE springlabs CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
    USE springlabs;

    DROP USER 'springlabs'@'localhost';
    CREATE USER 'springlabs'@'localhost' IDENTIFIED BY 'DrQi3kclsqO4v';
    GRANT  ALL PRIVILEGES ON springlabs.* TO 'springlabs'@'localhost';
    
### Run

    $ mvn spring-boot:run
    
### Swagger - API document

    UI   : http://localhost:8080/swagger-ui.html
    
    JSON : http://localhost:8080/v2/api-docs
    
### NOTE
    You can initial inserting data by adding (in application.properties) 
    spring.datasource.schema=classpath:import.sql 
    
    Visit http://localhost:8080 and authenticate with one of the two users:
    USER1: username=user, password=user
    USER2: username=admin, password=admin