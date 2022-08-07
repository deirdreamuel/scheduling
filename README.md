# Scheduling Assistant
This project is a backend service for scheduling meetings written in Kotlin
tools include Spring, SpringJPA (hibernate), PostgreSQL


## Setup 
compile with `mvn clean package`

run with `mvn spring-boot:run`

## Database Setup
enter database credentials in application.properties with the following format:

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.generate-ddl=true
```
