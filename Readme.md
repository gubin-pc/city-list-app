# Cities

## How to run
### In docker container

- To run application on 8080 port use
```
./mvnw clean package docker:build docker:run
```
- To run application on a specific port use
```-Dservice.port=```

Example:
```
./mvnw clean package docker:build docker:run -Dservice.port=9870
```

It definitely works for the **amd64** architecture. There may be problems with running on the **M1**.

### In local
Java 17 is required
```
./mvnw clean package
java -jar target/city-list-app-0.0.1-SNAPSHOT.jar
```

## Query examples
API can be viewed and tried at the link http://localhost:8080/swagger-ui/index.html