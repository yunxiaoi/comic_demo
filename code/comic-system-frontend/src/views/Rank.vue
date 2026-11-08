<template>
  <div class="rank-page">
    <div class="container">
      <h1>排行榜</h1>
      <el-tabs v-model="activeTab" @tab-click="loadRank">
        <el-tab-pane label="浏览量排行" name="views" />
        <el-tab-pane label="订购量排行" name="orders" />
        <el-tab-pane label="收藏量排行" name="favorites" />
      </el-tabs>
      <div class="rank-list">
        <div v-for="(comic, idx) in comics" :key="comic.id" class="rank-item">
          <div class="rank-num" :class="{'top': idx < 3}">{{ idx+1 }}</div>
          <div class="cover">
            <img :src="comic.cover || defaultCover" alt="">
          </div>
          <div class="info">
            <router-link :to="`/comic/${comic.id}`" class="title">{{ comic.title }}</router-link>
            <div class="meta">
              <span>作者：{{ comic.author }}</span>
              <span>浏览：{{ comic.views }}</span>
              <span>订购：{{ comic.orders }}</span>
              <span>收藏：{{ comic.favorites }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRankByViews, getRankByOrders, getRankByFavorites } from '@/api/comic'

const activeTab = ref('views')
const comics = ref([])
const defaultCover = 'https://via.placeholder.com/60x80?text=No+Cover'

const loadRank = async () => {
  let res
  if (activeTab.value === 'views') {
    res = await getRankByViews()
  } else if (activeTab.value === 'orders') {
    res = await getRankByOrders()
  } else {
    res = await getRankByFavorites()
  }
  comics.value = res.data || []
}

onMounted(loadRank)
</script>

<style scoped>
.rank-page {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 0 20px;
}
h1 {
  margin-bottom: 20px;
}
.rank-list {
  background: #fff;
  border-radius: 8px;
  margin-top: 20px;
}
.rank-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #eee;
}
.rank-item:last-child {
  border-bottom: none;
}
.rank-num {
  width: 50px;
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #999;
}
.rank-num.top {
  color: #f56c6c;
}
.cover {
  width: 60px;
  height: 80px;
  margin-right: 15px;
  flex-shrink: 0;
  border-radius: 4px;
  overflow: hidden;
}
.cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.info {
  flex: 1;
}
.title {
  font-size: 16px;
  font-weight: bold;
  text-decoration: none;
  color: #333;
}
.title:hover {
  color: #409eff;
}
.meta {
  margin-top: 8px;
  font-size: 12px;
  color: #999;
  display: flex;
  gap: 15px;
}
</style>