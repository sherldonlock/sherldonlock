import http from './http'
import axios from "axios";

export default {
  register(params) {
    return http.post('/api/consumer/system/register', params);
  },

  checkUserName(params) {
    return http.post('/api/consumer/system/checkUserName', params);
  },

  checkAccountNum(params) {
    return http.post('/api/consumer/system/checkAccountNum', params);
  },

  login(params) {
    return http.post('/api/consumer/system/login', params);
  },

  logout(params) {
    return http.post('/api/consumer/system/logout', params);
  },

  sendDynamic(params) {
    return http.post('/api/consumer/index/sendDynamic', params);
  },

  getDynamicList(params) {
    return http.post('/api/consumer/index/getDynamicList', params);
  },

  getTopicList() {
    return http.post('/api/consumer/index/getTopicList');
  },

  getUserNameList() {
    return http.post('/api/consumer/index/getUserNameList');
  },

  getHotTopicsTopTen() {
    return http.post('/api/consumer/index/getHotTopicsTopTen');
  },

  getRecommendList(params) {
    return http.post('/api/consumer/index/getRecommendList', params);
  },

  getUserInfo(params) {
    return http.post('/api/consumer/user/getUserInfo', params);
  },

  getDynamicListByUserId(params) {
    return http.post('/api/consumer/user/getDynamicListByUserId', params);
  },

  sendComment(params) {
    return http.post('/api/consumer/index/sendComment', params);
  },

  getCommentList(params) {
    return http.post('/api/consumer/index/getCommentList', params);
  },

  setCollectDynamic(params) {
    return http.post('/api/consumer/index/setCollectDynamic', params);
  },

  cancelCollectDynamic(params) {
    return http.post('/api/consumer/index/cancelCollectDynamic', params);
  },

  setLikeDynamic(params) {
    return http.post('/api/consumer/index/setLikeDynamic', params);
  },

  cancelLikeDynamic(params) {
    return http.post('/api/consumer/index/cancelLikeDynamic', params);
  },

  getMyCollectIdList(params) {
    return http.post('/api/consumer/index/getMyCollectIdList', params);
  },

  getMyLikeIdList(params) {
    return http.post('/api/consumer/index/getMyLikeIdList', params);
  },

  getFocusList(params) {
    return http.post('/api/consumer/user/getFocusList', params);
  },

  getFansList(params) {
    return http.post('/api/consumer/user/getFansList', params);
  },

  getMyLikeList(params) {
    return http.post('/api/consumer/user/getMyLikeList', params);
  },

  getMyCollectList(params) {
    return http.post('/api/consumer/user/getMyCollectList', params);
  },

  getMyFocusIdList(params) {
    return http.post('/api/consumer/user/getMyFocusIdList', params);
  },

  cancelFocus(params) {
    return http.post('/api/consumer/user/cancelFocus', params);
  },

  Focus(params) {
    return http.post('/api/consumer/user/Focus', params);
  },

  updateUserInfo(params) {
    return http.post('/api/consumer/user/updateUserInfo', params);
  },

  deleteDynamic(params) {
    return http.post('/api/consumer/index/deleteDynamic', params);
  },

  chatWith(params) {
    return http.post('/api/consumer/chat/chatWith', params);
  },

  getUserList(params) {
    return http.post('/api/consumer/chat/getUserList', params);
  },

  getDialog(params) {
    return http.post('/api/consumer/chat/getDialog', params);
  },

  updateReadFlag(params) {
    return http.post('/api/consumer/chat/updateReadFlag', params);
  },

  /*uploadImages(params) {
    return axios({
      url: '/api/consumer/index/uploadImages',
      method: 'POST',
      data: params,
      /!*headers: {
        'Content-Type': ' multipart/form-data; boundary=----WebKitFormBoundaryj6nHIJj0NuN1eho6'
      }*!/
    })
    // return http.post('/api/consumer/index/uploadImages', params);
  },*/
}
