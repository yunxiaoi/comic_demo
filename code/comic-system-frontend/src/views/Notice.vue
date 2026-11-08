<template>
  <div class="notice-page">
    <div class="container">
      <h1>系统公告</h1>
      <div class="notice-list">
        <div v-for="notice in notices" :key="notice.id" class="notice-item" @click="goToDetail(notice.id)">
          <div class="title">{{ notice.title }}</div>
          <div class="time">{{ formatTime(notice.createTime) }}</div>
        </div>
      </div>
      <el-empty v-if="notices.length === 0" description="暂无公告" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getNotices } from '@/api/common'
import dayjs from 'dayjs'

const router = useRouter()
const notices = ref([])

const formatTime = (time) => dayjs(time).format('YYYY-MM-DD')

const goToDetail = (id) => {
  router.push(`/notice/${id}`)
}

onMounted(async () => {
  const res = await getNotices()
  notices.value = res.data
})
onMounted(async () => {
  const res = await getNotices()
  console.log('公告返回:', res)  // 查看控制台输出
  notices.value = res.data
})
</script>

<style scoped>
.notice-page {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 0 20px;
}
h1 {
  margin-bottom: 20px;
}
.notice-list {
  background: #fff;
  border-radius: 8px;
}
.notice-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}
.notice-item:hover {
  background: #f9f9f9;
}
.title {
  font-size: 16px;
  color: #333;
}
.time {
  color: #999;
  font-size: 12px;
}
</style>