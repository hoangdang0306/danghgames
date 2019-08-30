DROP TABLE IF EXISTS world_info; 
CREATE TABLE `world_info` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `event_flag` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS account_info; 
CREATE TABLE `account_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS player_info; 
CREATE TABLE `player_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `gold` int(10) unsigned zerofill NOT NULL,
  `battle_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS card_info; 
CREATE TABLE `card_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` int(10) unsigned DEFAULT NULL,
  `card_base_id` int(11) DEFAULT NULL,
  `confident` smallint(6) DEFAULT NULL,
  `knowledge` smallint(6) DEFAULT NULL,
  `communication` smallint(6) DEFAULT NULL,
  `stamina` smallint(6) DEFAULT NULL,
  `leadership` smallint(6) DEFAULT NULL,
  `salary` smallint(6) DEFAULT NULL,
  `experience` smallint(6) DEFAULT NULL,
  `skill_1` smallint(6) DEFAULT NULL,
  `skill_2` smallint(6) DEFAULT NULL,
  `skill_3` smallint(6) DEFAULT NULL,
  `employee_level` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


