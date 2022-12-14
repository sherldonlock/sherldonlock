<template>
  <div class="userInfo">
    <div class="userInfoForm">
      <el-form
        label-position="right"
        label-width="100px"
        ref="userInfoForm"
        :model="userInfoForm"
        :rules="userInfoFormRules"
        :hide-required-asterisk="true">
        <el-form-item label="更换头像" style="height: 150px">
          <el-upload
            name="avatar"
            ref="uploadFile"
            action="/api/consumer/user/uploadAvatar"
            list-type="picture-card"
            :limit="1"
            accept=".jpg,.jpeg,.png,.bmp,.gif"
            :on-exceed="limitImgList"
            :on-change="limitImg"
            :on-preview="handlePictureCardPreview"
            :on-success="uploadSuccess"
            :auto-upload="false"
            :multiple="false"
            :data="this.userInfoForm"
            :disabled="this.prohibitEdit"
            >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible" :modal="false">
            <img width="100%" :src="userInfoForm.avatarUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item
          label="昵称"
          size="small"
          style="width: 400px;"
          prop="userName"
          >
          <el-input
            v-model="userInfoForm.userName"
            :show-word-limit="true"
            :maxlength="13"
            :disabled="this.prohibitEdit"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" size="small" style="width: 400px" prop="sex">
          <el-radio-group v-model="userInfoForm.sex" :disabled="this.prohibitEdit">
            <el-radio label="1">男</el-radio>
            <el-radio label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="学校" size="small" style="width: 400px">
          <el-input
            style="width: 220px"
            v-model="userInfoForm.schoolName"
            :disabled="true"
          ></el-input>
        </el-form-item>
        <el-form-item label="感情状况" size="small" style="width: 400px">
          <el-select
            style="width: 220px"
            v-model="userInfoForm.emotion"
            filterable
            :disabled="this.prohibitEdit"
          >
            <el-option
              v-for="item in emotionList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日" size="small" style="width: 400px">
          <el-date-picker
            v-model="userInfoForm.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            :disabled="this.prohibitEdit"
            >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="简介" size="small" style="width: 400px">
          <el-input
            type="textarea"
            :rows="3"
            v-model="userInfoForm.introduction"
            :show-word-limit="true"
            :maxlength="30"
            resize="none"
            :disabled="this.prohibitEdit"
          ></el-input>
        </el-form-item>
        <el-form-item size="small" style="width: 400px">
          <el-button @click="editUserInfo">修改</el-button>
          <el-button @click="updateUserInfo">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import Title from "../../components/title";
    export default {
      name: "userInfo",
      components: {Title},
      data() {

        const userNameValidator = (rule, value, callback) => {
          let reg = /^\S+$/;
          if (value === '') {
            callback(new Error('请设置昵称'));
          } else if (!reg.test(value)){
            callback(new Error("昵称不能包含空格"));
          } else {
            this.checkUserName(value)
              .then(flag => {
                if (flag) {
                  callback(new Error("该昵称已被使用"));
                } else {
                  callback();
                }
              });
          }
        };

        return {
          dialogVisible: false,
          prohibitEdit: true,
          updateUserInfoFlag: false,
          emotionList: [
            {'id': '0', 'name': '不选'},
            {'id': '1', 'name': '单身'},
            {'id': '2', 'name': '求交往'},
            {'id': '3', 'name': '恋爱中'},
          ],
          userInfoForm: {
            id: '',
            avatarUrl: '',
            userName: '',
            sex: '',
            schoolName: '',
            emotion: '',
            birthday: '',
            introduction: '',
          },
          imageTypes: ['image/jpeg', 'image/gif', 'image/png', 'image/bmp'],

          userInfoFormRules: {
            userName: [{ required: true, validator: userNameValidator, trigger: 'blur' }],
          },
        }
      },

      computed: {
        userInfo() {
          return JSON.parse(sessionStorage.getItem("userInfo"));
        },
      },

      mounted() {
        this.assignUserInfoForm();
      },

      methods: {

        async checkUserName(userName) {
          let flag = false;
          let params = {
            userName: userName,
          };
          await this.$api
            .checkUserName(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                flag = res.data.data > 0;
              }
            });
          return flag;
        },

        assignUserInfoForm() {
          this.userInfoForm = this.userInfo;
        },

        handlePictureCardPreview(file) {
          this.userInfoForm.avatarUrl = file.url;
          this.dialogVisible = true;
        },

        limitImgList(){
          this.$message.error("最多只能添加一张图片哦！");
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

        uploadSuccess(response, file, fileList) {
          this.$refs.uploadFile.clearFiles();
        },

        editUserInfo() {
          this.prohibitEdit = false;
        },

        updateUserInfo() {
          if (this.prohibitEdit == false) {
            this.$refs['userInfoForm'].validate((valid) => {
              if (valid) {
                this.prohibitEdit = true;
                this.$refs.uploadFile.submit();
                this.$api
                  .updateUserInfo(this.userInfoForm)
                  .then((res) => {
                    if (res.data.resultCode === 0) {
                      let userInfo = {
                        id: res.data.data.currentUser.id,
                        accountNum: res.data.data.currentUser.accountNum,
                        userName: res.data.data.currentUser.userName,//用户姓名
                        avatarUrl: res.data.data.currentUser.avatarUrl,
                        sex: res.data.data.currentUser.sex,
                        emotion: res.data.data.currentUser.emotion,
                        birthday: res.data.data.currentUser.birthday,
                        introduction: res.data.data.currentUser.introduction,
                        createDate: res.data.data.currentUser.createDate,
                        schoolCode: res.data.data.currentUser.schoolCode,
                        schoolName: res.data.data.currentUser.schoolName,
                      };
                      this.$store.commit("setUserInfo", userInfo);
                      this.$message.success("保存成功");
                    } else {
                      this.$message.error("保存失败");
                    }
                  })
                  .catch((err) => {
                    this.$message.error(err);
                  });
              }
            });
          }
        }
      },
    }
</script>

<style scoped>
  .userInfo {
    /*margin-top: 55px;*/
    /*margin-left: 585px;*/
    /*width: 694px;*/
    /*height: 735px;*/
    margin-top: 10px;
    padding-bottom: 15px;
    text-align: start;
    /*box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);*/
    border-radius: 4px;
    background-color: #ffffff;
  }
  .userInfoForm {
    padding-top: 15px;
  }
  >>>.el-upload {
    float: left;
  }
  >>>.el-upload-list--picture-card {
    float: left;
  }
  >>>.el-textarea__inner {
    font-size: 13px;
  }
  >>> .el-dialog{
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
</style>
