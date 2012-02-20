/*
 Navicat Premium Data Transfer

 Source Server         : asd
 Source Server Type    : MySQL
 Source Server Version : 50519
 Source Host           : localhost
 Source Database       : crowley

 Target Server Type    : MySQL
 Target Server Version : 50519
 File Encoding         : utf-8

 Date: 02/20/2012 04:29:56 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ban`
-- ----------------------------
DROP TABLE IF EXISTS `ban`;
CREATE TABLE `ban` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(100) NOT NULL,
  `created_at` datetime NOT NULL,
  `expires_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fuserank`
-- ----------------------------
DROP TABLE IF EXISTS `fuserank`;
CREATE TABLE `fuserank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `fuserank`
-- ----------------------------
BEGIN;
INSERT INTO `fuserank` VALUES ('1', 'User');
COMMIT;

-- ----------------------------
--  Table structure for `fuserank_fuseright`
-- ----------------------------
DROP TABLE IF EXISTS `fuserank_fuseright`;
CREATE TABLE `fuserank_fuseright` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fuserank_id` int(11) NOT NULL,
  `fuseright_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `fuserank_fuseright`
-- ----------------------------
BEGIN;
INSERT INTO `fuserank_fuseright` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3'), ('4', '1', '4'), ('5', '1', '5');
COMMIT;

-- ----------------------------
--  Table structure for `fuseright`
-- ----------------------------
DROP TABLE IF EXISTS `fuseright`;
CREATE TABLE `fuseright` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `right` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `fuseright`
-- ----------------------------
BEGIN;
INSERT INTO `fuseright` VALUES ('1', 'fuse_room_queue_default'), ('2', 'fuse_buy_credits'), ('3', 'fuse_login'), ('4', 'default'), ('5', 'fuse_trade');
COMMIT;

-- ----------------------------
--  Table structure for `habbo`
-- ----------------------------
DROP TABLE IF EXISTS `habbo`;
CREATE TABLE `habbo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `figure` varchar(100) NOT NULL,
  `sso_ticket` varchar(100) DEFAULT NULL,
  `sso_ip` varchar(15) DEFAULT NULL,
  `sso_expires` datetime DEFAULT NULL,
  `fuserank_id` int(11) NOT NULL DEFAULT '1',
  `motto` varchar(25) NOT NULL,
  `real_name` varchar(30) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `credits` int(11) NOT NULL DEFAULT '0',
  `activity_points` int(11) NOT NULL DEFAULT '0',
  `sound_preference` int(3) NOT NULL DEFAULT '100',
  `last_online` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `sso_ticket` (`sso_ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `habbo`
-- ----------------------------
BEGIN;
INSERT INTO `habbo` VALUES ('1', 'Crowley', 'lg-270-64.hr-165-32.ch-260-64.sh-300-64.hd-205-2', 'testTicket', '127.0.0.1', '2015-02-10 06:34:27', '1', 'Crowley FTW<3', 'CrossroadsCrowley', 'M', '0', '0', '100', '0000-00-00 00:00:00'), ('2', 'Crossroads', 'lg-270-64.hr-165-32.ch-260-64.sh-300-64.hd-205-2', 'testTicket2', '127.0.0.1', '2015-02-10 06:34:27', '1', 'Crowley FTW<3', 'Crossroads', 'M', '0', '0', '100', '0000-00-00 00:00:00');
COMMIT;

-- ----------------------------
--  Table structure for `habbo_ban`
-- ----------------------------
DROP TABLE IF EXISTS `habbo_ban`;
CREATE TABLE `habbo_ban` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `habbo_id` int(11) NOT NULL,
  `ban_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `habbo_friend`
-- ----------------------------
DROP TABLE IF EXISTS `habbo_friend`;
CREATE TABLE `habbo_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `habbo_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `habbo_friend`
-- ----------------------------
BEGIN;
INSERT INTO `habbo_friend` VALUES ('1', '1', '2'), ('2', '2', '1');
COMMIT;

-- ----------------------------
--  Table structure for `habbo_friend_request`
-- ----------------------------
DROP TABLE IF EXISTS `habbo_friend_request`;
CREATE TABLE `habbo_friend_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `habbo_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `schema_migrations`
-- ----------------------------
DROP TABLE IF EXISTS `schema_migrations`;
CREATE TABLE `schema_migrations` (
  `version` varchar(255) NOT NULL,
  UNIQUE KEY `unique_schema_migrations` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `schema_migrations`
-- ----------------------------
BEGIN;
INSERT INTO `schema_migrations` VALUES ('20120213035736');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL DEFAULT '',
  `encrypted_password` varchar(128) NOT NULL DEFAULT '',
  `reset_password_token` varchar(255) DEFAULT NULL,
  `reset_password_sent_at` datetime DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int(11) DEFAULT '0',
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) DEFAULT NULL,
  `last_sign_in_ip` varchar(255) DEFAULT NULL,
  `password_salt` varchar(255) DEFAULT NULL,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `confirmed_at` datetime DEFAULT NULL,
  `confirmation_sent_at` datetime DEFAULT NULL,
  `failed_attempts` int(11) DEFAULT '0',
  `unlock_token` varchar(255) DEFAULT NULL,
  `locked_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_on_email` (`email`),
  UNIQUE KEY `index_user_on_reset_password_token` (`reset_password_token`),
  UNIQUE KEY `index_user_on_confirmation_token` (`confirmation_token`),
  UNIQUE KEY `index_user_on_unlock_token` (`unlock_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_habbo`
-- ----------------------------
DROP TABLE IF EXISTS `user_habbo`;
CREATE TABLE `user_habbo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `habbo_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
