<template>
  <div class="news-detail" v-loading="loading">
    <div class="container" v-if="news">
      <div class="detail-header">
        <h1>{{ news.title }}</h1>
        <div class="meta">{{ formatTime(news.createTime) }}</div>
      </div>
      <div class="detail-content" v-html="news.content"></div>
      <!-- 封面图片放在内容下方 -->
      <div v-if="news.cover" class="cover">
        <img :src="news.cover" alt="封面图片">
      </div>
      <el-button @click="goBack">返回列表</el-button>
    </div>
    <el-empty v-else description="资讯不存在" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getNewsDetail } from '@/api/common'
import dayjs from 'dayjs'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const news = ref(null)

const formatTime = (time) => dayjs(time).format('YYYY-MM-DD HH:mm')

const goBack = () => {
  router.push('/notice') // 或 router.go(-1)，根据需求
}

onMounted(async () => {
  loading.value = true
  try {
    const res = await getNewsDetail(route.params.id)
    news.value = res.data
  } catch (error) {
    console.error('加载资讯详情失败:', error)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.news-detail {
  background: #fff;
  padding: 30px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 0 20px;
}
.detail-header {
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
  margin-bottom: 20px;
}
h1 {
  font-size: 28px;
  margin-bottom: 12px;
}
.meta {
  color: #999;
  font-size: 14px;
}
.detail-content {
  line-height: 1.8;
  font-size: 16px;
  margin-bottom: 30px;
  white-space: pre-wrap;
  word-break: break-word;
}
/* 封面图片样式：限制大小，居中显示 */
.cover {
  text-align: center;
  margin: 20px 0;
}
.cover img {
  max-width: 100%;
  max-height: 500px;   /* 限制最大高度 */
  width: auto;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  object-fit: contain;  /* 确保图片不变形 */
}
</style>