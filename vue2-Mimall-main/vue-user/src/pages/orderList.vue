<template>
  <div class="order-list">
    <order-header title="Order list">
      <template v-slot:order>
        <span
          >Please be cautious of phishing links or fraudulent phone calls. Learn
          more>
        </span>
      </template>
    </order-header>

    <div class="wrapper">
      <div class="container">
        <div class="order-box">
          <!-- <loading v-if="loading"></loading> -->
          <div style="height: 50px">
            <el-select
              v-model="value"
              placeholder="Please select"
              siz="mini"
              @change="change"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div
            class="order"
            v-for="(order, index) in list"
            :key="index"
           
          >
            <div class="order-title">
              <div class="item-info fl"  @click="addAddr(order)">
                {{ order.time }}
                <span>|</span>
                {{ order.shopname }}
                <span>|</span>
                Order number:{{ order.id }}
                <span>|</span>
                {{ order.paymentTypeDesc }}
              </div>
              <div class="item-money fr">
                <span > Order status：</span>
                <span class="money" :class="getStyleClass(order.status)">{{ order.status|findByStatus }}</span>
                <span></span>
              </div>
              <div class="item-money fr">
                <span>Order amount：</span>
                <span class="money">£{{ order.money }}</span>
                <span  style="margin-right: 20px;"></span>
              </div>
             
            </div>
            <div class="order-content clearfix">
              <div class="good-box fl">
                <!-- v-for="(item, i) in order.orderItemVoList"
                  :key="i" -->
                <div class="good-list">
                  <div class="good-img">
                    <img
                      v-lazy="
                        require(`../assets/imgs/ProjectImg/${order.imgurl}`)
                      "
                    />
                  </div>
                  <div class="good-name"  @click="addAddr(order)">
                    {{ order.sname }}
                    {{ " £" + order.money / order.number }}
                    {{ " x " + order.number }}
                  </div>
                </div>
              </div>
              <div class="good-state fr" v-if="isPaySuccess(order.status)">
                <a href="javascript:;">{{ order.status }}</a>
              </div>
              <div class="good-state fr" v-else>
                <a
                  href="javascript:;"
                  @click="goPay(order.id)"
                  v-if="order.status == 1"
                  style="margin-right: 10px;"
                >
                  <el-button type="success" size="mini"
                    >payment</el-button
                  >
                </a>
                <a
                  href="javascript:;"
                  @click="open(order.id)"
          
                  v-if="order.status == 2 || order.status==1"
                >
                  <el-button type="warning" size="mini"
                    > Cancel</el-button
                  >
                </a>


                <a
                  href="javascript:;"
                  @click="updateByStatus(order.id,order.status)"
                  v-if="order.status == 3"
                >
                  <el-button type="warning" size="mini"
                    >Confirm receipt</el-button
                  >
                </a>
                <a
                  href="javascript:;"
                  @click="openSize2(order.productid,order.id)"
                  v-if="order.status == 4"
                >
                  <el-button type="warning" size="mini"> Review</el-button>
                </a>
                <a
                  href="javascript:;"
                  @click="openSize3(order.id)"
                  v-if="order.status == 5"
                  style="margin-left: 10px"
                >
                  <el-button type="danger" size="mini"> Report</el-button>
                </a>
                <a
                  href="javascript:;"
                  @click="open(order.id)"
                  v-if="order.status == 5"
                  style="margin-left: 10px"
                >
                  <el-button type="danger" size="mini"> remove</el-button>
                </a>
              </div>
            </div>
          </div>

          <!-- 分页加载数据 -->
          <el-pagination
            v-if="false"
            class="pagination"
            background
            layout="prev, pager, next"
            :pageSize="pageSize"
            :total="total"
            @current-change="handleChange"
          >
          </el-pagination>

          <!-- 加载更多按钮 加载数据 -->
          <div class="load-more" v-if="false">
            <el-button type="primary" :loading="loading" @click="loadMore">
              加载更多
            </el-button>
          </div>

          <!-- 滚动到底部加载数据 -->
          <div
            class="scroll-more"
            v-infinite-scroll="scrollMore"
            infinite-scroll-disabled="busy"
            infinite-scroll-distance="410"
            v-if="true"
          >
            <img
              src="/imgs/loading-svg/loading-spinning-bubbles.svg"
              v-show="loading"
            />
          </div>
          <no-data v-if="!loading && list.length == 0">
            <template v-slot:text>
              <p>There are currently no submitted order records</p>
            </template>
          </no-data>
        </div>
      </div>
    </div>
    <modal
      :title="title"
      btnType="1"
      :showModal="showEditModal"
      @cancel="showEditModal = false"
      @submit="colseOrderText"
    >
      <template v-slot:body>
        <div class="edit-wrap">
          <el-form label-width="100px" size="mini">
            <div class="item">
              <el-form-item label="">
                <img
                  v-lazy="
                    require(`../assets/imgs/ProjectImg/${orderText.imgurl}`)
                  "
                  width="100px"
                  height="100px"
                />
              </el-form-item>
              <el-form-item label="">{{ orderText.sname }}</el-form-item>
            </div>
            <el-form-item label="No">{{ orderText.id }}</el-form-item>
            <el-form-item label="time">{{ orderText.time }}</el-form-item>
            <div class="item">
              <el-form-item label="info">
                <label for="" style="color: orange; font-size: 15px">{{
                  " £" + orderText.money / orderText.number
                }}</label>
                {{ " x " + orderText.number }}
              </el-form-item>
              <el-form-item label="sum" style="color: red"
                >£{{ orderText.money }}</el-form-item
              >
            </div>
            <div class="item">
              <el-form-item label="Tracking">
                {{ orderText.expree }}
              </el-form-item>
              <el-form-item label="status">
                {{ orderText.status | findByStatus }}
              </el-form-item>
              <el-form-item label="Shipping address">
                {{ orderText.addName }}-{{ orderText.addPhone }}-{{
                  orderText.addText
                }}
              </el-form-item>

            </div>
          </el-form>
        </div>
      </template>
    </modal>
    

    <modal
      :title="title"
      btnType="1"
      :showModal="showEditModal2"
      @cancel="showEditModal2 = false"
      @submit="Subcomments"
    >
      <template v-slot:body>
        <div class="edit-wrap">
          <el-form label-width="100px" size="mini">
            <div class="item">
              <el-form-item label="Review content">
                <el-input v-model="testOne.text"></el-input>
              </el-form-item>
            </div>
          </el-form>
        </div>
      </template>
    </modal>
    <modal
      :title="title"
      btnType="1"
      :showModal="showEditModal3"
      @cancel="showEditModal3 = false"
      @submit="Subreport"
    >
      <template v-slot:body>
        <div class="edit-wrap">
          <el-form label-width="100px" size="mini">
            <div class="item">
              <el-form-item label="Review content">
                <el-input v-model="testTwo.text"></el-input>
              </el-form-item>
            </div>
          </el-form>
        </div>
      </template>
    </modal>
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
      testOne:{
        productId:'',
        userId:'',
        text:'',
        status:1,
        orderId:''
      },
      testTwo:{
        productId:'',
        userId:'',
        text:''
      },
      showEditModal: false, //是否显示新增或者编辑弹框
      showEditModal2: false, 
      showEditModal3: false, 
      value: "",
      options: [
        {
          value: "1",
          label: "Pending payment",
        },
        {
          value: "2",
          label: "Pending shipment",
        },
        {
          value: "3",
          label: "Pending delivery",
        },
        {
          value: "4",
          label: "Pending review",
        },
        {
          value: "5",
          label: "Completed",
        },
        {
          value: "6",
          label: "all",
        },
      ],
      loading: false,
      list: [],
      order: {},
      total: 0,
      pageNum: 1,
      pageSize: 10,

      showNextPage: true, //加载更多，是否显示按钮
      busy: false, // busy 为false 滚动加载才会生效
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
      orderText: {
        addressid: "",
        expree: "",
        id: "",
        imgurl: "",
        kid: "",
        money: 0,
        number: 0,
        productid: "",
        shopid: null,
        shopimg: "",
        shopname: "",
        sname: "",
        status: "",
        time: "",
        time1: null,
        time2: null,
        userid: "",
        addName: "",
        addPhone: "",
        addEmail: "",
        addText: "",
      },
      checkedItem: {
        receiverProvince: "",
        receiverCity: "",
        receiverDistrict: "",
      }, //选中的商品对象
    };
  },
  mounted() {
    this.user = JSON.parse(window.sessionStorage.getItem("user"));
    this.getOrderList();
  },
  filters:{
    findByStatus(val){
      if(val==1){
        return "Unpaid";
      }
      if(val==2){
        return "Awaiting shipment";
      }
      if(val==3){
        return "Awaiting delivery";
      }
      if(val==4){
        return "Awaiting review";
      }
      if(val==5){
        return "Completed";
      }
    }
  },
  methods: {
    open(id) {
        this.$confirm('This operation will delete the order. Do you want to continue?', 'message', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          this.deleteBy(id);
          this.$message({
            type: 'success',
            message: 'Deletion successful!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancellation of deletion completed'
          });          
        });
      },
    getStyleClass(value) {
      if (value == 1) {
        return '.one';
      } 
       if (value == 2) {
        return '.two';
      }
      if (value == 3) {
        return '.three';
      }
      if (value == 4) {
        return '.four';
      }
      if (value == 5) {
        return '.five';
      }
    },
    updateByStatus(ids,statuss){
      let data = {
        id:ids,
        status: parseInt(statuss)+1,
      };
      axios
        .post("http://127.0.0.1:15010/api/order/updateByStatus", data)
        .then((res) => {
          if(statuss==3){
            Message.success("Confirmation of receipt successful");
          }
          this.getOrderList();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    colseOrderText(){
      this.showEditModal = false;
    },
    deleteBy(id) {
      let data = {
        id: id,
      };
      axios
        .post(`http://127.0.0.1:15010/api/order/deleteById`, data)
        .then((res) => {
          this.getOrderList();
        })
        .catch((error) => {
          // 请求失败处理
          console.error(error);
        });
    },
    openSize() {
      this.showEditModal = true;
    },
    openSize2(id,id2) {
      this.testOne.productId = id;
      this.testOne.orderId=id2,
      this.testOne.userId = this.user.id;
      this.title = "Reviewing the product";
      this.showEditModal2 = true;
    
    },
    Subcomments(){
      let data = {
        productId: this.testOne.productId,
        text: this.testOne.text,
        userId:this.testOne.userId,
        status:1
      };
      axios
        .post("http://127.0.0.1:15010/api/comment/addComm", data)
        .then((res) => {
          Message.success("Product review successful");
          this.updateByStatus(this.testOne.orderId,4)
          this.showEditModal2 = false;
          this.getOrderList();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    Subreport(){
      let data = {
        productId: this.testTwo.productId,
        text: this.testTwo.text,
        userId:this.testTwo.userId
      };
      axios
        .post("http://127.0.0.1:15010/api/report/addReporting", data)
        .then((res) => {
          Message.success("Product report successful");
          this.showEditModal3 = false;
          this.getOrderList();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    openSize3(id) {
      this.testTwo.productId = id;
      this.testTwo.userId = this.user.id;
      this.title = "Report the product"; 
      this.showEditModal3 = true;
    },
    addAddr(item) {
      this.orderText = item;
      this.showEditModal = true;
      this.title = "View details";
    },
    change(e) {
      if(e=='6'){
        this.value="";
      }
      this.getOrderList();
    },
    // 滚动加载
    scrollMore() {
      this.busy = true;
      setTimeout(() => {
        this.pageNum++;
        this.getOrderList();
      }, 500);
    },

    // 加载更多按钮
    loadMore: function () {
      this.pageNum++;
      this.getOrderList();
    },

    // 分页器
    handleChange(pageNum) {
      this.pageNum = pageNum;
      this.getOrderList();
    },
    getOrderList() {
      let data = {
        userid: this.user.id,
        status: this.value,
      };
      axios
        .post("http://127.0.0.1:15010/api/order/getListByStatus", data)
        .then((res) => {
          this.list = res.data;
          console.log(this.list);
        })
        .catch((error) => {
          console.error(error);
        });

      // this.loading = true;
      // this.busy = true;

      // this.axios
      //   .get("/orders", {
      //     params: {
      //       pageSize: this.pageSize,
      //       pageNum: this.pageNum,
      //     },
      //   })
      //   .then((res) => {
      //     this.loading = false;
      //     this.busy = false;
      //     const { list = [], total = 0, hasNextPage, pageNum = 0 } = res || {};
      //     this.list = this.list.concat(list);
      //     this.total = total;
      //     this.pageNum = pageNum;
      //     this.showNextPage = hasNextPage;
      //     if (hasNextPage) {
      //       this.busy = false;
      //     } else {
      //       this.busy = true;
      //     }
      //   })
      //   .catch(() => {
      //     this.loading = false;
      //   });
    },
    goPay(orderNo) {
      this.$router.push({
        path: "/order/pay",
        query: { orderNo },
      });
    },
    isPaySuccess(status) {
      return status === PAY_STATUS.SUCCESS;
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
  .one{
    color: green;
  }
  .two{
    color: blue;
  }
  .three{
    color: paleturquoise;
  }
  .four{
    color: orange;
  }
  .five{
    color: purple;
  }
}
</style>
