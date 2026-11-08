<template>
  <div class="my-comments">
    <div class="container">
      <h1>我的评论</h1>
      <el-table :data="comments" stripe>
        <el-table-column prop="content" label="内容" min-width="300" show-overflow-tooltip />
        <el-table-column label="类型" width="80">
          <template #default="{ row }">
            {{ row.targetType === 1 ? '漫画' : '帖子' }}
          </template>
        </el-table-column>
        <el-table-column prop="targetTitle" label="对象" min-width="150" show-overflow-tooltip />
        <el-table-column prop="createTime" label="评论时间" width="160" />
      </el-table>
      <el-empty v-if="comments.length === 0" description="暂无评论" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyComments } from '@/api/forum'
import dayjs from 'dayjs'

const comments = ref([])

onMounted(async () => {
  const res = await getMyComments()
  comments.value = res.data.map(c => ({
    ...c,
    createTime: dayjs(c.createTime).format('YYYY-MM-DD HH:mm')
  }))
})
</script>

<style scoped>
.my-comments {
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