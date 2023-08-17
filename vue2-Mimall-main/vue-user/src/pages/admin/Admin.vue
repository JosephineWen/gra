<template>
  <div id="app">
    <div class="navbar">
      <h1>Administrator</h1>
      <span style="margin-right: 20px">
        <span style="margin-left: 10px" @click="logout">logout</span>
      </span>
    </div>
    <div class="sidebar">
      <el-menu :default-active="$route.path" mode="vertical">
        <el-menu-item index="UserManagement"  @click="goToSettings('UserManagement')"> User</el-menu-item>
        <el-menu-item index="SellerManagement"  @click="goToSettings('SellerManagement')">Seller</el-menu-item>
        <el-menu-item index="ProductManagement"  @click="goToSettings('ProductManagement')">Product</el-menu-item>
        <el-menu-item index="CategoryManagement" @click="goToSettings('CategoryManagement')">Category</el-menu-item>
        <el-menu-item index="ExpressManagement"  @click="goToSettings('ExpressManagement')">Express</el-menu-item>
        <el-menu-item index="CommentManagement"  @click="goToSettings('CommentManagement')">Comment</el-menu-item>
        <el-menu-item index="ReportManagement" @click="goToSettings('ReportManagement')">Report</el-menu-item>
      </el-menu>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { Message } from "element-ui";
export default {
  methods:{
    goToSettings(file) {
      this.$router.push('/' + file);
    },
    logout() {
      Message.success("Logout successful");
      window.sessionStorage.removeItem("user");
      this.$router.push("/login");
      window.onload();
    },
  }
};
</script>

<style>
/* 导航栏样式 */
.navbar {
  background-color: white;
  color: rgb(9, 9, 9);
  padding: 20px;
  text-align: left;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;

  /* align-content: center; */
}

.navbar h1 {
  margin: 0;
  font-size: 24px;
  font-weight: bold;
}
.sidebar {
  /* 其他样式... */

}
/* 其他样式 */
/* 侧边栏样式 */
.sidebar {
  width: 150px;
  background-color: white;
  color: rgb(16, 16, 16);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
  /* Add box shadow for depth */
  /* background-color: lightblue; */
  font-size: 12px;
  padding: 20px;
  margin-top:  1.2%;
  margin-left: 20px;
    box-shadow: none; /* 移除阴影效果 */
  /* min-height: 100vh; */
}

/* 内容样式 */
.content {
  width: 50%;
  flex: 1;
  position: absolute;
  top: 10.3%;
  left: 14.5%;
  height: 78%;
  overflow: auto; /* 或者使用 overflow: scroll; */
  padding: 10px;
  padding-bottom: 2%;
 
  width: 80.5%;

  background-color:white; /* Add background color for separation */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
  /* Add box shadow for depth */
  /* height: auto; */
  
}

/* 菜单项样式 */
.sidebar .el-menu-item .menu-item-text {
  line-height: 50px;
  background-color: rebeccapurple;
}

.sidebar .el-menu-item {
  /* display: block; */
  margin-bottom: 10px;
  height: 50px;
  display: flex;
  align-items: center;
  overflow: hidden;
  border: 0px;
}

.sidebar /deep/ .el-menu-item {
  height: 100px;
  border: 0px;
}

/* 应用容器样式 */
#app {
  display: flex;
  height: 100vh;
  flex-direction: column;
  background-color: #f5f5f5;
}

</style>