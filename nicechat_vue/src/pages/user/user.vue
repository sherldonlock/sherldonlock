<template>
  <div>
    <Header></Header>
    <el-main>
      <div class="leftMain">
        <div class="aside">
          <Aside
            :menuList="menuList"
            @selectMenuItem="selectMenuItem"></Aside>
        </div>
      </div>
      <div class="middleMain">
        <div v-if="this.showPage == 'myIndex'">
          <user-card
            :userInfo="customerInfo"
            @getFocusOrFans="getFocusOrFans"></user-card>
          <show-dynamic
            @toUserInfo="toUserInfo"
            @selectTag="selectTag"
            v-for="(dynamic, index) in dynamicList"
            :key="index"
            :dynamic="dynamic"
            :myCollectIdList="myCollectIdList"
            :myLikeIdList="myLikeIdList"></show-dynamic>
          <no-data v-if="dynamicList.length === 0"></no-data>
        </div>
        <div v-else-if="this.showPage == 'myFocusAndFans'">
          <Title :title="title"></Title>
          <focus-andfans
            v-if="focusOrFansList.length > 0"
            @toUserInfo="toUserInfo"
            :focusOrFansList="focusOrFansList"></focus-andfans>
          <no-data v-if="focusOrFansList.length === 0" ></no-data>
        </div>
        <div v-else-if="this.showPage == 'myAgreeAndCollect'" class="myAgreeAndCollect">
          <Title :title="title"></Title>
          <show-dynamic
            @toUserInfo="toUserInfo"
            @selectTag="selectTag"
            v-for="(dynamic, index) in dynamicList"
            :key="index"
            :dynamic="dynamic"
            :myCollectIdList="myCollectIdList"
            :myLikeIdList="myLikeIdList"></show-dynamic>
          <no-data  v-if="dynamicList.length === 0" ></no-data>
        </div>
        <div v-else>
          <Title :title="title"></Title>
          <user-info></user-info>
        </div>
      </div>
      <div class="rightMain">
        <div class="calendar">
          <el-calendar></el-calendar>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script>
    import Aside from "../../components/aside";
    import Header from "../../components/header";
    import UserCard from "./userCard";
    import ShowDynamic from "../dynamic/showDynamic";
    import FocusAndfans from "./focusAndfans";
    import Title from "../../components/title";
    import UserInfo from "./userInfo";
    import NoData from "../../components/noData";
    export default {
      name: "User",
      components: {NoData, UserInfo, Title, FocusAndfans, ShowDynamic, UserCard, Header, Aside},
      data() {
        return {
          showPage: 'myIndex',
          title: '',
          menuList:[
            {"icon":"iconfont icon-guanliyuan", "name":"我的主页"},
            {"icon":"iconfont icon-add-account", "name":"我的关注"},
            {"icon":"iconfont icon-Customermanagement", "name":"我的粉丝"},
            {"icon":"iconfont icon-kehuquanyi", "name":"我的点赞"},
            {"icon":"iconfont icon-yiguanzhugongyingshang", "name":"我的收藏"},
            {"icon":"iconfont icon-tongxunlu", "name":"我的资料"},
          ],

          customerInfo: {},

          dynamicList: [],

          focusOrFansList: [],

          myCollectIdList: [],

          myLikeIdList: [],
        }
      },

      mounted() {
        window.addEventListener('scroll',this.handleScroll); // 监听滚动事件，然后用handleScroll这个方法进行相应的处理
        this.toUserInfo(this.userInfo.id);
        this.getMyCollectIdList();
        this.getMyLikeIdList();
      },

      computed: {
        userInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      methods: {

        selectMenuItem(item) {
          if (item.name == '我的主页') {
            this.toUserInfo(this.userInfo.id);
          } else if(item.name == '我的关注') {
            this.title = '我的关注';
            this.getMyFocusOrFans(this.userInfo.id, 'focus');
          } else if (item.name == '我的粉丝') {
            this.title = '我的粉丝';
            this.getMyFocusOrFans(this.userInfo.id, 'fans');
          } else if (item.name == '我的点赞') {
            this.title = '我的点赞';
            this.getMyLikeList();
          } else if (item.name == '我的收藏'){
            this.title = '我的收藏';
            this.getMyCollectList();
          } else {
            this.title = '我的资料';
            this.showPage = 'myMaterial';
          }
        },

        getMyLikeList() {
          let params = {
            userId: this.userInfo.id,
          };
          this.$api
              .getMyLikeList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.dynamicList = res.data.data;
                  this.showPage = 'myAgreeAndCollect';
                }
              })
              .catch((err) => {
                console.log(err);
              })
        },

        getMyCollectList() {
          let params = {
            userId: this.userInfo.id,
          };
          this.$api
            .getMyCollectList(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.dynamicList = res.data.data;
                this.showPage = 'myAgreeAndCollect';
              }
            })
            .catch((err) => {
              console.log(err);
            })
        },

        toUserInfo(userId) {
          let params = {
            userId: userId,
          };
          this.$api
            .getUserInfo(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.customerInfo = res.data.data;
                this.$api
                  .getDynamicListByUserId(params)
                  .then((res) => {
                    if (res.data.resultCode === 0) {
                      this.dynamicList = res.data.data;
                      this.showPage = 'myIndex';
                    }
                  })
                  .catch((err) => {
                    console.log(err);
                  })
              }
            })
            .catch((err) => {
              console.log(err);
            });
        },

        selectTag(topicName) {
          let params = {
            topicName: topicName,
          };
          this.$api
            .getDynamicList(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.dynamicList = res.data.data;
              }
              this.title = '#' + topicName + '#';
              this.showPage = 'myAgreeAndCollect';
            });
        },

        getMyFocusOrFans(userId, item) {
          if (item == 'focus') {
            //根据userId获取关注列表
            let params = {
              userId: userId,
            };
            this.$api
              .getFocusList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.focusOrFansList = res.data.data;
                  this.showPage = 'myFocusAndFans';
                }
              })
              .catch((err) => {
                console.log(err);
              });
          } else {
            //根据userId获取粉丝列表
            let params = {
              userId: userId,
            };
            this.$api
              .getFansList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.focusOrFansList = res.data.data;
                  this.showPage = 'myFocusAndFans';
                }
              })
              .catch((err) => {
                console.log(err);
              })
          }
        },

        getFocusOrFans(userInfo, item) {
          if (item == 'focus') {
            //根据userId获取关注列表
            let params = {
              userId: userInfo.id,
            };
            this.$api
              .getFocusList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.focusOrFansList = res.data.data;
                  this.title = userInfo.userName + "的关注";
                  this.showPage = 'myFocusAndFans';
                }
              })
              .catch((err) => {
                console.log(err);
              });
          } else {
            //根据userId获取粉丝列表
            let params = {
              userId: userInfo.id,
            };
            this.$api
              .getFansList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.focusOrFansList = res.data.data;
                  this.title = userInfo.userName + "的粉丝";
                  this.showPage = 'myFocusAndFans';
                }
              })
              .catch((err) => {
                console.log(err);
              })
          }
        },

        getMyCollectIdList() {
          let params = {
            userId: this.userInfo.id,
          };
          this.$api
            .getMyCollectIdList(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.myCollectIdList = res.data.data;
              }
            })
            .catch((err) => {
              console.log(err);
            })
        },

        getMyLikeIdList() {
          let params = {
            userId: this.userInfo.id,
          };
          this.$api
            .getMyLikeIdList(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.myLikeIdList = res.data.data;
              }
            })
            .catch((err) => {
              console.log(err);
            })
        },

        handleScroll() {
          let aside = document.querySelector('.aside');
          let calendar = document.querySelector('.calendar');
          if (aside != null && calendar != null) {
            if (window.pageYOffset >= 20) {
              aside.classList.add('aside-sticky');
              calendar.classList.add('aside-sticky');
            } else {
              aside.classList.remove('aside-sticky');
              calendar.classList.remove('aside-sticky');
            }
          }
        }
      }
    }
</script>

<style scoped>
  .el-main {
    margin-top: 55px;
    color: #333;
    background-color: #b3d4fc;
    text-align: center;
    display: flex;
    overflow: hidden;
  }
  .leftMain {
    height: 833px;
    width: 556px;
    margin-right: 10px;
    display: flex;
    justify-content: flex-end;
    /*background-color: #42b983;*/
  }
  .aside {
    border-radius: 4px;
    width: 208px;
    height: 806px;
    background-color: #ffffff;
  }
  .aside-sticky {
    position: fixed;
    top: 56px;
  }
  .middleMain {
    width: 694px;
    min-width: 694px;
    /*height: 806px;*/
    /*background-color: #ffffff;*/
  }
  .rightMain {
    width: 590px;
    height: 806px;
    margin-left: 10px;
    display: flex;
    flex-direction: column;
    /*background-color: #404040;*/
  }
  .calendar {
    width: 380px;
    height: 360px;
  }
  >>>.el-calendar {
    border-radius: 4px;
  }
  >>>.el-calendar-table .el-calendar-day{
    width: 48px;
    height: 39px;
  }
  .myAgreeAndCollect >>> .showDynamic {
    /*margin-top: 0;*/
    /*border-radius: 0;*/
  }
</style>
