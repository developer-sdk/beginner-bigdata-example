object DataFrameSample extends App {
  // -----------------------------
  // 데이터 프레임
  // -----------------------------

  // -----------------------------
  // 스파크 세션 초기화
  // -----------------------------
  import org.apache.spark.sql.SparkSession

  val spark = SparkSession
    .builder()
    .appName("Spark SQL basic example")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  // 하이브 연결 방법
  // hive.metastore.uris 옵션에 하이브 메타스토어 접속 주소를 입력한다.
  val spark = SparkSession.builder().appName("sample")
    .config("hive.metastore.uris", "thrift://hive_metastore_ip:hive_metastore_port")
    .enableHiveSupport().getOrCreate()


  // -----------------------------
  // 데이터 프레임 초기화 - RDD to DF #1 Array
  // -----------------------------
  val wordsRDD = sc.parallelize(Array("a", "b", "c", "d", "a", "a", "b", "b", "c", "d", "d", "d", "d"))
  val wordsDF = wordsRDD.toDF()

  // 데이터 프레임 확인
  scala> wordsDF.show()
  +-----+
  |value|
  +-----+
  |    a|
  |    b|
  |    c|

  // 칼럼명을 지정했을 때
  val wordsDF = wordsRDD.toDF("word")
  scala> wordsDF.show()
  +----+
  |word|
  +----+
  |   a|
  |   b|
  |   c|


  // -----------------------------
  // 데이터 프레임 초기화 - RDD to DF #2 튜플
  // -----------------------------
  val peopleRDD = sc.parallelize(
    Seq( ("David", 150),
      ("White", 200),
      ("Paul",  170) )
  )

  val peopleDF = peopleRDD.toDF("name", "salary")
  scala> peopleDF.show()
  +-----+------+
  | name|salary|
  +-----+------+
  |David|   150|
  |White|   200|
  | Paul|   170|
  +-----+------+

  // -----------------------------
  // 데이터 프레임 초기화 - Row
  // -----------------------------
  import org.apache.spark.sql._
  import org.apache.spark.sql.types._

  // RDD를 Row 로 초기화
  val peopleRDD = sc.parallelize(
    Seq(
      Row("David", 150),
      Row("White", 200),
      Row("Paul",  170)
    )
  )

  // RDD를 데이터프레임으로 변형하기 위한 스키마 생성
  val peopleSchema = new StructType().
    add(StructField("name", StringType, true)).
    add(StructField("salary", IntegerType, true))
  val peopleDF = spark.createDataFrame(peopleRDD, peopleSchema)
  peopleDF.show()

  // -----------------------------
  // 데이터 프레임 초기화 - TXT 파일
  // -----------------------------
  $ hadoop fs -ls /user/people.txt
  -rw-r--r--   2 hadoop hadoop         30 2019-03-13 06:14 /user/people.txt
  $ hadoop fs -cat /user/people.txt
  A, 29
  B, 30
  C, 19
  D, 15
  F, 20

  val peopleRDD = sc.textFile("/user/people.txt")
  val peopleSchema = new StructType().add(StructField("name",   StringType, true)).add(StructField("age", IntegerType, true))
  val sepPeopleRdd = peopleRDD.map(line => line.split(","))
    .map(x => Row(x(0), x(1).trim.toInt))
  val peopleDF = spark.createDataFrame(sepPeopleRdd, peopleSchema)
  peopleDF.show()


  // -----------------------------
  // 데이터 프레임 초기화 - json 초기화
  // -----------------------------
  $ hadoop fs -ls /user/
    -rw-r--r--   2 hadoop hadoop         73 2019-01-04 05:32 /user/people.json

  $ hadoop fs -cat /user/people.json
  {"name":"Michael"}
  {"name":"Andy", "age":30}
  {"name":"Justin", "age":19}


  val df = spark.read.json("/user/people.json")
  scala> df.show()
  +----+-------+
  | age|   name|
  +----+-------+
  |null|Michael|
  |  30|   Andy|
  |  19| Justin|
  +----+-------+


  // -----------------------------
  // 데이터 프레임 초기화 - orc
  // -----------------------------
  val df = spark.read.orc("/user/orc/")
  scala> df.show(10)
  +----+-------+
  | age|   name|
  +----+-------+
  |null|Michael|
  |  30|   Andy|
  |  19| Justin|
  +----+-------+


  // -----------------------------
  // 스키마 확인
  // -----------------------------
  val df = spark.read.orc("/user/orc/")

  scala> df.printSchema()
  root
  |-- age: string (nullable = true)
  |-- name: string (nullable = true)


  // -----------------------------
  // 데이터 프레임 쿼리
  // -----------------------------

  // -----------------------------
  // 조회
  // -----------------------------
  val peopleRDD = sc.parallelize(
    Seq( ("David", 25),
      ("White", 18),
      ("Paul",  23) )
  )

  val peopleDF = peopleRDD.toDF("name", "age")

  peopleDF.select("name").show()
  peopleDF.select($"name", $"age" + 1).show()

  // -----------------------------
  // 필터링
  // -----------------------------
  peopleDF.filter($"age" > 21).show()
  peopleDF.select($"name", $"age").filter($"age" > 20).show()
  peopleDF.select($"name", $"age").filter("age > 20").show()

  // -----------------------------
  // 그룹핑
  // -----------------------------
  peopleDF.groupBy("age").count().show()



  // -----------------------------
  // 뷰생성
  // -----------------------------
  val peopleDF = spark.read.json("/user/people.json")

  scala> peopleDF.show()
  +----+-------+
  | age|   name|
  +----+-------+
  |null|Michael|
  |  30|   Andy|
  |  19| Justin|
  +----+-------+

  // DataFrame으로 뷰를 생성
  peopleDF.createOrReplaceTempView("people")

  // 스파크세션을 이용하여 SQL 쿼리 작성
  sql("SELECT * FROM people").show()
  spark.sql("SELECT * FROM people WHERE age > 20").show()
  spark.sql("SELECT age, count(1) FROM people GROUP BY age").show()
}
