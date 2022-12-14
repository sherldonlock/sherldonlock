<template>
  <div class="message">
    <div class="messageHeader">
      <span class="name">{{ user.userName }}</span>
    </div>
    <div class="messageMain">
      <ul style="list-style: none">
        <li v-for="item in sessionList">
          <div v-if="item.userId != loginUser.id" class="other">
            <div>
              <el-image class="avatar" fit="cover" :src="staticUrl + item.avatarUrl"></el-image>
            </div>
            <div class="sendMessageOther">
              <div class="sendTimeOther">
                <div>{{ item.sendTime }}</div>
              </div>
              <div class="otherText">
                <div style="padding: 5px 0; min-height: 21px; min-width: 21px;">
                  {{ item.content }}
                </div>
              </div>
            </div>
          </div>
          <div v-else class="self">
            <div class="sendMessageSelf">
              <div class="sendTimeSelf">
                <div>{{ item.sendTime }}</div>
              </div>
              <div class="selfText">
                <div style="padding: 5px 0; min-height: 21px; min-width: 21px;">
                  {{ (item.content) }}
                </div>
              </div>
            </div>
            <div>
              <el-image class="avatar" fit="cover" :src="staticUrl + item.avatarUrl"></el-image>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
    export default {
        name: "dialogBox",
      data() {
          return {
            staticUrl: this.GLOBAL.staticUrl,
          }
      },

      props: {
        sessionList: {
          type:Array,
        },

        user: {
          type: Object,
        },

        loginUser: {
          type: Object,
        },
      },

      methods: {
          scrollView() {
            let messageScroll = document.querySelector('.messageMain');
            messageScroll.scrollTop = 99999;
          },
      },
    }
</script>

<style scoped>
  .message {
    height: 570px;
  }
  .messageHeader {
    height: 50px;
    border-bottom:1px solid rgba(0,0,0,0.1);
    display: flex;
    align-items: center;
  }
  .name {
    margin-left: 10px;
    color: #a3a7ae;
  }
  .messageMain {
    padding: 30px 15px;
    height: 500px;
    overflow-y: scroll;
    background-color: #eeeeee;
    scroll-behavior:smooth;
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
  .other {
    width: 100%;
    display: flex;
    padding-bottom: 20px;
  }
  .sendMessageOther {
    margin-left: 10px;
    max-width: 50%;
    display: flex;
    flex-direction: column;
    align-items: baseline;
    text-align: left;
  }
  .sendTimeOther {
    font-size: 14px;
    opacity: 0.4;
  }
  .otherText {
    margin-top: 3px;
    border-radius: 4px;
    background-color: #ffffff;
    padding: 0 7px;
  }
  .self {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    padding-bottom: 20px;
  }
  .sendMessageSelf {
    max-width: 50%;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    margin-right: 10px;
  }
  .sendTimeSelf {
    font-size: 14px;
    opacity: 0.4;
  }
  .selfText {
    text-align: left;
    margin-top: 3px;
    margin-left: 10px;
    border-radius: 4px;
    background-color: #b2e281;
    padding: 0 7px;
  }
</style>
