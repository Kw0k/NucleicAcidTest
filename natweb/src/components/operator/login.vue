<template>
<div>
  <van-nav-bar
      title="采集系统登录"
  />
  <van-form @submit="onSubmit">
  <van-field
      v-model="username"
      name="用户名"
      label="用户名"
      placeholder="用户名"
      :rules="[{ required: true, message: '请填写用户名' }]"
  />
  <van-field
      v-model="password"
      type="password"
      name="密码"
      label="密码"
      placeholder="密码"
      :rules="[{ required: true, message: '请填写密码' }]"
  />
  <div style="margin: 16px;">
    <van-button round block type="info" native-type="submit">登录</van-button>
  </div>
  </van-form>
</div>
</template>

<script>
import {Toast} from "vant";
export default {
  name: "login",
  data(){
    return {
        username: '',
        password: ''
    }
  },
  created() {
    this.checkLogin();
  },
  methods:{
    checkLogin(){
      this.axios.post(
          '/operator/checklogin'
      ).then((response) =>{
        if (response.data.flag){
          Toast.success(response.data.msg);
          this.$router.push("/operator/selectjob")
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    onSubmit(){
      this.axios.post(
          '/operator/login',
          this.qs.stringify({
            username: this.username,
            password: this.password})
      ).then((response) =>{
        if (response.data.flag){
          Toast.success(response.data.msg);
          this.$router.push("/operator/selectjob")
        }else {
          Toast.fail(response.data.msg)
        }
      }).catch(function (error) {
            console.log(error);
          });
    }
  }
}
</script>

<style scoped>

</style>
