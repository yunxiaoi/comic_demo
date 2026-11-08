<template>
  <div>
    <el-table :data="list" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="content" label="内容" min-width="300" show-overflow-tooltip />
      <el-table-column label="类型" width="80">
        <template #default="{ row }">{{ row.targetType === 1 ? '漫画' : '帖子' }}</template>
      </el-table-column>
      <el-table-column prop="targetTitle" label="对象" min-width="150" />
      <el-table-column prop="username" label="用户" width="120" />
      <el-table-column prop="createTime" label="时间" width="160" />
      <el-table-column label="操作" width="80">
        <template #default="{ row }">
          <el-button link type="danger" @click="deleteComment(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="page"
        :page-size="size"
        :total="total"
        layout="prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCommentsAdmin, deleteCommentAdmin } from '@/api/admin'
import dayjs from 'dayjs'

const list = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

const loadData = async () => {
  const res = await getCommentsAdmin({ page: page.value, size: size.value })
  list.value = (res.data.list || []).map(c => ({
    ...c,
    createTime: dayjs(c.createTime).format('YYYY-MM-DD HH:mm')
  }))
  total.value = res.data.total
}

const deleteComment = (id) => {
  ElMessageBox.confirm('确定删除吗？', '提示', {type: 'warning'}).then(async () => {
    await deleteCommentAdmin(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {
  })
}

onMounted(loadData)
</script>
