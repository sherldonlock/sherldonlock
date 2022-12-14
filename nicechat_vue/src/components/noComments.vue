<template>
  <div class="noData">
    <el-image fit="cover" v-if="this.showFlag" class="img" :src="staticUrl + '/images/systemImg/noComments.jpg'"></el-image>
  </div>
</template>

<script>
  export default {
    name: "noComments",
    data () {
      return {
        staticUrl: this.GLOBAL.staticUrl,
        showFlag: false,
      }
    },

    methods: {
      changeFlag() {
        this.showFlag = true;
      }
    },

    mounted() {
      setTimeout(this.changeFlag, 400);
    },
  }
</script>

<style scoped>
  .noData {
    margin: auto;
    height: 25%;
    width: 25%;
  }
  .img {
    border-radius: 4px;
    /* 加入下面css */
    image-rendering: -moz-crisp-edges;
    image-rendering: -o-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: crisp-edges;
    -ms-interpolation-mode: nearest-neighbor;
  }
</style>
