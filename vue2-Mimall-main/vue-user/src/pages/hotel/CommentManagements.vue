<template>
  <div>
    <el-table :data="comments" style="width: 100%">
      <el-table-column label="No." width="180">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="Username"></el-table-column>
      <el-table-column prop="text" label="Content"></el-table-column>
      <el-table-column prop="time" label="Time"></el-table-column>
      <el-table-column label="Offensive Remark">
        <template slot-scope="scope">
          <span :style="{ color: scope.row.sort === 1 ? 'red' : 'green' }">{{ scope.row.sort === 1 ? 'Abnormal' : 'Normal' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="100">
        <template slot-scope="scope">
          <el-button type="danger" v-if="scope.row.status==1" size="small" @click="openSize(scope.row)">reply</el-button>
        </template>
      </el-table-column>
    </el-table>
    <modal
      :title="title"
      btnType="1"
      :showModal="showEditModal2"
      @cancel="showEditModal2 = false"
      @submit="deleteComment"
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
  </div>
</template>

<script>
import axios from 'axios';
import Modal from "@/components/Modal.vue";
export default {

  components: {
    Modal
  },
  data() {
  
    return {
      testOne:{
        text:"",
        parent:"",
        userId:"",
        productId:"",
        status:""
      },
      showEditModal2:false,
      comments: [],
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
    };
  },
  mounted(){
    this.user =JSON.parse(window.sessionStorage.getItem('user'));
    this.getShopByUserId();
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
          this.getFindByAll();
          console.log(this.shop);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    addComment() {
      this.comments.push({ content: 'New Comment' });
    },
    openSize(item){
      this. showEditModal2=true;
      this.testOne.userId = this.user.id;
      this.testOne.parent = item.id,
      this.testOne.productId = item.productId
    },
    deleteComment() {
      this.testOne.status=2;
      let data = this.testOne;
      axios.post(`http://127.0.0.1:15010/api/comment/addComm`,data)
        .then(res => {
          // Delete comment success
          // console.log(res);
          // Refresh comments
          this.getFindByAll();
          this.showEditModal2= false;
        })
        .catch(error => {
          // Handle delete comment failure
          console.error(error);
        });
    },
    getFindByAll() {
      let data = {
        sort2:null,
        shopId:this.shop.id
      }
      axios.post('http://127.0.0.1:15010/api/comment/findByAllThree',data)
        .then(res => {
          this.comments = res.data.data;
          console.log(res);  
        })
        .catch(error => {
          // Handle request failure
          console.error(error);
        });
    }
  },
  
  created() {
    
  },
};
</script>