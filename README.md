Price processing service
========================

Assumptions
-----------

- Service is not required to be multi-threaded
- The output need not be like for like in formatting to the example output.

Pre-requisites
--------------

- JDK 1.7
- Maven 2 or above

Dependencies
------------

- Spring
- HSQLDB
- Junit
- Maven shade plugin (to create uber jar with all dependencies included)

To run unit tests
-----------------

	mvn clean test

To build executable jar
-----------------------

	mvn package

jar will be built to target folder

To run jar
-----------

Run the shaded jar (filename ending with uber)

	java -jar awesome-marketdata-service-1.0-SNAPSHOT-uber.jar

Test evidence
-------------

Once built, can be found in target/surefire-reports