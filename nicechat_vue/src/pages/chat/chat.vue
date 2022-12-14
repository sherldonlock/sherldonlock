<template>
  <div class="chat">
    <Header></Header>
    <el-main>
      <div class="chatBox">
        <div class="sidebar">
          <chatCard :user="loginUserInfo"></chatCard>
          <chatList
            :user="user"
            :userList="userList"
            @selectUser="selectUser"
          ></chatList>
        </div>
        <div class="chatMain">
          <div v-if="showDialog">
            <dialogBox
              ref="dialogBox"
              :sessionList="sessionList"
              :user="user"
              :loginUser="loginUserInfo"></dialogBox>
            <send-message @sendMessage="sendMessage"></send-message>
          </div>
          <div v-else>
            <div class="blank">
              <p class="tips">您还未选中或发起聊天，快去跟好友聊一聊吧</p>
            </div>
          </div>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script>
    import chatCard from "./chatCard";
    import chatList from "./chatList";
    import dialogBox from "./dialogBox";
    import SendMessage from "./sendMessage";
    import Header from "../../components/header";
    export default {
      name: "Chat",
      data () {
        return {

          // 用户列表
          userList: [],

          // 会话列表
          sessionList: [],

          websocket: '',

          user: {},

          showDialog: false,
        };
      },

      computed: {
        loginUserInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      mounted() {
        this.getUserList();
      },

      created() {
        this.localSocket();
      },

      methods: {

        localSocket() {

          let that = this;

          //判断当前浏览器是否支持WebSocket
          if('WebSocket' in window){
            this.websocket = new WebSocket("ws://localhost:6001/websocket/" + this.loginUserInfo.id);
          } else{
            alert('Not support websocket')
          }

          //连接发生错误的回调方法
          this.websocket.onerror = function(){
            console.log("socket连接失败");
          };

          //连接成功建立的回调方法
          this.websocket.onopen = function(event){
            // console.log("socket连接已打开");
          };

          //接收到消息的回调方法
          this.websocket.onmessage = function(event){
            let obj = JSON.parse(event.data);
            let data = {};
            if (obj.senderId === that.loginUserInfo.id) {
                data = {
                  userId: obj.senderId,
                  avatarUrl: obj.senderAvatarUrl,
                  content: obj.content,
                  sendTime: obj.sendTime,
                };
                that.sessionList.push(data);
            } else if (obj.receiverId === that.loginUserInfo.id && obj.senderId === that.user.id) {
                data = {
                  userId: obj.senderId,
                  avatarUrl: obj.senderAvatarUrl,
                  content: obj.content,
                  sendTime: obj.sendTime,
                };
                that.sessionList.push(data);
                that.updateReadFlag(that.user);
            }
            that.getUserList();
          };

          //连接关闭的回调方法
          this.websocket.onclose = function(){
            // console.log("socket连接已关闭");
          };

          //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
          window.onbeforeunload = function(){
            this.websocket.close();
          }
        },

        sendMessage(content) {
          let messageBody = {
            senderId: this.loginUserInfo.id,
            senderAvatarUrl: this.loginUserInfo.avatarUrl,
            receiverId: this.user.id,
            content: content,
          };
          this.websocket.send(JSON.stringify(messageBody));
          setTimeout(this.scrollDialogView, 200);
        },

        scrollDialogView() {
          this.$refs.dialogBox.scrollView();
        },

        updateReadFlag(item) {
          let params = {
            loginUserId: this.loginUserInfo.id,
            userId: item.id,
          };
          this.$api.updateReadFlag(params);
        },

        selectUser(item) {
          this.userList.forEach(x => {
            if (x.id === item.id) {
              x.unReadCount = 0;
            }
          });
          this.updateReadFlag(item);
          this.user = item;
          this.getDialog(item);
        },

        getDialog(item) {
          let params = {
            loginUserId: this.loginUserInfo.id,
            userId: item.id,
          };
          this.$api
              .getDialog(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.sessionList = res.data.data;
                  this.showDialog = true;
                  // this.getUserList();
                  setTimeout(this.scrollDialogView, 200);
                }
              })
        },

        getUserList() {
          let params = {
            loginUserId: this.loginUserInfo.id,
          };
          this.$api
              .getUserList(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.userList = res.data.data;
                }
              })
              .catch((err) => {
                console.log(err);
              })
        },
      },

      watch: {

      },
      components: {
        Header,
        SendMessage,
        chatList,
        chatCard,
        dialogBox,
      }
    }
</script>

<style scoped>
  .chat {
    /*text-align: center;*/
  }
  .el-main {
    margin-top: 55px;
    height: 873px;
    color: #333;
    text-align: center;
    display: flex;
    justify-content: center;
    overflow: hidden;
    background-color: #b3d4fc;
  }
  .chatBox {
    border-radius: 4px;
    height: 800px;
    width: 1000px;
  }
  .sidebar {
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px;
    float: left;
    height: 100%;
    width: 260px;
    color: #f4f4f4;
    background-color: #33353a;
  }
  .chatMain {
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
    height: 100%;
    overflow: hidden;
    background-color: #eee;
  }
  .blank {
    height: 800px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #a3a7ae;
  }
</style>
