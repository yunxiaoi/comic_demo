<template>
  <div>
    <el-button type="primary" @click="handleAdd">新增漫画</el-button>
    <el-input v-model="keyword" placeholder="搜索漫画" style="width: 200px; margin-left: 10px" @clear="loadData" clearable />
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column label="封面" width="80">
        <template #default="{ row }">
          <img :src="row.cover" style="width: 50px; height: 70px; object-fit: cover" />
        </template>
      </el-table-column>
      <el-table-column prop="title" label="名称" min-width="150" />
      <el-table-column prop="author" label="作者" width="120" />
      <el-table-column label="题材" width="100">
        <template #default="{ row }">{{ getGenreName(row.genreId) }}</template>
      </el-table-column>
      <el-table-column label="地区" width="80">
        <template #default="{ row }">{{ getRegionName(row.regionId) }}</template>
      </el-table-column>
      <el-table-column label="状态" width="80">
        <template #default="{ row }">{{ row.status === 0 ? '连载中' : '已完结' }}</template>
      </el-table-column>
      <el-table-column label="收费" width="80">
        <template #default="{ row }">{{ row.isPaid === 1 ? '付费' : '免费' }}</template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="primary" @click="goToChapters(row.id)">章节</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑漫画' : '新增漫画'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="名称" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" />
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
        <el-form-item label="题材" prop="genreId">
          <el-select v-model="form.genreId" placeholder="请选择">
            <el-option v-for="g in genres" :key="g.id" :label="g.name" :value="g.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="地区" prop="regionId">
          <el-select v-model="form.regionId" placeholder="请选择">
            <el-option v-for="r in regions" :key="r.id" :label="r.name" :value="r.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">连载中</el-radio>
            <el-radio :label="1">已完结</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="收费" prop="isPaid">
          <el-radio-group v-model="form.isPaid">
            <el-radio :label="0">免费</el-radio>
            <el-radio :label="1">付费</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="价格" v-if="form.isPaid === 1" prop="price">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input v-model="form.description" type="textarea" rows="4" />
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getComicList, getComicById, saveComic, deleteComic, uploadCover } from '@/api/admin'
import { getGenres, getRegions } from '@/api/admin'

const router = useRouter()
const list = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const keyword = ref('')
const genres = ref([])
const regions = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const submitting = ref(false)
const form = ref({
  id: null,
  title: '',
  author: '',
  cover: '',
  genreId: null,
  regionId: null,
  status: 0,
  isPaid: 0,
  price: 0,
  description: ''
})

const rules = {
  title: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  genreId: [{ required: true, message: '请选择题材', trigger: 'change' }],
  regionId: [{ required: true, message: '请选择地区', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }]
}

const getGenreName = (id) => {
  const g = genres.value.find(g => g.id === id)
  return g ? g.name : ''
}
const getRegionName = (id) => {
  const r = regions.value.find(r => r.id === id)
  return r ? r.name : ''
}

const loadData = async () => {
  const res = await getComicList({ page: page.value, size: size.value, keyword: keyword.value })
  list.value = res.data.list
  total.value = res.data.total
}

const handleAdd = () => {
  form.value = {
    id: null,
    title: '',
    author: '',
    cover: '',
    genreId: null,
    regionId: null,
    status: 0,
    isPaid: 0,
    price: 0,
    description: ''
  }
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  const res = await getComicById(row.id)
  form.value = res.data
  dialogVisible.value = true
}

const handleCoverChange = async (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  const res = await uploadCover(formData)
  form.value.cover = res.data
}

const submitForm = async () => {
  await formRef.value.validate()
  submitting.value = true
  try {
    await saveComic(form.value)
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
    await deleteComic(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

const goToChapters = (comicId) => {
  router.push(`/admin/comic-chapters/${comicId}`)
}

onMounted(async () => {
  const [genresRes, regionsRes] = await Promise.all([getGenres(), getRegions()])
  genres.value = genresRes.data
  regions.value = regionsRes.data
  loadData()
})
</script>

<style scoped>
.cover-preview {
  margin-top: 10px;
}
</style>