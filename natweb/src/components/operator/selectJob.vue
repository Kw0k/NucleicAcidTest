<template>
<div>
  <van-nav-bar
      title="采集任务选择"
  />
  <van-dropdown-menu>
    <van-dropdown-item  v-model="jobId" :options="jobs" />
  </van-dropdown-menu>
  <div style="margin: 16px;">
    <van-button round block type="primary" @click="start">开始采集</van-button>
  </div>
  <div style="margin:50px 16px;">
    <van-button round block type="warning" @click="logout">退出登录</van-button>
  </div>
</div>
</template>

<script>
import {Toast} from "vant";
export default {
  data() {
    return {
      jobId: -1,
      jobs: [
        { text: '选择采集任务', value: -1 },
      ]
    };
  },
  methods:{
    start(){
      if (this.jobId===-1){
        Toast.fail("请先选择采集任务")
      }else{
        for (let i = 0; i < this.jobs.length; i++) {
          if(this.jobs[i].value===this.jobId){
            sessionStorage.setItem("jobId",this.jobId)
            sessionStorage.setItem("jobName",this.jobs[i].text)
            this.$router.push("/operator/index")
          }
        }
      }
    },
    logout(){
      this.axios.post(
          '/logout'
      ).then((response) =>{
        if (response.data.flag){
          Toast.success(response.data.msg);
          this.$router.push("/operator/login")
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    getJobs(){
      this.axios.post(
          '/operator/job'
      ).then((response) =>{
        if (response.data.flag){
          var list=response.data.data;
          for (let i = 0; i < list.length; i++) {
            this.jobs.push({ text: list[i].name, value: list[i].id})
          }
        }else {
          Toast.fail(response.data.msg)
          this.$router.push("/operator/login")
        }
      }).catch(function (error) {
        console.log(error);
      });
    }
  },
  created() {
  this.getJobs()
  }
};
</script>

<style scoped>

</style>
