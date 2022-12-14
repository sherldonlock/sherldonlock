export default {
  setHeaderIndex(state, result) {
    state.headerIndex = result;
    sessionStorage.setItem('headerIndex', JSON.stringify(result));
  },

  setToken(state,result) {
    // 保存token
    // sessionStorage.setItem('token',JSON.stringify(result))
    // state.token = JSON.parse(sessionStorage.getItem('token'))
    sessionStorage.setItem('token',result);
    state.token = sessionStorage.getItem('token');
  },

  setLogOut(state,result) {
    state.token = '';
    state.userInfo = {};
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userInfo');
  },

  setUserInfo(state,result) {
    state.userInfo = result;
    try {
      sessionStorage.setItem('userInfo',JSON.stringify(result));
    }catch (e) {
      console.log(e);
      sessionStorage.setItem('userInfo',JSON.stringify(result));
    }
  },
}
