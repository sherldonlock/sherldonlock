import axios from 'axios'
import qs from 'qs'

axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.transformRequest = data => qs.stringify(data);
// 全局设置 axios 发送请求带上cookie
// axios.defaults.withCredentials = true;
axios.interceptors.request.use(
  config => {
    // 携带上token
    let token = sessionStorage.getItem('token');
    token && (config.headers.token = token);
    return config;
  },
  err => {
    // 请求拦截失败时返回err信息
    console.error("发送请求失败的操作")
    return Promise.reject(err);
  }
);
export default axios;
