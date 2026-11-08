<template>
  <div>
    <el-button type="primary" @click="handleAdd">新增轮播图</el-button>
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column label="图片" width="100">
        <template #default="{ row }">
          <img :src="row.image" style="width: 80px; height: 50px; object-fit: cover" />
        </template>
      </el-table-column>
      <el-table-column prop="link" label="链接" min-width="200" />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑轮播图' : '新增轮播图'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="图片">
          <el-upload
              action="#"
              :auto-upload="false"
              :on-change="handleImageChange"
              :limit="1"
              list-type="picture"
          >
            <el-button size="small">上传图片</el-button>
          </el-upload>
          <div v-if="form.image" class="image-preview">
            <img :src="form.image" style="width: 100px" />
          </div>
        </el-form-item>
        <el-form-item label="链接" prop="link">
          <el-input v-model="form.link" placeholder="跳转链接" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
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
import { getCarouselsAdmin, saveCarousel, deleteCarousel, uploadCarouselImage } from '@/api/admin'

const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const submitting = ref(false)
const form = ref({ id: null, image: '', link: '', sort: 0, status: 1 })

const rules = {
  link: [{ required: true, message: '请输入链接', trigger: 'blur' }]
}

const loadData = async () => {
  const res = await getCarouselsAdmin()
  list.value = res.data
}

const handleAdd = () => {
  form.value = { id: null, image: '', link: '', sort: 0, status: 1 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.value = { ...row }
  dialogVisible.value = true
}

const handleImageChange = async (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  const res = await uploadCarouselImage(formData)
  form.value.image = res.data
}

const submitForm = async () => {
  await formRef.value.validate()
  if (!form.value.image) {
    ElMessage.warning('请上传图片')
    return
  }
  submitting.value = true
  try {
    await saveCarousel(form.value)
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
    await deleteCarousel(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>

<style scoped>
.image-preview {
  margin-top: 10px;
}
</style>