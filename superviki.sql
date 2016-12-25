/*
Navicat MySQL Data Transfer

Source Server         : admin
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : superviki

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2016-12-25 12:42:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for affair
-- ----------------------------
DROP TABLE IF EXISTS `affair`;
CREATE TABLE `affair` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '事件主键ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '事件创建人',
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '事件标题',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '事件类型',
  `detail_id` int(11) unsigned NOT NULL COMMENT '事件文本类容',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '事件状态（优先级）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `has_attachment` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有附属文件（0=没有，1=有）',
  `affair_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有拓展信息（0=没有，1=有）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='事件信息表';

-- ----------------------------
-- Table structure for affair_delete
-- ----------------------------
DROP TABLE IF EXISTS `affair_delete`;
CREATE TABLE `affair_delete` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `affair_type` tinyint(3) unsigned NOT NULL COMMENT '事件类型',
  `affair_id` int(11) unsigned NOT NULL COMMENT '事件ID',
  `deltet_time` datetime NOT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回收站（用户删除事件表）';

-- ----------------------------
-- Table structure for affair_detail_normal
-- ----------------------------
DROP TABLE IF EXISTS `affair_detail_normal`;
CREATE TABLE `affair_detail_normal` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '事件详细信息主键ID',
  `theme_type` tinyint(3) unsigned NOT NULL COMMENT '事件类型',
  `detail_info` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '事件详细内容',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='一般事件详细信息表';

-- ----------------------------
-- Table structure for affair_detail_task
-- ----------------------------
DROP TABLE IF EXISTS `affair_detail_task`;
CREATE TABLE `affair_detail_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务事件主键ID',
  `theme_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '任务主题类型',
  `start_time` datetime NOT NULL COMMENT '任务开始时间（前端提供）',
  `end_time` datetime NOT NULL COMMENT '任务结束时间（前端提供）',
  `task_participant` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '任务参与者（待定）',
  `task_where` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '任务地点',
  `detail_info` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '任务详细描述',
  `caution_time` datetime DEFAULT NULL COMMENT '提醒时间（如果需要提醒）',
  `caution_type` tinyint(3) unsigned DEFAULT '0' COMMENT '提醒方式（如果需要提醒）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='任务事件表';

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '附件所属类型（目前，用户相关或事件相关）',
  `file_type` tinyint(3) unsigned NOT NULL COMMENT '附件文件类型（文本，图片，音频等）',
  `master_id` int(11) unsigned NOT NULL COMMENT '所属相应类型ID',
  `attachment_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件存储url路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `a_master_id` (`master_id`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表（包含全部用户上传的除文字信息外的文件）';

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `uid` int(1) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户邮箱',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户昵称（初始时，默认为用户账号）',
  `style` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别（1=男，2=女，0=保密）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`account`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `user_phone` (`phone`),
  UNIQUE KEY `user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for user_class
-- ----------------------------
DROP TABLE IF EXISTS `user_class`;
CREATE TABLE `user_class` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `user_type` tinyint(3) NOT NULL DEFAULT '2' COMMENT '用户类型（1 = 系统用户， 2 = 普通用户）',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '注册IP地址',
  `register_info` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '注册附加信息（JSON）',
  `last_login_time` datetime NOT NULL COMMENT '最近一次登录时间',
  `last_login_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '最近一次登录IP',
  `last_login_info` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '最近一次登录附件信息（JSON）',
  `update_time` datetime NOT NULL COMMENT '信息更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='用户类型表';

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `log_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'logID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `user_type` tinyint(3) unsigned NOT NULL COMMENT '用户类型',
  `log_type` tinyint(3) unsigned NOT NULL COMMENT 'log类型（用户操作类型信息）',
  `log_detail` varchar(100) NOT NULL COMMENT '用户操作详细信息',
  `log_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT 'log拓展',
  `log_time` datetime NOT NULL COMMENT 'log时间',
  PRIMARY KEY (`log_id`),
  KEY `ul_user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户主要操作信息表，保存用户主要操作历史信息';
