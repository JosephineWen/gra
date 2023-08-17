<template>
    <div class="header">
        <div class="nav-topbar">
            <div class="container">
                <div class="topbar-menu">
                    <a href="javascript:;">Home</a>
                    <!-- <a href="javascript:;">Categories</a> -->
                    <!-- <a href="javascript:;">cloud service</a>
                    <a href="javascript:;">protocol rules</a> -->
                </div>
                <div class="topbar-user">
                    <a href="javascript:;" v-if="user.username">{{ user.username }}</a>
                    <a href="javascript:;" v-if="!user.username" @click="login"
                    >sign in</a
                    >
                    <a href="javascript:;" v-if="user.username" @click="logout">logout</a>
                    <a href="/order/list" v-if="user.username">orders</a>
                    <a href="/order/my" v-if="user.username">my</a>
                    <a href="javascript:;" class="my-cart" @click="goToCart">
                        <span class="icon-cart"></span>
                        cart({{ cartCounts }})
                    </a>
                </div>
            </div>
        </div>
        <div class="nav-header">
            <div class="container">
                <div class="header-logo">
                    <a href="/index"></a>
                </div>
                <div class="header-menu">
                    <div class="item-menu">
                        <span @mousemove="getProductList(1)">Mobile Phones</span>
                        <div class="children">
                            <ul>
                                <li
                                        class="product"
                                        v-for="(item, index) in phoneList1"
                                        :key="index"
                                >
                                    <a v-bind:href="'/detail/' + item.id">
                                        <div class="pro-img">
                                            <img
                                                    :src="
                          require(`../assets/imgs/ProjectImg/${item.imgurl}`)
                        "
                                                    :alt="item.sname"
                                            />
                                        </div>
                                        <div class="pro-name">
                                            {{ item.sname.substring(0, 20) }}
                                        </div>
                                        <div class="pro-price">{{ item.price | currency }}</div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="item-menu">
                        <span @mousemove="getProductList(2)">Televisions</span>
                        <div class="children">
                            <ul>
                                <li
                                        class="product"
                                        v-for="(item, index) in phoneList2"
                                        :key="index"
                                >
                                    <a v-bind:href="'/detail/' + item.id">
                                        <div class="pro-img">
                                            <img
                                                    :src="
                          require(`../assets/imgs/ProjectImg/${item.imgurl}`)
                        "
                                                    :alt="item.sname"
                                            />
                                        </div>
                                        <div class="pro-name">
                                            {{ item.sname.substring(0, 20) }}
                                        </div>
                                        <div class="pro-price">{{ item.price | currency }}</div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="item-menu">
                        <span @mousemove="getProductList(3)">Computers</span>
                        <div class="children">
                            <ul>
                                <li
                                        class="product"
                                        v-for="(item, index) in phoneList3"
                                        :key="index"
                                >
                                    <a v-bind:href="'/detail/' + item.id">
                                        <div class="pro-img">
                                            <img
                                                    :src="
                          require(`../assets/imgs/ProjectImg/${item.imgurl}`)
                        "
                                                    :alt="item.sname"
                                            />
                                        </div>
                                        <div class="pro-name">
                                            {{ item.sname.substring(0, 20) }}
                                        </div>
                                        <div class="pro-price">{{ item.price | currency }}</div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="header-search">
                    <div class="wrapper">
                        <input type="text" name="keyword" v-model="search"/>
                        <a @click="sendValue"></a>
                    </div>
                </div>
            </div>
        </div>
        <Index ref="index"/>
    </div>
</template>

<script>
import {mapState, mapActions} from "vuex";
import {Message} from "element-ui";
import axios from "axios";
import Index from "../pages/index.vue";

export default {
    name: "nav-header",
    compoments: {
        Index,
    },
    data() {
        return {
            cartCounts: 0,
            props: ["childValue"],
            search: "",
            phoneList1: [],
            phoneList2: [],
            phoneList3: [],
            publicPath: process.env.BASE_URL,
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
    computed: {
        ...mapState(["username", "cartCount"]),
    },
    filters: {
        currency(val) {
            if (!val) {
                return "0.00";
            }
            return "€" + val.toFixed(2) + "";
        },
    },
    mounted() {
        this.getProductList();
        this.user = JSON.parse(window.sessionStorage.getItem("user"));
        if (this.user != undefined) {
            this.getCartCount();
        }
        this.interval = setInterval(this.getCartCount, 1000);

    },
    beforeDestroy() {
        clearInterval(this.interval);
    },
    methods: {
        ...mapActions(["saveUsername", "saveCartCount"]),
        sendValue() {
            let data = this.search;
            this.$router.push({path: '/search', query: {id: data}})
        },
        getProductList(val) {
            if (val == 1) {
                if (this.phoneList1.length > 0) {
                    return;
                }
            }
            if (val == 2) {
                if (this.phoneList2.length > 0) {
                    return;
                }
            }
            if (val == 3) {
                if (this.phoneList3.length > 0) {
                    return;
                }
            }
            let data = {
                typeid: val,
            };
            axios
                .post("http://127.0.0.1:15010/api/product/byTypelist", data)
                .then((res) => {
                    if (val == 1) {
                        this.phoneList1 = res.data.data;
                    }
                    if (val == 2) {
                        this.phoneList2 = res.data.data;
                    }
                    if (val == 3) {
                        this.phoneList3 = res.data.data;
                    }
                })
                .catch((error) => {
                    console.error(error);
                });
        },
        goToCart() {
            if (!this.user) {
                this.$router.push("/login");
                return;
            }
            if (JSON.parse(window.sessionStorage.getItem("user")) != null) {
                this.$router.push("/cart");
            } else {
                this.$router.push("/login");
            }
        },
        login() {
            this.$router.push("/login");
        },
        logout() {
            Message.success("Logout successful");
            window.sessionStorage.removeItem("user");
            this.user = {};
            this.cartCounts = 0;
            window.onload();
        },
        getCartCount() {
            let data = {
                userid: this.user.id
            }
            axios
                .post("http://127.0.0.1:15010/api/order/getCount", data)
                .then((res) => {
                    this.cartCounts = res.data.data;
                    console.log(this.cartCounts)
                })
                .catch((error) => {
                    console.error(error);
                });
        },
    },
};
</script>

<style lang="scss">
.header {
  .nav-topbar {
    height: 39px;
    line-height: 39px;
    background: $colorB;
    color: #b0b0b0;

    .container {
      @include flex();

      a {
        color: #b0b0b0;
        display: inline-block;
        margin-right: 17px;
      }

      .my-cart {
        width: 110px;
        background: $colorA;
        text-align: center;
        margin-right: 0px;
        color: $colorG;

        .icon-cart {
          @include bgImg(16px, 12px, "/public/imgs/icon-cart-checked.png");
          margin-right: 4px;
        }
      }
    }
  }

  .nav-header {
    .container {
      height: 112px;
      position: relative;
      @include flex();

      .header-logo {
        display: inline-block;
        width: 55px;
        height: 55px;
        background: $colorA;

        a {
          display: inline-block;
          width: 110px;
          height: 55px;

          &:before {
            content: "";
            @include bgImg(55px, 55px, "/public/imgs/mi-home.png", 55px);
            transition: margin 0.2s;
          }

          &:after {
            content: "";
            @include bgImg(55px, 55px, "/public/imgs/mi-home.png", 55px);
          }

          &:hover:before {
            margin-left: -55px;
            transition: margin 0.2s;
          }
        }
      }

      .header-menu {
        display: inline-block;
        padding-left: 209px;
        width: 643px;

        .item-menu {
          display: inline-block;
          color: $colorB;
          font-weight: bold;
          font-size: $fontI;
          line-height: 112px;
          margin-right: 20px;

          span {
            cursor: pointer;
          }

          &:hover {
            color: $colorA;

            .children {
              height: 220px;
              opacity: 1;
            }
          }

          .children {
            position: absolute;
            background: $colorG;
            top: 112px;
            left: 0;
            width: 1226px;
            border-top: 1px solid $colorH;
            box-shadow: 0px 7px 6px 0px rgba(0, 0, 0, 0.11);
            z-index: 10;
            height: 0px;
            opacity: 0;
            overflow: hidden;
            transition: height 0.2s;

            .product {
              float: left;
              width: 16.6%;
              height: 220px;
              font-size: $fontK;
              line-height: 12px;
              text-align: center;
              position: relative;

              &:before {
                position: absolute;
                top: 28px;
                right: 0;
                border-left: 1px solid $colorF;
                height: 100px;
                width: 1px;
                content: "";
              }

              &:last-child:before {
                display: none;
              }

              a {
                display: inline-block;
              }

              img {
                height: 110px;
                width: auto;
                margin-top: 26px;
              }

              .pro-img {
                height: 137px;
              }

              .pro-name {
                font-weight: bold;
                margin-top: 19px;
                margin-bottom: 8px;
                color: $colorB;
              }

              .pro-price {
                color: $colorA;
              }
            }
          }
        }
      }

      .header-search {
        width: 319px;

        .wrapper {
          height: 50px;
          border: 1px solid #e0e0e0;
          display: flex;
          align-items: center;

          input {
            border: none;
            border-right: 1px solid #e0e0e0;
            width: 264px;
            height: 50px;
            padding-left: 14px;
            box-sizing: border-box;
          }

          a {
            @include bgImg(18px, 18px, "/public/imgs/icon-search.png");
            margin-left: 17px;
          }
        }
      }
    }
  }
}
</style>
