-- MySQL dump 10.13  Distrib 5.7.19, for Win32 (AMD64)
--
-- Host: localhost    Database: luoxi
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `CALENDAR_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `CRON_EXPRESSION` varchar(120) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8_bin DEFAULT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) COLLATE utf8_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sql_log`
--

DROP TABLE IF EXISTS `sql_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sql_log` (
  `name` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `content` text COLLATE utf8_bin,
  `code` int(10) DEFAULT NULL,
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin` (
  `admin_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '管理员id',
  `username` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nick_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `province_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city_id` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `county_id` varchar(6) COLLATE utf8_bin DEFAULT 'CHN' COMMENT '县',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `gender` enum('0','1','2') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '性别-0女/1男/2保密',
  `phone_code` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '手机国家区域号',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `age` double(3,0) unsigned DEFAULT NULL COMMENT '年龄',
  `hobby` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好-多个用/分割',
  `head_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像路径',
  `last_login_time` timestamp(6) NULL DEFAULT NULL COMMENT '最后登录时间',
  `enable_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态-0禁用/1启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_app`
--

DROP TABLE IF EXISTS `t_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app` (
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '渠道id',
  `app_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '产品名称',
  `package_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `auth_type` enum('MAC','SN','MACSN') COLLATE utf8_bin NOT NULL DEFAULT 'MACSN' COMMENT '认证方式',
  `app_type` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '软件类型-0APP/1固件',
  `instruction` text COLLATE utf8_bin NOT NULL COMMENT '产品说明',
  `enable_sn` bit(1) NOT NULL DEFAULT b'1' COMMENT '启动激活码激活',
  `app_key` varchar(32) COLLATE utf8_bin NOT NULL,
  `app_secret` varchar(32) COLLATE utf8_bin NOT NULL,
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_app_earnings_setting`
--

DROP TABLE IF EXISTS `t_app_earnings_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_earnings_setting` (
  `app_earnings_setting_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品收益设置id',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `third_business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '第三方内容商id',
  `settlement_ratio` double(5,2) NOT NULL DEFAULT '100.00' COMMENT '结算比率%',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`app_earnings_setting_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='产品收益设置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_app_version`
--

DROP TABLE IF EXISTS `t_app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_version` (
  `version_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '版本id',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本号',
  `version_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '版本名称',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `instruction` text COLLATE utf8_bin NOT NULL COMMENT '更新说明',
  `file_size` decimal(19,2) DEFAULT NULL COMMENT '文件大小-单位b',
  `md5` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5值',
  `version_type` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '版本类型-0全量包/1拆分包',
  `url` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '文件地址',
  `url_type` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '文件地址类型-0内部/1外部',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`version_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='产品版本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_app_version_module`
--

DROP TABLE IF EXISTS `t_app_version_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_version_module` (
  `version_module_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '版本模块id',
  `version_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '版本id',
  `module_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '模块名称',
  `call_type` enum('SDK','APK','MP4') COLLATE utf8_bin NOT NULL DEFAULT 'APK' COMMENT '调用类型',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `module_icon` text COLLATE utf8_bin COMMENT '图标',
  `package_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '调用包名',
  `class_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '调用类名',
  `parameter` json DEFAULT NULL COMMENT '参数',
  `business_app_ids` text COLLATE utf8_bin COMMENT '内容商产品id逗号分隔',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '序号',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`version_module_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='版本模块';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_admin_role`
--

DROP TABLE IF EXISTS `t_auth_admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_admin_role` (
  `admin_role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户角色id',
  `admin_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '管理员id或渠道id',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  PRIMARY KEY (`admin_role_id`) USING BTREE,
  UNIQUE KEY `admin_id` (`admin_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_permission`
--

DROP TABLE IF EXISTS `t_auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_permission` (
  `permission_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '权限id',
  `permission_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限名称',
  `permission_type` enum('MODULE','MENU','BTN','FN') COLLATE utf8_bin NOT NULL COMMENT '权限类型',
  `permission_tag` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `apis` text COLLATE utf8_bin NOT NULL COMMENT '多个用逗号分隔',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '序号',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'icon',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_role`
--

DROP TABLE IF EXISTS `t_auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_role` (
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `role_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_type` enum('ADMIN','CHANNEL') COLLATE utf8_bin NOT NULL DEFAULT 'ADMIN' COMMENT '角色类型-ADMIN:平台/CHANNEL:渠道',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_auth_role_permission`
--

DROP TABLE IF EXISTS `t_auth_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_auth_role_permission` (
  `role_permission_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色权限id',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `permission_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_permission_id`) USING BTREE,
  UNIQUE KEY `role_id` (`role_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_book`
--

DROP TABLE IF EXISTS `t_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_book` (
  `book_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `book_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '课本名称',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科id',
  `press_id` int(11) DEFAULT NULL COMMENT '出版社id',
  `grade_id` int(11) DEFAULT NULL COMMENT '年级id',
  `volume` enum('A','B') COLLATE utf8_bin DEFAULT NULL COMMENT '册数-A上册/B下册',
  `detail` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '内容介绍',
  `cover_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '封面地址',
  `zip_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '压缩文件地址',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='课本';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_brand`
--

DROP TABLE IF EXISTS `t_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_brand` (
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id-BD1000',
  `brand_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '品牌名称-唯一',
  `source` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '来源-0导入/1记录',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`brand_id`) USING BTREE,
  UNIQUE KEY `brand_name` (`brand_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='品牌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_business`
--

DROP TABLE IF EXISTS `t_business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_business` (
  `business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商id',
  `business_code` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '内容商代码',
  `business_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '内容商名称',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='内容商';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_business_app`
--

DROP TABLE IF EXISTS `t_business_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_business_app` (
  `business_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商产品id',
  `business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商id',
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '产品名称',
  `package_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本号',
  `app_url` text COLLATE utf8_bin NOT NULL COMMENT '产品地址',
  `instruction` text COLLATE utf8_bin COMMENT '更新说明',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`business_app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='内容商产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_business_app_content`
--

DROP TABLE IF EXISTS `t_business_app_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_business_app_content` (
  `business_app_content_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商产品内容id',
  `business_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商产品id',
  `business_content_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商内容id',
  PRIMARY KEY (`business_app_content_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='内容商产品内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_business_content`
--

DROP TABLE IF EXISTS `t_business_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_business_content` (
  `business_content_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商内容id',
  `content_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '内容名称',
  `business_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '内容商id',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `phase` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '所属阶段',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`business_content_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='内容商内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_business_serial_number`
--

DROP TABLE IF EXISTS `t_business_serial_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_business_serial_number` (
  `business_serial_number_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商SN码id',
  `business_sn_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '内容商SN码',
  `call_type` enum('SDK','APK') COLLATE utf8_bin NOT NULL DEFAULT 'APK' COMMENT '调用类型',
  `max_use_number` decimal(1,0) unsigned NOT NULL DEFAULT '1' COMMENT '最大使用次数',
  `use_number` decimal(1,0) unsigned NOT NULL DEFAULT '0' COMMENT '使用次数',
  `use_status` enum('NO_USE','USEING','SUCCESS','FAIL') COLLATE utf8_bin NOT NULL DEFAULT 'NO_USE' COMMENT '使用状态-NO_USE/USEING/SUCCESS/FAIL',
  `business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商id',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`business_serial_number_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='内容商SN码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_channel`
--

DROP TABLE IF EXISTS `t_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_channel` (
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id-CH1000',
  `channel_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '渠道名称-唯一',
  `username` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `password` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='渠道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_channel_earnings`
--

DROP TABLE IF EXISTS `t_channel_earnings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_channel_earnings` (
  `channel_earnings_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道收益id',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id',
  `month` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '月份',
  `order_pay_total_amount` decimal(11,2) unsigned NOT NULL COMMENT '订单支付总额',
  `original_earnings_total_amount` decimal(11,2) unsigned NOT NULL COMMENT '原始收益总额',
  `actual_earnings_total_amount` decimal(11,2) unsigned NOT NULL COMMENT '实际收益总额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`channel_earnings_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='渠道收益';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_channel_earnings_detail`
--

DROP TABLE IF EXISTS `t_channel_earnings_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_channel_earnings_detail` (
  `channel_earnings_detail_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道收益明细id',
  `channel_earnings_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道收益id',
  `third_business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '第三方内容商id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `order_number` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `order_pay_time` datetime NOT NULL COMMENT '订单支付时间',
  `order_pay_amount` decimal(11,2) unsigned NOT NULL COMMENT '订单支付金额',
  `original_earnings_amount` decimal(11,2) unsigned NOT NULL COMMENT '原始收益金额',
  `settlement_ratio` double(5,2) NOT NULL COMMENT '结算比率',
  `actual_earnings_amount` decimal(11,2) unsigned NOT NULL COMMENT '实际收益金额',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`channel_earnings_detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='产品收益明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_device`
--

DROP TABLE IF EXISTS `t_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device` (
  `device_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `device_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '设备名称',
  `wired_mac` varchar(17) CHARACTER SET utf8 DEFAULT NULL COMMENT '有线mac',
  `wireless_mac` varchar(17) CHARACTER SET utf8 DEFAULT NULL COMMENT '无线mac',
  `imei` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT 'IMEI号',
  `imei2` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT 'IMEI2号',
  `bluetooth` varchar(17) COLLATE utf8_bin DEFAULT NULL COMMENT '蓝牙',
  `serial_number` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '终端序列号',
  `uuid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '终端uuid',
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `model_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '型号id',
  `firmware_info` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '固件信息',
  `source` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '来源-0导入/1记录',
  `enable_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态-0禁用/1启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`device_id`) USING BTREE,
  KEY `brand_id` (`brand_id`) USING BTREE,
  KEY `model_id` (`model_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='设备';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_device_app`
--

DROP TABLE IF EXISTS `t_device_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device_app` (
  `device_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `device_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '设备id',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `sn_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'SN码',
  `activate_time` timestamp(6) NULL DEFAULT NULL COMMENT '激活时间',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`device_app_id`) USING BTREE,
  KEY `device_id` (`device_id`) USING BTREE,
  KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='设备产品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_device_app_business_sn_code`
--

DROP TABLE IF EXISTS `t_device_app_business_sn_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device_app_business_sn_code` (
  `union_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `device_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '设备产品id',
  `business_serial_number_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '内容商SN码id',
  `use_status` enum('SUCCESS','FAIL') COLLATE utf8_bin NOT NULL COMMENT '使用状态-SUCCESS/FAIL',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`union_id`) USING BTREE,
  KEY `device_app_id` (`device_app_id`) USING BTREE,
  KEY `business_serial_number_id` (`business_serial_number_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='设备产品与内容商激活码使用记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_edu_directory`
--

DROP TABLE IF EXISTS `t_edu_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_edu_directory` (
  `directory_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '目录id',
  `directory_type` enum('CATEGORY','ALBUM','THEME','ABILITY') COLLATE utf8_bin NOT NULL COMMENT '目录类型',
  `directory_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '目录名称',
  `introduction` text COLLATE utf8_bin COMMENT '简介',
  `cover_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '封面图',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `sort` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '序号',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`directory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学习目录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_edu_directory_resource`
--

DROP TABLE IF EXISTS `t_edu_directory_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_edu_directory_resource` (
  `directory_resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '目录资源id',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `directory_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '目录id',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`directory_resource_id`) USING BTREE,
  KEY `directory_id` (`directory_id`) USING BTREE,
  KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='目录资源';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_edu_resource`
--

DROP TABLE IF EXISTS `t_edu_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_edu_resource` (
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `resource_title` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '资源标题',
  `introduction` text CHARACTER SET utf8 COMMENT '简介',
  `content` text CHARACTER SET utf8 COMMENT '内容',
  `business_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '内容商id',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级id',
  `third_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '第三方id',
  `resource_type` enum('RESOURCE','PAGE') COLLATE utf8_bin DEFAULT NULL COMMENT '资源类型',
  `media_type` enum('AUDIO','VIDEO','IMGBOOK') COLLATE utf8_bin DEFAULT NULL COMMENT '媒体类型',
  `imgbook_type` enum('AMT','ETC','LVRD','GAME') COLLATE utf8_bin DEFAULT NULL COMMENT '绘本类型-AMT动画/ETC电子/LVRD分级阅读/GAME游戏',
  `cover_url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '封面图',
  `handle_cover_url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '处理封面图',
  `file_url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '文件',
  `handle_file_url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '处理文件',
  `tags` text COLLATE utf8_bin COMMENT '标签-逗号分隔',
  `scenario` text COLLATE utf8_bin COMMENT '场景-逗号分隔',
  `sex` enum('BOY','GIRL') COLLATE utf8_bin DEFAULT NULL COMMENT '适用性别',
  `language` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '语言-逗号分隔',
  `min_age` int(11) unsigned DEFAULT NULL COMMENT '最小适合年龄',
  `max_age` int(11) unsigned DEFAULT NULL COMMENT '最大适用年龄',
  `phase` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '所属阶段',
  `total` int(11) unsigned DEFAULT NULL COMMENT '总集数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`resource_id`) USING BTREE,
  KEY `business_id` (`business_id`) USING BTREE,
  KEY `parent_id` (`parent_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学习资源';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_edu_timetable`
--

DROP TABLE IF EXISTS `t_edu_timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_edu_timetable` (
  `timetable_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '课程表id',
  `age` double(3,0) unsigned NOT NULL COMMENT '年龄',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `content` text COLLATE utf8_bin COMMENT '内容',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`timetable_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='课程表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_edu_timetable_resource`
--

DROP TABLE IF EXISTS `t_edu_timetable_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_edu_timetable_resource` (
  `timetable_resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '课程表资源id',
  `timetable_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '课程表id',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`timetable_resource_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='课程表资源';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_family_member`
--

DROP TABLE IF EXISTS `t_family_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_family_member` (
  `family_member_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '家庭成员id',
  `is_manager` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有管理权限-0否/1是',
  `is_face` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否人脸识别-0否/1是',
  `relation_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '关系名称',
  `nick_name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `head_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `gender` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '性别-0女/1男',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `age` double(3,0) unsigned DEFAULT NULL COMMENT '年龄',
  `hobby` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好-/分隔',
  `education` enum('0','1','2') COLLATE utf8_bin DEFAULT NULL COMMENT '学校-0幼儿园/1小学',
  `grade` enum('1','2','3','4','5','6','7','8','9','10','11','12') COLLATE utf8_bin DEFAULT NULL COMMENT '年级-1/2/3/4/5/6/7/8/9/10/11/12',
  `join_user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '关联用户id',
  `reward_number` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '奖励数',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`family_member_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='家庭成员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_feedback`
--

DROP TABLE IF EXISTS `t_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_feedback` (
  `feedback_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '反馈id',
  `package_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '内容',
  `qq` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'qq',
  `vx` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'vx',
  `tel` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'tel',
  `feedback_option_content1` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项1',
  `feedback_option_content2` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项2',
  `uuid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '终端序列号UUID',
  `mac` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'MAC地址',
  `brand_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `model_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '型号',
  `process_status` enum('NO','YES') COLLATE utf8_bin NOT NULL DEFAULT 'NO' COMMENT '处理状态',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`feedback_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='反馈';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_feedback_option`
--

DROP TABLE IF EXISTS `t_feedback_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_feedback_option` (
  `feedback_option_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '反馈选项id',
  `feedback_option_content` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '反馈选项内容',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '父级内容',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`feedback_option_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='反馈选项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_fm_browse`
--

DROP TABLE IF EXISTS `t_fm_browse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fm_browse` (
  `fm_browse_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '浏览id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `ratio` double(3,0) unsigned NOT NULL DEFAULT '0' COMMENT '播放占比%',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`fm_browse_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='浏览';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_fm_collect`
--

DROP TABLE IF EXISTS `t_fm_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fm_collect` (
  `fm_collect_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '收藏id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`fm_collect_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_fm_diary`
--

DROP TABLE IF EXISTS `t_fm_diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fm_diary` (
  `fm_diary_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '日记id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '内容',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`fm_diary_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='日记';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_fm_reward`
--

DROP TABLE IF EXISTS `t_fm_reward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fm_reward` (
  `fm_reward_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '奖励id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `reward_number` double(3,0) unsigned NOT NULL COMMENT '奖励数量',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`fm_reward_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='星星奖励记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_fm_standard`
--

DROP TABLE IF EXISTS `t_fm_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fm_standard` (
  `fm_standard_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `standard_type` enum('HEIGHT','WEIGHT') COLLATE utf8_bin NOT NULL,
  `sex` enum('GIRL','BOY') COLLATE utf8_bin NOT NULL COMMENT '性别',
  `month_age` int(10) unsigned NOT NULL COMMENT '月龄',
  `sd3_` double(10,2) unsigned NOT NULL,
  `sd2_` double(10,2) unsigned NOT NULL,
  `sd1_` double(10,2) unsigned NOT NULL,
  `center` double(10,2) unsigned NOT NULL,
  `sd1` double(10,2) unsigned NOT NULL,
  `sd2` double(10,2) unsigned NOT NULL,
  `sd3` double(10,2) unsigned NOT NULL,
  PRIMARY KEY (`fm_standard_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='身高体重';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_fm_vaccine`
--

DROP TABLE IF EXISTS `t_fm_vaccine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_fm_vaccine` (
  `fm_vaccine_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '疫苗id',
  `month_age` int(10) unsigned NOT NULL COMMENT '月龄',
  `vaccine_info` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '疫苗信息',
  PRIMARY KEY (`fm_vaccine_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='疫苗';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_grade`
--

DROP TABLE IF EXISTS `t_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_grade` (
  `grade_id` int(11) NOT NULL,
  `grade_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '年级名称',
  `volume` enum('ZERO','FIRST','SECOND','OTHER') COLLATE utf8_bin NOT NULL DEFAULT 'OTHER' COMMENT '册数',
  PRIMARY KEY (`grade_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='年级';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_model`
--

DROP TABLE IF EXISTS `t_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_model` (
  `model_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '型号id-MD1000',
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `model_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '型号名称-某个品牌下型号名称是唯一的',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `source` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '来源-0导入/1记录',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`model_id`) USING BTREE,
  UNIQUE KEY `model_name-brand_id` (`model_name`,`brand_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='品牌型号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_open_app`
--

DROP TABLE IF EXISTS `t_open_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_open_app` (
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用id',
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应用名称',
  `app_key` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应用秘钥(AES)',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id',
  `sign_device_number` int(10) unsigned NOT NULL DEFAULT '200' COMMENT '签约设备数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='开放平台应用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_open_app_business`
--

DROP TABLE IF EXISTS `t_open_app_business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_open_app_business` (
  `open_app_business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用业务id',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'APPID',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id',
  `business_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '业务名称',
  `business_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '业务代码',
  `business_key` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '业务key',
  `business_secret` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '业务secret',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`open_app_business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='开放平台应用业务信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_open_app_business_cdk`
--

DROP TABLE IF EXISTS `t_open_app_business_cdk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_open_app_business_cdk` (
  `open_app_business_cdk_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `open_app_business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用业务id',
  `cdk` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'cdk',
  `use_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0未使用/1已使用(实体类为boolean)',
  `open_device_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开放设备id',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`open_app_business_cdk_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='开放平台应用业务cdk';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_open_device`
--

DROP TABLE IF EXISTS `t_open_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_open_device` (
  `open_device_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `device_id` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '设备唯一标识',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用id',
  `brand` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `model` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '型号',
  `mac` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'mac',
  `sn` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '序列号',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`open_device_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='开放平台设备';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_press`
--

DROP TABLE IF EXISTS `t_press`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_press` (
  `press_id` int(11) NOT NULL,
  `press_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '出版社名称',
  `press_name_abbr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '出版社简称',
  PRIMARY KEY (`press_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='出版社';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_prize`
--

DROP TABLE IF EXISTS `t_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_prize` (
  `prize_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '奖品id',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id',
  `prize_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '奖品名称',
  `prize_img` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '奖品图片',
  `score` decimal(9,2) unsigned NOT NULL COMMENT '积分',
  `inventory` double(9,0) unsigned NOT NULL DEFAULT '1' COMMENT '库存',
  `prize_status` enum('UP','DOWN') COLLATE utf8_bin NOT NULL DEFAULT 'DOWN' COMMENT '奖品状态-UP:上架/DOWN:下架',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`prize_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='奖品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_region`
--

DROP TABLE IF EXISTS `t_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_region` (
  `region_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `pid` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `region_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '行政区划码',
  `province_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '省级',
  `city_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '市级',
  `county_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '县级',
  `area_code` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '区号',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '简称',
  `pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '拼音',
  `short_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '简拼',
  `first_letter` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '首字母',
  `english` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '英文',
  `gd_jd` decimal(20,17) DEFAULT NULL COMMENT '经度',
  `gd_wd` decimal(20,17) DEFAULT NULL COMMENT '维度',
  `province_short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '省级简称',
  `city_short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地级简称',
  `county_short_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '县级简称',
  `province_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '省级拼音',
  `city_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地级拼音',
  `county_pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '县级拼音',
  `pids` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '父级路径',
  `level` tinyint(1) DEFAULT NULL COMMENT '深度',
  `full_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '全路径',
  PRIMARY KEY (`region_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地区';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_sequence`
--

DROP TABLE IF EXISTS `t_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sequence` (
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `prefix` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='序列';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_serial_number`
--

DROP TABLE IF EXISTS `t_serial_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_serial_number` (
  `sn_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'sn码',
  `use_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '使用状态-0未使用/1已使用',
  `max_use_number` decimal(5,0) unsigned NOT NULL DEFAULT '1' COMMENT '最大使用次数',
  `use_number` decimal(5,0) unsigned NOT NULL DEFAULT '0' COMMENT '使用次数',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id(仅适用于此产品)',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`sn_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='SN码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_statistical_app_active`
--

DROP TABLE IF EXISTS `t_statistical_app_active`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_statistical_app_active` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `number` int(11) unsigned NOT NULL DEFAULT '0',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `date` (`date`,`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9720 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='统计产品日活用户数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_store_app`
--

DROP TABLE IF EXISTS `t_store_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_store_app` (
  `store_app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用商店id',
  `channel_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '渠道id',
  `app_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '应用名称',
  `package_name` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '包名',
  `call_class` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '调用类名',
  `file_size` decimal(19,2) NOT NULL DEFAULT '0.00' COMMENT '文件大小(b)字节',
  `version_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本名',
  `version_code` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '版本号',
  `url` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '文件地址',
  `md5` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5值',
  `icon` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '图标',
  `instruction` text COLLATE utf8_bin COMMENT '应用说明',
  `app_scope` enum('0','1') COLLATE utf8_bin DEFAULT NULL COMMENT '应用范围-0所有/1指定',
  `download_number` int(11) DEFAULT NULL COMMENT '下载次数',
  `phase_tag` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '阶段标签(0幼儿,1小学,2中学,3高中,100综合)',
  `release_time` date DEFAULT NULL COMMENT '发布时间',
  `content_type` enum('EDU','AMUSEMENT') COLLATE utf8_bin DEFAULT NULL COMMENT '内容类型',
  `developer` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开发商',
  `images` text COLLATE utf8_bin COMMENT '应用截图',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`store_app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='应用商店';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_store_app_scope`
--

DROP TABLE IF EXISTS `t_store_app_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_store_app_scope` (
  `store_app_scope_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `store_app_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '应用商店id',
  `app_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品id',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`store_app_scope_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='应用商店产品范围';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_subject`
--

DROP TABLE IF EXISTS `t_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_subject` (
  `subject_id` int(11) NOT NULL,
  `subject_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '学科名称',
  `pinyin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '学科拼音',
  PRIMARY KEY (`subject_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学科';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_third_business`
--

DROP TABLE IF EXISTS `t_third_business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_third_business` (
  `third_business_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '第三方内容商id',
  `third_business_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '第三方内容商名称',
  `third_business_code` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '第三方内容商代码',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`third_business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='第三方内容商';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_upgrade_plan`
--

DROP TABLE IF EXISTS `t_upgrade_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_upgrade_plan` (
  `upgrade_plan_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '升级计划id',
  `upgrade_plan_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '升级名称',
  `upgrade_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '升级时间-默认当前时间',
  `app_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `start_version_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品版本范围start',
  `end_version_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品版本范围end',
  `to_version_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '升级至产品版本',
  `device_scope` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '设备范围-0所有/1指定',
  `enforce` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否强制升级-0否/1是',
  `send_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '发布状态-0未发布/1已发布',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`upgrade_plan_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='设备升级计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_upgrade_plan_scope`
--

DROP TABLE IF EXISTS `t_upgrade_plan_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_upgrade_plan_scope` (
  `upgrade_plan_scope_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `upgrade_plan_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '升级计划id',
  `brand_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `model_scope` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '型号范围-0所有/1指定',
  `models` text COLLATE utf8_bin COMMENT '型号id-多个以,号分隔',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`upgrade_plan_scope_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='设备升级计划范围';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `user_id`       varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `app_id`        varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '产品id',
  `username`      varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `password`      varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `nick_name`     varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `birthday`      date DEFAULT NULL COMMENT '生日',
  `province_id`   varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '省id',
  `city_id`       varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '市id',
  `county_id`     varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '县id',
  `zip_code`      varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `email`         varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `gender`        enum('0','1','2') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '性别-0女/1男/2保密',
  `phone`         varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `age`           double(3,0) unsigned DEFAULT NULL COMMENT '年龄',
  `hobby`         varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好-多个用/分割',
  `head_url`      varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像路径',
  `enable_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用状态-0禁用/1启用',
  `remark`        varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time`   timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time`   timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `create_user`   varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `update_user`   varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者id',
  `status`        bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  `tag`           enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_address`
--

DROP TABLE IF EXISTS `t_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_address` (
  `user_address_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `consignee` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `phone` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '电话号码',
  `province_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '省id',
  `city_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '市id',
  `county_id` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '县id',
  `address` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '收货地址',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `default_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '默认状态',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`user_address_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户收货地址';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_login`
--

DROP TABLE IF EXISTS `t_user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_login` (
  `user_login_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `device_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `version_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `tag` enum('0','1') COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '0虚拟/1真实',
  PRIMARY KEY (`user_login_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `version_id` (`version_id`) USING BTREE,
  KEY `device_id` (`device_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户登录记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_score`
--

DROP TABLE IF EXISTS `t_user_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_score` (
  `user_score_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户积分id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `value` decimal(9,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '值',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`user_score_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户积分';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_score_detail`
--

DROP TABLE IF EXISTS `t_user_score_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_score_detail` (
  `user_score_detail_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户积分明细id',
  `user_score_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户积分id',
  `change_value` decimal(9,2) NOT NULL COMMENT '交易值',
  `after_value` decimal(9,2) unsigned NOT NULL COMMENT '交易后的值',
  `change_type` enum('EDU','EXCHANGE') COLLATE utf8_bin NOT NULL COMMENT '交易类型-EDU:学习/EXCHANGE:兑换',
  `join_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '关联业务id',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`user_score_detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户积分明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_score_exchange`
--

DROP TABLE IF EXISTS `t_user_score_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_score_exchange` (
  `user_score_exchange_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户积分兑换id',
  `order_number` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `channel_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '渠道id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `prize_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '奖品id',
  `prize_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '奖品名称',
  `prize_img` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '奖品图片',
  `score` decimal(9,2) unsigned NOT NULL COMMENT '积分',
  `courier_number` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '快递单号',
  `send_status` enum('NSEND','YSEND') COLLATE utf8_bin NOT NULL DEFAULT 'NSEND' COMMENT '发货状态-NSEND:未发货/YSEND:已发货',
  `send_time` timestamp(6) NULL DEFAULT NULL COMMENT '发货时间',
  `consignee` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `phone` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '电话号码',
  `province` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '省',
  `city` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '市',
  `county` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '县',
  `address` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '收货地址',
  `zip_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注-内部使用',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`user_score_exchange_id`) USING BTREE,
  UNIQUE KEY `order_number` (`order_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户积分兑换记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_target_prize`
--

DROP TABLE IF EXISTS `t_user_target_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_target_prize` (
  `user_target_prize_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户目标奖品id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `prize_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '奖品id',
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间,默认系统时间,不需要手动插入',
  `update_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间,默认系统时间,不需要手动插入',
  PRIMARY KEY (`user_target_prize_id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`,`prize_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户目标奖品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `v_edu_theme_resource`
--

DROP TABLE IF EXISTS `v_edu_theme_resource`;
/*!50001 DROP VIEW IF EXISTS `v_edu_theme_resource`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_edu_theme_resource` AS SELECT 
 1 AS `resource_id`,
 1 AS `directory_id`,
 1 AS `directory_type`,
 1 AS `directory_name`,
 1 AS `introduction`,
 1 AS `cover_url`,
 1 AS `parent_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_edu_theme_resource`
--

/*!50001 DROP VIEW IF EXISTS `v_edu_theme_resource`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_edu_theme_resource` AS select `b`.`resource_id` AS `resource_id`,`a`.`directory_id` AS `directory_id`,`a`.`directory_type` AS `directory_type`,`a`.`directory_name` AS `directory_name`,`a`.`introduction` AS `introduction`,`a`.`cover_url` AS `cover_url`,`a`.`parent_id` AS `parent_id` from (`t_edu_directory` `a` join `t_edu_directory_resource` `b` on((`b`.`directory_id` = `a`.`directory_id`))) where ((`a`.`status` = TRUE) and (`a`.`directory_type` = 'THEME')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-04  0:47:41
