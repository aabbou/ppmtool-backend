CREATE DATABASE  IF NOT EXISTS `ppm_db`;
USE `ppm_db`;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(45) NOT NULL,
  `project_identifier` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `start_date` DATE DEFAULT NULL,
  `end_date` DATE DEFAULT NULL,
  `created_At` DATE DEFAULT NULL,
  `updated_At` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `T_PROJECT`
--

-- INSERT INTO `T_PROJECT` VALUES 
-- 	(1,'Leslie','Andrews','leslie@luv2code.com'),
-- 	(2,'Emma','Baumgarten','emma@luv2code.com'),
-- 	(3,'Avani','Gupta','avani@luv2code.com'),
-- 	(4,'Yuri','Petrov','yuri@luv2code.com'),
-- 	(5,'Juan','Vega','juan@luv2code.com');

