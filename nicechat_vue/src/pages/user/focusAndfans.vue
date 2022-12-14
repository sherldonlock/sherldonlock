<template>
  <div class="focusAndfans">
    <div class="listMain">
      <div class="list">
        <div class="userInfo" v-for="(item, index) in focusOrFansList" :key="index" @click="toUserInfo(item.id)">
          <div class="userInfoInner">
            <div class="avatar_">
              <el-image class="avatar" fit="cover" :src="staticUrl + item.avatarUrl"></el-image>
            </div>
            <div class="username_school_dynamic_fans">
              <div class="username">
                {{ item.userName }}
              </div>
              <div class="school">
                {{ item.schoolName }}
              </div>
              <div class="dynamic_fans">
                <div class="dynamic">
                  <span>动态</span> {{ item.dynamicCount }}
                </div>
                <div class="fans">
                  <span>粉丝</span> {{ item.fansCount }}
                </div>
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
      name: "focusAndfans",
      components: {},
      data() {
        return {
          staticUrl: this.GLOBAL.staticUrl,
        }
      },

      methods: {
        toUserInfo(userId) {
          this.$emit('toUserInfo', userId);
        },
      },

      props: {
        title: {
          type: String,
        },

        focusOrFansList : {
          type: Array
        },
      }
    }
</script>

<style scoped>
  .focusAndfans {
    border-radius: 4px;
    width: 100%;
    background-color: #ffffff;
  }
  .listMain {
    margin-top: 10px;
  }
  .list {
    width: 100%;
    padding-top: 15px;
    /*background-color: #DD4A68;*/
    display: flex;
    flex-direction: column;
    /*padding-bottom: 10px;*/
  }
  .userInfo {
    height: 72px;
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
  }
  .userInfo:hover {
    cursor: pointer;
    background-color: #f0f1f4;
  }
  .userInfoInner {
    width: 94%;
    text-align: start;
    display: flex;
    height: 70px;
  }
  .avatar_ {

  }
  .avatar {
    border-radius: 4px;
    height: 70px;
    width: 70px;
    /* 加入下面css */
    image-rendering: -moz-crisp-edges;
    image-rendering: -o-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: crisp-edges;
    -ms-interpolation-mode: nearest-neighbor;
  }
  .username_school_dynamic_fans {
    display: flex;
    flex-direction: column;
    margin-left: 15px;
  }
  .username {

  }
  .school {
    color: #9393af;
    font-size: 14px;
    margin-top: 5px;
  }
  .dynamic_fans {
    margin-top: 5px;
    font-size: 14px;
    display: flex;
  }
  .fans {
    margin-left: 10px;
  }
  .dynamic>span {
    color: #9393af;
  }
  .fans>span {
    color: #9393af;
  }
</style>
