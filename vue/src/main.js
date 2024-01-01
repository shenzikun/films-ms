import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from 'axios'  // 确保导入 axios

import '@/assets/css/global.css'

const app = createApp(App)

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})

// 全局注册 ElementPlus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 全局注册 axios
app.config.globalProperties.$axios = axios

app.mount('#app')
