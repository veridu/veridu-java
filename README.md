# Veridu Java SDK 

Installation
------------

Download this project or clone it into the same directory of your project:
```
├── Your directory
│   ├── veridu-java
│   ├── your project

```

On your `pom.xml` file, add :
  
```xml
<dependencies>
        <!-- add other dependencies over here! -->
        <dependency>
            <groupId>com.veridu</groupId>
            <artifactId>veridu-java</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```

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

