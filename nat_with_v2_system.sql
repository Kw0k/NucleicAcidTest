-- MySQL dump 10.13  Distrib 8.0.24, for Linux (x86_64)
--
-- Host: localhost    Database: nat
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `groupId` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (6,'XX小区1月17日',8,1),(7,'XXX小区1月18日',8,0),(8,'测试测试',8,0),(9,'123123',13,0);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_v2_uer_record`
--

DROP TABLE IF EXISTS `system_v2_uer_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_v2_uer_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `idcardnum` varchar(18) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_v2_uer_record_un` (`idcardnum`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_v2_uer_record`
--

LOCK TABLES `system_v2_uer_record` WRITE;
/*!40000 ALTER TABLE `system_v2_uer_record` DISABLE KEYS */;
INSERT INTO `system_v2_uer_record` VALUES (1,'123','411111111111111112'),(2,'123','411111111111111113'),(5,'123','411111111111111110');
/*!40000 ALTER TABLE `system_v2_uer_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_v2_user`
--

DROP TABLE IF EXISTS `system_v2_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_v2_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_v2_user_un` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='第二套登录系统表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_v2_user`
--

LOCK TABLES `system_v2_user` WRITE;
/*!40000 ALTER TABLE `system_v2_user` DISABLE KEYS */;
INSERT INTO `system_v2_user` VALUES (1,'123','123');
/*!40000 ALTER TABLE `system_v2_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `systemuser` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastlogintime` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `groupid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemuser`
--

LOCK TABLES `systemuser` WRITE;
/*!40000 ALTER TABLE `systemuser` DISABLE KEYS */;
INSERT INTO `systemuser` VALUES (2,'admin','e10adc3949ba59abbe56e057f20f883e','2023-03-12 23:32:29','0',NULL),(8,'医院A','e10adc3949ba59abbe56e057f20f883e','2022-01-17 16:04:19','1',NULL),(9,'采集员A','e10adc3949ba59abbe56e057f20f883e','2023-03-12 22:48:34','2',8),(10,'医院B','e10adc3949ba59abbe56e057f20f883e',NULL,'1',NULL),(11,'采集员A2','e10adc3949ba59abbe56e057f20f883e',NULL,'2',8),(12,'采集员B','e10adc3949ba59abbe56e057f20f883e',NULL,'2',10),(13,'新余医院','e10adc3949ba59abbe56e057f20f883e',NULL,'1',NULL),(14,'00','c6f057b86584942e415435ffb1fa93d4','2023-03-12 23:30:57','2',13);
/*!40000 ALTER TABLE `systemuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tube`
--

DROP TABLE IF EXISTS `tube`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tube` (
  `tubeId` varchar(255) NOT NULL,
  `operatorId` int DEFAULT NULL,
  `groupId` int DEFAULT NULL,
  `jobId` int DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`tubeId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tube`
--

LOCK TABLES `tube` WRITE;
/*!40000 ALTER TABLE `tube` DISABLE KEYS */;
INSERT INTO `tube` VALUES ('Kwok666',9,8,6,'2022-01-17 15:59:57','2023-03-12 00:11:45',1),('试管1',14,13,9,'2023-03-12 23:31:57','2023-03-12 23:32:50',2);
/*!40000 ALTER TABLE `tube` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tubeuser`
--

DROP TABLE IF EXISTS `tubeuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tubeuser` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tubeId` varchar(255) DEFAULT NULL,
  `idcardnum` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tubeuser`
--

LOCK TABLES `tubeuser` WRITE;
/*!40000 ALTER TABLE `tubeuser` DISABLE KEYS */;
INSERT INTO `tubeuser` VALUES (21,'Kwok666','411111111111111114'),(22,'Kwok666','411111111111111113'),(23,'Kwok666','411111111111111112'),(24,'Kwok666','411111111111111111'),(25,'试管1','111111111111111111');
/*!40000 ALTER TABLE `tubeuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `idcardnum` varchar(18) NOT NULL,
  `tname` varchar(255) NOT NULL,
  `sex` tinyint NOT NULL,
  `phonenum` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`idcardnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES ('411111111111111100','探路者',1,'11111111111','澳门特别行政区/离岛/圣方济各堂区','破碎月亮'),('411111111111111110','Apex',1,'11111111111','甘肃省/甘南藏族自治州/临潭县','捍卫者路'),('411111111111111112','李四',1,'13888888888','北京市/北京市/东城区','XXX小区'),('411111111111111113','王五',1,'13222222222','北京市/北京市/东城区','XX小区'),('411111111111111114','赵六',1,'13333333333','北京市/北京市/东城区','Xx小区');
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechatuser`
--

DROP TABLE IF EXISTS `wechatuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wechatuser` (
  `openid` varchar(255) DEFAULT NULL,
  `idcardnum` varchar(18) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
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

-- Dump completed on 2023-03-19  9:43:27
