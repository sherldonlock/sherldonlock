import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/index/index.vue'
import Chat from '@/pages/chat/chat.vue'
import User from '@/pages/user/user.vue'
import Register from '@/components/register.vue'
import Login from '@/components/login.vue'

Vue.use(Router);
//添加以下代码
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/index',
    },

    {
      path: '/index',
      name: 'Index',
      component: Index
    },

    {
      path: '/chat',
      name: 'Chat',
      meta: {
        requireAuth: true,
      },
      component: Chat
    },

    {
      path: '/user',
      name: 'User',
      meta: {
        requireAuth: true,
      },
      component: User
    },

    {
      path: '/register',
      name: 'Register',
      component: Register
    },

    {
      path: '/login',
      name: 'Login',
      component: Login
    },
  ]
});

router.beforeEach((to, from, next) => {
  let token = sessionStorage.getItem('token');
  if (to.meta.requireAuth) {
    if (token) {
      next();
    } else {
      // 没有token
      // alert('您还没有登录，请先登录')
      Vue.prototype.$confirm('您还未登录，请先登录', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 登录框显示
        next({
          path: '/login',
          // query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
        });
      }).catch((err) => {
        console.log(err);
      })
    }
  }
  else {
    next();
  }
});

export default router


