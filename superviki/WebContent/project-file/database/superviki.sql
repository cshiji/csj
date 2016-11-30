/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50519
Source Host           : 127.0.0.1:3306
Source Database       : superviki

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2016-11-30 10:46:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for affair
-- ----------------------------
DROP TABLE IF EXISTS `affair`;
CREATE TABLE `affair` (
  `affair_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '�¼�����ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '�¼�������',
  `affair_title` varchar(50) NOT NULL COMMENT '�¼�����',
  `affair_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '�¼�����',
  `affair_detail_id` int(11) unsigned NOT NULL COMMENT '�¼��ı�����',
  `affair_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '�¼�״̬�����ȼ���',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `has_attachment` bit(1) NOT NULL DEFAULT b'0' COMMENT '�Ƿ��и����ļ���0=û�У�1=�У�',
  `affair_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT '�Ƿ�����չ��Ϣ��0=û�У�1=�У�',
  PRIMARY KEY (`affair_id`),
  UNIQUE KEY `user_affair` (`user_id`,`affair_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�¼���Ϣ��';

-- ----------------------------
-- Records of affair
-- ----------------------------

-- ----------------------------
-- Table structure for affair_delete
-- ----------------------------
DROP TABLE IF EXISTS `affair_delete`;
CREATE TABLE `affair_delete` (
  `user_id` int(11) unsigned NOT NULL COMMENT '�û�ID',
  `affair_type` tinyint(3) unsigned NOT NULL COMMENT '�¼�����',
  `affair_id` int(11) unsigned NOT NULL COMMENT '�¼�ID',
  PRIMARY KEY (`user_id`,`affair_type`,`affair_id`),
  KEY `ad_affair_id` (`affair_id`),
  CONSTRAINT `ad_affair_id` FOREIGN KEY (`affair_id`) REFERENCES `affair` (`affair_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ad_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����վ���û�ɾ���¼����';

-- ----------------------------
-- Records of affair_delete
-- ----------------------------

-- ----------------------------
-- Table structure for affair_detail_normal
-- ----------------------------
DROP TABLE IF EXISTS `affair_detail_normal`;
CREATE TABLE `affair_detail_normal` (
  `affair_detail_normal_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '�¼���ϸ��Ϣ����ID',
  `theme_type` tinyint(3) unsigned NOT NULL COMMENT '�¼�����',
  `detail_info` varchar(1000) NOT NULL COMMENT '�¼���ϸ����',
  PRIMARY KEY (`affair_detail_normal_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='һ���¼���ϸ��Ϣ��';

-- ----------------------------
-- Records of affair_detail_normal
-- ----------------------------

-- ----------------------------
-- Table structure for affair_detail_task
-- ----------------------------
DROP TABLE IF EXISTS `affair_detail_task`;
CREATE TABLE `affair_detail_task` (
  `affair_detail_task_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '�����¼�����ID',
  `theme_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '������������',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʼʱ�䣨ǰ���ṩ��',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '�������ʱ�䣨ǰ���ṩ��',
  `task_participant` varchar(50) DEFAULT NULL COMMENT '��������ߣ�������',
  `task_where` varchar(50) DEFAULT NULL COMMENT '����ص�',
  `detail_info` varchar(200) DEFAULT NULL COMMENT '������ϸ����',
  `caution_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ�䣨�����Ҫ���ѣ�',
  `caution_type` tinyint(3) unsigned DEFAULT '0' COMMENT '���ѷ�ʽ�������Ҫ���ѣ�',
  PRIMARY KEY (`affair_detail_task_id`),
  KEY `adt_time` (`end_time`,`start_time`,`theme_type`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='�����¼���';

-- ----------------------------
-- Records of affair_detail_task
-- ----------------------------

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `attachment_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `attachment_type` tinyint(3) unsigned NOT NULL COMMENT '�����������ͣ�Ŀǰ���û���ػ��¼���أ�',
  `file_type` tinyint(3) unsigned NOT NULL COMMENT '�����ļ����ͣ��ı���ͼƬ����Ƶ�ȣ�',
  `master_id` int(10) unsigned NOT NULL COMMENT '������Ӧ����ID',
  `attachment_url` varchar(50) NOT NULL COMMENT '�ļ��洢url·��',
  PRIMARY KEY (`attachment_id`),
  KEY `a_master_id` (`master_id`,`attachment_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�����������ȫ���û��ϴ��ĳ�������Ϣ����ļ���';

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stu_id` int(11) NOT NULL DEFAULT '0',
  `stu_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('5', '王麻子4', '20', '2016-11-21', '444@qq.com', '四川德阳', null);
INSERT INTO `student` VALUES ('6', '王麻子5', '20', '2016-11-21', '444@qq.com', '四川德阳', null);
INSERT INTO `student` VALUES ('7', '王麻子6', '20', '2016-11-21', '444@qq.com', '四川德阳', null);

-- ----------------------------
-- Table structure for sys_code_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_code_info`;
CREATE TABLE `sys_code_info` (
  `sys_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_code_name` varchar(255) NOT NULL,
  `sys_code_type` varchar(255) NOT NULL,
  `sys_code_value` varchar(255) NOT NULL,
  PRIMARY KEY (`sys_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_code_info
-- ----------------------------

-- ----------------------------
-- Table structure for test
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
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `user_name` varchar(20) NOT NULL COMMENT '�û��˺�',
  `user_password` varchar(32) NOT NULL COMMENT '�û�����',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '�û��绰',
  `user_email` varchar(50) DEFAULT NULL COMMENT '�û�����',
  `nickname` varchar(20) NOT NULL COMMENT '�û��ǳƣ���ʼʱ��Ĭ��Ϊ�û��˺ţ�',
  `style` varchar(255) DEFAULT NULL COMMENT '�û�ͷ��',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '�Ա�1=�У�2=Ů��0=���ܣ�',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '�������ʱ��',
  `user_type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '��ͨ�û���ϵͳ�û���0��1��',
  `user_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT '��չ��Ϣ��1=�У�0=û�У�',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `user_phone` (`user_phone`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�û���Ϣ��';

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `log_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'logID',
  `user_id` varchar(28) NOT NULL,
  `log_type` tinyint(3) unsigned NOT NULL COMMENT 'log���ͣ��û�����������Ϣ��',
  `log_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT 'logʱ�䣨����д��ʱ�䣩',
  `log_detail` varchar(100) NOT NULL COMMENT '�û�������ϸ��Ϣ',
  `log_extend` bit(1) NOT NULL DEFAULT b'0' COMMENT 'log��չ',
  PRIMARY KEY (`log_id`),
  KEY `ul_user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='�û�������Ϣ��������û�������ʷ��Ϣ';

-- ----------------------------
-- Records of user_log
-- ----------------------------
