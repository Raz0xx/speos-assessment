# speos-assessment

B2Boost - Grails REST API problem

## Run the app

To run the application, after installing maven and its dependencies, simply start the `SpeosAssessmentApplication.java`
file.
The application runs within an embedded Tomcat environment, it's directly accessible on the
port [8080](http://localhost:8080)
of your machine.

## APIs

### Health check

A Spring Boot Actuator API is available under the `/health` endpoint. It tests the full health of the application,
including its connectivity to the H2 database.

### Partners

The Partners API is available under the `/api/partners` endpoint. For functional testing or documentation please use the
Swagger environment available under the [/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Tests

You can run the integration tests with Spring by executing the `mvn test` command.
No unit tests have been created for this application as the scope is rather limited, the integration tests already cover
the end-to-end flows.