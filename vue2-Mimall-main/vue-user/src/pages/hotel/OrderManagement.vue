<template>
  <div>
    <el-table :data="orders" style="width: 100%">
      <el-table-column prop="id" label="No" width="220"></el-table-column>
      <el-table-column prop="expree" label="expreNo" width="200">
        <template slot-scope="scope">
          <el-input v-model="scope.row.expree" size="mini" v-if="scope.row.status == '2'"></el-input>
          <el-input v-model="scope.row.expree" size="mini" v-if="scope.row.status != '2'" style="border: 0px;" disabled></el-input>
        </template>
      </el-table-column>
      <el-table-column prop="sname" label="Name" width="220"></el-table-column>
      <el-table-column prop="image" label="img"  width="120">
        <template slot-scope="scope" >
          <img
            :src="require(`../../assets/imgs/ProjectImg/${scope.row.imgurl}`)"
            alt=""
            style="width: 80px; height: 80px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="money" label="price" width="100"></el-table-column>
      <el-table-column prop="number" label="number" width="100"></el-table-column>
      <el-table-column prop="addText" label="address" width="220"></el-table-column>
      <el-table-column prop="time" label="OderTime" width="220"></el-table-column>
    
      <el-table-column label="status" width="200">
        <template slot-scope="scope">
          <el-tag :type="getStatusTagType(scope.row.status)">{{ scope.row.status|messagse }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Operation" fixed="right" width="200">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleAction(scope.row)" v-if="scope.row.status == '2'">{{ getActionText(scope.row.status) }}</el-button>
          <el-button type="primary" size="small" @click="handleAction(scope.row)" v-if="scope.row.status == '1'">{{ getActionText(scope.row.status) }}</el-button>

        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      orders: [],
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
      shop:{
        id:'',
        userid:'',
        name:'',
        imgurl:''
      }
    }
  },
  mounted() {
    this.user =JSON.parse(window.sessionStorage.getItem('user'));
    this.getShopByUserId();
  },
  filters:{
    messagse(value){
      if(value=='1'){
        return 'Pending payment ';
      }
      if(value=='2'){
        return 'Pending shipment';
      }
      if(value=='3'){
        return 'Pending delivery';
      }
      if(value=='4'){
        return 'Pending review';
      }
      if(value=='5'){
        return ' Complete the order';  
      }
      

      
    }
  },
  methods: {
    getShopByUserId(){
      let data = {
        id:this.user.id
      }
      axios
        .post("http://127.0.0.1:15010/api/shop/getById",data)
        .then((res) => {
          this.shop = res.data;
          this.findByAll();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    findByAll(){
      let data = {
        pinglinid:this.shop.id
      }
      axios
        .post(`http://127.0.0.1:15010/api/order/getListByStatusorPid2`,data)
        .then((res) => {
          this.orders = res.data; // 将获取到的数据赋值给reports数组
          console.log(this.orders)
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getStatusTagType(value) {
      if(value=='1'){
        return 'warning';
      }
      if(value=='2'){
        return 'info';
      }
      if(value=='3'){
        return 'success';
      }
      if(value=='4'){
        return 'warning';
      }
      if(value=='5'){
        return 'success';
      }
      
    },
    getActionText(status) {
      switch (status) {
        case '1':
          return 'Cancel the order';
        case '2':
          return 'Ship the order';
        default:
          return '';
      }
    },
    handleAction(order) {
      if(order.status=='2'){
        if(order.expree==null){
          alert(" Please enter the tracking number");
          return;
        }
      }
      order.status = parseInt(order.status)+1
      let data = order;
      axios
        .post(`http://127.0.0.1:15010/api/order/updateByStatus`, data)
        .then((res) => {
         this.getShopByUserId();
        })
        .catch((error) => {
          console.error(error);
        });




    },
    shipOrder(order) {
      // Implement your logic to ship the order here
      // For example, you can update the order status to '已发货' and perform other necessary actions
      order.status = 'Shipped';
    },
    handleDropshipping(order) {
      // Implement your logic to handle dropshipping here
      // For example, you can update the order status to '卖家发货' and perform other necessary actions
      order.status = '卖家发货';
    }
  }
}
</script>
