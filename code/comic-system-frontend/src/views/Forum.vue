<template>
  <div class="forum-page">
    <div class="container">
      <div class="forum-header">
        <h1>社区论坛</h1>
        <el-button type="primary" @click="goToCreate">发帖</el-button>
      </div>
      <div class="categories">
        <el-radio-group v-model="selectedCategory" @change="loadPosts">
          <el-radio-button :label="null">全部</el-radio-button>
          <el-radio-button v-for="cat in categories" :key="cat.id" :label="cat.id">
            {{ cat.name }}
          </el-radio-button>
        </el-radio-group>
      </div>
      <div class="post-list">
        <div v-for="post in posts" :key="post.id" class="post-item" @click="goToPost(post.id)">
          <div class="post-title">{{ post.title }}</div>
          <div class="post-meta">
            <span>作者：{{ post.nickname || post.username }}</span>
            <span><el-icon><View /></el-icon> {{ post.views }}</span>
            <span>{{ formatTime(post.createTime) }}</span>
          </div>
        </div>
        <el-empty v-if="posts.length === 0" description="暂无帖子" />
      </div>
      <div class="pagination">
        <el-pagination
            v-model:current-page="page"
            :page-size="size"
            :total="total"
            layout="prev, pager, next"
            @current-change="loadPosts"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { View } from '@element-plus/icons-vue'
import { getPosts } from '@/api/common'
import { getPostCategories } from '@/api/common'
import dayjs from 'dayjs'

const router = useRouter()
const posts = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const selectedCategory = ref(null)
const categories = ref([])

const formatTime = (time) => dayjs(time).format('YYYY-MM-DD')

const loadPosts = async () => {
  const params = { page: page.value, size: size.value }
  if (selectedCategory.value) params.categoryId = selectedCategory.value
  const res = await getPosts(params)
  posts.value = res.data.list || []
  total.value = res.data.total || 0
}

const goToPost = (id) => {
  router.push(`/post/${id}`)
}

const goToCreate = () => {
  router.push('/my-posts')
}

onMounted(async () => {
  const catRes = await getPostCategories()
  categories.value = catRes.data || []
  loadPosts()
})
</script>

<style scoped>
.forum-page {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 0 20px;
}
.forum-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.categories {
  margin-bottom: 20px;
  background: #fff;
  padding: 10px;
  border-radius: 8px;
}
.post-list {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
}
.post-item {
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
  transition: background 0.2s;
}
.post-item:hover {
  background: #f9f9f9;
}
.post-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
  color: #333;
}
.post-meta {
  display: flex;
  gap: 15px;
  font-size: 12px;
  color: #999;
}
.post-meta span {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>