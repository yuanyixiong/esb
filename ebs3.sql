
DROP TABLE IF EXISTS `esb_application`;
CREATE TABLE `esb_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `app_name` varchar(25) NOT NULL COMMENT '应用名称',
  `app_code` varchar(10) NULL COMMENT '应用编号-自动生成',
  `contacts` varchar(25) NOT NULL COMMENT '联系人',
  `email` varchar(50) NOT NULL COMMENT '联系人邮箱',
  `ip` varchar(20) NOT NULL COMMENT '填写运行第三方服务的服务器的ip地址，第三应用调用接口时，接口会根据这个ip判断接口调用是否从配置应用发出的。',
  `is_enabled` int(1) DEFAULT '2' COMMENT '启用状态：1 启用 2 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='平台应用表';

-- ----------------------------
-- Records of esb_application
-- ----------------------------

-- ----------------------------
-- Table structure for esb_cache_setup
-- ----------------------------
DROP TABLE IF EXISTS `esb_cache_setup`;
CREATE TABLE `esb_cache_setup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `cache_type_id` bigint(20) NOT NULL COMMENT '缓存类型ID',
  `db_cache` int(11) NULL COMMENT '缓存大小，单位(mb)',
  `cache_key` varchar(50) NULL COMMENT '缓存key',
  `cache_method` varchar(50) NULL COMMENT '缓存清空方法名',
  `file_cache` int(11) NULL COMMENT '文件目录缓存大小，单位(mb)',
  `spring_cache` int(11) NOT NULL COMMENT 'Spring缓存大小，单位(mb)',
  `is_enabled` int(1) DEFAULT '2' COMMENT '启用状态：1 启用 2 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='缓存配置信息表';

DROP TABLE IF EXISTS `esb_cache_type`;
CREATE TABLE `esb_cache_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `cache_name` varchar(20) NOT NULL COMMENT '缓存类型名称 redis缓冲',
  `cache_code` varchar(10) NOT NULL COMMENT '缓存类型编码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='缓存类型表';

-- ----------------------------
-- Records of esb_cache_setup
-- ----------------------------

-- ----------------------------
-- Table structure for esb_connection_setup
-- ----------------------------
DROP TABLE IF EXISTS `esb_connection_setup`;
CREATE TABLE `esb_connection_setup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `max_active_connection` int(11) NOT NULL COMMENT '最大活动连接数',
  `max_connection` int(11) NOT NULL COMMENT '最大并发连接数',
  `protocol_type` varchar(3) NOT NULL COMMENT '协议类型 10 RestApi 20 WebService 30 Socket 40 MQ',
  `timeout` int(11) NOT NULL COMMENT '默认接口调用时间',
  `is_enabled` int(1) DEFAULT '2' COMMENT '启用状态：1 启用 2 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='连接配置信息表';

-- ----------------------------
-- Records of esb_connection_setup
-- ----------------------------

-- ----------------------------
-- Table structure for esb_encrypt_decrypt
-- ----------------------------
DROP TABLE IF EXISTS `esb_encrypt_decrypt`;
CREATE TABLE `esb_encrypt_decrypt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `decrypt_name` varchar(50) NOT NULL COMMENT '加解密名称',
  `decrypt_type` varchar(2) NOT NULL COMMENT '加解密类型 1 加密 2 解密',
  `class_url` varchar(20) NOT NULL COMMENT '类名url',
  `class_name` varchar(20) NOT NULL COMMENT '加解密完整类名',
  `is_enabled` int(1) DEFAULT '2' COMMENT '启用状态：1 启用 2 停用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口加解密信息表';

-- ----------------------------
-- Records of esb_encrypt_decrypt
-- ----------------------------

-- ----------------------------
-- Table structure for esb_kafka_topic
-- ----------------------------
DROP TABLE IF EXISTS `esb_kafka_topic`;
CREATE TABLE `esb_kafka_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `topic_name` varchar(20) NOT NULL COMMENT 'kafka topic名称',
  `topic_code` varchar(20) NOT NULL COMMENT 'topic 编码',
  `is_enabled` int(1) DEFAULT '2' COMMENT '启用状态：1 启用 2 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='topic管理';

-- ----------------------------
-- Records of esb_kafka_topic
-- ----------------------------

-- ----------------------------
-- Table structure for esb_interface
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface`;
CREATE TABLE `esb_interface` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `protocol_type` varchar(8) NULL COMMENT '协议类型：http:// https://',
  `server_ip` varchar(15) NOT NULL COMMENT '服务IP',
  `server_port` int(6) NOT NULL COMMENT '端口',
  `send_url` varchar(100) DEFAULT NULL COMMENT '请求url',
  `description` varchar(50) DEFAULT NULL COMMENT '接口描述',
  `key_property` varchar(20) DEFAULT NULL COMMENT '接口对应数据类型的唯一标识字段名称（通常是原数据的id字段）',
  `interface_name` varchar(50) NOT NULL COMMENT '接口名称',
  `need_approve` bit(1) DEFAULT NULL COMMENT '是否需要审核',
  `request_mime` varchar(20) DEFAULT NULL COMMENT '定义RestApi接口的时候用到，RestApi接口的请求MIME类型，application-json/ form-data等',
  `request_type` varchar(20) DEFAULT NULL COMMENT '定义RestApi接口的时候用到，RestApi接口的请求方式，get/post',
  `response_type` varchar(20) DEFAULT NULL COMMENT '定义RestApi接口的时候用到，返回类型',
  `target_name_space` varchar(200) DEFAULT NULL COMMENT '定义webService主动接口的时候用到，无需填写，通过调用ESB-PORT模块的方法获取（以wsdl地址作为参数）',
  `ws_method` varchar(200) DEFAULT NULL COMMENT '定义webService主动接口的时候用到，通过调用ESB-PORT模块的方法获取（以wsdl地址作为参数）获取webService方法列表供用户选择',
  `typ_active` varchar(2) NOT NULL COMMENT '1.主动:主动接口/2.被动:被动接口',
  `type_transfer` varchar(2) NOT NULL COMMENT '1.输入：输入接口/2.输出：输出接口',
  `crypt_id` bigint(20) DEFAULT NULL COMMENT '加解密ID',
  `protocol_id` bigint(20) NOT NULL COMMENT '协议ID',
  `template_id` bigint(20) DEFAULT NULL COMMENT '接口模版ID',
  `is_enabled` int(1) DEFAULT '2' COMMENT '应用状态：1 启用 2 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8 COMMENT='接口配置表';

-- ----------------------------
-- Records of esb_interface
-- ----------------------------

-- ----------------------------
-- Table structure for esb_interface_params
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_params`;
CREATE TABLE `esb_interface_params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `param_expression` varchar(50) NOT NULL COMMENT '参数值表达式,例如主动输出接口的某一参数是对应输入接口数据中的 data 属性的值，可使用表达式$p{data},某体的表达式类型参考文档',
  `param_name` varchar(50) NOT NULL COMMENT '参数名称',
  `param_type` int NOT NULL COMMENT '参数类型：1 string  2 int 3 boolean 4 long',
  `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口参数表';

-- ----------------------------
-- Table structure for esb_protocol
-- ----------------------------
DROP TABLE IF EXISTS `esb_protocol`;
CREATE TABLE `esb_protocol` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `protocol_name` varchar(20) NOT NULL COMMENT '协议名称，目前支持WebService,RestApi',
  `protocol_type` varchar(3) NOT NULL COMMENT '协议类型 10 RestApi 20 WebService 30 Socket 40 MQ',
  `version_number` varchar(20) NOT NULL COMMENT '版本号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口传输协议表';

-- ----------------------------
-- Records of esb_protocol
-- ----------------------------

-- ----------------------------
-- Table structure for esb_whitelist
-- ----------------------------
DROP TABLE IF EXISTS `esb_whitelist`;
CREATE TABLE `esb_whitelist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `app_id` bigint(20) NOT NULL COMMENT '应用ID',
  `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
  `ips` varchar(500) NOT NULL COMMENT '白名单ip;分号分隔',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='白名单信息表';

-- ----------------------------
-- Records of esb_whitelist
-- ----------------------------

-- ----------------------------
-- Table structure for esb_interface_application
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_application`;
CREATE TABLE `esb_interface_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `app_id` bigint(20) NOT NULL COMMENT '应用ID',
  `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口应用对应关系表';

-- ----------------------------
-- Table structure for esb_interface_crypt
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_crypt`;
CREATE TABLE `esb_interface_crypt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
  `crypt_id` bigint(20) NOT NULL COMMENT '加解密(esb_encrypt_decrypt)ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口加解密关系表';

-- ----------------------------
-- Records of esb_interface_application
-- ----------------------------

-- ----------------------------
-- Table structure for esb_port_invoke_approve
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_invoke_approve`;
CREATE TABLE `esb_interface_invoke_approve` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `send_app_id` bigint(20) NOT NULL COMMENT '发送应用ID',
  `rece_app_id` bigint(20) NOT NULL COMMENT '接收应用ID',
  `send_interface_id` bigint(20) NOT NULL COMMENT '发送接口ID',
  `rece_interface_id` bigint(20) NOT NULL COMMENT '接收接口ID',
  `approve_comment` varchar(200) DEFAULT NULL COMMENT '审核意见',
  `approve_date` datetime NOT NULL COMMENT '核审时间',
  `data_text` mediumtext COMMENT '接口数据，最大16M',
  `import_date` datetime NOT NULL COMMENT '数据接入时间',
  `is_approved` tinyint(1) DEFAULT '1' COMMENT '是否不通过  1 待审核 2 审核通过',
  `approve_user_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `approve_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口调用审核';

-- ----------------------------
-- Records of esb_port_invoke_approve
-- ----------------------------

-- ----------------------------
-- Table structure for esb_port_invoke_exception
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_invoke_exception`;
CREATE TABLE `esb_interface_invoke_exception` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `send_app_id` bigint(20) NOT NULL COMMENT '发送应用ID',
  `rece_app_id` bigint(20) NOT NULL COMMENT '接收应用ID',
  `send_interface_id` bigint(20) NOT NULL COMMENT '发送接口ID',
  `rece_interface_id` bigint(20) NOT NULL COMMENT '接收接口ID',
  `handle_type` int(2) DEFAULT '0' COMMENT '异常处理方式，0 待处理 10 驳回 20 重发',
  `hanle_time` datetime DEFAULT NULL COMMENT '异常被处理时间',
  `is_handled` tinyint(1) DEFAULT '2'  NULL COMMENT '异常是否已经被处理 1 已经处理 2 未处理',
  `ocurrence_time` datetime NOT NULL COMMENT '异常发生时间',
  `handle_user_id` bigint(20) DEFAULT NULL COMMENT '处理异常的用户ID',
  `data_text` mediumtext COMMENT '接口数据',
  `exception_info` varchar(500) DEFAULT NULL COMMENT '异常信息，例如错误码，错误栈信息等',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口调用异常记录表';

-- ----------------------------
-- Records of esb_port_invoke_exception
-- ----------------------------

-- ----------------------------
-- Table structure for esb_port_invoke_log
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_invoke_log`;
CREATE TABLE `esb_interface_invoke_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `send_app_id` bigint(20) NOT NULL COMMENT '发送应用ID',
  `rece_app_id` bigint(20) NOT NULL COMMENT '接收应用ID',
  `send_interface_id` bigint(20) NOT NULL COMMENT '发送接口ID',
  `rece_interface_id` bigint(20) NOT NULL COMMENT '接收接口ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `begin_time` datetime NOT NULL COMMENT '调用开始时间',
  `end_time` datetime NOT NULL COMMENT '调用完成时间',
  `total_time` bigint(20) NOT NULL COMMENT '调用总时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口调用日志';

-- ----------------------------
-- Records of esb_port_invoke_log
-- ----------------------------

-- ----------------------------
-- Table structure for esb_port_schedule
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_schedule`;
CREATE TABLE `esb_interface_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `schedule_days` varchar(100) NOT NULL COMMENT '日期定义的数值，使用comma(,)隔开，这里的每一个数据值根据scheduleType值的不同而不同，如果scheduleType的值为月，那么这里就代表几号，如果scheduleType是周，则代表周几，如果scheduleType为天，那么就代表几天',
  `schedule_times` varchar(250) NOT NULL COMMENT '定义的时间点，格式为HH:mm:ss，每个格式使用comma(,)隔开',
  `schedule_type` varchar(2) NOT NULL COMMENT '定时类别，按月，接星期，按每几天，分别可使用 月，周，日作为存储值',
  `port_id` bigint(20) NOT NULL COMMENT '接口ID',
  `preset_port_id` bigint(20) DEFAULT NULL COMMENT '前置接口（在调用当前调置接口之前调用）ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口调用计划表';

-- ----------------------------
-- Records of esb_port_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for esb_port_template
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_template`;
CREATE TABLE `esb_interface_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `template_name` varchar(50) DEFAULT NULL COMMENT '数据模版名称',
  `store_db` tinyint(1) NOT NULL COMMENT '是否入库 1 入 2 不入',
  `store_kafka` tinyint(1) NOT NULL COMMENT '是否入kafka 1 入 2 不入',
  `table_name` varchar(50) NOT NULL COMMENT '对应数据库表名称',
  `topic_id` bigint(20) DEFAULT NULL COMMENT '关联kafka topic',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口数据模版表';

-- ----------------------------
-- Records of esb_port_template
-- ----------------------------



-- ----------------------------
-- Table structure for esb_report
-- ----------------------------
DROP TABLE IF EXISTS `esb_report`;
CREATE TABLE `esb_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `data_source` varchar(20) NOT NULL COMMENT '数据来源，对应mongodb的document',
  `name` varchar(20) NOT NULL COMMENT '报表名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表信息表';

-- ----------------------------
-- Records of esb_report
-- ----------------------------

-- ----------------------------
-- Table structure for esb_report_columns
-- ----------------------------
DROP TABLE IF EXISTS `esb_report_columns`;
CREATE TABLE `esb_report_columns` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `aggregation` varchar(10) DEFAULT NULL COMMENT '聚合字段的聚合方式,例如max,min,sum,avg等',
  `display_name` varchar(20) NOT NULL COMMENT '报表字段在用户界面上显示的名称',
  `format` varchar(20) DEFAULT NULL COMMENT '字段的格式',
  `is_filter` bit(1) NOT NULL COMMENT '是否作为查询条件',
  `is_group` bit(1) NOT NULL COMMENT '是否group',
  `type` varchar(20) NOT NULL COMMENT '报表字段类型',
  `value_expression` varchar(20) NOT NULL COMMENT '报表字段取值表达式',
  `report_id` bigint(20) DEFAULT NULL COMMENT '报表ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表信息字段名细表';

-- ----------------------------
-- Records of esb_report_columns
-- ----------------------------

-- ----------------------------
-- Table structure for esb_role
-- ----------------------------
DROP TABLE IF EXISTS `esb_role`;
CREATE TABLE `esb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表信息字段名细表';



-- ----------------------------
-- Records of esb_user_role
-- ----------------------------



DROP TABLE IF EXISTS `esb_operate_log`;
CREATE TABLE IF NOT EXISTS `esb_operate_log` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id自增ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(100) NULL COMMENT '操作人',
  `operate_type` int NOT NULL COMMENT '操作类型 10 添加白名单 11 修改白名单 .....',
  `operate_type_des` varchar(100) NULL COMMENT '操作类型描述',
  `ip` varchar(15) NOT NULL COMMENT 'ip地址',
  `operate_time` timestamp NULL DEFAULT NULL COMMENT '操作日期',
  `remark` varchar(250) NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Table structure for esb_use_log
-- ----------------------------
DROP TABLE IF EXISTS `esb_use_log`;
CREATE TABLE `esb_use_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `opt_type` varchar(20) NOT NULL COMMENT '登录类型（登录/退出）1 登录 2 退出',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统登录日志信息表';

-- ----------------------------
-- Records of esb_use_log
-- ----------------------------


-- ----------------------------
-- Table structure for esb_interface
-- ----------------------------

DROP TABLE IF EXISTS `esb_interface`;
CREATE TABLE `esb_interface` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `protocol_type` varchar(8) NULL COMMENT '协议类型：http:// https://',
  `server_ip` varchar(15) NOT NULL COMMENT '服务IP',
  `server_port` int(6) NOT NULL COMMENT '端口',
  `send_url` varchar(100) DEFAULT NULL COMMENT '请求url',
  `description` varchar(50) DEFAULT NULL COMMENT '接口描述',
  `key_property` varchar(20) DEFAULT NULL COMMENT '接口对应数据类型的唯一标识字段名称（通常是原数据的id字段）',
  `interface_name` varchar(50) NOT NULL COMMENT '接口名称',
  `need_approve` bit(1) DEFAULT NULL COMMENT '是否需要审核',
  `request_mime` varchar(20) DEFAULT NULL COMMENT '定义RestApi接口的时候用到，RestApi接口的请求MIME类型，application-json/ form-data等',
  `request_type` varchar(20) DEFAULT NULL COMMENT '定义RestApi接口的时候用到，RestApi接口的请求方式，get/post',
  `response_type` varchar(20) DEFAULT NULL COMMENT '定义RestApi接口的时候用到，返回类型',
  `target_name_space` varchar(200) DEFAULT NULL COMMENT '定义webService主动接口的时候用到，无需填写，通过调用ESB-PORT模块的方法获取（以wsdl地址作为参数）',
  `ws_method` varchar(200) DEFAULT NULL COMMENT '定义webService主动接口的时候用到，通过调用ESB-PORT模块的方法获取（以wsdl地址作为参数）获取webService方法列表供用户选择',
  `type_transfer` varchar(2) NOT NULL COMMENT '输入输出类型 10: 输入 A-接口(接口平台提供统) 20: 输出 接口-B(包含异步回传) 30 通知广播 接口-A、B、C、D',
  `protocol_id` bigint(20) NOT NULL COMMENT '协议ID',
  `template_id` bigint(20) DEFAULT NULL COMMENT '接口模版ID',
  `is_enabled` int(1) DEFAULT '2' COMMENT '应用状态：1 启用 2 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8 COMMENT='接口配置表';

-- ----------------------------
-- Table structure for esb_interface_params
-- ----------------------------

DROP TABLE IF EXISTS `esb_interface_params`;
CREATE TABLE `esb_interface_params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `param_expression` varchar(50) NOT NULL COMMENT '参数值表达式,例如主动输出接口的某一参数是对应输入接口数据中的 data 属性的值，可使用表达式$p{data},某体的表达式类型参考文档',
  `param_name` varchar(50) NOT NULL COMMENT '参数名称',
  `param_type` int NOT NULL COMMENT '参数类型：1 string  2 int 3 boolean 4 long',
  `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接口参数表';

-- ----------------------------
-- Table structure for esb_interface_application
-- ----------------------------
DROP TABLE IF EXISTS `esb_interface_application`;
CREATE TABLE `esb_interface_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `send_app_id` bigint(20) NOT NULL COMMENT '发送应用ID',
  `send_interface_id` bigint(20) NOT NULL COMMENT '发送接口ID',
  `topic_id` bigint(20) NOT NULL COMMENT 'topic_id',
  `is_enabled` int(1) DEFAULT '2' COMMENT '应用状态：1 启用 2 停用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口应用绑定关系表';

-- ----------------------------
-- Table structure for esb_interface_application_detail
-- ----------------------------

DROP TABLE IF EXISTS `esb_interface_application_detail`;
CREATE TABLE `esb_interface_application_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `esb_if_app_Id` bigint(20) NOT NULL COMMENT '接口应用绑定关系表Id',
  `rece_app_id` bigint(20) NOT NULL COMMENT '接收应用ID',
  `rece_interface_id` bigint(20) NOT NULL COMMENT '接收接口ID',
  `connection_id` bigint(20) NOT NULL COMMENT '连接配置表ID 获取超时时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `yn` tinyint(1) DEFAULT '1' COMMENT '是否删除 0 无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口应用绑定关系详情表';