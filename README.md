## Run the application 
The application can be executed with docker (the image can be found on dockerhub):
```
docker run -d -p 8080:8080 --name tasklist hijackit/tasklist:1.0
```
Then you can access the OpenAPI (Swagger) page at:
http://127.0.0.1:8080/swagger/views/swagger-ui/index.html

## Build the application
In order to compile and build the application you will need a JDK 17.

Then you will be able to launch the command `gradlew assembleDist` that will create a ZIP file with all the libraries and executables.

You can also recreate the docker image with `gradlew dockerBuild`

## Application overview
It is a very simple backend CRUD application that exposes the APIs through a REST interface.
Through the APIs it is possible to get, create, update and delete tasks.

The tasks are persisted in an embedded database (H2) and the schema migrations are automatically applied by the application.