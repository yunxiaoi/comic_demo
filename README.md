针对当前主流漫画平台功能臃肿、社区互动薄弱、冷门作品曝光难等痛点，本项目设计并实现了一套轻量化漫画分享与阅读系统。

系统面向 管理员 与 普通用户 两类角色，实现了漫画内容管理、社区互动交流、付费内容解锁三大核心业务，为漫画爱好者提供简洁高效的阅读与交流环境。

后端：Spring Boot 3 + MyBatis + MySQL + JWT

前端：Vue 3 + Element-Plus + Pinia + Vue Router + Axios

优化：图片懒加载（Intersection Observer API）、ECharts 数据可视化

✨ 功能特性
👤 用户端
模块	功能
账号管理	手机号验证码注册、用户名/手机号+密码登录、个人信息修改、密码修改
首页浏览	轮播图、畅销热门、高能排行、完结佳作、热门资讯、热门帖子
漫画阅读	多条件分类检索（题材/地区/收费）、每周更新查看、漫画详情、章节阅读、收藏
付费购买	账户充值、余额购买付费漫画、订购记录查看
社区互动	浏览已审核帖子、发布/编辑帖子（待审核）、评论漫画/帖子、收藏
个人中心	我的帖子、我的订购、我的收藏、我的评论、阅读记录
🔧 管理员端
模块	功能
数据可视化	近一周消费金额折线图、漫画热度饼图、帖子数量柱状图、核心指标卡片
漫画管理	题材/地区管理、漫画信息增删改查（付费/免费）、章节图片上传、更新计划配置
社区管理	帖子分类管理、帖子审核（通过/驳回）、评论管理
订单管理	订购信息查看、充值记录查看
系统配置	轮播图管理、系统公告管理、用户封禁/解封
🛠 技术栈
后端
技术	版本	用途
Spring Boot	3.2.0	核心框架
MyBatis	3.0.3	持久层框架
MySQL	8.0+	关系型数据库
JWT (jjwt)	0.11.5	无状态身份认证
BCrypt	-	密码哈希加密
Hutool	5.8.25	工具类库
Maven	3.9+	项目构建
前端
技术	版本	用途
Vue	3.3	核心框架
Element-Plus	2.4	UI 组件库
Pinia	2.1	状态管理
Vue Router	4.2	前端路由
Axios	1.6	HTTP 请求
ECharts	5.4	数据可视化
Vite	5.0	构建工具

🚀 快速开始
环境要求
JDK 21

Maven 3.9+

Node.js 18+

MySQL 8.0+

IDE：IntelliJ IDEA / VS Code

1️⃣ 数据库初始化
sql
-- 创建数据库
CREATE DATABASE comic_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE comic_db;

-- 执行建表脚本
source comic-system-backend/src/main/resources/db/schema.sql;
⚠️ 建表脚本中已包含管理员初始账号：admin / admin123

2️⃣ 后端启动
bash
cd comic-system-backend

#修改 src/main/resources/application.yml 中的数据库连接信息
#spring.datasource.username:你的用户名
#spring.datasource.password:的密码

# 编译并启动
mvn clean compile
mvn spring-boot:run
后端服务运行在 http://localhost:8080

3️⃣ 前端启动
bash
cd comic-system-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
前端服务运行在 http://localhost:5173

4️⃣ 访问系统
角色	地址	账号
用户端	http://localhost:5173	自行注册
管理员端	http://localhost:5173/admin	admin / admin123
⚙️ 配置说明
application.yml 关键配置：

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/comic_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

jwt:
  secret: yourSecretKeyForJWTGenerationShouldBeLongEnough
  expiration: 86400000   # 24小时

file:
  upload-path: ./uploads/   # 图片上传路径
🔒 安全提示：生产环境请将数据库密码、JWT 密钥等敏感信息改用环境变量配置，不要直接提交到 Git。

🎯 技术亮点
1. 图片懒加载优化
基于 Vue 3 自定义指令 + Intersection Observer API 实现：

javascript
// src/directives/lazyload.js
export default {
  mounted(el, binding) {
    if (!window.IntersectionObserver) {
      el.src = binding.value
      return
    }
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          el.src = binding.value
          observer.unobserve(el)
        }
      })
    })
    observer.observe(el)
  }
}
效果：一个 50 图的章节，首屏图片请求数由 50 降至 2~3，下降约 94%。

2. 多条件组合检索
使用 MyBatis 动态 SQL + 全量 #{} 预编译：

xml
<sql id="whereCondition">
    <where>
        <if test="keyword != null and keyword != ''">
            AND (title LIKE CONCAT('%', #{keyword}, '%') OR author LIKE CONCAT('%', #{keyword}, '%'))
        </if>
        <if test="genreId != null">AND genre_id = #{genreId}</if>
        <if test="regionId != null">AND region_id = #{regionId}</if>
        <if test="isPaid != null">AND is_paid = #{isPaid}</if>
    </where>
</sql>
3. 事务一致性
购买付费漫画时使用 @Transactional 保证原子性：

java
@Transactional
public boolean purchase(Integer userId, Integer comicId, Double amount) {
    if (!userService.deductBalance(userId, amount)) return false;
    orderMapper.insert(userId, comicId, amount);
    comicService.incrementOrders(comicId);
    return true;
}
4. 阅读记录"一漫一条"
通过联合唯一索引实现：

sql
UNIQUE KEY `user_comic_unique` (`user_id`, `comic_id`)
重复阅读时只更新 read_time 和 chapter_id，避免记录冗余。

📊 性能测试
使用 Apache Bench 对核心接口进行压测（-n 1000 -c 100）：

接口	总请求数	并发数	平均响应 (ms)	QPS	失败率
首页轮播图	500	50	22	218	0%
漫画检索	1000	100	9	1030	0%
用户登录	200	20	76	25	0%
系统在 100 并发下稳定运行，满足"支持至少 100 并发用户"的设计目标。

🗄 数据库设计
共 17 张核心数据表：

分类	表名
用户	user、admin_info
漫画	comic、comic_chapter、comic_genre、comic_region、update_schedule
交易	user_order、recharge_record
社区	forum_post、post_category、comment
个性化	user_favorite、user_read_history
内容	comic_news、carousel、notice

📄 许可证
本项目采用 MIT License 开源协议。

本项目为本科毕业设计作品，仅供学习交流使用。

🙏 致谢
感谢指导老师在项目选题、设计与开发过程中的悉心指导。

<p align="center"> <i>如果这个项目对你有帮助，欢迎点个 ⭐ Star 支持一下！</i> </p> ```
