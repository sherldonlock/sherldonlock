<template>
  <div class="title">
    <span>{{ title }}</span>
  </div>
</template>

<script>
    export default {
      name: "Title",
      props: {
          title: {
            type: String,
          }
      },

      mounted() {
        window.addEventListener('scroll',this.handleScroll); // 监听滚动事件，然后用handleScroll这个方法进行相应的处理
      },

      methods: {
        handleScroll() {
          let title = document.querySelector('.title');
          if (title != null) {
            if (window.pageYOffset >= 20) {
              title.classList.add('title-sticky');
            } else {
              title.classList.remove('title-sticky');
            }
          }
        }
      },
    }
</script>

<style scoped>
  .title {
    width: 694px;
    /*width: 100%;*/
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    border-top:1px solid rgba(0,0,0,0.1);
    border-bottom:1px solid rgba(0,0,0,0.1);
    border-radius: 4px;
    background-color: #ffffff;
  }
  .title-sticky {
    z-index: 100;
    position: fixed;
    top: 56px;
  }
</style>
