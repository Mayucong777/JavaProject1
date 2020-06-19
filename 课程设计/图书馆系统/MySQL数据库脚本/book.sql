/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-06-19 16:58:47
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
