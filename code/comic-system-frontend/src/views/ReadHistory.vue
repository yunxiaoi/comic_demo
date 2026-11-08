<template>
  <div class="read-history">
    <div class="container">
      <h1>阅读记录</h1>
      <el-table :data="histories" stripe>
        <el-table-column prop="comicTitle" label="漫画名称" />
        <el-table-column prop="chapterTitle" label="最后阅读章节" />
        <el-table-column prop="readTime" label="阅读时间" width="160" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="continueReading(row)">继续阅读</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="histories.length === 0" description="暂无阅读记录" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getReadHistory } from '@/api/user'
import dayjs from 'dayjs'

const router = useRouter()
const histories = ref([])

onMounted(async () => {
  const res = await getReadHistory()
  histories.value = res.data.map(h => ({
    ...h,
    readTime: dayjs(h.readTime).format('YYYY-MM-DD HH:mm')
  }))
})

const continueReading = (history) => {
  router.push(`/reader/${history.comicId}/${history.chapterId}`)
}
</script>

<style scoped>
.read-history {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 0 20px;
}
h1 {
  margin-bottom: 20px;
}
</style>