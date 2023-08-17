<template>
  <div class="edit-profile">
    <h2>change information</h2>
    <div class="profile-picture">
      <!-- <el-image :src="require(`../../assets/imgs/ProjectImg/user/${avatar}`)" fit="cover" :class="{'square-avatar': avatar }" style="width: 150px;height: 150px;"></el-image><br>
      <el-upload class="avatar-upload" action="http://127.0.0.1:15010/api/user/upload2" :show-file-list="false" :data="{id:user.id}" :on-success="handleAvatarSuccess">
        <el-button size="small" type="primary">点击上传头像</el-button>
      </el-upload> -->
    </div>
    <el-form>
      <el-form-item label="Username">
        <el-input v-model="user.username"></el-input>
      </el-form-item>
      <!-- <el-form-item label="Contact">
        <el-input v-model="contact"></el-input>
      </el-form-item> -->
      <el-form-item label=" ShopName">
        <el-input v-model="user.shop"></el-input>
      </el-form-item>
      <el-form-item label=" Password">
        <el-input type="password" v-model="user.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveChanges">Save</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      avatar: "", // 头像URL
      username: "", // 用户名
      contact: "", // 联系方式
      password: "", // 密码,
      shopName: "",
      user: {
        id: "",
        username: "",
        password: "",
        name: "",
        age: "",
        phone: "",
        code: "",
        imgurl: null,
        money: null,
        status: 0,
        shop: null,
        phoneCode: null,
      },
    };
  },
  mounted() {
    this.user = JSON.parse(window.sessionStorage.getItem("user"));
    this.avatar = this.user.imgurl;
    if (this.avatar == null) {
      this.avatar = defult.jpg;
    }
  },
  methods: {
    handleAvatarSuccess(response) {
      // 处理上传头像成功的逻辑
      this.avatar = response.url;
    },
    saveChanges() {
      let data = this.user;
      axios
        .post("http://127.0.0.1:15010/api/user/update", data)
        .then((res) => {
          this.getUserId(this.user.id)
        })
        .catch((error) => {
          console.error(error);
        });
      console.log("保存修改");
    },
    getUserId(ids) {
      let data = {
        id:ids
      };
      axios
        .post("http://127.0.0.1:15010/api/user/getById", data)
        .then((res) => {
          this.user = res.data.data;
          window.sessionStorage.setItem('user', JSON.stringify(this.user));
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
.edit-profile {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f2f2f2;
}

.profile-picture {
  text-align: center;
  margin-bottom: 20px;
}

.square-avatar {
  width: 100px;
  height: 100px;
  border-radius: 5px;
}

.avatar-upload {
  display: inline-block;
}

.el-form-item__label {
  width: 100px;
}

.el-input {
  width: 100%;
}

.el-button {
  margin-top: 20px;
  border-radius: 3px;
  background-color: #007bff;
  color: #fff;
}
</style>
