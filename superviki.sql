/*
Navicat MySQL Data Transfer

Source Server         : admin
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : superviki

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2016-11-18 16:25:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `affair`
-- ----------------------------
DROP TABLE IF EXISTS `affair`;
CREATE TABLE `affair` (
  `affair_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '事件主键ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '事件创建人',
  `affair_title` varchar(50) NOT NULL COMMENT '事件标题',
  `affair_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '事件类型',
  `affair_detail_id` int(11) unsigned NOT NULL COMMENT '事件文本类容',
  `affair_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '事件状态（优先级）',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `has_attachment` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有附属文件（0=没有，1=有）',
  `affair_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有拓展信息（0=没有，1=有）',
  PRIMARY KEY (`affair_id`),
  UNIQUE KEY `user_affair` (`user_id`,`affair_id`) USING BTREE,
  CONSTRAINT `a_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件信息表';

-- ----------------------------
-- Records of affair
-- ----------------------------

-- ----------------------------
-- Table structure for `affair_delete`
-- ----------------------------
DROP TABLE IF EXISTS `affair_delete`;
CREATE TABLE `affair_delete` (
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `affair_type` tinyint(3) unsigned NOT NULL COMMENT '事件类型',
  `affair_id` int(11) unsigned NOT NULL COMMENT '事件ID',
  PRIMARY KEY (`user_id`,`affair_type`,`affair_id`),
  KEY `ad_affair_id` (`affair_id`),
  CONSTRAINT `ad_affair_id` FOREIGN KEY (`affair_id`) REFERENCES `affair` (`affair_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ad_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回收站（用户删除事件表）';

-- ----------------------------
-- Records of affair_delete
-- ----------------------------

-- ----------------------------
-- Table structure for `affair_detail_normal`
-- ----------------------------
DROP TABLE IF EXISTS `affair_detail_normal`;
CREATE TABLE `affair_detail_normal` (
  `affair_detail_normal_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '事件详细信息主键ID',
  `theme_type` tinyint(3) unsigned NOT NULL COMMENT '事件类型',
  `detail_info` varchar(1000) NOT NULL COMMENT '事件详细内容',
  PRIMARY KEY (`affair_detail_normal_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='一般事件详细信息表';

-- ----------------------------
-- Records of affair_detail_normal
-- ----------------------------

-- ----------------------------
-- Table structure for `affair_detail_task`
-- ----------------------------
DROP TABLE IF EXISTS `affair_detail_task`;
CREATE TABLE `affair_detail_task` (
  `affair_detail_task_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务事件主键ID',
  `theme_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '任务主题类型',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '任务开始时间（前端提供）',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '任务结束时间（前端提供）',
  `task_participant` varchar(50) DEFAULT NULL COMMENT '任务参与者（待定）',
  `task_where` varchar(50) DEFAULT NULL COMMENT '任务地点',
  `detail_info` varchar(200) DEFAULT NULL COMMENT '任务详细描述',
  `caution_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '提醒时间（如果需要提醒）',
  `caution_type` tinyint(3) unsigned DEFAULT '0' COMMENT '提醒方式（如果需要提醒）',
  PRIMARY KEY (`affair_detail_task_id`),
  KEY `adt_time` (`end_time`,`start_time`,`theme_type`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='任务事件表';

-- ----------------------------
-- Records of affair_detail_task
-- ----------------------------

-- ----------------------------
-- Table structure for `attachment`
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `attachment_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `attachment_type` tinyint(3) unsigned NOT NULL COMMENT '附件所属类型（目前，用户相关或事件相关）',
  `file_type` tinyint(3) unsigned NOT NULL COMMENT '附件文件类型（文本，图片，音频等）',
  `master_id` int(10) unsigned NOT NULL COMMENT '所属相应类型ID',
  `attachment_url` varchar(50) NOT NULL COMMENT '文件存储url路径',
  PRIMARY KEY (`attachment_id`),
  KEY `a_master_id` (`master_id`,`attachment_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表（包含全部用户上传的除文字信息外的文件）';

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `uid` int(1) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('256');
INSERT INTO `test` VALUES ('123456789');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(20) NOT NULL COMMENT '用户账号',
  `user_password` varchar(32) NOT NULL COMMENT '用户密码',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `user_email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `nickname` varchar(20) NOT NULL COMMENT '用户昵称（初始时，默认为用户账号）',
  `style` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别（1=男，2=女，0=保密）',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
  `user_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT '拓展信息（1=有，0=没有）',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `user_phone` (`user_phone`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_log`
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `log_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'logID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `log_type` tinyint(3) unsigned NOT NULL COMMENT 'log类型（用户操作类型信息）',
  `log_detail` varchar(100) NOT NULL COMMENT '用户操作详细信息',
  `log_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT 'log拓展',
  PRIMARY KEY (`log_id`),
  KEY `ul_user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户操作信息表，保存用户操作历史信息';

-- ----------------------------
-- Records of user_log
-- ----------------------------

-- ----------------------------
-- Table structure for `user_login_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_login_info`;
CREATE TABLE `user_login_info` (
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `latest_ip` int(11) unsigned NOT NULL,
  `latest_login_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `latest_logout_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`user_id`),
  KEY `latest_login_time` (`latest_login_time`),
  CONSTRAINT `uli_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录信息记录表';

-- ----------------------------
-- Records of user_login_info
-- ----------------------------
