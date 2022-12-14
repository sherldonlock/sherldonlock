<template>
  <div class="login">
      <Title :title="title"></Title>
      <div class="loginInner">
        <el-form
          label-position="right"
          label-width="80px"
          ref="loginForm"
          :rules="loginRules"
          :model="loginForm"
          :hide-required-asterisk="true"
          @keyup.enter.native="LoginSubmit('loginForm')">
          <el-form-item label="账号" size="small" style="width: 380px;" prop="accountNum">
            <el-input v-model="loginForm.accountNum" placeholder="请输入您的账号" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码" size="small" style="width: 380px;margin-top: 25px;" prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入您的密码" clearable></el-input>
          </el-form-item>
          <el-form-item size="small" style="width: 380px;margin-top: 25px;">
            <el-button @click="LoginSubmit('loginForm')" >登录</el-button>
            <span class="toRegister" @click="toRegister">没有账号？立即注册</span>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>

<script>
    import Title from "./title";
    export default {
      name: "login",
      components: {Title},
      data() {
        return {
          title: '登录',
          commitFlag: false,
          loginForm: {
            accountNum: '',
            password: '',
          },
          loginRules: {
            accountNum: [{ required: true, message: '请输入账号', trigger: 'change' }],
            password: [{ required: true, message: '请输入密码', trigger: 'change' }],
          },
        }
      },

      mounted() {
        this.loginForm = {
          accountNum: '',
          password: '',
        };
      },

      methods: {
        LoginSubmit(formName) {
          if (this.commitFlag == false) {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                this.commitFlag = true;
                this.$api.login(this.loginForm).then((res) => {
                  if (res.data.resultCode === 0) {
                    let token = res.data.data.token;
                    let userInfo = {
                      id: res.data.data.user.id,
                      accountNum: res.data.data.user.accountNum,
                      userName: res.data.data.user.userName,//用户姓名
                      avatarUrl: res.data.data.user.avatarUrl,
                      sex: res.data.data.user.sex,
                      emotion: res.data.data.user.emotion,
                      birthday: res.data.data.user.birthday,
                      introduction: res.data.data.user.introduction,
                      createDate: res.data.data.user.createDate,
                      schoolCode: res.data.data.user.schoolCode,
                      schoolName: res.data.data.user.schoolName,
                    };
                    this.$store.commit("setToken", token);
                    this.$store.commit("setUserInfo", userInfo);
                    this.$store.commit("setHeaderIndex", 0);
                    this.$message.success(res.data.resultMessage);
                    setTimeout(this.toIndex, 2000);
                  } else {
                    this.commitFlag = false;
                    this.$message.error(res.data.resultMessage);
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

        toRegister() {
          this.$router.push('/register');
        },

        toIndex() {
          this.commitFlag = false;
          this.$router.push('/index');
          // window.location.reload();
        }
      },
    }
</script>

<style scoped>
  .login {
    width: 440px;
    height: 280px;
    position: absolute;
    margin: auto;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    /*margin-top: 200px;*/
    /*margin-left: 700px;*/
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    border-radius: 4px;
    /*background-color: #DD4A68;*/
  }
  .login >>> .title {
    width: 440px;
    border-radius: 4px 4px 0 0;
  }
  .loginInner {
    margin-top: 40px;
  }
  .toRegister {
    font-size: 12px;
    float: right;
  }
  .toRegister:hover {
    cursor: pointer;
    color: #525252;
  }
</style>
