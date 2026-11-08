<template>
  <div>
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增章节</el-button>
      <el-button @click="goBack">返回漫画列表</el-button>
    </div>
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column prop="chapterNo" label="章节号" width="100" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="updateTime" label="更新时间" width="160" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑章节' : '新增章节'" width="800px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="章节号" prop="chapterNo">
          <el-input-number v-model="form.chapterNo" :min="1" />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="图片列表">
          <div class="image-list">
            <div v-for="(img, idx) in imageUrls" :key="idx" class="image-item">
              <img :src="img" />
              <el-button size="small" type="danger" @click="removeImage(idx)">删除</el-button>
            </div>
          </div>
          <el-upload
              action="#"
              :auto-upload="false"
              :on-change="handleImageUpload"
              multiple
              list-type="picture"
          >
            <el-button size="small">上传图片</el-button>
          </el-upload>
          <div class="hint">建议图片宽度不超过800px，支持批量上传</div>
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
import { ref, onMounted, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getChaptersByComic, saveChapter, deleteChapter, uploadChapterImage } from '@/api/admin'
import draggable from 'vuedraggable'

const route = useRoute()
const router = useRouter()
const comicId = parseInt(route.params.comicId)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const submitting = ref(false)
const form = ref({ id: null, comicId, chapterNo: 1, title: '', content: '' })
const imageUrls = ref([])

const rules = {
  chapterNo: [{ required: true, message: '请输入章节号', trigger: 'blur' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
}

const loadData = async () => {
  const res = await getChaptersByComic(comicId)
  list.value = res.data
}

const handleAdd = () => {
  const maxNo = Math.max(...list.value.map(c => c.chapterNo), 0)
  form.value = { id: null, comicId, chapterNo: maxNo + 1, title: '', content: '' }
  imageUrls.value = []
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  form.value = { ...row }
  if (row.content) {
    try {
      imageUrls.value = JSON.parse(row.content)
    } catch {
      imageUrls.value = []
    }
  } else {
    imageUrls.value = []
  }
  dialogVisible.value = true
}

const handleImageUpload = async (file, fileList) => {
  // 获取当前所有未上传的文件
  const files = fileList.map(f => f.raw)
  // 并发上传但保持结果顺序
  const uploadPromises = files.map(file => {
    const formData = new FormData()
    formData.append('file', file)
    return uploadChapterImage(formData)
  })
  const results = await Promise.all(uploadPromises)
  imageUrls.value = results.map(res => res.data)
}

const removeImage = (idx) => {
  imageUrls.value.splice(idx, 1)
}

const submitForm = async () => {
  await formRef.value.validate()
  form.value.content = JSON.stringify(imageUrls.value)
  submitting.value = true
  try {
    await saveChapter(form.value)
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
    await deleteChapter(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

const goBack = () => {
  router.push('/admin/comic-list')
}

onMounted(loadData)
</script>

<style scoped>
.header {
  display: flex;
  gap: 10px;
}
.image-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 10px;
}
.image-item {
  position: relative;
  width: 150px;
  border: 1px solid #ddd;
  padding: 5px;
  border-radius: 4px;
}
.image-item img {
  width: 100%;
  height: auto;
}
.image-item .el-button {
  margin-top: 5px;
  width: 100%;
}
.hint {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}
</style>