<template>
  <div class="register">
      <Title :title="title"></Title>
      <div class="registerForm">
        <el-form
          label-position="right"
          label-width="100px"
          ref="registerForm"
          :rules="registerRules"
          :model="registerForm"
          >
          <!--<el-form-item label="个人头像" style="height: 150px">
            <el-upload
              name="avatar"
              ref="uploadFile"
              action="/api/consumer/system/uploadAvatar"
              list-type="picture-card"
              :limit="1"
              accept=".jpg,.png"
              :on-exceed="limitImgList"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :auto-upload="false"
              :before-upload="limitImg"
              :multiple="false">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible" :modal="false">
              <img width="100%" :src="avatarUrl" alt="">
            </el-dialog>
          </el-form-item>-->
          <el-form-item label="昵称" size="small" style="width: 400px;" prop="userName">
            <el-input
              placeholder="请输入您的昵称"
              v-model="registerForm.userName"
              :show-word-limit="true"
              :maxlength="13"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="账号" size="small" style="width: 400px" prop="accountNum">
            <el-input v-model="registerForm.accountNum" placeholder="请输入您的账号" clearable></el-input>
          </el-form-item>
          <el-form-item label="设置密码" size="small" style="width: 400px" prop="password">
            <el-input type="password" v-model="registerForm.password" placeholder="请输入您的密码" clearable></el-input>
          </el-form-item>
          <el-form-item label="确认密码" size="small" style="width: 400px" prop="confirmPassword">
            <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请再次输入您的密码" clearable :disabled="this.prohibitConfirmPassword"></el-input>
          </el-form-item>
          <el-form-item label="性别" size="small" style="width: 400px" prop="sex">
            <el-radio-group v-model="registerForm.sex">
              <el-radio label="1">男</el-radio>
              <el-radio label="2">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="学校" size="small" style="width: 400px" prop="schoolCode">
            <!--<el-autocomplete
              style="width: 220px"
              class="inline-input"
              v-model="registerForm.schoolName"
              :fetch-suggestions="querySearchSchool"
              value-key="name"
              placeholder="请选择学校名称"
              :trigger-on-focus="false"
              @select="handleSelectSchool"
              clearable
            ></el-autocomplete>-->
            <el-select
              style="width: 220px"
              v-model="registerForm.schoolCode"
              filterable
              clearable
              placeholder="请选择学校"
              @focus="loadUniversityData"
            >
              <el-option
                v-for="item in universityData"
                :key="item.code"
                :label="item.name"
                :value="item.code">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="感情状况" size="small" style="width: 400px">
            <el-select
              style="width: 220px"
              v-model="registerForm.emotion"
              filterable
            >
              <el-option
                v-for="item in emotionList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="生日" size="small" style="width: 400px" prop="birthday">
            <el-date-picker
              v-model="registerForm.birthday"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择出生日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="简介" size="small" style="width: 400px">
            <el-input
              type="textarea"
              :rows="3"
              placeholder="介绍一下自己吧~"
              v-model="registerForm.introduction"
              :show-word-limit="true"
              :maxlength="30"
              resize="none"
            ></el-input>
          </el-form-item>
          <el-form-item size="small" style="width: 400px">
            <el-button @click="toLogin">返回登录</el-button>
            <el-button @click="registerSubmit('registerForm')">立即注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>

<script>
    import Title from "./title";
    import universityData from "../utils/json/university";
    export default {
      name: "register",
      components: {Title},
      data() {
        const confirmPasswordValidator = (rule, value, callback) => {
          let reg = /^[a-zA-Z0-9+/*~!@#$%&().]+$/;
          if (value === '') {
            callback(new Error('请确认密码'));
          } else if(!reg.test(value)) {
            callback(new Error("密码只能由数字、字母和特殊字符+/*~!@#$%&().组成"));
          } else if (value !== this.registerForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };

        const passwordValidator = (rule, value, callback) => {
          let reg = /^[a-zA-Z0-9+/*~!@#$%&().]+$/;
          if (value === '') {
            callback(new Error('请设置密码'));
          } else if (!reg.test(value)){
            callback(new Error("密码只能由数字、字母和特殊字符+/*~!@#$%&().组成"));
          }
        };

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
                    callback(new Error("该昵称已被注册"));
                  } else {
                    callback();
                  }
                });
          }
        };

        const accountNumValidator = (rule, value, callback) => {
          let reg = /^[a-zA-Z0-9]+$/;
          if (value === '') {
            callback(new Error('请设置账号'));
          } else if (!reg.test(value)){
            callback(new Error("账号只能由数字、字母组成"));
          } else {
            this.checkAccountNum(value)
                .then(flag => {
                  if (flag) {
                    callback(new Error("该账号已被注册"));
                  } else {
                    callback();
                  }
                });
          }
        };

        return {
          title: '注册',
          dialogVisible: false,
          universityData: '',
          prohibitConfirmPassword: true,
          uploadSuccessFlag: true,
          commitFlag: false,
          avatarUrl: '',
          emotionList: [
            {'id': '0', 'name': '不选'},
            {'id': '1', 'name': '单身'},
            {'id': '2', 'name': '求交往'},
            {'id': '3', 'name': '恋爱中'},
          ],
          registerForm: {
            userName: '',
            accountNum: '',
            password: '',
            confirmPassword: '',
            sex: '1',
            emotion: '0',
            birthday: '',
            schoolCode: '',
            introduction: '',
          },

          registerRules: {
            userName: [{ required: true, validator: userNameValidator, trigger: 'blur' }],
            accountNum: [{ required: true, validator: accountNumValidator, trigger: 'blur' }],
            password: [{ required: true, validator: passwordValidator, trigger: 'blur' }],
            confirmPassword: [{ required: true, validator: confirmPasswordValidator, trigger: 'blur' }],
            // birthday: [{ required: true, type: 'date', message: '请选择出生日期', trigger: 'change' }],
            schoolCode: [{ required: true, message: '请选择学校', trigger: 'blur' }],
          },
        }
      },

      mounted() {
        this.registerForm = {
          userName: '',
          accountNum: '',
          password: '',
          confirmPassword: '',
          sex: '1',
          emotion: '0',
          birthday: '',
          schoolCode: '',
          introduction: '',
        };
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

        async checkAccountNum(account) {
          let flag = false;
          let params = {
            accountNum: account,
          };
          await this.$api
            .checkAccountNum(params)
            .then((res) => {
              if (res.data.resultCode === 0) {
                flag = res.data.data > 0;
              }
            });
          return flag;
        },

        loadUniversityData() {
          this.universityData = universityData;
        },

        registerSubmit(formName) {
          if (this.commitFlag == false) {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                this.commitFlag = true;
                this.$api.register(this.registerForm)
                  .then((res) => {
                    if (res.data.resultCode === 0) {
                      this.$message.success("注册成功！");
                      setTimeout(this.toLogin, 2000);
                    } else {
                      this.commitFlag = false;
                      this.$message.error("注册失败！");
                    }
                  }).catch((err) => {
                  this.commitFlag = false;
                  this.$message.error(err);
                  console.log(err);
                })
              } else {
                return false;
              }
            });
          }
        },

        toLogin() {
          this.commitFlag = false;
          this.$router.push('/login');
        },

        /*querySearchSchool(queryString, cb) {
          let universityData = this.universityData;
          let results = queryString ? universityData.filter(this.createFilterSchool(queryString)) : universityData;
          // 调用 callback 返回建议列表的数据
          cb(results);
        },

        createFilterSchool(queryString) {
          return (item) => {
            return (item.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
          };
        },

         handleSelectSchool(item) {
          this.registerForm.schoolCode = item.code;
          this.schoolName = item.name;
          console.log("handleSelectSchool:" + this.schoolName);
        },

         handleRemove(file, fileList) {
          this.registerForm.avatarUrl = '';
        },

        handlePictureCardPreview(file) {
          this.registerForm.avatarUrl = file.url;
          this.dialogVisible = true;
        },

        limitImgList(){
          this.$message.error("最多只能添加一张图片哦！");
        },

        limitImg(file) {
          const isLt2M = file.size / 1024 / 1024 < 2;
          if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
            this.uploadSuccessFlag = !this.uploadSuccessFlag;
          }
          return isLt2M;
        },
         */
      },

      watch: {
        'registerForm.password': {
          handler() {
            this.prohibitConfirmPassword = this.registerForm.password == '';
          },
          immediate: true,
        }
      },

      directives:{

      },
    }
</script>

<style scoped>
  .register {
    position: absolute;
    margin: auto;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    /*margin-top: 55px;*/
    /*margin-left: 585px;*/
    width: 594px;
    height: 610px;
    padding-bottom: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    border-radius: 4px;
    /*background-color: #DD4A68;*/
  }
  .register >>> .title {
    width: 594px;
    border-radius: 4px 4px 0 0;
  }
  .registerForm {
    margin-top: 20px;

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
</style>
