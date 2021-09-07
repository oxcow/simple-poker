
create table if not exists pokers (
  id int not null primary key AUTO_INCREMENT comment '主键',
  name varchar(255) not null comment 'poker名字',
  role enum('MASTER','POKER', 'GUEST')  not null comment 'poker角色',
  create_time TIMESTAMP not null default CURRENT_TIMESTAMP comment '创建时间'
);

create table if not exists rooms (
  id int not null primary key AUTO_INCREMENT comment '主键',
  name varchar(255) not null comment '房间名称',
  type enum('TEST','PRIVATE','PUBLIC') not null comment '房间类型',
  owner int not null comment '房间所有人。对应poker表',
  create_time TIMESTAMP not null default CURRENT_TIMESTAMP comment '创建时间'
);

-- http://h2database.com/html/tutorial.html?highlight=csv&search=csv#csv
CREATE TABLE csv_table(ID INT PRIMARY KEY, NAME VARCHAR(255))
    AS SELECT * FROM CSVREAD('classpath:database/csv_table.csv');