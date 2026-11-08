<template>
  <div>
    <el-input v-model="keyword" placeholder="用户名" style="width: 200px" clearable @clear="loadData" />
    <el-button @click="loadData" type="primary" style="margin-left: 10px">搜索</el-button>
    <el-table :data="list" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="订单ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="comicTitle" label="漫画名称" />
      <el-table-column prop="amount" label="金额" width="100" />
      <el-table-column prop="createTime" label="购买时间" width="160" />
    </el-table>
    <el-pagination
        v-model:current-page="page"
        :page-size="size"
        :total="total"
        layout="prev, pager, next"
        @current-change="loadData"
        style="margin-top: 20px"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getOrderList } from '@/api/admin'
import dayjs from 'dayjs'

const list = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const keyword = ref('')

const loadData = async () => {
  const res = await getOrderList({ page: page.value, size: size.value, username: keyword.value })
  list.value = res.data.list.map(o => ({
    ...o,
    createTime: dayjs(o.createTime).format('YYYY-MM-DD HH:mm')
  }))
  total.value = res.data.total
}

onMounted(loadData)
</script>