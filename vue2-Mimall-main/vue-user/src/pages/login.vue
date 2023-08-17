<template>
  <div class="login">
    <div class="container">
      <a href="/index">
        Welcome to our Online Store
      </a>
    </div>  
    <div class="wrapper">
      <div class="container">
        <div class="login-form">
          <h3>
            <span class="checked">Sign in</span>
          
          </h3>
          <div class="input">
            <input type="text" placeholder="Please enter your phone number" v-model="loginUser.phone" />
          </div>
          <div class="input">
            <input
              type="password"
              placeholder="Please enter your password"
              v-model="loginUser.password"
            />
          </div>
          <div class="btn-box">
            <a href="javascript:;" class="btn" v-on:click="login">Sign</a>
          </div>
          <div class="tips">
            <div class="sms" @click="register">register</div>
          </div>
        </div>
      </div>
    </div>

    <div class="footer">
    
      <div class="copyright">
       email:1306075230@qq.com
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from "axios";
import { Message } from "element-ui";
export default {
  data() {
    return {
      user:{
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
        phoneCode: null
    },
    loginUser:{
      password: "",
      phone: ""
    },
      res: {},
    };
  },
  methods: {
    ...mapActions(["saveUsername"]),
    login() {
      if(this.loginUser.phone=="" || this.loginUser.password==""){
        Message.warning('Please enter complete information');
        return;
      }
      let data = this.loginUser;
      axios
        .post("http://127.0.0.1:15010/api/user/login",data)
        .then((res) => {
          this.user = res.data.data;
          if(res.data.code==500){
            Message.warning(res.data.data);
            return;
          }
          
          window.sessionStorage.setItem('user', JSON.stringify(this.user));
          console.log(  JSON.parse(window.sessionStorage.getItem('user')) )
          if(this.user.code==1){
            this.$router.push('/index');
          }
          if(this.user.code==2){
            this.$router.push('/ProductInfo');
          }
          if(this.user.code==3){
            this.$router.push('/UserManagement');
          }
         
        })
        .catch((error) => {
          Message.warning('Please confirm if you have a registered account or check your password');
        });
    },
    register() {
      this.$router.push('/insert');
      // this.axios
      //   .post("/user/register", {
      //     username: "admin1",
      //     password: "admin1",
      //     emial: "admin1@163.com",
      //   })
      //   .then((res) => {
      //     Message.success(res || "Successful registration");
      //   });
    },
  },
};
</script>

<style lang="scss">
.login {
  & > .container {
    height: 113px;
    font-size: 30px;
    display: flex;
    justify-content: left;
    align-items: center;
    img {
      width: auto;
      height: 100%;
    }
  }
  .wrapper {
    background: url("/public/imgs/login-bg.jpg") no-repeat center;
    .container {
      height: 576px;
      .login-form {
        padding-left: 31px;
        padding-right: 31px;
        width: 410px;
        height: 510px;
        background-color: $colorG;
        position: absolute;
        bottom: 29px;
        right: 0;
        h3 {
          line-height: 23px;
          font-size: $fontE;
          text-align: center;
          margin: 40px auto 49px;
          .checked {
            color: $colorA;
          }
          .sep-line {
            margin: 0 32px;
          }
        }
        .input {
          width: 100%;
          display: inline-block;
          height: 50px;
          border: 1px solid $colorH;
          margin-bottom: 20px;
          input {
            width: 100%;
            height: 100%;
            border: none;
            padding: 18px;
          }
        }
        .input2 {
          width: 100%;
          display: inline-block;
          height: 50px;
          border: 1px solid $colorH;
          margin-bottom: 20px;
          input {
            width: 50%;
            height: 100%;
            border: none;
            padding: 18px;
          }
        }
        .btn {
          width: 100%;
          height: 50px;
          line-height: 50px;
          margin-top: 10px;
          font-size: $fontI;
        }
        .tips {
          margin-top: 14px;
          display: flex;
          justify-content: space-between;
          font-size: $fontJ;
          cursor: pointer;
          .sms {
            color: $colorA;
          }
          .reg {
            color: $colorD;
            span {
              margin: 0 7px;
            }
          }
        }
      }
    }
  }
  .footer {
    height: 100px;
    padding-top: 60px;
    color: $colorD;
    font-size: $fontI;
    text-align: center;
    .footer-link {
      a {
        color: $colorD;
        display: inline-block;
        position: relative;
        margin-right: 8px;
        padding-right: 8px;
        &:before {
          position: absolute;
          right: 0;
          top: 7px;
          width: 1px;
          height: 10px;
          content: "";
          border-right: 1px solid $colorF;
        }

        &:last-child:before {
          border: none;
        }
        span {
          margin: 0 10px;
        }
      }
    }
    .copyright {
      margin-top: 13px;
    }
  }
}
</style>
