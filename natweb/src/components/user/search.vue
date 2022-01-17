<template>
<div>
  <van-nav-bar
      left-text="返回"
      left-arrow

      title="检测结果查询"
      @click-left="onClickLeft"
  />
  <van-image
      width="100%"
      height="150"
      :src="indexpng"
      fit="fill"
  />
  <van-notice-bar
      wrapable
      left-icon="volume-o"
      text="1.请输入并核实检测人员姓名和证件号码查询检测结果。2.核酸检测结果分批发布，请您耐心等待并持续关注官方发布消息。"
  />
  <van-form @submit="onSubmit">
    <van-field
        v-model="tname"
        name="姓名"
        label="姓名"
        placeholder="姓名"
        :rules="[{ required: true, message: '请填写姓名' }]"
    />
    <van-field
        v-model="idcardnum"
        name="身份证号码"
        label="身份证号码"
        placeholder="身份证号码"
        :rules="[{ required: true, message: '请填写身份证号码' }]"
    />
    <div style="margin: 16px;">
      <van-button round block type="info" native-type="submit">查询</van-button>
    </div>
  </van-form>

  <van-card v-for="(item,i) in list" :key="i">
    <template #tags >
      <span style="font-size: 15px;line-height: 20px">姓名:{{item.tname}}</span><br>
      <span style="font-size: 15px;line-height: 20px">身份证号:{{item.idcardnum}}</span><br>
      <span style="font-size: 15px;line-height: 20px">采集时间:{{item.starttime}}</span><br>
      <span style="font-size: 15px;line-height: 20px">检测时间:{{item.endtime}}</span><br>
      <span style="font-size: 15px;line-height: 20px">检测单位:{{item.group}}&nbsp;</span><span style="font-size: 15px;line-height: 20px">检测结果:
    <van-tag v-if="item.status===0" type="primary">检测中</van-tag>
<van-tag v-if="item.status===1" type="success">阴性</van-tag>
<van-tag v-if="item.status===2" type="danger">阳性</van-tag>
    </span>
    </template>
  </van-card>

</div>
</template>

<script>
import indexpng from "@/assets/核酸检测.png"
import {Toast} from "vant";
export default {
  data(){
    return{
      tname:null,
      idcardnum:null,
      indexpng:indexpng,
      list:[]
    }
  },
  name: "search",
  methods: {
    onSubmit(){
      Toast.loading({
        message: '查询中...',
        forbidClick: true,
      });
      this.axios.post(
          '/search',
          this.qs.stringify({
            tname: this.tname,
            idcardnum: this.idcardnum
          })
      ).then((response) =>{
        if (response.data.flag){
          this.list=response.data.data;
        }else {
          //
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    },
    onClickLeft() {
      this.$router.push("/user")
    },
  },
}
</script>

<style scoped>

</style>
