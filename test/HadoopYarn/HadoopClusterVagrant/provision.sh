#!/usr/bin/env bash
while getopts m:f: option

do
 case "${option}"
 in
 m) MASTER=${OPTARG};;
 f) FORMAT=$OPTARG;;
 esac
done

IF $MASTER != hostname
	sh public-key.sh
ELSE
	sh install-key.sh

sh install-java.sh
sh install-git.sh
sh install-maven.sh
IF $MASTER != not
	sh configure-eketal.sh
	sh configure-hadoop.sh
