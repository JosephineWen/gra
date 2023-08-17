<template>
  <div class="order-pay">
    <order-header title="Order payment">
      <template v-slot:order>
        <span>
          Please be cautious of phishing links or fraudulent phone calls. Learn
          more>
        </span>
      </template>
    </order-header>
    <div class="wrapper">
      <div class="container">
        <div class="order-wrap">
          <div class="item-order">
            <div class="icon-succ"></div>
            <div class="order-info">
              <h2>
                Order submitted successfully! Go ahead and make the payment~
              </h2>
              <p>
                Please complete the payment within <span>30 minutes</span> The
                order will be canceled if the payment is not made within the
                specified time
              </p>
            </div>
            <div class="order-total">
              <p>
                total amount payable:<span>{{ payment }}</span>
              </p>
              <p>
                Order details<em
                  class="icon-down"
                  :class="{ up: showDetail }"
                  @click="showDetail = !showDetail"
                ></em>
              </p>
            </div>
          </div>
          <div class="item-detail" v-if="showDetail">
            <div class="item">
              <div class="detail-title">Order number:</div>
              <div class="detail-info theme-color">
                <span v-for="(item,index) in list" :key="index">  {{ item.id }},</span>
              
              </div>
            </div>
            <div class="item">
              <div class="detail-title">logistics：</div>
              <div class="detail-info">
                {{
                  list[0].addName +
                  "-" +
                  list[0].addPhone +
                  "-" +
                  list[0].addText
                }}
              </div>
            </div>
            <div class="item good">
              <div class="detail-title">Product name:</div>
              <div class="detail-info">
                <ul>
                  <li v-for="item in list" :key="item.id">
                    <img
                      v-lazy="
                        require(`../assets/imgs/ProjectImg/${item.imgurl}`)
                      "
                    />
                    {{ item.sname }}
                  </li>
                </ul>
              </div>
            </div>
            <!-- <div class="item">
              <div class="detail-title">Invoice information</div>
              <div class="detail-info">Electronic invoice - Individual</div>
            </div> -->
          </div>
        </div>
        <div class="item-pay">
          <h3>
            Choose one of the following payment methods to make the payment
          </h3>
          <div class="pay-way">
            <p>Payment platform</p>
            <div class="pay pay-ali" @click="paySubmit(1)"></div>
            <div class="pay pay-wechat" @click="paySubmit(2)"></div>
          </div>
        </div>
      </div>
    </div>
    <scan-pay-code
      v-if="showPay"
      @close="closePayModal"
      :img="payImg"
    ></scan-pay-code>
    <modal
      title="支付确认"
      btnType="3"
      :showModal="showPayModal"
      sureText="查看订单"
      cancleText="未支付"
      @cancel="showPayModal = false"
      @submit="goOrderList"
    >
      <template v-slot:body>
        <p>您确认是否完成支付？</p>
      </template>
    </modal>
  </div>
</template>

<script>
import OrderHeader from "@/components/OrderHeader";
import ScanPayCode from "@/components/ScanPayCode.vue";
import Modal from "@/components/Modal.vue";
import QRCode from "qrcode";
import axios from "axios";
import { PAY_TYPE, PAY_STATUS } from "@/util";
import { Message } from "element-ui";
export default {
  name: "order-pay",
  components: { OrderHeader, ScanPayCode, Modal },
  data() {
    return {
      payment: 0,
      showDetail: false,
      payType: PAY_TYPE.ALIPAY,
      orderNo: this.$route.query.orderNo,
      addressInfo: "", //收货人地址
      productName: "",
      orderDetail: [], //订单详情
      showPay: false,
      payImg: "",
      showPayModal: false,
      T: "", //定时器ID
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
    this.getOrderList();
  },
  created() {},
  computed: {
    // isAliPayChecked() {
    //   return this.payType === PAY_TYPE.ALIPAY;
    // },
  },
  methods: {
    // 轮询 查看支付状态,定时器
    loopOrderStatus() {
      this.T = setInterval(() => {
        this.axios.get(`/orders/${this.orderNo}`).then((res) => {
          const { status } = res || {};
          if (status == PAY_STATUS.SUCCESS) {
            clearInterval(this.T);
            this.showPayModal = false;
            this.goOrderList();
          }
        });
      }, 2000);
    },
    goOrderList() {
      this.$router.push("/order/list");
    },
    paySubmit(payType) {
      if (this.orderNo != null) {
        let data = {
          id: this.orderNo,
          status: 2,
        };
        axios
          .post("http://127.0.0.1:15010/api/order/updateByStatus", data)
          .then((res) => {
            Message.success("Payment successful");
            this.$router.push("/order/list");
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        for (var i = 0; i < this.list.length; i++) {
          this.updateByStatus(this.list[i].id, 2);
        }
      }
    },
    updateByStatus(ids, status) {
      let data = {
        id: ids,
        status: status,
      };
      axios
        .post("http://127.0.0.1:15010/api/order/updateByStatus", data)
        .then((res) => {
          Message.success("Payment successful");
          this.$router.push("/order/list");
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getOrderList() {
      if (this.orderNo != null) {
        this.getOrderListById();
        return;
      }
      const flattenedArray = JSON.parse(window.sessionStorage.getItem('list'));
     
      this.list = flattenedArray.flat();
      console.log(this.list)
      for (var i = 0; i < this.list.length; i++) {
        // console.log(this.list[i])
        this.payment += this.list[i].money;
      }
      this.$forceUpdate();
    },


    getOrderListById() {
      let data = {
        id: this.orderNo,
      };
      axios
        .post("http://127.0.0.1:15010/api/order/getListByIds", data)
        .then((res) => {
          this.list = res.data;
          for (var i = 0; i < this.list.length; i++) {
            this.payment += this.list[i].money;
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },

    closePayModal() {
      this.showPay = false;
      this.showPayModal = true;
      this.payImg = "";
      clearInterval(this.T);
    },
    getOrderDetail() {
      this.axios.get(`/orders/${this.orderNo}`).then((res) => {
        const { shippingVo = {}, orderItemVoList, payment } = res || {};
        const {
          receiverName,
          receiverProvince,
          receiverCity,
          receiverDistrict,
          receiverAddress,
          receiverMobile,
          receiverZip,
        } = shippingVo;
        this.addressInfo = `${receiverName} ${receiverMobile}
        ${receiverProvince}/${receiverCity}/${receiverDistrict}/${receiverAddress} ${receiverZip}
        `;

        // let productNameArr = [];
        // orderItemVoList.map((item) => {
        //   productNameArr.push(`${item.productName} x ${item.quantity}`);
        // });
        // this.productName = productNameArr.join(",");
        this.orderDetail = orderItemVoList;
        this.payment = payment;
      });
    },

    isAlipay() {
      return this.payType === PAY_TYPE.ALIPAY;
    },
  },
};
</script>

<style lang="scss">
.order-pay {
  .wrapper {
    background-color: $colorJ;
    padding-top: 30px;
    padding-bottom: 61px;
    .order-wrap {
      padding: 62px 50px;
      background-color: $colorG;
      font-size: $fontJ;
      margin-bottom: 30px;
      .item-order {
        display: flex;
        align-items: center;
        .icon-succ {
          width: 90px;
          height: 90px;
          border-radius: 50%;
          background: url("/public/imgs/icon-gou.png") #80c58a no-repeat center;
          background-size: 60px;
          margin-right: 40px;
        }
        .order-info {
          margin-right: 248px;
          h2 {
            font-size: $fontE;
            color: $colorB;
            margin-bottom: 20px;
          }
          p {
            color: $colorC;
            span {
              color: #ff6700;
            }
          }
        }
        .order-total {
          & > p:first-child {
            margin-bottom: 30px;
          }
          span {
            font-size: $fontC;
            color: #ff6700;
          }
          .icon-down {
            display: inline-block;
            width: 14px;
            height: 10px;
            background: url("/public/imgs/icon-down.png") no-repeat center;
            background-size: contain;
            margin-left: 9px;
            transition: all 0.5s;
            cursor: pointer;
            &.up {
              transform: rotate(180deg);
            }
          }
          .icon-up {
            transform: rotate(180deg);
          }
        }
      }
      .item-detail {
        border-top: 1px solid $colorF;
        padding-top: 47px;
        padding-left: 130px;
        font-size: $fontJ;
        margin-top: 45px;
        .item {
          margin-bottom: 19px;
          .detail-title {
            float: left;
            width: 100px;
          }
          .detail-info {
            display: inline-block;
            img {
              width: 30px;
              vertical-align: middle;
            }
          }
        }
      }
    }
    .item-pay {
      padding: 26px 50px 72px;
      background-color: $colorG;
      color: $colorB;
      h3 {
        font-size: $fontG;
        font-weight: 200;
        color: $colorB;
        padding-bottom: 24px;
        border-bottom: 1px solid $colorF;
        margin-bottom: 26px;
      }
      .pay-way {
        font-size: $fontH;
        .pay {
          display: inline-block;
          width: 188px;
          height: 64px;
          border: 1px solid $colorF;
          cursor: pointer;
          &:last-child {
            margin-left: 20px;
          }
          &.checked {
            border: 1px solid #ff6700;
          }
        }
        .pay-ali {
          background: url("../assets/imgs/pay/icon-ali.png") no-repeat center;
          background-size: 103px 38px;
          margin-top: 19px;
        }
        .pay-wechat {
          background: url("../assets/imgs/pay/icon-wechat.png") no-repeat center;
          background-size: 103px 38px;
        }
      }
    }
  }
}
</style>
