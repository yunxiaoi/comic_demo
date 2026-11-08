<template>
  <div class="category-page">
    <div class="container">
      <div class="filters">
        <el-form :inline="true" :model="filters">
          <el-form-item label="题材">
            <el-select v-model="filters.genreId" placeholder="全部" clearable @change="search">
              <el-option v-for="g in genres" :key="g.id" :label="g.name" :value="g.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="地区">
            <el-select v-model="filters.regionId" placeholder="全部" clearable @change="search">
              <el-option v-for="r in regions" :key="r.id" :label="r.name" :value="r.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="进度">
            <el-select v-model="filters.status" placeholder="全部" clearable @change="search">
              <el-option label="连载中" :value="0" />
              <el-option label="已完结" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="收费">
            <el-select v-model="filters.isPaid" placeholder="全部" clearable @change="search">
              <el-option label="免费" :value="0" />
              <el-option label="付费" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.keyword" placeholder="搜索漫画" clearable @clear="search" @keyup.enter="search">
              <template #append>
                <el-button @click="search" icon="Search" />
              </template>
            </el-input>
          </el-form-item>
        </el-form>
      </div>

      <div class="comic-grid">
        <ComicCard v-for="comic in comics" :key="comic.id" :comic="comic" />
      </div>

      <div class="pagination">
        <el-pagination
            v-model:current-page="page"
            :page-size="size"
            :total="total"
            layout="prev, pager, next"
            @current-change="search"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ComicCard from '@/components/ComicCard.vue'
import { searchComics } from '@/api/comic'
import { getGenres, getRegions } from '@/api/common'

const comics = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(20)
const filters = ref({
  keyword: '',
  genreId: null,
  regionId: null,
  status: null,
  isPaid: null
})
const genres = ref([])
const regions = ref([])

const search = async () => {
  const params = {
    page: page.value,
    size: size.value,
    ...filters.value
  }
  const res = await searchComics(params)
  comics.value = res.data.list || []
  total.value = res.data.total || 0
}

onMounted(async () => {
  const [genresRes, regionsRes] = await Promise.all([getGenres(), getRegions()])
  genres.value = genresRes.data
  regions.value = regionsRes.data
  search()
})
</script>

<style scoped>
.category-page {
  background: #f5f5f5;
  padding: 20px 0;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
.filters {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}
.comic-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>