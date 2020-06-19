/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-06-19 16:59:06
*/

SET FOREIGN_KEY_CHECKS=0;

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
