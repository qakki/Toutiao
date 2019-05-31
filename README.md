Record nowcoder project study 
牛客网项目课学习记录

总结
  using
    springboot+velocity+redis+mybatis+mysql+tomcat+nginx+git
  功能
    1. 用户登陆，注册功能
      · 用户名合法性及存在性检验
      · 用户密码强度检验
      · 密码salt+md5加密
      · 未登录权限控制，通过interceptor拦截器实现
      · 每次登陆发送站内信，记录时间ip，异步操作
      · 已登陆用户加cookie，数据库备份token
      · 退出用户token失效
    2. 新闻资讯功能
      · 添加新闻资讯入口
      · 图片转存，采用七牛云存储（原计划hdfs存储到虚拟机，机器太慢）
    3. 评论和站内信
      · 分页，使用了PageHealper，前端未增加
      · 站内信阅读之后，异步设置已读
      · 发送站内信，接口实现，前端未增加
    4. 点赞点踩
      · 使用JedisPool管理Jedis对象
      · 使用redis-list数据结构来存储点赞信息
      · 点赞后，异步发送通知告知新闻添加人
    5. 邮件发送
      · 邮件工具类，前端未配置
    6. 前端模板引擎
      · springboot2.0以上版本默认采用thymeleaf，springboot1.5以上不支持velocity
      · springboot采用的1.4.7 兼容velocity
    7. 持久层配置
      · Mysql部署在腾讯云虚拟机上
      · 持久层采用mybatis，mapper采用xml
      · mybatis-generate plugins初始化model类和dao
      · 数据库和表建立适用Navicat可视化创建
    8. 云服务器部署
      · 部署在腾讯云服务器Tomcat+Nginx转发
      · 域名花了1块钱买了一年
