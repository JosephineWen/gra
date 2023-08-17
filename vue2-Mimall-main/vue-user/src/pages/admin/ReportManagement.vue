<template>
  <div>
    <el-table :data="reports" style="width: 100%">
      <el-table-column label="No" prop="id" width="300"></el-table-column>
      <el-table-column label="Report the product" prop="productId"></el-table-column>
      <el-table-column label="Report content" prop="text"></el-table-column>
      <el-table-column label="User" prop="userId"></el-table-column>
      <el-table-column label="time" prop="time"></el-table-column>
      <el-table-column label="status">
        <template slot-scope="scope">
          <span :style="getStatusStyle(scope.row.status)">{{ getStatusText(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 1" type="primary" size="small" @click="coordinateReport(scope.row)">Processing completed</el-button>
          <el-button v-else-if="scope.row.status === 2" type="warning" size="small" @click="resolveReport(scope.row)">Processing completed</el-button>
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
      reports: []
    };
  },
  methods: {
    findByAll() {
      axios.get('http://127.0.0.1:15010/api/report/findByAll')
        .then(res => {
          // 请求成功处理
          this.reports = res.data.data; // 将获取到的数据赋值给reports数组

          console.log(res.data.code)
        })
        .catch(error => {
          // 请求失败处理
          console.error(error);
        });
    },
    getStatusStyle(status) {
      if (status === 1) {
        return { color: 'red' };
      } else if (status === 2) {
        return { color: 'orange' };
      } else if (status === 3) {
        return { color: 'green' };
      }
    },
    getStatusText(status) {
      if (status === 1) {
        return 'Pending processing';
      } else if (status === 2) {
        return 'Being coordinated';
      } else if (status === 3) {
        return 'Resolved';
      }
    },
    coordinateReport(date2) {
      let date = {
        id:date2.id,
        status:2
      }
      axios.post('http://127.0.0.1:15010/api/report/updateBYStatus',date)
        .then(res => {
         this.findByAll();
         console.log(res);  
        })
        .catch(error => {
          // 请求失败处理
          console.error(error);
        });
    },
    resolveReport(date2) {
      let date = {
        id:date2.id,
        status:3
      }
      axios.post('http://127.0.0.1:15010/api/report/updateBYStatus',date)
        .then(res => {
         this.findByAll();
         console.log(res);  
        })
        .catch(error => {
          // 请求失败处理
          console.error(error);
        });
    }
  },
  created() {
    this.findByAll();
  },
};
</script>
