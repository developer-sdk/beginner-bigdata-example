from pyspark.sql.functions import udf
from pyspark.sql.types import StringType

# UDF 선언 
@udf(returnType=StringType())
def get_first(param):
    if param is None:
        return "N"
    return param[0]

from udfs import get_first
from pyspark.sql.functions import col

# UDF를 이용한 조회 
df = spark.createDataFrame(datas2)
df.select(get_first("name")).show()
'''
+---------------+                                                               
|get_first(name)|
+---------------+
|              A|
|              A|
|              A|
+---------------+
'''

# UDF를 이용한 조회. 칼럼명 변경 
df.withColumn("name_first", get_first(col("name"))).show()
'''
+---+------+-----+----------+                                                   
|age|height| name|name_first|
+---+------+-----+----------+
|  5|    80|Alice|         A|
|  5|    80|Alice|         A|
| 10|    80|Alice|         A|
+---+------+-----+----------+
'''
