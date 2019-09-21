
CREATE TABLE IF NOT EXISTS `appraise_census_` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT,
  `entity_id_` int(11) NOT NULL DEFAULT '0' COMMENT '考核主体id',
  `entity_type_` enum('主管部门','用工单位') NOT NULL DEFAULT '主管部门' COMMENT '考核主体类型',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '考核时间',
  `check_num_` mediumint(9) NOT NULL COMMENT '登记数量',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='考核统计';

DELETE FROM `appraise_census_`;

CREATE TABLE IF NOT EXISTS `basic_info_` (
  `identity_` varchar(20) NOT NULL COMMENT '身份证主键',
  `name_` varchar(20) NOT NULL COMMENT '姓名',
  `marriage_` enum('未婚','已婚') NOT NULL DEFAULT '未婚' COMMENT '婚姻状况',
  `education_` enum('小学','初中','高中','技校','中专','大专','本科','硕士','博士') NOT NULL DEFAULT '初中' COMMENT '文化程度',
  `politic_countenance_` enum('党员','其他党派','群众') NOT NULL DEFAULT '群众' COMMENT '政治面貌',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` int(11) NOT NULL COMMENT '创建者id',
  `sys_status_` enum('Y','N') NOT NULL COMMENT '系统状态',
  PRIMARY KEY (`identity_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='拓展人员基础信息表(冗余)';

DELETE FROM `basic_info_`;
INSERT INTO `basic_info_` (`identity_`, `name_`, `marriage_`, `education_`, `politic_countenance_`, `create_time_`, `update_time_`, `create_id_`, `sys_status_`) VALUES
	('440281199308080808', '张三', '未婚', '初中', '群众', '2019-08-20 14:15:21', '2019-08-20 14:15:22', 1, 'Y');

CREATE TABLE IF NOT EXISTS `common_relation_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT,
  `relation_type_` enum('MANAGE','VICE_MINISTRIES') NOT NULL DEFAULT 'MANAGE' COMMENT '关系名称',
  `master_id_` int(11) NOT NULL DEFAULT '0' COMMENT '主关系方',
  `slave_id_` int(11) NOT NULL DEFAULT '0' COMMENT '从关系方',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id_`),
  KEY `relation_type_` (`relation_type_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='通用关系表';

DELETE FROM `common_relation_`;
INSERT INTO `common_relation_` (`id_`, `relation_type_`, `master_id_`, `slave_id_`, `create_time_`, `update_time_`, `create_id_`) VALUES
	(2, 'MANAGE', 6, 4, '2019-09-18 11:40:24', '2019-09-18 11:40:24', 1);

CREATE TABLE IF NOT EXISTS `education_info_` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `identity_` varchar(20) NOT NULL COMMENT '身份证',
  `name_` varchar(20) NOT NULL COMMENT '姓名',
  `grade_` tinyint(4) NOT NULL COMMENT '年级',
  `school_class_` varchar(20) NOT NULL COMMENT '班级',
  `enrol_time_` datetime NOT NULL COMMENT '入学时间',
  `leave_time_` datetime DEFAULT NULL COMMENT '转学/辍学/毕业时间',
  `guardian_identity` varchar(20) NOT NULL COMMENT '监护人身份证',
  `guardian_name` varchar(20) NOT NULL COMMENT '监护人姓名',
  `create_time_` datetime NOT NULL COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` int(11) NOT NULL COMMENT '创建者id',
  `sys_status_` enum('Y','N') NOT NULL COMMENT '创建时间',
  `remarks_` varchar(2000) NOT NULL COMMENT '备注,如留级或停学就医记录等',
  PRIMARY KEY (`id`),
  KEY `identity_` (`identity_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='就学信息表';

DELETE FROM `education_info_`;

CREATE TABLE IF NOT EXISTS `manager_user_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `name_` varchar(40) NOT NULL DEFAULT ' ' COMMENT '姓名',
  `password_` varchar(128) NOT NULL DEFAULT ' ' COMMENT '密码',
  `identity_` varchar(20) NOT NULL DEFAULT ' ' COMMENT '身份证',
  `phone_` varchar(20) NOT NULL DEFAULT ' ' COMMENT '联系电话',
  `account_` varchar(40) NOT NULL DEFAULT ' ' COMMENT '账号',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `sys_status_` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '系统状态',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `account_` (`account_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='管理员表';

DELETE FROM `manager_user_`;
INSERT INTO `manager_user_` (`id_`, `name_`, `password_`, `identity_`, `phone_`, `account_`, `create_time_`, `update_time_`, `create_id_`, `sys_status_`) VALUES
	(1, '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '440000190001010102', '8888881', 'admin', '2019-08-21 09:39:22', '2019-09-10 11:58:05', 1, 'Y'),
	(9, '李四', 'e10adc3949ba59abbe56e057f20f883e', '440555199909090909', '5555555', 'lisi', '2019-09-03 17:07:19', '2019-09-03 17:07:29', 1, 'Y');

CREATE TABLE IF NOT EXISTS `medical_info_` (
  `id_` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `name_` varchar(20) NOT NULL COMMENT '姓名',
  `identity_` varchar(20) NOT NULL COMMENT '身份证',
  `phone_` varchar(20) NOT NULL COMMENT '联系电话',
  `address_` varchar(128) NOT NULL COMMENT '居住地址',
  `medical_card_` varchar(50) DEFAULT NULL COMMENT '医疗保健卡',
  `vaccin_` varchar(50) DEFAULT NULL COMMENT '疫苗信息',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` varchar(50) NOT NULL COMMENT '创建者id',
  `sys_status_` enum('Y','N') NOT NULL COMMENT '系统状态',
  PRIMARY KEY (`id_`),
  KEY `identity_` (`identity_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='医疗信息表';

DELETE FROM `medical_info_`;

CREATE TABLE IF NOT EXISTS `menu_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `name_` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名',
  `parent_id_` int(11) NOT NULL DEFAULT '0' COMMENT '菜单父id',
  `menu_url_` varchar(256) NOT NULL COMMENT '菜单链接',
  `menu_type_` enum('IFRAME','WINDOW','BUTTON','VOID') NOT NULL DEFAULT 'IFRAME' COMMENT '菜单类型',
  `menu_icon_` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `sys_status_` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '系统状态',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `name_` (`name_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='菜单-三共平台';

DELETE FROM `menu_`;
INSERT INTO `menu_` (`id_`, `name_`, `parent_id_`, `menu_url_`, `menu_type_`, `menu_icon_`, `sys_status_`, `create_time_`) VALUES
	(1, '系统管理', 0, '', 'VOID', '', 'Y', '2019-08-23 10:39:49'),
	(3, '菜单管理', 1, '/menu/menuList', 'IFRAME', '', 'Y', '2019-08-23 10:40:36'),
	(23, '角色管理', 1, '/role/roleList', 'IFRAME', '', 'Y', '2019-08-28 15:46:46'),
	(24, '部门管理', 1, '/ministries/ministriesList', 'IFRAME', '', 'Y', '2019-08-29 16:27:51'),
	(29, '用户管理', 1, '/manager-user/userList', 'IFRAME', '', 'Y', '2019-09-03 14:00:12'),
	(30, '消息管理', 1, '/message/messageList', 'IFRAME', '', 'Y', '2019-09-04 13:50:14'),
	(31, '功能列表', 0, '无', 'VOID', '', 'Y', '2019-09-09 10:48:06'),
	(32, '人员管理', 31, '/t-renter/renterList', 'IFRAME', '', 'Y', '2019-09-09 10:58:14');

CREATE TABLE IF NOT EXISTS `message_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `title_` varchar(200) NOT NULL DEFAULT '暂无' COMMENT '消息标题',
  `content_` text NOT NULL COMMENT '消息内容',
  `message_type_` enum('SYSTEM','PERSONAL') NOT NULL DEFAULT 'PERSONAL' COMMENT '消息类别',
  `create_id_` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_name_` varchar(50) NOT NULL DEFAULT '暂无' COMMENT '创建者名称',
  `receiver_id_` int(11) NOT NULL DEFAULT '0' COMMENT '接收者id',
  `receiver_name_` varchar(50) NOT NULL DEFAULT '暂无' COMMENT '接收者名称',
  `sys_status_` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '系统状态',
  `read_status_` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '已读未读',
  `send_status_` enum('DRAFT','SEND') NOT NULL DEFAULT 'DRAFT' COMMENT '发送状态 草稿/已发',
  `reply_to_` int(11) NOT NULL DEFAULT '0' COMMENT '回复消息id',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='消息表';

DELETE FROM `message_`;
INSERT INTO `message_` (`id_`, `title_`, `content_`, `message_type_`, `create_id_`, `create_name_`, `receiver_id_`, `receiver_name_`, `sys_status_`, `read_status_`, `send_status_`, `reply_to_`, `create_time_`, `update_time_`) VALUES
	(1, '你好,您的余额不足,请及时充值', '您的余额还剩55元,充值请拨打10086', 'PERSONAL', 1, '暂无', 1, '超级管理员', 'Y', 'Y', 'DRAFT', 0, '2019-09-04 15:10:24', '2019-09-04 16:51:36'),
	(2, '哈哈哈哈', '阿斯打死打伤打上的阿斯顿', 'PERSONAL', 1, '超级管理员', 1, '超级管理员', 'Y', 'Y', 'DRAFT', 0, '2019-09-04 16:34:10', '2019-09-04 16:51:39'),
	(3, '打发上帝发誓地方阿斯', '阿斯顿发生的', 'PERSONAL', 1, '超级管理员', 1, '超级管理员', 'Y', 'Y', 'DRAFT', 0, '2019-09-04 16:37:51', '2019-09-04 17:05:05'),
	(4, '这是一条消息', '这是一条消息这是一条消息', 'PERSONAL', 9, '李四', 1, '超级管理员', 'Y', 'Y', 'SEND', 0, '2019-09-19 15:55:56', '2019-09-19 15:55:56');

CREATE TABLE IF NOT EXISTS `ministries_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name_` varchar(100) NOT NULL DEFAULT '0' COMMENT '部门名称',
  `level_` tinyint(4) NOT NULL DEFAULT '3' COMMENT '管理优先级数字越小级别越高',
  `parent_id_` int(11) NOT NULL DEFAULT '0' COMMENT '直接上级部门id',
  `path_` varchar(100) NOT NULL DEFAULT '0' COMMENT '路径,  爷id-父id-自己id',
  `area_code_` varchar(50) NOT NULL DEFAULT '0' COMMENT '地址编号',
  `area_type_` enum('region','zjlx','door','floor') NOT NULL COMMENT '地址类型',
  `address_detail_` varchar(128) NOT NULL DEFAULT '0' COMMENT '地址详情',
  `work_unit_` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '是否用工单位',
  `type_` enum('国企','私企','合资','机关事业单位','学校','医疗机构','其他') NOT NULL DEFAULT '其他' COMMENT '类别',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `sys_status_` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '系统状态',
  `remarks_` varchar(2000) NOT NULL DEFAULT 'Y' COMMENT '备注',
  PRIMARY KEY (`id_`),
  KEY `path_` (`path_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='部门';

DELETE FROM `ministries_`;
INSERT INTO `ministries_` (`id_`, `name_`, `level_`, `parent_id_`, `path_`, `area_code_`, `area_type_`, `address_detail_`, `work_unit_`, `type_`, `create_time_`, `update_time_`, `create_id_`, `sys_status_`, `remarks_`) VALUES
	(1, '督查办', 2, 6, '0-6-1', '0', '', '0', 'N', '其他', '2019-09-02 11:16:20', '2019-09-18 15:17:19', 0, 'Y', 'Y'),
	(2, '政法委', 3, 1, '0-6-1-2', '0', '', '0', 'N', '其他', '2019-09-02 11:17:13', '2019-09-18 15:17:21', 0, 'Y', 'Y'),
	(3, '公安局', 4, 2, '0-6-1-2-3', '0', '', '0', 'N', '其他', '2019-09-02 11:17:30', '2019-09-18 15:16:40', 0, 'Y', 'Y'),
	(4, '南明区莲花派出所', 5, 3, '0-6-1-2-3-4', '0', '', '0', 'N', '机关事业单位', '2019-09-02 11:18:19', '2019-09-18 15:16:46', 0, 'Y', 'Y'),
	(6, '管理中心', 1, 0, '0-6', '0', '', '0', 'N', '其他', '2019-09-02 16:02:41', '2019-09-02 16:02:49', 0, 'Y', 'Y');

CREATE TABLE IF NOT EXISTS `occupation_info_` (
  `id_` bigint(20) NOT NULL COMMENT '自增长主键',
  `name_` varchar(50) NOT NULL COMMENT '姓名',
  `identity_` int(11) NOT NULL COMMENT '身份证弱外键',
  `gender_` enum('男','女') NOT NULL DEFAULT '男' COMMENT '性别',
  `native_place_` varchar(20) NOT NULL COMMENT '籍贯',
  `type_` enum('正式员工','试用员工','合同工') NOT NULL DEFAULT '正式员工' COMMENT '员工类型',
  `sign_time_` datetime NOT NULL COMMENT '入职时间',
  `resign_time_` datetime DEFAULT NULL COMMENT '离职时间',
  `belong_unit_` datetime NOT NULL COMMENT '所属用工单位',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id_` int(11) NOT NULL COMMENT '创建者id',
  `sys_status_` enum('Y','N') NOT NULL COMMENT '系统状态',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='用工就业信息';

DELETE FROM `occupation_info_`;

CREATE TABLE IF NOT EXISTS `role_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长',
  `role_name_` varchar(24) DEFAULT NULL COMMENT '角色名',
  `role_level_` smallint(6) DEFAULT NULL COMMENT '角色等级',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_status_` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '系统状态',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `role_name_` (`role_name_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

DELETE FROM `role_`;
INSERT INTO `role_` (`id_`, `role_name_`, `role_level_`, `create_time_`, `sys_status_`) VALUES
	(1, 'admin', 1, '2019-05-08 17:35:56', 'Y'),
	(13, '普通用户', NULL, '2019-09-03 17:21:36', 'Y');

CREATE TABLE IF NOT EXISTS `role_ministries_relation_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id_` int(11) NOT NULL DEFAULT '0' COMMENT '角色表id',
  `role_name_` varchar(50) NOT NULL DEFAULT '0' COMMENT '角色名',
  `ministries_id_` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `ministries_name_` varchar(50) NOT NULL DEFAULT '0' COMMENT '部门名',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id_` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='角色部门关联表';

DELETE FROM `role_ministries_relation_`;
INSERT INTO `role_ministries_relation_` (`id_`, `role_id_`, `role_name_`, `ministries_id_`, `ministries_name_`, `create_time_`, `create_id_`) VALUES
	(3, 1, 'admin', 1, '督查办', '2019-09-02 15:49:31', 0),
	(4, 1, 'admin', 6, '管理中心', '2019-09-02 16:03:00', 0),
	(5, 13, '普通用户', 3, '公安局', '2019-09-03 17:36:26', 0),
	(6, 1, 'admin', 4, '南明区莲花派出所', '2019-09-18 17:54:27', 0),
	(7, 13, '普通用户', 4, '南明区莲花派出所', '2019-09-18 17:54:27', 0),
	(9, 13, '普通用户', 2, '政法委', '2019-09-19 13:43:09', 0);

CREATE TABLE IF NOT EXISTS `role_permission_relation_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `menu_url_` varchar(100) NOT NULL COMMENT '菜单url',
  `menu_id_` int(11) NOT NULL COMMENT '菜单id',
  `role_id_` int(11) NOT NULL COMMENT '角色表主键',
  `role_name_` varchar(50) NOT NULL COMMENT '角色名',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sys_status_` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '系统状态',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='菜单角色权限表';

DELETE FROM `role_permission_relation_`;
INSERT INTO `role_permission_relation_` (`id_`, `menu_url_`, `menu_id_`, `role_id_`, `role_name_`, `create_time_`, `update_time_`, `sys_status_`) VALUES
	(69, '', 1, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(70, '/menu/menuList', 3, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(71, '/role/roleList', 23, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(72, '/ministries/ministriesList', 24, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(73, '/manager-user/userList', 29, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(74, '/message/messageList', 30, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(75, '无', 31, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y'),
	(76, '/t-renter/renterList', 32, 1, 'admin', '2019-09-19 15:04:47', '2019-09-19 15:04:47', 'Y');

CREATE TABLE IF NOT EXISTS `user_ministries_` (
  `id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id_` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name_` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户姓名',
  `ministries_id_` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `ministries_name_` varchar(50) NOT NULL DEFAULT '0' COMMENT '部门名字',
  `create_id_` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `create_time_` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='用户部门关系表';

DELETE FROM `user_ministries_`;
INSERT INTO `user_ministries_` (`id_`, `user_id_`, `user_name_`, `ministries_id_`, `ministries_name_`, `create_id_`, `create_time_`) VALUES
	(25, 9, '李四', 4, '南明区莲花派出所', 0, '2019-09-18 17:54:52'),
