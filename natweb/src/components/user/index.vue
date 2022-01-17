<template>
<div>
  <van-nav-bar
      title="核酸检测登记系统"

  />
  <van-notice-bar
      left-icon="volume-o"
      text="请在现场工作人员指导下进行信息填写。"
  />
  <van-image
      width="100%"
      height="150"
      :src="indexpng"
      fit="fill"
  />
  <van-button icon="edit" class="bot10 top10 button-high" type="info" size="large" to="/user/info">个人信息登记</van-button>
  <van-button icon="search" class="bot10 top10 button-high" type="primary" size="large" to="/user/search">检测结果查询</van-button>
</div>
</template>

<script>
import indexpng from "@/assets/核酸检测.png"
export default {
  data(){
    return{
      indexpng:indexpng
    }
  },
  name: "index"
}
</script>

<style scoped>
.button-high{
  height: 80px;
}
.bot10 {
  margin-bottom: 10px;
}
.top10 {
  margin-top: 10px;
}
</style>
