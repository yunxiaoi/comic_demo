<template>
  <div class="my-posts">
    <div class="container">
      <h1>我的帖子</h1>
      <el-button type="primary" @click="showCreateDialog" style="margin-bottom: 20px">发布新帖</el-button>

      <el-table :data="posts" stripe>
        <el-table-column prop="title" label="标题" min-width="200">
          <template #default="{ row }">
            <router-link :to="`/post/${row.id}`" class="post-title">{{ row.title }}</router-link>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="views" label="浏览" width="80" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : row.status === 0 ? 'warning' : 'danger'">
              {{ row.status === 1 ? '已通过' : row.status === 0 ? '待审核' : '未通过' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="editPost(row)" :disabled="row.status !== 0">编辑</el-button>
            <el-button link type="danger" @click="handleDeletePost(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" :title="editingPost ? '编辑帖子' : '发布帖子'" width="600px">
      <el-form :model="postForm" :rules="postRules" ref="postFormRef">
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="postForm.categoryId" placeholder="请选择分类">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="postForm.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
              v-model="postForm.content"
              type="textarea"
              :rows="6"
              placeholder="请输入内容，支持HTML标签，图片会以 <img> 形式插入"
          />
          <!-- 图片上传按钮 -->
          <div style="margin-top: 10px;">
            <el-upload
                action="#"
                :auto-upload="false"
                :show-file-list="false"
                :on-change="handleImageUpload"
                accept="image/*"
            >
              <el-button size="small" type="primary">上传图片</el-button>
            </el-upload>
            <span style="margin-left: 10px; font-size: 12px; color: #999;">上传后将自动插入图片标签到内容末尾</span>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPost" :loading="submitLoading">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyPosts, createPost, updatePost, deletePost } from '@/api/forum'
import { getPostCategories } from '@/api/common'
import { uploadImage } from '@/api/user'   // 新增导入
import dayjs from 'dayjs'

const posts = ref([])
const categories = ref([])
const dialogVisible = ref(false)
const editingPost = ref(null)
const submitLoading = ref(false)
const postFormRef = ref()

const postForm = ref({
  categoryId: null,
  title: '',
  content: ''
})

const postRules = {
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

const loadData = async () => {
  const postsRes = await getMyPosts()
  posts.value = postsRes.data.map(p => ({
    ...p,
    createTime: dayjs(p.createTime).format('YYYY-MM-DD HH:mm')
  }))
  const catsRes = await getPostCategories()
  categories.value = catsRes.data || []
  // 为每个帖子补充分类名
  posts.value.forEach(post => {
    const cat = categories.value.find(c => c.id === post.categoryId)
    post.categoryName = cat ? cat.name : ''
  })
}

const showCreateDialog = () => {
  editingPost.value = null
  postForm.value = { categoryId: null, title: '', content: '' }
  dialogVisible.value = true
}

const editPost = (post) => {
  editingPost.value = post
  postForm.value = {
    categoryId: post.categoryId,
    title: post.title,
    content: post.content
  }
  dialogVisible.value = true
}

const submitPost = async () => {
  await postFormRef.value.validate()
  submitLoading.value = true
  try {
    if (editingPost.value) {
      await updatePost(editingPost.value.id, postForm.value)
      ElMessage.success('编辑成功，等待审核')
    } else {
      await createPost(postForm.value)
      ElMessage.success('发布成功，等待审核')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    submitLoading.value = false
  }
}

const handleDeletePost = (id) => {
  ElMessageBox.confirm('确定删除该帖子吗？', '提示', { type: 'warning' }).then(async () => {
    await deletePost(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

const handleImageUpload = async (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  try {
    const res = await uploadImage(formData)
    const imgUrl = res.data
    // 将图片标签追加到内容末尾（也可以插入到光标位置，为简单起见追加到末尾）
    postForm.value.content += `\n<img src="${imgUrl}" alt="图片" style="max-width:100%;">\n`
    ElMessage.success('图片已插入内容中')
  } catch (error) {
    ElMessage.error('图片上传失败')
  }
}

onMounted(loadData)
</script>

<style scoped>
.my-posts {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
h1 {
  margin-bottom: 20px;
}
.post-title {
  text-decoration: none;
  color: #409eff;
}
</style>