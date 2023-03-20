import Vue from 'vue'
import App from './App.vue'
import router from './router';
import '../public/config'
import Vant from 'vant'
import 'vant/lib/index.css';
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui'
import axios from 'axios'
import VueAxios from 'vue-axios'
import qs from 'qs';
import VueCookies from 'vue-cookies'

Vue.use(VueCookies)
Vue.use(VueAxios, axios)
Vue.prototype.qs = qs;
Vue.use(Vant)
Vue.use(ElementUI)
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.withCredentials = true //解决跨域请求时sessionid不一致
axios.defaults.baseURL=GlobeConfig.baseURL //后端地址
router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title} | 核酸检测登记系统`;
  next();
});
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
