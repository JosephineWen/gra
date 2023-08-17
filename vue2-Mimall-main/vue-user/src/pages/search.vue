<template>
    <div class="index">
        <div class="product-box">
            <div class="container">
                <h2>Search Result</h2>
                <div class="wrapper">
                    <div class="list-box">
                        <div class="list">
                            <div v-for="(item, i) in productList" :key="item.id" class="item">
                                <span :class="{ 'new-pro': i % 2 === 0 }">{{
                                        item.title
                                    }}</span>
                                <div class="item-img">
                                    <a v-bind:href="'/detail/' + item.id">
                                        <img v-lazy="require(`../assets/imgs/ProjectImg/${item.imgurl}`)"/>
                                    </a>
                                </div>
                                <div class="item-info">
                                    <h3 style="font-size: 10px">
                                        {{ item.sname.substring(0, 50) }}
                                    </h3>
                                    <p>{{ item.subtitle }}</p>
                                    <p class="price" @click="addCart(item.id)">
                                        £{{ item.price }}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <service-bar></service-bar>
        <modal
            :showModal="showModal"
            btnType="1"
            modalType="middle"
            sureText="show cart"
            title="message"
            @cancel="showModal = false"
            @submit="goToCart"
        >
            <template v-slot:body>
                <p>Item added successfully!</p>
            </template>
        </modal>
    </div>
</template>

<script>
import ServiceBar from "@/components/ServiceBar.vue";
import Modal from "@/components/Modal.vue";
import {directive} from "vue-awesome-swiper";
import "swiper/css/swiper.css";
import axios from "axios";

export default {
    name: "index",
    props: ["dataFromOtherPage"],
    components: {
        ServiceBar,
        Modal,
    },
    directives: {
        swiper: directive,
    },
    watch: {
        dataFromOtherPage(newValue) {
            // 在接收到数据时执行相应的方法
            this.searchTwo(newValue);
        },
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
            swiperOption: {
                autoplay: true,
                loop: true,
                effect: "cube",
                cubeEffect: {
                    shadowOffset: 10,
                    shadowScale: 0.6,
                },
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                },
                navigation: {
                    nextEl: ".swiper-button-next",
                    prevEl: ".swiper-button-prev",
                },
            },
            sliderList: [
                {img: require("../assets/imgs/slider/slide-1.jpg")},
                {img: require("../assets/imgs/slider/slide-2.jpg")},
                {img: require("../assets/imgs/slider/slide-3.jpg")},
                {img: require("../assets/imgs/slider/slide-4.jpg")},
                {img: require("../assets/imgs/slider/slide-5.jpg")},
            ],
            menuList: [],
            productList: [
                [1, 1, 1, 1],
                [1, 1, 1, 1],
            ],
            showModal: false,
            publicPath: process.env.BASE_URL,
        };
    },
    mounted() {
        this.user = JSON.parse(window.sessionStorage.getItem("user"));
        this.searchTwo(this.$route.query.id)
        this.unwatch = this.$watch("$route.query.id", (newName) => {
            this.searchTwo(this.$route.query.id)
        });
        this.init();
        this.findByAll();
        this.getFindByAll();
    },
    computed(){
        this.user = JSON.parse(window.sessionStorage.getItem("user"));
    },
    methods: {
        getFindByAll() {
            axios
                .post("http://127.0.0.1:15010/api/product/typeListTwo")
                .then((res) => {
                    this.menuList = res.data;
                    console.log(this.categories);
                })
                .catch((error) => {
                    console.error(error);
                });
        },
        splitArray(arr, size) {
            const result = [];
            for (let i = 0; i < arr.length; i += size) {
                result.push(arr.slice(i, i + size));
            }
            return result;
        },
        searchTwo(ac) {
            if (ac == undefined){
                this.findByAll()
            }
            axios
                .post(`http://127.0.0.1:15010/api/product/searchTwo?search=${ac}`)
                .then((res) => {
                    let arr = res.data.data
                    this.productList = arr
                    console.log(this.productList);
                })
                .catch((error) => {
                    console.error(error);
                });
        },
        findByAll() {
            axios
                .post(`http://127.0.0.1:15010/api/product/timeList`)
                .then((res) => {
                    let arr = res.data.data
                    this.productList = arr
                    console.log(this.productList);
                })
                .catch((error) => {
                    console.error(error);
                });
        },
        init() {
            this.axios
                .get("/products", {
                    params: {
                        categoryId: 100012,
                        pageSize: 14,
                    },
                })
                .then((res) => {
                    // slice不会改变原数组
                    res.list = res.list.slice(6, 14);
                    this.productList = [res.list.slice(0, 4), res.list.slice(4, 8)];
                });
        },
        addCart(productId) {
            if (JSON.parse(window.sessionStorage.getItem("user")) == null) {
                this.$router.push("/login");
            }
            let data = {
                productid: productId,
                userid: this.user.id,
            };
            console.log(data)
            axios
                .post("http://127.0.0.1:15010/api/car/add", data)
                .then((res) => {
                    console.log(this.res);
                })
                .catch((error) => {
                    console.error(error);
                });
            this.showModal = true;
        },
        goToCart() {
            this.$router.push("/cart");
        },
    },
};
</script>

<style lang="scss">
.index {
    .swiper-box {
        position: relative;

        .nav-menu {
            width: 264px;
            height: 451px;
            position: absolute;
            left: 0;
            top: 0;
            z-index: 9;
            padding: 26px 0;
            background: #55585a7a;
            box-sizing: border-box;

            .menu-wrap {
                .menu-item {
                    height: 50px;
                    line-height: 50px;

                    &:hover {
                        background: $colorA;

                        .children {
                            display: block;
                            // background-color: rebeccapurple;
                            overflow-y: scroll;
                        }
                    }

                    a {
                        font-size: $fontI;
                        color: $colorG;
                        padding-left: 30px;
                        position: relative;
                        display: block;

                        &:after {
                            @include bgImg(10px, 15px, "/public/imgs/icon-arrow.png");
                            content: "";
                            position: absolute;
                            top: 17.5px;
                            right: 30px;
                        }
                    }

                    .children {
                        width: 962px;
                        height: 451px;
                        background-color: $colorG;
                        position: absolute;
                        left: 264px;
                        top: 0;
                        border: 1px solid $colorH;
                        display: none;

                        ul {
                            @include flex();
                            height: 75px;

                            li {
                                height: 75px;
                                line-height: 75px;
                                width: 241px;
                                flex: 1;
                                padding-left: 23px;
                            }

                            a {
                                color: $colorB;
                                font-style: 14px;
                            }

                            img {
                                width: 42px;
                                height: 35px;
                                vertical-align: middle;
                                margin-right: 15px;
                            }
                        }
                    }
                }
            }
        }

        .swiper-container {
            height: 451px;

            img {
                width: 100%;
                height: 100%;
            }

            .swiper-button-prev {
                margin-left: 264px;
            }
        }
    }

    .ads-box {
        @include flex();
        margin-top: 14px;
        margin-bottom: 31px;

        a {
            width: 296px;
            height: 167px;
        }
    }

    .banner {
        margin-bottom: 50px;
    }

    .product-box {
        background-color: $colorJ;
        padding: 30px 0 50px;

        .container {
            width: $min-width;
            margin: 0 auto;

            h2 {
                font-size: $fontF;
                line-height: 21px;
                height: 21px;
                color: $colorB;
                margin-bottom: 20px;
            }

            .wrapper {
                display: flex;

                .banner-left {
                    margin-right: 16px;

                    img {
                        width: 224px;
                        height: 619px;
                    }
                }

                .list-box {
                    .list {
                        justify-content: flex-start;
                        flex-wrap: wrap;
                        width: 1286px;
                        margin-bottom: 14px;
                        &:last-child {
                            margin-bottom: 0;
                        }

                        .item {
                            width: 236px;
                            height: 302px;
                            background-color: $colorG;
                            text-align: center;
                            margin-top: 20px;
                            margin-left: 20px;
                            span {
                                display: inline-block;
                                width: 67px;
                                height: 24px;
                                line-height: 24px;
                                font-size: $fontJ;
                                color: $colorG;
                                margin-bottom: 14px;

                                &.new-pro {
                                    background-color: #7ecf68;
                                }

                                &.kill-pro {
                                    background-color: #e82626;
                                }
                            }

                            .item-img {
                                margin-bottom: 20px;

                                img {
                                    width: 80%;
                                    height: 159px;
                                }
                            }

                            .item-info {
                                h3 {
                                    font-size: $fontJ;
                                    color: $colorB;
                                    line-height: $fontJ;
                                    font-weight: bold;
                                }

                                p {
                                    color: $colorD;
                                    line-height: 13px;
                                    margin: 6px auto 13px;
                                }

                                .price {
                                    color: #f20a0a;
                                    font-size: $fontJ;
                                    font-weight: bold;
                                    cursor: pointer;

                                    &:after {
                                        content: "";
                                        margin-left: 5px;
                                        vertical-align: middle;
                                        @include bgImg(
                                                22px,
                                                22px,
                                                "/public/imgs/icon-cart-hover.png"
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
</style>

