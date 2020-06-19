/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-06-19 17:18:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bookID` varchar(20) NOT NULL COMMENT '图书编号',
  `bookname` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '书名',
  `supply` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '出版社',
  `author` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '作者',
  `booktype` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '图书所属类型',
  `inventory` int(255) NOT NULL COMMENT '剩余库存',
  PRIMARY KEY (`bookID`),
  KEY `bookname` (`bookname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('100001', '水浒赚', '华科出版社', '马大神', '经典名著', '2');
INSERT INTO `book` VALUES ('100002', '嘻游记', '武大出版社', '武城恩', '休闲娱乐', '1');
INSERT INTO `book` VALUES ('100003', '三锅演绎', '清华出版社', '罗罐中', '科幻悬疑', '2');
INSERT INTO `book` VALUES ('100004', '洪楼梦', '北大出版社', '曹雪琴', '浪漫爱情', '3');
INSERT INTO `book` VALUES ('100008', '斗破苍穹', '贵州出版社', '天蚕土豆', '古装剧情', '4');
INSERT INTO `book` VALUES ('100009', '斗罗大陆', '西北出版社', '唐家三少', '浪漫言情', '2');
INSERT INTO `book` VALUES ('100012', '三体', '人民出版社', '刘慈欣', '科幻悬疑', '5');

-- ----------------------------
-- Table structure for borrowdetail
-- ----------------------------
DROP TABLE IF EXISTS `borrowdetail`;
CREATE TABLE `borrowdetail` (
  `jyh` varchar(20) NOT NULL COMMENT '借阅号',
  `username` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '借阅人',
  `userID` varchar(20) NOT NULL COMMENT '学号',
  `bookname` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '书名',
  `bookID` varchar(20) NOT NULL COMMENT '书号',
  `time` datetime DEFAULT NULL COMMENT '借书时间',
  `duration` int(255) NOT NULL COMMENT '借阅时间',
  `state` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '借阅状态',
  PRIMARY KEY (`jyh`),
  KEY `duration` (`duration`),
  KEY `userID` (`userID`),
  KEY `bookID` (`bookID`),
  KEY `bookname` (`bookname`),
  KEY `username` (`username`),
  CONSTRAINT `bookID` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `bookname` FOREIGN KEY (`bookname`) REFERENCES `book` (`bookname`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of borrowdetail
-- ----------------------------
INSERT INTO `borrowdetail` VALUES ('20200619B0001', '马宇聪', '1804240713', '水浒赚', '100001', '2020-06-19 16:29:37', '2', '借阅');
INSERT INTO `borrowdetail` VALUES ('20200619R0002', '马宇聪', '1804240713', '水浒赚', '100001', '2020-06-19 16:35:52', '2', '归还');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` varchar(20) NOT NULL COMMENT '用户账号',
  `password` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '密码',
  `username` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '真实姓名',
  `academy` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '所属学院',
  `role` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '用户身份',
  `id` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '学号/工号',
  PRIMARY KEY (`userID`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('16042401', 'Ma123', '马大神', '教职工', '教师', '16042401');
INSERT INTO `user` VALUES ('1804240713', 'Mayucong123', '马宇聪', '数计学院', '管理员', '1804240713');
INSERT INTO `user` VALUES ('1934616839', 'Liu123456789', '刘长春', '经济学院', '学生', '1904240901');
