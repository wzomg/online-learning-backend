/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28 : Database - online_learn
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`online_learn` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `online_learn`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级评论id',
  `uid` int(11) NOT NULL COMMENT '评论者id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `content` text NOT NULL COMMENT '评论内容',
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '评论层级（0，1，2）',
  `target_id` int(11) DEFAULT NULL COMMENT '目标人id',
  `gmt_create` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

/*Data for the table `comment` */

insert  into `comment`(`id`,`parent_id`,`uid`,`post_id`,`content`,`level`,`target_id`,`gmt_create`) values (1,0,2,5,'c产地四川省调查是',0,NULL,'2021-04-06 15:14:37'),(2,0,1,6,'你牛非法 成都v首付vDVD发',0,NULL,'2021-04-06 15:16:17'),(3,0,2,7,'你牛非法 产生的参数',0,NULL,'2021-04-06 15:40:56'),(4,1,1,5,'产生的测试测试的反对v',1,2,'2021-04-06 17:11:36'),(5,1,2,5,'v地方v地方v地方v的',2,1,'2021-04-06 17:24:38'),(6,0,1,10,'测试一级评论???',0,NULL,'2021-04-07 22:32:48'),(7,6,1,10,'测试level为1的评论',1,1,'2021-04-07 23:14:27'),(8,6,2,10,'测试level2的评论',2,1,'2021-04-07 23:17:15'),(9,1,2,5,'参数的长度是',2,1,'2021-04-07 23:21:32'),(10,1,2,5,'成都市成都市测试',2,1,'2021-04-07 23:21:41'),(11,0,2,14,'零零零零',0,NULL,'2021-04-08 10:24:53'),(12,6,2,10,'产生的参数的的城市',2,1,'2021-04-08 10:29:38'),(13,0,2,10,'测试我的?',0,NULL,'2021-04-08 10:29:50'),(14,13,2,10,'来了',1,2,'2021-04-08 10:30:24'),(15,6,1,10,'1111',1,1,'2021-04-08 14:36:48'),(16,0,1,10,'产生的参数的测试?',0,NULL,'2021-04-08 14:36:59'),(17,0,1,10,'cdscsd?',0,NULL,'2021-04-16 12:02:05');

/*Table structure for table `favorite` */

DROP TABLE IF EXISTS `favorite`;

CREATE TABLE `favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `subject_id` int(11) NOT NULL COMMENT '课程id',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '收藏时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `favorite` */

insert  into `favorite`(`id`,`uid`,`subject_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values (2,1,11,0,'2021-04-05 22:38:57',NULL),(3,1,15,0,'2021-04-06 08:51:09',NULL),(4,1,18,1,'2021-04-06 08:51:16',NULL),(5,1,13,0,'2021-04-06 09:36:41','2021-04-06 09:36:41'),(6,1,16,1,'2021-04-06 09:56:41','2021-04-06 09:56:41'),(7,1,12,0,'2021-04-06 09:56:56','2021-04-06 09:56:56'),(8,2,11,0,'2021-04-08 10:33:08','2021-04-08 10:33:08'),(9,3,11,1,'2021-04-16 20:29:19','2021-04-16 20:29:19');

/*Table structure for table `love` */

DROP TABLE IF EXISTS `love`;

CREATE TABLE `love` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '点赞id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `uid` int(11) NOT NULL COMMENT '点赞人id',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `love` */

insert  into `love`(`id`,`post_id`,`uid`,`is_deleted`,`gmt_create`,`gmt_modified`) values (1,6,1,0,'2021-04-05 14:02:55','2021-04-05 14:02:55'),(2,6,2,1,'2021-04-05 14:05:41','2021-04-05 14:05:41'),(3,7,1,0,'2021-04-05 15:24:19','2021-04-05 15:24:19'),(4,5,1,0,'2021-04-05 15:25:54','2021-04-05 15:25:54'),(5,10,1,0,'2021-04-07 22:52:21','2021-04-07 22:52:21'),(6,8,1,1,'2021-04-07 22:52:46','2021-04-07 22:52:46'),(7,14,2,0,'2021-04-08 10:24:58','2021-04-08 10:24:58'),(8,10,3,0,'2021-04-16 20:32:01','2021-04-16 20:32:01');

/*Table structure for table `note` */

DROP TABLE IF EXISTS `note`;

CREATE TABLE `note` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '笔记id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `filename` varchar(100) NOT NULL COMMENT '文件名',
  `download_url` varchar(100) NOT NULL COMMENT '下载链接',
  `gmt_create` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

/*Data for the table `note` */

insert  into `note`(`id`,`uid`,`username`,`filename`,`download_url`,`gmt_create`) values (7,1,'123456','e3b89637822602c27c69255db04ade90_r.jpg','http://localhost:8006/note/download/5e300a45b1004c1ab207d9f3593bf051.jpg','2021-04-08 01:22:18'),(8,1,'123456','33.png','http://localhost:8006/note/download/414b4a0ce7454adaad73593fa622002f.png','2021-04-08 01:24:54'),(9,1,'123456','erciyuanjingsetupian2.jpg','http://localhost:8006/note/download/55b0cafae5bd4d8895dcc779f5bfec1f.jpg','2021-04-08 01:27:34'),(10,1,'123456','20150728093415_cMdfK.thumb.700_0.jpeg','http://localhost:8006/note/download/f43f43a31eba4c13a87d90a183349818.jpeg','2021-04-08 01:28:07'),(12,1,'123456','erciyuanjingsetupian2.jpg','http://localhost:8006/note/download/0e404454679047c7931a8cf98ac4e879.jpg','2021-04-08 01:32:05'),(13,1,'123456','bg.png','http://localhost:8006/note/download/a34fcb968d52417a86c01b1796f1684f.png','2021-04-08 01:32:27'),(14,1,'123456','计算机图形学.jpg','http://localhost:8006/note/download/bb5f42f658a8424a8cc1357b9b456061.jpg','2021-04-08 01:33:52'),(15,1,'123456','多媒体技术.jpg','http://localhost:8006/note/download/c3084314f25643d082136422c10ed871.jpg','2021-04-08 01:39:53'),(16,1,'123456','语音识别.jpg','http://localhost:8006/note/download/e9996278cb7b4c2bbfbcc54266efce17.jpg','2021-04-08 01:39:58'),(18,2,'1234567','计算机视觉.txt','http://localhost:8006/note/download/a9671e6880644a5aad54ca60eeaa8fb3.txt','2021-04-08 10:31:46'),(19,1,'123456','机器学习.png','http://localhost:8006/note/download/4fb63e7cb5434f31a003842ae6c2d340.png','2021-04-08 14:34:43');

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `content` text NOT NULL COMMENT '帖子内容',
  `replynum` int(11) DEFAULT '0' COMMENT '评论数',
  `pictures` varchar(500) DEFAULT NULL COMMENT '上传的图片列表',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

/*Data for the table `post` */

insert  into `post`(`id`,`uid`,`content`,`replynum`,`pictures`,`gmt_create`,`gmt_modified`) values (5,1,'csdcsdsc???',5,'[\"http://localhost:8006/post/imgLoader/fe8f4346b275498ba5f8d36a5d9384ac.jpg\"]','2021-04-05 23:26:35','2021-04-05 23:26:35'),(6,2,'23412312',1,'[\"http://localhost:8006/post/imgLoader/c3922ededa4744a28307320e9ee903e6.png\"]','2021-04-06 01:04:28','2021-04-06 01:04:28'),(7,1,'?',1,'[\"http://localhost:8006/post/imgLoader/8f8d01e39ac348bcb769402bb9af8621.jpg\"]','2021-04-06 08:52:47','2021-04-06 08:52:47'),(8,1,'?测试擦产地四川省调查是的产生的参数的产地四川省测试的',0,'[\"http://localhost:8006/post/imgLoader/c96d9d6d59c54f398d223f96421cd293.jpg\"]','2021-04-06 19:43:46','2021-04-06 21:17:44'),(10,1,'????csdcsdcsdcsdc11',9,'[\"http://localhost:8006/post/imgLoader/a44fac249f724e74b0f7523616d0bbad.jpg\",\"http://localhost:8006/post/imgLoader/144de21d0c0c47b4b685ae9095ed3c9d.jpg\",\"http://localhost:8006/post/imgLoader/3bd0d741617e4b109020658c8bcc9615.jpg\"]','2021-04-07 19:55:07','2021-04-08 14:36:22'),(12,1,'测试测试速度测试?',0,'[]','2021-04-08 01:03:56','2021-04-08 01:03:56'),(13,1,'45675567',0,'[]','2021-04-08 01:06:11','2021-04-08 01:06:11'),(14,1,'村上春树的四渡赤水',1,'[]','2021-04-08 01:08:20','2021-04-08 01:08:20'),(15,1,'测试11111??',0,'[\"http://localhost:8006/post/imgLoader/433d1754604743f4a13cca668b975866.jpg\",\"http://localhost:8006/post/imgLoader/b4fe25d2fd32446f83acf6477acda933.jpg\"]','2021-04-08 14:35:43','2021-04-08 14:35:43'),(16,1,'机器学习',0,'[\"http://localhost:8006/post/imgLoader/035ca373b6804cd2aabebab635462e48.png\"]','2021-04-09 16:51:20','2021-04-09 16:51:20'),(17,3,'机器学习',0,'[]','2021-04-16 20:32:57','2021-04-16 20:32:57');

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父级id',
  `title` varchar(50) DEFAULT NULL COMMENT '课程标题',
  `description` text COMMENT '课程描述',
  `link` varchar(200) DEFAULT NULL COMMENT '学习链接',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

/*Data for the table `subject` */

insert  into `subject`(`id`,`parent_id`,`title`,`description`,`link`,`gmt_create`,`gmt_modified`) values (10,0,'机器学习',NULL,NULL,'2021-04-05 19:44:14','2021-04-05 19:44:14'),(11,10,'回归算法','机器学习(Machine Learning, ML)是一门多领域交叉学科，涉及概率论、统计学、逼近论、凸分析、算法复杂度理论等多门学科。专门研究计算机怎样模拟或实现人类的学习行为，以获取新的知识或技能，重新组织已有的知识结构使之不断改善自身的性能。\n它是人工智能的核心，是使计算机具有智能的根本途径，其应用遍及人工智能的各个领域，它主要使用归纳、综合而不是演绎。','https://www.cnblogs.com/acgoto/p/10080372.html','2021-04-05 19:44:14','2021-04-09 17:25:43'),(12,10,'深度学习算法','村上春树的的产生的参数的','https://www.cnblogs.com/acgoto/p/9313348.html','2021-04-05 19:44:14','2021-04-09 17:25:43'),(13,10,'正则算法','产生的参数大城市的村上春树','https://www.cnblogs.com/acgoto/p/9251668.html','2021-04-05 19:44:14','2021-04-09 17:25:43'),(14,0,'计算机视觉',NULL,NULL,'2021-04-05 19:44:14','2021-04-05 19:44:14'),(15,14,'图像分类','产地四川省调查是第三大城市的','https://www.cnblogs.com/acgoto/p/10854127.html','2021-04-05 19:44:14','2021-04-09 17:25:43'),(16,14,'对象检测','产生的参数豆腐干恢复恢复','https://www.cnblogs.com/acgoto/p/11570188.html','2021-04-05 19:44:14','2021-04-09 17:25:43'),(17,0,'数据库技术',NULL,NULL,'2021-04-05 19:44:14','2021-04-05 19:44:14'),(18,17,'mysql','csdsfefergdvbdfbdfbdfbfgbfbfbfgb','https://www.cnblogs.com/acgoto/p/9219034.html','2021-04-05 19:44:14','2021-04-09 17:25:43'),(19,0,'语音识别',NULL,NULL,'2021-04-09 16:54:13','2021-04-09 16:54:13'),(21,0,'自然语言处理',NULL,NULL,'2021-04-09 16:54:13','2021-04-09 16:54:13'),(23,0,'知识工程',NULL,NULL,'2021-04-09 16:54:13','2021-04-09 16:54:13'),(25,0,'计算机图形学',NULL,NULL,'2021-04-09 16:54:13','2021-04-09 16:54:13'),(27,0,'多媒体技术',NULL,NULL,'2021-04-09 16:54:14','2021-04-09 16:54:14'),(29,0,'人机交互技术',NULL,NULL,'2021-04-09 16:54:14','2021-04-09 16:54:14'),(31,0,'机器人',NULL,NULL,'2021-04-09 16:54:14','2021-04-09 16:54:14'),(34,0,'可视化技术',NULL,NULL,'2021-04-09 16:54:14','2021-04-09 16:54:14'),(36,0,'数据挖掘',NULL,NULL,'2021-04-09 16:54:14','2021-04-09 16:54:14');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(19) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `type` tinyint(4) DEFAULT '0' COMMENT '0-普通用户; 1-管理员',
  `salt` varchar(50) DEFAULT NULL COMMENT '加密盐值',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `gmt_create` datetime DEFAULT NULL COMMENT '注册时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`avatar`,`sex`,`type`,`salt`,`email`,`phone`,`gmt_create`,`gmt_modified`) values (1,'123456','30460012f367af71ea7accfc979580f8','http://localhost:8006/user/header/1030f04dd2b14906a63717732bf1b7c4.png',0,1,'4a5b1','zhangsan1@qq.com','13770417429','2021-04-02 22:43:39','2021-04-30 23:13:40'),(2,'1234567','d492a46166b126ea7cd1e932807e0423','http://images.nowcoder.com/head/890t.png',0,0,'a6713','2244045904@qq.com','123456','2021-04-04 16:09:48','2021-04-04 16:09:48'),(3,'520000','b709d6be8da9e2b2044cd8c5ae72cc4b','http://images.nowcoder.com/head/296t.png',1,0,'19b60','1213260565@qq.com','15733517798','2021-04-16 20:28:41','2021-04-16 20:28:41');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
