# eketal

###Eclipse update site
  http://unicesi.github.io/eketal/repository/
  
###Compiling from sources
  If you want to compile this project locally, you must do the following
  -Install maven
  -Increase memory
  
```bash
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```
  -Where you download your copy (inside the parent project):
```bash
mvn install
```
You can install locally the project in the following way:
--Go to Help -> Install new software
Add -> Local
Finally find "co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.repository/target/repository" directory and accept

  
