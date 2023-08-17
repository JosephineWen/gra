<template>
  <div class="container">
    <div class="register">
      <h2>registration</h2>
      <form>
        <div class="form-group">
          <label for="phone">phone number：</label>
          <input type="text" id="phone" v-model="phone" placeholder="Please enter your mobile phone number" />
        </div>
        <div class="form-group">
          <label for="code">CAPTCHA：</label>
          <input type="text" id="code" v-model="code" placeholder="Please enter the verification code" />
          <button class="btn-get-code" @click="getCode" :disabled="countdown > 0">
            {{ countdown > 0 ? countdown + 's' : 'Get CAPTCHA' }}
          </button>
        </div>
        <div v-if="isCodeValid">
          <div class="form-group">
            <label for="username">username：</label>
            <input type="text" id="username" v-model="username" placeholder="Please enter your username" />
          </div>
          <div class="form-group">
            <label for="password">password：</label>
            <input type="password" id="password" v-model="password" placeholder="Please enter your password" />
          </div>
        </div>
        <button class="btn-register" @click="register">registration</button>
      </form>
      <div class="sms" @click="login">Return to Login</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      phone: '',
      code: '',
      countdown: 0,
      isCodeValid: false,
      username: '',
      password: ''
    };
  },
  methods: {
    login(){
      this.$router.push('/login');
    },
    getCode() {
     
      this.countdown = 60;
      const timer = setInterval(() => {
        if (this.countdown > 0) {
          this.countdown--;
        } else {
          clearInterval(timer);
        }
      }, 1000);
    },
    register() {
      // Logic of registration
      // ...
      // Submit form
      console.log('phone number：', this.phone);
      console.log('CAPTCHA：', this.code);
      console.log('username：', this.username);
      console.log('password：', this.password);
    },
      code() {
      // Check if the captcha is correct
      if (this.code === '123456') {
        this.isCodeValid = true;
      } else {
        this.isCodeValid = false;
      }
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register {
  max-width: 400px;
  padding: 20px;
  background-color: #f0f0f0;
  border-radius: 5px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.btn-get-code {
  padding: 8px 15px;
  border: none;
  border-radius: 3px;
  background-color: #2196f3;
  color: #fff;
  cursor: pointer;
}

.btn-get-code:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.btn-register {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 3px;
  background-color: #4caf50;
  color: #fff;
  cursor: pointer;
}
</style>
