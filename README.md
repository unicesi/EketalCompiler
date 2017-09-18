# Eketal
  Eketal is an event based programming model and language for runtime detection, monitoring, and dynamic modification of distributed and concurrent applications.

  This project uses XText to implement a compiler for EKetal. We use Jbase to generate pure java expressions and statements using the XText framework.

  The compiler generates AspectJ and Java code to instrument distributed applications.

  This guide describes first how to install the development environment using MAVEN and test it. If you are a programmer interested in experimenting with the language or a reviewer interested in the project, this may be the recommended installation procedure.

  If you are interested in extending or contributing to the language design and compiler implementation we also provide a description of how to download the source code and generate the development environment. First, we describe how to download the source code, how to compile it using MAVEN. Then we describe how to install the IDE as an Eclipse's plugin and create the development environment from eclipse.

## 1. Installing EKETAL's development environment using MAVEN

This section describes how to download the project, how to build and install the language, and how to build a sample project. This is an example using JBossCache, to run an example using a mock of JBossCache and a recent version of JGroups, use the second git clone command. Finally, in this [video](https://www.dropbox.com/s/os1rrj7h3gu2l7i/Eketal.mp4?dl=0) you can see all the steps described next.

### 1.1. Download the sources
  You can download the sources in two ways: directly from github or using git.
#### From Github
 Click in the button "Clone or Download" and press the "Download Zip" option and uncompress the files.
#### Using Git
  You must have Git installed.
  Type into the git bash:

```
  git clone -b "master" https://github.com/unicesi/eketal.git
```
	
Once you have downloaded the sources, run the following commands in the maven prompt line (with maven installed)

### 1.2. Increase memory.

```
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

### 1.3. Build the language

Before start installing, please use java SE 8 as the JAVA_HOME to avoid this [issue](https://github.com/unicesi/eketal/issues/3).

To change the environment variables in IOS/linux
```
export JAVA_HOME=</your/java/path>
```
In windows, change it in environment variables.

You can skip the test of the kernel Ketal with this instruction

```
cd eketal/co.edu.icesi.eketal.parent-master/
mvn clean install -DskipTests=true
```

Or run it with the test's (it takes longer)

```
cd eketal/co.edu.icesi.eketal.parent-master/
mvn clean install
```

### 1.4. Build sample projects
Inside the [test folder](https://github.com/unicesi/eketal/tree/master/test) are three deployable test's and how to use them, the main example is the Datarace and will be describe how to deploy next:
```
cd ..
cd test/Datarace
```
This will compile and generate the sources
```
mvn clean package
```
Finally, run the generated application. **Make sure to have access to internet and you can 'ping www.google.com' from your machine**
```
mvn exec:java -Dexec.mainClass="local.StartExecute"
```
Now, open a new command line and run the following:
```
mvn exec:java -Dexec.mainClass="local.RunExecute"
```

If you like to run this example with the jar files, from a command line (with a jdk 1.8 as the JAVA_HOME), execute the following commands:
```
java -jar target/StartExecute.jar
```
And in a new command line:
```
java -jar target/RunExecute.jar
```

This example shows how Eketal detects a complex pattern, followed by the automaton, in two different Java Virtual Machine's. Once both programs are up, run the command "start" in the program named **StartExecute**, and in the other command line write the same instruction "start", to deploy it. Finally, watch how they send messages between them. At the end of the example, Both programs show the message of the *reaction* defined in the eventClass.

Explore more about this example and the other examples [here](https://github.com/unicesi/eketal/tree/master/test)

## 2. Installing the IDE as an Eclipse's plugin
#### 1. Open the eclipse IDE
#### 2. Go to "Help" -> "Install New Software..."
#### 3. Click on "Add..."
#### 4. In the location, put the EKetal update site: http://unicesi.github.io/eketal/repository/ and press OK.
#### 5. Select all the items and proceed to install.

## 3. Installing EKETAL's development environment using Eclipse **NEON**
### 3.1. Download the sources
  You can download the sources in two ways: directly from Github and using git.
 #### From Github
 Click in the button "Clone or Download" and press the "Download Zip" option
 #### Using Git
  You must have Git installed.
  Type into the git bash:

```
  git clone https://github.com/unicesi/eketal.git
```

First, install the following tools in the suggested order, here are their update sites repositories, install them to your eclipse as explained in the (2) "Installing the IDE as an Eclipse's plugin" section.

Firts, xtext, later AspectJ, then JBase and finally MWE:

-Xtext: http://download.eclipse.org/modeling/tmf/xtext/updates/releases/2.12.0/ 

-AspectJ: http://download.eclipse.org/tools/ajdt/46/dev/update

[x] *AspectJ Development tools*

[x] *Other AJDT Tools*

-JBase: https://dl.bintray.com/lorenzobettini/xtext-jbase/updates/0.7/

\* *It is not necessary to download sources of JBase*

-MWE: http://download.eclipse.org/modeling/emft/mwe/updates/releases/2.9.0/


### 3.2. Import the project

Once you have installed those features, import the downloaded sources in your favorite eclipse IDE as a maven project as follows:
#### 3.2.1. Go to "File" -> "Import…"
#### 3.2.2. Then "Maven" -> "Existing Maven Projects"
#### 3.2.3. Select the folder where you downloaded the sources, select all and click on "Finish".

### 3.3. Fix the build path
Finally, Github does not allow to have empty folders, so eclipse will show a build path error named as follows "The project cannot be built until build path errors are resolved co.edu.icesi.eketal.ide”. So the missing folders must be added manually as shown next:

#### 3.3.1. In the “Package Explorer” view from eclipse, right click in the "co.edu.icesi.eketal.ide project" -> "New" -> "Folder"
#### 3.3.2. Name that folder as “src” without the quotes and "Finish" option.
#### 3.3.3. Repeat (3.3.1.) and name the new folder as "xtend-gen"
#### 3.3.4. Finally, right click in the in the parent project "co.edu.icesi.eketal.parent" -> "Maven" -> "Update Project".

In case of problems, please submit an issue.
