# MyBatis增强工具

## feature

1. 使用模板技术生成固定格式的CRUD操作，无需每个xxxMapper.xml文件再写一遍相同的sql
2. 无入侵，只在启动阶段生成模板，不影响使用MyBatis
3. kotlin代码编写 



## 原理 

MyBatis启动完毕，启动增加工具，解析模板生成对应的CRUD操作SQL替换对应mapper接口对应的mapper xml文件中的内容，达到增强的目的



## TODO

1. 模板CRUD操作的空类型需要再次排查
2. 分页操作增强