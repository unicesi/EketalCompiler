# Eketal test's

  This are some test using the Eketal compiler, to deploy, you must have locally installed with maven the eketal project as follows:

  You must have maven installed

  Inside the parent project (../co.edu.icesi.eketal.parent-master/) run the following commands from the bash:

```
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

```bash
mvn clean install
```

Or skipping the test from the compiler as follows:
```bash
mvn clean install -DskipTests=true
```

There are 3 examples that are:

## 1. Hello-World

### 1. Test
```
 cd ../Hello-World/
 mvn clean test
```

### 2. About the source code
   Once it has finished, you can check the generated files in Hello-World/src/main/generated-sources/ directory.
  
   Once you have installed this project in eclipse, you can create your own sample project of type Eketal in the "New Project" window, this project have a class with the .eketal extension and his own TestClass, all the other classes are generated.
   
   About the implementation: This example defines the declaration of an automaton, a working group and the events of interest. We will look toward the source code of the class to explain each of his statements.
   
   Event Declaration:
   ```
event eventHello():host(localGroup)&&call(core.HelloWorld.helloMethod());
event eventoWorld(): call(core.HelloWorld.worldMethod());
   ```
   Events are a set of formal clauses separated by '&&', '||' or just negated '!'. They can be the intercept of a method call and the group host where that called was received.

   Automaton:
   
   ```
automaton automatonConstructor(){
  start firstState : (eventHello -> middleState);
  middleState : (eventHello -> finalState) || (eventoWorld -> firstState);
  end finalState;
}
   ```
   This is a finite state automaton and is composed by three states: firstState, middleState, finalState; where the first is the initial state, and the last is the final state, it has two event triggers (eventHello and eventWorld) defined in the Event Declaration. As we can see, this automaton will recognize all the events sequences of the type: eventHello (eventoWorld eventHello)* eventHello. Those events represents services in distributed application.
   
   Group:
   ```
group localGroup{
  localhost
}
   ```
   This declaration allows to create groups, composed by host's, that will filter the incoming and outcoming calls of the events. In other words, this is reflected in a validation for the received events, and verifies which machine (or node in the distributed application) triggered the event.


## 2. Datarace
  This example was proposed to work in a real case, that consist in issue that occurs when various threads want to access the same memmory location concurrently, and can cause problems or bugs in the program. So, as can be seen in the Datarace.eketal file, There are some events, and one of them take place in a non-controled situation that ends up with a datarace if the incorrect sequence of methods calls is executed.

  The automaton recognize the event sequence follow by: request writeLock (cacheLoader writeUnlock init request writeLock)* cacheLoader evictNode
```
automaton dataraceDetector(){
  start init: (request -> acquireLock);
  acquireLock: (writeLock -> potentialDatarace);
  potentialDatarace: (cacheLoader -> evictData) || (evictNode -> restart);
  evictData: (evictNode -> datarace);
  restart: (writeUnlock -> init);
  end datarace;
}
```

  Finally, a reaction can be performed when occurs this situation.
 ``` 
reaction before dataraceDetector.datarace{
  String msg = "DataRace detected!";
  System.out.println("----------------------------------------");
  System.out.println(msg);
  System.out.println("----------------------------------------");
}
```

## 3. Deadlock
  
  ### 3.1. Import project
First, import this example as a Maven project.
#### 3.1.1. Go to "File" -> "Import…"
#### 3.1.2. Then "Maven" -> "Existing Maven Projects"
#### 3.1.3. Select the folder where you downloaded the sources, select the "Deadlock" test project and click on "Finish".

### 3.2. Compile sources
The required classes to launch this project have to be generated, to do this, follow the instructions bellow
#### 3.2.1. Right click on the project ->  ->
#### 3.2.2. Then "Maven" -> "Existing Maven Projects"

To generate the required classes, once you have
#### 3.2.3. Right click on the project -> "Maven" -> "Update Project..." and press "OK"
#### 3.2.4. Right click on the project -> "Run As" -> "Maven Build..."
#### 3.2.5. In the Goals field write "clean compile" and press the "Run" button

### 3.3. Run the test

#### 3.3.1 Open two console views in eclipse. Go to "Window" -> "Show View" -> "Console". (recommended in the Console view to uncheck the "Show Console When Standard Out Changes" and "Show Console When Standard Errors Changes")
#### 3.3.2 Run the project twice.
The application interacts with the user, so there are three reserved words to use this example, and they are: prepare, commit and stop.
#### 3.3.3 Start both consoles with the command "prepare" to begin the JGroups channels.
#### 3.3.4 In one of the consoles write "commit", and watch in the other console view how it recognize the event and print the Deadlock. 
#### 3.3.5 Finally, use the stop command in both consoles to stop their channels.
  
