<template>
  <div class="notice-detail" v-loading="loading">
    <div class="container" v-if="notice">
      <div class="detail-header">
        <h1>{{ notice.title }}</h1>
        <div class="meta">{{ formatTime(notice.createTime) }}</div>
      </div>
      <div class="detail-content" v-html="notice.content"></div>
      <el-button @click="goBack">返回列表</el-button>
    </div>
    <el-empty v-else description="公告不存在" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getNoticeDetail } from '@/api/common'
import dayjs from 'dayjs'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const notice = ref(null)

const formatTime = (time) => dayjs(time).format('YYYY-MM-DD HH:mm')

const goBack = () => {
  router.push('/notice')
}

onMounted(async () => {
  loading.value = true
  try {
    const res = await getNoticeDetail(route.params.id)
    notice.value = res.data
  } catch (error) {
    console.error('加载公告详情失败:', error)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.notice-detail {
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
</style>