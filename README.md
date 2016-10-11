# Eketal
  Eketal is an event based programming model and language for runtime for detencting and monitoring of distributed and concurrent applications.
   
  This project uses xtext to develop the programming language, and a compatible library named Jbase, its purpose is to allow the use of pure java expressions and statements into the xtext framework. Also, Eketal uses Aspectj to detect and react events.

## 1. Download:

### 1. From the page:

Click in the button "Clone or Download" and press the "Download Zip" option

### 2. Using Git.

You must have Git installed.
Type into the git bash:

```
  git clone https://github.com/unicesi/eketal.git
```

## 2. Installation:

There are several ways to install this software: (1) With maven (binaries) (2) With eclipse

### 1. Using maven.

Once you have downloaded the sources, run the folllowing prompts in the command (with maven installed) line

#### 1. Increase memory.

```
export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
```

#### 2. Build the language

```
cd co.edu.icesi.eketal.parent-master/
mvn clean install
```

You can skip the test of the kernel Ketal with this instruction

```
cd co.edu.icesi.eketal.parent-master/
mvn clean install -DskipTests=true
```

#### 3. Build example projects

There are 3 example projects into the test directory, intructions to deploy are inside that [folder](https://github.com/unicesi/eketal/tree/master/test)

### 2. Try it with eclipse:

 First, it is necessary to install the tools mencioned at the beginning, here are their update sites repositories, add them to your eclipse as explained next in the "Eclipse update site" tittle.

-Xtext http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/

-AspectJ http://download.eclipse.org/tools/ajdt/45/dev/update

-JBase https://dl.bintray.com/lorenzobettini/xtext-jbase/updates/0.5/

#### 1. Eclipse update site
  This is the eclipse update site repository: http://unicesi.github.io/eketal/repository/
  You can install in your eclipse as follows:

   Go to Help -> Install new software.
   
   Add the update site repository and install all the packages.

#### 2. Locally install in your eclipse
#####Compiling from sources
  If you want to compile this project locally, follow the installation instructions using maven, and install it as follow:

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
