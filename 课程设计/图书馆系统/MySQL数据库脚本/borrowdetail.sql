/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-06-19 16:58:57
*/

SET FOREIGN_KEY_CHECKS=0;

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
