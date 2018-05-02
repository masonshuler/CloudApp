-- MySQL dump 10.13  Distrib 5.1.73, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: GoodwillOnlineDB
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE GoodwillOnlineDB;
USE GoodwillOnlineDB;

DROP TABLE IF EXISTS `UserPhoto`;
DROP TABLE IF EXISTS `ItemPhoto`;
DROP TABLE IF EXISTS `Item`;
DROP TABLE IF EXISTS `User`;

--
-- Table structure for table `User`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `password_hash` varchar(256) NOT NULL,
  `first_name` varchar(32) NOT NULL,
  `middle_name` varchar(32) DEFAULT NULL,
  `last_name` varchar(32) NOT NULL,
  `address1` varchar(128) NOT NULL,
  `address2` varchar(128) DEFAULT NULL,
  `city` varchar(64) NOT NULL,
  `state` varchar(2) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `security_question` int(11) NOT NULL,
  `security_answer` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  `auth_code` varchar(15) DEFAULT NULL,
  `isAdmin` bit(1) NOT NULL,
  `username` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'cd916028a2d8a1b901e831246dd5b9b4d3832786ddc63bbf5af4b50d9fc98f50','Good',NULL,'Will','1411 N Main St',NULL,'Blacksburg','VA','24060',0,'Blacksburg','admin@gmail.com','8045162266',NULL,'','admin'),(2,'ba1c933e549aaf3869599deffd8a526e2f5c8e11dcd15c0da6fb7b14bd61b1cd','Scott','','McGhee','1411 N Main St','','Blacksburg','VA','24060',0,'Blacksburg','emcghee@vt.edu','8045162266',NULL,'','McGhee'),(3,'6a182cf567519bd04b62299e00604fb38b266563e3e1651eec90cf6f24b71ece','Shuvo','','Rahman','1411 N Main St','','Blacksburg','VA','24060',0,'Blacksburg','shuvesta@vt.edu','8045162266',NULL,'','Rahman'),(4,'977ed29985e6291eebd361519be9a17e7e384a298ed6ed006f1fcebe38e8a370','Mason','','Shuler','1411 N Main St','','Blacksburg','VA','24060',0,'Blacksburg','mshuler@vt.edu','8045162266',NULL,'','Shuler'),(5,'b2f6aba1026a95d33fb216934bb7dbdbc94e78bc0d3d2940ae0a7cf7aa3650d2','Matt','','Tuckman','1411 N Main St','','Blacksburg','VA','24060',0,'Blacksburg','matt4@vt.edu','8045162266',NULL,'','Tuckman'),(6,'e051a9d36a1deb07a8a07fc0bb7e98852566651d77223004e4db5011e3b43359','Jordan','','Kuhn','1411 N Main St','','Blacksburg','VA','24060',0,'Blacksburg','jordan01@vt.edu','8045162266',NULL,'','JordanKuhn');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `UserPhoto`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserPhoto` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `extension` enum('jpeg','jpg','png','gif') NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserPhoto`
--

LOCK TABLES `UserPhoto` WRITE;
/*!40000 ALTER TABLE `UserPhoto` DISABLE KEYS */;
INSERT INTO `UserPhoto` VALUES (1,'png',2),(2,'jpeg',3),(3,'png',4),(4,'png',5),(5,'png',6);
/*!40000 ALTER TABLE `UserPhoto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reserved_user` int(10) unsigned DEFAULT NULL,
  `title` varchar(64) NOT NULL,
  `price` float(7,2) NOT NULL,
  `rating` float(4,2) NOT NULL,
  `category` varchar(32) NOT NULL,
  `size` varchar(32) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `date_published` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `reserved_user` (`reserved_user`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item`
--

LOCK TABLES `Item` WRITE;
/*!40000 ALTER TABLE `Item` DISABLE KEYS */;
INSERT INTO `Item` VALUES (1,NULL,'Polka Dot Slim Fit Dress Shirt',129.00,0.00,'T-Shirts','M','Material is cotton/silk, slim fit.','2018-04-18'),(2,NULL,'Diesel Brothers Shirt',16.98,0.00,'T-Shirts','S','Shirt with orange and skull logo from diesel brothers','2018-04-18'),(3,NULL,'Aston Martin Racing Shirt',44.99,0.00,'T-Shirts','L','Black shirt with logos','2018-04-18'),(4,NULL,'PK Subban Playoff Hockey Shirt',19.99,0.00,'T-Shirts','L','Black shirt with small hockey logo in the middle','2018-04-18'),(5,NULL,'Biker Denim Jeans',13.99,0.00,'Pants','XL','Short cut, mens ripped denim jeans','2018-04-18'),(6,NULL,'Long Gym Sweatpants',7.99,0.00,'Pants','XXL','Sporty sweatpants with white stripe on the side','2018-04-18'),(7,NULL,'Military Tactical Combat Pants',28.00,0.00,'Pants','S','Khaki colored military pants with many pockets','2018-04-18'),(8,NULL,'Dark Navy Blue Khaki Pants',11.99,0.00,'Pants','XS','Traditional dark navy blue khaki pants','2018-04-18'),(9,NULL,'Hooded Color Block Corduroy Jacket',39.99,0.00,'Coats & Jackets','XXXL','Multi-colored coat with green base and yellow/red accents','2018-04-18'),(10,NULL,'North Face Cinder Fleece Jacket',58.45,0.00,'Coats & Jackets','M','Black fleece jacket made by north face','2018-05-01'),(11,NULL,'Alpine Swiss Mens Zach Knee Length Jacket',29.99,0.00,'Coats & Jackets','XL','Long, knee-length, overcoat jacket made by Apline Swiss','2018-05-01'),(12,NULL,'Under Armor UA Storm Jacket',39.99,0.00,'Coats & Jackets','S','Lightweight waterproof jacket made by Under Armor','2018-05-01'),(13,NULL,'Floral Print Smocked Tie Back',42.99,0.00,'Dresses','XL','Sleevless, back tie blue floral dress from Juniors','2018-05-01'),(14,NULL,'Floral Lace Bridesmain Dress',26.99,0.00,'Dresses','XS','A short, blue, floral bridesmaid dress with v-neck','2018-05-01'),(15,NULL,'Short Sleeve Solid V-Neck Dress',13.83,0.00,'Dresses','M','Green, 100% cotton short sleeve solid v-neck tshirt dress','2018-05-01'),(16,NULL,'Summer Loose Sleevless Sundress',8.99,0.00,'Dresses','S','Summer cotton loose vest sleeveless beach sundress','2018-05-01'),(17,NULL,'Crown and Ivy Floral High Blouse',24.99,0.00,'Tops & Blouses','S','Black floral high low blouse with white background','2018-05-01'),(18,NULL,'Blue and White Floral Spaghetti Strap',12.00,0.00,'Tops & Blouses','L','Torrid womens top blue and white floral spaghetti strap tank top','2018-05-01'),(19,NULL,'Live and Let Live Blouse',12.71,0.00,'Tops & Blouses','XL','One world live and let live womens shirt multi-colored','2018-05-01'),(20,NULL,'Black and Gray Shirt',6.97,0.00,'Tops & Blouses','M','Medium GIOIA black and gray shirt','2018-05-01'),(21,NULL,'V Neck Playsuit',12.99,0.00,'Jumpsuits & Rompers','XXL','Summer v-neck playsuit casual long pant jumpsuit','2018-05-01'),(22,NULL,'Boutique Cold Shoulder Jumpsuit',42.00,0.00,'Jumpsuits & Rompers','S','NWT Boutique black small cold shoulder jumpsuit','2018-05-01'),(23,NULL,'Strapless Casual Loose Black Jumpsuit',11.57,0.00,'Jumpsuits & Rompers','S','Lady strapless casual loose black harem v playsuit','2018-05-01'),(24,NULL,'Sequin Clubwear Sleeveless Jumpsuit',18.79,0.00,'Jumpsuits & Rompers','M','Party overalls sleeveless jumpsuit','2018-05-01'),(25,NULL,'Body Shaper High Waisted Leggings',7.99,0.00,'Leggings','L','Alta womens seamless body shaper high waisted premium stretch leggings','2018-05-01'),(26,NULL,'Columbia Womens Glacial Leggings',19.99,0.00,'Leggings','L','Columbia womens glacial leggings','2018-05-01'),(27,NULL,'Skinny High Waist Pencil Leggings',9.99,0.00,'Leggings','XL','New skinny floral high waist stretchy penicl leggings','2018-05-01'),(28,NULL,'Reebok Womens Sports Leggings',12.99,0.00,'Leggings','S','Reebok sports leggings for athletic activities','2018-05-01');
/*!40000 ALTER TABLE `Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemPhoto`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ItemPhoto` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `extension` enum('jpeg','jpg','png','gif') NOT NULL,
  `item_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemPhoto`
--

LOCK TABLES `ItemPhoto` WRITE;
/*!40000 ALTER TABLE `ItemPhoto` DISABLE KEYS */;
INSERT INTO `ItemPhoto` VALUES (1,'jpeg',1),(2,'jpeg',2),(3,'jpeg',3),(4,'jpeg',4),(5,'jpeg',5),(6,'jpeg',6),(7,'jpeg',7),(8,'jpeg',8),(9,'jpg',9),(10,'jpeg',10),(11,'jpeg',11),(12,'jpeg',12),(13,'jpeg',13),(14,'jpeg',14),(15,'jpeg',15),(16,'jpeg',16),(17,'jpeg',17),(18,'jpeg',18),(19,'jpeg',19),(20,'jpeg',20),(21,'jpeg',21),(22,'jpeg',22),(23,'jpeg',23),(24,'jpeg',24),(25,'jpeg',25),(26,'jpeg',26),(27,'jpeg',27),(28,'jpeg',28);
/*!40000 ALTER TABLE `ItemPhoto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-02  5:20:27
