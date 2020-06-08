from pyspark.sql import SparkSession

# SparkSession을 이용한 Hive 연동 
spark = SparkSession.builder.appName("sample").config("hive.metastore.uris", "thrift://ip:port").enableHiveSupport().getOrCreate()
spark.sql("use db_name")
df = spark.sql("SELECT * FROM table")
df.show()
