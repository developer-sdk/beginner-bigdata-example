from pyspark.sql.types import StructType, StructField, StringType
schema = StructType([
    StructField("age", StringType(), True),
    StructField("height", StringType(), True),
    StructField("name", StringType(), True)])

spark.read.csv("/user/shs/sample.csv").show()
spark.read.csv("/user/shs/sample.csv", header=False, schema=schema).show()
