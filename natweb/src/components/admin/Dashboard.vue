<template>
  <div class='main'>
    <el-row class="row" :gutter="15">
      <el-col :span="6">
        <el-card shadow="hover" style="height:100px;">
          <div class="user-info">
            <div class="user-info-cont">
              <div class="user-info-name">欢迎你，{{ name }}。</div>
              <div>{{ rolestr }} 上次登录时间：
                <span>{{ lastlogintime }}</span></div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-s-order grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ tubeCount }}</div>
              <div>已采集试管</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-search grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ tubeCount12 }}</div>
              <div>已检测试管</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-2">
            <i class="el-icon-success grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ tubeCount1 }}</div>
              <div>阴性试管</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row class="row" :gutter="15" v-if="this.role===0">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-s-custom grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ userInfoCount }}</div>
              <div>已登记人数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-s-order grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ tubeUserCount }}</div>
              <div> 已采集人次</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1">
            <i class="el-icon-search grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ tubeUserCount12 }}</div>
              <div>已检测人次</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-2">
            <i class="el-icon-success grid-con-icon"></i>
            <div class="grid-cont-right">
              <div class="grid-num">{{ tubeUserCount1 }}</div>
              <div>阴性人次</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row class="row" :gutter="15" v-if="this.role===0">
      <el-col :span="24">
        <el-table
            :data="allNodeData"
            border
            class="table"
            ref="multipleTable"
            header-cell-class-name="table-header">
          <el-table-column prop="last_time" label="最后登陆时间" align="center"></el-table-column>
          <el-table-column label="在线状态" width="100" align="center">
            <template slot-scope="scope">
              <el-tag size="medium" v-if='scope.row.session_id.length === 0' type="danger">离线</el-tag>
              <el-tag size="medium" v-else-if='scope.row.session_id.length > 0' type="success">在线</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="opt_id" label="节点操作员ID" align="center"></el-table-column>
          <el-table-column label="试管数量" align="center">
            <template slot-scope="scope">
              <el-tag size="medium" v-if='scope.row.material_tube_num < 100' type="danger">
                {{ scope.row.material_tube_num }}
              </el-tag>
              <el-tag size="medium" v-else type="success">
                {{ scope.row.material_tube_num }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="拭子数量" align="center">
            <template slot-scope="scope">
              <el-tag size="medium" v-if='scope.row.material_swab_num < 5000' type="danger">
                {{ scope.row.material_swab_num }}
              </el-tag>
              <el-tag size="medium" v-else type="success">
                {{ scope.row.material_swab_num }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="医用酒精数量" align="center">
            <template slot-scope="scope">
              <el-tag size="medium" v-if='scope.row.material_alcohol_num < 100' type="danger">
                {{ scope.row.material_alcohol_num }}
              </el-tag>
              <el-tag size="medium" v-else type="success">
                {{ scope.row.material_alcohol_num }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="节点工作人员数量" align="center">
            <template slot-scope="scope">
              <el-tag size="medium" v-if='scope.row.staff_num < 5' type="danger">
                {{ scope.row.staff_num }}
              </el-tag>
              <el-tag size="medium" v-else type="success">
                {{ scope.row.staff_num }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="下发命令" align="center">
            <template slot-scope="scope">
              <el-select v-model="selectInfo[scope.$index]" placeholder="请选择命令">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
              <el-button type="primary" size="mini" plain
                         @click="handleCommand(scope.row.opt_id,selectInfo[scope.$index])">下发命令
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'dashboard',
  data() {
    return {
      userInfoCount: 0,//已登记人数
      tubeCount12: 0,//已检测试管
      tubeUserCount12: 0,//已检测人次
      tubeCount1: 0,//阴性试管
      tubeUserCount1: 0,//阴性人次
      tubeCount: 0,//已采集试管
      tubeUserCount: 0,//已采集人次
      name: "",
      lastlogintime: "",
      role: 0,
      rolestr: '',
      allNodeData: [],
      options: [{
        value: '1',
        label: '操作1'
      }, {
        value: '2',
        label: '操作2'
      }, {
        value: '3',
        label: '操作3'
      }, {
        value: '4',
        label: '操作4'
      }, {
        value: '5',
        label: '操作5'
      }],
      selectInfo: [],
      nodeRedreshTimer: ''
    }
  },
  created() {
    this.checkLogin();
    this.getTubeCount();
    this.getTubeUserCount();
    this.getUserInfoCount();
  },
  beforeDestroy() {
    clearInterval(this.nodeRedreshTimer);
  },
  mounted() {
    this.nodeRedreshTimer = setInterval(this.getAllNodeData, 1000)
  },
  methods: {
    handleCommand(optId, selectInfo) {
      console.log(optId, selectInfo)
      //
      this.axios.post(
          "/node/opt",
          this.qs.stringify({
            optId: optId,
            instruction: selectInfo,
          })
      ).then((response) => {
        if (response.data.flag) {

        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    getAllNodeData() {
      this.axios.post(
          "/node/get_all"
      ).then((response) => {
        if (response.data.flag) {
          this.allNodeData = response.data.data
        }
      }).catch(function (error) {
        console.log(error);
      });
    }
    ,
    getUserInfoCount() {
      this.axios.get(
          '/admin/getUserInfoCount'
      ).then((response) => {
        if (response.data.flag) {
          this.userInfoCount = response.data.data;
        }
      }).catch(function (error) {
        console.log(error);
      });
    }
    ,
    getTubeUserCount() {
      this.axios.get(
          '/admin/getTubeUserCount'
      ).then((response) => {
        if (response.data.flag) {
          this.tubeUserCount = response.data.data[0];
          this.tubeUserCount12 = response.data.data[1];
          this.tubeUserCount1 = response.data.data[2];
        }
      }).catch(function (error) {
        console.log(error);
      });
    }
    ,
    getTubeCount() {
      this.axios.get(
          '/admin/getTubeCount'
      ).then((response) => {
        if (response.data.flag) {
          this.tubeCount = response.data.data[0];
          this.tubeCount12 = response.data.data[1];
          this.tubeCount1 = response.data.data[2];
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    }
    ,
    checkLogin() {
      this.axios.post(
          '/checklogin'
      ).then((response) => {
        if (response.data.flag) {
          this.name = response.data.data.username;
          this.lastlogintime = response.data.data.lastlogintime;
          this.role = response.data.data.role;
          switch (this.role) {
            case 0:
              this.rolestr = '超级管理员';
              break;
            case 1:
              this.rolestr = '单位账号';
              break;
          }
        } else {
          this.$router.push('/admin/login');
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    }
  }
}
;
</script>


<style scoped>
.row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-info-cont {
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 24px;
  color: #222;
}

.user-info-list span {
  margin-left: 70px;
}

.schart {
  width: 100%;
  height: 300px;
}
</style>
