<template>
  <div class="userCard">
    <div class="backgroundImg"/>
    <div class="userInfo">
      <div class="avatar_username_focus_fans">
        <div class="avatar_">
          <el-image
            class="avatar"
            fit="cover"
            :src="staticUrl + userInfo.avatarUrl"
            :preview-src-list="[staticUrl + userInfo.avatarUrl]">
            <div slot="error">

            </div>
          </el-image>
        </div>
        <div class="username_focus_fans">
          <div class="username">
            {{ userInfo.userName }}
          </div>
          <div class="focus_fans">
            <div class="dynamic" @click="getDynamic(userInfo, 'dynamic')">
              <span>动态</span> {{ userInfo.dynamicCount }}
            </div>
            <div class="focus" @click="getFocusOrFans(userInfo, 'focus')">
              <span>关注</span> {{ userInfo.focusCount }}
            </div>
            <div class="fans" @click="getFocusOrFans(userInfo, 'fans')">
              <span>粉丝</span> {{ userInfo.fansCount }}
            </div>
          </div>
        </div>
        <div class="focus_pm" v-if="this.loginUserInfo != null && this.loginUserInfo.id != this.userInfo.id">
          <el-button round v-if="this.myFocusIdList.indexOf(userInfo.id) >= 0"
                     style="width:84px; height:40px; background-color: rgb(204 204 204); border: 1px solid rgb(204 204 204);"
                     @click="cancelFocus(userInfo.id)">已关注</el-button>
          <el-button round v-else @click="focus(userInfo.id)"><i class="el-icon-plus"></i>关注</el-button>
          <el-button round @click="toChat(userInfo)"><i class="iconfont icon-atm"></i>私信</el-button>
        </div>
      </div>
      <div class="userMessage">
        <div class="showUserMessageBtn">
          <i class="iconfont icon-arrow-down showMegBtn" @click="toggleInfoShowFlag"></i>
        </div>
        <div v-if="infoShowFlag" class="Info">
          <div class="infoInner">
            <div class="Item">
              <div class="itemValue">
                {{ "学校: " + userInfo.schoolName }}
              </div>
            </div>
            <div class="Item">
              <div class="itemValue" v-if="userInfo.sex == 1">
                性别: 男
              </div>
              <div class="itemValue" v-else>
                性别: 女
              </div>
            </div>
            <div class="Item">
              <div class="itemValue" v-if="userInfo.birthday != null">
                {{  "年龄: " + (currentYear - (userInfo.birthday).substring(0, 4)) + "岁" }}
              </div>
              <div class="itemValue" v-else>
                年龄: 保密
              </div>
            </div>
            <div class="Item">
              <div class="itemValue" v-if="userInfo.emotion == 0">
                感情状况: 保密
              </div>
              <div class="itemValue" v-else-if="userInfo.emotion == 1">
                感情状况: 单身
              </div>
              <div class="itemValue" v-else-if="userInfo.emotion == 2">
                感情状况: 求交往
              </div>
              <div class="itemValue" v-else-if="userInfo.emotion == 3">
                感情状况: 恋爱中
              </div>
            </div>
            <div class="Item">
              <div class="itemValue">
                {{ (userInfo.createDate).substring(0, 10) + " " + "加入Nicechat"}}
              </div>
            </div>
            <div class="Item">
              <div class="itemValue" v-if="userInfo.introduction == ''">
                简介: 这个人很懒什么都没留下~
              </div>
              <div class="itemValue" v-else>
                {{ "简介: " + userInfo.introduction }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "userCard",
      data() {
        return {
          infoShowFlag:false,
          staticUrl: this.GLOBAL.staticUrl,
          currentYear: new Date().getFullYear(),
          myFocusIdList: [],
        }
      },

      props: {
        userInfo: {
          type: Object,
        }
      },

      computed: {
        loginUserInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      mounted() {
        if (this.loginUserInfo != null) {
          this.getMyFocusIdList();
        }
      },

      methods: {

        getMyFocusIdList() {
          let params = {
            userId: this.loginUserInfo.id,
          };
          this.$api
              .getMyFocusIdList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.myFocusIdList = res.data.data;
                }
              })
              .catch((err) => {
                console.log(err);
              })
        },

        cancelFocus(userId) {
          let params = {
            loginUserId: this.loginUserInfo.id,
            userId: userId,
            };
          this.$api
              .cancelFocus(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.getMyFocusIdList();
                  this.$message.success("取关成功")
                }
              })
              .catch((err) => {
                console.log(err);
              })
        },

        focus(userId) {
          if (sessionStorage.getItem('token') != null) {
            let params = {
              loginUserId: this.loginUserInfo.id,
              userId: userId,
            };
            this.$api
              .Focus(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.getMyFocusIdList();
                  this.$message.success("关注成功")
                }
              })
              .catch((err) => {
                console.log(err);
              })
          } else {
            // 没有token
            // alert('您还没有登录，请先登录')
            this.$confirm('您还未登录，请先登录', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$router.push('login');
            }).catch((err) => {
              console.log(err);
            })
          }
        },

        toggleInfoShowFlag() {
          this.infoShowFlag = !this.infoShowFlag;
        },

        getDynamic(userInfo) {

        },

        getFocusOrFans(userInfo, item) {
          this.$emit('getFocusOrFans', userInfo, item);
        },

        toChat(userInfo) {
          if (sessionStorage.getItem('token') != null) {
            let params = {
              loginUserId: this.loginUserInfo.id,
              userId: userInfo.id,
            };
            this.$api
              .chatWith(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.$router.push({
                    name: 'Chat',
                    query: {
                      userInfo
                    }
                  });
                }
              })
              .catch((err) => {
                this.$message.error(err);
              })
          } else {
            // 没有token
            // alert('您还没有登录，请先登录')
            this.$confirm('您还未登录，请先登录', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$router.push('login');
            }).catch((err) => {
              console.log(err);
            })
          }
        },
      },

    }
</script>

<style scoped>
  .userCard {
    width: 100%;
    /*height: 380px;*/
    border-radius: 4px;
    background-color: #ffffff;
  }
  .backgroundImg {
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    width: 100%;
    height: 240px;
    background-image: url("../../assets/img/banner.png");
  }
  .userInfo {
    width: 100%;
    padding-bottom: 10px;
    /*height: 140px;*/
    /*background-color: #2441a2;*/
  }
  .avatar_username_focus_fans {
    width: 100%;
    height: 50px;
    /*background-color: red;*/
    display: flex;
  }
  .avatar_ {
    margin: -48px 0 0 20px;
  }
  .avatar {
    border-radius: 4px;
    height: 96px;
    width: 96px;
    /* 加入下面css */
    image-rendering: -moz-crisp-edges;
    image-rendering: -o-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: crisp-edges;
    -ms-interpolation-mode: nearest-neighbor;
    background-color: #ffffff;
  }
  .username_focus_fans {
    text-align: start;
    margin-left: 20px;
    width: 345px;
  }
  .focus_pm {
    margin-top: 10px;
  }
  .username {
    height: 20px;
    margin-top: 5px;
    font-size: 16px;
    font-weight: 700;
  }
  .focus_fans {
    margin-top: 3px;
    display: flex;
    font-size: 14px;
  }
  .dynamic:hover {
    cursor: pointer;
  }
  .focus:hover {
    cursor: pointer;
  }
  .fans:hover {
    cursor: pointer;
  }
  .focus {
    margin-left: 10px;
  }
  .fans {
    margin-left: 10px;
  }
  .dynamic>span {
    color: #9393af;
  }
  .focus>span {
    color: #9393af;
  }
  .fans>span {
    color: #9393af;
  }
  .showUserMessageBtn {
    display: flex;
  }
  .showMegBtn {
    margin-left: 660px;
    font-size: 25px;
  }
  .showMegBtn:hover {
    cursor: pointer;
  }
  .Info {
    width: 100%;
    padding-bottom: 10px;
    /*height: 200px;
    background-color: #DD4A68;*/
  }
  .infoInner {
    margin-left: 24px;
    width: 50%;
    /*height: 150px;
    background-color: #0086b3;*/
    display: flex;
    flex-direction: column;
    text-align: start;
  }
  .Item {
    display: flex;
    align-items: center;
    /*background-color: #669900;*/
    margin-bottom: 5px;
    opacity: 0.5;
  }
  .itemValue {
    font-size: 14px;
    /*margin-left: 5px;*/
  }
  >>>.is-round {
    padding: 11px 19px;
  }
</style>
