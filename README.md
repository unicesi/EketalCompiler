# Eketal
  Eketal is a project based on xtext with jbase integration and uses aspectj for his purpose. For its deploy, you must have installed both tools mentioned before.
  You can find their releases, for eclipse mars, here:


  -Xtext http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
  
  -AspectJ http://download.eclipse.org/tools/ajdt/45/dev/update
  
  -[JBase](https://github.com/LorenzoBettini/jbase) https://dl.bintray.com/lorenzobettini/xtext-jbase/updates/0.5/

  First we will explain how to test the Eketal software: (1) with maven, (2) installing in eclipse (remote and local installation). Second, (3) how to contribute to the project, and finally, we will explain the (4) sample project.

## 1. Try it with maven:

You must have maven installed

### 1. Increase memory.

```
   export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

### 2. Build the language

```
 cd co.edu.icesi.eketal.parent-master/
 mvn clean install
```

### 3. Build the example projects

```
 cd ../Example-project/
 mvn clean test
```
 Once it has finished, you can check the generated files in Example-project/src/main/generated-sources/ directory.

## 2. Try with eclipse:

### 1. Eclipse update site
  This is the eclipse update site repository: http://unicesi.github.io/eketal/repository/
  You can install in your eclipse as follows:

   Go to Help -> Install new software.
   
   Add the update site repository and install.

### 2. Locally install
####Compiling from sources
  If you want to compile this project locally, you must do the following
  -Download or clone the project.
  -Install maven.
  -Increase memory.
  
```bash
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```
  -Where you downloaded your copy (inside the "co.edu.icesi.eketal.parent-master/" project):
```bash
mvn install
```

You can locally install the project in the following way:

  Go to Help -> Install new software.

  Add -> Local.

Finally find "../co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.repository/target/repository" directory and accept.

## 3. Developers
  To contribute , this project can be clone to a local repository and you can work from there.
  For deploy in your workspace, you must have Eclipse mars, with aspectj, maven and xtext as mentioned in the beggining. Then you can import it as a maven project.
   
   In case of having a build path problems with the following message: "The project cannot be built until build path errors are resolved  co.edu.icesi.eketal.ide" in your eclipse, do the following: in the co.edu.icesi.eketal.ide project, you must add manually the missing folders as the following: 
   >Right click in the co.edu.icesi.eketal.ide project -> New -> Folder.
   
   Next, in the name put "src" and repeat the procces to create a "xtend-gen" folder. Finally:
   >Right click in the in the parent project -> Maven -> Update Project.

## 4. Sample project
  
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
   
   
   In case of problems, please submit an issue.
