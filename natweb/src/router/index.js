import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/user'
        },
        {
            path: '/admin',
            redirect: '/admin/login'
        },
        {
            path: '/operator',
            redirect: '/operator/login'
        },
        {
            path: '/admin',
            component: () => import('../components/admin/common/Home.vue'),
            meta: { title: '后台管理' },
            children: [
                {
                    path: '/admin/dashboard',
                    component: () => import('../components/admin/Dashboard'),
                    meta: { title: '后台管理首页' }
                },
                {
                    path: '/admin/result',
                    component: () => import('../components/admin/ResultTable'),
                    meta: { title: '检测结果' }
                },
                {
                    path: '/admin/tube',
                    component: () => import('../components/admin/TubeManage'),
                    meta: { title: '试管管理' }
                },
                {
                    path: '/admin/job',
                    component: () => import('../components/admin/JobManage'),
                    meta: { title: '任务管理' }
                },
                {
                    path: '/admin/userinfo',
                    component: () => import('../components/admin/UserInfo'),
                    meta: { title: '人员信息' }
                },
                {
                    path: '/admin/systemuser',
                    component: () => import('../components/admin/SystemUser'),
                    meta: { title: '系统用户' }
                }
            ]
        },
        {
            path: '/admin/login',
            component: () => import('../components/admin/Login.vue'),
            meta: { title: '后台登陆' }
        },

        {
            path: '/user',
            component: () => import('../components/user/index.vue'),
            meta: { title: '用户首页' }
        }, {
            path: '/user/system/login',
            component: () => import('../components/user/systemLogin.vue'),
            meta: { title: '用户系统登录' }
        },
        {
            path: '/user/info',
            component: () => import('../components/user/userInfo.vue'),
            meta: { title: '个人信息登记' }
        },
        {
            path: '/user/search',
            component: () => import('../components/user/search.vue'),
            meta: { title: '检测结果查询' }
        }, {
            path: '/user/info/view',
            component: () => import('../components/user/infoView.vue'),
            meta: { title: '个人信息详情' }
        }, {
            path: '/user/info/edit',
            component: () => import('../components/user/infoEdit.vue'),
            meta: { title: '个人信息编辑' }
        }, {
            path: '/operator/index',
            component: () => import('../components/operator/index.vue'),
            meta: { title: '分组扫码' }
        }, {
            path: '/operator/login',
            component: () => import('../components/operator/login.vue'),
            meta: { title: '采集登陆' }
        }, {
            path: '/operator/selectjob',
            component: () => import('../components/operator/selectJob.vue'),
            meta: { title: '采集任务选择' }
        },
    ]

});
