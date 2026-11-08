<template>
  <div class="schedule-page">
    <div class="container">
      <h1>每周更新计划</h1>
      <el-tabs v-model="activeWeekday" @tab-click="loadSchedule">
        <el-tab-pane v-for="(name, idx) in weekdays" :key="idx" :label="name" :name="String(idx+1)" />
      </el-tabs>
      <div class="comic-grid" v-if="comics.length">
        <ComicCard v-for="comic in comics" :key="comic.id" :comic="comic" />
      </div>
      <el-empty v-else description="本周暂无更新" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ComicCard from '@/components/ComicCard.vue'
import { getSchedule } from '@/api/comic'

const weekdays = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
const activeWeekday = ref('1')
const comics = ref([])

const loadSchedule = async () => {
  const res = await getSchedule()
  const scheduleMap = res.data
  comics.value = scheduleMap[activeWeekday.value] || []
}

onMounted(loadSchedule)
</script>

<style scoped>
.schedule-page {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
h1 {
  margin-bottom: 20px;
}
.comic-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 20px;
  margin-top: 20px;
}
</style>