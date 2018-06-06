#!/usr/bin/env bash
echo "Updating hostfile"
sudo echo -e "$1\t$2" >> /etc/hosts
#sudo -u vagrant touch /home/vagrant/host_list
#sudo -u vagrant echo -e "$1\t$2" >> /home/vagrant/host_list
