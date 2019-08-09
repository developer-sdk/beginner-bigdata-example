val df = spark.read.json("/user/people.json")
scala> df.show()
+----+-------+
| age|   name|
+----+-------+
|null|Michael|
|  30|   Andy|
|  19| Justin|
+----+-------+

// 새로운 칼럼 추가 
scala> df.withColumn("xx", $"name").show()
+----+-------+-------+
| age|   name|     xx|
+----+-------+-------+
|null|Michael|Michael|
|  30|   Andy|   Andy|
|  19| Justin| Justin|
+----+-------+-------+

// 칼럼의 정보를 변경
// when() 함수를 이용하여 조건 설정, otherwise()를 이용하여 참이 아닐때 설정 
scala> df.withColumn("xx", when($"age".isNull, "KKK").otherwise($"name")).show()
+----+-------+------+
| age|   name|    xx|
+----+-------+------+
|null|Michael|   KKK|
|  30|   Andy|  Andy|
|  19| Justin|Justin|
+----+-------+------+

// 여러개의 조건을 이용할 때 처리 
scala> df.withColumn("xx", when($"age".isNull and $"name" === "Michael", "KKK").otherwise($"name")).show()
+----+-------+------+
| age|   name|    xx|
+----+-------+------+
|null|Michael|   KKK|
|  30|   Andy|  Andy|
|  19| Justin|Justin|
+----+-------+------+

// udf를 이용한 처리 
import org.apache.spark.sql.functions.udf
val func = udf((s:String) => if(s.isEmpty) "KKK" else s)

scala> df.select($"age", $"name", func($"name").as("xx") ).show()
+----+-------+-------+
| age|   name|     xx|
+----+-------+-------+
|null|Michael|Michael|
|  30|   Andy|   Andy|
|  19| Justin| Justin|
+----+-------+-------+
