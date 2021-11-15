create database capstone;
use capstone;

CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  `access_level` varchar(45) DEFAULT NULL,
  UNIQUE KEY `role_id_UNIQUE` (`role_id`),
  UNIQUE KEY `access_level_UNIQUE` (`access_level`)
);

CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `project_description` varchar(100) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id_UNIQUE` (`project_id`)
);

CREATE TABLE `status` (
	`status_id` int NOT NULL AUTO_INCREMENT,
    `status` varchar(45),
    PRIMARY KEY (`status_id`)
);

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `role_id` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `role_id_idx` (`role_id`)
);

CREATE TABLE `log` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `log_id_UNIQUE` (`log_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `userID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ;

CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_id` int DEFAULT NULL,
  `start_time` timestamp default null,
  `total_hours` varchar(45) DEFAULT NULL,
  `status_id` int default 1,
  `project_id` int,
  `note` varchar(45) DEFAULT NULL,
  `task_description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `task_id_UNIQUE` (`task_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `project_id_idx` (`project_id`),
  CONSTRAINT `project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `status_id` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`)
);


INSERT INTO `log` VALUES (1,2,'added new project');
INSERT INTO `project` VALUES (1,'Capstone',NULL);
INSERT INTO `role` VALUES (1,'Admin','1'),(2,'User','2'),(3,'Observer ','3');
INSERT INTO `task` VALUES (1,'Testing',1,current_timestamp(),'2021-11-11 11:59:08',null,1,1,NULL);
INSERT INTO `user` VALUES (1,'Kelvin',1,'kelvin234@gmail.com','test123'),(2,'Martin',2,'martin655@gmail.com','test@123'),(3,'Harry',3,'harryjack@gmail.com','Test@54676');
INSERT INTO `status` VALUES (1, 'START'), (2 ,'PAUSED'), (3, 'INPROGRESS'), (4, 'COMPLETED');