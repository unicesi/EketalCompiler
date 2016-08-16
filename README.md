# Eketal
  Eketal is a project based on xtext and uses aspectj for his purpose. For its deploy, you must have installed both tools mentioned before.
  You can find their releases, for eclipse mars, here:


  -Xtext http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
  
  -AspectJ http://download.eclipse.org/tools/ajdt/45/dev/update

##Try it with maven:

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

##Eclipse update site
  http://unicesi.github.io/eketal/repository/
  You can install in your eclipse int the following way:

   Go to Help -> Install new software.
   
   Add the update site repository and install.

###Sample project
  
   Once you have done this, you can create your own sample project of type Eketal in the "New Project" window, this project have a class with the .eketal extension and his own TestClass, all the other classes are generated.
   
   About the implementation: This example defines the declaration of an automaton, a working group and the events of interest. We will look toward the source code of the class to explain each of his statements.
   
   Automaton:
   
   ```
   automaton automatonConstructor(){
    start firstState : (eventoHello -> middleState);
    middleState : (eventoHello -> finalState) || (eventoWorld -> firstState);
    end finalState;
   }
   ```
   This is a finite state automaton and is composed by three states: firstState, middleState, finalState; where the first is the initial state, and the last is the final state, it counts with two events (eventHello and eventWorld) which are defined later. As we can see, this automaton will recognize all the events sequences of the type: eventoHello (eventoWorld eventoHello)* eventoHello. Those events represents services in a distributed application.
   
   Group:
   ```
   group localGroup{
    localhost
   }
   ```
   This declaration allows to create groups, with multiple host's, that will filter the incoming calls of the events. In other words, this is reflected in a validation for the incoming events, and verifies which machine (or node in the distributed application) triggers the event.
   
   Event Declaration:
   ```
   event eventoHello():host(localGroup)&&call(core.HelloWorld.helloMethod());
   event eventoWorld(): call(core.HelloWorld.worldMethod());
   ```
   Finally, the events are a set of methods with formal sintax, each 'event' declaration can support multiple methods call and the 'host' mappes the groups that will be affected by that event.
  
##Compiling from sources
  If you want to compile this project locally, you must do the following
  -Install maven
  -Increase memory
  
```bash
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```
  -Where you downloaded your copy (inside the parent project):
```bash
mvn install
```

##Locally install
You can install locally the project in the following way:

  Go to Help -> Install new software.

  Add -> Local.

Finally find "../co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.repository/target/repository" directory and accept

##Developers
  To contribute , this project can be clone to a local repository and you can work from there.
  For deploy in your workspace, you must have Eclipse mars, with aspectj, maven and xtext as mentioned in the beggining. Then you can import as a maven project and import it. You must have Maven installed.
   
   In case of having a build path problems with the following message "The project cannot be built until build path errors are resolved  co.edu.icesi.eketal.ide" in your eclipse, do the following: in the co.edu.icesi.eketal.ide project, you must add manually the missing folders as the following: Right click in the co.edu.icesi.eketal.ide project -> New -> Folder. Next, in the name put "src" and repeat the procces to create a "xtend-gen" folder. Finally, Right click in the in the parent project -> Maven -> Update Project.
   
   In case of problems, please submit an issue.
