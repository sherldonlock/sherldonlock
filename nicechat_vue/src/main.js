// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import store from './store'
import api from './axios/api'
import global from './components/global'
import {message} from './utils/resetMessage';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/iconfont/iconfont.css';
import './assets/css/global.css';


Vue.use(ElementUI);
Vue.prototype.$message = message;
Vue.config.productionTip = false;
Vue.prototype.$api = api;
Vue.prototype.GLOBAL = global;

/*禁止页面右键与选中*/
Vue.directive('prevent-event', {undefined,

  bind: function (el, binding, vnode) {undefined

    // 禁用右键

    el.oncontextmenu = function (event) {undefined

      event.preventDefault(); // 阻止默认事件行为

      return false

    };

    // 禁用选择

    el.onselectstart = function (event) {undefined

      event.preventDefault(); // 阻止默认事件行为

      return false

    }
  }
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});
