<template>
  <div class="sendBox">
    <el-input
      class="dynamicArea"
      type="textarea"
      :rows="4"
      placeholder="有什么新鲜事想分享给大家？"
      v-model="dynamicTextarea"
      :show-word-limit="true"
      :maxlength="this.dynamicTextareaLength"
      resize="none"
    >
    </el-input>
    <el-popover
      placement="bottom-start"
      width="500"
      trigger="click"
      >
      <emoji @selectEmoji="selectEmoji"></emoji>
      <i slot="reference" class="iconfont icon-smile emoIcon"></i>
    </el-popover>
    <i class="iconfont icon-pic picIcon" @click="picDialogVisible = !picDialogVisible"></i>
    <el-popover
      placement="bottom-start"
      width="330"
      trigger="click"
      v-model="topicVisible"
    >
      <div>
        <el-autocomplete
          class="inline-input topicInput"
          v-model="topInput"
          value-key="topicName"
          :fetch-suggestions="querySearchTopic"
          placeholder="请输入话题"
          @select="handleSelectTopic"
          :maxlength="13"
          :show-word-limit="true"
        ></el-autocomplete>
        <!--<el-input
          class="topicInput"
          placeholder="请输入话题"
          v-model="topInput"
          maxlength="10"
          :show-word-limit="true"
        >
        </el-input>-->
        <el-button class="topicBtn" @click="addTopic(topInput)" :disabled="topicStatus">确定</el-button>
      </div>
      <i slot="reference" class="iconfont icon-hashjinghao topicIcon" ></i>
    </el-popover>
    <!--<el-dialog
      title="添加图片"
      :visible.sync="picDialogVisible"
      width="30%"
      :modal="false"
      :destroy-on-close="true"
      >-->
    <el-button round style="margin-top: 8px;margin-right: 10px;float: right;" :disabled="sendStatus" @click="sendDynamic">发布</el-button>
    <div class="tags">
      <div v-if="tags.length > 0">
        <el-tag size="medium" v-for="(tag, index) in tags" :key="index" closable @close="handleClose(tag)">{{ "#" + tag.topicName + "#" }}</el-tag>
      </div>
    </div>
    <div v-show="picDialogVisible" style="width: 100%; height: 100%;margin-top: 10px">
      <el-upload
        name="images"
        ref="uploadImages"
        action="/api/consumer/index/uploadImages"
        list-type="picture-card"
        :limit="9"
        accept=".jpg,.jpeg,.png,.bmp,.gif"
        multiple
        :on-exceed="limitImgList"
        :on-change="limitImg"
        :on-preview="handlePictureCardPreview"
        :auto-upload="false"
        :data="this.dynamic"
        >
        <i class="el-icon-plus"></i>
      </el-upload>
      <el-dialog :visible.sync="dialogVisible" :modal="false">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
    </div>
      <!--<span slot="footer" class="dialog-footer">
        <el-button @click="picDialogVisible = false">取 消</el-button>
        <el-button @click="addImg">确 定</el-button>
      </span>
    </el-dialog>-->
  </div>
</template>

<script>
    import Emoji from "../../components/emoji/emoji";
    export default {
      name: "sendDynamic",
      components: {
        Emoji,
      },
      data() {
          return {
            dynamicTextareaLength: 140,
            dynamicTextarea: '',
            dialogImageUrl: '',
            dialogVisible: false,
            picDialogVisible: false,
            sendStatus: true,
            topicStatus: true,
            sendDynamicFlag: false,
            topInput:'',
            topicVisible:false,
            topicList:[],
            tags: [],
            test: [],
            dynamic: {
              dynamicId: '',
            },
            imageTypes: ['image/jpeg', 'image/gif', 'image/png', 'image/bmp'],
          }
      },

      props: {
        topics: {
          type: Array,
        },
      },

      computed: {
        userInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      mounted() {

      },

      methods: {
      selectEmoji(emoji) {
        if (this.dynamicTextarea.length < this.dynamicTextareaLength - 1)
          this.dynamicTextarea += emoji;
      },

      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },

      querySearchTopic(queryString, cb) {
        let topicList = this.topicList;
        let results = queryString ? topicList.filter(this.createFilterTopic(queryString)) : topicList;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },

      createFilterTopic(queryString) {
        return (item) => {
          return (item.topicName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },

      handleSelectTopic(item) {
        this.topInput = item.topicName;
      },

      addTopic(topInput) {
        if (this.tags.findIndex(x => x.topicName === topInput) >= 0) {
          this.$message.error("不能添加相同的话题哦");
        } else {
          this.tags.push({'topicName': topInput});
          this.topicVisible = false;
          this.topInput = '';
        }
      },

      handleClose(tag) {
        this.tags.splice(this.tags.indexOf(tag), 1);
      },

      limitImg(file, fileList) {
        const imgType = file.raw.type;
        const isLt3M = file.size / 1024 / 1024 < 3;
        if (this.imageTypes.indexOf(imgType) < 0) {
          this.$message.error('图片格式只能是jpg、jpeg、png、bmp、gif,且图片大小不能超过3MB!');
          fileList.forEach((item, index) => {
            if (item.raw.type === imgType) {
              fileList.splice(index, 1);
            }
          });
        } else if (!isLt3M) {
          this.$message.error('图片格式只能是jpg、jpeg、png、bmp、gif,且图片大小不能超过3MB!');
          fileList.forEach((item, index) => {
            if (item.size / 1024 / 1024 > 3) {
              fileList.splice(index, 1);
            }
          });
        }
      },

      limitImgList(){
        this.$message.error("最多只能添加九张图片哦！");
      },

      sendDynamicSuccess() {
        this.dynamicTextarea = '';
        this.tags = [];
        this.$refs.uploadImages.clearFiles();
        this.sendDynamicFlag = false;
        this.$message.success("发布成功");
        setTimeout(this.closePicDialog, 1000);
      },

      closePicDialog() {
        this.picDialogVisible = false;
        this.$emit('getDynamicList');
      },

      sendDynamic() {
        if (sessionStorage.getItem('token') != null) {
          if (this.sendDynamicFlag == false) {
            this.sendDynamicFlag = true;
            let params = {
              userId: this.userInfo.id,
              content: this.dynamicTextarea,
              tags: JSON.stringify(this.tags),
            };
            this.$api
              .sendDynamic(params)
              .then((res) => {
                if (res.data.resultCode === 0) {
                  this.dynamic.dynamicId = res.data.data.dynamicId;
                  this.$refs.uploadImages.submit();
                  setTimeout(this.sendDynamicSuccess, 1000);
                } else {
                  this.$message.success(res.data.resultMessage);
                }
              }).catch((err) => {
              this.$message.error(err);
              console.log(err);
            });
          }
        } else {
          // 没有token
          // alert('您还没有登录，请先登录')
          this.$confirm('您还未登录，请先登录', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push('login');
          }).catch((err) => {
            console.log(err);
          })
        }
      },

      /*uploadImages() {
        this.$refs.uploadImages.submit();
      },*/

     /* addImg() {
        // this.uploadImages();
        console.log(this.userInfo.id);
        console.log(this.images);
        this.picDialogVisible = false;
      },*/

      /*handleChanged(file) {
        this.images.push(file.raw)
      },*/

      /*uploadImages() {
        let formData = new FormData();
        this.images.forEach(item => formData.append('files', item));
        // formData.append('images', this.images);
        this.$api
          .uploadImages(formData)
          .then((res) => {
            if (res.data.resultCode === 0) {
              console.log("成功");
            } else {
              console.log("失败");
            }
          }).catch((err) => {
            console.log(err);
        })
      },*/

    },

    watch: {
      dynamicTextarea() {
        this.sendStatus = this.dynamicTextarea == '';
      },

      topInput() {
        this.topicStatus = this.topInput == '';
      },

      topics: function(newVal, oldVal) {
        this.topicList = newVal;
      },
    }
  }
</script>

<style scoped>
  .dynamicArea {
    margin-top: 10px;
    /*margin: 10px 7px 0 7px;*/
    width: 680px;
  }

  .topicInput {
    width: 250px;
  }
  >>> .el-dialog {
    /*width: 18%;*/
  }
  >>> .el-upload-list__item {
    /* 加入下面css */
    image-rendering: -moz-crisp-edges;
    image-rendering: -o-crisp-edges;
    image-rendering: -webkit-optimize-contrast;
    image-rendering: crisp-edges;
    -ms-interpolation-mode: nearest-neighbor;
  }
  .sendBox {
    border-radius: 4px;
    width: 100%;
    /*text-align: center;*/
    padding-bottom: 35px;
    /*height:165px;*/
    background-color: #ffffff;
    /*box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);*/
  }

  .emoIcon {
    margin-top: 5px;
    font-size: 30px;
    float: left;
    /*display: inline-block;*/
    margin-left: 10px;
    position: relative;
  }
  .emoIcon:hover {
    cursor: pointer;
  }
  .picIcon {
    /*width: 0;
    height: 0;*/
    /*line-height: 1px;*/
    float: left;
    /*display: inline-block;*/
    margin-top: 5px;
    font-size: 30px;
  }
  .picIcon:hover {
    cursor: pointer;
  }
  .topicIcon {
    margin-top: 4px;
    font-size: 27px;
    float: left;
    /*display: inline-block;*/
    /*margin-left: 31px;*/
  }
  .topicIcon:hover {
    cursor: pointer;
  }
  .topicBtn {
    float: right;
  }
  .tags {
    margin-top: 7px;
    display: inline-block;
    text-align: start;
    width: 70%;
    /*margin-bottom: 10px;*/
  }
  >>>.el-upload--picture-card {
    /*margin-top: 30px;*/
  }
</style>
