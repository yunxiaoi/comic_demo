<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>添加更新计划</span>
          </template>
          <el-form :model="form" label-width="80px">
            <el-form-item label="选择漫画">
              <el-select v-model="form.comicId" filterable placeholder="请选择漫画">
                <el-option v-for="c in comics" :key="c.id" :label="c.title" :value="c.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="更新日">
              <el-select v-model="form.weekday">
                <el-option v-for="(name, idx) in weekdays" :key="idx" :label="name" :value="idx+1" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addSchedule" :loading="adding">添加</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>当前计划</span>
          </template>
          <el-tabs v-model="activeTab" @tab-click="handleTabClick">
            <el-tab-pane v-for="(name, idx) in weekdays" :key="idx" :label="name" :name="String(idx+1)" />
          </el-tabs>
          <div v-for="s in schedules" :key="s.id" class="schedule-item">
            <span>{{ s.comicTitle }}</span>
            <el-button link type="danger" @click="removeSchedule(s.id)">删除</el-button>
          </div>
          <el-empty v-if="schedules.length === 0" description="暂无" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getComicList, getScheduleList, saveSchedule, deleteSchedule } from '@/api/admin'

const weekdays = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
const activeTab = ref('1')
const comics = ref([])
const schedules = ref([])
const adding = ref(false)
const form = ref({ comicId: null, weekday: 1 })


const loadComics = async () => {
  const res = await getComicList({ page: 1, size: 100 })
  comics.value = res.data.list
}

const loadSchedules = async () => {
  console.log('当前 weekdays:', activeTab.value)
  const res = await getScheduleList(parseInt(activeTab.value))
  schedules.value = res.data
}

const addSchedule = async () => {
  if (!form.value.comicId) {
    ElMessage.warning('请选择漫画')
    return
  }
  adding.value = true
  try {
    await saveSchedule(form.value)
    ElMessage.success('添加成功')
    loadSchedules()
    form.value.comicId = null
  } catch (error) {
    console.error(error)
  } finally {
    adding.value = false
  }
}

const removeSchedule = (id) => {
  ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteSchedule(id)
    ElMessage.success('删除成功')
    loadSchedules()
  }).catch(() => {})
}

const handleTabClick = (tab) => {
  activeTab.value = tab.paneName
  loadSchedules()
}


onMounted(() => {
  loadComics()
  loadSchedules()
})
</script>

<style scoped>
.schedule-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}
</style>