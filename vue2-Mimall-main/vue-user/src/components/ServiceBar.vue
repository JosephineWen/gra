<template>
  <div class="service">
    <div class="container">
      <ul>
        <li><span class="icon-setting"></span>estimated time of delivery</li>
        <li><span class="icon-7day"></span>7 days no reason to return</li>
        <li><span class="icon-15day"></span>15 days free exchange</li>
        <li><span class="icon-post"></span>Over 150 is free</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "service-bar",
};
</script>

<style lang="scss">
.service {
  padding: 33px 0;
  color: $colorC;
  font-size: $fontI;
  text-align: center;

  li {
    display: inline-block;
    width: 24.8%;
    text-align: center;
    border-right: 1px solid $colorH;

    &:last-child {
      border-right: none;
    }
    span {
      vertical-align: middle;
      margin-right: 8px;
    }

    .icon-setting {
      @include bgImg(20px, 20px, "/public/imgs/icon-setting.png");
    }
    .icon-7day {
      @include bgImg(20px, 20px, "/public/imgs/icon-7day.png");
    }
    .icon-15day {
      @include bgImg(20px, 20px, "/public/imgs/icon-15day.png");
    }
    .icon-post {
      @include bgImg(20px, 20px, "/public/imgs/icon-post.png");
    }
  }
}
</style>
