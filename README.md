# Eketal
  Eketal is an event based programming model and language for runtime detection, monitoring, and dynamic modification of distributed and concurrent applications.

  This project uses XText to implement a compiler for EKetal. We use Jbase to generate pure java expressions and statements using the XText framework.

  The compiler generates AspectJ and Java code to instrument distributed applications.

  This guide describes first how to install the IDE as an eclipse's plugin. If you are a programmer interested in experimenting with the language or a reviewer interested in the project, this may be the recommended installation procedure.

  If you are interested in extending or contributing to the language design and compiler implementation we also provide a description of how to download the source code and generate the development environment. First, we describe how to download the source code, how to compile it using MAVEN. Then we describe how to create the development environment from eclipse.

## Installing the IDE as an Eclipse's plugin
#### 1. Open the eclipse IDE
#### 2. Go to Help -> Install new software
#### 3. Click on "Add..."
#### 4. In the location, put the EKetal update site: http://unicesi.github.io/eketal/repository/ and press OK.
#### 5. Select all the items and proceed to install.

## Installing EKETAL's development environment using MAVEN
### 1. Download the souces
  You can download the sources in two ways: directly from github and using git.
 #### From Github
 Click in the button "Clone or Download" and press the "Download Zip" option.
 #### Using Git
  You must have Git installed.
  Type into the git bash:

```
  git clone https://github.com/unicesi/eketal.git
```

Once you have downloaded the sources, run the folllowing commands in the maven prompt line (with maven installed)

### 2. Increase memory.

```
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

### 3. Build the language

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

### 4. Build sample projects

There are 3 sample projects into the test directory, intructions to deploy are inside that [folder](https://github.com/unicesi/eketal/tree/master/test)


## Installing EKETAL's development environment using Eclipse
### 1. Download the souces
  You can download the sources in two ways: directly from github and using git.
 #### From Github
 Click in the button "Clone or Download" and press the "Download Zip" option
 #### Using Git
  You must have Git installed.
  Type into the git bash:

```
  git clone https://github.com/unicesi/eketal.git
```

First, install the following tools, here are their update sites repositories, install them to your eclipse as explained in the "Installing the IDE as an Eclipse's plugin" section.

-Xtext: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/

-JBase: https://dl.bintray.com/lorenzobettini/xtext-jbase/updates/0.5/

-AspectJ: http://download.eclipse.org/tools/ajdt/45/dev/update

### 2. Import the project

Once you have installed those features, import the downloaded sources in your favorite eclipse IDE as a maven project as follows:
#### 1. File -> Import…
#### 2. Maven -> Existing Maven Projects
#### 3. Select the folder where you downloaded the sources, select all and Finish.

### 3. Fix the build path
Finally, Github does not allow to have empty folders, so eclipse will show a build path error named as follows "The project cannot be built until build path errors are resolved co.edu.icesi.eketal.ide”. So the missing folders must be added manually as shown next:

#### a. In the “Package Explorer” from eclipse, right click in the co.edu.icesi.eketal.ide project -> New -> Folder
#### b. Name that folder as “src” without the quotes and "Finish" option.
#### c. Repeat (a) and name the new folder as "xtend-gen"
#### d. Finally, right click in the in the parent project (co.edu.icesi.eketal.parent) -> Maven -> Update Project.

In case of problems, please submit an issue.
