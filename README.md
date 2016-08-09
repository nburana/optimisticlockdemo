# Optimisticly Locking Your Spring Boot Services
This project details two ways you can optimisticly lock you web services.

1. With built in mechanisms provided by the JPA specification and the Hibernate provider.
2. With a custom annotation and annotation processor for cases where your ORM does not support optimistic locking.  In our case we're using Spring JDBC.

Start up your Spring Boot app by running the `server.sh` file.
During startup, Spring Boot will initialize an in memory database and create our products table for us.  It will also insert a record for us to work with.

## Database console access
The database can be accessed through the browser at [http://localhost:8080/h2-console](http://localhost:8080/h2-console).  No password necessary.  This database serves both the JPA and JDBC examples.

## Running the JPA based example
To run the JPA example, execute `scripts/test-jpa.py`

## Running the JDBC based example
To run the JPA example, execute `scripts/test-jdbc.py`
