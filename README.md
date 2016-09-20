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

You can skip the test as follow

```
 cd co.edu.icesi.eketal.parent-master/
 mvn clean install -DskipTests=true
```

### 3. Build example projects

There are 3 example projects into the directory test, intructions to deploy are inside the [directory](https://github.com/unicesi/eketal/new/master/test)

## 2. Try with eclipse:

### 1. Eclipse update site
  This is the eclipse update site repository: http://unicesi.github.io/eketal/repository/
  You can install in your eclipse as follows:

   Go to Help -> Install new software.
   
   Add the update site repository and install.

### 2. Locally install in your eclipse
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
mvn clean install
```

Or skipping the test from the compiler as follows:
```bash
mvn clean install -DskipTests=true
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
   
   
   In case of problems, please submit an issue.
