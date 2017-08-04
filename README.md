# 喵乎
[![Build Status](https://www.travis-ci.org/Nbsaw/miaohu.svg?branch=master)](https://www.travis-ci.org/Nbsaw/miaohu)

## 使用之前

### 数据库设置
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

### idea spring项目支持
使用快捷键`Ctrl+Shift+Alt+S`点里面的`Facets`然后添加spring到项目里。就能得到支持。最明显的就是yml文件有了spring的图标，并且支持补全。
