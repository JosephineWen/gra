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
          <el-card
            v-for="comment in arrlist"
            :key="comment.id"
            class="comment-card"
          >
            <div slot="header" class="comment-header">
              <span>{{ comment.userName }}</span>
              <span>{{ comment.time }}</span>
            </div>
            <div class="comment-content">
              <p>{{ comment.text }}</p>
            </div>
           
            <div class="reply-content" v-if="comment.list!=undefined">
              <p>
                Reply person:{{ comment.list.userName }}
                &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Reply time:{{ comment.time }}</p><br>
              <p>{{comment.list.text }}</p>
            </div>
          </el-card>
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
      comments: [
        {
          id: 1,
          username: "用户1",
          date: "2021-01-01",
          text: "Here's a comment.",
          reply: "",
          replyUser: "",
          replyDate: "",
        },
        {
          id: 2,
          username: "用户2",
          date: "2021-01-02",
          text: "Here's another comment",
          reply: "",
          replyUser: "",
          replyDate: "",
        },
      ],
      replyId: null,
      replyForm: {
        replyText: "",
      },
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
      oldPassowrd: "",
      productIds: "",
      arrlist: [],
    };
  },
  mounted() {
    this.productIds = JSON.parse(window.sessionStorage.getItem("productId"));
    this.saveChanges();
  },
  filters: {},
  methods: {
    saveChanges() {
      let data = {
        productId: this.productIds,
      };
      axios
        .post("http://127.0.0.1:15010/api/comment/selectByProduct", data)
        .then((res) => {
          this.arrlist = res.data.data;
          console.log(this.arrlist);
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
          this.user = res.data.data;
          this.user.password = "";
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
.comment-card {
  margin-bottom: 10px;
}
.comment-header {
  display: flex;
  justify-content: space-between;
}
.comment-content {
  margin-top: 10px;
}
.reply-form {
  margin-top: 10px;
}
.reply-content {
  margin-top: 10px;
  background-color: #f5f5f5;
  padding: 10px;
}
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
