<template>
<div>
  <van-nav-bar
      left-text="返回"
      left-arrow
      title="个人信息登记"
      @click-left="onClickLeft"
  />
  <van-button icon="plus" class="addbutton"  type="info" size="large" to="info/edit">添加个人信息</van-button>
  <van-divider>已登记信息</van-divider>
  <van-list
      finished-text="没有更多了"
  >
    <van-cell v-for="item in list" :key="item" :title="'姓名：'+item.tname+' 身份证号：'+item.idcardnum" :to="'info/view?idcardnum='+item.idcardnum" >
      <van-icon  size="35" name="arrow" />
    </van-cell>
  </van-list>
</div>
</template>

<script>
import axios from "axios";
import {getUrlParam} from "@/components/js/GetUrlParam";
import {Toast} from "vant";
export default {
  name: "userInfo",
  data() {
    return {
      list: [],
    };
  },
  created() {
      this.checkLogin();
  },
  methods: {
    checkLogin(){
      axios.post(
          '/wechat/checklogin',
      ).then((response) =>{
        if (response.data.flag){
          //已登陆
          this.getUserInfo()
        }else {
          this.getCode()
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    },
    onClickLeft() {
      this.$router.push("/user")
    },
    getUserInfo(){ //获取当前微信登记过的个人信息
      axios.post(
          '/wechat/userInfoAll'
      ).then((response) =>{
        if (response.data.flag){
         this.list=response.data.data;
        }else {
          Toast.fail(response.data.msg);
          if (response.data.code===408){
            this.$router.push("/user/info")
          }
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    },
    login(code){
      axios.post(
          '/wechat/login',
          this.qs.stringify({
            code: code
          })
      ).then((response) =>{
        if (response.data.flag){
          var _url = window.location.protocol + '//' + window.location.host + '/#/user/info';
          window.history.pushState({},0, _url)
          this.getUserInfo()
        }else {
          console.log(response.data.msg)
        }
      })
      .catch(function (error) {
            console.log(error);
          });
    },
    getCode() {
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
.addbutton{
  margin-top: 10px;
  height: 80px;
}
</style>
