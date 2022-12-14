<template>
  <div class="comment">
    <div class="headerComment">
      <div class="sendComment">
        <div class="left">
          <el-image class="avatar" fit="cover" v-if="userInfo != null" :src="staticUrl + userInfo.avatarUrl" @click.native="toUserInfo(userInfo.id)"></el-image>
          <i v-else class="iconfont icon-account commentIcon"></i>
        </div>
        <div class="sendCommentRight">
          <div>
            <el-input
              class="commentTextarea"
              type="textarea"
              :rows="1"
              placeholder="发布你的评论"
              v-model="commentTextarea"
              :show-word-limit="true"
              :maxlength="this.commentTextareaLength"
              :autosize="{minRows:1, maxRows: 4}"
              resize="none">
            </el-input>
          </div>
          <div class="commentControl">
            <el-popover
              placement="bottom-start"
              width="500"
              trigger="click"
              :value="showEmojiStatus"
            >
              <emoji @selectEmoji="selectEmoji"></emoji>
              <i slot="reference" class="iconfont icon-smile commentIcon"></i>
            </el-popover>
            <el-button class="commentBtn" @click="addComment(dynamicId)" :disabled="commentStatus">评论</el-button>
          </div>
        </div>
      </div>
    </div>
    <div class="mainComment" v-for="(commentInfo, index) in commentList" :key="index">
      <div class="commentList" >
        <div class="left">
          <el-image class="avatar" fit="cover" :src="staticUrl + commentInfo.avatarUrl" @click.native="toUserInfo(commentInfo.userId)"></el-image>
        </div>
        <div class="right">
          <div class="rightHeader">
            <div class="answer">
              <span class="username" @click="toUserInfo(commentInfo.userId)">{{ commentInfo.userName }}</span>：{{ commentInfo.comment }}
            </div>
            <span class="postTime">{{ (commentInfo.sendTime).substring(0, 16) }} 来自 {{ commentInfo.schoolName }}</span>
          </div>
          <div class="serialNumber">
            {{ '#' + (index + 1) }}
          </div>
        </div>
      </div>
    </div>
    <no-comments v-if="commentList.length === 0"></no-comments>
  </div>
</template>

<script>
    import Emoji from "../../components/emoji/emoji";
    import NoComments from "../../components/noComments";
    export default {
      name: "comment",
      components: {NoComments, Emoji},
      props : {
        commentList : {
          type: Array,
        },

        dynamicId: {
          type: String,
        }
      },
      data() {
        return {
          commentTextarea:'',
          commentTextareaLength:140,
          commentStatus:true,
          showEmojiStatus:false,
          addCommentFlag: false,
          staticUrl: this.GLOBAL.staticUrl,
        }
      },

      methods: {
        selectEmoji(emoji) {
          if (this.commentTextarea.length < this.commentTextareaLength - 1)
            this.commentTextarea += emoji;
        },

        addComment(dynamicId) {
          if (sessionStorage.getItem('token') != null) {
            if (this.addCommentFlag == false) {
              this.addCommentFlag = true;
              let params = {
                comment: this.commentTextarea,
                dynamicId: dynamicId,
                userId: this.userInfo.id,
              };
              this.$api
                .sendComment(params)
                .then((res) => {
                  if (res.data.resultCode === 0) {
                    this.$message.success("评论成功");
                    this.commentTextarea = '';
                    this.addCommentFlag = false;
                    //更新评论列表
                    this.$emit('refreshCommentList', dynamicId);
                  }
                }).catch((err) => {
                this.$message.error(err);
                console.log(err);
              });
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

        toUserInfo(userId) {
          this.$emit('toUserInfo', userId);
        },
      },

      computed: {
        userInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      watch: {
        commentTextarea() {
          this.commentStatus = this.commentTextarea == '';
        },
      },
    }
</script>

<style scoped>
  .comment {
    padding-top: 5px;
    padding-bottom: 5px;
    width: 100%;
    display: flex;
    flex-direction: column;
  }
  .headerComment {
    display: flex;
    justify-content: center;
  }
  .sendComment {
    padding-top: 10px;
    padding-bottom: 10px;
    border-top:1px solid rgba(0,0,0,0.1);
    border-bottom:1px solid rgba(0,0,0,0.1);
    width: 95.2%;
    display: flex;
  }
  .avatar {
    cursor: pointer;
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
  .sendCommentRight {
    width: 100%;
    padding-left: 10px;
    text-align: start;
    display: flex;
    flex-direction: column;
  }
  .right {
    width: 100%;
    padding-left: 10px;
    text-align: start;
    display: flex;
    /*flex-direction: column;*/
  }
  .serialNumber {
    font-size: 14px;
    width: 6%;
    text-align: right;
  }
  .commentIcon {
    float: left;
    padding-top: 5px;
    font-size: 30px;
  }
  .commentIcon:hover {
    cursor: pointer;
  }
  .commentBtn {
    margin-top: 10px;
    float: right;
  }
  .mainComment {
    display: flex;
    justify-content: center;
    /*background-color: #795da3;*/
  }
  .commentList {
    padding-top: 10px;
    padding-bottom: 10px;
    /*background-color: #3967FF;*/
    width: 95.2%;
    display: flex;
  }
  .rightHeader {
    width: 94%;
    display: flex;
    flex-direction: column;
  }
  .answer {
    padding: 0 0 5px 0;
    font-size: 13px;
  }
  .username{
    cursor: pointer;
  }
  .postTime{
    font-size: 12px;
    opacity: 0.4;
  }
</style>
