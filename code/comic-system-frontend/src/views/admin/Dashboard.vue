<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-title">漫画数量</div>
            <div class="stat-value">{{ stats.comicCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-title">帖子数量</div>
            <div class="stat-value">{{ stats.postCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-title">评论数量</div>
            <div class="stat-value">{{ stats.commentCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-title">订单数量</div>
            <div class="stat-value">{{ stats.orderCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card>
          <template #header>
            <span>近一周每日消费金额</span>
          </template>
          <div ref="salesChartRef" style="height: 400px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>不同类型漫画饼状图</span>
          </template>
          <div ref="comicTypeChartRef" style="height: 400px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>不同类型帖子数量柱状图</span>
          </template>
          <div ref="postTypeChartRef" style="height: 400px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats } from '@/api/admin'

const stats = ref({})
const salesChartRef = ref(null)
const comicTypeChartRef = ref(null)
const postTypeChartRef = ref(null)

let salesChart = null
let comicTypeChart = null
let postTypeChart = null

onMounted(async () => {
  try {
    const statsRes = await getDashboardStats()
    stats.value = statsRes.data
    console.log('统计数据：', statsRes.data)  // 调试用

    // 近一周消费金额折线图
    if (salesChartRef.value) {
      salesChart = echarts.init(salesChartRef.value)
      const dailySales = statsRes.data.dailySales
      salesChart.setOption({
        xAxis: { type: 'category', data: dailySales.days || [] },
        yAxis: { type: 'value', name: '金额(元)' },
        series: [{ type: 'line', data: dailySales.amounts || [], smooth: true, areaStyle: {} }]
      })
    }

    // 不同类型漫画饼图
    if (comicTypeChartRef.value) {
      comicTypeChart = echarts.init(comicTypeChartRef.value)
      const comicTypeStats = statsRes.data.comicTypeStats || []
      comicTypeChart.setOption({
        tooltip: { trigger: 'item' },
        series: [{
          type: 'pie',
          radius: '50%',
          data: comicTypeStats.map(item => ({ name: item.name, value: item.count }))
        }]
      })
    }

    // 不同类型帖子数量柱状图
    if (postTypeChartRef.value) {
      postTypeChart = echarts.init(postTypeChartRef.value)
      const postTypeStats = statsRes.data.postTypeStats || []
      postTypeChart.setOption({
        xAxis: { type: 'category', data: postTypeStats.map(item => item.name) },
        yAxis: { type: 'value' },
        series: [{ type: 'bar', data: postTypeStats.map(item => item.count) }]
      })
    }
  } catch (error) {
    console.error('加载统计数据失败：', error)
  }
})

onUnmounted(() => {
  if (salesChart) salesChart.dispose()
  if (comicTypeChart) comicTypeChart.dispose()
  if (postTypeChart) postTypeChart.dispose()
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
.stat-card {
  text-align: center;
}
.stat-title {
  font-size: 14px;
  color: #999;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
  margin-top: 8px;
}
</style>