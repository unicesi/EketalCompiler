# OpenJDK 8
# Maven 3.2.2
# Git
# Nano

# pull base image Ubuntu 16.04 LTS (Xenial)
FROM ubuntu:xenial

MAINTAINER Camilo Pimienta Rosero

# this is a non-interactive automated build - avoid some warning messages
ENV DEBIAN_FRONTEND noninteractive

# install the OpenJDK 8 java runtime environment and curl
RUN apt update; \
  apt upgrade -y; \
  apt install -y default-jdk curl wget git nano; \
  apt-get install build-essential -y; \
  apt-get clean

ENV JAVA_HOME /usr
ENV PATH $JAVA_HOME/bin:$PATH

# get maven 3.2.2 and verify its checksum
RUN wget --no-verbose -O /tmp/apache-maven-3.2.2.tar.gz http://archive.apache.org/dist/maven/maven-3/3.2.2/binaries/apache-maven-3.2.2-bin.tar.gz; \
  echo "87e5cc81bc4ab9b83986b3e77e6b3095 /tmp/apache-maven-3.2.2.tar.gz" | md5sum -c

# install maven
RUN tar xzf /tmp/apache-maven-3.2.2.tar.gz -C /opt/; \
  ln -s /opt/apache-maven-3.2.2 /opt/maven; \
  ln -s /opt/maven/bin/mvn /usr/local/bin; \
  rm -f /tmp/apache-maven-3.2.2.tar.gz
ENV MAVEN_HOME /opt/maven

# install protobuf
RUN curl -L -O https://github.com/google/protobuf/releases/download/v2.5.0/protobuf-2.5.0.tar.gz
RUN tar xvzf /protobuf-2.5.0.tar.gz -C /opt/; \
 rm -f /protobuf-2.5.0.tar.gz; \
  cd /opt/protobuf-2.5.0; \
  ./configure; \
  make; \
  make check; \
  make install
ENV PROTO_BF /opt/protobuf-2.5.0
ENV LD_LIBRARY_PATH /usr/local/lib
CMD ["bin/bash"]