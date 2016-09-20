# Eketal test's

  This are some test using the Eketal compiler, to deploy, you must have locally installed with maven the eketal project as follows:

  You must have maven installed

  Inside the parent project (../co.edu.icesi.eketal.parent-master/) run (with maven) the following command:
  "co.edu.icesi.eketal.parent-master/" project):

```
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

```bash
mvn install
```

Or skipping the test from the compiler as follows:
```bash
mvn install -DskipTests=true
```

There are 3 examples that are:

## 1. Hello-World

### 1. Test
```
 cd ../Hello-World/
 mvn clean test
```

### 2. 
   Once it has finished, you can check the generated files in Example-project/src/main/generated-sources/ directory.
  
   Once you have installed this project in eclipse, you can create your own sample project of type Eketal in the "New Project" window, this project have a class with the .eketal extension and his own TestClass, all the other classes are generated.
   
   About the implementation: This example defines the declaration of an automaton, a working group and the events of interest. We will look toward the source code of the class to explain each of his statements.
   
   Event Declaration:
   ```
event eventoHello():host(localGroup)&&call(core.HelloWorld.helloMethod());
event eventoWorld(): call(core.HelloWorld.worldMethod());
   ```
   Events are a set of formal clauses separated by '&&', '||' or just negated '!'. They can be the intercept of a method call and the group host where that called was received.

   Automaton:
   
   ```
automaton automatonConstructor(){
  start firstState : (eventoHello -> middleState);
  middleState : (eventoHello -> finalState) || (eventoWorld -> firstState);
  end finalState;
}
   ```
   This is a finite state automaton and is composed by three states: firstState, middleState, finalState; where the first is the initial state, and the last is the final state, it has two event triggers (eventHello and eventWorld) defined in the Event Declaration. As we can see, this automaton will recognize all the events sequences of the type: eventoHello (eventoWorld eventoHello)* eventoHello. Those events represents services in distributed application.
   
   Group:
   ```
group localGroup{
  localhost
}
   ```
   This declaration allows to create groups, composed by host's, that will filter the incoming and outcoming calls of the events. In other words, this is reflected in a validation for the received events, and verifies which machine (or node in the distributed application) triggered the event.

## 2. DeadLock


## 3. Datarace


In case of problems, please submit an issue.
