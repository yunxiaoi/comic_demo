<template>
  <div>
    <el-button type="primary" @click="handleAdd">新增资讯</el-button>
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column prop="views" label="浏览" width="80" />
      <el-table-column prop="createTime" label="创建时间" width="160" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑资讯' : '新增资讯'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="封面">
          <el-upload
              action="#"
              :auto-upload="false"
              :on-change="handleCoverChange"
              :limit="1"
              list-type="picture"
          >
            <el-button size="small">上传封面</el-button>
          </el-upload>
          <div v-if="form.cover" class="cover-preview">
            <img :src="form.cover" style="width: 100px" />
          </div>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" rows="8" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitting">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getNewsListAdmin, saveNews, deleteNews, uploadCarouselImage } from '@/api/admin'

const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const submitting = ref(false)
const form = ref({ id: null, title: '', cover: '', content: '' })

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const loadData = async () => {
  const res = await getNewsListAdmin()
  list.value = res.data
}

const handleAdd = () => {
  form.value = { id: null, title: '', cover: '', content: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.value = { ...row }
  dialogVisible.value = true
}

const handleCoverChange = async (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  const res = await uploadCarouselImage(formData)
  form.value.cover = res.data
}

const submitForm = async () => {
  await formRef.value.validate()
  submitting.value = true
  try {
    await saveNews(form.value)
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    submitting.value = false
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteNews(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>

<style scoped>
.cover-preview {
  margin-top: 10px;
}
</style>