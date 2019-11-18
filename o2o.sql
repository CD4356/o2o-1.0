/*
Navicat MySQL Data Transfer

Source Server         : cd4356
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : o2o

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-11-18 19:39:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `area_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `area_name` varchar(100) NOT NULL COMMENT '区域名称',
  `priority` int(3) NOT NULL DEFAULT '0' COMMENT '权重/优先级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES ('1', '爱联', '1', '2019-10-02 23:33:32', '2019-10-10 16:40:10');
INSERT INTO `area` VALUES ('2', '荷坳', '2', '2019-10-03 02:34:08', '2019-10-10 16:40:14');
INSERT INTO `area` VALUES ('3', '颐安', '0', '2019-10-16 03:02:30', '2019-10-16 03:02:34');

-- ----------------------------
-- Table structure for head_line
-- ----------------------------
DROP TABLE IF EXISTS `head_line`;
CREATE TABLE `head_line` (
  `line_id` int(10) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(100) DEFAULT NULL,
  `line_link` varchar(100) NOT NULL,
  `line_img` varchar(100) NOT NULL,
  `priority` int(2) DEFAULT NULL,
  `enable_status` int(2) DEFAULT '0' COMMENT '0:不可用 1:可用',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of head_line
-- ----------------------------
INSERT INTO `head_line` VALUES ('1', '1', 'https://github.com/CD4356', '/upload/headLine/2017061320315746624.jpg', '3', '1', '2019-11-04 15:04:21', '2019-11-04 15:04:24');
INSERT INTO `head_line` VALUES ('2', '2', 'http://m.sui.taobao.org/extends/#swiper', '/upload/headLine/2017061320393452772.jpg', '2', '1', null, null);
INSERT INTO `head_line` VALUES ('3', '3', 'https://blog.csdn.net/weixin_42950079', '/upload/headLine/2017061320400198256.jpg', '1', '1', null, null);

-- ----------------------------
-- Table structure for local_account
-- ----------------------------
DROP TABLE IF EXISTS `local_account`;
CREATE TABLE `local_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(28) NOT NULL,
  `password` varchar(28) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `person_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pid` (`person_id`),
  KEY `username` (`username`) USING BTREE,
  CONSTRAINT `fk_pid` FOREIGN KEY (`person_id`) REFERENCES `person` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_account
-- ----------------------------
INSERT INTO `local_account` VALUES ('14', 'root', 'root', '2019-11-16 03:12:13', '2019-11-16 03:12:13', '1');

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '个人id',
  `name` varchar(32) NOT NULL COMMENT '用户名',
  `pwd` varchar(255) DEFAULT NULL,
  `profile_img` varchar(1024) DEFAULT NULL COMMENT '头像地址',
  `email` varchar(1024) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `enable_status` int(2) NOT NULL DEFAULT '1' COMMENT '0:禁止使用本商城,1:允许使用本商城',
  `person_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:顾客, 2:商家, 3:超级管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', '宝宝', '123', '/upload/local/宝宝/2019111416022456129.jpg', '372437614', '男', '1', '2', '2019-10-05 20:33:43', '2019-10-05 20:33:49');
INSERT INTO `person` VALUES ('2', '宝妈', '123', '/upload/local/宝宝/2019111416022456129.jpg', null, null, '1', '1', '2019-11-14 16:02:20', '2019-11-14 16:02:20');
INSERT INTO `person` VALUES ('3', '辣妹', '123', '/upload/local/辣妹/2019111418065650111.jpg', null, null, '1', '1', '2019-11-14 18:06:56', '2019-11-14 18:06:56');
INSERT INTO `person` VALUES ('4', '强哥', '123', '/upload/local/强哥/2019111421133811862.png', null, null, '1', '1', '2019-11-14 21:13:39', '2019-11-14 21:13:39');
INSERT INTO `person` VALUES ('5', '咪咪', '123', '/upload/local/咪咪/2019111519184656000.jpg', null, null, '1', '1', '2019-11-15 19:18:45', '2019-11-15 19:18:45');
INSERT INTO `person` VALUES ('6', '路西', '123', '/upload/local/路西/2019111519215515869.gif', null, null, '1', '1', '2019-11-15 19:21:54', '2019-11-15 19:21:54');
INSERT INTO `person` VALUES ('7', '小仙女', '123', '/upload/local/小仙女/2019111520075154703.jpg', null, null, '1', '1', '2019-11-15 20:07:51', '2019-11-15 20:07:51');
INSERT INTO `person` VALUES ('8', '小小', '123', '/upload/local/小小/20191117205836109117.jpg', null, null, '1', '1', '2019-11-17 20:58:37', '2019-11-17 20:58:37');
INSERT INTO `person` VALUES ('9', '潇潇', '123', '/upload/local/潇潇/20191117225551108941.jpg', null, null, '1', '1', '2019-11-17 22:55:52', '2019-11-17 22:55:52');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(40) NOT NULL,
  `product_desc` varchar(1000) DEFAULT NULL,
  `img_address` varchar(100) DEFAULT '',
  `normal_price` varchar(10) DEFAULT NULL COMMENT '原价',
  `promotion_price` varchar(10) DEFAULT NULL COMMENT '折扣价',
  `priority` int(3) DEFAULT '0',
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '商品状态 0：商品下架 1：在前端展示界面展示',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `product_category_id` int(11) DEFAULT NULL COMMENT '商品分类id',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺id',
  PRIMARY KEY (`product_id`),
  KEY `fk_shop_product` (`shop_id`),
  KEY `fk_product_category_id` (`product_category_id`),
  CONSTRAINT `fk_product_category_id` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`product_category_id`),
  CONSTRAINT `fk_shop_product` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('2', '五谷金汤渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111101345169795.jpg', '18.0', '16.5', '99', '1', '2019-10-19 00:16:38', '2019-11-16 18:05:07', '16', '23');
INSERT INTO `product` VALUES ('3', '五谷招牌渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001180446865.jpg', '17.0', '16.0', '98', '1', '2019-10-19 00:19:08', '2019-11-13 14:50:03', '16', '23');
INSERT INTO `product` VALUES ('4', '五谷原味渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001193856461.jpg', '18.0', '16.0', '97', '1', '2019-10-19 00:19:54', '2019-11-10 01:19:38', '16', '23');
INSERT INTO `product` VALUES ('5', '五谷麻辣渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001202110816.jpg', '17.0', '16.0', '96', '1', '2019-10-19 00:22:43', '2019-11-10 01:20:22', '16', '23');
INSERT INTO `product` VALUES ('7', '鱼头酸菜面', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001210132255.jpg', '17.0', '16.0', '94', '1', '2019-10-19 00:24:17', '2019-11-10 01:21:02', '18', '23');
INSERT INTO `product` VALUES ('8', '五谷鱼头渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001215211955.jpg', '17.0', '16.0', '93', '1', '2019-10-19 00:24:58', '2019-11-10 01:21:52', '16', '23');
INSERT INTO `product` VALUES ('9', '五谷花甲渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001223934046.jpg', '19.0', '17.5', '92', '1', '2019-10-19 00:26:06', '2019-11-10 01:22:40', '16', '23');
INSERT INTO `product` VALUES ('10', '五谷鲜虾渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001240770925.jpg', '18.0', '16.5', '90', '1', '2019-10-19 00:27:10', '2019-11-10 01:24:08', '16', '23');
INSERT INTO `product` VALUES ('11', '五谷肉末粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001243659125.jpg', '17.0', '16.0', '89', '1', '2019-10-19 00:28:46', '2019-11-10 01:24:37', '16', '23');
INSERT INTO `product` VALUES ('12', '五谷牛肉丸粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001251113053.jpg', '18.0', '16.5', '88', '1', '2019-10-19 00:29:19', '2019-11-10 01:25:11', '16', '23');
INSERT INTO `product` VALUES ('13', '五谷番茄渔粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001253123057.jpg', '17.0', '16.0', '87', '1', '2019-10-19 00:29:43', '2019-11-10 01:25:31', '16', '23');
INSERT INTO `product` VALUES ('15', '五谷豆豉鲮鱼粉', '老板，麻烦再来一碗这个口味的粉', '/upload/shop/23/2019111001255867973.jpg', '18.0', '16.5', '86', '1', '2019-10-19 00:30:10', '2019-11-12 02:57:35', '16', '23');
INSERT INTO `product` VALUES ('17', '五谷鱼排渔粉', '美食美味，美味美食！', '/upload/shop/23/2019111001233851347.jpg', '19.0', '17.5', '92', '1', '2019-10-19 00:30:43', '2019-11-10 01:23:39', '16', '23');
INSERT INTO `product` VALUES ('18', '奶绿', '~捧在手心里的 ~ 是你醇香的爱~', '/upload/shop/29/2019102216135161528.jpg', '20.0', '18.0', '20', '1', '2019-10-22 16:13:52', '2019-11-04 01:23:14', '28', '29');
INSERT INTO `product` VALUES ('20', '晨曦', '~温柔的唤醒 ~ 这便是晨曦的魅力~', '/upload/shop/29/2019102622182096648.jpg', '17.0', '17.0', '23', '1', '2019-10-26 22:18:21', '2019-10-29 09:35:43', '29', '29');
INSERT INTO `product` VALUES ('22', '珍珠奶茶', '奶茶入口，唇齿留香。\n珍珠弹牙，乐趣多多。', '/upload/shop/29/2019110313451972806.jpg', '16.0', '16.0', '27', '1', '2019-11-03 13:45:19', '2019-11-03 13:45:19', '29', '29');
INSERT INTO `product` VALUES ('24', 'aa', '', '/upload/shop/36/2019111617125583351.jpg', '12.0', '11.0', '23', '1', '2019-11-16 17:12:55', '2019-11-16 17:18:09', '41', '36');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(40) NOT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `shop_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_category_id`),
  KEY `fk_shop_id` (`shop_id`),
  CONSTRAINT `fk_shop_id` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('16', '渔粉（面）', '8', '2019-10-19 00:12:35', '23');
INSERT INTO `product_category` VALUES ('18', '特色粉面', '99', '2019-10-19 00:13:23', '23');
INSERT INTO `product_category` VALUES ('19', '手工水饺', '5', '2019-10-19 00:13:49', '23');
INSERT INTO `product_category` VALUES ('20', '风味小吃', '3', '2019-10-19 00:14:30', '23');
INSERT INTO `product_category` VALUES ('21', '加料区', '0', '2019-10-19 00:14:53', '23');
INSERT INTO `product_category` VALUES ('27', '饮料', '0', '2019-10-20 04:31:26', '23');
INSERT INTO `product_category` VALUES ('28', '招牌系列', '9', '2019-10-22 14:23:53', '29');
INSERT INTO `product_category` VALUES ('29', '果茶系列', '8', '2019-10-22 14:23:53', '29');
INSERT INTO `product_category` VALUES ('30', '咖啡系列', '6', '2019-10-22 14:23:53', '29');
INSERT INTO `product_category` VALUES ('31', '原创奶茶', '7', '2019-10-22 14:23:53', '29');
INSERT INTO `product_category` VALUES ('40', 'haha', '111', '2019-11-16 16:57:06', '36');
INSERT INTO `product_category` VALUES ('41', 'bbb', '222', '2019-11-16 17:11:40', '36');

-- ----------------------------
-- Table structure for product_img
-- ----------------------------
DROP TABLE IF EXISTS `product_img`;
CREATE TABLE `product_img` (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品图片id',
  `product_detail_img` varchar(100) NOT NULL COMMENT '图片地址',
  `img_desc` varchar(1000) DEFAULT NULL,
  `priority` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`product_img_id`),
  KEY `fk_product_id` (`product_id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_img
-- ----------------------------
INSERT INTO `product_img` VALUES ('1', '/upload/shop/29/2019102216135299361.jpg', null, null, null, '18');
INSERT INTO `product_img` VALUES ('2', '/upload/shop/29/2019102216135289761.jpg', null, null, null, '18');
INSERT INTO `product_img` VALUES ('8', '/upload/shop/29/2019102909354367997.jpg', null, null, null, '20');
INSERT INTO `product_img` VALUES ('9', '/upload/shop/29/2019102909354462438.jpg', null, null, null, '20');
INSERT INTO `product_img` VALUES ('12', '/upload/shop/29/2019110313451938217.jpg', null, null, null, '22');
INSERT INTO `product_img` VALUES ('13', '/upload/shop/29/2019110313451915658.jpg', null, null, null, '22');
INSERT INTO `product_img` VALUES ('14', '/upload/shop/23/2019111101254130434.jpg', null, null, '2019-11-11 01:25:42', '2');
INSERT INTO `product_img` VALUES ('15', '/upload/shop/23/2019111101254125911.jpg', null, null, '2019-11-11 01:25:42', '2');
INSERT INTO `product_img` VALUES ('16', '/upload/shop/23/2019111101254191857.jpg', null, null, '2019-11-11 01:25:42', '2');
INSERT INTO `product_img` VALUES ('26', '/upload/shop/36/2019111617125572102.jpg', null, null, '2019-11-16 17:12:55', '24');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `shop_name` varchar(20) DEFAULT NULL COMMENT '//店铺名称',
  `shop_desc` varchar(1000) DEFAULT NULL COMMENT '店铺描述',
  `shop_address` varchar(100) DEFAULT NULL COMMENT '店铺地址',
  `shop_phone` varchar(20) DEFAULT NULL COMMENT '商家电话',
  `shop_img` varchar(100) DEFAULT NULL COMMENT '店铺图片',
  `priority` int(3) DEFAULT '0' COMMENT '权重',
  `advice` varchar(1000) DEFAULT NULL COMMENT '超级管理员给商家的提醒',
  `enable_status` int(2) DEFAULT '0' COMMENT '店铺状态 -1:不可用 0:审核 1:可用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最近更新时间',
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `shop_category_id` int(11) DEFAULT NULL COMMENT '店铺类别',
  `area_id` int(4) DEFAULT NULL COMMENT '店铺所属区域',
  PRIMARY KEY (`shop_id`),
  KEY `fk_owner_id` (`owner_id`),
  KEY `fk_area_id` (`area_id`),
  KEY `fk_shop_category_id` (`shop_category_id`),
  CONSTRAINT `fk_area_id` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`),
  CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `person` (`user_id`),
  CONSTRAINT `fk_shop_category_id` FOREIGN KEY (`shop_category_id`) REFERENCES `shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('22', '暧昧咖啡馆', '为广大情侣提供舒心的约会环境', '爱联A区', '13149004370', '/upload/shop/22/20191015223352104039.jpg', '98', '审核通过', '1', '2019-10-11 17:29:49', '2019-10-15 22:33:53', '1', '7', '1');
INSERT INTO `shop` VALUES ('23', '五谷鱼粉', '五谷有根，渔粉有道', '颐安一期2栋103号', '13751017024', '/upload/shop/23/2019111222372186130.jpg', '99', '审核通过', '1', '2019-10-16 03:52:40', '2019-11-12 22:37:21', '1', '12', '3');
INSERT INTO `shop` VALUES ('24', '物碗粥', '一碗粥，一天好心情', '荷坳饮食一条街', '13430993417', '/upload/shop/24/2019101813245078319.jpg', '95', '审核通过', '1', '2019-10-16 07:50:28', '2019-10-18 13:24:50', '1', '10', '2');
INSERT INTO `shop` VALUES ('25', '老北京炸酱面', '老北京炸酱面，带着传统气息的中国美食', '颐安一期2栋107号', '13430993184', '/upload/shop/25/2019101607560040638.png', '0', '审核通过', '1', '2019-10-16 07:56:00', '2019-10-16 07:56:00', '1', '12', '3');
INSERT INTO `shop` VALUES ('26', '汤粉世家', '汤粉世家为您服务', '颐安一期2栋104号', '13430990310', '/upload/shop/26/2019101607595115600.jpg', '96', '审核通过', '1', '2019-10-16 07:59:51', '2019-10-16 07:59:51', '1', '12', '3');
INSERT INTO `shop` VALUES ('27', '兰州拉面', '兰州拉面，最正宗的味道', '爱联A区', '13430455016', '/upload/shop/27/2019101608063478333.jpg', '97', '审核通过', '1', '2019-10-16 08:06:35', '2019-10-16 08:06:35', '1', '12', '1');
INSERT INTO `shop` VALUES ('28', '品蚝轩烧烤', '点炒米粉送可乐', '荷坳榕城坊美食城', '18688754664', '/upload/shop/28/2019102202153683616.jpg', '98', '审核通过', '1', '2019-10-22 02:15:37', '2019-10-22 02:15:37', '1', '14', '2');
INSERT INTO `shop` VALUES ('29', '一点点奶茶', '一杯奶茶一句问候\n邀你共享美味时刻', '颐安一期1栋116号', '13370755205', '/upload/shop/29/2019102214160478599.jpg', '98', '审核通过', '1', '2019-10-22 14:16:05', '2019-10-22 14:16:05', '1', '17', '3');
INSERT INTO `shop` VALUES ('30', '语轩烧烤', '下单送宿舍，请提前下单，谢谢配合', '大围桂萍路46号102', '17478880150', '/upload/shop/30/20191024223314103440.jpg', '0', '审核通过', '1', '2019-10-24 22:33:15', '2019-10-24 22:33:15', '1', '14', '2');
INSERT INTO `shop` VALUES ('36', 'AC', '', '阿斯蒂', '123456', '/upload/shop/36/20191116165532102902.jpg', '0', '审核通过', '1', '2019-11-16 16:08:09', '2019-11-16 16:55:33', '5', '17', '2');
INSERT INTO `shop` VALUES ('38', '咪咪奶茶', '', '', '', null, '0', '审核中', '0', '2019-11-16 16:40:08', '2019-11-16 16:40:08', '5', '17', '2');
INSERT INTO `shop` VALUES ('39', '咪咪烧烤', '', '', '', '/upload/shop/39/2019111616425142773.jpg', '0', '审核中', '0', '2019-11-16 16:42:40', '2019-11-16 16:42:41', '5', '17', '2');

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺类别id',
  `shop_category_name` varchar(20) NOT NULL DEFAULT '' COMMENT '店铺类别名称',
  `shop_category_desc` varchar(1000) DEFAULT '' COMMENT '店铺类别简介',
  `shop_category_img` varchar(1000) DEFAULT NULL COMMENT '店铺类别图片',
  `priority` int(2) NOT NULL DEFAULT '0' COMMENT '店铺类别权重',
  `create_time` datetime DEFAULT NULL COMMENT '店铺类别创建时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '店铺类别最近更新时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '店铺类别上级分类id',
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_parentId` (`parent_id`),
  CONSTRAINT `fk_parentId` FOREIGN KEY (`parent_id`) REFERENCES `shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop_category
-- ----------------------------
INSERT INTO `shop_category` VALUES ('5', '美容美发', '让你焕发迷人光彩', '/upload/shopCategory/2017061223273314635.png', '1', '2019-10-09 02:05:58', '2019-10-09 02:06:01', null);
INSERT INTO `shop_category` VALUES ('6', '美食饮品', '享受美食，享受生活', '/upload/shopCategory/2017061223274213433.png', '1', '2019-10-09 02:06:32', '2019-10-09 02:06:48', null);
INSERT INTO `shop_category` VALUES ('7', '咖啡', '二级分类', null, '0', '2019-10-09 02:07:53', '2019-10-09 02:07:58', '6');
INSERT INTO `shop_category` VALUES ('8', '自助餐', '二级分类', null, '0', '2019-10-09 16:04:10', '2019-10-09 16:04:13', '6');
INSERT INTO `shop_category` VALUES ('9', '快餐', '二级分类', null, '0', '2019-10-16 03:06:13', '2019-10-16 03:06:16', '6');
INSERT INTO `shop_category` VALUES ('10', '粥类', '二级分类', null, '0', '2019-10-16 03:07:08', '2019-10-16 03:07:11', '6');
INSERT INTO `shop_category` VALUES ('11', '麻辣烫', '二级分类', null, '0', '2019-10-16 03:07:42', '2019-10-16 03:07:45', '6');
INSERT INTO `shop_category` VALUES ('12', '米粉面馆', '二级分类', null, '0', '2019-10-16 03:08:15', '2019-10-16 03:08:17', '6');
INSERT INTO `shop_category` VALUES ('13', '炸鸡汉堡', '二级分类', null, '0', '2019-10-16 03:08:43', '2019-10-16 03:08:46', '6');
INSERT INTO `shop_category` VALUES ('14', '烧烤', '二级分类', null, '0', '2019-10-16 03:09:24', '2019-10-16 03:09:28', '6');
INSERT INTO `shop_category` VALUES ('15', '蛋糕甜点', '二级分类', null, '0', '2019-10-16 03:10:44', '2019-10-16 03:10:47', '6');
INSERT INTO `shop_category` VALUES ('16', '肠粉', '二级分类', null, '0', '2019-10-16 03:11:15', '2019-10-16 03:11:19', '6');
INSERT INTO `shop_category` VALUES ('17', '奶茶', '二级分类', null, '1', '2019-10-22 14:12:06', '2019-10-22 14:12:08', '6');
INSERT INTO `shop_category` VALUES ('18', '休闲娱乐', '放松心情，享受生活', '/upload/shopCategory/2017061223275121460.png', '1', '2019-11-15 12:35:52', '2019-11-15 12:35:55', null);
INSERT INTO `shop_category` VALUES ('19', '教育培训', '活到老，学到老', '/upload/shopCategory/2017061223280082147.png', '1', '2019-11-15 12:38:07', '2019-11-15 12:38:10', null);
INSERT INTO `shop_category` VALUES ('20', '二手市场', '二手市场', '/upload/shopCategory/2017061223272255687.png', '1', '2019-11-15 13:08:02', '2019-11-15 13:08:05', null);
INSERT INTO `shop_category` VALUES ('21', '租赁市场', '租赁市场', '/upload/shopCategory/2017061223281361578.png', '1', '2019-11-15 13:10:09', '2019-11-15 13:10:07', null);
