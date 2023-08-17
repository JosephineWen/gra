<template>
  <div>
    <el-button type="primary" @click="showAddDialog">Add a product</el-button>
    <el-table :data="goodsList" style="width: 100%">
      <el-table-column label="No." width="180">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
        </el-table-column>
      <el-table-column
        prop="sname"
        label="Name"
        width="300"
      ></el-table-column>
      <el-table-column prop="image" label="Image">
        <template slot-scope="scope">
          <img
            :src="require(`../../assets/imgs/ProjectImg/${scope.row.imgurl}`)"
            alt=""
            style="width: 80px; height: 80px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="price" label="Price" width="100"></el-table-column>
      <el-table-column prop="number" label="Inventory " width="100"></el-table-column>

      <el-table-column prop="typeid" label="Product classification" width="100"></el-table-column>
      <el-table-column prop="pinglinid" label="Store affiliation." width="100"></el-table-column>
      <el-table-column prop="code" label="status" width="200">
        <template slot-scope="scope">
          <span v-if="scope.row.code == '1'" class="status-on">Put on</span>
          <span v-else class="status-off">Take off </span>
        </template>
      </el-table-column>
      <el-table-column label="Operation" fixed="right" width="320">
        <template slot-scope="scope">
          <el-button
            type="success"
            size="small"
            @click="changeStatus(scope.row)"
            >{{ scope.row.code == 1 ? "Take off " : "Put on" }}</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="showEditDialog(scope.row)"
            >Modify</el-button
          >
          <el-button
            type="danger"
            size="small"
            @click="deleteGoods(scope.row.id)"
            >Delete</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="addDialogVisible"
      title="Add a product"
      @close="resetAddDialog"
    >
      <el-form ref="addForm" :model="addForm" label-width="80px">
        <el-form-item label="Name">
          <el-input v-model="addForm.sname"></el-input>
        </el-form-item>
        <el-form-item label="Image">
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
        <el-form-item label="price">
          <el-input v-model="addForm.price"></el-input>
        </el-form-item>
        <el-form-item label="Inventory">
          <el-input v-model="addForm.number"></el-input>
        </el-form-item>
        <el-form-item label="Status">
          <el-radio-group v-model="addForm.code">
            <el-radio label="1">Put on</el-radio>
            <el-radio label="2">Take off </el-radio>
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
        <el-form-item label="Store">
          <el-select v-model="addForm.pinglinid">
            <el-option
              v-for="(item, index) in shopList"
              :key="index"
              :label="item.name"
              :value="item.id"
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
      <el-form ref="editForm" :model="editForm" label-width="80px">
        <el-form-item label="Modify the product">
          <el-input v-model="editForm.sname"></el-input>
        </el-form-item>
        <el-form-item label=" ">
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
        <el-form-item label="Product category">
          <el-select v-model="editForm.typeid">
            <el-option
              v-for="(item, index) in categories"
              :key="index"
              :label="item.text"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Tag">
          <el-select v-model="editForm.title">
            <el-option
              v-for="(item, index) in productLabels"
              :key="index"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Store affiliation">
          <el-select v-model="editForm.pinglinid">
            <el-option
              v-for="(item, index) in shopList"
              :key="index"
              :label="item.name"
              :value="item.id"
            ></el-option>
            </el-select>
        </el-form-item>
      </el-form>
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
      goodsList: [
        {},
        // 其他商品数据...
      ],
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
    };
  },
  created() {
    this.findByAll();
    this.getTypeList();
    this.getShopList();
  },
  methods: {
    getShopList() {
      axios
        .post("http://127.0.0.1:15010/api/shop/shopList")
        .then((res) => {
          this.shopList = res.data;
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
      axios
        .get(`http://127.0.0.1:15010/api/product/listAll`)
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
</style>