<template>
  <div class="searchBox">
    <div class="searchBoxInner">
      <!--<el-select v-model="schoolCode" filterable clearable placeholder="请选择学校"
                 @focus="loadUniversityData" :disabled="showSchoolFlag">
        <el-option
          v-for="item in universityData"
          :key="item.code"
          :label="item.name"
          :value="item.code">
        </el-option>
      </el-select>-->
      <el-autocomplete
        class="inline-input"
        v-model="schoolName"
        :fetch-suggestions="querySearchSchool"
        value-key="name"
        placeholder="请输入学校"
        :trigger-on-focus="false"
        clearable
        :disabled="showSchoolFlag"
      ></el-autocomplete>
      <el-select v-model="topicName" filterable clearable placeholder="请选择话题">
        <el-option
          v-for="(item, index) in topicList"
          :key="index"
          :label="item.topicName"
          :value="item.topicName">
        </el-option>
      </el-select>
      <el-autocomplete
        class="inline-input"
        v-model="userName"
        :fetch-suggestions="querySearchUsername"
        value-key="userName"
        placeholder="请输入用户名"
        :trigger-on-focus="false"
        @select="handleSelectUsername"
        clearable
      ></el-autocomplete>
      <el-button class="searchBtn" @click="search">搜索</el-button>
    </div>
  </div>
</template>

<script>
  import universityData from '../utils/json/university.json'
    export default {
      name: "searchBox",
      data() {
        return {
          topicName:'',
          userName:'',
          schoolCode:'',
          schoolName:'',
          universityData: universityData,
          topicList: [],
          userNameList:[],
        }
      },

      props: {
        showSchoolFlag: {
          type: Boolean,
        },

        topics: {
          type: Array,
        },
      },

      mounted() {
        this.getUserNameList();
      },

      methods: {

        getUserNameList() {
          this.$api
            .getUserNameList()
            .then((res) => {
              if (res.data.resultCode === 0) {
                this.userNameList = res.data.data;
              }
            }).catch((err) => {
              this.$message.error(err);
              console.log(err)
          })
        },

        querySearchUsername(queryString, cb) {
          let userNameList = this.userNameList;
          let results = queryString ? userNameList.filter(this.createFilterUsername(queryString)) : userNameList;
          // 调用 callback 返回建议列表的数据
          cb(results);
        },

        createFilterUsername(queryString) {
          return (item) => {
            return (item.userName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
          };
        },

        handleSelectUsername(item) {
          this.userName = item.userName;
        },

        querySearchSchool(queryString, cb) {
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

        search() {
          let params = {
            schoolName: this.schoolName,
            topicName: this.topicName,
            userName: this.userName,
            };
          this.$emit('clickSearch', params);
        },

        loadUniversityData() {
          this.universityData = universityData;
        },
      },

      watch: {
        topics: function(newVal, oldVal) {
          this.topicList = newVal;
        },
      }
    }
</script>

<style scoped>
  .searchBox {
    margin-top: 10px;
    border-radius: 4px;
    width: 694px;
    height:62px;
    background-color: #ffffff;
    /*box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);*/
  }
  .searchBoxInner {
    float: left;
    margin-left: 3px;
  }
  .searchBtn {
    margin-left: 10px;
  }
  >>>.el-input {
    margin-top: 10px;
    margin-left: 10px;
    width: 185px;
  }
</style>
