# NucleicAcidTest
核酸检测登记查询系统

natweb目录为前端项目 采用Vue+ElementUI+VantUI

natserver目录为后端项目 采用SpringBoot+SpringSecurity+Mybatis+MySQL

nat.sql 为数据库文件 (admin默认密码为123456)

（提醒：部署在服务器上时，请配置SSL证书，前端通过浏览器调用摄像头时必须为https协议）

## 项目配置

运行前请修改配置

前端配置路径 public/config.js

（微信公众平台也要配置相应的前端域名）

```javascript
const GlobeConfig={
    appid:"", //微信公众平台appid 必填用户端登记信息时需在微信内打开
    baseURL:"http://localhost:8888" //后端地址
}
```

后端配置路径 src/main/resources/application.yaml

运行前修改数据库连接地址、端口号以及微信相关配置

```yaml
WeChat: #微信公众号相关配置 用户端需要在微信内打开 此参数必须配置
  appid: 
  secret: 
```

## 项目功能简介

### 用户端

(路径:/#/user)

个人信息登记、个人信息二维码展示、检测结果查询

### 采集端

(路径:/#/operator)

通过扫描试管条码和个人信息二维码进行信息录入

### 后台

(路径:/#/admin)

后台有两种角色可登录，超级管理员和采集单位账号

超级管理员可进行检测结果查询、人员信息查询、以及所有单位的试管管理、任务管理和采集人员管理

单位账号可进行本单位下的试管管理、任务管理和采集人员管理