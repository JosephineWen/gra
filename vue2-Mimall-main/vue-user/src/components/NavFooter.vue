<template>
  <div class="footer">
    <div class="footer-logo">

      <p>Shopping</p>
    </div>

    <div class="footer-link">
      <a href="/index">Home</a>
      <a href="javascript:;">friendly link</a>
      <a href="javascript:;">friendly link</a>
      <a href="javascript:;">friendly link</a>
      <a href="javascript:;">friendly link</a>
    </div>
    <div class="copyright">
      Reserve judicial interpretation&nbsp;&nbsp; email:1306075230@qq.com
    </div>
  </div>
</template>

<script>
export default {
  name: "nav-footer",
  data() {
    return {
      publicPath: process.env.BASE_URL,
    };
  },
};
</script>

<style lang="scss" scoped>
.footer {
  height: 234px;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  color: $colorL;
  font-size: $fontI;
  text-align: center;
  .footer-logo {
    margin-top: 46px;
    margin-bottom: 31px;
    img {
      width: 53px;
      height: 36px;
      margin-bottom: 13px;
    }
    p {
    }
  }
  .footer-link {
    margin-bottom: 13px;
    a {
      position: relative;
      color: $colorL;
      margin-right: 8px;
      display: inline-block;
      padding-right: 8px;
      color: $colorA;

      &:before {
        position: absolute;
        right: 0;
        top: 7px;
        width: 1px;
        height: 10px;
        content: "";
        border-right: 1px solid $colorF;
      }

      &:last-child:before {
        border: none;
      }
    }
  }
}
</style>
