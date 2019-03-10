-- source의 내용을 target 테이블에 입력 
INSERT INTO TABLE target 
SELECT *
  FROM source;

-- OVERWRITE가 붙으면 해당 위치의 내용을 삭제하고 덮어 쓴다. 
INSERT OVERWRITE TABLE target PARTITION(col1 = 'a', col2)
SELECT data1,
       date2
  FROM source;
  
-- FROM INSERT 문 
FROM (
  SELECT *
    FROM source1
  UNION
  SELECT *
    FROM source2
) R
INSERT INTO TABLE target1
SELECT R.name,
       R.age

INSERT OVERWRITE TABLE target2 PARTITION(col1 = 'a', col2)
SELECT R.name,
       R.age;
       
-- INSERT, UPDATE, DELETE, MERGE 문 예제 
CREATE TABLE students (name VARCHAR(64), age INT, gpa DECIMAL(3, 2))
  CLUSTERED BY (age) INTO 2 BUCKETS STORED AS ORC;

-- INSERT 
INSERT INTO TABLE students
  VALUES ('fred flintstone', 35, 1.28), ('barney rubble', 32, 2.32);

-- UPDATE
UPDATE students SET age = 10 WHERE name = 'fred flintstone';

-- DELETE
DELETE FROM students WHERE name = 'fred flintstone';

-- MERGE
MERGE INTO <target table> AS T USING <source expression/table> AS S
ON <boolean expression1>
WHEN MATCHED [AND <boolean expression2>] THEN UPDATE SET <set clause list>
WHEN MATCHED [AND <boolean expression3>] THEN DELETE
WHEN NOT MATCHED [AND <boolean expression4>] THEN INSERT VALUES<value list>

-- INSERT Directory 예제 
INSERT OVERWRITE DIRECTORY 'hdfs://1.0.0.1:8020/user/'
SELECT *
  FROM source
;

INSERT OVERWRITE DIRECTORY 'hdfs://1.0.0.1:8020/user/'
   ROW FORMAT DELIMITED
   FIELDS TERMINATED BY '\t'
SELECT *
  FROM source
;