<template>
  <div>
    <van-nav-bar
        title="用户端登录"
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
      <div style="margin: 10px;">
        <van-notice-bar color="#1989fa" background="#ecf9ff" left-icon="info-o">
          新用户如未注册登录后将自动注册
        </van-notice-bar>
      </div>
    </van-form>
  </div>
</template>

<script>
import {Toast} from "vant";

export default {
  name: "user_login",
  data() {
    return {
      username: '',
      password: ''
    }
  },
  created() {
  },
  methods: {
    onSubmit() {
      this.axios.post(
          '/system-v2/register-login',
          this.qs.stringify({
            username: this.username,
            password: this.password
          })
      ).then((response) => {
        if (response.data.flag) {
          this.$router.push("/user")
        } else {
          Toast.fail(response.data.msg)
        }
      }).catch(function (error) {
        console.error(error);
      });
    }
  }
}
</script>

<style scoped>

</style>
