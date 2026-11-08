<template>
  <div class="my-orders">
    <div class="container">
      <h1>我的订购</h1>
      <el-table :data="orders" stripe>
        <el-table-column prop="comicTitle" label="漫画名称" />
        <el-table-column prop="amount" label="金额" width="100" />
        <el-table-column prop="createTime" label="购买时间" width="160" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button link type="primary" @click="goToComic(row.comicId)">阅读</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="orders.length === 0" description="暂无订购记录" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getOrders } from '@/api/user'
import dayjs from 'dayjs'

const router = useRouter()
const orders = ref([])

onMounted(async () => {
  const res = await getOrders()
  orders.value = res.data.map(o => ({
    ...o,
    createTime: dayjs(o.createTime).format('YYYY-MM-DD HH:mm')
  }))
})

const goToComic = (comicId) => {
  router.push(`/comic/${comicId}`)
}
</script>

<style scoped>
.my-orders {
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