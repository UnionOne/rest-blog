# rest-blog

##### You’ll use the gradle build task frequently. This task compiles, tests, and assembles the code into a JAR file. You can run it like this:

`gradle build`

___

##### Run the following command to download and initialize the wrapper scripts:

`gradle wrapper`

___

##### Run the wrapper script to perform the build task, just like you did previously:

`./gradlew build`

___

##### Then you can run the app!

`./gradlew bootRun`

___

#### Open in whatever rest client

* `http://localhost:8080/api/post?id=1` - return person with id = 1
```json
{
    "id": 1,
    "name": "post1",
    "description": "this is 1 post",
    "text": "text1",
    "date": {
        "year": 2016,
        "month": "MAY",
        "monthValue": 5,
        "chronology": {
            "calendarType": "iso8601",
            "id": "ISO"
        },
        "era": "CE",
        "dayOfYear": 128,
        "dayOfWeek": "SATURDAY",
        "leapYear": true,
        "dayOfMonth": 7
    },
    "author": "author1",
    "new": false
}
```
* `http://localhost:8080/api/post/1` - return person with id = 1
* `http://localhost:8080/api/post/all` - return all posts
* `http://localhost:8080/api/post/` - added new post
```json
{
    "name": "new post",
    "description": "this is new post",
    "text": "text of new post",
    "author": "author of new post"
}
```

##### `@SpringBootApplication` is a convenience annotation that adds all of the following:
* `@Configuration` tags the class as a source of bean definitions for the application context.
* `@EnableAutoConfiguration` tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
* Normally you would add `@EnableWebMvc` for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a `DispatcherServlet`.
* `@ComponentScan` tells Spring to look for other components, configurations, and services in the package, allowing it to find the `GreetingController`.

___

##### The Spring Boot gradle plugin provides many convenient features:

* It collects all the jars on the classpath and builds a single, runnable `über-jar`, which makes it more convenient to execute and transport your service.
* It searches for the `public static void main()` method to flag as a runnable class.
* It provides a built-in dependency resolver that sets the version number to match Spring Boot dependencies. You can override any version you wish, but it will default to Boot’s chosen set of versions.