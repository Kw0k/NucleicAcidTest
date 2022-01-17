<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-document-checked"></i> 任务管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
              <el-button type="primary" icon="el-icon-plus" class="mr10" @click="addJob">添加任务</el-button>

              <el-select v-model="query.groupid" clearable placeholder="采集单位" class="handle-select mr10">
                <el-option v-for="(item,index) in groups" :key="index" :label="item.username" :value="item.id"></el-option>
              </el-select>
              <el-select v-model="query.status" clearable placeholder="任务状态" class="handle-select mr10">
                <el-option key="0" label="采集中" value="0"></el-option>
                <el-option key="1" label="已完成" value="1"></el-option>
              </el-select>
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
              <el-table-column prop="jobname" label="任务名称"  align="center"></el-table-column>
              <el-table-column label="任务状态" width="80" align="center">
                <template slot-scope="scope">
                  <el-tag size="medium" v-if='scope.row.status===0'>采集中</el-tag>
                  <el-tag size="medium" v-else-if='scope.row.status===1' type="success">已完成</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="100" align="center">

                <template slot-scope="scope">
                <el-button
                    v-if="scope.row.status===0"
                    size="small"
                    type="primary"
                    icon="el-icon-circle-check"
                    @click="handleFinish(scope.row)"
                >完成</el-button>
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
      <!-- 弹出框 -->
      <el-dialog title="添加任务" :visible.sync="addVisible" width="30%">
        <el-form ref="form" :rules="rules" :model="form" label-width="100px">
          <el-form-item prop="jobname" label="任务名称" >
            <el-input  v-model="form.jobname" placeholder="请输入任务名称"></el-input>
          </el-form-item>
          <el-form-item prop="groupid" label="所属单位">
            <el-select v-model="form.groupid" placeholder="所属单位"  class="handle-select mr10">
              <el-option v-for="(item,index) in groups" :key="index" :label="item.username" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="cancel" >取 消</el-button>
                <el-button type="primary" @click="sumbitJob" >确 定</el-button>
            </span>
      </el-dialog>
    </div>

</template>

<script>
export default {
    name: 'jobManage',
    data() {
        return {
          rules: {
            jobname: [{ required: true, message: '请输入任务名称', trigger: 'blur' }],
            groupid: [{ required: true, message: '请选择所属单位', trigger: 'blur' }],
          },
          form:{

          },
          addVisible:false,
            query: {
              groupid: null,
              status: null
            },
          tableData: [],
          pageSize: 20,
          currentPage:1,
          total:50,
          groups:null,
        };
    },
    created() {
   this.getData()
      this.getGroupData()
    },
    methods: {
      handleFinish(row){
        this.$confirm('确定要将任务状态设置为已完成吗？采集端将看不到该任务', '提示', {
          type: 'warning'
        })
            .then(() => {
              this.axios.post(
                  '/admin/finishJob',this.qs.stringify({
                        jobid: row.jobid}
                  )).then((response) =>{
                this.getData()
                if (response.data.flag){
                  this.$message.success(response.data.msg);
                }else {
                  this.$message.error(response.data.msg);
                }
              })
                  .catch(function (error) {
                    console.log(error);
                  });

            })
            .catch(() => {});


        console.log(row)
      },
      cancel(){
        this.addVisible=false;
        this.getData()
      },
      sumbitJob(){
        this.$refs.form.validate(valid => {
          if (valid) {
            this.axios.post(
                '/admin/addJob',this.qs.stringify({
                      groupid: this.form.groupid,
                      jobname: this.form.jobname
                    }
                )).then((response) =>{
                  this.getData()
              this.addVisible=false;
              if (response.data.flag){
                this.$message.success(response.data.msg);
              }else {
                this.$message.error(response.data.msg);
              }
            })
                .catch(function (error) {
                  console.log(error);
                });
          } else {
            this.$message.error('请填写完整');
            return false;
          }
        });
      },
      addJob(){
        this.form=[];
this.addVisible=true;

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
            '/admin/getJobData',this.qs.stringify({
                  groupid: this.query.groupid,
                  status: this.query.status,
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
