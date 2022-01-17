<template>
<div>
  <van-nav-bar
      left-text="返回"
      left-arrow
      title="核酸检测分组扫码"
      @click-left="onClickLeft"
  />
  <van-field label="采集任务：" :value="jobName" readonly />
  <van-field label="试管条码："  v-model="tubeCode" />
  <van-button type="primary" style="width: 40%" @click="scanTube">扫描试管</van-button>
  <van-button type="info" style="width: 60%" @click="scanPeople">扫描人员</van-button>
  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column
        type="index"
        width="50">
    </el-table-column>
    <el-table-column
        prop="tname"
        label="姓名"
        width="100">
    </el-table-column>
    <el-table-column
        prop="idcardnum"
        label="身份证号"
        >
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
            size="mini" type="danger"
            @click="handleDelete(scope.$index, scope.row)"
        >删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div style="margin: 16px;">
    <van-button round block type="info" @click="handleSubmit">提交本组信息</van-button>
  </div>
  <van-overlay :show="show" @click="show = false">
    <div class="scaner" ref="scaner">
      <div class="cover">
        <p class="line"></p>
        <span class="square top left"></span>
        <span class="square top right"></span>
        <span class="square bottom right"></span>
        <span class="square bottom left"></span>
      </div>
      <video
          id="video"
          class="source"
          ref="video"
          :width="videoWH.width"
          :height="videoWH.height"
      ></video>
    </div>
  </van-overlay>
</div>
</template>

<script>
import {BrowserMultiFormatReader} from "@zxing/library";
import {Toast} from "vant";
import { Dialog } from 'vant';

export default {

  data() {
    return {
      jobId:-1,
      jobName:'',
      tubeCode:"",//试管条码
      codeReader: new BrowserMultiFormatReader(),
      show:false,
      tableData: []
    }
  },
  props: {
    // 视频宽度
    videoWidth: {
      type: Number,
      default: document.documentElement.clientWidth || document.body.clientWidth
    },
    // 视频高度
    videoHeight: {
      type: Number,
      default: document.documentElement.clientHeight - 48 || document.body.clientHeight - 48
    }
  },computed: {
    videoWH () {
      if (this.containerWidth) {
        const width = this.containerWidth;
        const height = width * 0.75;
        return { width, height };
      }
      return { width: this.videoWidth, height: this.videoHeight };
    }
  },
  created() {
    this.checkLogin()
  },
  methods: {
    handleSubmit(){
      if (this.tubeCode===''){
        Toast.fail("试管信息为空")
        return;
      }
      if (this.tableData.length===0){
        Toast.fail("人员信息为空")
        return;
      }
      this.axios.post(
          '/operator/submit',
          this.qs.stringify({
            jobId: this.jobId,
            tubeId: this.tubeCode,
            list:JSON.stringify(this.tableData)
          })
      ).then((response) =>{
        if (response.data.flag){
         Toast.success(response.data.msg);
         this.tableData=[]
          this.tubeCode=''
        }else {
         Toast.fail(response.data.msg)
          if (response.data.code===408){
            this.$router.push("/operator/login")
          }
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    checkLogin(){
      this.axios.post(
          '/operator/checklogin'
      ).then((response) =>{
        if (response.data.flag){
          //已登陆 获取采集任务ID和任务名称
          this.jobId=sessionStorage.getItem("jobId")
          this.jobName=sessionStorage.getItem("jobName")
          if (this.jobId===null||this.jobId===-1)
            this.$router.push("/operator/selectjob")
        }else {
          Toast.fail(response.data.msg)
          this.$router.push("/operator/login")
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    async openScan(type) {
      this.codeReader.getVideoInputDevices()
          .then((videoInputDevices) => {
            console.log(videoInputDevices)
            let selectedDeviceId = videoInputDevices[0].deviceId;
            const videoInputDeviceslablestr = JSON.stringify(videoInputDevices[0].label);
            if (videoInputDevices.length > 1) {
              // 判断是否后置摄像头
              if (videoInputDeviceslablestr.indexOf('back') > -1) {
                selectedDeviceId = videoInputDevices[0].deviceId;
              } else {
                selectedDeviceId = videoInputDevices[1].deviceId;
              }
            }
            this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
              if (result) {
                console.log(result);
                if(type){
                  //扫描人员
                  var flag=false;//是否重复扫描
                  var peopleInfo=JSON.parse(result.getText())
                  this.tableData.forEach(function (item) {
                    if (item.tname===peopleInfo.tname&&item.idcardnum===peopleInfo.idcardnum) {
                      Toast('重复扫描');
                      flag=true;
                    }
                  })
                  if (!flag){
                    this.tableData.push(peopleInfo)
                    Toast('扫描人员成功');
                  }
                }else {
                  //扫描试管
                  this.tubeCode=result.getText()
                  Toast('扫描试管成功：'+result.getText());
                }
                this.show=false;// 关闭遮罩层
                this.codeReader.stopAsyncDecode();
                this.codeReader.stopContinuousDecode();
              }
              if (err && !(err)) {
                Toast('扫描失败:'+err);
                console.error(err);
              }
            });
            console.log(`Started continous decode from camera with id ${selectedDeviceId}`);
          })
          .catch((err) => {
            console.error(err);
          });
    },
    onClickLeft() {
      this.$router.push("/operator/selectjob");
    },
    handleDelete(index, row) {//删除人员
      Dialog.confirm({
        title: ' 删除确认',
        message: '确定要删除'+row.tname+'吗？',
      })
          .then(() => {
            this.tableData.splice(index,1)
          })
          .catch(() => {
            // on cancel
          });
    },
    scanTube(){
      if (this.tubeCode!=""){
        Dialog.confirm({
          title: '重新扫描',
          message: '试管已扫描，是否要重新扫描试管？',
        })
            .then(() => {
              this.show=true
              this.openScan(0) //0表示扫描试管 1表示扫描人员
            })
            .catch(() => {
              // on cancel
            });
      }else{
        this.show=true
        this.openScan(0) //0表示扫描试管 1表示扫描人员
      }
    },
    scanPeople(){
      if (this.tableData.length===10){
        Toast("本组人员已满")
        return
      }
      this.show=true
      this.openScan(1) //0表示扫描试管 1表示扫描人员
    }
  }
}
</script>

<style scoped>
.scaner {
  background: #000000;
  position: fixed;
  top: 48px;
  left: 0;
  width: 100%;
  height: 100%;
  height: -webkit-calc(100% - 48px);
  height: -moz-calc(100% - 48px);
  height: -ms-calc(100% - 48px);
  height: -o-calc(100% - 48px);
  height: calc(100% - 48px);
}
.scaner .cover {
  height: 220px;
  width: 220px;
  position: absolute;
  top:50%;
  left:50%;
  -webkit-transform: translate(-50%,-50%);
  -moz-transform: translate(-50%,-50%);
  -ms-transform: translate(-50%,-50%);
  -o-transform: translate(-50%,-50%);
  transform: translate(-50%,-50%);
  border: .5px solid #999999;
  z-index: 1111;
}
.scaner .cover .line {
  width: 200px;
  height: 1px;
  margin-left: 10px;
  background: #5F68E8;
  background: linear-gradient(to right, transparent, #5F68E8, #0165FF, #5F68E8, transparent);
  position: absolute;
  -webkit-animation: scan 1.75s infinite linear;
  -moz-animation: scan 1.75s infinite linear;
  -ms-animation: scan 1.75s infinite linear;
  -o-animation: scan 1.75s infinite linear;
  animation: scan 1.75s infinite linear;
  -webkit-animation-fill-mode: both;
  -moz-animation-fill-mode: both;
  -ms-animation-fill-mode: both;
  -o-animation-fill-mode: both;
  animation-fill-mode: both;
  border-radius: 1px;
}
.scaner .cover .square {
  display: inline-block;
  height: 20px;
  width: 20px;
  position: absolute;
}
.scaner .cover .square.top {
  top: 0;
  border-top: 1px solid #5F68E8;
}
.scaner .cover .square.left {
  left: 0;
  border-left: 1px solid #5F68E8;
}
.scaner .cover .square.bottom {
  bottom: 0;
  border-bottom: 1px solid #5F68E8;
}
.scaner .cover .square.right {
  right: 0;
  border-right: 1px solid #5F68E8;
}
.scaner .cover .tips {
  position: absolute;
  bottom: -48px;
  width: 100%;
  font-size: 14px;
  color: #FFFFFF;
  opacity: 0.8;
}
@-webkit-keyframes scan {
  0% {top: 0}
  25% {top: 50px}
  50% {top: 100px}
  75% {top: 150px}
  100% {top: 200px}
}
@-moz-keyframes scan {
  0% {top: 0}
  25% {top: 50px}
  50% {top: 100px}
  75% {top: 150px}
  100% {top: 200px}
}
@-o-keyframes scan {
  0% {top: 0}
  25% {top: 50px}
  50% {top: 100px}
  75% {top: 150px}
  100% {top: 200px}
}
@keyframes scan {
  0% {top: 0}
  25% {top: 50px}
  50% {top: 100px}
  75% {top: 150px}
  100% {top: 200px}
}
</style>
