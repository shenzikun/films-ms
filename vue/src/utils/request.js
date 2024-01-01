// 引入element-plus的消息提示组件，用于显示提示信息
import {ElMessage} from 'element-plus'
// 引入Vue路由器，用于页面间的导航
import router from '../router'
// 引入axios库，用于发送HTTP请求
import axios from "axios";

// 使用axios创建一个新的HTTP请求实例
// 这个实例将用于发送所有HTTP请求
const request = axios.create({
    // 设置基础URL，所有请求都会基于这个URL
    // 这个值来自环境变量，通常在项目的配置文件中设置
    baseURL: import.meta.env.VITE_BASE_URL,

    // 设置请求超时时间为30000毫秒（30秒）
    // 如果请求超过这个时间还未得到响应，将被中断
    timeout: 30000
})

// 添加请求拦截器
// 拦截器允许在请求发送到服务器之前对请求数据进行处理
request.interceptors.request.use(config => {
    // 设置请求头的内容类型为JSON，字符集为utf-8
    // 这确保发送到服务器的数据是JSON格式
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    return config
}, error => {
    // 如果请求配置过程中发生错误，返回一个拒绝的promise
    return Promise.reject(error)
});

// 添加响应拦截器
// 拦截器允许在服务器响应数据返回到客户端之前处理数据
request.interceptors.response.use(
    response => {
        // 获取响应数据
        let res = response.data;

        // 如果响应的数据类型是'blob'（通常用于文件下载），直接返回数据
        if (response.config.responseType === 'blob') {
            return res
        }

        // 如果响应数据是字符串类型，尝试将其解析为JSON对象
        // 这是为了兼容某些服务端返回JSON字符串的情况
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }

        // 如果响应数据中的code是'401'（未授权），显示错误消息
        // 并将用户导航到登录页面
        if (res.code === '401') {
            ElMessage.error(res.msg);
            router.push("/login")
        }

        // 返回处理后的响应数据
        return res;
    },
    error => {
        // 如果响应过程中发生错误，打印错误信息并返回一个拒绝的promise
        console.log('err' + error)
        return Promise.reject(error)
    }
)

// 导出配置好的axios实例
// 这样在其他文件中可以直接使用这个实例来发送HTTP请求
export default request
