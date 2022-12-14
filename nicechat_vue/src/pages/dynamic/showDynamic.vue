<template>
  <div>
    <div class="showDynamic">
      <div class="showBox">
        <div class="bodyBox">
          <div class="left">
            <el-image class="avatar" fit="cover" :src="staticUrl + dynamic.avatarUrl" @click.native="toUserInfo(dynamic.userId)"></el-image>
          </div>
          <div class="right">
            <div class="rightHeader">
              <div class="nameBox">
                <span class="username" @click="toUserInfo(dynamic.userId)">{{ dynamic.userName }}</span>
              </div>
              <span class="postTime">{{(dynamic.postTime).substring(0, 16) }} 来自 {{ dynamic.schoolName }}</span>
            </div>
            <div class="rightContent">
              <div class="fontContent">
                {{ dynamic.content }}
              </div>
              <div class="imgContent" v-if="dynamic.imgList.length > 0">
                <ul class="imgList">
                  <li v-for="img in dynamic.imgList">
                    <el-image fit="cover" class="image" :src="staticUrl + img.imageUrl" :preview-src-list="dynamic.imgList.map(x => {return staticUrl + x.imageUrl})">
                    </el-image>
                  </li>
                </ul>
              </div>
              <div class="tagContent" v-if="dynamic.tagList.length > 0">
                <el-tag size="small" class="tag" v-for="(tag, index) in dynamic.tagList" :key="index" @click="selectTag(tag.topicName)">{{ "#" + tag.topicName + "#" }}</el-tag>
              </div>
            </div>
          </div>
          <div class="component" v-if="this.userInfo != null && this.userInfo.id == dynamic.userId">
            <el-popover
              placement="bottom-end"
              trigger="click"
              :offset="8"
              >
              <span @click="deleteDynamic(dynamic)" style="cursor: pointer;">删除动态</span>
              <i slot="reference" style="font-size: 25px; cursor: pointer;" class="iconfont icon-arrow-down"></i>
            </el-popover>
          </div>
        </div>
        <div class="footerBox">
          <div @click="collectDynamic(dynamic.id)">
            <i v-if="this.myCollectIds.indexOf(dynamic.id) >= 0" class="iconfont icon-collection-fill"></i>
            <i v-else class="iconfont icon-collection"></i>
            {{ (dynamic.collectCount + collectCount) }}
          </div>
          <div @click="toggleCommentShowFlag(dynamic.id)">
            <i v-if="this.commentShowFlag" class="iconfont icon-comments-fill"></i>
            <i v-else class="iconfont icon-comments"></i>
            {{ (dynamic.commentCount + commentCount) }}
          </div>
          <div @click="likeDynamic(dynamic.id)">
            <i v-if="this.myLikeIds.indexOf(dynamic.id) >= 0" class="iconfont icon-good-fill"></i>
            <i v-else class="iconfont icon-good"></i>
            {{ (dynamic.likeCount + likeCount) }}
          </div>
        </div>
        <comment
          v-if="commentShowFlag"
          @toUserInfo="toUserInfo"
          :commentList="commentList"
          @refreshCommentList="refreshCommentList"
          :dynamicId="dynamic.id"
        ></comment>
      </div>
    </div>
  </div>
</template>

<script>
    import SearchBox from "../../components/searchBox";
    import Comment from "./comment";
    export default {
      name: "showDynamic",
      components: {Comment, SearchBox},
      data() {
        return {
          staticUrl: this.GLOBAL.staticUrl,
          commentShowFlag: false,
          collectFlag: true,
          likeFlag: true,
          commentList: [],
          myCollectIds: this.myCollectIdList,
          myLikeIds: this.myLikeIdList,
          commentCount: 0,
          collectCount: 0,
          likeCount: 0,
        }
      },

      props: {
        dynamic: {
          type: Object,
        },

        myCollectIdList: {
          type: Array,
        },

        myLikeIdList: {
          type: Array,
        },
      },

      computed: {
        userInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      watch: {
        myCollectIdList: function (newVal, oldVal) {
          this.myCollectIds = newVal;
        },

        myLikeIdList: function (newVal, oldVal) {
          this.myLikeIds = newVal;
        },
      },

      mounted() {

      },

      methods: {

        deleteDynamic(dynamic) {
          this.$confirm('确定删除此动态么', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let params = {
              dynamicId: dynamic.id,
            };
            this.$api
                .deleteDynamic(params)
                .then((res) => {
                  if (res.data.resultCode === 0) {
                    this.$message.success("删除动态成功");
                    window.location.reload();
                  } else {
                    this.$message.error("删除动态失败");
                  }
                })
                .catch((err) => {
                  this.$message.error(err);
                  console.log(err);
                })
          }).catch((err) => {
            console.log(err);
          });
        },

        collectDynamic(dynamicId) {
          if (sessionStorage.getItem('token') != null) {
            let params = {
              dynamicId: dynamicId,
              loginUserId: this.userInfo.id,
            };
            if (this.myCollectIds.indexOf(dynamicId) >= 0) {
              this.cancelCollectDynamic(params);
            } else {
              this.setCollectDynamic(params);
            }
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

        setCollectDynamic(params) {
          this.$api
            .setCollectDynamic(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.collectCount += 1;
                this.myCollectIds = res.data.data;
              }
            })
            .catch((err) => {
              this.$message.error(err);
              console.log(err);
            })
        },

        cancelCollectDynamic(params) {
          this.$api
            .cancelCollectDynamic(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.collectCount -= 1;
                this.myCollectIds = res.data.data;
              }
            })
            .catch((err) => {
              this.$message.error(err);
              console.log(err);
            })
        },

        likeDynamic(dynamicId) {
          if (sessionStorage.getItem('token') != null) {
            let params = {
              dynamicId: dynamicId,
              loginUserId: this.userInfo.id,
            };
            if (this.myLikeIds.indexOf(dynamicId) >= 0) {
              this.cancelLikeDynamic(params);
            } else {
              this.setLikeDynamic(params);
            }
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

        setLikeDynamic(params) {
          this.$api
            .setLikeDynamic(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.likeCount += 1;
                this.myLikeIds = res.data.data;
              }
            })
            .catch((err) => {
              this.$message.error(err);
              console.log(err);
            })
        },

        cancelLikeDynamic(params) {
          this.$api
            .cancelLikeDynamic(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.likeCount -= 1;
                this.myLikeIds = res.data.data;
              }
            })
            .catch((err) => {
              this.$message.error(err);
              console.log(err);
            })
        },

        toggleCommentShowFlag(dynamicId) {
          if(this.commentShowFlag == false){
            //更新评论列表
            this.getCommentList(dynamicId);
          }
          this.commentShowFlag = !this.commentShowFlag;
        },

        refreshCommentList(dynamicId) {
          this.getCommentList(dynamicId);
          this.commentCount += 1;
        },

        getCommentList(dynamicId) {
          let params = {
            dynamicId: dynamicId,
          };
          this.$api
            .getCommentList(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.commentList = res.data.data;
              }
            }).catch((err) => {
              console.log(err);
          })
        },

        toUserInfo(userId) {
          this.$emit('toUserInfo', userId);
        },

        selectTag(topicName) {
          this.$emit('selectTag', topicName);
        },
      },
    }
</script>

<style scoped>
  .showDynamic {
    margin-top: 10px;
    border-radius: 4px;
    width: 100%;
    background-color: #ffffff;
    /*box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);*/
  }
  .showBox {
    display: flex;
    flex-direction: column;
    border-radius: 4px;
    width: 100%;
    height:30%;
  }
  .bodyBox {
    display: flex;
  }
  .left{
    padding: 17px 14px;
    height: 40px;
    width: 40px;
  }
  .left .el-image {
    border-radius: 4px;
    width: 100%;
    height: 100%;
  }
  .right{
    text-align: start;
    width: 85%;
    padding:0 1px;
  }
  .component{
    margin-top: 12px;
  }
  .rightHeader {
    margin-top: 10px;
    width: 100%;
    display: flex;
    flex-direction: column;
  }
  .avatar {
    cursor: pointer;
  }
  .nameBox {
    padding: 5px 0;
  }
  .username{
    font-weight: bold;
    cursor: pointer;
    font-size: 14px;
  }
  .postTime{
    font-size: 12px;
    opacity: 0.4;
  }
  .rightContent {
    padding: 5px 0;
  }
  .fontContent {
    font-size: 14px;
  }
  .imgContent {
    padding-top: 10px;
    width: 80%;
  }
  .imgList::after{
    content: "";
    display: block;
    clear: both;
    visibility: hidden;
  }
  .imgList>li{
    float: left;
    text-align: center;
    list-style: none;
  }
  .image {
    border-radius: 4px;
    margin-right: 4px;
    width: 140px;
    height:140px;
    /* 加入下面css */
    image-rendering: -moz-crisp-edges;
    image-rendering: -o-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: crisp-edges;
    -ms-interpolation-mode: nearest-neighbor;
  }
  .imgList>li:nth-of-type(3n){
    margin-right: 0;
  }
  .imgList>li:nth-of-type(n+7){
    margin-bottom: 0;
  }
  .tagContent {
    padding-top: 5px;
  }
  .tag {
    cursor: pointer;
  }
  .footerBox{
    width: 100%;
    height: 40px;
    display: flex;
    justify-content:space-around;
  }
  .footerBox > div{
    padding: 10px 0;
    width: 30%;
    height: 20px;
    text-align: center;
    cursor: pointer;
    /*font-size: 17px;*/
    line-height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #777777;
  }
  .footerBox > div:hover{
    /*color: #525252;*/
  }
  .footerBox > div >i{
    font-size: 17px;
    padding: 5px;
  }
  .footerBox > div:nth-child(1){
    border-right:1px solid rgba(0,0,0,0.1);
  }
  .footerBox > div:nth-child(2){
    border-right:1px solid rgba(0,0,0,0.1);
  }
</style>
