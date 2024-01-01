<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入货物名称查询"
                prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="data.tableData" style="width:100%">
        <el-table-column label="货物名称" prop="name"/>
        <el-table-column label="剩余数量" prop="number"/>
        <el-table-column>
          <template #default="scope">
            <el-button type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" plain @click="handleDelete(scope.row.id) " v-if="user.role === 'ADMIN'">删除
            </el-button>
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
    <el-dialog width="35%" v-model="data.formVisible" title="货物信息" destroy-on-close>

      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right: 40px">

        <el-form-item label="货物名称" v-if="user.role === 'ADMIN'">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="货物数量">
          <el-input v-model="data.form.number" autocomplete="off"/>
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

request.get('/inventory').then(res => {
  console.log(res)
})

const data = reactive({
  name: '',
  number: '',
  tableData: [],
  total: 0,
  pageNum: 1, //当前页
  pageSize: 5, //每页个数
  formVisible: false,
  form: {}
})

// 获取后台数据
const load = () => {
  request.get('/inventory/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      number: data.number
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

// 调用方法获取后台数据 
load()

//增加货物方法
const handleAdd = () => {
  //清理上一次数据
  data.form = {}
  data.formVisible = true
}

//编辑货物
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row)) //深拷贝到forme的数据中去
  data.formVisible = true //打开弹窗
}

//删除货物
const handleDelete = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？', '删除确认', {type: 'warning'}).then(res => {
    request.delete('/inventory/delete/' + id).then(res => {
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

const user = JSON.parse(localStorage.getItem('user') || '{}')

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
    url: data.form.id ? '/inventory/update' : '/inventory/add',
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