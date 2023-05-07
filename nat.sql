-- MySQL dump 10.13  Distrib 5.5.62, for Linux (x86_64)
--
-- Host: localhost    Database: nat
-- ------------------------------------------------------
-- Server version	5.5.62-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (6,'XX小区1月17日',8,1),(7,'XXX小区1月18日',8,0),(8,'测试测试',8,0),(9,'123123',13,1),(10,'一号测试',13,0),(11,'采集东西-1',13,0);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `node_log`
--

DROP TABLE IF EXISTS `node_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `node_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `session_id` varchar(100) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `opt_id` int(11) NOT NULL,
  `material_tube_num` int(11) DEFAULT NULL,
  `staff_num` int(11) DEFAULT NULL,
  `material_swab_num` int(11) DEFAULT NULL,
  `material_alcohol_num` int(11) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `node_log_un` (`opt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_log`
--

LOCK TABLES `node_log` WRITE;
/*!40000 ALTER TABLE `node_log` DISABLE KEYS */;
INSERT INTO `node_log` VALUES (6,'',NULL,14,NULL,NULL,NULL,NULL,'2023-04-03 20:50:58'),(7,'',NULL,15,6973,3097,462,3311,'2023-05-06 11:26:48');
/*!40000 ALTER TABLE `node_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_v2_uer_record`
--

DROP TABLE IF EXISTS `system_v2_uer_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_v2_uer_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `idcardnum` varchar(18) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_v2_uer_record_un` (`idcardnum`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_v2_uer_record`
--

LOCK TABLES `system_v2_uer_record` WRITE;
/*!40000 ALTER TABLE `system_v2_uer_record` DISABLE KEYS */;
INSERT INTO `system_v2_uer_record` VALUES (1,'123','411111111111111112'),(2,'123','411111111111111113'),(5,'123','411111111111111110'),(7,'phc','411111111111111011');
/*!40000 ALTER TABLE `system_v2_uer_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_v2_user`
--

DROP TABLE IF EXISTS `system_v2_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_v2_user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_v2_user_un` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_v2_user`
--

LOCK TABLES `system_v2_user` WRITE;
/*!40000 ALTER TABLE `system_v2_user` DISABLE KEYS */;
INSERT INTO `system_v2_user` VALUES (1,'123','123'),(7,'phc','phc');
/*!40000 ALTER TABLE `system_v2_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastlogintime` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemuser`
--

LOCK TABLES `systemuser` WRITE;
/*!40000 ALTER TABLE `systemuser` DISABLE KEYS */;
INSERT INTO `systemuser` VALUES (2,'admin','e10adc3949ba59abbe56e057f20f883e','2023-05-06 10:52:09','0',NULL),(8,'医院A','e10adc3949ba59abbe56e057f20f883e','2022-01-17 16:04:19','1',NULL),(9,'采集员A','e10adc3949ba59abbe56e057f20f883e','2023-05-06 11:09:17','2',8),(10,'医院B','e10adc3949ba59abbe56e057f20f883e',NULL,'1',NULL),(11,'采集员A2','e10adc3949ba59abbe56e057f20f883e',NULL,'2',8),(12,'采集员B','e10adc3949ba59abbe56e057f20f883e','2023-04-01 15:05:40','2',10),(13,'新余医院','e10adc3949ba59abbe56e057f20f883e',NULL,'1',NULL),(14,'00','c6f057b86584942e415435ffb1fa93d4','2023-03-12 23:30:57','2',13),(15,'node-1','e10adc3949ba59abbe56e057f20f883e','2023-03-20 21:47:53','2',13),(97,'node-xy','e10adc3949ba59abbe56e057f20f883e',NULL,'2',13);
/*!40000 ALTER TABLE `systemuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tube`
--

DROP TABLE IF EXISTS `tube`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tube`
--

LOCK TABLES `tube` WRITE;
/*!40000 ALTER TABLE `tube` DISABLE KEYS */;
INSERT INTO `tube` VALUES ('123456',9,8,7,'2023-05-05 22:12:51',NULL,0),('Kwok666',9,8,6,'2022-01-17 15:59:57','2023-03-12 00:11:45',1),('试管1',14,13,9,'2023-03-12 23:31:57','2023-03-12 23:32:50',2);
/*!40000 ALTER TABLE `tube` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tubeuser`
--

DROP TABLE IF EXISTS `tubeuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tubeuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tubeId` varchar(255) DEFAULT NULL,
  `idcardnum` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tubeuser`
--

LOCK TABLES `tubeuser` WRITE;
/*!40000 ALTER TABLE `tubeuser` DISABLE KEYS */;
INSERT INTO `tubeuser` VALUES (21,'Kwok666','411111111111111114'),(22,'Kwok666','411111111111111113'),(23,'Kwok666','411111111111111112'),(24,'Kwok666','411111111111111111'),(25,'试管1','111111111111111111'),(26,'123456','411111111111111011');
/*!40000 ALTER TABLE `tubeuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinfo` (
  `idcardnum` varchar(18) NOT NULL,
  `tname` varchar(255) NOT NULL,
  `sex` tinyint(4) NOT NULL,
  `phonenum` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`idcardnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES ('360555555555555555','海晨',1,'1234564567899','内蒙古自治区/呼和浩特市/托克托县','一个地方'),('411111111111111011','mac',1,'11111111111','北京市/北京市/丰台区','捍卫者路'),('411111111111111100','探路者',1,'11111111111','澳门特别行政区/离岛/圣方济各堂区','破碎月亮'),('411111111111111110','Apex',1,'11111111111','甘肃省/甘南藏族自治州/临潭县','捍卫者路'),('411111111111111112','李四',1,'13888888888','北京市/北京市/东城区','XXX小区'),('411111111111111113','王五',1,'13222222222','北京市/北京市/东城区','XX小区'),('411111111111111114','赵六',1,'13333333333','北京市/北京市/东城区','Xx小区'),('411111111111188888','海晨',1,'11111111112','山西省/太原市/小店区','一条路');
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechatuser`
--

DROP TABLE IF EXISTS `wechatuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechatuser` (
  `openid` varchar(255) DEFAULT NULL,
  `idcardnum` varchar(18) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechatuser`
--

LOCK TABLES `wechatuser` WRITE;
/*!40000 ALTER TABLE `wechatuser` DISABLE KEYS */;
INSERT INTO `wechatuser` VALUES ('oukBP58WB6rvw1qD-m6tJWyr7c5A','411111111111111111',17),('oukBP58WB6rvw1qD-m6tJWyr7c5A','411111111111111112',18),('oukBP58WB6rvw1qD-m6tJWyr7c5A','411111111111111113',19);
/*!40000 ALTER TABLE `wechatuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'nat'
--

--
-- Dumping routines for database 'nat'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-07 19:56:51
