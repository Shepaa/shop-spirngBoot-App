# ProductStoreApp

# Run

## 1. Preparing

- **Install Java**. Application is running on Java JDK 17.
  To make sure that you are using the correct version - `java -version`. Install for [Windows][1].
- **Install Docker**. For [Windows][3].
- After you must go root file of project and follow the path: src->main->resources.
  There you will see the apl.yaml file and in it you need the url line: jdbc:mysql://${DB_HOST:localhost}:
  3306/${DB_NAME:ProductstoreDB}
  change 3306 to 3307

## Step 2: Building

Run `./mvnw clean install`

Alternatively, if Maven is installed:

Run `mvn clean install`

## Step 3: Local Execution

Before running, ensure the database is up:

``` sh
docker-compose up -d
```

After that, you should go to the target folder and execute the following command using the console:

``` sh
java -jar shop-0.0.1-SNAPSHOT.jar
```

Happy Hacking! =)