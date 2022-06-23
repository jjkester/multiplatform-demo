# Kotlin Multiplatform demo

The code in this repository demonstrates a use case of Kotlin Multiplatform.

The repository consists of a server and two clients.
The applications can find up to 100 issues for any GitHub repository.
The server serves as an intermediary between the clients and the GitHub API.
The CLI and web application fetch date through the server application.

Both the web app and CLI use the same shared client implementation.
Additionally, some data classes are shared between the server and client(s).

**Versions**

- Gradle 7.4.2
- Kotlin 1.7.0
- Ktor 2.0.2

## Modules

- `domain`: common objects for the client and server modules - implemented in Kotlin (common), Kotlin/JVM and Kotlin/JS
  - `client`: common client library - implemented in Kotlin (common) with [Ktor](https://ktor.io/), build targets for JVM and JS
    - `cli`: command line client - implemented in Kotlin/JVM
    - `webapp`: web client - implemented in Kotlin/JS
  - `server`: server implementation - implemented in Kotlin/JVM with [Ktor](https://ktor.io/)

## Running the applications

### Server

The server can be built and started by executing the following command:

```shell script
./gradlew :server:run
```

The server will start listening on host `localhost` and port `8080`.

### Web app

The web application can be built and started by executing the following command:

```shell script
./gradlew :webapp:run
```

A browser window showing the application will open.

### Command-line interface

The command line interface can be built and started by executing the following command:

```shell script
./gradlew :cli:run --args "<owner> <repo>"
```

Replace `<owner>` and `<repo>` with the repository details.
Example: `"kotlin kotlin-examples"`

## Contributing

The code in this repository is static and for demonstration purposes only.
No contributions will be accepted.

## Disclaimer

The code in this repository is for demonstration purposes only.
The quality of the code is not up to production-ready standards.
