<template>
  <div class="cart">
    <order-header title="Shipping Information">
      <template v-slot:order>
        <span
          >Tips: Whether the product is successfully purchased is subject to the
          final order, please settle as soon as possible</span
        >
      </template>
    </order-header>
    <div class="wrapper">
      <div class="container">
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="Name" label-position="top">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="Phone Number" label-position="top">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
          <el-form-item label="Email" label-position="top">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="Shipping Address" label-position="top">
            <el-input type="textarea" v-model="form.address"></el-input>
          </el-form-item>

          <div
            class="total fr"
            style="display: flex; float: left; margin-left: 100px"
          >
            <a
              href="javascript:;"
              class="btn"
              @click="godoOrder"
              style="
                margin-right: 20px;
                height: 50px;
                display: flex;
                align-items: center;
                justify-content: center;
              "
              >Previous step</a
            >
            <a
              href="javascript:;"
              class="btn"
              @click="godoPlay"
              style="
                margin-right: 20px;
                height: 50px;
                display: flex;
                align-items: center;
                justify-content: center;
                background: rgb(235, 73, 73);
              "
              >Go to payment</a
            >
          </div>
        </el-form>
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
      form: {
        id:'',
        name: "",
        phone: "",
        email: "",
        address: "",
        userid:""
      },
      addressId:"",
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
    this.user = JSON.parse(window.sessionStorage.getItem("user"));
    this.form.userid = this.user.id;
  },
  methods: {
    godoOrder() {
      this.$router.push("/cart");
    },
    godoPlay() {
      if(this.form.name==''||this.form.phone==''||this.form.email==''||this.form.address==''){
        Message.warning("Please enter complete information");
        return;
      }
      this.updateAnd();
    },
    submitForm() {
      console.log(this.form);
    },
    
   addOrders() {
      let data = {
        userid:this.user.id,
        addressid:this.addressId
      }
      axios
        .post("http://127.0.0.1:15010/api/order/addByOrder", data)
        .then((res) => {
           let list = [];
           list = res.data.data;
           window.sessionStorage.setItem('list', JSON.stringify(list));
           console.log(JSON.parse(window.sessionStorage.getItem('list')))
           this.$router.push("/order/pay");
        })
        .catch((error) => {
          console.error(error);
        });
    },
    updateAnd() {
     let data = this.form;
      axios
        .post("http://127.0.0.1:15010/api/address/addByAddress", data)
        .then((res) => {
          this.addressId = res.data.data;
          this.addOrders();
        })
        .catch((error) => {
          console.error(error);
        });
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
          height: 60px;
          line-height: 50px;
          font-size: $fontH;
          margin-left: 37px;
        }
      }
    }
  }
}
</style>
