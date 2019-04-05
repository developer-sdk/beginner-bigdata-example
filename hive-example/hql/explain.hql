# 실행 계획 확인 
explain INSERT OVERWRITE DIRECTORY 'hdfs:///user/data/location'
select name, count(1)
  from sample_table
  where yymmdd=20180201
 group by name

Plan optimized by CBO.    // CBO 적용