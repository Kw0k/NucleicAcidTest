<template>
  <div>
    <van-nav-bar left-text="返回" left-arrow title="个人信息登记" @click-left="onClickLeft"/>
    <van-button icon="plus" class="addbutton" type="info" size="large" to="info/edit">添加个人信息</van-button>
    <van-divider>已登记信息</van-divider>
    <van-list finished-text="没有更多了">
      <van-cell v-for="item in list" :key="item.tname" :title="'姓名：' + item.tname + '\t身份证号：' + item.idcardnum"
                :to="'info/view?idcardnum=' + item.idcardnum">
        <van-icon size="35" name="arrow"/>
      </van-cell>
    </van-list>
  </div>
</template>

<script>
import {getUrlParam} from "@/components/js/GetUrlParam";
import axios from "axios";
import {Toast} from "vant";
import login from "../operator/login";

export default {
  name: "userInfo",
  data() {
    return {
      systemId: "systen_id",
      list: [],
    };
  },
  created() {
    if (UserLoginConfig.useWechet) {
      this.checkWechetLogin();
    } else {
      this.checkSystemLogin();
    }
  },
  methods: {
    checkSystemLogin() {
      console.log("执行系统登录检测");
      axios.post("/system-v2/checklogin").then((response) => {
        if (response.data.flag) {
          //已登陆
          console.log("已登录");
          //请求账号名下所有的身份信息，进行显示
          this.getSystemV2UserInfo()
        } else {
          Toast.fail("请先登录");
          this.$router.push("/user/system/login")
        }
      }).catch(function (error) {
        console.error(error)
      })
    },
    checkWechetLogin() {
      console.log("执行微信登录检测");
      axios.post(
          '/wechat/checklogin',
      ).then((response) => {
        if (response.data.flag) {
          //已登陆
          this.getWechetUserInfo()
        } else {
          this.getWechetCode()
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    onClickLeft() {
      //返回上一个界面
      this.$router.push("/user")
    },
    getWechetUserInfo() { //获取当前微信登记过的个人信息
      axios.post(
          '/wechat/userInfoAll'
      ).then((response) => {
        if (response.data.flag) {
          this.list = response.data.data;
        } else {
          Toast.fail(response.data.msg);
          if (response.data.code === 408) {
            this.$router.push("/user/info")
          }
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    getSystemV2UserInfo() {
      console.log("请求该账号下的等级信息")
      axios.post(
          '/system-v2/userInfoAll'
      ).then((response) => {
        if (response.data.flag) {
          console.log(response.data.data)
          this.list = response.data.data;
        } else {
          Toast.fail(response.data.msg);
        }
      }).catch(function (error) {
        console.error(error)
      })
    },
    login(code) {
      axios.post(
          '/wechat/login',
          this.qs.stringify({
            code: code
          })
      ).then((response) => {
        if (response.data.flag) {
          var _url = window.location.protocol + '//' + window.location.host + '/#/user/info';
          window.history.pushState({}, 0, _url)
          this.getWechetUserInfo()
        } else {
          console.log(response.data.msg)
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    },
    getWechetCode() {
      const code = getUrlParam('code') // 截取路径中的code，如果没有就去微信授权，如果已经获取到了就直接传code给后台
      const local = window.location.href
      if (code == null || code === '') {
        window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + GlobeConfig.appid + '&redirect_uri=' + encodeURIComponent(local) + '&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect'
      } else {
        this.login(code)
      }
    }
  }
}
</script>

<style scoped>
.addbutton {
  margin-top: 10px;
  height: 80px;
}
</style>
