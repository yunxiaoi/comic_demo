<template>
  <div>
    <el-radio-group v-model="status" @change="loadData">
      <el-radio-button :label="null">全部</el-radio-button>
      <el-radio-button :label="0">待审核</el-radio-button>
      <el-radio-button :label="1">已通过</el-radio-button>
      <el-radio-button :label="2">未通过</el-radio-button>
    </el-radio-group>
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column prop="username" label="作者" width="120" />
      <el-table-column prop="categoryName" label="分类" width="100" />
      <el-table-column prop="views" label="浏览" width="80" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : row.status === 0 ? 'warning' : 'danger'">
            {{ row.status === 1 ? '已通过' : row.status === 0 ? '待审核' : '未通过' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="viewDetail(row.id)">查看</el-button>
          <el-button v-if="row.status === 0" link type="success" @click="audit(row.id, 1)">通过</el-button>
          <el-button v-if="row.status === 0" link type="danger" @click="audit(row.id, 2)">驳回</el-button>
          <el-button link type="danger" @click="deletePost(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getForumPosts, auditPost, deleteForumPost } from '@/api/admin'

const router = useRouter()
const list = ref([])
const status = ref(null)

const loadData = async () => {
  const res = await getForumPosts(status.value)
  list.value = res.data
}

const viewDetail = (id) => {
  router.push(`/post/${id}`)
}

const audit = async (id, status) => {
  await auditPost(id, status)
  ElMessage.success(status === 1 ? '审核通过' : '已驳回')
  loadData()
}

const deletePost = (id) => {
  ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteForumPost(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>