<template>
  <div class="recommend">
    <div class="recommendHeader">
      <div class="recommendHeaderFont">
        你可能认识的校友
      </div>
      <div class="recommendHeaderIcon">
        <i class="iconfont icon-exchangerate recommendIcon" @click="refreshRecommendList"></i>
      </div>
    </div>
    <div class="recommendList">
      <div class="recommendInfo" v-for="item in recommendList" @click="selectRecommendItem(item)">
        <el-image fit="cover" class="avatar" :src="staticUrl + item.avatarUrl"></el-image>
        <div class="Item">
          <span class="recommendItemName">{{item.userName}}</span>
          <div class="dynamic_focus_fans">
            <div class="dynamic">
              <span>动态</span> {{ item.dynamicCount }}
            </div>
            <div class="focus">
              <span>关注</span> {{ item.focusCount }}
            </div>
            <div class="fans">
              <span>粉丝</span> {{ item.fansCount }}
            </div>
          </div>
        </div>
      </div>
      <no-school-friend v-if="recommendList.length === 0"></no-school-friend>
    </div>
  </div>
</template>

<script>
    import NoSchoolFriend from "./noSchoolFriend";
    export default {
      name: "recommend",
      components: {NoSchoolFriend},
      data() {
        return {
          staticUrl: this.GLOBAL.staticUrl,
        }
      },

      props: {
        recommendList: {
          type: Array,
        },
      },

      methods: {
        selectRecommendItem(item) {
          this.$emit('selectRecommendItem', item);
        },

        refreshRecommendList() {
          this.$emit('refreshRecommendList');
        },
      }
    }
</script>

<style scoped>
  .recommend {
    margin-top: 10px;
    padding-bottom: 10px;
    width: 280px;
    background-color: #ffffff;
    border-radius: 4px;
    display: flex;
    flex-direction: column;
  }
  .recommendHeader {
    padding: 10px;
  }
  .recommendHeaderFont {
    float: left;
    font-size: 15px;
  }
  .recommendHeaderIcon {
    /*margin-top: 3px;*/
    float: right;
  }
  .recommendIcon:hover{
    cursor: pointer;
  }
  .recommendInfo {
    display: flex;
    padding: 13px;
    text-align: start;
    transition: border-color .3s, background-color .3s, color .3s;
  }
  .recommendInfo:hover {
    cursor: pointer;
    background-color: #D3F8E2;
    /*color: #ffffff !important;*/
    border-radius: 4px;
  }
  .avatar {
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
  .Item {
    margin-left: 5px;
    display: flex;
    flex-direction: column;
  }
  .recommendItemName {
     font-size: 15px;
  }
  .recommendItemGrade {
    align-items: flex-start;
    font-size: 12px;
    color: #949494;
  }
  .dynamic_focus_fans {
    display: flex;
    font-size: 14px;
  }
  .focus {
    margin-left: 7px;
  }
  .fans {
    margin-left: 7px;
  }
  .dynamic>span {
    color: #9393af;
  }
  .focus>span {
    color: #9393af;
  }
  .fans>span {
    color: #9393af;
  }
</style>
