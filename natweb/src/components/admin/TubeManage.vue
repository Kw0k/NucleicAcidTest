<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-s-data"></i> 试管管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
      <div class="container" style="margin-bottom: 10px">
        <el-select v-model="result.status" clearable placeholder="试管状态" class="handle-select mr10">
          <el-option key="1" label="阴性" value="1"></el-option>
          <el-option key="2" label="阳性" value="2"></el-option>
        </el-select>
        <el-input v-model="result.tubeid" placeholder="试管条码" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-position" @click="setTube">发布结果</el-button>
        <el-divider content-position="left">批量发布</el-divider>
        <el-select v-model="jobid" clearable placeholder="选择任务" class="handle-select mr10">
          <el-option v-for="(item,index) in jobs2" :key="index" :label="item.name" :value="item.id"></el-option>
        </el-select><el-button type="primary" icon="el-icon-position" @click="setJobTube">批量发布</el-button>
         <span>(将该任务下的所有未发布结果的试管设置为阴性,仅可选择状态为'已完成'的采集任务)</span>
      </div>
        <div class="container">
          <div class="handle-box">
            <el-select v-model="query.groupid" clearable placeholder="采集单位" @change="onGroupChange" class="handle-select mr10">
              <el-option v-for="(item,index) in groups" :key="index" :label="item.username" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="query.jobid" clearable placeholder="采集任务" class="handle-select mr10">
              <el-option v-for="(item,index) in jobs" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <template>
              <el-date-picker
                  class="mr10"
                  v-model="times"
                  type="datetimerange"
                  :picker-options="pickerOptions"
                  range-separator="至"
                  start-placeholder="采集时间"
                  end-placeholder="采集时间"
                  align="right">
              </el-date-picker>
            </template>
            <el-select v-model="query.status" clearable placeholder="试管状态" class="handle-select mr10">
              <el-option key="0" label="已采集" value="0"></el-option>
              <el-option key="1" label="阴性" value="1"></el-option>
              <el-option key="2" label="阳性" value="2"></el-option>
            </el-select>
            <el-input v-model="query.tubeid" placeholder="试管条码" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="getData">搜索</el-button>
          </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
              <el-table-column prop="group" label="采集单位"  align="center"></el-table-column>
              <el-table-column prop="operator" label="采集人" width="100" align="center"></el-table-column>
              <el-table-column prop="jobname" label="采集任务"  align="center"></el-table-column>
              <el-table-column prop="tubeId" label="试管条码"  align="center"></el-table-column>
              <el-table-column prop="starttime" label="采集时间"  align="center"></el-table-column>
              <el-table-column prop="endtime" label="检测时间"  align="center"></el-table-column>
              <el-table-column label="试管状态" width="80" align="center">
                <template slot-scope="scope">
                  <el-tag size="medium" v-if='scope.row.status===0'>已采集</el-tag>
                  <el-tag size="medium" v-else-if='scope.row.status===1' type="success">阴性</el-tag>
                  <el-tag size="medium" v-else-if='scope.row.status===2' type="danger">阳性</el-tag>
                </template>
              </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    :current-page="currentPage"
                    :page-sizes="[20, 30, 50, 100]"
                    :page-size="20"
                    background
                    layout="total, sizes, prev, pager, next"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                ></el-pagination>
            </div>
        </div>

      </div>
</template>

<script>
export default {
    name: 'tubeManage',
    data() {
        return {
          pickerOptions: {
            shortcuts: [{
              text: '最近一周',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }]
          },
          result:{
            status: null,
            tubeid: null
          },
            query: {
              groupid: null,
              jobid: null,
              operatorid: null,
              status: null,
              idcardnum: null,
              tubeid: null,
            },
          tableData: [],
          pageSize: 20,
          currentPage:1,
          total:50,
          times:[],
          groups:null,
          jobs:null,
          jobs2:null,
          jobid:null
        };
    },
    created() {
      this.getData()
      this.getGroupData()
      this.getJobData()
      this.getJobData2()
   //
    },
    methods: {
      setJobTube(){
        if (this.jobid===null||this.jobid===''){
          this.$message.error('请选择任务');
          return;
        }
        this.axios.post(
            '/admin/setJobTubeStatus',this.qs.stringify({
                  jobid: this.jobid
                }
            )).then((response) =>{
          if (response.data.flag){
            this.$message.success(response.data.msg);
            this.getData()
          }else {
            this.$message.error(response.data.msg);
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      setTube(){
        if (this.result.status===null||this.result.status===''){
          this.$message.error('请选择试管状态');
          return;
        }
        if (this.result.tubeid===null||this.result.tubeid===''){
          this.$message.error('请输入试管条码');
          return;
        }
        this.axios.post(
            '/admin/setTubeStatus',this.qs.stringify({
                  tubeid: this.result.tubeid,
                  status:this.result.status
                }
            )).then((response) =>{
          if (response.data.flag){
            this.$message.success(response.data.msg);
            this.getData()

          }else {
          this.$message.error(response.data.msg);
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      getJobData(){
        this.axios.post(
            '/admin/getJob',this.qs.stringify({
                  groupid: this.query.groupid
                }
            )).then((response) =>{
          if (response.data.flag){
            this.jobs=response.data.data
          }else {
//
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      getJobData2(){
        this.axios.post(
            '/admin/getJob',this.qs.stringify({
                  groupid: this.query.groupid,
                  status:1
                }
            )).then((response) =>{
          if (response.data.flag){
            this.jobs2=response.data.data
          }else {
//
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      onGroupChange(){
        this.query.jobid=null
        if (this.query.groupid===''){
          this.query.groupid=null
        }
        console.log(this.query.groupid)
        this.getJobData()
      },
      getGroupData(){
        this.axios.post(
            '/admin/getGroup'
        ).then((response) =>{
          if (response.data.flag){
            this.groups=response.data.data
          }else {
//
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      getData(){
        this.axios.post(
            '/admin/getTube',this.qs.stringify({
                  groupid: this.query.groupid,
                  jobid: this.query.jobid,
                  operatorid: this.query.operatorid,
                  status: this.query.status,
                  tubeid:this.query.tubeid,
                  time1:this.times[0],
                  time2:this.times[1],
                  pageNum:this.currentPage,
                  pageSize:this.pageSize
                }
            )).then((response) =>{
          if (response.data.flag){
            this.tableData=response.data.data.list;
            this.total=response.data.data.total;
          }else {
            this.tableData=response.data.data;
          }
          console.log( this.tableData)
        })
            .catch(function (error) {
              console.log(error);
            });
      },

        // 分页导航
        handleSizeChange(val) {
            this.pageSize =val;
          this.getData()

        },
        handleCurrentChange(val) {
            this.currentPage = val;
          this.getData()

        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 180px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
