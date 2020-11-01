/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : exercise

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-11-01 20:37:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_city
-- ----------------------------
DROP TABLE IF EXISTS `t_city`;
CREATE TABLE `t_city` (
  `cityCode` varchar(255) NOT NULL,
  `cityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cityCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_city
-- ----------------------------
INSERT INTO `t_city` VALUES ('0101', '武汉');
INSERT INTO `t_city` VALUES ('0102', '宜昌');
INSERT INTO `t_city` VALUES ('0103', '荆州');
INSERT INTO `t_city` VALUES ('0104', '黄冈');
INSERT INTO `t_city` VALUES ('0105', '孝感');
INSERT INTO `t_city` VALUES ('0201', '朝阳');
INSERT INTO `t_city` VALUES ('0301', '温州');
INSERT INTO `t_city` VALUES ('0302', '杭州');
INSERT INTO `t_city` VALUES ('0401', '南京');
INSERT INTO `t_city` VALUES ('0402', '盐城');

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
-- Table structure for t_province
-- ----------------------------
DROP TABLE IF EXISTS `t_province`;
CREATE TABLE `t_province` (
  `provinceCode` varchar(255) NOT NULL,
  `provinceName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`provinceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_province
-- ----------------------------
INSERT INTO `t_province` VALUES ('01', '湖北');
INSERT INTO `t_province` VALUES ('02', '北京');
INSERT INTO `t_province` VALUES ('03', '浙江');
INSERT INTO `t_province` VALUES ('04', '江苏');

-- ----------------------------
-- Table structure for t_p_c
-- ----------------------------
DROP TABLE IF EXISTS `t_p_c`;
CREATE TABLE `t_p_c` (
  `id` varchar(255) NOT NULL,
  `provinceCode` varchar(255) NOT NULL,
  `cityCode` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cityCode` (`cityCode`),
  KEY `provinceCode` (`provinceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_p_c
-- ----------------------------
INSERT INTO `t_p_c` VALUES ('1', '01', '0101');
INSERT INTO `t_p_c` VALUES ('10', '04', '0402');
INSERT INTO `t_p_c` VALUES ('2', '01', '0102');
INSERT INTO `t_p_c` VALUES ('3', '01', '0103');
INSERT INTO `t_p_c` VALUES ('4', '01', '0104');
INSERT INTO `t_p_c` VALUES ('5', '01', '0105');
INSERT INTO `t_p_c` VALUES ('6', '02', '0201');
INSERT INTO `t_p_c` VALUES ('7', '03', '0301');
INSERT INTO `t_p_c` VALUES ('8', '03', '0302');
INSERT INTO `t_p_c` VALUES ('9', '04', '0401');

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
INSERT INTO `t_resource` VALUES ('3', 'personalCenter', '/PersonalCenter.jsp');
INSERT INTO `t_resource` VALUES ('4', 'resourceManage', '/resourseManage.jsp');
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
  `mail` varchar(255) DEFAULT NULL,
  `provinceCode` varchar(255) DEFAULT NULL,
  `cityCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('admin', '12345', '马大神', '1934616829@qq.com', '01', '0101');
INSERT INTO `t_user` VALUES ('admin1', '12345', '马时', '1@qq.com', '01', '0101');
INSERT INTO `t_user` VALUES ('admin2', '12345', '马时', '1@qq.com', '01', '0102');
INSERT INTO `t_user` VALUES ('mmm', '333', '马毛毛', '333@qq.com', '04', '0402');
INSERT INTO `t_user` VALUES ('mxt', '666666', '马小跳', '666@qq.com', '02', '0201');
INSERT INTO `t_user` VALUES ('nbb', '666', '暖宝宝', '3@qq.com', '01', '0102');
INSERT INTO `t_user` VALUES ('sss', '444', '硅谷', '1@qq.com', '03', '0301');
INSERT INTO `t_user` VALUES ('xxs', '555', '宝贝', '2@qq.com', '03', '0302');

-- ----------------------------
-- Table structure for t_user2
-- ----------------------------
DROP TABLE IF EXISTS `t_user2`;
CREATE TABLE `t_user2` (
  `userName` varchar(255) NOT NULL,
  `chrName` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `provinceName` varchar(255) DEFAULT NULL,
  `cityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user2
-- ----------------------------
INSERT INTO `t_user2` VALUES ('admin', '马大神', '1934616829@qq.com', '湖北', '武汉');

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
