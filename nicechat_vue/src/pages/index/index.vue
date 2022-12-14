<template>
  <div class="index">
    <Header></Header>
    <el-main>
        <div class="leftMain">
          <div class="aside">
            <Aside
              :menuList="menuList"
              :hotTopics="hotTopicsTopTen"
              @getHotTopicItem="getHotTopicItem"
              @selectMenuItem="selectMenuItem"></Aside>
          </div>
        </div>
        <div class="middleMain">
          <div v-if="this.showPage == 'sendAndShowDynamic'">
            <SendDynamic
              @getDynamicList="getDynamicList"
              :topics="topicList"></SendDynamic>
            <keep-alive>
              <search-box
                :showSchoolFlag="showSchoolFlag"
                @clickSearch="clickSearch"
                :topics="topicList"></search-box>
            </keep-alive>
            <div>
              <show-dynamic
                @toUserInfo="toUserInfo"
                @selectTag="selectTag"
                v-for="(dynamic, index) in dynamicList"
                :key="index"
                :dynamic="dynamic"
                :myCollectIdList="myCollectIdList"
                :myLikeIdList="myLikeIdList"
              ></show-dynamic>
              <no-data v-if="dynamicList.length === 0" ></no-data>
            </div>
          </div>
          <div v-else-if="this.showPage == 'userInfo'">
            <user-card :userInfo="customerInfo" @getFocusOrFans="getFocusOrFans"></user-card>
            <show-dynamic
              @toUserInfo="toUserInfo"
              @selectTag="selectTag"
              v-for="(dynamic, index) in dynamicList"
              :key="index"
              :dynamic="dynamic"
              :myCollectIdList="myCollectIdList"
              :myLikeIdList="myLikeIdList"
            ></show-dynamic>
            <no-data v-if="dynamicList.length === 0" ></no-data>
          </div>
          <div v-else-if="this.showPage == 'hotTopics'" class="hotTopics">
            <Title :title="title"></Title>
            <show-dynamic
              @toUserInfo="toUserInfo"
              @selectTag="selectTag"
              v-for="(dynamic, index) in dynamicList"
              :key="index"
              :dynamic="dynamic"
              :myCollectIdList="myCollectIdList"
              :myLikeIdList="myLikeIdList"
            ></show-dynamic>
          </div>
          <div v-else-if="this.showPage == 'myFocusAndFans'">
            <Title :title="title"></Title>
            <focus-andfans
              v-if="focusOrFansList.length > 0"
              @toUserInfo="toUserInfo"
              :focusOrFansList="focusOrFansList"
            ></focus-andfans>
            <no-data v-else ></no-data>
          </div>
        </div>
        <div class="rightMain">
          <div class="rightComponent">
            <weather/>
            <recommend
              :recommendList="recommendList"
              @selectRecommendItem="selectRecommendItem"
              @refreshRecommendList="this.getRecommendList"/>
          </div>
        </div>
      </el-main>
  </div>
</template>


<script>
  import SendDynamic from "../dynamic/sendDynamic";
  import Aside from "../../components/aside";
  import ShowDynamic from "../dynamic/showDynamic";
  import SearchBox from "../../components/searchBox";
  import Weather from "../../components/weather";
  import Recommend from "../../components/recommend";
  import Header from "../../components/header";
  import UserCard from "../user/userCard";
  import Title from "../../components/title";
  import FocusAndfans from "../user/focusAndfans";
  import NoData from "../../components/noData";
  import Vue from "vue";

  export default {
    name: "Index",
    components: {
      NoData,
      Title,
      UserCard,
      Header,
      Recommend,
      Weather,
      SearchBox,
      ShowDynamic,
      Aside,
      SendDynamic,
      FocusAndfans,
    },
    data() {
      return {
        showPage: 'sendAndShowDynamic',
        showSchoolFlag: false,
        title: '',
        menuList:[
          {"icon":"iconfont icon-Exportservices", "name":"探索发现"},
          {"icon":"iconfont icon-company", "name":"校园圈子"},
          {"icon":"iconfont icon-category", "name":"全部关注"},
        ],

        hotTopicsTopTen: [],

        customerInfo: {},

        dynamicList: [],

        focusOrFansList: [],

        recommendList: [],

        myCollectIdList: [],

        myLikeIdList: [],

        topicList: [],
      }
    },

    mounted(){
      window.addEventListener('scroll',this.handleScroll); // 监听滚动事件，然后用handleScroll这个方法进行相应的处理
      this.getDynamicList();
      this.getHotTopicsTopTen();
      this.getTopicList();
      if (this.userInfo != null) {
        this.getRecommendList();
        this.getMyCollectIdList();
        this.getMyLikeIdList();
      }
    },

    activated() {

    },

    methods: {

      clickSearch(params) {
        this.getDynamicList(params);
      },

      getRecommendList() {
        if (sessionStorage.getItem('token') != null) {
          let params = {
            loginUserId: this.userInfo.id,
            schoolCode: this.userInfo.schoolCode,
          };
          this.$api
            .getRecommendList(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.recommendList = res.data.data;
              }
            }).catch((err) => {
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

      getDynamicList(params) {
        this.$api
          .getDynamicList(params)
          .then((res) => {
            if (res.data.resultCode === 0) {
              this.dynamicList = res.data.data;
            }
          })
      },

      getHotTopicsTopTen() {
        this.$api
          .getHotTopicsTopTen()
          .then((res) => {
            if (res.data.resultCode === 0) {
              this.hotTopicsTopTen = res.data.data;
            }
          })
      },

      getTopicList() {
        this.$api
          .getTopicList()
          .then((res) => {
            if (res.data.resultCode === 0) {
              this.topicList = res.data.data;
            }
          }).catch((err) => {
          console.log(err);
        })
      },

      getHotTopicItem(item) {
        //根据热点话题获取动态
        let params = {
          topicName: item.topicName,
        };
        this.$api
          .getDynamicList(params)
          .then((res) => {
            if (res.data.resultCode === 0) {
              this.dynamicList = res.data.data;
            }
            this.title = '#' + item.topicName + '#';
            this.showPage = 'hotTopics';
          });
      },

      selectMenuItem(item) {
        //根据侧栏获取动态
        if (item.name == '校园圈子') {
          if (sessionStorage.getItem('token') != null) {
            let params = {
              schoolName: this.userInfo.schoolName,
            };
            this.getDynamicList(params);
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
        } else if (item.name == '全部关注') {
          if (sessionStorage.getItem('token') != null) {
            let params = {
              userId: this.userInfo.id,
            };
            this.getDynamicList(params);
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
        } else {
          this.getDynamicList();
        }
        this.showSchoolFlag = item.name == '校园圈子';
        this.showPage = 'sendAndShowDynamic';
      },

      selectRecommendItem(item) {
        //根据推荐的用户获取用户详情
        let params = {
          userId: item.id,
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
                    this.showPage = 'userInfo';
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
                    this.showPage = 'userInfo';
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
            this.showPage = 'hotTopics';
          });
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
        let rightComponent = document.querySelector('.rightComponent');
        if (aside != null && rightComponent != null) {
          if (window.pageYOffset >= 20) {
            aside.classList.add('aside-sticky');
          } else {
            aside.classList.remove('aside-sticky');
          }
          if (window.pageYOffset >= 230) {
            rightComponent.classList.add('rightComponent-sticky');
          } else {
            rightComponent.classList.remove('rightComponent-sticky');
          }
        }
      }
    },

    computed: {
      userInfo() {
        return JSON.parse(sessionStorage.getItem("userInfo"));
      },
    },

    watch: {

    },
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
    /*height: 1100px;*/
  }
  .leftMain {
    height: 833px;
    width: 556px;
    margin-right: 10px;
    display: flex;
    justify-content: flex-end;
  }
  .aside {
    width: 208px;
    height: 806px;
    background-color: #ffffff;
    border-radius: 4px;
  }
  .aside-sticky {
    position: fixed;
    top: 56px;
  }
  .middleMain {
    width: 694px;
  }
  .rightMain {
    width: 590px;
    /*height:1600px;*/
    /*background-color: mediumseagreen;*/
    margin-left: 10px;
    display: flex;
    flex-direction: column;
  }
  .rightComponent-sticky {
    position: fixed;
    top: -152px;
  }
  .hotTopics >>> .showDynamic {
    /*margin-top: 0;*/
    /*border-radius: 0;*/
   }
</style>
