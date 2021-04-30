# online-learning-backend

> 在线学习网站后端

## Backend Stack

>  Spring Boot、Mybatis-Plus、Redis、MySQL、Swagger、EasyExcel

## Description

- 该系统是基于前后端分离，采用`Spring Boot + Vue`开发的在线学习网站。

- 已实现的功能：
  - 普通用户：
    - 上传和删除自己发布的学习笔记，可以下载其他人包括自己上传的学习笔记；
    - 搜索并收藏某个课程，添加、删除和搜索自己收藏的课程；
    - 发布、删除和修改自己发布的帖子，搜索所有帖子；
    - 对所有帖子都可以进行点赞、取消点赞、评论和回复；
    - 讨论区支持上传和预览图片，添加`emoji`表情包；
    - 修改个人信息包括更换头像等，修改登录密码；
  - 管理员：
    - 下载`excel`课程模板，上传`excel`文件添加和更新课程信息；
    - 包含普通用户所拥有的功能；
- 待完善的功能：
  - 管理员：
    - 删除课程信息；
    - 增加用户表字段，对用户进行管理，冻结和解除用户账号；
  - 普通用户：
    - 对发布的内容进行敏感词过滤；
    - 删除自己发表的评论或回复；
- 遇到的问题及解决方案：
  - 由于评论，点赞，帖子查询结果实体互相嵌套，导致`fastJson`将结果实体转成`Json`时使用了循环引用，为了不影响转换效率，使用局部关闭循环引用的方式，即在字段上添加注解：`@JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})`
- 打包时记得将`OnlinelearningApplicationTests.java`文件中的内容注释掉。