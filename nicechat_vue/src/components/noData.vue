<template>
  <div class="noData">
    <el-image fit="cover" v-if="this.showFlag" class="img" :src="staticUrl + '/images/systemImg/noData.jpg'"></el-image>
  </div>
</template>

<script>
    export default {
        name: "noData",
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
     margin-top: 10px;
     width: 694px;
     height: 469px;
     background-color: #ffffff;
     border-radius: 4px;
     /*display: flex;
     justify-content: center;
     align-items: center;*/
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
