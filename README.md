# Eketal
  Eketal is an event based programming model and language for runtime detection, monitoring, and dynamic modification of distributed and concurrent applications.

  This project uses XText to implement a compiler for EKetal. We use Jbase to generate pure java expressions and statements using the XText framework.

  The compiler generates AspectJ and Java code to instrument distributed applications.

  This guide describes first how to install the IDE as an eclipse's plugin. If you are a programmer interested in experimenting with the language or a reviewer interested in the project, this may be the recommended installation procedure.

  If you are interested in extending or contributing to the language design and compiler implementation we also provide a description of how to download the source code and generate the development environment. First, we describe how to download the source code, how to compile it using MAVEN. Then we describe how to create the development environment from eclipse.

# Installing the IDE as an Eclipse's plugin
#### 1. Open the eclipse IDE
#### 2. Go to Help ...
#### 3. Eclipse update site
  This is the eclipse update site repository: http://unicesi.github.io/eketal/repository/
  You can install in your eclipse as follows:

   Go to Help -> Install new software.

   Add the update site repository and install all the packages.

##### 2. Locally install in your eclipse
#####Compiling from sources
  If you want to compile this project locally, follow the installation instructions using maven, and install it as follow:

  Go to Help -> Install new software.

  Add -> Local.

Finally find "../co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.repository/target/repository" directory and accept.


# Installing EKETAL's development environment using MAVEN

# Installing EKETAL's development environment using Eclipse
