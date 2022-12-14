<template>
  <div class="m-list">
    <ul style="list-style: none;">
      <li class="userList" v-for="(item, index) in userList" @click="selectUser(item, index)">
        <div class="user">
          <el-image class="avatar" fit="cover" :src="staticUrl + item.avatarUrl"></el-image>
          <div class="nameBox">
            <p class="name">{{ item.userName }}</p>
          </div>
          <el-badge v-if="item.unReadCount > 0 && user.id != item.id" :value="item.unReadCount"></el-badge>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
    export default {
      name: "chatList",
      data() {
        return {
          staticUrl: this.GLOBAL.staticUrl,
        }
      },

      props: {
        userList: {
          type: Array,
        },

        user: {
          type: Object,
        },
      },

      methods: {
        selectUser(item, index) {
          let userList = document.querySelectorAll('.userList');
          userList.forEach((x, index) => {
            userList[index].classList.remove('userList_active');
          });
          userList[index].classList.add('userList_active');
          this.$emit('selectUser', item);
        }
      },
    }
</script>

<style scoped>
  .m-list {
    padding: 12px 0;
  }
  .user {
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
  .nameBox {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: left;
  }
  .name {
    width: 160px;
    margin-left: 10px;
  }
  .userList {
    padding: 15px 12px;
  }
  .userList:hover {
    cursor: pointer;
    background-color: hsla(0,0%,100%,.1);
  }
  .userList_active {
    border-left:solid 4px #dda4ff;
  }
  >>> .el-badge__content {
    border: none;
  }
</style>
