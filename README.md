#  cwuteify, next gen spoify rip off 

## Overview
Goal of this project was to develop a backend api for a music streaming service. The backend has been developed using the Spring framework. This project currently consists only of the backend api. Frontend part will be developed later in the future. But for now, you can use the Insomnia collection to play with the api. You can find link to the collection lower in this document.

## Technical Details

This project is built using the following technologies:
- Spring Boot
- Java
- PostgreSQL

The following dependencies are used:
- spring-boot-starter-data-jpa: for using JPA with Spring to access and manage data in a database
- spring-boot-starter-web: for building web applications using Spring MVC
- postgresql: for accessing and managing a PostgreSQL database
- spring-boot-starter-security: for adding security features to the application
- spring-boot-starter-test: for testing the application
- lombok: for generating boilerplate code such as getters, setters, and constructors
- java-jwt: for generating and validating JSON Web Tokens (JWTs) [Auth0](https://auth0.com/docs/secure/tokens/access-tokens#jwt-access-tokens)

## Prerequisites

- Java 17
- IDE of your choice
- Maven
- PostgreSQL

You will need to have PostgreSQL server running either on your machine or server and you will need to configure it in the application.properties file. You can find the file in the src/main/resources folder.

## Installation

1. Clone this repository to your local machine.
2. Open the project in IntelliJ IDEA.
3. Import the project as a Maven project.
4. In the "Project" pane on the left side of the screen, right-click the project and select "Add Framework Support."
5. Select "Spring Boot" and click "OK."
6. In the "Project" pane, navigate to the "src/main/resources" folder and open the "application.properties" file.(You can either edit application.properties or create different profile. There is application-dev.properties as an example.)
7. Update the following properties with your PostgreSQL connection details:
    - spring.datasource.url
    - spring.datasource.username
    - spring.datasource.password
8. Save the "application.properties" file and close it.
9. In the "Project" pane, navigate to the "src/main/java" folder and open the "Application.java" file.
10. Right-click the "Application" class and select "Run 'Application.main()'" to start the application.

## Usage

Open Insomnia or any other api testing tool and either import the Insomnia collection or create your own requests.
[DOCS](https://docs.cwute.dev/)
[Insomnia Collection](https://docs.cwute.dev/Insomnia-export.yaml)

## Testing
No tests. I forgor

## Looking for databse scripts?
There are no database scripts. Everything is done by using JPA annotations with PostgreSQL driver.

## Important urls for this project
- [Api endpoint](https://api.cwute.dev)
- [Docs](https://docs.cwute.dev)
- [Simple web to test fetching song data](https://test.cwute.dev)
- [Insomnia collection](https://docs.cwute.dev/Insomnia-export.yaml)
- Databse address - 167.172.109.251:5432 (I genuinely have no clue why i connect to it trough public ip instead of localhost)

## Sources
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL](https://www.postgresql.org/)
- [Java JWT](https://auth0.com/docs/secure/tokens/access-tokens#jwt-access-tokens)
- [File uploading](https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial)


## Formatting
Code is formatted using the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).  
[Github Action i used](https://github.com/axel-op/googlejavaformat-action)

## Security
- Passwords are hashed using BCrypt.
- JWTs are used for authentication.
- JWTs are stored inside Authorization header and prefixed with   "Bearer ".They are being sent with every request to the server.


## Deployment
In order to deploy this project you will need to have installed java 17 and set up PostgreSQL server. For creation of admin account run jar file with create-admin argument. For example:
```bash
java -jar cwuteify-17.70.13-dev.jar --create-admin
```
You will be prompted to enter passwords. During next starts you wont need to run this again unless u drop the database.

## Other Notes
- default port is 727
- Application is in really early stage and is probably vulnerable to many attacks.
- If you wanna test this out it's most likely running on my server  [api.cwute.dev](https://api.cwute.dev)
- I'm really dumb so instead of running this app in some sane way i run it on localhost and then i use apache2 to forward requests to localhost. It's probably not the best way to go about this but atleast ssl is easy to use this way.
## License

[MIT](https://choosealicense.com/licenses/mit/)
