-- 가상 칼럼 
select INPUT__FILE__NAME, key, BLOCK__OFFSET__INSIDE__FILE 
  from src;

select key, count(INPUT__FILE__NAME) 
  from src 
 group by key 
 order by key;

select * 
  from src 
 where BLOCK__OFFSET__INSIDE__FILE > 12000 
 order by key;