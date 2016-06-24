# Veridu Java SDK 

Installation
------------
Download or clone this project. 


Settings
--------
Inside src/main/resources there is going to be a config.json.dist file. You need to rename it to config.json and set the configuration values with your own credentials. 

Now run  ````mvn install```` in the root of veridu-java project that you just downloaded.

After, there are two ways to setup the veridu-java SDK: 

###### Using maven

If you are using ```maven```, add the veridu-java SDK dependency inside the `pom.xml` file of your own project:

````xml
<dependencies>
        <!-- add other dependencies of your own project over here! -->
        <dependency>
            <groupId>com.veridu</groupId>
            <artifactId>veridu-java</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```

###### Using as a jar library

Inside the src/main/target there is going to be a jar file. In your own project, just add this jar file as a dependency. 



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

