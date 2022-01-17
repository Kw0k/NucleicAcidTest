<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
          user:null,
            collapse: false,
            items: [
            ]
        };
    },
    computed: {

        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
  methods:{
      pushItem(){
        this.items.push(
            {
              icon: 'el-icon-s-home',
              index: 'dashboard',
              title: '系统首页'
            }
        )
        if (this.user.role===0)
        this.items.push({
          icon: 'el-icon-s-order',
          index: 'result',
          title: '检测结果'
        })
        this.items.push({
              icon: 'el-icon-s-data',
              index: 'tube',
              title: '试管管理'
            },
            {
              icon: 'el-icon-document-checked',
              index: 'job',
              title: '任务管理'
            })
        if (this.user.role===0)
          this.items.push({
          icon: 'el-icon-s-custom',
          index: 'userinfo',
          title: '人员信息'
        })
        this.items.push({
          icon: 'el-icon-user-solid',
          index: 'systemuser',
          title: '系统用户'
        })
      },
    checkLogin(){
      this.axios.post(
          '/checklogin'
      ).then((response) =>{
        if (response.data.flag){
          this.user=response.data.data;
          this.pushItem();
        }else {
          this.$message.error(response.data.msg);
          this.$router.push('/admin/login');
        }
      })
          .catch(function (error) {
            console.log(error);
          });
    },
  },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
      this.checkLogin()
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 150px;
}
.sidebar > ul {
    height: 100%;
}
</style>
