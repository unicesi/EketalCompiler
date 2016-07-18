# Eketal
  Eketal is a project based on xtext and uses aspectj for his purpose. For its deploy, you must have installed both tools mentioned before.
  You can find their releases, for eclipse mars, here:

  -Xtext http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
  -AspectJ http://download.eclipse.org/tools/ajdt/45/dev/update

###Eclipse update site
  http://unicesi.github.io/eketal/repository/
  You can install in your eclipse int the following way:

   Go to Help -> Install new software.
   and add the update site repository.
   Once you have down this, you can create your own sample project of type Eketal, this project had class with the extension .eketal and his own TestClass, this allows to create a sample project that generates its own classes
  
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
   To contribute , the project can be clone to a local repository and can be work from there.
   In case of problems please submit an issue.
