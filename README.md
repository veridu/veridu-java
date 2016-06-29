# Veridu Java SDK 

Installation
------------
Download or clone this project. 

#### Settings

Run `mvn install` in the root of `veridu-java` project that you just downloaded.

After, there are two ways to setup the `veridu-java` SDK: 

##### Using maven

If you are using `maven`, add the `veridu-java` SDK dependency inside the `pom.xml` file inside the `<dependencies>` tag of your own project, after intalling the `veridu-java` project with `mvn install`:

````xml
<dependency>
    <groupId>com.veridu</groupId>
    <artifactId>veridu-java</artifactId>
    <version>1.0</version>
</dependency>
```

##### Using as a standalone jar library (if you are <strong>NOT</strong> using maven in your own project)

After building the `veridu-java` project, a standalone jar file with all dependencies will be generated at ```veridu-java/target/veridu-java-1.0-jar-with-dependencies.jar```.

If you are <strong>NOT</strong> using maven in own your project, just add this jar file to your build path. 


Code documentation
------------------
In the root directory run ```mvn site```.
A ```index.html``` file is going to be generated inside the directory: ``` /target/site ```. Open it on any web browser. 


Examples
--------
Examples of basic usage can be found at samples [https://github.com/veridu/samples/tree/master/java](https://github.com/veridu/samples/tree/master/java)

Tests
-----
To run the tests, from the terminal in the root directory, please run: ```mvn test```

