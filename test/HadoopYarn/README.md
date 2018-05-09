# Hadoop Yarn test

In this example, we modify Hadoop Yarn's project. So, it is necessary to built entire Hadoop's project from its sources.

To achieve this, there are some sotware requirements required for Hadoop and REAL, as:

* Java 8
* Maven
* ProtocolBuffer 2.5 (Require for Hadoop only)
* Hadoop sources 2.7.3

However, if you have Docker or Vagrant (with VirtualBox), we provide alternatives, so you do not have to install any new software. Even, with this last one, you may, also, launch a cluster with all the artifacts deploy. So, 

## 1. With Docker
Inside [Docker_built](https://github.com/unicesi/eketal/tree/master/test/HadoopYarn/Docker_built)'s folder, we present a Dockerfile that creates an ubuntu image with all the software requirements described above, also, some others that may be usefull. Once the image is built, you may launch a container, with that image, and built the required artifacts this test. Installed software in the image is:

* Java 8
* Maven
* Git
* ProtocolBuffer
* Wget (Require when installing maven)
* Curl

To built the image, use the following scripts.
Inside the [Docker_built](https://github.com/unicesi/eketal/tree/master/test/HadoopYarn/Docker_built)'s folder:

```
	docker build -t real_image .
```
Now, create a new folder wherever you want, so, there it can be download and built REAL and HADOOP (it may be source directory in which you download this repository).

```
docker run -it -v $(pwd):/root real_image /bin/bash
#Run this in the containers console:
cd /root

#This command may be omitted if you choose the shared folder to be root of this repository
git clone -b "master" https://github.com/unicesi/eketal.git 

cd eketal/co.edu.icesi.eketal.parent-master/
#Installed REAL in the container
mvn clean install -DskipTests=true
#Download Hadoop sources
curl -L -O https://github.com/apache/hadoop/archive/rel/release-2.7.3.tar.gz
tar -xzvf release-2.7.3.tar.gz -C /root
cd /root/hadoop-rel-release-2.7.3
cp /root/eketal/test/HadoopYarn/HadoopClusterVagrant/ENodeManager.eketal hadoop-yarn-project/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-nodemanager/src/main/java/org/apache/hadoop/yarn/server/nodemanager/
cp /root/eketal/test/HadoopYarn/HadoopClusterVagrant/pom.xml hadoop-yarn-project/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-nodemanager/
mvn clean package -DskipTests
```
Finally, as docker built all of this in a shared folder with the host machine, the hadoop-yarn's jar is in the same directory that you created.

The generated jars are in "hadoop-yarn-project/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-nodemanager/target/" directory.

## 2. With Vagrant
Inside [HadoopClusterVagrant](https://github.com/unicesi/eketal/tree/master/test/HadoopYarn/HadoopClusterVagrant)'s folder

```
vagrant up
```
Bellow command will built an entire cluster environment with Hadoop integrated with REAL, so you just only need to interact with the master's Virtual Machine, and run Hadoop.

## 3. Installing software artifacts.

Required software was presented at the beggining. So, once those artifacts are installed, you may proceed as follows:

Install Eketal (as described in root's directory of this repository). Then just build the Hadoop's artifacts with:
```
cp $EKETAL_HOME/test/HadoopYarn/HadoopClusterVagrant/ENodeManager.eketal $HADOOP_SRC/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-nodemanager/src/main/java/org/apache/hadoop/yarn/server/nodemanager/
cp $EKETAL_HOME/test/HadoopYarn/HadoopClusterVagrant/pom.xml $HADOOP_SRC/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-nodemanager/
cd $HADOOP_SRC 
mvn clean package -DskipTests
```
