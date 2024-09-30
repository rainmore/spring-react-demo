
# Spring Boot and React Demo project

## Configuration

### H2 Database

These applications use an H2 in-memory database. You can use
the H2 console to browse what schema and data is in the database.

### Browse the database contents

With the Spring Boot application running, browse to:

* api – http://localhost:8080/h2-api-console

Then connect using the following:

```
Saved Settings: Generic H2 (Embedded)
Setting Name:   Generic H2 (Embedded)
-------------------------------------
Driver Class:   org.h2.Driver
JDBC URL:       jdbc:h2:mem:test
User Name:      guest
Password:       guest
```

### Database initialisation

The Spring Boot application initialises the database with the data found in

```
src/main/resources/schema.sql
src/main/resources/data.sql
```

## Swagger

http://localhost:8080/swagger-ui/index.html?url=/api-docs
