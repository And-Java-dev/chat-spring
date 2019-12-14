/*
SQLyog Professional v13.1.1 (64 bit)
MySQL - 8.0.18 : Database - chat_spring
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chat_spring` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `chat_spring`;

/*Table structure for table `invite` */

DROP TABLE IF EXISTS `invite`;

CREATE TABLE `invite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_date` datetime(6) NOT NULL,
  `status` enum('PENDING','ACCEPT','DENIED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PENDING',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `from_id` int(11) NOT NULL,
  `to_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK23cdmbo3k6rt31d712llxnypb` (`to_id`),
  KEY `FKjpm2g29ggaedh158k1p6e3n94` (`from_id`),
  CONSTRAINT `FK23cdmbo3k6rt31d712llxnypb` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKjpm2g29ggaedh158k1p6e3n94` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `invite` */

/*Table structure for table `massage` */

DROP TABLE IF EXISTS `massage`;

CREATE TABLE `massage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6uublcoub16btkrl9rq7dk56l` (`from_id`),
  KEY `FK5pimuohn4qk2erqmtwptyqc7f` (`to_id`),
  CONSTRAINT `FK5pimuohn4qk2erqmtwptyqc7f` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK6uublcoub16btkrl9rq7dk56l` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `massage` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_path` varchar(255) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` enum('ADMIN','USER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'USER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

/*Table structure for table `user_friends` */

DROP TABLE IF EXISTS `user_friends`;

CREATE TABLE `user_friends` (
  `user_id` int(11) NOT NULL,
  `friends_id` int(11) NOT NULL,
  KEY `FK9i7cldnk7cx2g47qex8ovm2ah` (`user_id`),
  KEY `FKh8stk1uq1nmm88tjeinj18ndc` (`friends_id`),
  CONSTRAINT `FK9i7cldnk7cx2g47qex8ovm2ah` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKe1jhkryq5denjdrjngi7lj9h4` FOREIGN KEY (`friends_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_friends` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
