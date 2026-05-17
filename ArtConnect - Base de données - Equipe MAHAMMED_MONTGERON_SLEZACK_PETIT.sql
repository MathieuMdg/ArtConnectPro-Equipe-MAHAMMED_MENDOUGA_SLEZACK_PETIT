CREATE DATABASE  IF NOT EXISTS `artconnect` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `artconnect`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: artconnect
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('USER','ADMIN') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'USER',
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_user_member` (`member_id`),
  CONSTRAINT `fk_user_member` FOREIGN KEY (`member_id`) REFERENCES `community_member` (`member_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'admin','$2a$10$Bci505mfkkcGLSBm4QTmxOCFrEr5neEWGVhCm0DuAnyIl6QihgUDS','ADMIN',NULL),(2,'alice','$2a$10$m.Eha6Ry229qPXCs7ZAQ2uIpppzqAB4FRW84u1Ctl0oqYrjaLbdu2','USER',1),(3,'mathieumdg','$2a$10$lmmkTnTxVRoNuHb0ljJ1ruQl2.olqqLmMpYE19.xJlciu3fSiEmya','USER',NULL),(4,'student','$2a$10$frrO61IG7VQKw8LNQ6dRZugT4tIUabhtP6QaYQ1Z/73t6SBvurdKa','USER',NULL),(5,'mathieu','$2a$10$39eokDjXGAuuUY7VNuGhbev1uAYJ5Xc3v5iN80JPW/pwK1pr88LT2','USER',NULL),(6,'robin','$2a$10$2yHoc6dIPq0mQDzgU47TS.t55crwxG.nB3udA0Zr040XP7C8Pv/wK','USER',NULL),(7,'joker','$2a$10$tcbxENiFG3m5G.0MlPUfQed..rRZdNx7ipaAqH5fWErFFX3v/Ka5e','USER',16),(9,'alfred','$2a$10$Ept/KMdrl/SRw3Zve1vEqegs.Tp21bSldQEJa9T87X4oMy0cs6uAK','USER',18);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artist` (
  `artist_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bio` text COLLATE utf8mb4_unicode_ci,
  `birth_year` int DEFAULT NULL,
  `contact_email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `website` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `social_media` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`artist_id`),
  UNIQUE KEY `contact_email` (`contact_email`),
  KEY `idx_artist_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (2,'Claude Monet','Master of impressionist painting.',1840,'claude@monet.fr','+33-222-222','Giverny','www.monet.fr','@claude_monet',1),(3,'Ansel Adams','Landscape and black-and-white photographer.',1902,'ansel@adams.co','+1-333-333','San Francisco','www.anseladams.co','@ansel_adams',1),(4,'Frida Kahlo','Iconic painter focused on identity and symbolism.',1907,'frida@kahlo.mx','+52-444-444','Mexico City','www.fridakahlo.mx','@frida_kahlo',1),(5,'Auguste Rodin','French sculptor and art mentor.',1840,'auguste@rodin.fr','+33-555-555','Paris','www.rodin.fr','@auguste_rodin',1),(6,'Sofia Turner','Digital and mixed-media contemporary artist.',1992,'sofia@turner.art','+44-666-666','London','www.sofiaturner.art','@sofia_turner',1),(12,'Batman','Je suis batman',1988,'batman@gmail.com','841972','Gotham','www.batman.web','batstagram',1);
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist_discipline`
--

DROP TABLE IF EXISTS `artist_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artist_discipline` (
  `artist_id` int NOT NULL,
  `discipline_id` int NOT NULL,
  PRIMARY KEY (`artist_id`,`discipline_id`),
  KEY `fk_artist_discipline_discipline` (`discipline_id`),
  CONSTRAINT `fk_artist_discipline_artist` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_artist_discipline_discipline` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`discipline_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist_discipline`
--

LOCK TABLES `artist_discipline` WRITE;
/*!40000 ALTER TABLE `artist_discipline` DISABLE KEYS */;
INSERT INTO `artist_discipline` VALUES (2,1),(4,1),(5,2),(3,3),(6,4),(6,5);
/*!40000 ALTER TABLE `artist_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artwork`
--

DROP TABLE IF EXISTS `artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artwork` (
  `artwork_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creation_year` int DEFAULT NULL,
  `type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `medium` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dimensions` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `price` decimal(15,2) DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `artist_id` int NOT NULL,
  PRIMARY KEY (`artwork_id`),
  KEY `fk_artwork_artist` (`artist_id`),
  KEY `idx_artwork_title` (`title`),
  CONSTRAINT `fk_artwork_artist` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_artwork_price` CHECK (((`price` is null) or (`price` >= 0))),
  CONSTRAINT `chk_artwork_status` CHECK ((`status` in (_utf8mb4'FOR_SALE',_utf8mb4'SOLD',_utf8mb4'EXHIBITED')))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artwork`
--

LOCK TABLES `artwork` WRITE;
/*!40000 ALTER TABLE `artwork` DISABLE KEYS */;
INSERT INTO `artwork` VALUES (3,'Water Lilies',1916,'Painting','Oil on canvas','200x180 cm','Series inspired by Monet\'s garden.',40000000.00,'FOR_SALE',2),(4,'The Two Fridas',1939,'Painting','Oil on canvas','173x173 cm','Double self-portrait.',5000000.00,'EXHIBITED',4),(5,'The Thinker',1904,'Sculpture','Bronze','186x98x145 cm','Famous bronze sculpture.',15000000.00,'FOR_SALE',5),(6,'Monolith, The Face of Half Dome',1927,'Photography','Gelatin silver print','40x50 cm','Iconic black-and-white landscape.',100000.00,'SOLD',3),(7,'Neon Fragments',2022,'Digital Art','Digital mixed media','4K','Contemporary digital composition.',12000.00,'FOR_SALE',6),(8,'Urban Pulse',2023,'Mixed Media','Acrylic and digital print','120x90 cm','City-inspired hybrid artwork.',18000.00,'FOR_SALE',6),(10,'Capture du Joker',1985,'Joker','oui','12','En prison le filou',12.00,'FOR_SALE',12);
/*!40000 ALTER TABLE `artwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artwork_tag`
--

DROP TABLE IF EXISTS `artwork_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artwork_tag` (
  `tag_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artwork_tag`
--

LOCK TABLES `artwork_tag` WRITE;
/*!40000 ALTER TABLE `artwork_tag` DISABLE KEYS */;
INSERT INTO `artwork_tag` VALUES (6,'Abstract'),(5,'BlackAndWhite'),(4,'Classic'),(2,'Landscape'),(3,'Modern'),(1,'Portrait');
/*!40000 ALTER TABLE `artwork_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artwork_tag_map`
--

DROP TABLE IF EXISTS `artwork_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artwork_tag_map` (
  `artwork_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`artwork_id`,`tag_id`),
  KEY `fk_artwork_tag_map_tag` (`tag_id`),
  CONSTRAINT `fk_artwork_tag_map_artwork` FOREIGN KEY (`artwork_id`) REFERENCES `artwork` (`artwork_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_artwork_tag_map_tag` FOREIGN KEY (`tag_id`) REFERENCES `artwork_tag` (`tag_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artwork_tag_map`
--

LOCK TABLES `artwork_tag_map` WRITE;
/*!40000 ALTER TABLE `artwork_tag_map` DISABLE KEYS */;
INSERT INTO `artwork_tag_map` VALUES (4,1),(3,2),(6,2),(5,3),(7,3),(8,3),(6,5),(4,6),(7,6);
/*!40000 ALTER TABLE `artwork_tag_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `workshop_id` int NOT NULL,
  `member_id` int NOT NULL,
  `booking_date` datetime NOT NULL,
  `payment_status` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `fk_booking_member` (`member_id`),
  KEY `idx_booking_workshop_status` (`workshop_id`,`payment_status`),
  CONSTRAINT `fk_booking_member` FOREIGN KEY (`member_id`) REFERENCES `community_member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_workshop` FOREIGN KEY (`workshop_id`) REFERENCES `workshop` (`workshop_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chk_booking_payment_status` CHECK ((`payment_status` in (_utf8mb4'PENDING',_utf8mb4'PAID',_utf8mb4'FAILED',_utf8mb4'CANCELLED')))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (3,2,3,'2026-04-02 11:15:00','PAID'),(4,2,5,'2026-04-02 12:00:00','PAID'),(5,3,4,'2026-04-03 14:20:00','PAID'),(6,4,6,'2026-04-04 16:00:00','PENDING'),(7,4,1,'2026-04-04 16:30:00','PAID'),(8,2,6,'2026-05-12 19:37:26','PAID'),(10,4,3,'2026-05-12 19:37:26','PAID');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_member`
--

DROP TABLE IF EXISTS `community_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birth_year` int DEFAULT NULL,
  `phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `membership_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_member`
--

LOCK TABLES `community_member` WRITE;
/*!40000 ALTER TABLE `community_member` DISABLE KEYS */;
INSERT INTO `community_member` VALUES (1,'Alice Wonderland','alice@art.com',1998,'+33-801-001','Paris','Premium'),(2,'Bob Ross','bob@happytrees.com',1985,'+44-801-002','London','Standard'),(3,'Charlie Brown','charlie@peanuts.com',1995,'+1-801-003','New York','Student'),(4,'Diana Moore','diana@culture.org',1990,'+49-801-004','Berlin','Premium'),(5,'Emma Davis','emma@creative.net',2001,'+33-801-005','Lyon','Standard'),(6,'Farid Khan','farid@artsocial.io',1993,'+212-801-006','Casablanca','Premium'),(9,'Bruce Wayne','brucewayne@gmail.com',1987,'112454','Gotham','batabonnement'),(16,'Joker','joker@gmail.com',102506,'0606','Gotham','Student'),(17,'Robin','robin@gmail.com',50605,'0606','Gotham','Student'),(18,'Alfred','alfred@gmail.com',606,'0606','Gotham','Student');
/*!40000 ALTER TABLE `community_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discipline`
--

DROP TABLE IF EXISTS `discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discipline` (
  `discipline_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`discipline_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discipline`
--

LOCK TABLES `discipline` WRITE;
/*!40000 ALTER TABLE `discipline` DISABLE KEYS */;
INSERT INTO `discipline` VALUES (4,'Digital Art'),(5,'Mixed Media'),(1,'Painting'),(3,'Photography'),(6,'Printmaking'),(2,'Sculpture');
/*!40000 ALTER TABLE `discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exhibition`
--

DROP TABLE IF EXISTS `exhibition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exhibition` (
  `exhibition_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `curator_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `theme` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gallery_id` int NOT NULL,
  PRIMARY KEY (`exhibition_id`),
  KEY `fk_exhibition_gallery` (`gallery_id`),
  CONSTRAINT `fk_exhibition_gallery` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_exhibition_dates` CHECK (((`end_date` is null) or (`end_date` >= `start_date`)))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exhibition`
--

LOCK TABLES `exhibition` WRITE;
/*!40000 ALTER TABLE `exhibition` DISABLE KEYS */;
INSERT INTO `exhibition` VALUES (1,'Renaissance Revival','2026-03-09','2026-04-15','A celebration of classical Renaissance masterpieces.','Marie Laurent','Classic Renaissance',1),(2,'Sculpting the Soul','2026-03-25','2026-05-01','Exploration of sculptural expression.','James Carter','Modern & Classical Sculpture',2),(3,'Impressionist Dreams','2026-02-09','2026-03-20','Light, color and poetic atmosphere.','Olivia Stone','Light and Color',3),(4,'Digital Horizons','2026-04-05','2026-05-30','Contemporary digital and hybrid artworks.','Hans Keller','Future of Expression',4),(5,'Large Horizons','2026-04-08','2026-05-05','Modern art work.','Mahammed','Future of Art',5),(6,'Photography and Light','2026-06-10','2026-07-05','A curated exhibition around photography and light.','Olivia Stone','Visual Atmospheres',3),(8,'filou de joker','2026-05-05','2026-05-08','Joker je t\'ai eu','surement','capture de méchants',10);
/*!40000 ALTER TABLE `exhibition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exhibition_artwork`
--

DROP TABLE IF EXISTS `exhibition_artwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exhibition_artwork` (
  `exhibition_id` int NOT NULL,
  `artwork_id` int NOT NULL,
  PRIMARY KEY (`exhibition_id`,`artwork_id`),
  KEY `fk_exhibition_artwork_artwork` (`artwork_id`),
  CONSTRAINT `fk_exhibition_artwork_artwork` FOREIGN KEY (`artwork_id`) REFERENCES `artwork` (`artwork_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_exhibition_artwork_exhibition` FOREIGN KEY (`exhibition_id`) REFERENCES `exhibition` (`exhibition_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exhibition_artwork`
--

LOCK TABLES `exhibition_artwork` WRITE;
/*!40000 ALTER TABLE `exhibition_artwork` DISABLE KEYS */;
INSERT INTO `exhibition_artwork` VALUES (3,3),(3,4),(2,5),(4,6),(4,7),(4,8);
/*!40000 ALTER TABLE `exhibition_artwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery`
--

DROP TABLE IF EXISTS `gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gallery` (
  `gallery_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `owner_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `opening_hours` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contact_phone` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rating` decimal(3,1) DEFAULT NULL,
  `website` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`gallery_id`),
  CONSTRAINT `chk_gallery_rating` CHECK (((`rating` is null) or ((`rating` >= 0) and (`rating` <= 5))))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery`
--

LOCK TABLES `gallery` WRITE;
/*!40000 ALTER TABLE `gallery` DISABLE KEYS */;
INSERT INTO `gallery` VALUES (1,'Louvre Art House','Rue de Rivoli, Paris','Marie Laurent','09:00-18:00','+33-701-111',4.9,'www.louvrearthouse.fr'),(2,'The British Gallery','Great Russell St, London','James Carter','10:00-18:30','+44-702-222',4.7,'www.britishgallery.uk'),(3,'Metropolitan Hub','1000 5th Ave, New York','Olivia Stone','09:30-19:00','+1-703-333',4.8,'www.metropolitanhub.us'),(4,'Modern Arts Center','22 Art Street, Berlin','Hans Keller','10:00-20:00','+49-704-444',4.6,'www.modernartscenter.de'),(5,'Beaux Arts','32 Art Street, Berlin','Mahammed','10:01-20:00','+49-704-445',4.8,'www.modernartscenter.dz'),(10,'Gotham','gotham city','Bruce Wayne','12','12',2.0,'waynefoundation@gmail.com');
/*!40000 ALTER TABLE `gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_favorite_discipline`
--

DROP TABLE IF EXISTS `member_favorite_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_favorite_discipline` (
  `member_id` int NOT NULL,
  `discipline_id` int NOT NULL,
  PRIMARY KEY (`member_id`,`discipline_id`),
  KEY `fk_member_fav_discipline_discipline` (`discipline_id`),
  CONSTRAINT `fk_member_fav_discipline_discipline` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`discipline_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_fav_discipline_member` FOREIGN KEY (`member_id`) REFERENCES `community_member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_favorite_discipline`
--

LOCK TABLES `member_favorite_discipline` WRITE;
/*!40000 ALTER TABLE `member_favorite_discipline` DISABLE KEYS */;
INSERT INTO `member_favorite_discipline` VALUES (1,1),(2,1),(1,2),(6,2),(3,3),(4,4),(6,4),(5,5);
/*!40000 ALTER TABLE `member_favorite_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `reviewer_member_id` int NOT NULL,
  `artwork_id` int NOT NULL,
  `rating` int NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci,
  `review_date` date NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `fk_review_member` (`reviewer_member_id`),
  KEY `fk_review_artwork` (`artwork_id`),
  CONSTRAINT `fk_review_artwork` FOREIGN KEY (`artwork_id`) REFERENCES `artwork` (`artwork_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_review_member` FOREIGN KEY (`reviewer_member_id`) REFERENCES `community_member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chk_review_rating` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (2,2,3,4,'Beautiful colors and atmosphere.','2026-03-11'),(3,3,6,5,'Powerful photograph with great depth.','2026-03-12'),(4,4,7,4,'Very modern and engaging.','2026-04-06'),(5,5,5,5,'A monumental sculpture.','2026-03-28'),(6,6,4,4,'Emotional and striking composition.','2026-03-13');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_artwork_details`
--

DROP TABLE IF EXISTS `vw_artwork_details`;
/*!50001 DROP VIEW IF EXISTS `vw_artwork_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_artwork_details` AS SELECT 
 1 AS `artwork_id`,
 1 AS `title`,
 1 AS `type`,
 1 AS `medium`,
 1 AS `price`,
 1 AS `status`,
 1 AS `artist_name`,
 1 AS `artist_city`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_exhibition_catalog`
--

DROP TABLE IF EXISTS `vw_exhibition_catalog`;
/*!50001 DROP VIEW IF EXISTS `vw_exhibition_catalog`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_exhibition_catalog` AS SELECT 
 1 AS `exhibition_id`,
 1 AS `exhibition_title`,
 1 AS `theme`,
 1 AS `gallery_name`,
 1 AS `artwork_title`,
 1 AS `artist_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_workshop_booking_summary`
--

DROP TABLE IF EXISTS `vw_workshop_booking_summary`;
/*!50001 DROP VIEW IF EXISTS `vw_workshop_booking_summary`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_workshop_booking_summary` AS SELECT 
 1 AS `workshop_id`,
 1 AS `title`,
 1 AS `date`,
 1 AS `max_participants`,
 1 AS `total_bookings`,
 1 AS `paid_bookings`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `workshop`
--

DROP TABLE IF EXISTS `workshop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workshop` (
  `workshop_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `duration_minutes` int NOT NULL,
  `max_participants` int NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `instructor_artist_id` int NOT NULL,
  `location` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `level` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`workshop_id`),
  KEY `fk_workshop_artist` (`instructor_artist_id`),
  CONSTRAINT `fk_workshop_artist` FOREIGN KEY (`instructor_artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_workshop_duration` CHECK ((`duration_minutes` > 0)),
  CONSTRAINT `chk_workshop_level` CHECK ((`level` in (_utf8mb4'Beginner',_utf8mb4'Intermediate',_utf8mb4'Advanced'))),
  CONSTRAINT `chk_workshop_max_participants` CHECK ((`max_participants` > 0)),
  CONSTRAINT `chk_workshop_price` CHECK (((`price` is null) or (`price` >= 0)))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workshop`
--

LOCK TABLES `workshop` WRITE;
/*!40000 ALTER TABLE `workshop` DISABLE KEYS */;
INSERT INTO `workshop` VALUES (2,'Impressionist Landscapes','2026-04-19 14:00:00',150,20,120.00,2,'Metropolitan Hub','Painting landscapes with light and movement.','Beginner'),(3,'Sculpting Modernity','2026-04-24 09:30:00',210,12,200.00,5,'The British Gallery','Study of expressive sculpture techniques.','Advanced'),(4,'Creative Digital Layers','2026-05-03 11:00:00',160,18,90.00,6,'Modern Arts Center','Experimenting with digital and mixed-media workflows.','Intermediate'),(6,'Manoir Wayne','2026-05-06 18:23:00',45,2,15.00,12,'Gotham','cherche robin pour capture du joker','Beginner');
/*!40000 ALTER TABLE `workshop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workshop_registration`
--

DROP TABLE IF EXISTS `workshop_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workshop_registration` (
  `registration_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `workshop_id` int NOT NULL,
  `registration_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`registration_id`),
  UNIQUE KEY `unique_registration` (`member_id`,`workshop_id`),
  KEY `fk_registration_workshop` (`workshop_id`),
  CONSTRAINT `fk_registration_member` FOREIGN KEY (`member_id`) REFERENCES `community_member` (`member_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_registration_workshop` FOREIGN KEY (`workshop_id`) REFERENCES `workshop` (`workshop_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workshop_registration`
--

LOCK TABLES `workshop_registration` WRITE;
/*!40000 ALTER TABLE `workshop_registration` DISABLE KEYS */;
INSERT INTO `workshop_registration` VALUES (3,1,6,'2026-05-14 22:10:08'),(4,1,4,'2026-05-14 22:10:10'),(7,16,6,'2026-05-15 05:42:16'),(8,18,6,'2026-05-15 05:49:54');
/*!40000 ALTER TABLE `workshop_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `vw_artwork_details`
--

/*!50001 DROP VIEW IF EXISTS `vw_artwork_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_artwork_details` AS select `a`.`artwork_id` AS `artwork_id`,`a`.`title` AS `title`,`a`.`type` AS `type`,`a`.`medium` AS `medium`,`a`.`price` AS `price`,`a`.`status` AS `status`,`ar`.`name` AS `artist_name`,`ar`.`city` AS `artist_city` from (`artwork` `a` join `artist` `ar` on((`a`.`artist_id` = `ar`.`artist_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_exhibition_catalog`
--

/*!50001 DROP VIEW IF EXISTS `vw_exhibition_catalog`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_exhibition_catalog` AS select `e`.`exhibition_id` AS `exhibition_id`,`e`.`title` AS `exhibition_title`,`e`.`theme` AS `theme`,`g`.`name` AS `gallery_name`,`a`.`title` AS `artwork_title`,`ar`.`name` AS `artist_name` from ((((`exhibition` `e` join `gallery` `g` on((`e`.`gallery_id` = `g`.`gallery_id`))) join `exhibition_artwork` `ea` on((`e`.`exhibition_id` = `ea`.`exhibition_id`))) join `artwork` `a` on((`ea`.`artwork_id` = `a`.`artwork_id`))) join `artist` `ar` on((`a`.`artist_id` = `ar`.`artist_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_workshop_booking_summary`
--

/*!50001 DROP VIEW IF EXISTS `vw_workshop_booking_summary`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_workshop_booking_summary` AS select `w`.`workshop_id` AS `workshop_id`,`w`.`title` AS `title`,`w`.`date` AS `date`,`w`.`max_participants` AS `max_participants`,count(`b`.`booking_id`) AS `total_bookings`,sum((case when (`b`.`payment_status` = 'PAID') then 1 else 0 end)) AS `paid_bookings` from (`workshop` `w` left join `booking` `b` on((`w`.`workshop_id` = `b`.`workshop_id`))) group by `w`.`workshop_id`,`w`.`title`,`w`.`date`,`w`.`max_participants` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-17 17:24:07
