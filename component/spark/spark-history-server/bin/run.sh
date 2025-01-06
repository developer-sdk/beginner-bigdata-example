#!/bin/bash

# 웹 URL의 프록시 베이스 서정 
export APPLICATION_WEB_PROXY_BASE=/proxy_base

# 실행 
/usr/lib/jdk/bin/java -cp /opt/spark/conf/:/opt/spark/jars/*:/etc/hadoop/conf/:/opt/hadoop/share/hadoop/common/lib/*:/opt/hadoop/share/hadoop/common/*:/opt/hadoop/share/hadoop/hdfs/:/opt/hadoop/share/hadoop/hdfs/lib/*:/opt/hadoop/share/hadoop/hdfs/*:/opt/hadoop/share/hadoop/yarn/:/opt/hadoop/share/hadoop/yarn/lib/*:/opt/hadoop/share/hadoop/yarn/*:/opt/hadoop/share/hadoop/mapreduce/lib/*:/opt/hadoop/share/hadoop/mapreduce/*:mysql-connector-java.jar/etc/tez/conf:/opt/tez/*:/opt/tez/lib/*:/opt/hadoop/share/hadoop/tools/lib/* \
-Dspark.log.dir=/var/log/spark \
-Dspark.log.file=spark-ubuntu-historyserver.log \
-Xmx1g org.apache.spark.deploy.history.HistoryServer

# s3를 히스토리 로그 파일 디렉토리로 설정하는 방법. 
# port 를 변경하는 방법 
/usr/lib/jdk/bin/java -cp /opt/spark/conf/:/opt/spark/jars/*:/etc/hadoop/conf/:/opt/hadoop/share/hadoop/common/lib/*:/opt/hadoop/share/hadoop/common/*:/opt/hadoop/share/hadoop/hdfs/:/opt/hadoop/share/hadoop/hdfs/lib/*:/opt/hadoop/share/hadoop/hdfs/*:/opt/hadoop/share/hadoop/yarn/:/opt/hadoop/share/hadoop/yarn/lib/*:/opt/hadoop/share/hadoop/yarn/*:/opt/hadoop/share/hadoop/mapreduce/lib/*:/opt/hadoop/share/hadoop/mapreduce/*:mysql-connector-java.jar/etc/tez/conf:/opt/tez/*:/opt/tez/lib/*:/opt/hadoop/share/hadoop/tools/lib/* \
-Dspark.history.fs.logDirectory=s3a://s3-bucket/log-history \
-Dspark.history.ui.port=28082 \
-Dspark.log.dir=/var/log/spark \
-Dspark.log.file=spark-ubuntu-historyserver.log \
-Xmx1g org.apache.spark.deploy.history.HistoryServer
