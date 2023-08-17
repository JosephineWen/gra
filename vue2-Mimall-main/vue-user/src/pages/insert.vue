<template>
  <div class="login">
    <div class="container">
      <a href="/index">
        <!-- <img src="/imgs/login-logo.png" /> -->
        Welcome to our Online Store
      </a>
    </div>
    <div class="wrapper">
      <div class="container">
        <div class="login-form">
          <h3>
            <span class="checked">register</span>
          </h3>
          <div class="input">
            <input
              type="text"
              placeholder="Please enter your phone number"
              v-model="user.phone"
            />
          </div>
          <div class="input" v-if="!showError">
            <input
              type="text"
              placeholder="Please enter the verification code"
              v-model="user.phoneCode"
              style="width: 50%"
            />
            <button
              class="btn-get-code"
              @click="getCode"
              :disabled="countdown > 0"
            >
              {{ countdown > 0 ? countdown + "s" : "Send Code" }}
            </button>
          </div>
          <div class="input" v-if="showError">
            <input
              type="user.username"
              placeholder="Please enter your username"
              v-model="user.username"
            />
          </div>
          <div class="input" v-if="showError">
            <input
              type="password"
              placeholder="Please enter your password"
              v-model="user.password"
            />
          </div>
          <div class="input3" v-if="showError">
            <label style="height: 30px; margin-right: 20px">
              <input type="radio" v-model="user.code" value="1" />
              Regular user or ordinary user
            </label>
            <label>
              <input type="radio" v-model="user.code" value="2" />
              Merchant or business owner
            </label>
          </div>
          <div class="btn-box" v-if="!showError">
            <a href="javascript:;" class="btn" v-on:click="verification"
              >Verification</a
            >
          </div>
          <div class="btn-box" v-if="showError">
            <a href="javascript:;" class="btn" v-on:click="login">enroll</a>
          </div>
          <div class="tips">
            <div class="sms" @click="register">sign in</div>
          </div>
        </div>
      </div>
    </div>

    <div class="footer">
      <div class="copyright">email:6663414228@qq.com</div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { Message } from "element-ui";
import axios from "axios";
export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
        userId: "",
        phone: "",
        phoneCode: "",
        code: 1,
      },
      showError: false,
      res: {},
      countdown: 0,
    };
  },
  methods: {
    ...mapActions(["saveUsername"]),
    verification() {
      let data = {
        phone: this.user.phone,
        phoneCode: this.user.phoneCode,
      };
      axios
        .post(`http://127.0.0.1:15010/api/user/verifyCode`, data)
        .then((res) => {
          if (res.data.data.code == 200) {
            this.showError = true;
          } else {
            Message.warning(" verification code eror");
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    login() {
      if (this.user.phoneCode == "") {
        Message.warning("Please enter the verification code.");
      }
      if (
        this.user.phone == "" ||
        this.user.password == "" ||
        this.user.username == ""
      ) {
        Message.warning("Please enter complete information");
        return;
      }
      let data = this.user;
      axios
        .post(`http://127.0.0.1:15010/api/user/addTwo`,data)
        .then((res) => {
          Message.success("Registration successful")
          this.$router.push("/login");
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getCode() {
      // const regex = /^\+44 0\d{10}$/;
      const regex = /^0\d{10}$/;
      const isValidPhoneNumber = regex.test(this.user.phone);
      if (!isValidPhoneNumber) {
        Message.warning("The phone number format is incorrect");
        return;
      }
      axios
        .get(`http://127.0.0.1:15010/api/user/getCode?phone=${this.user.phone}`)
        .then((res) => {
          alert(res.data.data);
        })
        .catch((error) => {
          console.error(error);
        });

      this.countdown = 60;
      const timer = setInterval(() => {
        if (this.countdown > 0) {
          this.countdown--;
        } else {
          clearInterval(timer);
        }
      }, 1000);
    },
    register() {
      this.$router.push("/login");
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
.btn-get-code {
  background-color: rgb(73, 73, 244);
  color: white;
  border: 0px;
  height: 99%;
  width: 48%;
  margin-left: 2%;
}
.login {
  & > .container {
    height: 113px;
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
          .input3 {
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
