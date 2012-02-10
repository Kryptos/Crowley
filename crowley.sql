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

 Date: 02/10/2012 07:18:15 AM
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
--  Table structure for `fuseright`
-- ----------------------------
DROP TABLE IF EXISTS `fuseright`;
CREATE TABLE `fuseright` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `right` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `sso_ticket` (`sso_ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

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
