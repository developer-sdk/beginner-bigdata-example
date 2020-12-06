# 슈퍼 유저 권한 획득 방법

## 아이디:암호를 해쉬값으로 생성 
주키퍼에서 제공하는 
```bash
export ZK_CLASSPATH=/etc/zookeeper/conf/:/usr/hdp/current/zookeeper-server/lib/*:/usr/hdp/current/zookeeper-server/* 
java -cp $ZK_CLASSPATH org.apache.zookeeper.server.auth.DigestAuthenticationProvider super:super123 
OUTPUT: super:super123->super:UdxDQl4f9v5oITwcAsO9bmWgHSI=
```

## java.env에 추가 
```bash
SERVER_JVMFLAGS=-Dzookeeper.DigestAuthenticationProvider.superDigest=super:UdxDQl4f9v5oITwcAsO9bmWgHSI=
```

## 주키퍼 접속후 권한 획득
```bash
[zk: sandbox.hortonworks.com:2181(CONNECTED) 1] addauth digest super:super123
```
