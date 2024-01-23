drop table if exists t_vision_report;
drop table if exists t_vision_test;
drop table if exists t_eye;



/*==============================================================*/
/* Table: t_eye                                                 */
/*==============================================================*/
create table t_eye
(
   user_id              varchar(64) not null comment '用户序列号',
   type                 int default 1 comment '类型（0左眼,1右眼）',
   height               decimal(8,2) default null comment '眼睛高度（毫米）',
   width                decimal(8,2) default null comment '眼睛宽度（毫米）',
   pupi_distance        decimal(8,2) default null comment '瞳距（毫米）',
   length_of_optic_axis decimal(8,2) default null comment '眼轴（毫米）',
   corneal_diameter     decimal(8,2) default null comment '角膜直径（毫米）',
   corneal_thickness    decimal(8,2) default null comment '角膜厚度（微米）',
   pupil_diameter       decimal(8,2) default null comment '瞳孔直径（毫米）',
   intraocular_pressure decimal(8,2) default null comment '眼压IOP（mmHg）',
   radius_curvature     decimal(8,2) default null comment '曲率半径（毫米）',
   refractive_index_for_eyeball decimal(8,2) comment '介质折射率',
   radius_curvature_for_retina decimal(8,2) comment '视网膜曲率半径（毫米）',
   aperture_for_high_light decimal(8,2) comment '强光光圈',
   aperture_for_low_light decimal(8,2) comment '低光光圈',
   focus_distance_for_object_space decimal(8,2) comment '物方焦距（毫米）',
   focus_distance_for_image_space decimal(8,2) comment '像方焦距（毫米）',
   focal_power_for_image_space decimal(8,2) comment '像方光焦度',
   owner_id             varchar(64) default null comment '眼睛拥有者ID，用户ID，来自第三方系统',
   owner_name           varchar(64) default null comment '眼睛拥有者姓名，用户姓名，来自第三方系统',
   owner_tel            varchar(64) default null comment '眼睛拥有者电话，用户电话，来自第三方系统',
   owner_age            int default null comment '眼睛拥有者年龄，用户年龄，来自第三方系统',
   owner_height         decimal(8,2) default null comment '眼睛拥有者身高，用户身高，单位厘米，来自第三方系统',
   eye_id               varchar(64) not null comment '序列号',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (eye_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table t_eye comment '眼睛';

alter table t_eye add constraint FK_user_eye foreign key (user_id)
      references t_user (user_id) on delete restrict on update restrict;


/*==============================================================*/
/* Table: t_vision_test                                         */
/*==============================================================*/
create table t_vision_test
(
   eye_id               varchar(64) not null comment '眼睛序列号',
   date_time            timestamp(6) not null default current_timestamp(6) comment '测试日期和时间',
   device_type          int not null comment '设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）',
   distance             decimal(8,2) not null default 5.0 comment '测试距离（单位米）',
   env_light_intensity  decimal(8,2) default null comment '环境光照强度',
   time_duration        int default null comment '测试持续时长（单位秒）',
   test_mode            int not null default 1 comment '测试模式（0自测模式，1家长协助模式，2医生协助模式）',
   test_id              varchar(64) not null comment '测试序列号',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (test_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table t_vision_test comment '视力测试活动';

alter table t_vision_test add constraint FK_eye_vision foreign key (eye_id)
      references t_eye (eye_id) on delete restrict on update restrict;


/*==============================================================*/
/* Table: t_vision_report                                       */
/*==============================================================*/
create table t_vision_report
(
   test_id              varchar(64) not null comment '测试序列号',
   judgement_result     int default null comment '判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）',
   decimal_record       decimal(8,2) default null comment '小数记录',
   logarithm_record     decimal(8,2) default null comment '对数记录，5分制',
   diopter_for_sph      decimal(8,2) default null comment '球镜屈光度S(单位D)',
   diopter_for_cyl      decimal(8,2) default null comment '柱镜屈光度C(单位D)',
   corneal_diopter      decimal(8,2) default null comment '角膜屈光度(单位D)',
   crystal_diopter      decimal(8,2) default null comment '晶体度(单位D)',
   axis_degrees_for_astigmatism decimal(8,2) default null comment '散光轴度A',
   proposal             varchar(1024) comment '建议或诊断',
   report_id            varchar(64) not null comment '报告序列号',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table t_vision_report comment '视力报告';

alter table t_vision_report add constraint FK_vision_test foreign key (test_id)
      references t_vision_test (test_id) on delete restrict on update restrict;
	  
CREATE TABLE t_vision_table  (
  `id`                   int NOT NULL COMMENT '序号',
  `table_type`           int NOT NULL COMMENT '类型 0近视力表 1远视力表',
  `line_sn`              int NOT NULL COMMENT '行序号，从上数到下，0开始',
  `distance`             double NOT NULL COMMENT '标准距离，单位米',
  `decimal_record`       double NOT NULL COMMENT '小数计数',
  `logarithm_record`     double NOT NULL COMMENT '对数计数',
  `sighting_mark_length` double NOT NULL COMMENT '视标边长，单位豪米',
  `fail_count_allowed`   int NOT NULL COMMENT '被允许的错误次数，一般0.1-0.4允许0次，即错一次就不认可，0.5-0.8允许1次，即错两次就不认可，1.0-1.2允许错误2次，1.5及其以上允许错误3次',
  `right_count`          int NOT NULL COMMENT '连续正确次数，一般0.1-0.4连续2次，0.5以上连续4次',
  `remark`               varchar(512) default null comment '备注-内部使用',
  `create_time`          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
  `update_time`          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
  `create_user`          varchar(64) default null comment '创建者id',
  `update_user`          varchar(64) default null comment '修改者id',
  `status`               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
  PRIMARY KEY (`id`)
);
alter table t_vision_table comment '视力表';
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (1, 1, 0, 5, 0.1, 4, 72.72, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:31:59.818395', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (2, 1, 1, 5, 0.12, 4.1, 57.76, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:26:21.774556', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (3, 1, 2, 5, 0.15, 4.2, 45.88, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:26:31.270466', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (4, 1, 3, 5, 0.2, 4.3, 36.45, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:31:33.679517', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (5, 1, 4, 5, 0.25, 4.4, 28.95, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:26:49.274180', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (6, 1, 5, 5, 0.3, 4.5, 23, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:31:25.201184', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (7, 1, 6, 5, 0.4, 4.6, 18.27, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:31:08.599467', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (8, 1, 7, 5, 0.5, 4.7, 14.51, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:27:08.955665', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (9, 1, 8, 5, 0.6, 4.8, 11.53, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:30:54.477564', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (10, 1, 9, 5, 0.8, 4.9, 9.16, NULL, '2021-05-01 17:53:47.878320', '2021-07-26 17:30:43.427454', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (11, 1, 10, 5, 1, 5, 7.27, NULL, '2021-05-01 17:45:14.729463', '2021-07-26 17:12:19.480788', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (12, 1, 11, 5, 1.2, 5.1, 5.78, NULL, '2021-05-01 17:45:14.729463', '2021-07-26 17:12:21.237383', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (13, 1, 12, 5, 1.5, 5.2, 4.59, NULL, '2021-05-01 17:45:14.729463', '2021-07-26 17:12:28.533699', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (14, 1, 13, 5, 2, 5.3, 3.64, NULL, '2021-05-01 17:45:14.729463', '2021-07-26 17:10:57.328693', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (15, 1, 14, 5, 2.5, 5.4, 2.89, NULL, '2021-07-26 17:13:59.151776', '2021-07-26 17:38:39.456190', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (16, 1, 15, 5, 3.2, 5.5, 2.3, NULL, '2021-07-26 17:20:28.266131', '2021-07-26 17:39:24.129725', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (17, 1, 16, 5, 4, 5.6, 1.83, NULL, '2021-07-26 17:21:20.008340', '2021-07-26 17:40:40.341681', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (18, 1, 17, 5, 5, 5.7, 1.45, NULL, '2021-07-26 17:21:46.354259', '2021-07-26 17:41:16.362429', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (19, 1, 18, 5, 6.3, 5.8, 1.15, NULL, '2021-07-26 17:22:21.131428', '2021-07-26 17:42:14.716790', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (20, 1, 19, 5, 8, 5.9, 0.92, NULL, '2021-07-26 17:22:57.500745', '2021-07-26 17:42:39.980820', NULL, NULL, b'1');
INSERT INTO `t_vision_table` (`id`, `table_type`, `line_sn`, `distance`, `decimal_record`, `logarithm_record`, `sighting_mark_length`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`) VALUES (21, 1, 20, 5, 10, 6, 0.73, NULL, '2021-07-26 17:23:27.114799', '2021-07-26 17:42:49.270307', NULL, NULL, b'1');

/*==============================================================*/
/* Table: t_vision_plan                                         */
/*==============================================================*/
create table t_vision_plan
(
   user_id              varchar(64) not null comment '用户序列号，关联用户信息',
   plan_name            varchar(64) not null comment '计划名称',
   start_date           timestamp(6) not null comment '计划开始时间，执行计划所规定的活动的开始时间，也是首次提醒的时间点',
   end_date             timestamp(6) default current_timestamp(6) comment '计划结束时间，执行计划所规定的活动的结束开始时间，最后一次提醒的时间点不能超过计划结束时间',
   plan_type            int not null comment '提醒类型（0每年提醒,1每月提醒,2每周提醒,3每天提醒,4每小时提醒,5一次性提醒,6每45分钟提醒,7每30分钟提醒,8每3天提醒）',
   action_mode          int not null comment '执行模式（0状态栏提醒,1弹出框提醒,2立即强制切换,3倒计时切换可取消）',
   plan_action          int default null comment '计划操作（0视力测试,1视力防护,2其他）',
   plan_id              varchar(64) not null comment '计划序列号',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (plan_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table t_vision_plan comment '视力计划';

alter table t_vision_plan add constraint FK_user_plan foreign key (user_id)
      references t_user (user_id) on delete restrict on update restrict;

/*==============================================================*/
/* Table: t_eyestrain                                           */
/*==============================================================*/
create table t_eyestrain
(
   user_id              varchar(64) not null comment '用户序列号，关联用户信息',
   eye_id               varchar(64) not null comment '眼睛序列号，关联用户眼睛信息，备用',
   date_time            timestamp(6) not null default current_timestamp(6) comment '用眼日期和时间',
   use_type        int not null comment '用眼类型（-1所有类型、0看书、1看黑板、2看手机、3看电脑、4看平板、5看电视、6其他），默认为0',
   time_duration        int not null comment '持续时长（单位秒）',
   use_mode             int default null comment '用眼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式），默认为0',
   eyestrain_id         varchar(64) not null comment '用眼活动序列号',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (eyestrain_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table t_eyestrain comment '眼疲劳记录（用眼活动）';

alter table t_eyestrain add constraint FK_user_eyestrain foreign key (user_id)
      references t_user (user_id) on delete restrict on update restrict;

/*==============================================================*/
/* Table: t_vision_exercise                                     */
/*==============================================================*/
create table t_vision_exercise
(
   user_id              varchar(64) not null comment '用户序列号，关联用户信息',
   eye_id               varchar(64) not null comment '眼睛序列号，关联用户眼睛信息，备用',
   date_time            timestamp(6) not null default current_timestamp(6) comment '锻炼日期和时间',
   exercise_type        int not null comment '锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4其他），默认为0',
   time_duration        int not null comment '持续时长（单位秒）',
   exercise_mode        int default null comment '锻炼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式），默认为0',
   exercise_id          varchar(64) not null comment '锻炼序列号',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (exercise_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

alter table t_vision_exercise comment '视力锻炼';

alter table t_vision_exercise add constraint FK_user_exer foreign key (user_id)
      references t_user (user_id) on delete restrict on update restrict;
	  
	  
/*==============================================================*/
/* Table: t_content                                             */
/*==============================================================*/
create table t_content
(
   content_id           varchar(64)  comment '内容ID',
   name                 varchar(256) not null comment '内容名称',
   display_title        varchar(256) not null comment '显示标题',
   digest               varchar(512) comment '摘要',
   keyword              varchar(256) comment '关键词',
   industry             int not null comment '行业（关联行业表）',
   type1                int not null comment '锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4和锻炼无关）',
   type2                int not null comment '锻炼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式、3和锻炼无关）',
   type3                int not null comment '判定结果（null未判定 0正常 1警戒 2危险 3高危 4和视力判定无关）',
   `text`               longtext not null comment 'H5文本内容，不含<html>和<body>标签',
   video_url            varchar(512) not null comment '视频链接，可以是对象存储链接',
   audio_url            varchar(512) not null comment '音频链接，可以是对象存储链接',
   cover_picture_url    varchar(512) not null comment '封面图片链接，可以是对象存储链接',
   `order`              int comment '置顶（整数值越小，排在前面）',
   external_link        varchar(256) comment '外部文章链接',
   type4                int not null default -1 comment '来源（-1未知，0原创，1转载，2翻译，3其他）',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (content_id)
);

alter table t_content comment '内容';

/*==============================================================*/
/* Table: t_comment                                               */
/*==============================================================*/
create table t_comment
(
   comment_id           varchar(64) comment '评论ID',
   content_id           varchar(64) not null comment '内容ID',
   user_id              varchar(64) not null comment '用户ID',
   comment_content      varchar(1024) not null comment '评论内容',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (comment_id)
);

alter table t_comment comment '评论';

alter table t_comment add constraint FK_coment_content foreign key (content_id)
      references t_content (content_id) on delete restrict on update restrict;

/*==============================================================*/
/* Table: t_creation_management                                   */
/*==============================================================*/
create table t_creation_management
(
   cm_id                varchar(64) not null comment '创作记录ID',
   content_id           varchar(64) not null comment '内容ID',
   creation_type        int not null comment '修改类型',
   update_content_before longtext not null comment '修改之前内容',
   video_len_before     int not null comment '视频修改之前长度',
   video_len_after      int not null comment '视频修改之后长度',
   audio_len_before     int not null comment '音频修改之前长度',
   audio_len_after      int not null comment '音频修改之后长度',
   update_content_after longtext not null comment '修改之后内容',
   author               varchar(64) not null comment '作者',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (cm_id)
);

alter table t_creation_management comment '创作管理';

alter table t_creation_management add constraint FK_Reference_2 foreign key (content_id)
      references t_content(content_id) on delete restrict on update restrict;

/*==============================================================*/
/* Table: t_class                                               */
/*==============================================================*/
create table t_class
(
   class_id             varchar(64) comment '行业ID',
   name                 varchar(256) not null comment '行业名称',
   parent_id            varchar(64) default null comment '上级ID',
   level                int not null comment '行业级别 -1无级别 0-最高级（门类），1第一级（大类），2第二级（中类），3第三级（小类）',
   remark               varchar(512) default null comment '备注-内部使用',
   create_time          timestamp(6) not null default current_timestamp(6) comment '创建时间,默认系统时间,不需要手动插入',
   update_time          timestamp(6) not null default current_timestamp(6) on update current_timestamp(6) comment '修改时间,默认系统时间,不需要手动插入',
   create_user          varchar(64) default null comment '创建者id',
   update_user          varchar(64) default null comment '修改者id',
   status               bit(1) not null default b'1' comment '数据有效性-0无效/1有效(实体类为boolean)',
   primary key (class_id)
);

alter table t_class comment '行业';