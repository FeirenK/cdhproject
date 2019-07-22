#!/bin/bash

name=$1

echo "hello $name" >> /tmp/oozieshell.log

sudo -u Feirentest hadoop fs -mkdir -p /Feirentest/jars
sudo -u Feirentest hadoop fs -put /opt/cloudera/parcels/CDH/jars/spark-examples-1.6.0-cdh5.13.1-hadoop2.6.0-cdh5.13.1.jar /Feirentest/jars
sudo -u Feirentest hadoop fs -ls /Feirentest/jars



[root@ip-172-31-6-148 ~]# sudo -u Feirentest hadoop fs -mkdir -p /user/Feirentest/oozie/testoozie
[root@ip-172-31-6-148 ~]# ll /opt/workflow.xml
-rwxr-xr-x 1 root root 810 Feb 13 12:23 /opt/workflow.xml
[root@ip-172-31-6-148 ~]# sudo -u hdfs hadoop fs -put /opt/workflow.xml /user/Feirentest/oozie/testoozie
[root@ip-172-31-6-148 ~]# sudo -u hdfs hadoop fs -ls /user/Feirentest/oozie/testoozie



sudo -u Feirentest hadoop fs -mkdir -p /Feirentest/jars
sudo -u Feirentest hadoop fs -put /opt/cloudera/parcels/CDH/jars/hadoop-mapreduce-examples-2.6.0-cdh5.13.1.jar /Feirentest/jars
sudo -u Feirentest hadoop fs -ls /Feirentest/jars


[root@ip-172-31-6-148 opt]# sudo -u Feirentest hadoop fs -mkdir -p /user/Feirentest/oozie/javaaction
[root@ip-172-31-6-148 opt]# sudo -u Feirentest hadoop fs -put /opt/workflow.xml /user/Feirentest/oozie/javaaction
[root@ip-172-31-6-148 opt]# sudo -u Feirentest hadoop fs -ls /user/Feirentest/oozie/javaaction


sudo -u Feirentest hadoop fs -mkdir -p /Feirentest/jars
sudo -u Feirentest hadoop fs -put /opt/ooziejob.sh /Feirentest/jars
sudo -u Feirentest hadoop fs -ls /Feirentest/jars

[root@ip-172-31-6-148 opt]# sudo -u Feirentest hadoop fs -mkdir -p /user/Feirentest/oozie/sehllaction
[root@ip-172-31-6-148 opt]# sudo -u Feirentest hadoop fs -put /opt/workflow.xml /user/Feirentest/oozie/sehllaction
[root@ip-172-31-6-148 opt]# sudo -u Feirentest hadoop fs -ls /user/Feirentest/oozie/sehllaction

