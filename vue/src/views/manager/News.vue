<template>
  <div class="card">
    <el-container>
      <el-header>
        <div style="display: flex; justify-content: space-between;margin: 10px;">
          <!-- logo -->
          <span style="flex-grow: 1;padding-left: 20px; display: flex; align-items: center">
            <img src="@/assets/imgs/news.png" alt="" style="width: 30px">
            <div style="font-weight: bold; font-size: 20px; margin-left: 10px;margin-bottom: 3px;">观闻天下</div>
          </span>
          <!-- 搜索内容 -->
          <span style="flex-grow: 1; text-align: center;">
            <!-- 搜索框 -->
            <el-input v-model="data.title" style="width: 300px; " placeholder="请输入新闻标题" prefix-icon="Search"
                      clearable></el-input>
            <el-button type="primary" @click="load">搜索</el-button>
            <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
          </span>
          <!-- 新闻发布按钮 -->
          <span style="flex-grow: 1; text-align: right;">
            <el-button type="primary" @click="handleAdd">发布</el-button>
          </span>
        </div>
      </el-header>

      <el-main>
        <!-- 轮播图 -->
        <div style="margin: 20px;">
          <el-carousel :interval="4000" height="250px">
            <el-carousel-item v-for="(item, index) in data.tableData" :key="index">
              <img :src="item.url" alt="Carousel image" class="carousel-image">
            </el-carousel-item>
          </el-carousel>
        </div>

        <el-backtop :right="100" :bottom="100"/>
        <!-- 新闻列表 -->
        <div class="news-container">
          <el-row v-for="(item, index) in data.tableData" :key="index" class="card" style="margin: 10px;" :gutter="20"
                  @click="handleAdd()">
            <!-- 新闻图片，占据三分之一的宽度 -->
            <el-col :span="8">
              <div class="image-container">
                <el-image :src="item.url" alt="News Image" fit="cover"></el-image>
              </div>
            </el-col>
            <!-- 新闻内容，占据三分之二的宽度 -->
            <el-col :span="16">
              <div class="news-content">
                <h1 class="news-title">{{ item.title }}</h1>
                <p>{{ item.publisher }}</p>
                <p class="news-meta">{{ item.date }}</p>
              </div>
            </el-col>
          </el-row>
        </div>
        <!-- 用dialog弹窗显示内容 -->

      </el-main>
      <el-footer>
        <!-- 分页 -->
        <div class="card">
          <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                         @current-change="handelCurrentChange" background layout="prev, pager, next"
                         :total="data.total"/>
        </div>
      </el-footer>

    </el-container>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from 'vue'
import {ElMessageBox, ElMessage} from "element-plus";


request.get('/').then(res => {
  console.log(res)
})

const data = reactive({
  title: '',
  article: '',
  tableData: [],
  total: 0,
  pageNum: 1,//当前页
  pageSize: 4,//每页个数
  formVisible: false,
  form: {}
})

//获取后台数据
const load = () => {
  request.get('/news/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title,
      article: data.article,
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

// 调用方法获取后台数据 
load()

//重置方法
const reset = () => {
  data.title = ''
  load()
}

const handelCurrentChange = () => {
  // 当翻页的时候重新加载数据即可 
  load()
}

//发布新闻方法
const handleAdd = () => {
  //清楚上一次数据
  data.form = {}
  data.formVisible = true
}
</script>

<style scoped>
.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.carousel-image {
  width: 100%;
  height: 300px;
  object-fit: contain;
  /* 更新这里 */
}

.news-row {
  margin-bottom: 20px;
}

.news-card {
  display: flex;
  align-items: center;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.12);
}

.news-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.news-info {
  padding-left: 20px;
  /* 添加一些内边距 */
}

.news-title {
  margin-bottom: 10px;
  font-size: 18px;
  font-weight: bold;
}

.news-date,
.news-publisher {
  margin-bottom: 5px;
  color: #666;
  font-size: 14px;
}</style>