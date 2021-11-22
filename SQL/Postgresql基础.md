# 1. 运算符

## 1.1 算术运算符

```sql
SELECT 3+2 a, 3-2 b, 3*2 c, 3.0/2 d, 3/2 e, 3%2 f;

a	b	c					d						e	f
5	1	6	1.5000000000000000	1	1
```

## 1.2 比较运算符

|   运算符    | 作用                             |
| :---------: | -------------------------------- |
|      =      |                                  |
|   <>(!=)    |                                  |
|     <=      |                                  |
|     >=      |                                  |
|      >      |                                  |
|      <      |                                  |
|    LEAST    | 在有两个或多个参数时，返回最小值 |
|  GREATEST   | 在有两个或多个参数时，返回最大值 |
| BETWEEN AND | 判断一个值是否落在两个值之间     |
|     IN      |                                  |
|    LIKE     |                                  |

**示例1：**

```sql
SELECT 1=0 a, '2'=2 b, 'b'='b' c, NULL=NULL d, null=1 e;

a	b	c	d	e
f	t	t		
```

1. 如果有一个或两个参数为null，则返回结果为空；
2. ==如果字符串和数字比较，pg库会自动将字符串转为数字进行比较==。

**示例2：**

```sql
select 2 between 1 and 3 as A, 2 between 3 and 5 as B, 3 between 3 and 6 as C;

a	b	c
t	f	t
```

1. `between and`是==闭区间==。

**示例3：**

```sql
select 'abc' like 'a%' as c1, 'abc' like '_b_' as c2, 'abc' not like '%d' as c3;

c1	c2	c3
t		t		t
```

1. %：匹配任意多个或零个字符；
2. _：匹配任意单个字符

## 1.3 逻辑运算符

### not

```
select not '1' c1, not 'y' c2, not '0' c3, not 'n' c4;

c1	c2	c3	c4
f		f		t		t
```

1. '1' 和 'y' 被代表True；
2. '0' 和 'n' 被代表False

### and

```sql
select '0' and 'y' c1, 'y' and '1' c2, 'n' and '0' c3;

c1	c2	c3
f		t		f
```

### or

```sql
select '0' or 'y' c1, 'y' or '1' c2, 'n' or '0' c3;

c1	c2	c3
t		t		f
```

# 2. 常用函数

## 2.1 数值函数

| 函数名称 | 作用               |
| :------: | :----------------- |
|  AVG()   | 返回某列的平均值   |
| COUNT()  | ==返回某列的行数== |
|  MAX()   | 返回某列的最大值   |
|  MIN()   | 返回某列的最小值   |
|  SUM()   | 返回某列值之和     |

## 2.2 字符串函数

|         函数名称          | 作用               |
| :-----------------------: | ------------------ |
|         LENGTH(s)         | 计算字符串长度     |
|     CONCAT(s1,s2,...)     | 拼接字符串         |
| LTRIM(s)/RTRIM(s)/TRIM(s) | 删除字符串空格函数 |
|     REPLACE(s,s1,s2)      | 字符串替换函数     |
|    SUBSTRING(s,n,len)     | 获取子串           |

**示例：**

```sql
SELECT CONCAT(UUID, '_', nickname) c1 FROM public.ods_member LIMIT 5;

				c1
1603451758593247_德满
1601911376366671_郭
1601946619973779_追求完美^o^
1605077628234612_钟爱一生
1605079352918714_漂流瓶
```

## 2.3 时间日期函数

|       函数名称       | 作用               |
| :------------------: | ------------------ |
| EXTRACT(type FROM d) | 获取日期指定值函数 |
|     CURRENT_DATE     | 获取当前日期       |
|     CURRENT_TIME     | 获取当前时间       |
|        NOW()         | 获取当前日期时间   |

**示例1：**

```sql
select current_date cd, current_time ct, now() dt;

cd									ct										dt
2021-11-20	00:01:21.687047+08	2021-11-20 00:01:21.687047+08
```

**示例2：**

```sql
SELECT  '2001-02-16 20:38:40' cur_time
        ,EXTRACT(YEAR FROM TIMESTAMP '2001-02-16 20:38:40') y
        ,EXTRACT(MONTH FROM TIMESTAMP '2001-02-16 20:38:40') m
        ,EXTRACT(DAY FROM TIMESTAMP '2001-02-16 20:38:40') d;

cur_time							y			 m	 d
2001-02-16 20:38:40	2001.0	2.0	16.0
```

## 2.4 自定义函数

### 2.4.1 函数创建的语法

```sql
CREATE FUNCTION  										// 声明创建函数
	add(interger, integer)						// 定义函数名称, 参数类型
RETURNS integer											// 定义函数的返回值
	AS 'select $1 + $2;'							// 定义函数体
LANGUAGE SQL												// 用以实现函数的语言的名字
RETURNS NULL ON NULL INPUT;					// 定义参数为null时处理情况
```

**示例：**

```sql
-- 定义自定义函数
CREATE OR REPLACE FUNCTION my_add (INTEGER, INTEGER) RETURNS INTEGER 
    AS 'select $1 + $2;' 
LANGUAGE SQL 
IMMUTABLE 
RETURNS NULL ON NULL INPUT
;

-- 使用自定义函数
SELECT my_add (1, 2);
```

### 2.4.2 函数的删除

```sql
drop function my_add(integer, integer);
```

# 3. 数据库索引

## 3.1 索引的作用

提升检索效率。

## 3.2 索引的分类

| 索引名称 | 使用场景                       |
| :------: | ------------------------------ |
|  B-tree  | 适合处理那些能够按顺序存储数据 |
|   Hash   | 只能处理简单的等于比较         |
|   GiST   | 一种索引架构                   |
|   GIN    | 反转索引，处理包含多个值的键   |

## 3.3 索引的创建和删除

**创建索引：**

```SQL
-- 在表employee的e_name字段上创建索引(默认B-tree索引)
create index emp_name_idx on employee(e_name);
```

**删除索引：**

```sql
drop index emp_name_idx;
```

## 3.4 使用索引的优缺点

**优点:**

- 提高数据的查询速度
- 加速表与表之前的连接

**缺点：**

- 创建和维护索引需要消耗时间；
- 需要占用磁盘空间

# 4. 数据库视图

## 4.1 视图的含义

在PostgreSQL中，视图(VIEW)是一个伪表。 它不是物理表，而是作为普通表选择查询。视图也可以表示连接的表。 它可以包含表的所有行或来自一个或多个表的所选行。

**视图便于用户执行以下操作：**

- 它以自然和直观的方式构建数据，并使其易于查找。
- 它限制对数据的访问，使得用户只能看到有限的数据而不是完整的数据。
- 它归总来自各种表中的数据以生成报告。

## 4.2 视图的创建

```sql
-- 创建视图
CREATE VIEW v_ods_member as SELECT uuid, openid, time, nickname, type from public.ods_member WHERE nickname is NOT NULL LIMIT 5;

-- 使用视图
SELECT * FROM v_ods_member;
```

## 4.3 视图的删除

```sql
drop view v_ods_member;
```

## 4.4 视图的作用

- 简单化

- 安全性

  创建视图，屏蔽敏感数据，与其他公司交互。

- 逻辑数据独立性

  基于视图的查询，对视图中的查询语句的修改，可以不用修改基于视图的查询语句。

# 5. CRUD

## 5.1 简单插入

```sql
CREATE TABLE student (
    id INT,
	NAME VARCHAR(30),
	birthday DATE,
	score NUMERIC(5,2)
);

INSERT INTO student VALUES (1, '张三', '1990-01-01', NULL);

INSERT INTO student (id, birthday, NAME) VALUES (2, '1990-01-02', '李四');

SELECT * FROM student;

id	name	 birthday	    score
1,	张三,	 1990-01-01,	 NULL
2,	李四,	 1990-01-02,	 NULL
```

## 5.2 批量插入

### 5.2.1 使用insert

```sql
insert into student (id, name, birthday, score) values
(3, '王五', '1991-02-02', 89.8),
(4, '赵六', '1991-04-02', 90.8),
(5, '孙钱', '1991-02-02', 89.8);
```

### 5.2.2 使用select

```sql
CREATE TABLE student_new (
  id serial not null,
	NAME VARCHAR(30),
	birthday DATE,
	score NUMERIC(5,2),
  CONSTRAINT student_new_pkey PRIMARY KEY(id)
)
WITH(OIDS=FALSE);

-- 通过select向新表插入数据
insert into student_new (name, birthday, score) select name, birthday, score from student;
```

## 5.3 更新操作

```sql
select * from student_new;

id		name		birthday		 score
1,		张三,		1990-01-01,		NULL
2,		李四,		1990-01-02,		NULL
3,		王五,		1991-02-02,		89.8
4,		赵六,		1991-04-02,		90.8
5,		孙钱,		1991-02-02,		89.8

update student_new set name = '孔明', score = 99.90 where id = 5;
update student_new set score = '88.8' where score is null;
select * from student_new;

-- 更新后结果
id		name		birthday		 score
1,		张三,		1990-01-01,		88.8
2,		李四,		1990-01-02,		88.8
3,		王五,		1991-02-02,		89.8
4,		赵六,		1991-04-02,		90.8
5,		孔明,		1991-02-02,		99.9
```

**注意 ：**==判断条件是否为空，用 is null==

## 5.4 删除数据

```sql
select * from student;

id		name		birthday		 score
1,		张三,		1990-01-01,		NULL
2,		李四,		1990-01-02,		NULL
3,		赵六,		1991-04-02,		90.8
3,		孙钱,		1991-02-02,		89.8
3,		王五,		1991-02-01,		89.8

-- 根据条件删除数据
delete from student where birthday between '1990-01-02' and '1990-02-01';

select * from student;

id		name		birthday		 score
1,		张三,		1990-01-01,		NULL
3,		赵六,		1991-04-02,		90.8
3,		孙钱,		1991-02-02,		89.8
3,		王五,		1991-02-01,		89.8

-- 删除整张表
delete from student;  -- 务必小心
```

```sql
select * from student_new order by id;
id		name		birthday		 score
1,		张三,		1990-01-01,		88.8
2,		李四,		1990-01-02,		88.8
3,		王五,		1991-02-02,		89.8
4,		赵六,		1991-04-02,		90.8
5,		孔明,		1991-02-02,		99.9

-- 清空一张表
truncate table student_new;
select * from student_new;

id		name		birthday		 score
```

|              | DELETE | TRUNCATE |
| :----------: | :----: | :------: |
|   执行速度   |   慢   |    快    |
|  可指定条件  |  可以  |  不可以  |
|   语句分类   |  DML   |   DDL    |
| 可以回滚事务 |  可以  |  不可以  |
|   记录日志   |  记录  |  不记录  |

## 5.5 主键和外键

创建主键的两种方式：

```sql
CREATE TABLE emp(
	id INT PRIMARY KEY,  -- 列级约束，不能给主键取名
	name VARCHAR(30),
	salary NUMERIC(9,2)
);

CREATE TABLE emp_new(
	id INT,
	name VARCHAR(30),
	salary NUMERIC(9,2),
	CONSTRAINT pk_emp_new PRIMARY KEY(id)  -- 表级约束，可以给主键取名
);
```

创建外键及注意事项

```sql
--删除所有表
drop owned by qzlydao;

-- 创建部门表，并加上主键约束
create table dept(
	id  int primary  key,
	name varchar(30)
);

insert into dept values(1,'研发部');
insert into dept values(2,'测试部');

select * from dept;
id   name
1,	研发部
2,	测试部

-- 创建emp表, 并设置外键
CREATE TABLE emp(
	id serial PRIMARY KEY,
	NAME VARCHAR(30),
	salary NUMERIC(9,2),
	deptId INT,
	CONSTRAINT fk_emp_dept FOREIGN KEY(deptId) REFERENCES dept(id)
);

insert into emp(name, salary, deptId) values ('张三', 3000,1);
insert into emp(name, salary, deptId) values ('李四', 4000,2);
-- insert into emp(name, salary, deptId) values ('王五', 4400,3);  报错，因为deptId=3在dept表中不存在
select * from emp;
id,  name, salary, deptId
1,	 张三,	3000,			1
2,	 李四,	4000,			2

-- delete from dept where id = 1; 报错，因为在emp中有关联数据
--ERROR:  update or delete on table "dept" violates foreign key constraint "fk_emp_dept" on table "emp"
--DETAIL:  Key (id)=(1) is still referenced from table "emp".
```

外键约束作用：

- 保证数据的完整性；
- 提高数据的检索效率

## 5.6 非空约束、唯一约束、默认值约束

非空约束`not null`

```sql
CREATE TABLE student(
	id INT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	score NUMERIC(4,2)
);
```

唯一约束`unique`

```sql
CREATE TABLE student2(
	id INT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	phone VARCHAR(30) UNIQUE, --唯一值约束
	score NUMERIC(4,2)
);

insert into student2 values 
(1, '张三', '13344445555', 89.9),
(2, '李四', null, 89.9),
(3, '王五', null, 89.9);
--(4, '赵六', '13344445555', 89.9); -- 违反唯一约束

select * from student2;
id, name, 		phone, 		score
1,	张三,		13344445555,	89.9
2,	李四,		NULL,					89.9
3,	王五,		NULL,					89.9
```

- 唯一约束，对于null是可以重复的

默认值约束

```sql
CREATE TABLE student3(
	id INT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	phone VARCHAR(30) UNIQUE,
	score NUMERIC(4,2) DEFAULT 0.0 -- 默认值约束
);
INSERT INTO student3 (id, name , phone) VALUES (1, '张三', '13344445555');
SELECT * FROM student3;

id, name, 		phone, 		score
1,	张三,		13344445555,	0
```

# 6. 复杂查询

数据准备

```sql
-- 清空所有表
DROP owned BY qzlydao;

-- 创建部门表
CREATE TABLE dept( 
	d_no INT PRIMARY KEY,
	d_name VARCHAR(30),
	d_location VARCHAR(300)
);

-- dept表初始化数据
INSERT INTO dept VALUES (10,'开发部','北京市海淀区');
INSERT INTO dept VALUES (20,'测试部','北京市东城区');
INSERT INTO dept VALUES (30,'销售部','上海市');
INSERT INTO dept VALUES (40,'财务部','广州市');
INSERT INTO dept VALUES (50,'运维部','武汉市');
INSERT INTO dept VALUES (60,'集成部','南京市');

-- 创建employee表
CREATE TABLE employee ( 
	e_no INT PRIMARY KEY,
	e_name VARCHAR(30) NOT NULL, 
	e_gender CHAR(2) NOT NULL,
	dept_no INT,
	e_job VARCHAR(50) NOT NULL,
	e_salary NUMERIC(9, 2),
	e_hireDate DATE,
CONSTRAINT fk_emp_deptno FOREIGN KEY (dept_no) REFERENCES dept(d_no)
);

-- 初始化employee表
INSERT INTO employee VALUES (100, '赵志军', 'f',  10,  '开发工程师', 5000, '2010-01-01');
INSERT INTO employee VALUES (101, '张铭雨', 'f',  10, '开发工程师',  6000, '2012-04-04');
INSERT INTO employee VALUES (102, '许锋',   'f',  10,  '开发经理',  8000, '2008-01-01');
INSERT INTO employee VALUES (103, '王嘉琦', 'm',  20,  '测试工程师', 4500, '2013-08-12');
INSERT INTO employee VALUES (104, '李江新', 'f',  20, '测试工程师',  5000, '2011-08-16');
INSERT INTO employee VALUES (105, '马海英', 'm',  20,  '测试经理',   6000, '2009-11-12');
INSERT INTO employee VALUES (106, '马恩波', 'f',  30,  '销售人员',   3000, '2014-09-01');
INSERT INTO employee VALUES (107, '李慧敏', 'm',  30, '销售人员',     5000,'2010-01-11');
INSERT INTO employee VALUES (108, '马双双', 'm',  30, '销售经理',    9000, '2006-12-02');
INSERT INTO employee VALUES (109, '史晓云', 'm',  30,  '销售高级经理',12000,'2003-07-14');
INSERT INTO employee VALUES (110, '刘艳芬', 'm',  40, '财务人员',    3000, '2011-06-01');
INSERT INTO employee VALUES (111, '王科',   'f',  40,  '财务人员',   3500, '2010-05-01');
INSERT INTO employee VALUES (112, '李林英', 'm',  40,  '财务经理',   5000, '2008-05-07');
INSERT INTO employee VALUES (113, '李杨',  'f',   10, '实习工程师',  NULL, '2015-05-07');
INSERT INTO employee VALUES (114, '李刚',  'f',  NULL, '实习工程师', NULL, '2015-05-07'); 
INSERT INTO employee VALUES (115, '王林',  'f',  NULL,'实习工程师',  NULL, '2015-05-07'); 
```

## 6.1 简单数据查询

```sql
-- 以下四种查询方式等价
select e_no, e_name, e_salary from employee;

select employee.e_no, employee.e_name, employee.e_salary from employee;

-- 给表取别名
select e.e_no, e.e_name, e.e_salary from employee e;

-- 给字段取别名
select e.e_no as a, e.e_name as b, e.e_salary as c from employee e;

-- 给字段取别名，省略as
select e.e_no a, e.e_name b, e.e_salary c from employee e;
```

## 6.2 单表指定条件查询

```sql
select e_no, e_name, e_salary from employee where e_salary < 5000;

e_no, e_name, e_salary
103,	王嘉琦,	4500
106,	马恩波,	3000
110,	刘艳芬,	3000
111,	王科,		 3500

-- 根据in条件查询
select e_no, e_name, dept_no from employee where dept_no in (20, 30);
select e_no, e_name, dept_no from employee where dept_no not in (20, 30);

-- 根据between and 条件检索
select e_no, e_name, e_hiredate from employee where e_hiredate between '2010-01-01' and '2012-12-30';

-- like 模糊查询
select e_no, e_name, dept_no from employee where e_name like '李%';

e_no, e_name, dept_no
104,	李江新,		20
107,	李慧敏,		30
112,	李林英,		40
113,	李杨,			 10
114,	李刚,			NULL
```

## 6.3 单表指定条件复杂查询

```sql
-- null值查询
select e_no, e_name, e_salary from employee where e_salary is null;
-- select e_no, e_name, e_salary from employee where e_salary = null;  错误的查询

-- and多条件查询
select e_no, e_name, e_gender, dept_no from employee where e_gender = 'f' and dept_no in (20, 30);

-- 查询结果排序
select e_no, e_name, dept_no, e_salary from employee order by e_salary;
select e_no, e_name, dept_no, e_salary from employee order by e_salary desc; -- 降序排序

-- 多个条件排序
select e_no, e_name, dept_no, e_salary, e_hiredate from employee order by e_salary asc, e_hiredate desc;

-- 排序对null的处理
select e_no, e_name, dept_no, e_salary from employee order by e_salary nulls last; -- 默认nulls first

-- limit和offset对查询数据进行限制
select * from employee limit 5;
select * from employee limit 5 offset 4;

e_no, e_name, e_gender, dept_no, 		e_job, 		e_salary, e_hiredate
104,	李江新,		 f ,			20,			测试工程师,		5000,		2011-08-16
105,	马海英,		 m ,			20,			测试经理,			 6000,	 2009-11-12
106,	马恩波,		 f ,			30,			销售人员,			 3000,	 2014-09-01
107,	李慧敏,		 m ,			30,			销售人员,			 5000,	 2010-01-11
108,	马双双,		 m ,			30,			销售经理,			 9000,	 2006-12-02
```

## 6.4 多表连接查询

```sql
-- 内连接查询
-- 隐式内连接
select e_no, e_name, dept_no, d_no, d_name, d_location from employee, dept where dept_no = d_no;
-- 显式内连接
select e_no, e_name, dept_no, d_no, d_name, d_location from employee inner join dept on dept_no = d_no;

e_no	e_name	dept_no	d_no	d_name	d_location
100		赵志军			10		 10		开发部		北京市海淀区
101		张铭雨			10		 10		开发部		北京市海淀区
102		许锋			 10		  10	 开发部	 北京市海淀区
103		王嘉琦			20		 20		测试部		北京市东城区
104		李江新			20		 20		测试部		北京市东城区
105		马海英			20		 20		测试部		北京市东城区
106		马恩波			30		 30		销售部		上海市
107		李慧敏			30		 30		销售部		上海市
108		马双双			30		 30		销售部		上海市
109		史晓云			30		 30		销售部		上海市
110		刘艳芬			40		 40		财务部		广州市
111		王科	 		 40		  40	 财务部	 广州市
112		李林英	 		40		 40	  财务部		广州市
113		李杨	 		 10		  10	 开发部	北京市海淀区
```



```sql
-- left join/left outer join等价
select e_no, e_name, dept_no, d_no, d_name, d_location from employee left join dept on dept_no = d_no;
select e_no, e_name, dept_no, d_no, d_name, d_location from employee left outer join dept on dept_no = d_no;

-- 添加条件
select 
	e_no, e_name, dept_no, d_no, d_name, d_location 
from employee left outer join dept on dept_no = d_no 
where dept_no = '20';
```

## 6.5 子查询

### 6.5.1 exists子查询

```sql
select * from employee where exists
(select d_no from dept where d_name='开发部' and d_no = dept_no);

e_no	e_name	e_gender	dept_no		e_job		e_salary	e_hiredate
100		赵志军			f 				10		开发工程师		5000		 2010-01-01
101		张铭雨			f 				10		开发工程师		6000		 2012-04-04
102		许锋			 f 				 10		 开发经理		 8000			2008-01-01
113		李杨			 f 				 10		 实习工程师	 					2015-05-07
```

`exists`做为`where`条件时，是先对`where`前的主查询询进行查询，然后用主查询的结果一个一个的代入`exists`的查询进行判断，如果为真则输出当前这一条主查询的结果，否则不输出。 

查询时，一般情况下，子查询会分成两种情况：     

- 子查询与外表的字段有关系时
  `select 字段1 , 字段2 from 表1 where exists (select 字段1 , 字段2 from 表2 where 表2.字段2 = 表1.字段2)`
  这时候，此SQL语句相当于一个关联查询。它先执行表1的查询，然后把表1中的每一条记录放到表2的条件中去查询，如果存在，则显示此条记录。

- 子查询与外表的字段没有任何关联
  `Select 字段1 , 字段2 from 表1 where exists ( select * from 表2 where 表2.字段 = ‘ 条件‘)`
  当子查询与主表不存在关联关系时，简单认为只要exists为一个条件判断，如果为true,就输出所有记录。如果为false则不输出任何的记录。

### 6.5.2 in子查询

```sql
select * from employee where dept_no in (select d_no from dept where d_name='开发部');
```

### 6.5.3 标量子查询

```sql
select e_no, e_name, (select d_name || '_' || d_location from dept where dept_no = d_no) as address 
from employee where dept_no = 20;

e_no, 	e_name, 		 	address
103,	  王嘉琦,		测试部_北京市东城区
104,	  李江新,		测试部_北京市东城区
105,	  马海英,		测试部_北京市东城区
```

## 6.6 查询结果集合并

```sql
-- union all 不会去重
select e_no, e_name, dept_no, e_salary from employee where dept_no in (20, 30)
union all 
select e_no, e_name, dept_no, e_salary from employee where e_salary > 6000 and e_salary is not null;

-- 会出现重复
e_no, 	e_name, 	dept_no, e_salary
103,	  王嘉琦,			20,			4500
104,	  李江新,			20,			5000
105,	  马海英,			20,			6000
106,	  马恩波,			30,			3000
107,	  李慧敏,			30,			5000
108,	  马双双,			30,			9000
109,	  史晓云,			30,			12000
102,	  许锋, 			 10,		 8000
108,	  马双双,			30,			9000
109,	  史晓云,			30,			12000

-- union 会去重
select e_no, e_name, dept_no, e_salary from employee where dept_no in (20, 30)
union 
select e_no, e_name, dept_no, e_salary from employee where e_salary > 6000 and e_salary is not null;

-- 不会出现重复
e_no, 	e_name, 	dept_no, e_salary
104,		李江新,			20,			5000
102,		许锋,   		 10,		 8000
106,		马恩波,			30,			3000
108,		马双双,			30,			9000
109,		史晓云,			30,			12000
107,		李慧敏,			30,			5000
105,		马海英,			20,			6000
103,		王嘉琦,			20,			4500
```

