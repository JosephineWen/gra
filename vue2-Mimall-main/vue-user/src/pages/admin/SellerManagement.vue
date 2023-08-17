<template>
  <div>
    <el-button type="primary" @click="showAddDialog">addSeller</el-button>
    <el-table :data="personnelList" style="width: 100%">
      <el-table-column label="No." width="180">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="shop" label="shopName"></el-table-column>
      <el-table-column prop="name" label="Name"></el-table-column>
      <el-table-column prop="age" label="Age"></el-table-column>
      <el-table-column prop="phone" label="Contact"></el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-button type="text" @click="showEditDialog(scope.row)">Edit</el-button>
          <el-button style="color: red;" type="text" @click="disableLogin(scope.row)" v-if="scope.row.status==1">Login prohibited</el-button>
          <el-button style="color: green;" type="text" @click="enableLogin(scope.row)" v-else>Login allowed</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="addDialogVisible" title="addSeller" @close="resetAddForm">
      <el-form :model="addForm" ref="addForm" :rules="addFormRules" label-width="90px">
        <el-form-item label="Shop" prop="shop">
          <el-input v-model="addForm.shop"></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Age" prop="age">
          <el-input v-model="addForm.age"></el-input>
        </el-form-item>
        <el-form-item label="Contact" prop="phone">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addPersonnel">Add</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="editDialogVisible" :title="'update ' + editForm.name" @close="resetEditForm">
      <el-form :model="editForm" ref="editForm" :rules="editFormRules" label-width="90px">
        <el-form-item label="Shop" prop="shop">
          <el-input v-model="editForm.shop"></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Age" prop="age">
          <el-input v-model="editForm.age"></el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="editForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updatePersonnel">Save</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      isLogin:true,
      personnelList: [

      ],
      addDialogVisible: false,
      addForm: {
        name: '',
        age: '',
        phone: '',
        password:'',
        code:2,
        shop:''
      },
      addFormRules: {
        name: [
          { required: true, message: 'Please enter your name.', trigger: 'blur' },
        ],
        age: [
          { required: true, message: 'Please enter your age', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: 'Please enter your contact information', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ],
        shop: [
          { required: true, message: 'Please enter your shop', trigger: 'blur' },
        ],
      },
      editDialogVisible: false,
      editForm: {
        name: '',
        age: '',
        phone: '',
        password:''
      },
      editFormRules: {
        name: [
          { required: true, message: 'Please enter your name', trigger: 'blur' },
        ],
        age: [
          { required: true, message: 'Please enter your age', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: 'Please enter your contact information', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password.', trigger: 'blur' },
        ],
      },
    };
  },
  created() {
    this.findByAll(2);
  },
  methods: {
    findByAll(rowId){
      axios.post(`http://127.0.0.1:15010/api/user/list?code=${rowId}`)
          .then(res => {
            // 请求成功处理
            this.personnelList = res.data.data; // 将获取到的数据赋值给reports数组
          })
          .catch(error => {
            // 请求失败处理
            console.error(error);
          });
    },
    showAddDialog() {
      this.addDialogVisible = true;
    },
    resetAddForm() {
      this.$refs.addForm.resetFields();
      this.addForm = {
        name: '',
        age: '',
        phone: '',
        code:2,
        shop:''
      };
    },
    addPersonnel() {
      let data = this.addForm;
      axios.post('http://127.0.0.1:15010/api/user/add',data)
          .then(res => {
            this.findByAll(2);
          })
          .catch(error => {
            console.error(error);
          });
      this.$message.success('Added successfully');
    },
    showEditDialog(row) {
      this.editForm = Object.assign({}, row);
      this.editDialogVisible = true;
    },
    resetEditForm() {
      this.$refs.editForm.resetFields();
      this.editForm = {
        name: '',
        age: '',
        position: '',
      };
    },
    updatePersonnel() {
      let data = this.editForm;
      axios.post('http://127.0.0.1:15010/api/user/update',data)
          .then(res => {
            this.findByAll(2);
          })
          .catch(error => {
            console.error(error);
          });
      this.$message.success('Modified successfully');
    },
    deletePersonnel(index) {
      this.personnelList.splice(index, 1);
      message.success('Deleted successfully');
    },
    disableLogin(row) {
      let data = {
        id:row.id,
        status:2
      }
      axios.post('http://127.0.0.1:15010/api/user/updateStatus',data)
          .then(res => {
            this.findByAll(2);
          })
          .catch(error => {
            console.error(error);
          });
      this.$message.error('Login prohibited');
    },
    enableLogin(row) {
      let data = {
        id:row.id,
        status:1
      }
      axios.post('http://127.0.0.1:15010/api/user/updateStatus',data)
          .then(res => {
            this.findByAll(2);
          })
          .catch(error => {
            console.error(error);
          });
      this.$message.success('Login allowed');
    },
  },
};
</script>