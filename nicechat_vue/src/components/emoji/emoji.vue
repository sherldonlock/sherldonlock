<template>
  <div v-prevent-event>
    <span class="showEmojiList" v-for="(item, index) in emojiList" :key="index" @click="handleEmoji(item)">
      {{item.text}}
    </span>
  </div>
</template>

<script>
    export default {
      name: "emoji",
      data() {
        return {
          emojiList:[],
        }
      },
      created(){

      },
      mounted() {
        this.showEmoji();
      },
      methods: {
        handleEmoji(item) {
          this.$emit('selectEmoji', item.text);
        },
        showEmoji() {
          const emojiList = [
            '😀', '😄', '😅', '🤣', '😂', '😉', '😊', '😍', '😘', '😜',
            '😑', '😕', '🧐', '😝', '😏', '😒', '🙄', '😔', '😴', '😷',
            '🤮', '🥵', '😎', '😮', '😰', '😭', '😱', '😩', '😡', '🤓',
            '🥳', '👴', '👵', '👶', '🤡','🙋','🙋‍♂','💀', '👽', '😺', '😹',
            '😻', '💩', '👌','👋', '🤚', '👍', '👎', '👏', '🤏','👊','🙏',
            '💪','🦵','🦶','👂','👃','🧠', '👀','👅','💋',
          ];
          this.emojiList = emojiList.map(emoji => ({text: emoji}));
        },
      },
    }
</script>

<style scoped>
  .showEmojiList {
    width: 100px;
    font-size: 20px;
  }
  .showEmojiList:hover {
    cursor: pointer;
  }
</style>
