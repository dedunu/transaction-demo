# Transaction Demo

## Assumptions

* Double.MAX_VALUE is the expected largest value.
* Future timestamps won't be processed.
* In case of zero transaction count, program will return 0.
* Only one instance of this micro-service is used at any given moment.


Build will take some time with test because it takes 60 or more seconds sometimes to test.

## Build Instruction

```
mvn clean package
```
Without tests

```
mvn clean package -DskipTests
```
## Run application

```
java -jar target/transaction-demo-1.0-SNAPSHOT.jar
```