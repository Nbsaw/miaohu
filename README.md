# 喵乎
[![Build Status](https://www.travis-ci.org/Nbsaw/miaohu.svg?branch=master)](https://www.travis-ci.org/Nbsaw/miaohu)

## 使用之前

先确保数据库的字符集为utf8。如果不是的话前往my.cnf里面修改。

``` mysql
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8


[mysqld]
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
character-set-server = utf8
```

然后创建对应的数据库。

``` mysql
CREATE DATABASE miaohu;
```

之后再启动项目。
