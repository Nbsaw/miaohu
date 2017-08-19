## 初始化项目设置
下载项目后请打开`init-project`文件夹一步一步来完成初始化项目的流程。我承认这个流程有些繁琐。这一整个流程最主要的是在配置`idea`的`spring boot`项目支持。如果你觉得不需要的话当然也不是不行。不做的话无法完美的提供开发的支持。但不影响项目的运行。
全部流程都用截图保存下来了。虽然用截图感觉挺不好的。但现在阶段只能这样子。可能未来我会简化这个流程。

## 数据库设置
初始化项目以后。要先确保数据库的字符集为utf8。如果不是的话前往my.cnf里面修改。

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
## 数据库创建
检查完字符编码后。你需要创建miaohu使用的对应的数据库。

``` mysql
CREATE DATABASE miaohu;
```