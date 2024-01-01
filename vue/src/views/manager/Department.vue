<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px" placeholder="请输入关键字查询"
                prefix-icon="Search"></el-input>
      <el-input v-model="data.no" style="width: 300px; margin-right: 10px" placeholder="请输入部门编号查询"
                prefix-icon="Search"></el-input>
      <el-input v-model="data.leader" style="width: 300px; margin-right: 10px" placeholder="请输入部门管理员查询"
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
        <el-table-column label="部门名称" prop="name"/>
        <el-table-column label="部门编号" prop="no"/>
        <el-table-column label="部门描述" prop="descr"/>
        <el-table-column label="工作时间" prop="times"/>
        <el-table-column label="部门领导" prop="leader"/>
        <el-table-column label="操作" align="center" width="160"/>
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
    <el-dialog width="35%" v-model="data.formVisible" title="课程信息">
      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right: 40px">
        <el-form-item label="部门名称">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="部门编号">
          <el-input v-model="data.form.no" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="部门描述">
          <el-input v-model="data.form.descr" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="工时">
          <el-input v-model="data.form.times" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="部门领导">
          <el-input v-model="data.form.leader" autocomplete="off"/>
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
  name: '',
  no: '',
  leader: '',
  tableData: [],
  total: 0,
  pageNum: 1, //当前页
  pageSize: 5, //每页个数
  formVisible: false,
  form: {}
})

// 获取后台数据
const load = () => {
  request.get('/department/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      no: data.no,
      leader: data.leader
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
    request.delete('/department/delete/' + id).then(res => {
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
  data.name = ''
  data.no = ''
  data.leader = ''
  load()
}

// 保存数据到后台
const save = () => {
  request.request({
    url: data.form.id ? '/department/update' : '/department/add',
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