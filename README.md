# ProductStoreApp

# Run

## Running Instructions

### Step 1: Setup

- **Java Installation**: Make sure you have Java JDK 17 installed. Confirm your Java version with `java -version`.
  Install it for [Windows][1].
- **Docker Installation**: Install Docker for [Windows][3]..
- Navigate to the root directory of the project. Find the path: `src->main->resources`.
  Inside the `apl.yaml` file, locate the `url` line:  jdbc:mysql://${DB_HOST:localhost}:
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