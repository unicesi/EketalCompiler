# REAL test's

  This are some test using the REAL compiler, to deploy, you must have locally installed with maven the REAL project as follows:

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

There are 4 examples explained below:


## 1. SecurityRestServer - Using Lineal Temporal Logic to verify a temporal property

This example deploys a simple client/server application using REST in Java with Spark. The server deploys services for interacting with an API from a basic model. The client consumes several server's services.

Finally, over the client a REAL's monitor is launched for verifying a temporal property over all the deployed clients. In order to use this example, we provide some instructions to detect a violation, but first we need to create the jars.

```
mvn clean package
```

This will create the jars for the client and the server. Now, we need to launch the server with:
```
java -jar target/SecurityRestServer.jar
```
It will deploy the server in the machine using its default ip, using the port 4567. You may query this server with your default browser, accessing to the following urls:

```
http://localhost:4567/hello
http://localhost:4567/sessions
http://localhost:4567/session/[1-4]
```

Now, once the server is running, you may deploy as many clients as you want to consume the server interfaces. The client may be run in two modes, first one that will address the violation, and the other one that only makes petitions. Modes are explained below:
```
java -jar target/SecurityRestServer-client.jar
java -jar target/SecurityRestServer-client.jar -destroy
```
As the idea is to use different machine with different ip's, you can specify the server's ip with the -ip x.x.x.x option. If not specify, default is localhost. So, all the petitions will be done to that ip.
```
java -jar target/SecurityRestServer-client.jar -ip <your SERVER ip>
```
## 2. Hadoop Yarn

In this example, we provide an integration of REAL with Hadoop's Yarn project. Main objective is to count the number of Containers launched by yarn in each of the nodes, and, to communicate them eachother.

To launch and test this example, we propose two ways. Inside the [HadoopYarn](https://github.com/unicesi/eketal/tree/master/test/HadoopYarn/)'s folder, you can find complete documentation for built the test, depending on your requirements.


## 3. Hello-World

### 1. Test
```
 cd ../Hello-World/
 mvn clean test
```

### 2. About the source code
   Once it has finished, you can check the generated files in Hello-World/src/main/generated-sources/ directory.
  
   Once you have installed this project in eclipse, you can create your own sample project of type REAL in the "New Project" window, this project have a class with the .eketal extension and his own TestClass, all the other classes are generated.
   
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


## 4. Datarace
  This example was proposed to work in a real case, that consist in issue that occurs when various threads want to access the same memmory location concurrently, and can cause problems or bugs in the program. So, as can be seen in the Datarace.eketal file, There are some events, and one of them take place in a non-controled situation that ends up with a datarace if the incorrect sequence of methods calls is executed. To simulate this problem, there will be two programs that interact with the JBossCache, that is used to save and read some data

The automaton looks for the following sequence of events: **consult** -> **insert** -> **insert**; when it finds it, triggers a reaction. Each node make a different interact in the cache, but their individual interacts are not recognized by the automaton to perform the reaction, so both programs must be running at the time to accomplish the expected event sequence.
```
automaton seqEventDetector(){
  start initialState: (consult -> firtsState)||(insert -> initialState);
  firtsState: (consult -> initialState)||(insert -> secondState);
  secondState: (consult -> initialState)||(insert -> findSequenceState);
  findSequenceState: (consult -> finalState)||(insert -> finalState);
  end finalState: (consult -> finalState)||(insert -> finalState);
}
```

  Finally, a reaction can be performed when occurs this situation.
 ``` 
reaction before seqEventDetector.finalState{
  String msg = "Reaction detected";
  System.out.println("----------------------------------------");
  System.out.println(msg);
  System.out.println("----------------------------------------");
}
```

To run this example, follow this instructions:

**Run it via Maven**

```
cd ..
cd test/Datarace
```
This will compile and generate the sources
```
mvn clean compile
```
Finally, run the generated application
```
mvn exec:java -Dexec.mainClass="local.StartExecute"
```
Now, open a new command line and run the following:
```
mvn exec:java -Dexec.mainClass="local.RunExecute"
```

This example shows how REAL detects a complex pattern, followed by the automaton, in two different Java Virtual Machine's. Once both programs are up, run the command "start" in the program named **StartExecute**, and in the other command line write the same instruction "start", to deploy it. Finally, watch how they send messages between them. At the end of the example, Both programs show the message of the *reaction* defined in the eventClass.

## 5. Deadlock

**Run it via Maven**

```
cd ..
cd test/Deadlock
```
This will compile and generate the sources
```
mvn clean compile
```
Finally, run the generated application
```
mvn exec:java
```
Now you can open another prompt (make sure to also set the JAVA_HOME with Java SE 8) and run the previous command inside the test/Deadlock directory.

The application interacts with the user, so there are three reserved words to use this example, and they are: **prepare**, **commit** and **stop**.
Start both consoles with the command "prepare" to begin the JGroups channels.
In one of the consoles write "commit", and watch in the other console view how it recognize the event and print the Deadlock. 
Finally, use the stop command in both consoles to stop their channels.

**Run it with Eclipse**

### 5.1. Import project
First, import this example as a Maven project.
#### 5.1.1. Go to "File" -> "Import…"
#### 5.1.2. Then "Maven" -> "Existing Maven Projects"
#### 5.1.3. Select the folder where you downloaded the sources, select the "Deadlock" test project and click on "Finish".

### 5.2. Compile sources
The required classes to launch this project have to be generated, to do this, follow the instructions bellow
#### 5.2.1. Right click on the project ->  ->
#### 5.2.2. Then "Maven" -> "Existing Maven Projects"

To generate the required classes, once you have
#### 5.2.3. Right click on the project -> "Maven" -> "Update Project..." and press "OK"
#### 5.2.4. Right click on the project -> "Run As" -> "Maven Build..."
#### 5.2.5. In the Goals field write "clean compile" and press the "Run" button

### 5.3. Run the test

#### 5.3.1 Open two console views in eclipse. Go to "Window" -> "Show View" -> "Console". (recommended in the Console view to uncheck the "Show Console When Standard Out Changes" and "Show Console When Standard Errors Changes")
#### 5.3.2 Run the project twice.
The application interacts with the user, so there are three reserved words to use this example, and they are: prepare, commit and stop.
#### 5.3.3 Start both consoles with the command "prepare" to begin the JGroups channels.
#### 5.3.4 In one of the consoles write "commit", and watch in the other console view how it recognize the event and print the Deadlock. 
#### 5.3.5 Finally, use the stop command in both consoles to stop their channels.
