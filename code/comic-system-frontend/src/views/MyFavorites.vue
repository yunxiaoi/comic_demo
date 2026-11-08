<template>
  <div class="my-favorites">
    <div class="container">
      <h1>我的收藏</h1>
      <div class="comic-grid">
        <ComicCard v-for="fav in favorites" :key="fav.comicId" :comic="fav.comic" />
      </div>
      <el-empty v-if="favorites.length === 0" description="暂无收藏漫画" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ComicCard from '@/components/ComicCard.vue'
import { getFavorites } from '@/api/user'

const favorites = ref([])

onMounted(async () => {
  const res = await getFavorites()
  favorites.value = res.data
})
</script>

<style scoped>
.my-favorites {
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
}
</style>