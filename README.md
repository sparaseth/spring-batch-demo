# spring-batch-demo

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

1. Change the database preperties in `application.properties` file
2. Import `schema.sql` into your database
3. Build the project using `mvn clean install`
4. Run using `mvn spring-boot:run`
5. This will insert all the data from `Employees.csv` to your database table. 
