<template>
  <div class="header">
    <div style="width: 690px;">
      <img class="logo" src="../../src/assets/img/logo.png"/>
    </div>
    <div>
      <div class="tabs">
        <!--<el-tabs  v-model="this.headerName" @tab-click="handleClick">
          <el-tab-pane name="Index"><span slot="label"><i class="iconfont icon-search icon"></i></span></el-tab-pane>
          <el-tab-pane name="Chat"><span slot="label"><i class="iconfont icon-search icon"></i></span></el-tab-pane>
          <el-tab-pane name="User"><span slot="label"><i class="iconfont icon-search icon"></i></span></el-tab-pane>
        </el-tabs>-->
        <!--<el-menu
          router :default-active="$route.path"
          class="el-menu-demo"
          mode="horizontal"

          text-color="#fff"
          active-text-color="#525252"
          @select="handleClick">
          <el-menu-item index="Index"><i class="iconfont icon-search icon"></i></el-menu-item>
          <el-menu-item index="Chat"><i class="iconfont icon-search icon"></i></el-menu-item>
          <el-menu-item index="User"><i class="iconfont icon-search icon"></i></el-menu-item>
        </el-menu>-->
        <div class="tab" @click="selectPage('/index', 0)">
          <div class="tabInner">
            <i class="iconfont icon-home icon"></i>
          </div>
          <div class="tabBorder"></div>
        </div>
        <div class="tab" @click="selectPage('/chat', 1)">
          <div class="tabInner">
            <i class="iconfont icon-atm icon"></i>
          </div>
          <div class="tabBorder"></div>
        </div>
        <div class="tab" @click="selectPage('/user', 2)">
          <div class="tabInner" v-if="loginFlag != null">
            <el-image class="avatar" fit="cover" :src="staticUrl + userInfo.avatarUrl"></el-image>
          </div>
          <div class="tabInner" v-else>
            <i class="iconfont icon-account icon"></i>
          </div>
          <div class="tabBorder"></div>
        </div>
        <div class="tab userHandle">
          <div class="notLogin" v-if="loginFlag == null" >
            <span @click="toLogin">登录</span> | <span @click="toRegister">注册</span>
          </div>
          <div class="isLogin" v-else>
            <span @click="logout">退出登录</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "Header",
      data() {
        return {
          staticUrl: this.GLOBAL.staticUrl,
          loginFlag: sessionStorage.getItem('token'),
        }
      },

      mounted() {
        let headerIndex = sessionStorage.getItem('headerIndex');
        if (headerIndex == null) headerIndex = 0;
        let tabBorders = document.querySelectorAll('.tabBorder');
        tabBorders[headerIndex].classList.add("tabBorder-checked");
      },

      methods: {
        selectPage(page, index) {
          this.$router
              .push(page)
              .then(() => {
                let tabBorders = document.querySelectorAll('.tabBorder');
                let headerIndex = sessionStorage.getItem('headerIndex');
                if (tabBorders.length > 0 && headerIndex != null && index != headerIndex) {
                  tabBorders[headerIndex].classList.remove("tabBorder-checked");
                }
                if (tabBorders.length > 0) {
                  tabBorders[index].classList.add("tabBorder-checked");
                  this.$store.commit('setHeaderIndex', index);
                }
              });
        },

        toLogin() {
          this.$router.push("/login");
        },

        toRegister() {
          this.$router.push("/register");
        },

        logout() {
          this.$api.logout().then((res) => {
            if (res.data.resultCode === 0) {
              this.$router.push("/index");
              sessionStorage.removeItem('token');
              sessionStorage.removeItem('userInfo');
              window.location.reload();
            } else {
              this.$message.error(res.data.resultMessage);
            }
          }).catch((err) => {
            this.$message.error(err);
            console.log(err)
          });
        },
      },

      computed: {
        userInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      watch: {

      },

     /* beforeDestroy() {
        sessionStorage.removeItem('headerIndex');
      }*/
    }
</script>

<style scoped>
  .header {
    position: fixed;
    /*margin: auto;*/
    z-index: 3000;
    top: 0;
    width: 100%;
    background-color: #ffffff;
    color: #333;
    display: flex;
    height: 56px;
    /*justify-content: center;*/
  }
  .logo {
    margin-left: 300px;
  }
  .tabs {
    /*margin-right: 40px;*/
    width: 550px;
    height: 55px;
    display: flex;
    justify-content: space-around;
  }
  .tab {
    width: 70px;
    cursor: pointer;
  }
  .tabInner {
    margin-top: 5px;
    display: flex;
    justify-content: center;
  }
  .avatar {
    border-radius: 4px;
    height: 40px;
    width: 40px;
    /* 加入下面css */
    image-rendering: -moz-crisp-edges;
    image-rendering: -o-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: crisp-edges;
    -ms-interpolation-mode: nearest-neighbor;
  }
  .tabInner:hover {
    background-color: #f2f2f2;
  }
  .tabBorder-checked {
    margin-top: 9px;
    background-color: #525252;
    height: 2px;
    width: 100%;
  }
  .icon {
    font-size: 37px;
  }
  .userHandle {
    cursor: auto;
  }
  .notLogin, .isLogin{
    /*background-color: #DD4A68;*/
    width: 80px;
    margin-top: 12px;
  }
  .notLogin, .isLogin > span {
    cursor: pointer;
  }
</style>
