<template>
  <!-- <div slot="">

    <div class="card" style="line-height: 30px ">
      <div><span style="color: dodgerblue;">{{ user.name }} </span>祝您今天过得开心！</div>
    </div>
    <div class="card" style="margin-top: 30px;">
      <h1>今日公告</h1>
      <p>暂无</p>
    </div>
  </div> -->
  <div>
    <el-carousel :interval="4000" type="card" height="250px">
      <el-carousel-item v-for="item in urls" :key="item">
        <el-image :src="item" fit="cover"></el-image>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref, shallowRef, onMounted} from 'vue'
import {ElCarousel, ElCarouselItem, ElImage} from 'element-plus'

const user = JSON.parse(localStorage.getItem('user') || '{}')

const urls = [

  'https://szk-data.oss-cn-beijing.aliyuncs.com/imgs/202312271114432.jpeg',
  'https://szk-data.oss-cn-beijing.aliyuncs.com/imgs/202312271114084.jpg',
  'https://szk-data.oss-cn-beijing.aliyuncs.com/imgs/202312271114542.jpg',
  'https://static.yfsp.tv/upload/user/202311091306570663403.jpg'

]

</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__container {
  height: 600px;
}

.el-image {
  width: 100%;
  height: 100%;
}
</style>