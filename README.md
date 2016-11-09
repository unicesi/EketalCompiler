# Eketal
  Eketal is an event based programming model and language for runtime detection, monitoring, and dynamic modification of distributed and concurrent applications.

  This project uses XText to implement a compiler for EKetal. We use Jbase to generate pure java expressions and statements using the XText framework.

  The compiler generates AspectJ and Java code to instrument distributed applications.

  This guide describes first how to install the development environment using MAVEN and test it. If you are a programmer interested in experimenting with the language or a reviewer interested in the project, this may be the recommended installation procedure.

  If you are interested in extending or contributing to the language design and compiler implementation we also provide a description of how to download the source code and generate the development environment. First, we describe how to download the source code, how to compile it using MAVEN. Then we describe how to install the IDE as an Eclipse's plugin and create the development environment from eclipse.

## 1. Installing EKETAL's development environment using MAVEN
### 1.1. Download the sources
  You can download the sources in two ways: directly from github and using git.
 #### From Github
 Click in the button "Clone or Download" and press the "Download Zip" option.
 #### Using Git
  You must have Git installed.
  Type into the git bash:

```
  git clone https://github.com/unicesi/eketal.git
```

Once you have downloaded the sources, run the following commands in the maven prompt line (with maven installed)

### 1.2. Increase memory.

```
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

### 1.3. Build the language

You can skip the test of the kernel Ketal with this instruction

```
cd co.edu.icesi.eketal.parent-master/
mvn clean install -DskipTests=true
```

Or run it with the test's (it takes longer)

```
cd co.edu.icesi.eketal.parent-master/
mvn clean install
```

### 1.4. Build sample projects
Inside the [test folder](https://github.com/unicesi/eketal/tree/master/test) are three deployable test's and how to use them, the main example is the Deadlock and will be describe next:
#### 1.4.1. Import project
First, import this example as a Maven project.
##### 1.4.1.1. Go to "File" -> "Import…"
##### 1.4.1.2. Then "Maven" -> "Existing Maven Projects"
##### 1.4.1.3. Select the folder where you downloaded the sources, select the "Deadlock" test project and click on "Finish".

#### 1.4.2. Compile sources
The required classes to launch this project have to be generated, to do this, follow the instructions bellow
##### 1.4.2.1. Right click on the project ->  ->
##### 1.4.2.2. Then "Maven" -> "Existing Maven Projects"

To generate the required classes, once you have
##### 1.4.2.1. Right click on the project -> "Maven" -> "Update Project..." and press "OK"
##### 1.4.2.2. Right click on the project -> "Run As" -> "Maven Build..."
##### 1.4.2.3. In the Goals field write "clean compile" and press the "Run" button
##### 1.4.2.4. Open two console views in eclipse. Go to "Window" -> "Show View" -> "Console". (recommended in the Console view to uncheck the "Show Console When Standard Out Changes" and "Show Console When Standard Errors Changes")
##### 1.4.2.5. Run the project twice.
The application interacts with the user, so there are three reserved words to use this example, and they are: prepare, commit and stop.
##### 1.4.2.6. Start both consoles with the command "prepare" to begin the JGroups channels.
##### 1.4.2.7. In one of the consoles write "commit", and watch in the other console view how it recognize the event and print the Deadlock. 
##### 1.4.2.8. Finally, use the stop command in both consoles to stop their channels.

Explore the other examples [here](https://github.com/unicesi/eketal/tree/master/test)

## 2. Installing the IDE as an Eclipse's plugin
#### 1. Open the eclipse IDE
#### 2. Go to "Help" -> "Install New Software..."
#### 3. Click on "Add..."
#### 4. In the location, put the EKetal update site: http://unicesi.github.io/eketal/repository/ and press OK.
#### 5. Select all the items and proceed to install.

## 3. Installing EKETAL's development environment using Eclipse
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

First, install the following tools, here are their update sites repositories, install them to your eclipse as explained in the (2) "Installing the IDE as an Eclipse's plugin" section.

-Xtext: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/

-JBase: https://dl.bintray.com/lorenzobettini/xtext-jbase/updates/0.5/

-AspectJ: http://download.eclipse.org/tools/ajdt/45/dev/update

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
