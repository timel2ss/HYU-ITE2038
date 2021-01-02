-- MariaDB dump 10.17  Distrib 10.5.6-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: music_streaming_service
-- ------------------------------------------------------
-- Server version	10.5.6-MariaDB

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
-- Current Database: `music_streaming_service`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `music_streaming_service` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `music_streaming_service`;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `A_Index` int(11) NOT NULL AUTO_INCREMENT,
  `A_ID` varchar(20) NOT NULL,
  `A_PW` varchar(80) NOT NULL,
  `A_Name` varchar(20) NOT NULL,
  `A_SSN` char(16) NOT NULL,
  `A_Address` varchar(30) DEFAULT NULL,
  `A_PhoneNumber` varchar(20) DEFAULT NULL,
  `A_Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`A_Index`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin','11111111','adminAddress','11111111','adminEmail@admin.com'),(2,'admin2','1c142b2d01aa34e9a36bde480645a57fd69e14155dacfab5a3f9257b77fdc8d8','admin2','22222222','admin2Address','22222222','admin2Email@admin.com'),(3,'admin3','4fc2b5673a201ad9b1fc03dcb346e1baad44351daa0503d5534b4dfdcc4332e0','admin3','33333333','admin3Address','33333333','admin3Email@admin.com');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contains`
--

DROP TABLE IF EXISTS `contains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contains` (
  `Music_Idx` int(11) NOT NULL,
  `PlayList_Idx` int(11) NOT NULL,
  PRIMARY KEY (`Music_Idx`,`PlayList_Idx`),
  KEY `contains_P_FK` (`PlayList_Idx`),
  CONSTRAINT `contains_M_FK` FOREIGN KEY (`Music_Idx`) REFERENCES `music` (`M_Index`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contains_P_FK` FOREIGN KEY (`PlayList_Idx`) REFERENCES `playlist` (`P_Index`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contains`
--

LOCK TABLES `contains` WRITE;
/*!40000 ALTER TABLE `contains` DISABLE KEYS */;
INSERT INTO `contains` VALUES (1,1),(3,1),(4,1);
/*!40000 ALTER TABLE `contains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `Music_Idx` int(11) NOT NULL,
  `MusicGenre` varchar(20) NOT NULL,
  PRIMARY KEY (`Music_Idx`,`MusicGenre`),
  CONSTRAINT `Genre_FK` FOREIGN KEY (`Music_Idx`) REFERENCES `music` (`M_Index`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'dance'),(2,'ballad'),(3,'dance'),(4,'hiphop'),(4,'rap');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manages_music`
--

DROP TABLE IF EXISTS `manages_music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manages_music` (
  `Admin_Idx` int(11) NOT NULL,
  `Music_Idx` int(11) NOT NULL,
  PRIMARY KEY (`Admin_Idx`,`Music_Idx`),
  KEY `MgM_M_FK` (`Music_Idx`),
  CONSTRAINT `MgM_A_FK` FOREIGN KEY (`Admin_Idx`) REFERENCES `admin` (`A_Index`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MgM_M_FK` FOREIGN KEY (`Music_Idx`) REFERENCES `music` (`M_Index`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manages_music`
--

LOCK TABLES `manages_music` WRITE;
/*!40000 ALTER TABLE `manages_music` DISABLE KEYS */;
INSERT INTO `manages_music` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `manages_music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manages_user`
--

DROP TABLE IF EXISTS `manages_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manages_user` (
  `Admin_Idx` int(11) NOT NULL,
  `User_Idx` int(11) NOT NULL,
  PRIMARY KEY (`Admin_Idx`,`User_Idx`),
  KEY `MgU_U_FK` (`User_Idx`),
  CONSTRAINT `MgU_A_FK` FOREIGN KEY (`Admin_Idx`) REFERENCES `admin` (`A_Index`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MgU_U_FK` FOREIGN KEY (`User_Idx`) REFERENCES `user` (`U_Index`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manages_user`
--

LOCK TABLES `manages_user` WRITE;
/*!40000 ALTER TABLE `manages_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `manages_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music` (
  `M_Index` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(40) NOT NULL,
  `Album` varchar(40) NOT NULL,
  `ReleaseDate` date DEFAULT NULL,
  `Lyrics` varchar(2000) DEFAULT NULL,
  `PlayTime` int(11) NOT NULL,
  `PlayCount` int(11) NOT NULL DEFAULT 0,
  `Composer` varchar(100) DEFAULT NULL,
  `Lyricist` varchar(100) DEFAULT NULL,
  `Arranger` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`M_Index`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (1,'Dynamite','Dynamite (DayTime Version)','2020-08-21',NULL,224,1,'David Stewart, Jessuca Agombar','David Stewart, Jessuca Agombar','David Stewart, Jessuca Agombar'),(2,'Can\'t_sleep','잠이 오질  않네요','2020-10-24',NULL,276,0,'Jang-Bum-Joon','장범준, 박경구','장범준, 전영호, 황인현, 이규형'),(3,'Dolphin','NONSTOP','2020-04-27',NULL,197,2,'Ryan S. Jhun, Celine Svanback','서정아','Ryan S. Jhun, Celine Svanback'),(4,'VVS','쇼미더머니9','2020-11-21',NULL,336,0,'GroovyRoom, Justice, Sik-K','저스디스, 그루비룸, 식케이','그루비룸');
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `P_Index` int(11) NOT NULL AUTO_INCREMENT,
  `P_Name` varchar(40) NOT NULL,
  `User_Idx` int(11) NOT NULL,
  `MusicCount` int(11) NOT NULL DEFAULT 0,
  `TotalLength` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`P_Index`),
  KEY `PlayList_FK` (`User_Idx`),
  CONSTRAINT `PlayList_FK` FOREIGN KEY (`User_Idx`) REFERENCES `user` (`U_Index`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES (1,'Playlist1',1,3,757),(2,'Playlist2',1,0,0);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singer`
--

DROP TABLE IF EXISTS `singer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `singer` (
  `Music_Idx` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`Music_Idx`,`Name`),
  CONSTRAINT `singer_FK` FOREIGN KEY (`Music_Idx`) REFERENCES `music` (`M_Index`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singer`
--

LOCK TABLES `singer` WRITE;
/*!40000 ALTER TABLE `singer` DISABLE KEYS */;
INSERT INTO `singer` VALUES (1,'BTS'),(2,'Jang-Bum-Joon'),(3,'Oh-my-girl'),(4,'Khundi_Panda'),(4,'Miranni'),(4,'Munchman'),(4,'Mushvenom');
/*!40000 ALTER TABLE `singer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `U_Index` int(11) NOT NULL AUTO_INCREMENT,
  `U_ID` varchar(20) NOT NULL,
  `U_PW` varchar(80) NOT NULL,
  `U_Name` varchar(20) NOT NULL,
  `U_SSN` char(16) NOT NULL,
  `U_Address` varchar(30) DEFAULT NULL,
  `U_PhoneNumber` varchar(20) DEFAULT NULL,
  `U_Email` varchar(50) DEFAULT NULL,
  `Subscription` tinyint(1) NOT NULL,
  `ExpirationDate` date DEFAULT NULL,
  `AutoSubscription` tinyint(1) NOT NULL,
  `ListCount` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`U_Index`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb','user','11111111','userAddress','11111111','userEmail@user.com',0,NULL,0,2,0),(2,'qwer1234','4d4f26369171994f3a46776ee2d88494fb9955800a5bb6261c016c4bb9f30b56','James','22222222','user2Address','22222222','user2Email@user.com',0,NULL,0,0,0),(3,'q1w2e3r4','13a5c202e320d0bf9bb2c6e2c7cf380a6f7de5d392509fee260b809c893ff2f9','John','33333333','user3Address','33333333','user3Email@user.com',0,NULL,0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-07  4:40:55
