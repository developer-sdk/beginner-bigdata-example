# Start
1. ZooKeeper
    1. zkServer.sh start
    2. hdfs zkfc –formatZK
2. JournalNode (Hadoop)
    1. hadoop-daemon.sh start journalnode
3. NameNode (Hadoop)
    1. hdfs namenode -format
    2. adoop-daemon.sh start namenode
    3. hdfs namenode -bootstrapStandby
    4. hadoop-daemon.sh start namenode
7. ZKFC (Hadoop)
    1.  hadoop-daemon.sh start zkfc
4. DataNode (Hadoop) 
    1. hadoop-daemons.sh start datanode
5. Main ResourceManager/NodeManager (Hadoop)
    1.  start-yarn.sh
6. Backup ResourceManager (Hadoop)
    1.  yarn-daemon.sh start resourcemanager
8. MapReduce JobHistory (Hadoop)
    1.  mr-jobhistory-daemon.sh   start historyserver
9. Master Hmaster/HRegionServer (HBase)
    1.  start-hbase.sh 
10. Backup Hmaster (HBase)
    1.  hbase-daemon.sh start master

# Stop
1.  Close backup HMaster
    1.  hbase-daemon.sh stop master
2.  Shut down the main HMaster and HRegionServer
    1.  stop-hbase.sh
3.  Close the history log service
    1.  mr-jobhistory-daemon.sh stop historyserver
4.  Close ZKFC
    1.  hadoop-daemon.sh stop zkfc
5.  Close YARN separately at RM2
    1.  yarn-daemon.sh stop resourcemanager
6.  Close YARN at RM1
    1.  stop-yarn.sh
7.  Shut down all DataNodes in the cluster
    1.  hadoop-daemons.sh stop datanode
8.  Close NameNode2
    1.  hadoop-daemon.sh stop namenode
9.  Close the remaining NameNode
    1.  hadoop-daemon.sh stop namenode
10.  Close the JournalNode cluster
    1.  hadoop-daemon.sh stop journalnode
11.  Shut down the ZooKeeper cluster
    1.  zkServer.sh stop
