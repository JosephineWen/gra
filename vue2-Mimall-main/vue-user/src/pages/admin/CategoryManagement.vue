<template>
  <div>
    <el-button type="primary" @click="addCategory">Add category</el-button>
    <el-table :data="categories" style="width: 100%">
      <el-table-column label="No." width="180">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
        </el-table-column>
      <el-table-column label="Category name" prop="text"></el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="editCategory(scope.row)"
            >Edit</el-button
          >
          <el-button
            type="danger"
            size="mini"
            @click="deleteCategory(scope.row.value)"
            >Delete</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="category">
      <el-form :model="currentCategory" label-width="80px">
      
        <el-form-item label="Category name">
          <el-input v-model="currentCategory.text"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveCategory">Save</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      categories: [
       
      ],
      dialogVisible: false,
      currentCategory: {
        value:'',
        text:''
      },
    };
  },
  methods: {
    getFindByAll() {
      axios
        .post("http://127.0.0.1:15010/api/product/typeList")
        .then((res) => {
          this.categories = res.data;
          console.log( this.categories);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    editCategory(category) {
      this.currentCategory = Object.assign({}, category);
      this.dialogVisible = true;
    },
    deleteCategory(category) {
      axios
        .get(`http://127.0.0.1:15010/api/product/deleteById?id=${category}`)
        .then((res) => {
          this.getFindByAll();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    saveCategory() {
      let data = this.currentCategory;
      axios
        .post("http://127.0.0.1:15010/api/product/typeAdd",data)
        .then((res) => {
          this.getFindByAll();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    addCategory() {
      this.currentCategory = {};
      this.dialogVisible = true;
    },
  },
  created() {
    this.getFindByAll();
  },
};
</script>
