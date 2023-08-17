// 组件功能：负责统一导出一个配置后的 axios
// 1.导入 axios 模块
import axios from 'axios'
// 2 .配置 axios 模块
// 2.1. 配置基地址
axios.defaults.baseURL = 'http://127.0.0.1:15010/api/'
// 2.2 设置请求拦截器
// 请求在到达服务器之前，先会调用use中的这个回调函数来添加请求头信息
axios.interceptors.request.use(config => {
    // 为请求头对象，添加token验证的Authorization字段
    let token = window.sessionStorage.getItem("token");
    // var str = token.substring(1,token.length-1);
    config.headers.Authorization = token;
    // config.headers.Authorization = 'ac'
    return config
})
// 
// 3.导出 axios 模块方法
export default axios


//封装axios
// import Vue from 'vue'
// import axios from 'axios';
// import router from '../router';
// import store from '../store/index';
// import element from 'element-ui';
// 
// let vueobj = new Vue();
// 
// const toSignin = function (msg) {
//   store.dispatch("clearUserInfo");
// 
//   var message = msg ? msg : "session过期，即将前往登录页面";
//   vueobj.$message.error({showClose: true, message: message, duration: 3000, onClose: function () {
    //   router.replace({path: '/signin', query: {redirect: router.currentRoute.fullPath}
    //   });
//   }});
// }
// /**
//  * 请求失败后的错误统一处理
//  * @param {Number} status 请求失败的状态码
//  */
// const errorHandle = (status, other) => {
//   状态码判断
//   switch (status) {
    // case 400:
    //   vueobj.$message.error({showClose: true, message: "请求参数有误！", duration: 3000});
    //   break;
    // 401: 未登录状态，跳转登录页
    // case 401:
    //   vueobj.$message.error({showClose: true, message: "session过期，即将前往登录页面！", duration: 3000});
    //   toSignin();
    //   break;
    // 403 token过期
    // 清除token并跳转登录页
    // case 403:
    //   vueobj.$message.error({showClose: true, message: "没有操作权限！", duration: 3000});
    //   break;
    // 404请求不存在
    // case 404:
    //   vueobj.$message.error({showClose: true, message: "请求不存在", duration: 3000});
    //   break;
    // case 500:
    //   vueobj.$message.error({showClose: true, message: "请求失败，服务器内部错误", duration: 3000});
    //   break;
    // case 504:
    //   vueobj.$message.error({showClose: true, message: "与服务器连接失败！", duration: 3000});
    //   break;
    // default:
    //   console.log(other);
//   }}
// 
// 
// 创建axios实例
// var instance = axios.create({ timeout: 1000 * 12});
// 
// 响应拦截器
// instance.interceptors.response.use(
//   response => {
    // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
    // 否则的话抛出错误
    // if (response.status === 200) {
    //   if(typeof response.data != 'undefined'){
        // return Promise.resolve(response);
    //   }else{
        // return Promise.reject(response);
    //   }
    // } else {
    //   return Promise.reject(response);
    // }
//   },
//   服务器状态码不是2开头的的情况
//   这里可以跟你们的后台开发人员协商好统一的错误状态码
//   然后根据返回的状态码进行一些操作，例如登录过期提示，错误提示等等
//   下面列举几个常见的操作，其他需求可自行扩展
//   error => {
// 
    // const { response } = error;
    // if (response) {
    //   请求已发出，但是不在2xx的范围
    //   errorHandle(response.status, response.data.message);
    //   return Promise.reject(response);
// 
    // } else {
    //   处理断网的情况
    //   eg:请求超时或断网时，更新state的network状态
    //   network状态在app.vue中控制着一个全局的断网提示组件的显示隐藏
    //   关于断网组件中的刷新重新获取数据，会在断网组件中说明
    //   store.commit('changeNetwork', false);
    //   toSignin();
    //   vueobj.$message.error({showClose: true, message: "与服务器连接失败！", duration: 3000});
// 
    //   var originalRequest = error.config;
    //   if(error.code == 'ECONNABORTED' && error.message.indexOf('timeout')!=-1 && !originalRequest._retry){
        // originalRequest._retry = true
        // return axios.request(originalRequest);
        // vueobj.$message.error({showClose: true, message: "请求超时，请稍后重试！", duration: 3000});
        // return Promise.reject(response);
    //   }else{
// 
    //   }
    // }
//   }
// );
// 
// export default instance;
// 
