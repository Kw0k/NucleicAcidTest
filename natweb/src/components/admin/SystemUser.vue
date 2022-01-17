<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-user-solid"></i> 系统用户
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
          <div class="handle-box">
            <el-button type="primary" icon="el-icon-plus" @click="addUser" class="mr10">添加用户</el-button>
            <el-select v-model="query.groupid" :disabled="groupdisable" clearable placeholder="所属单位"  class="handle-select mr10">
              <el-option v-for="(item,index) in groups" :key="index" :label="item.username" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="query.role" clearable placeholder="用户类型" @change="onRoleChange" class="handle-select mr10">
              <el-option key="1" label="单位账号" value="1"></el-option>
              <el-option key="2" label="采集人员" value="2"></el-option>
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
            <el-table-column prop="id" label="用户编号" width="55" align="center"></el-table-column>
            <el-table-column prop="username" label='用户名'></el-table-column>
            <el-table-column label="用户类型">
              <template slot-scope="scope">
                <span v-if='scope.row.role===0'>超级管理员</span>
                <span v-if='scope.row.role===1'>单位账号</span>
                <span v-if='scope.row.role===2'>采集人员</span>
              </template>
            </el-table-column>
            <el-table-column prop="lastlogintime" label='最后登录时间'></el-table-column>
            <el-table-column prop="group" label='所属单位'></el-table-column>
            <el-table-column label="操作" width="180" align="center">
              <template  slot-scope="scope">
                <el-button
                    size="small"
                    v-if="scope.row.role!==0"
                    type="primary"
                    icon="el-icon-edit"
                    @click="handleEdit(scope.row)"
                >编辑</el-button>
                <el-button
                    size="small"

                    v-if="scope.row.role!==0"
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                >删除</el-button>
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
      <!-- 编辑弹出框 -->
      <el-dialog :title="dialogTitle" :visible.sync="editVisible" width="30%">
        <el-form ref="form" :model="form" label-width="70px">
          <el-form-item label="用户编号">
            <el-input readonly v-model="form.id" placeholder="无需填写"></el-input>
          </el-form-item>
          <el-form-item label="用户名" >
            <el-input  v-model="form.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="用户类型">
            <el-select v-model="form.role" :disabled="selectDisable1" @change="roleChange" placeholder="用户类型"  class="handle-select mr10">
              <el-option key="2" label="单位账号" :value="1"></el-option>
              <el-option key="3" label="采集人员" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属单位">
            <el-select v-model="form.groupid" :disabled="selectDisable2" placeholder="所属单位"  class="handle-select mr10">
              <el-option v-for="(item,index) in groups" :key="index" :label="item.username" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" :placeholder="dialogPassword"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
      </el-dialog>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: 'ordertable',
    data() {
        return {
          selectDisable1:false,
          selectDisable2:false,
          editVisible: false,
          form:[],
          dialogTitle:"编辑",
          dialogPassword:"若无需修改密码，请留空",
            query: {
                groupid:null,
                role:null
            },
          tableData: [],
          pageSize: 20,
          currentPage:1,
          total:50,
          groups:null,
          groupdisable:true,
          user:null
        };
    },
    created() {
   this.getData()
      this.checkLogin()
      this.getGroupData()
    },
    methods: {
      roleChange(){
        if(this.user.role===0){
          if (this.form.role===1){
            this.selectDisable2=true;
            this.form.groupid=null
          }else{
            this.selectDisable2=false
          }
        }
      },
      checkLogin(){
        axios.post(
            '/checklogin'
        ).then((response) =>{
          if (response.data.flag){
            this.user=response.data.data;
          }else {
            //
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      saveEdit() {
        this.axios.post(
            '/admin/addOrUpdateSystemUser',
            this.qs.stringify({
              id: this.form.id,
              username: this.form.username,
              password:this.form.password,
              role:this.form.role,
              groupid:this.form.groupid
            })
        ).then((response) =>{
          this.getData();
          this.getGroupData()
          this.editVisible = false;

          if (response.data.flag){
            this.$message.success(response.data.msg);
          }else {
            this.$message.error(response.data.msg);
          }
        })
            .catch(function (error) {
              console.log(error);
            });
      },
      addUser(){
        this.form=[];
        if (this.user.role===1){
          this.selectDisable1=true
          this.selectDisable1=true
          this.form.groupid=this.user.id
          this.form.role=2
        }
        else{
          this.selectDisable1=false
          this.selectDisable2=false

        }
        this.dialogPassword="请输入密码"
        this.dialogTitle="添加用户"
        this.editVisible=true;

      },
      cancel(){
        this.editVisible=false;
        this.getData()
      },
      handleEdit (row){
          this.selectDisable1=true
          this.selectDisable2=true
        this.dialogPassword="若无需修改密码，请留空"
        this.dialogTitle="编辑用户"
        this.form=row;
        this.form.password="";
        this.editVisible=true;
      },
      handleDelete(row) {
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', {
          type: 'warning'
        })
            .then(() => {
              this.axios.post(
                  '/admin/delSystemUser',
                  this.qs.stringify({
                    id: row.id,
                  })
              ).then((response) =>{
                this.getData();
                this.getGroupData()

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

      },
      onRoleChange(){
        if (this.query.role==2){
          this.groupdisable=false
        }
        else{
          this.groupdisable=true
          this.query.groupid=null
        }
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
            '/admin/getAllSystemUser',this.qs.stringify({
                  pageNum:this.currentPage,
                  pageSize:this.pageSize,
                  groupid:this.query.groupid,
                  role:this.query.role
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
