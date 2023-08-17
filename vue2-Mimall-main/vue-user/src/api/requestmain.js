import axios from "../util/request";
const coures = {
  findByAllUser() {
    return axios({
      url: '/user/list',
      method: 'get',
    })
  },
  deleteByOrderId(data) {
    return axios({
      url: '/order/deleteById',
      method: 'post',
      data
    })
  },
}
export default coures;





