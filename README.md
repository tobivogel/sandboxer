# sandboxer

small service to display output of other services

* Run `./gradlew idea` to generate IntelliJ idea files and open the project in the idea
* Go to ./sandboxerService and run `./gradlew run` to start the embedded jetty
* `localhost:8090/json` will hit the sandboxer json endpoint
* `localhost:8090/html` will hit the sandboxer html endpoint