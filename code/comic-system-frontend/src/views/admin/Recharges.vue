<template>
  <div>
    <el-table :data="list" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="amount" label="金额" width="100" />
      <el-table-column prop="createTime" label="时间" width="160" />
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
import { getRechargeList } from '@/api/admin'
import dayjs from 'dayjs'

const list = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)

const loadData = async () => {
  const res = await getRechargeList({ page: page.value, size: size.value })
  list.value = res.data.list.map(r => ({
    ...r,
    createTime: dayjs(r.createTime).format('YYYY-MM-DD HH:mm')
  }))
  total.value = res.data.total
}

onMounted(loadData)
</script>