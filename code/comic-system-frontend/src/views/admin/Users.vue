<template>
  <div>
    <el-table :data="list" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="balance" label="余额" width="100" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '正常' : '封禁' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button v-if="row.status === 1" link type="danger" @click="handleBanUser(row.id)">封禁</el-button>
          <el-button v-else link type="success" @click="handleUnbanUser(row.id)">解封</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUsers, banUser, unbanUser } from '@/api/admin'

const list = ref([])

const loadData = async () => {
  const res = await getUsers()
  list.value = res.data
}

const handleBanUser = (id) => {
  ElMessageBox.confirm('确定封禁该用户吗？', '提示', { type: 'warning' }).then(async () => {
    await banUser(id)
    ElMessage.success('已封禁')
    loadData()
  }).catch(() => {})
}

const handleUnbanUser = (id) => {
  ElMessageBox.confirm('确定解封该用户吗？', '提示', { type: 'info' }).then(async () => {
    await unbanUser(id)
    ElMessage.success('已解封')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>