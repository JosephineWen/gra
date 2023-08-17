import Vue from "vue";
import Router from "vue-router";
import Index from "./pages/index";
import Home from "./pages/home";

Vue.use(Router);

const routes = [{
    path: "/",
    name: "home",
    component: Home,
    redirect: "/index",
    children: [
        {path: "index", name: "index", component: Index},
        {
            path: "product/:id",
            name: "product",
            component: () =>
                import("./pages/product"),
        },
        {
            path: "detail/:id",
            name: "detail",
            component: () =>
                import("./pages/detail"),
        },
        {
            path: "search",
            name: "search",
            component: () =>
                import("./pages/search"),
        }
    ],
},
    {
        path: "/login",
        name: "login",
        component: () =>
            import("./pages/login"),
    },
    {
        path: "/insert",
        name: "insert",
        component: () =>
            import("./pages/insert"),
    },
    {
        path: "/cart",
        name: "cart",
        component: () =>
            import("./pages/cart"),
    },
    {
        path: "/address",
        name: "address",
        component: () =>
            import("./pages/address"),
    },
    {
        path: "/hotel",
        name: "hotel",
        component: () =>
            import("./pages/hotel/Hotel"),
        children: [
            {
                path: "/ProductInfo",
                name: "ProductInfo ",
                component: () =>
                    import("./pages/hotel/ProductInfo"),
            },
            {
                path: "/InfoManagementTwo",
                name: "InfoManagementTwo",
                component: () =>
                    import("./pages/hotel/InfoManagementTwo"),
            },
            {
                path: "/OrderManagement",
                name: "OrderManagement",
                component: () =>
                    import("./pages/hotel/OrderManagement"),
            },
            {
                path: "/CommentManagements",
                name: "CommentManagements",
                component: () =>
                    import("./pages/hotel/CommentManagements"),
            }
        ],
    },
    {
        path: "/admin",
        name: "admin",
        component: () =>
            import("./pages/admin/Admin"),
        children: [
            {
                path: "/UserManagement",
                name: "UserManagement",
                component: () =>
                    import("./pages/admin/UserManagement"),
            },
            {
                path: "/SellerManagement",
                name: "SellerManagement",
                component: () =>
                    import("./pages/admin/SellerManagement"),
            },
            {
                path: "/ProductManagement",
                name: "ProductManagement",
                component: () =>
                    import("./pages/admin/ProductManagement"),
            },
            {
                path: "/CategoryManagement",
                name: "CategoryManagement",
                component: () =>
                    import("./pages/admin/CategoryManagement"),
            },
            {
                path: "/ExpressManagement",
                name: "ExpressManagement",
                component: () =>
                    import("./pages/admin/ExpressManagement"),
            },
            {
                path: "/ReportManagement",
                name: "ReportManagement",
                component: () =>
                    import("./pages/admin/ReportManagement"),
            },
            {
                path: "/CommentManagement",
                name: "CommentManagement",
                component: () =>
                    import("./pages/admin/CommentManagement"),
            },

        ],
    },

    {
        path: "/order",
        name: "order",
        component: () =>
            import("./pages/order"),
        children: [{
            path: "confirm",
            name: "order-confirm",
            component: () =>
                import("./pages/orderConfirm"),
        },
            {
                path: "list",
                name: "order-list",
                component: () =>
                    import("./pages/orderList"),
            },
            {
                path: "my",
                name: "my",
                component: () =>
                    import("./pages/my"),
            },

            {
                path: "commtents",
                name: "commtents",
                component: () =>
                    import("./pages/commtents"),
            },
            {
                path: "pay",
                name: "order-pay",
                component: () =>
                    import("./pages/orderPay"),
            },
            {
                path: "alipay",
                name: "alipay",
                component: () =>
                    import("./pages/alipay"),
            },
        ],
    },
];

export default new Router({mode: "history", routes});
