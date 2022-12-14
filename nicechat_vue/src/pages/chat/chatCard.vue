<template>
  <div class="m-card">
    <div class="header">
      <el-image class="avatar" fit="cover" :src="staticUrl + user.avatarUrl"></el-image>
      <div class="nameBox">
        <p class="name">{{ user.userName }}</p>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      name: "chatCard",
      data() {
        return {
          staticUrl: this.GLOBAL.staticUrl,
        }
      },
      props:{
        user: {
          type: Object,
        }
      },
    }
</script>

<style scoped>
  .m-card {
    padding: 25px 9pt;
    border-bottom: 1px solid #24272c;
  }
  .header {
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
  }
  .name {
    margin-left: 10px;
  }
</style>
