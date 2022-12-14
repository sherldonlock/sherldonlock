<template>
  <div class="aside">
    <el-menu
      :default-active="'1' + num"
    >
      <el-menu-item
        v-for="(item, num) in menuList"
        :index="'1' + num"
        :key="num"
        @click="selectMenuItem(item, num)"
        >
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
      </el-menu-item>
    </el-menu>
    <div v-if="hotTopics !== undefined && hotTopics.length > 0" class="hotTopics">
      <div class="hotTopicFont">
        <i class="iconfont icon-hot"></i>
        热门话题
      </div>
      <ul class="group">
        <li class="groupLi" v-for="(item, index) in hotTopics" @click="getHotTopicItem(item, index)">
          {{ '#' + item.topicName + '#'}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
    export default {
      name: "Aside",
      data() {
        return {
          num: '0',
          index: 0,
        }
      },

      props: {
        menuList: {
          type: Array,
        },

        hotTopics: {
          type: Array,
        },
      },

      methods: {
        getHotTopicItem(item, index) {
          let menu = document.querySelectorAll('.aside > .el-menu > .el-menu-item.is-active');
          if (menu.length > 0) {
            menu[0].classList.remove("is-active");
          }
          let groupLis = document.querySelectorAll('.groupLi');
          groupLis[index].classList.add("groupLi-checked");
          if (index !== this.index) {
            groupLis[this.index].classList.remove("groupLi-checked");
          }
          this.index = index;
          this.$emit('getHotTopicItem', item);
        },

        selectMenuItem(item, num) {
          let groupLis = document.querySelectorAll('.groupLi');
          if (groupLis.length > 0) {
            groupLis[this.index].classList.remove("groupLi-checked");
          }
          let menu = document.querySelectorAll('.aside > .el-menu > .el-menu-item');
          menu[num].classList.add("is-active");
          this.$emit('selectMenuItem', item);
        }
      },

      watch: {

      },
    }
</script>

<style scoped>
  >>>.el-menu {
    border-radius: 4px 4px 0 0;
    /*box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);*/
  }
  .hotTopics {
    /*padding-top: 5px;*/
    border-top:1px solid rgba(0,0,0,0.1);
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
    background-color: #ffffff;
  }
  .hotTopicFont {
    padding: 20px;
    font-size: 16px;
    color: #525252;
  }
  .hotTopicFont>i{
   font-size: 25px;
  }
  .group {
    list-style: none;
  }
  .groupLi {
    color: #949494;
    padding: 15px;
    font-size: 15px;
    transition: border-color .3s, background-color .3s, color .3s;
    white-space:nowrap;
    overflow:hidden;
    text-overflow:ellipsis;
  }
  .groupLi-checked {
    font-weight: bold;
    color: #ffffff;
    background-color: #F694C1;
    border-radius: 4px;
  }
  .groupLi:hover{
    background-color: #F694C1;
    color: #ffffff !important;
    border-radius: 4px;
    cursor: pointer;
  }
</style>
