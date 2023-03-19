<template>
  <div style="width: 100%" class="center">
    <van-nav-bar
        left-text="返回"
        left-arrow

        title="个人信息详情"
        @click-left="onClickLeft"
    />
    <van-divider>个人信息二维码</van-divider>
    <div class="qrcode" ref="qrCodeUrl"></div>
    <van-divider>个人信息详情</van-divider>
    <van-form disabled>
      <van-field
          v-model="userInfo.tname"
          name="姓名"
          label="姓名"
      />
      <van-field
          label="性别"
          v-model="computeSex"
      >
      </van-field>
      <van-field
          v-model="userInfo.idcardnum"
          name="身份证号"
          label="身份证号"
      />
      <van-field
          v-model="userInfo.phonenum"
          name="手机号"
          label="手机号"
      />
      <van-field
          clickable
          name="area"
          :value="userInfo.area"
          label="所属辖区"
          placeholder="点击选择省市区"
      />
      <van-field
          v-model="userInfo.address"
          name="详细地址"
          label="详细地址"
      />
    </van-form>
    <div style="margin: 16px;">
      <van-button round block type="info" :to="'edit?idcardnum='+this.userInfo.idcardnum">编辑个人信息</van-button>
    </div>
    <div style="margin: 16px;">
      <van-button round block type="danger" @click="handleDelete">删除</van-button>
    </div>
  </div>
</template>

<script>
import QRCode from 'qrcodejs2'
import {getUrlParam} from "@/components/js/GetUrlParam";
import {Dialog, Toast} from "vant";

export default {
  name: "infoView",
  data() {
    return {
      userInfo: {
        address: '',
        sex: 1,
        phonenum: '',
        idcardnum: '',
        tname: '',
        area: '',
      },
      showArea: false,
      areaList: {},
    }
  },
  computed: {
    computeSex() {
      return this.userInfo.sex === 1 ? '男' : '女'
    }
  },
  created() {
    this.userInfo.idcardnum = getUrlParam("idcardnum")
    if (this.userInfo.idcardnum === '' || this.userInfo.idcardnum === null) {
      this.$router.push("/user/info")
    } else {
      if (UserLoginConfig.useWechet) {
        this.wechatCheckLogin();
      } else {
        this.systemV2CheckLogin();
      }
    }
  },
  methods: {
    systemV2CheckLogin() {
      this.axios.post(
          '/system-v2/checklogin',
      ).then((response) => {
        if (response.data.flag) {
          //已登陆
          this.getSystemV2UserInfo()
        } else {
          Toast.fail(response.data.msg);
          this.$router.push("/user/info")
        }
      }).catch(function (error) {
        console.error(error);
      });
    },
    wechatCheckLogin() {
      this.axios.post(
          '/wechat/checklogin',
      ).then((response) => {
        if (response.data.flag) {
          //已登陆
          this.getWechatUserInfo()
        } else {
          Toast.fail(response.data.msg);
          this.$router.push("/user/info")
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    handleDelete() {
      if (UserLoginConfig.useWechet) {
        this.weChatDelete()
      } else {
        this.userV2SystemDelete()
      }
    },
    userV2SystemDelete() {
      Dialog.confirm({
        title: ' 删除确认',
        message: '确定要删除吗？',
      }).then(() => {
        Toast.loading({
          message: '删除中...',
          forbidClick: true,
        });
        this.axios.post(
            '/system-v2/deleteBindingRelationshipByV2SystemAndUserInfo',
            this.qs.stringify({
              idcardnum: this.userInfo.idcardnum,
            })
        ).then((response) => {
          if (response.data.flag) {
            Toast.success(response.data.msg);
            this.$router.push("/user/info")
          } else {
            Toast.fail(response.data.msg);
            if (response.data.code === 408) {
              this.$router.push("/user/info")
            }
          }
        }).catch(function (error) {
          console.log(error);
        });
      }).catch(() => {
        // on cancel
      });
    },
    weChatDelete() {
      Dialog.confirm({
        title: ' 删除确认',
        message: '确定要删除吗？',
      }).then(() => {
        Toast.loading({
          message: '删除中...',
          forbidClick: true,
        });
        this.axios.post(
            '/wechat/delWechatUser',
            this.qs.stringify({
              idcardnum: this.userInfo.idcardnum,
            })
        ).then((response) => {
          if (response.data.flag) {
            Toast.success(response.data.msg);
            this.$router.push("/user/info")
          } else {
            Toast.fail(response.data.msg);
            if (response.data.code === 408) {
              this.$router.push("/user/info")
            }
          }
        }).catch(function (error) {
          console.log(error);
        });
      }).catch(() => {
        // on cancel
      });
    },
    creatQrCode() {
      new QRCode(this.$refs.qrCodeUrl, {
        text: '{"idcardnum":"' + this.userInfo.idcardnum + '","tname":"' + this.userInfo.tname + '"}', // 需要转换为二维码的内容
        width: 180,
        height: 180,
        colorDark: '#000000',
        colorLight: '#ffffff',
        correctLevel: QRCode.CorrectLevel.H
      })
    },
    getWechatUserInfo() {
      this.axios.post(
          '/wechat/userInfoViewAndEdit',
          this.qs.stringify({
            idcardnum: this.userInfo.idcardnum
          })
      ).then((response) => {
        if (response.data.flag) {
          this.userInfo = response.data.data[0];
          this.creatQrCode()
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
      this.axios.post('/system-v2/userInfoViewAndEdit',
          this.qs.stringify({
            idcardnum: this.userInfo.idcardnum
          }))
          .then((response) => {
            this.userInfo = response.data.data[0];
            this.creatQrCode()
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    onClickLeft() {
      this.$router.push("/user/info")
    },
  },
}
</script>

<style scoped>
.qrcode {
  width: 180px;
  height: 180px;
  margin: 0 auto;
}
</style>
