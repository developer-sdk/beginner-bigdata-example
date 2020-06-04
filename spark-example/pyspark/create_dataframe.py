from pyspark.sql import Row

datas1 = [("foo", 1), ("bar", 2)]
datas2 = [ Row(name='Alice', age=5, height=80),
           Row(name='Alice', age=5, height=80),
           Row(name='Alice', age=10, height=80)]

# Spark Context를 이용하는 방법 
sc.parallelize(datas1).toDF().show()
sc.parallelize(datas2).toDF().show()

# SparkSession을 이용하는 방법 
spark.createDataFrame(datas1).show()
spark.createDataFrame(datas2).show()
