<template>
  <div>
    <el-button type="primary" @click="showAddDialog">add Product</el-button>
    <el-table :data="goodsList" style="width: 100%">
      <el-table-column
        prop="sname"
        label="Name"
        width="300"
      ></el-table-column>
      <el-table-column prop="image" label="Product img">
        <template slot-scope="scope">
          <img
            :src="require(`../../assets/imgs/ProjectImg/${scope.row.imgurl}`)"
            alt=""
            style="width: 80px; height: 80px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="price" label="Price"></el-table-column>
      <el-table-column prop="number" label="Inventory"></el-table-column>
      <el-table-column prop="typeid" label="Product category"></el-table-column>
      <!-- <el-table-column prop="pinglinid" label="所属店铺"></el-table-column> -->
      <el-table-column prop="code" label="Product status">
        <template slot-scope="scope">
          <span v-if="scope.row.code == '1'" class="status-on">Put on shelves</span>
          <span v-else class="status-off">Take off shelves</span>
        </template>
      </el-table-column>
      <el-table-column label="Operation" fixed="right" width="300">
        <template slot-scope="scope">
          <el-button
            type="success"
            size="small"
            @click="changeStatus(scope.row)"
            >{{ scope.row.code == 1 ? "Put on" : "Take off" }}</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="showEditDialog(scope.row)"
            >update</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="deleteGoods(scope.row.id)"
            >remove</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="addDialogVisible"
      title="add Product"
      @close="resetAddDialog"

    >
      <el-form ref="addForm" :model="addForm" label-width="80px">
        <el-form-item label="Name">
          <el-input v-model="addForm.sname"></el-input>
        </el-form-item>
        <el-form-item label="Img">
          <el-upload
            action="http://127.0.0.1:15010/api/product/upload3"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload"
            :file-list="addForm.imgurl"
            list-type="picture-card"
            :auto-upload="true"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="Price">
          <el-input v-model="addForm.price"></el-input>
        </el-form-item>
        <el-form-item label="Inventory">
          <el-input v-model="addForm.number"></el-input>
        </el-form-item>
        <el-form-item label="Status">
          <el-radio-group v-model="addForm.code">
            <el-radio label="1">Put on</el-radio>
            <el-radio label="2">Take off</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Category">
          <el-select v-model="addForm.typeid">
            <el-option
              v-for="(item, index) in categories"
              :key="index"
              :label="item.text"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="tag">
          <el-select v-model="addForm.title">
            <el-option
              v-for="(item, index) in productLabels"
              :key="index"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      
      </el-form>
      <div slot="footer">
        <el-button @click="resetAddDialog">Cancel</el-button>
        <el-button type="primary" @click="addGoods">Confirm</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="editDialogVisible"
      title="Modify the product"
      
      @close="resetEditDialog"
    >
      <el-form ref="editForm" :model="editForm" label-width="80px" >
        <el-form-item label="Name">
          <el-input v-model="editForm.sname"></el-input>
        </el-form-item>
        <el-form-item label="Img">
          <el-upload
            action="http://127.0.0.1:15010/api/product/upload2"
            :data="{ id: editForm.id }"
            :on-success="handleUploadSuccess2"
            :before-upload="beforeUpload"
            :file-list="editForm.imgurl"
            list-type="picture-card"
            :auto-upload="true"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="Price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
        <el-form-item label="Inventory">
          <el-input v-model="editForm.number"></el-input>
        </el-form-item>
        <el-form-item label="category">
          <el-select v-model="editForm.typeid">
            <el-option
              v-for="(item, index) in categories"
              :key="index"
              :label="item.text"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
       
      </el-form>
      <el-form-item label="tag">
          <el-select v-model="editForm.title">
            <el-option
              v-for="(item, index) in productLabels"
              :key="index"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      <div slot="footer">
        <el-button @click="resetEditDialog">Cancel</el-button>
        <el-button type="primary" @click="updateGoods">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  <script>
import axios from "axios";
export default {
  data() {
    return {
      productLabels: [
        'Bestseller',
        'Hot item',
        'Trending product',
        'Popular pick',
        'Top-rated',
        'Limited edition',
        'New arrival',
        'Clearance sale',
        'Exclusive',
        'Featured',
        'Must-have',
        'Deal of the day',
        'On sale',
        'Best value',
        'Clearance',
        'Pre-order',
        'Back in stock'
      ],
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
      goodsList: [
        {},
        // 其他商品数据...
      ],
      addDialogVisible: false,
      editDialogVisible: false,
      categories: [],
      shopList:[],
      addForm: {
        sname: "",
        imgurl: "",
        price: "",
        number: "",
        code: "",
        typeid: "",
        pinglinid: "",
        tags:[]
      },
      editForm: {
        sname: "",
        imgurl: "",
        price: "",
        number: "",
        code: "",
        typeid: "",
        pinglinid: "",
        tags:[]
      },
      shop:{
        id:'',
        userid:'',
        name:'',
        imgurl:''
      }
    };
  },
  mounted() {
    this.user =JSON.parse(window.sessionStorage.getItem('user'));
    this.getShopByUserId();
    this.getTypeList();
   
   
   
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
          console.log(this.shop);
          this.findByAll();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getTypeList() {
      
      axios
        .post("http://127.0.0.1:15010/api/product/typeList")
        .then((res) => {
          this.categories = res.data;
          console.log(this.categories);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    findByAll() {
      let data = {
        id:this.shop.id
      }
      axios
        .post(`http://127.0.0.1:15010/api/product/listByShopId`,data)
        .then((res) => {
          this.goodsList = res.data.data; // 将获取到的数据赋值给reports数组
        })
        .catch((error) => {
          console.error(error);
        });
    },
    showAddDialog() {
      this.addDialogVisible = true;
    },
    resetAddDialog() {
      this.addDialogVisible = false;
      this.$refs.addForm.resetFields();
    },
    addGoods() {
      this.addForm.pinglinid = this.shop.id;
      let data = this.addForm;
      axios
        .post(`http://127.0.0.1:15010/api/product/add`, data)
        .then((res) => {
          this.findByAll();
        })
        .catch((error) => {
          console.error(error);
        });

      this.resetAddDialog();
    },
    showEditDialog(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    resetEditDialog() {
      this.editDialogVisible = false;
      this.$refs.editForm.resetFields();
    },
    updateGoods() {
      let data = this.editForm;
      axios
        .post(`http://127.0.0.1:15010/api/product/update`, data)
        .then((res) => {
          this.findByAll();
        })
        .catch((error) => {
          console.error(error);
        });
      // 处理修改商品逻辑
      this.resetEditDialog();
    },
    deleteGoods(row) {
      axios
        .get(`http://127.0.0.1:15010/api/product/deleteByPid?id=${row}`)
        .then((res) => {
          this.findByAll();
        })
        .catch((error) => {
          // 请求失败处理
          console.error(error);
        });
      console.log(row);
    },
    changeStatus(row) {
      let codes = row.code;
      if (codes == 1) {
        codes = 2;
      } else {
        codes = 1;
      }
      let data = {
        id: row.id,
        code: codes,
      };
      axios
        .post(`http://127.0.0.1:15010/api/product/updateByCode`, data)
        .then((res) => {
          this.findByAll();
        })
        .catch((error) => {
          console.error(error);
        });
      console.log(row);
    },
    handleUploadSuccess(response, file, fileList) {
      this.addForm.imgurl = response.imgurl;
      this.addForm.tags = response.tags;
    },
    handleUploadSuccess2(response, file, fileList) {
      this.editForm.imgurl = response.imgurl;
     
    },
    beforeUpload(file) {
      // 处理上传前的逻辑
      console.log(file);
    },
  },
};
</script>
  
  <style scoped>
.status-on {
  color: green;
}
.status-off {
  color: red;
}
.v-modal{

}
</style>