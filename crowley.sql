/*
 Navicat Premium Data Transfer

 Source Server         : asd
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost
 Source Database       : crowley

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : utf-8

 Date: 02/11/2012 19:33:19 PM
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
INSERT INTO `fuseright` VALUES ('1', 'fuse_room_queue_defa'), ('2', 'fuse_buy_credits'), ('3', 'fuse_login'), ('4', 'default'), ('5', 'fuse_trade');
COMMIT;

-- ----------------------------
--  Table structure for `habbo`
-- ----------------------------
DROP TABLE IF EXISTS `habbo`;
CREATE TABLE `habbo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `figure` varchar(100) NOT NULL,
  `sso_ticket` varchar(100) NOT NULL,
  `sso_ip` varchar(15) NOT NULL,
  `sso_expires` datetime NOT NULL,
  `fuserank_id` int(11) NOT NULL,
  `motto` varchar(25) NOT NULL,
  `real_name` varchar(30) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `credits` int(11) NOT NULL DEFAULT '0',
  `activity_points` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `sso_ticket` (`sso_ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `habbo`
-- ----------------------------
BEGIN;
INSERT INTO `habbo` VALUES ('1', 'Crowley', 'lg-270-64.hr-165-32.ch-260-64.sh-300-64.hd-205-2', 'testTicket', '127.0.0.1', '2015-02-10 06:34:27', '1', 'Crowley FTW<3', 'CrossroadsCrowley', 'M', '0', '0'), ('2', 'Crossroads', 'lg-270-64.hr-165-32.ch-260-64.sh-300-64.hd-205-2', 'testTicket2', '127.0.0.1', '2015-02-10 06:34:27', '1', 'Crowley FTW<3', 'Crossroads', 'M', '0', '0');
COMMIT;

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
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `password_salt` varchar(255) NOT NULL,
  `signed_up` datetime NOT NULL,
  `registration_ip` varchar(15) NOT NULL,
  `current_ip` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'crowlie@hybridcore.net', 'asd', 'asd', '0000-00-00 00:00:00', '127.0.0.1', '127.0.0.1');
COMMIT;

-- ----------------------------
--  Table structure for `user_ban`
-- ----------------------------
DROP TABLE IF EXISTS `user_ban`;
CREATE TABLE `user_ban` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ban_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
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
