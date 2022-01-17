/*
 Navicat Premium Data Transfer

 Source Server         : centos.kwok.fun
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : centos.kwok.fun:3306
 Source Schema         : nat

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 17/01/2022 16:30:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job
-- ----------------------------
BEGIN;
INSERT INTO `job` VALUES (6, 'XX小区1月17日', 8, 0);
INSERT INTO `job` VALUES (7, 'XXX小区1月18日', 8, 0);
COMMIT;

-- ----------------------------
-- Table structure for systemuser
-- ----------------------------
DROP TABLE IF EXISTS `systemuser`;
CREATE TABLE `systemuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastlogintime` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemuser
-- ----------------------------
BEGIN;
INSERT INTO `systemuser` VALUES (2, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '2022-01-17 16:22:20', '0', NULL);
INSERT INTO `systemuser` VALUES (8, '医院A', 'e10adc3949ba59abbe56e057f20f883e', '2022-01-17 16:04:19', '1', NULL);
INSERT INTO `systemuser` VALUES (9, '采集员A', 'e10adc3949ba59abbe56e057f20f883e', '2022-01-17 15:59:23', '2', 8);
INSERT INTO `systemuser` VALUES (10, '医院B', 'e10adc3949ba59abbe56e057f20f883e', NULL, '1', NULL);
INSERT INTO `systemuser` VALUES (11, '采集员A2', 'e10adc3949ba59abbe56e057f20f883e', NULL, '2', 8);
INSERT INTO `systemuser` VALUES (12, '采集员B', 'e10adc3949ba59abbe56e057f20f883e', NULL, '2', 10);
COMMIT;

-- ----------------------------
-- Table structure for tube
-- ----------------------------
DROP TABLE IF EXISTS `tube`;
CREATE TABLE `tube` (
  `tubeId` varchar(255) NOT NULL,
  `operatorId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `jobId` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`tubeId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tube
-- ----------------------------
BEGIN;
INSERT INTO `tube` VALUES ('Kwok666', 9, 8, 6, '2022-01-17 15:59:57', '2022-01-17 16:04:35', 1);
COMMIT;

-- ----------------------------
-- Table structure for tubeuser
-- ----------------------------
DROP TABLE IF EXISTS `tubeuser`;
CREATE TABLE `tubeuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tubeId` varchar(255) DEFAULT NULL,
  `idcardnum` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tubeuser
-- ----------------------------
BEGIN;
INSERT INTO `tubeuser` VALUES (21, 'Kwok666', '411111111111111114');
INSERT INTO `tubeuser` VALUES (22, 'Kwok666', '411111111111111113');
INSERT INTO `tubeuser` VALUES (23, 'Kwok666', '411111111111111112');
INSERT INTO `tubeuser` VALUES (24, 'Kwok666', '411111111111111111');
COMMIT;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `idcardnum` varchar(18) NOT NULL,
  `tname` varchar(255) NOT NULL,
  `sex` tinyint(4) NOT NULL,
  `phonenum` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`idcardnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
BEGIN;
INSERT INTO `userinfo` VALUES ('411111111111111111', '张三', 1, '18888888888', '北京市/北京市/东城区', 'XXX小区5号楼2单元1601');
INSERT INTO `userinfo` VALUES ('411111111111111112', '李四', 1, '13888888888', '北京市/北京市/东城区', 'XXX小区');
INSERT INTO `userinfo` VALUES ('411111111111111113', '王五', 1, '13222222222', '北京市/北京市/东城区', 'XX小区');
INSERT INTO `userinfo` VALUES ('411111111111111114', '赵六', 1, '13333333333', '北京市/北京市/东城区', 'Xx小区');
COMMIT;

-- ----------------------------
-- Table structure for wechatuser
-- ----------------------------
DROP TABLE IF EXISTS `wechatuser`;
CREATE TABLE `wechatuser` (
  `openid` varchar(255) DEFAULT NULL,
  `idcardnum` varchar(18) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wechatuser
-- ----------------------------
BEGIN;
INSERT INTO `wechatuser` VALUES ('oukBP58WB6rvw1qD-m6tJWyr7c5A', '411111111111111111', 17);
INSERT INTO `wechatuser` VALUES ('oukBP58WB6rvw1qD-m6tJWyr7c5A', '411111111111111112', 18);
INSERT INTO `wechatuser` VALUES ('oukBP58WB6rvw1qD-m6tJWyr7c5A', '411111111111111113', 19);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
