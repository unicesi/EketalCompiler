#!/usr/bin/env bash

#On master

for i in `cat /etc/hosts | tail -n +4  | awk '{print $2}'`; do sudo -u vagrant sshpass -p "vagrant" sudo -u vagrant ssh-copy-id -o StrictHostKeyChecking=no vagrant@$i;
done

#NODE_MASTER=$1
#ssh-keyscan -t rsa $NODE_MASTER | awk '{print $2 " " $3 " vagrant@" $1}' >> .ssh/known_hosts
