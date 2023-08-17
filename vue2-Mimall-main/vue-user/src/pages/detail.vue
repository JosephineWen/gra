<template>
  <div class="detail">
    <product-param :title="product.sname"></product-param>

    <div class="wrapper">
      <div class="container clearfix">
        <div class="swiper">
          <swiper :options="swiperOption">
            <swiper-slide v-for="item in 1" :key="item.id">
              <img :src="require(`../assets/imgs/ProjectImg/${product.imgurl}`)" />
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>

        <div class="content">
          <div class="item-title">{{ product.sname }}</div>
          <div class="item-info">
            {{ product.subtitle }}
          </div>
          <div class="delivery">{{ product.shop }}</div>
          <div class="item-price">
            £{{ product.price || 0 }}<span class="del">{{ product.price+(product.price*0.3) }}</span>
          </div>
          <div class="line"></div>
          <div class="item-addr">
            <i class="icon-loc"></i>
            <div class="addr">Current location</div>
            <div class="stock">Inventory:{{ product.number }}</div>
          </div>
          <div class="item-version clearfix">
            <h2>Select version</h2>
            <div
              class="phone fl"
              :class="{ checked: version === 1 }"
              @click="version = 1"
            >
            Default configuration
            </div>
            <!-- <div
              class="phone fr"
              :class="{ checked: version === 2 }"
              @click="version = 2"
            >
              4GB+64GB 移动4G
            </div> -->
          </div>
          <div class="item-color">
            <h2>Select color</h2>
            <div class="phone checked">
              <span class="color"></span>
              Default color
            </div>
          </div>

          <div class="item-total">
            <div class="phone-info clearfix">
              <div class="phone fl">
                {{ product.name }}
                {{ version === 1 ? "  Default configuration" : " Default configuration" }}
                Default color
              </div>
              <div class="phone fr"> £{{ product.price || 0 }}</div>
            </div>
            <div class="phone-total">Total: £{{ product.price || 0 }}</div>
          </div>

          <div class="btn-group">
            <span  class="btn btn-large" @click="addCart"
              >Add to cart</span
            >
          </div>
        </div>
      </div>
    </div>

    <div class="price-info">
      <div class="container">
        <h2></h2>
        <div class="desc">
          <img src="" alt="" />
        </div>
      </div>
    </div>
    <service-bar></service-bar>
  </div>
</template>

<script>
import axios from "axios";
import ProductParam from "@/components/ProductParam";
import { swiper, swiperSlide } from "vue-awesome-swiper";
import ServiceBar from "@/components/ServiceBar.vue";
import { Message } from "element-ui";
export default {
  name: "detail",
  components: {
    ProductParam,
    swiper,
    swiperSlide,
    ServiceBar,
  },
  data() {
    return {
      productId: this.$route.params.id,
      product: {
        address: "",
        code: "",
        id: "",
        imgurl: "",
        number: 0,
        ordernumber: null,
        pinglinid: "",
        price:0,
        sname: "",
        text: "",
        title: " ",
        tname: "",
        typeid: ""
      },
      swiperOption: {
        autoplay: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      sliderList: [
        { id: 2, img: "/imgs/detail/phone-1.jpg" },
        { id: 3, img: "/imgs/detail/phone-2.jpg" },
        { id: 4, img: "/imgs/detail/phone-3.jpg" },
        { id: 5, img: "/imgs/detail/phone-4.jpg" },
      ],
      version: 1, //
    };
  },
  mounted() {
    this.getProductInfo();
    this.user =JSON.parse(window.sessionStorage.getItem('user'));
  
  },
  methods: {
    addCart() {
     
      if(JSON.parse(window.sessionStorage.getItem('user'))==null){
        this.$router.push("/login");
      }
      let data = {
        productid:this.productId,
        userid:this.user.id
      };
      axios
        .post("http://127.0.0.1:15010/api/car/add",data)
        .then((res) => {
          Message.success("Successfully added to the shopping cart");
          console.log( this.res);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getProductInfo() {
      window.sessionStorage.setItem('productId', JSON.stringify(this.productId));
     
      let data = {
        id:this.productId
      }
      axios
        .post(`http://127.0.0.1:15010/api/product/getById`,data)
        .then((res) => {
          this.product=res.data.data;
        })
        .catch((error) => {
          console.error(error);
        });

      // this.axios.get(`/products/${this.productId}`).then((res) => {
      //   this.product = res || {};
      // });
    },
    
  },
};
</script>

<style lang="scss">
.detail {
  .wrapper {
    .swiper {
      float: left;
      width: 542px;
      height: 517px;
      margin-top: 5px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .content {
      float: right;
      width: 584px;
      height: auto;
      min-height: 870px;
      padding-bottom: 10px;
      .item-title {
        font-size: $fontC;
        padding-top: 30px;
        padding-bottom: 16px;
        min-height: 26px;
        height: auto;
        
      }
      .item-info {
        font-size: $fontJ;
        height: 36px;
      }
      .delivery {
        font-size: $fontI;
        color: $colorA;
        margin-top: 26px;
        margin-bottom: 14px;
        height: 15px;
      }
      .item-price {
        font-size: $fontG;
        color: $colorA;
        height: 19px;
        .del {
          font-size: $fontI;
          color: $colorD;
          margin-left: 10px;
          text-decoration: line-through;
        }
      }
      .line {
        height: 0;
        margin-top: 25px;
        margin-bottom: 28px;
        border: 1px solid $colorH;
      }
      .item-addr {
        height: 108px;
        background-color: #fafafa;
        border: 1px solid $colorH;
        box-sizing: border-box;
        padding-top: 31px;
        padding-left: 64px;
        font-size: $fontJ;
        line-height: 14px;
        position: relative;
        .icon-loc {
          position: absolute;
          top: 27px;
          left: 34px;
          @include bgImg(20px, 20px, "/public/imgs/detail/icon-loc.png");
        }
        .addr {
          color: $colorC;
        }
        .stock {
          margin-top: 15px;
          color: $colorA;
        }
      }
      .item-version,
      .item-color {
        margin-top: 30px;
        h2 {
          font-size: $fontH;
          margin-bottom: 20px;
        }
      }
      .item-version,
      .item-color {
        margin-top: 30px;
        .phone {
          width: 287px;
          height: 50px;
          line-height: 50px;
          font-size: $fontI;
          color: $colorB;
          border: 1px solid $colorH;
          box-sizing: border-box;
          text-align: center;
          cursor: pointer;
          span {
            color: $colorC;
            margin-left: 15px;
          }
          .color {
            display: inline-block;
            width: 14px;
            height: 14px;
            background-color: $colorC;
          }

          &.checked {
            border: 1px solid $colorA;
            color: $colorA;
          }
        }
      }

      .item-total {
        height: 108px;
        background-color: #fafafa;
        padding: 24px 33px 29px 30px;
        font-size: $fontJ;
        margin-top: 50px;
        margin-bottom: 30px;
        box-sizing: border-box;
        .phone-total {
          font-size: $fontE;
          color: $colorA;
          margin-top: 18px;
        }
      }
    }
  }
  .price-info {
    background-color: #f3f3f3;
    height: 340px;
    h2 {
      font-size: $fontE;
      color: $colorB;
      padding-top: 38px;
      margin-bottom: 30px;
    }
  }
}
</style>
