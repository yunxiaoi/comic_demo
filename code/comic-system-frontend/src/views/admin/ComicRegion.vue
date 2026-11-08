<template>
  <div>
    <el-button type="primary" @click="handleAdd">新增地区</el-button>
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="地区名称" />
      <el-table-column prop="createTime" label="创建时间" width="160" />
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑地区' : '新增地区'" width="400px">
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" />
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
import { getRegions, saveRegion, deleteRegion } from '@/api/admin'

const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const submitting = ref(false)
const form = ref({ id: null, name: '' })

const rules = {
  name: [{ required: true, message: '请输入地区名称', trigger: 'blur' }]
}

const loadData = async () => {
  const res = await getRegions()
  list.value = res.data
}

const handleAdd = () => {
  form.value = { id: null, name: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  submitting.value = true
  try {
    await saveRegion(form.value)
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
    await deleteRegion(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(loadData)
</script>