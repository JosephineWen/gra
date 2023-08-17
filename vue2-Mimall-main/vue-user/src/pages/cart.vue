<template>
  <div class="cart">
    <order-header title="MyCar">
      <template v-slot:order>
        <span>Tips: Whether the product is successfully purchased is subject to the final order, please settle as soon as possible</span>
      </template>
    </order-header>
    <div class="wrapper">
      <div class="container">
        <div class="cart-box">
          <ul class="cart-item-head flex">
            <li class="col-3">
              img
              <!-- <span
                class="checkbox"
                :class="{ checked: allChecked }"
                @click="toggleAll"
              > -->
              <!-- </span>
              全选 -->
            </li>
            
            <li class="col-3">name</li>
            <li class="col-1">price</li>
            <li class="col-2">number</li>
            <li class="col-1">count</li>
            <li class="col-1">操作</li>
          </ul>

          <ul class="cart-item-list">
            <li
              class="cart-item flex"
              v-for="item in list.carList"
              :key="item.productId"
            >
              <div class="col-1">
                <!-- <span
                  class="checkbox"
                  :class="{ checked: item.productSelected }"
                  @click="updateCart(item)"
                ></span> -->
              </div>
              <div class="item-name col-3">
                <img v-lazy=" require(`../assets/imgs/ProjectImg/${item.imgurl}`)" />
                  <!-- <img src="../assets/imgs/ProjectImg/0094a09d-ed2d-4b8b-aa28-3b22f71236fe.webp" /> -->
                <span>{{ item.sname}}</span>
              </div>
              <div class="item-price col-1">{{ item.price }}</div>
              <div class="item-num col-2">
                <div class="num-box">
                  <a href="javascript:;" @click="updateCart(item, '-')">-</a>
                  <span>{{ item.count }}</span>
                  <a href="javascript:;" @click="updateCart(item, '+')">+</a>
                </div>
              </div>
              <div class="item-total col-1">£{{ item.count * item.price }}</div>
              <div class="item-del col-1" @click="delProduct(item.id)"></div>
            </li>
          </ul>

        </div>
        <div class="order-wrap cleafix">
          <div class="cart-tip fl">
            <a href="/#/index">Continue shopping</a>
            total<span>{{ list.count }}</span> items
          </div>
          <div class="total fr">
            Total or sum：<span>£{{ list.money }} </span>
            <a href="javascript:;" class="btn" @click="order">Proceed to checkout</a>
          </div>
        </div>
      </div>
    </div>
    <service-bar></service-bar>
    <nav-footer></nav-footer>
  </div>
</template>

<script>
import OrderHeader from "@/components/OrderHeader";
import NavFooter from "@/components/NavFooter";
import ServiceBar from "@/components/ServiceBar.vue";
import { Message } from "element-ui";
import axios from "axios";
export default {
  name: "cart",
  components: {
    OrderHeader,
    NavFooter,
    ServiceBar,
  },
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
      list: [],
      checkedNum: 0,
      cartTotalPrice: 0,
      allChecked: false,
    };
  },
  mounted() {
    this.user =JSON.parse(window.sessionStorage.getItem('user'));
    this.getCartList();
  },
  methods: {
    // 获取购物车列表
    getCartList() {
      let data = {
        userid:this.user.id
      }
      axios
        .post("http://127.0.0.1:15010/api/car/list",data)
        .then((res) => {
          this.list = res.data.data;
          console.log(this.list);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    toggleAll() {
      let url = this.allChecked ? "/carts/unSelectAll" : "carts/selectAll";
      this.axios.put(url).then((res) => {
        this.renderData(res);
      });
    },
    order() {
      this.$router.push("/address");
      // let isCheck = this.list.every((item) => !item.productSelected);
      // if (isCheck) {
      //   Message.error("请选择一件商品");
      // } else {
      //   this.$router.push("/order/confirm");
      // }
    },
  
    updateCart(item, type) {
      let { count,id,number } = item;
      if (type === "-") {
        if (count == 1) {
          Message.error("Keep at least one item of the product");
          return;
        }
        --count;
        this.updateCount(id,count);
      } else if (type === "+") {
        if (count > number) {
          Message.error("The purchase quantity cannot exceed the stock quantity");
          return;
        }
        ++count;
        this.updateCount(id,count);
      } 
    },
    updateCount(ids,counts) {
     let data = {
      id:ids,
      count:counts
     }
     axios
        .post("http://127.0.0.1:15010/api/car/updateByCount",data)
        .then((res) => {
          this.getCartList();
        })
        .catch((error) => {
          console.error(error);
        });

    },
    delProduct(item) {
     let data = {
      id:item
     }
     axios
        .post("http://127.0.0.1:15010/api/car/deleteById",data)
        .then((res) => {
          this.getCartList();
        })
        .catch((error) => {
          console.error(error);
        });

    },
    renderData(res) {
      const {
        cartProductVoList = [],
        selectedAll = false,
        cartTotalPrice = 0,
      } = res || {};
      this.list = cartProductVoList;
      this.allChecked = selectedAll;
      this.cartTotalPrice = cartTotalPrice;
      this.checkedNum = this.list.filter((item) => item.productSelected).length;
    },
  },
};
</script>
<style lang="scss">
.cart {
  .wrapper {
    background-color: $colorJ;
    padding-top: 30px;
    padding-bottom: 114px;
    .cart-box {
      background-color: $colorG;
      font-size: $fontJ;
      color: $colorD;
      text-align: center;
      .checkbox {
        display: inline-block;
        width: 22px;
        height: 22px;
        border: 1px solid $colorH;
        vertical-align: middle;
        margin-right: 17px;
        cursor: pointer;
        &.checked {
          background: url("/public/imgs/icon-gou.png") $colorA no-repeat center;
          background-size: 16px 12px;
          border: none;
        }
      }
      .cart-item-head {
        height: 79px;
        line-height: 79px;
      }
      .cart-item-list {
        .cart-item {
          align-items: center;
          height: 125px;
          border-top: 1px solid $colorH;
          font-size: $fontI;
        }
        .item-name {
          font-size: $fontH;
          color: $colorB;
          display: flex;
          align-items: center;
          img {
            width: 80px;
            height: 80px;
            vertical-align: middle;
          }
          span {
            margin-left: 30px;
          }
        }
        .item-price {
          color: $colorB;
        }
        .item-num {
          .num-box {
            display: inline-block;
            width: 150px;
            height: 40px;
            line-height: 40px;
            border: 1px solid $colorH;
            font-size: $fontJ;
            a {
              display: inline-block;
              width: 50px;
              color: $colorB;
            }
            span {
              display: inline-block;
              width: 50px;
              color: $colorB;
            }
          }
        }
        .item-total {
          color: $colorA;
        }
        .item-del {
          width: 14px;
          height: 12px;
          background: url("/public/imgs/icon-close.png") no-repeat center;
          background-size: contain;
          cursor: pointer;
        }
      }
    }
    .order-wrap {
      font-size: $fontJ;
      color: $colorC;
      margin-top: 20px;
      height: 50px;
      line-height: 50px;
      .cart-tip {
        margin-left: 29px;
        a {
          color: $colorC;
          margin-right: 37px;
        }
        span {
          color: $colorA;
          margin: 0 5px;
        }
      }
      .total {
        font-size: $fontJ;
        color: $colorA;
        span {
          font-size: $fontE;
        }
        a {
          width: 202px;
          height: 50px;
          line-height: 50px;
          font-size: $fontH;
          margin-left: 37px;
        }
      }
    }
  }
}
</style>
