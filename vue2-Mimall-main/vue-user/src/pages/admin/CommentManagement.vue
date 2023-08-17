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
          <el-button type="danger" size="small" @click="deleteComment(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      comments: []
    };
  },
  
  methods: {
    addComment() {
      this.comments.push({ content: 'New Comment' });
    },
    deleteComment(rowId) {
      axios.get(`http://127.0.0.1:15010/api/comment/deleteByIdTwo?id=${rowId}`)
        .then(res => {
          // Delete comment success
          // console.log(res);
          // Refresh comments
          this.getFindByAll();
        })
        .catch(error => {
          // Handle delete comment failure
          console.error(error);
        });
    },
    getFindByAll() {
      let data = {
        sort2:1,
        shop:null
      }
      axios.post('http://127.0.0.1:15010/api/comment/findByAllTwo',data)
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
    this.getFindByAll();
  },
};
</script>