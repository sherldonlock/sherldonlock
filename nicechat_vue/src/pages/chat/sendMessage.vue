<template>
  <div class="m-text">
    <div class="textTools">
      <el-popover
        placement="top-start"
        width="500"
        trigger="click"
      >
        <emoji @selectEmoji="selectEmoji"></emoji>
        <i slot="reference" class="iconfont icon-cry emoIcon"></i>
      </el-popover>
    </div>
    <textarea v-model="content" @keydown="sendMessage"></textarea>
    <p class="tips">按下Enter发送内容/ Ctrl+Enter换行</p>
  </div>
</template>

<script>
    import Emoji from "../../components/emoji/emoji";
    export default {
        name: "sendMessage",
      components: {Emoji},
      data() {
          return {
            content:'',
          }
      },

      methods: {
        selectEmoji(emoji) {
            this.content += emoji;
        },

        sendMessage(e) {
          if(e.ctrlKey && e.keyCode === 13){
            this.content += "\n"; //换行
          }else if(e.keyCode === 13){
            if (this.content != '') {
              this.$emit('sendMessage', this.content);
              this.content = '';
              e.preventDefault();//禁止回车的默认换行
            } else {
              this.$message.error("消息不能为空");
              e.preventDefault();//禁止回车的默认换行
            }
          }
        },
      },

    }
</script>

<style scoped>
  .m-text {
    margin-top: 40px;
    height: 190px;
    border-top: solid 1px #ddd;
    display: flex;
    flex-direction: column;
  }
  .textTools {
    height: 30px;
    background-color: #ffffff;
    border-top:1px solid rgba(0,0,0,0.1);
  }
  .emoIcon {
    font-size: 25px;
    float: left;
    margin: 5px;
  }
  .emoIcon:hover {
    cursor: pointer;
  }
  textarea {
    font-size: 15px;
    padding: 10px;
    height: 100%;
    width: 98%;
    border: none;
    outline: none;
    resize: none;
  }
  .tips {
    font-size: 12px;
    height: 36px;
    padding-right: 18px;
    /*line-height: 36px;*/
    text-align: right;
    color: #a3a7ae;
    background-color: #ffffff;
  }
</style>
