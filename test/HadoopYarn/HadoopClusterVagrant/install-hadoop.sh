#!/usr/bin/env bash
echo "Installing Hadoop"

HADOOP_BIN=$(ls /vagrant/hadoop-2.7.3.tar.gz | head -n +1)
if [ -z $HADOOP_BIN ]; then
 echo "Downloading binaries"
 curl -O -L https://archive.apache.org/dist/hadoop/core/hadoop-2.7.3/hadoop-2.7.3.tar.gz
 echo "Download ended"
else
 echo "Binaries found, proceding to installing it"
 cp $HADOOP_BIN /home/vagrant/
fi

sudo tar -xzvf hadoop-2.7.3.tar.gz -C /home/vagrant
sudo mv /home/vagrant/hadoop-2.7.3 /usr/lib/hadoop
sudo touch /etc/profile.d/hadoop.sh
sudo cat >/etc/profile.d/hadoop.sh <<EOL
HADOOP_COMMON_HOME=/usr/lib/hadoop
HADOOP_MAPRED_HOME=\$HADOOP_COMMON_HOME
HADOOP_HDFS_HOME=\$HADOOP_COMMON_HOME
YARN_HOME=\$HADOOP_COMMON_HOME
HADOOP_PREFIX=\$HADOOP_COMMON_HOME

export HADOOP_COMMON_HOME
export HADOOP_MAPRED_HOME
export HADOOP_HDFS_HOME
export YARN_HOME
export HADOOP_PREFIX
export PATH=\$PATH:\$HADOOP_COMMON_HOME/bin:\${HADOOP_COMMON_HOME}/sbin
EOL

source /etc/profile.d/eketal.sh
source /etc/profile.d/hadoop.sh

#Cuando tail -n es igual a 4 incluye el master, cuando es 5 no lo incluye al master
cat /etc/hosts | tail -n +4  | awk '{print $2}' > $HADOOP_COMMON_HOME/etc/hadoop/slaves
#for i in `cat /etc/hosts | tail -n +4  | awk '{print $2}'`; do sudo echo -e $1 >> $HADOOP_COMMON_HOME/etc/hadoop/slaves
#done

sudo chmod -R 777 /usr/lib/hadoop

cd /home/vagrant/hadoop-rel-release-2.7.3
cp hadoop-yarn-project/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-nodemanager/target/*.jar $HADOOP_COMMON_HOME/share/hadoop/yarn

cd /root/.m2/repository/
cp dk/brics/automaton/automaton/1.11-8/*.jar $HADOOP_COMMON_HOME/share/hadoop/yarn/lib
cp org/aspectj/aspectjrt/1.8.9/*.jar $HADOOP_COMMON_HOME/share/hadoop/yarn/lib
cp org/jgroups/jgroups/3.6.14.Final/*.jar $HADOOP_COMMON_HOME/share/hadoop/yarn/lib
#cp $EKETAL_HOME/co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.lib.eketal/target/*jar $HADOOP_COMMON_HOME/share/hadoop/yarn/lib
cp $EKETAL_HOME/co.edu.icesi.eketal.parent-master/co.edu.icesi.eketal.lib/target/*jar $HADOOP_COMMON_HOME/share/hadoop/yarn/lib

cp /vagrant/default-config-files/* $HADOOP_COMMON_HOME/etc/hadoop

exec 3>&1 1>~/logs
for i in `cat ${HADOOP_COMMON_HOME}/etc/hadoop/slaves | head -n -1`; do echo $i; sudo -u vagrant rsync -avxP --exclude=logs $HADOOP_COMMON_HOME/ $i:/home/vagrant/hadoop/; cat /etc/profile.d/hadoop.sh | sudo -u vagrant ssh slave1 "touch hadoop.sh && cat > hadoop.sh && sudo mv hadoop.sh /etc/profile.d && sudo mv /home/vagrant/hadoop/ /usr/lib/hadoop"
done
exec 1>&3 3>&-;

