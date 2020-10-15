/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : exercise

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-10-15 16:56:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_downloadlist
-- ----------------------------
DROP TABLE IF EXISTS `t_downloadlist`;
CREATE TABLE `t_downloadlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL COMMENT '相对路径',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `size` longtext COMMENT '文件大小',
  `star` int(11) DEFAULT NULL COMMENT '星级',
  `image` varchar(255) DEFAULT NULL COMMENT '图片相对地址',
  `time` date DEFAULT NULL COMMENT '资源上传的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_downloadlist
-- ----------------------------
INSERT INTO `t_downloadlist` VALUES ('1', '1', 'WEB-INF/files/1.doc', '空文档', '9KB', '2', '../fileIcon/doc.png', '2020-09-28');
INSERT INTO `t_downloadlist` VALUES ('2', '2', 'WEB-INF/files/2.ppt', '空的演示文件', '9KB', '3', '../fileIcon/ppt.png', '2020-09-28');
INSERT INTO `t_downloadlist` VALUES ('3', 'Java教案', 'WEB-INF/files/Java教案.docx', '聂刚老师的倾心力作，详细生动又实用的展示了java语言的知识，以及最贴近未来职场需求的框架结构.', '14129KB', '5', '../fileIcon/doc.png', '2020-05-23');

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `resourceId` int(11) NOT NULL,
  `resourcename` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('1', 'main', '/main.jsp');
INSERT INTO `t_resource` VALUES ('2', 'download', '/download.jsp');
INSERT INTO `t_resource` VALUES ('3', 'personalCenter', '/personalCenter.jsp');
INSERT INTO `t_resource` VALUES ('4', 'resourceManage', '/resourceManage.jsp');
INSERT INTO `t_resource` VALUES ('5', 'userManage', '/userManage.jsp');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `roleId` int(11) NOT NULL,
  `rolename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员');
INSERT INTO `t_role` VALUES ('2', '普通用户');
INSERT INTO `t_role` VALUES ('3', '测试员');

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `id` int(11) NOT NULL,
  `resourceId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `resourceId` (`resourceId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `resourceId` FOREIGN KEY (`resourceId`) REFERENCES `t_resource` (`resourceId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('1', '1', '1');
INSERT INTO `t_role_resource` VALUES ('2', '2', '1');
INSERT INTO `t_role_resource` VALUES ('3', '3', '1');
INSERT INTO `t_role_resource` VALUES ('4', '4', '1');
INSERT INTO `t_role_resource` VALUES ('5', '5', '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `chrname` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('admin', '12345', '马大神', '管理员');
INSERT INTO `t_user` VALUES ('mmm', '333', '马毛毛', '用户');
INSERT INTO `t_user` VALUES ('mxt', '666666', '马小跳', '测试员');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL,
  `roleId` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `roleId` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', 'admin');
INSERT INTO `t_user_role` VALUES ('2', '2', 'mmm');
INSERT INTO `t_user_role` VALUES ('3', '3', 'mxt');
