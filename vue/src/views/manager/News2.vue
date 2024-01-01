<template>
  <div class="card" style="margin-bottom: 10px;">
    <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入关键字查询"
              prefix-icon="Search"></el-input>
    <el-button type="primary" @click="load">查询</el-button>
    <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
  </div>
  <div class="news-management">
    <!-- 搜索栏 -->
    <el-row class="search-bar">
      <el-col :span="16" :offset="4">
        <el-input
            v-model="searchTerm"
            placeholder="搜索新闻标题或内容..."
            class="search-input"
            @keyup.enter="fetchNewsList(1)"
        >
          <el-button slot="append" icon="el-icon-search" @click="fetchNewsList(1)">搜索</el-button>
        </el-input>
      </el-col>
    </el-row>

    <!-- 轮播图组件，展示重要新闻 -->
    <el-carousel interval="5000" height="200px" class="news-carousel">
      <el-carousel-item v-for="item in importantNews" :key="item.id">
        <h3>{{ item.title }}</h3>
      </el-carousel-item>
    </el-carousel>

    <!-- 新闻列表 -->
    <el-table :data="newsList" style="width: 100%" class="news-table">
      <!-- 表格列的定义 -->
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
        @current-change="fetchNewsList"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="totalNews"
        class="pagination"
    >
    </el-pagination>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';

// 定义响应式变量
const searchTerm = ref('');
const newsList = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const totalNews = ref(0);

// 获取新闻列表的方法
const fetchNewsList = (page) => {
  axios.get('/api/news/list', {
    params: {
      page: page,
      size: pageSize.value,
      search: searchTerm.value,
    }
  }).then(response => {
    newsList.value = response.data.records;
    totalNews.value = response.data.total;
  }).catch(error => {
    console.error("Error fetching news list:", error);
  });
  currentPage.value = page;
};

// 查看新闻详情的方法
const viewNewsDetail = (newsId) => {
  // 逻辑以导航到新闻详情页
};

// 组件挂载时自动获取第一页的新闻列表
onMounted(() => {
  fetchNewsList(1);
});
</script>

<style scoped>
.news-management {
  margin: 20px;
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 10px;
}

.search-bar {
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
}

.news-carousel {
  margin-bottom: 20px;
}

.news-table {
  margin-bottom: 20px;
}

.pagination {
  text-align: center;
}
</style>