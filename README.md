# Eketal
  Eketal is a project based on xtext and uses aspectj for his purpose. For its deploy, you must have installed both tools mentioned before.
  You can find their releases, for eclipse mars, here:

  -Xtext http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
  
  -AspectJ http://download.eclipse.org/tools/ajdt/45/dev/update

###Eclipse update site
  http://unicesi.github.io/eketal/repository/
  You can install in your eclipse int the following way:

   Go to Help -> Install new software.
   
   Add the update site repository.

###Sample project
  
   Once you have done this, you can create your own sample project of type Eketal in the "New Project" window, this project have a class with the extension .eketal and his own TestClass, all the other classes are generated.
  
###Compiling from sources
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

###Locally install
You can install locally the project in the following way:

	Go to Help -> Install new software.

	Add -> Local.

Finally find "../co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.repository/target/repository" directory and accept

###Developers
   To contribute , this project can be clone to a local repository and you can work from there. You must have Maven installed
   
   In case of having a build path problem -that can not find two source folders- in eclipse, do the following: in the .ide project, you must add manually the folders as the following: Right click in the .ide project -> New -> Folder. Next, in the name put “src” and repeat the procces to create a "xtend-gen" folder. Finally, Right click in the in the parent project -> Maven -> Update Project.
   
   In case of problems, please submit an issue.
