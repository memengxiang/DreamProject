/*
 Navicat Premium Data Transfer

 Source Server         : springboot
 Source Server Type    : MySQL
 Source Server Version : 50541
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 50541
 File Encoding         : 65001

 Date: 15/06/2021 15:09:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_account_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `modified_account_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
  `deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标识(0、否 1、是)',
  PRIMARY KEY (`account_id`) USING BTREE,
  INDEX `FK_account_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `FK_account_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账号表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 1, 'admin', '17a1640916cfa8356adc4336a72ac75d', 'ecbe5fac60d1499595fbb98dfa854501', 'admin', '男', 'mp@126.com', '2020-11-10 13:46:32', '2021-06-14 14:12:37', NULL, 1, 0);
INSERT INTO `account` VALUES (2, 1, 'mmx', '5c91eb3b781daccd7a6f50ddbd756940', '11e14ee19aaf4397a833a61733da1635', '马梦想', '男', '741@qq.com', '2021-06-11 17:18:53', '2021-06-11 17:50:45', 1, 2, 1);
INSERT INTO `account` VALUES (3, 1, 'mmx', '483241049467f4183a89f84db1b09a0b', '27eb2816cae242f8a3ec46fd4fed3c6d', '马梦想', '男', 'mmx@12.com', '2021-06-14 14:17:05', NULL, 1, NULL, 1);
INSERT INTO `account` VALUES (4, 6, 'coho', 'f59b2e70c4169c097dee96362c9c08db', 'f63cac4ccf0b4b3b847805e768241f85', '劳勤', '男', 'coho@2021.com', '2021-06-15 14:11:22', NULL, 1, NULL, 0);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customer_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_account_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `modified_account_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
  `deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标识(0、否 1、是)',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '张若男', '女', 23, '123@qq.com', '15896325484', '上海市闵行区', '2021-06-10 15:21:02', NULL, 1, NULL, 0);
INSERT INTO `customer` VALUES (2, '李飞', '男', 22, '1946@qq.com', '14326984523', '安徽省合肥市', '2021-06-10 15:21:02', NULL, 1, NULL, 0);
INSERT INTO `customer` VALUES (3, '王菲', '女', 33, '741@qq.com', '19875639851', '安徽省六安市', NULL, NULL, NULL, NULL, 0);
INSERT INTO `customer` VALUES (4, '马华', '男', 22, 'mahua@qq.com', '15488962315', '河南省郑州市', NULL, '2021-06-11 11:50:29', 1, 1, 1);
INSERT INTO `customer` VALUES (5, '赵龙123', '女', 32, 'zhaolong@213.com', '16238956161', '北京市海淀区', '2021-06-11 11:51:53', '2021-06-11 16:56:46', 1, 1, 0);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `resource_id` bigint(20) UNSIGNED NOT NULL COMMENT '主键',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '父id',
  `resource_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `resource_type` tinyint(4) NULL DEFAULT NULL COMMENT '资源类型(0、目录 1、菜单 2、按钮)',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识码',
  `sort` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, NULL, '系统管理', 0, NULL, NULL, 1);
INSERT INTO `resource` VALUES (2, NULL, '客户管理', 0, NULL, NULL, 2);
INSERT INTO `resource` VALUES (11, 1, '角色管理', 1, 'role/toList', NULL, 1);
INSERT INTO `resource` VALUES (12, 1, '账号管理', 1, 'account/toList', NULL, 2);
INSERT INTO `resource` VALUES (21, 2, '客户管理', 1, 'customer/toList', 'customer', 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_account_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人',
  `modified_account_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
  `deleted` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标识(0、否 1、是)',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '练习角色', '练习角色', NULL, NULL, NULL, NULL, 0);
INSERT INTO `role` VALUES (2, '操作员', '123', '2021-06-14 15:51:00', NULL, 1, NULL, 1);
INSERT INTO `role` VALUES (3, '管理员', '123', '2021-06-14 15:52:28', NULL, 1, NULL, 1);
INSERT INTO `role` VALUES (4, '测试', '测试', '2021-06-14 16:35:23', NULL, 1, NULL, 1);
INSERT INTO `role` VALUES (5, '梦想', '马梦想', '2021-06-15 10:21:12', '2021-06-15 13:46:00', 1, 1, 0);
INSERT INTO `role` VALUES (6, '普通员工', '普通', '2021-06-15 14:10:48', NULL, 1, NULL, 0);

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `role_resource_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`role_resource_id`) USING BTREE,
  INDEX `FK_role_resource_role_id`(`role_id`) USING BTREE,
  INDEX `FK_role_rerce_resourc_idD2D9`(`resource_id`) USING BTREE,
  CONSTRAINT `FK_role_rerce_resourc_idD2D9` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`resource_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_role_resource_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES (1, 1, 1);
INSERT INTO `role_resource` VALUES (2, 1, 2);
INSERT INTO `role_resource` VALUES (3, 1, 11);
INSERT INTO `role_resource` VALUES (4, 1, 12);
INSERT INTO `role_resource` VALUES (5, 1, 21);
INSERT INTO `role_resource` VALUES (54, 5, 1);
INSERT INTO `role_resource` VALUES (55, 5, 11);
INSERT INTO `role_resource` VALUES (56, 5, 12);
INSERT INTO `role_resource` VALUES (57, 5, 2);
INSERT INTO `role_resource` VALUES (58, 5, 21);
INSERT INTO `role_resource` VALUES (59, 6, 2);
INSERT INTO `role_resource` VALUES (60, 6, 21);

SET FOREIGN_KEY_CHECKS = 1;
