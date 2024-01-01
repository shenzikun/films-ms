<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input v-model="data.username" style="width: 300px; margin-right: 10px" placeholder="请输入员工工号查询"
                prefix-icon="Search"></el-input>
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入员工姓名查询"
                prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="data.tableData" style="width:100%">
        <!-- <el-table-column label="序号" prop="id" width="70" /> -->
        <el-table-column label="头像" prop="avatar">
          <template #default="scope">
            <el-image v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"
                      style="width: 40px; height: 40px; border-radius: 5px"></el-image>
          </template>
        </el-table-column>

        <el-table-column label="工号" prop="username"/>
        <el-table-column label="员工姓名" prop="name"/>
        <el-table-column label="性别" prop="sex"/>
        <el-table-column label="生日" prop="birth"/>
        <el-table-column label="手机号" prop="phone"/>
        <el-table-column label="电子邮件" prop="email"/>
        <el-table-column>
          <template #default="scope">
            <el-button type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" plain @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="card">
      <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     @current-change="handelCurrentChange"
                     background layout="prev, pager, next" :total="data.total"/>
    </div>


    <!-- 用dialog弹窗显示修改的表单 -->
    <el-dialog width="35%" v-model="data.formVisible" title="员工信息" destroy-on-close>

      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right: 40px">

        <el-form-item label="员工工号">
          <el-input v-model="data.form.username" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="员工密码">
          <el-input v-model="data.form.password" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="员工姓名">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="性别">
          <el-radio-group v-model="data.form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="生日">
          <!-- <el-data-picker style="width: 100%;" format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="data.form.birth"></el-data-picker> -->
          <el-date-picker
              v-model="data.form.birth"
              type="date"
              placeholder="选择你的生日"

          />
        </el-form-item>

        <el-form-item label="手机号">
          <el-input v-model="data.form.phone" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="data.form.email" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="上传头像">
          <el-upload action="http://localhost:9090/files/upload" list-type="picture"
                     :on-success="handleImgUploadSuccess">
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </span>
      </template>

    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";
import {Search} from "@element-plus/icons-vue";

request.get('/').then(res => {
  console.log(res)
})

const data = reactive({
  username: '',
  name: '',
  tableData: [],
  total: 0,
  pageNum: 1, //当前页
  pageSize: 5, //每页个数
  formVisible: false,
  form: {}
})

const handleImgUploadSuccess = (res) => {
  data.form.avatar = res.data
}

// 获取后台数据
const load = () => {
  request.get('/employee/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.username,
      name: data.name,
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

// 调用方法获取后台数据 
load()

//增加部门方法
const handleAdd = () => {
  //清楚上一次数据
  data.form = {}
  data.formVisible = true
}

//编辑部门
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row)) //深拷贝到forme的数据中去
  data.formVisible = true //打开弹窗
}

//删除部门
const handleDelete = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？', '删除确认', {type: 'warning'}).then(res => {
    request.delete('/employee/delete/' + id).then(res => {
      if (res.code === '200') {
        load()    // 重新获取数据
        ElMessage.success("操作成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(res => {
    ElMessage({
      type: 'info',
      message: '取消删除'
    })
  })
}


const handelCurrentChange = () => {

  // 当翻页的时候重新加载数据即可 
  load()
}

//重置方法
const reset = () => {
  data.username = ''
  data.name = ''
  load()
}

// 保存数据到后台
const save = () => {
  request.request({
    url: data.form.id ? '/employee/update' : '/employee/add',
    method: data.form.id ? 'PUT' : 'POST',
    data: data.form
  }).then(res => {
    if (res.code === '200') {
      load()    // 重新获取数据
      data.formVisible = false  // 关闭弹窗
      ElMessage.success("操作成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>