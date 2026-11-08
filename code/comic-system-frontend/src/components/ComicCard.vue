<template>
  <div class="comic-card" @click="goDetail">
    <div class="cover">
      <img :src="comic.cover || defaultCover" :alt="comic.title">
      <div class="badge" v-if="comic.isPaid === 1">付费</div>
      <div class="badge free" v-else>免费</div>
    </div>
    <div class="info">
      <h3 class="title">{{ comic.title }}</h3>
      <p class="author">{{ comic.author }}</p>
      <div class="stats">
        <span><el-icon><View /></el-icon> {{ comic.views }}</span>
        <span><el-icon><Star /></el-icon> {{ comic.favorites }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { View, Star } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  comic: Object
})
const router = useRouter()
const defaultCover = 'https://via.placeholder.com/200x280?text=No+Cover'

const goDetail = () => {
  router.push(`/comic/${props.comic.id}`)
}
</script>

<style scoped>
.comic-card {
  width: 180px;
  cursor: pointer;
  transition: transform 0.2s;
}
.comic-card:hover {
  transform: translateY(-5px);
}
.cover {
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  aspect-ratio: 2 / 2.8;
}
.cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.badge {
  position: absolute;
  top: 8px;
  right: 8px;
  background: #f56c6c;
  color: #fff;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 12px;
}
.badge.free {
  background: #67c23a;
}
.info {
  padding: 8px 0;
}
.title {
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.author {
  font-size: 12px;
  color: #999;
  margin-bottom: 4px;
}
.stats {
  display: flex;
  gap: 12px;
  font-size: 12px;
  color: #999;
}
.stats span {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}
</style>