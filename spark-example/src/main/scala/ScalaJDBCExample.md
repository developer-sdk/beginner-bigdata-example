Spark SQL에서 JDBC를 이용하여 데이터베이스에 직업 연결하여 데이터베이스의 데이터를 가져와서 처리할 수 있습니다. 다음과 같이 사용합니다. 


```scala
import java.util.Properties

// 프로퍼티 설정 
val prop = new Properties()
prop.put("user", "db_user_name")
prop.put("password", "db_password")

// 데이터베이스 연결 및 뷰 생성 
val tableDf = spark.read.jdbc("jdbc:mysql://mysql_url:mysql_port/database_name", "table_name", prop)
tableDf.createOrReplaceTempView("tempTable")

// 테이블 조회
val scDf = spark.sql("""SELECT * 
                          FROM tempTable""") 
// 출력 
scDf.show
```
