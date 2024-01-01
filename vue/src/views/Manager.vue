<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <div style="flex: 1; padding-top: 20px">
          <div style="padding-left: 20px; display: flex; align-items: center">
            <img src="@/assets/imgs/ticket.png" alt="" style="width: 40px">
            <div style="font-weight: bold; font-size: 20px; margin-left: 5px">电影管理系统</div>
          </div>
        </div>
        <el-divider content-position="center">导航栏</el-divider>
        <div style="display: flex">
          <div
              style="width: 200px; background-color: #ffffff; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
            <el-menu router style="border: none" :default-active="$route.path" :default-openeds="['/home', '2']">
              <el-menu-item index="/home">
                <el-icon>
                  <House/>
                </el-icon>
                <span>首页</span>
              </el-menu-item>
              <el-menu-item index="/home">
                <el-icon>
                  <Medal/>
                </el-icon>
                <span>热门影片</span>
              </el-menu-item>

              <!-- <el-menu-item index="/inventory">
                <el-icon>
                  <Medal />
                </el-icon>
                <span>热门影片</span>
              </el-menu-item> -->

              <el-menu-item index="/WORKER" v-if="user.role === 'ADMIN'">
                <el-icon>
                  <Film/>
                </el-icon>
                <span>影片管理</span>
              </el-menu-item>

              <el-menu-item index="/news" v-if="user.role === 'ADMIN'">
                <el-icon>
                  <Document/>
                </el-icon>
                <span>新闻管理</span>
              </el-menu-item>

              <el-menu-item index="/person" v-if="user.role === 'WORKER'">
                <el-icon>
                  <User/>
                </el-icon>
                <span>个人资料</span>
              </el-menu-item>
              <el-menu-item index="login" @click="logout">
                <el-icon>
                  <SwitchButton/>
                </el-icon>
                <span>退出系统</span>
              </el-menu-item>
            </el-menu>
          </div>
        </div>
      </el-aside>
      <el-container>
        <el-header>
          <div
              style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
            <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
              <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt=""
                   style="width: 40px; height: 40px; border-radius: 50%;">
              <span style="margin-left: 5px">{{ user.name }}</span>
            </div>
          </div>
        </el-header>
        <el-main>
          <div style="flex: 1; width: auto; background-color: #f8f8ff; padding: 10px">
            <router-view/>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script setup>
import {useRoute} from 'vue-router'

const $route = useRoute()
console.log($route.path)
const user = JSON.parse(localStorage.getItem('user') || '{}')

const logout = () => {
  localStorage.removeItem('user')
}
</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #dcede9 !important;
}

.el-menu-item:hover {
  color: #11A983;
}

:deep(th) {
  color: #333;
}</style>