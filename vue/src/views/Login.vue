<script setup>
import {reactive, ref} from "vue" // 引入reactive和ref
import request from "@/utils/request"; // 引入封装的axios
import {ElMessage} from "element-plus"; // 引入element-plus的消息提示

// 响应式数据
const data = reactive({
  form: {
    username: '',
    password: '',
    role: 'ADMIN'
  }
})

const formRef = ref('')

// 登录功能实现
const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/login', data.form).then(res => {

        if (res.code === '200') {   //登录成功
          localStorage.setItem('user', JSON.stringify(res.data)) // 将用户信息存储到localStorage
          ElMessage.success('登录成功')
          location.href = '/home'  // 跳转到主页
        } else {//登录失败
          ElMessage.error(res.msg) // 提示错误信息
        }
      })
    }
  })
}


// 表单校验
const rules = reactive({
  username: [
    {required: true, message: '请输入账号', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
  ],
})

</script>

<template>
  <div class="login-container">
    <div class="login-box">
      <el-container>
        <el-header>
          <div style="font-weight: bold;font-size: 50px; text-align: center; margin-bottom: 20px;">登录</div>
        </el-header>
        <el-divider/>
        <el-container>
          <el-main>
            <el-form :model="data.form" ref="formRef" :rules="rules">
              <!-- 身份选择 -->
              <el-form-item prop="role">
                <el-select style="width: 100%" v-model="data.form.role">
                  <el-option value="ADMIN" label="管理员"></el-option>
                  <el-option value="WORKER" label="员工"></el-option>
                </el-select>
              </el-form-item>
              <!-- <el-form-item prop="role">
                <el-radio-group v-model="data.form.role" style="width: 100%">
                  <el-radio value="ADMIN" label="管理员"></el-radio>
                  <el-radio value="WORKER" label="员工"></el-radio>
                </el-radio-group>
              </el-form-item> -->

              <el-form-item prop="username" label="账号">
                <el-input prefix-icon="user" v-model="data.form.username" placeholder="请输入账号"/>
              </el-form-item>
              <el-form-item prop="password" label="密码">
                <el-input prefix-icon="lock" v-model="data.form.password" placeholder="请输入密码"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="width:100%" @click="login">登录</el-button>
              </el-form-item>
            </el-form>
          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url(@/assets/imgs/film.jpg);
  background-size: cover;
}

.login-box {
  background-color: rgba(255, 255, 255, 0.8);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
  width: auto;
  border-radius: 5px;
}
</style>