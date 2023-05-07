**不小心把仓库给设置成私有了，star全没了，大佬们再给点点吧**

# NucleicAcidTest
核酸检测登记查询系统

natweb目录为前端项目 采用Vue+ElementUI+VantUI

natserver目录为后端项目 采用SpringBoot+SpringSecurity+Mybatis+MySQL

nat.sql 为数据库文件，nat_with_v2_system为兼容微信登录和非微信登陆的数据库 (admin默认密码为123456)

（提醒：部署在服务器上时，请配置SSL证书，前端通过浏览器调用摄像头时必须为https协议）

功能演示视频：https://www.bilibili.com/video/BV1Cq4y1k71h/

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

目前可以支持不使用微信平台来进行用户数据录入功能，添加了一套独立的用户登录功能（开关位于`UserLoginConfig.useWechet`），请使用nat.sql数据库文件来兼容独立用户登录功能和硬件节点信息功能

### 采集端

(路径:/#/operator)

通过扫描试管条码和个人信息二维码进行信息录入

chrome 在47后调用navigator.mediaDevices.getUserMedia 仅能在在https和 loaclhost环境下才可以使用。本机调试的话**只有用户在浏览器端设置信任该域名才可以使用本地摄像头。**

#### 设置方法

地址栏输入`chrome://flags/`, 搜索`unsafely`

enabled 并填入要授信的域名。

### 后台

(路径:/#/admin)

后台有两种角色可登录，超级管理员和采集单位账号

超级管理员可进行检测结果查询、人员信息查询、以及所有单位的试管管理、任务管理和采集人员管理

单位账号可进行本单位下的试管管理、任务管理和采集人员管理

### 硬件节点

位于hardwareNode文件夹中，采用ESP12F Arduino开发，需要配置ssid和pwd，还有WebSocket的ip

## 参考项目

https://github.com/dragonir/h5-scan-qrcode

https://github.com/lin-xin/vue-manage-system/
