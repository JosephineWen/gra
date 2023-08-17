<template>
  <div class="order-list">
    <order-header title=" personal information">
      <template v-slot:order>
        <span> </span>
      </template>
    </order-header>

    <div class="wrapper">
      <div class="container">
        <div class="order-box">
          <el-form>
            <el-form-item label=" phone">
              <el-input v-model="user.phone" disabled></el-input>
            </el-form-item>
            <el-form-item label="Username">
              <el-input v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item label=" Age">
              <el-input type="text" v-model="user.age"></el-input>
            </el-form-item>
            <el-form-item label=" Password">
              <el-input type="password" v-model="user.password" show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveChanges">Save</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import OrderHeader from "@/components/OrderHeader";
import Loading from "@/components/Loading.vue";
import NoData from "@/components/NoData.vue";
import { Pagination, Button, Alert } from "element-ui";
import infiniteScroll from "vue-infinite-scroll";
import { PAY_STATUS } from "@/util";
import Modal from "@/components/Modal.vue";
import axios from "axios";
import { Message } from "element-ui";
export default {
  name: "order-list",
  components: {
    Modal,
    OrderHeader,
    Loading,
    NoData,
    [Pagination.name]: Pagination,
    [Button.name]: Button,
  },
  directives: { infiniteScroll },
  data() {
    return {
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
      oldPassowrd:'',
    };
  },
  mounted() {
    this.user = JSON.parse(window.sessionStorage.getItem("user"));
  },
  filters: {},
  methods: {
    saveChanges() {

      let data = this.user;
      axios
        .post("http://127.0.0.1:15010/api/user/update", data)
        .then((res) => {
          this.getUserId(this.user.id);
        })
        .catch((error) => {
          console.error(error);
        });
      console.log("Save changes");
    },
    getUserId(ids) {
      let data = {
        id: ids,
      };
      axios
        .post("http://127.0.0.1:15010/api/user/getById", data)
        .then((res) => {
          this.user = res.data.data
          window.sessionStorage.setItem("user", JSON.stringify(this.user));
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style lang="scss">
.order-list {
  .wrapper {
    background-color: $colorJ;
    padding-top: 33px;
    padding-bottom: 110px;
    .order-box {
      .order {
        @include border();
        background-color: $colorG;
        margin-bottom: 16px;
        box-shadow: 1px 1px 10px $colorJ;
        &:last-child {
          margin-bottom: 0;
        }
        .order-title {
          @include height(34px);
          background-color: #e9e9e9;
          padding: 0 43px;
          font-size: $fontJ;
          color: $colorC;
          .item-info {
            font-size: $fontK;
            span {
              margin: 0 9px;
            }
          }
          .money {
            font-size: $fontI;
            color: $colorB;
          }
        }
        .order-content {
          padding: 0 43px;
          .good-box {
            width: 85%;
            .good-list {
              display: flex;
              align-items: center;
              height: 70px;
              .good-img {
                width: 50px;
                img {
                  width: 100%;
                }
              }
              .good-name {
                font-size: $fontJ;
                color: $colorB;
              }
            }
          }
          .good-state {
            @include height(70px);
            font-size: $fontJ;
            color: $colorA;
            a {
              color: $colorA;
            }
          }
        }
      }
      .pagination {
        text-align: right;
      }

      .load-more,
      .scroll-more {
        text-align: center;
      }
    }
  }
  .one {
    color: green;
  }
  .two {
    color: blue;
  }
  .three {
    color: paleturquoise;
  }
  .four {
    color: orange;
  }
  .five {
    color: purple;
  }
}
</style>
