<template>
  <div class="home">
    <div class="banner">
      <el-carousel height="400px" :interval="5000">
        <el-carousel-item v-for="item in carousels" :key="item.id">
          <img :src="item.image" class="carousel-img" :alt="item.title">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="container">
      <section class="section">
        <div class="section-header">
          <h2>畅销热门</h2>
          <router-link to="/rank?type=orders">更多 &gt;</router-link>
        </div>
        <div class="comic-grid">
          <ComicCard v-for="comic in hotSales" :key="comic.id" :comic="comic" />
        </div>
      </section>

      <section class="section">
        <div class="section-header">
          <h2>高能排行</h2>
          <router-link to="/rank?type=views">更多 &gt;</router-link>
        </div>
        <div class="comic-grid">
          <ComicCard v-for="comic in highViews" :key="comic.id" :comic="comic" />
        </div>
      </section>

      <section class="section">
        <div class="section-header">
          <h2>完结佳作</h2>
          <router-link to="/category?status=1">更多 &gt;</router-link>
        </div>
        <div class="comic-grid">
          <ComicCard v-for="comic in finished" :key="comic.id" :comic="comic" />
        </div>
      </section>

      <div class="two-columns">
        <section class="section news">
          <div class="section-header">
            <h2>热门资讯</h2>
            <router-link to="/news">更多 &gt;</router-link>
          </div>
          <div class="news-list">
            <div v-for="item in hotNews" :key="item.id" class="news-item">
              <router-link :to="`/news/${item.id}`">{{ item.title }}</router-link>
              <span class="views">{{ item.views }}浏览</span>
            </div>
          </div>
        </section>

        <section class="section posts">
          <div class="section-header">
            <h2>热门帖子</h2>
            <router-link to="/forum">更多 &gt;</router-link>
          </div>
          <div class="post-list">
            <div v-for="item in hotPosts" :key="item.id" class="post-item">
              <router-link :to="`/post/${item.id}`">{{ item.title }}</router-link>
              <span class="views">{{ item.views }}浏览</span>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ComicCard from '@/components/ComicCard.vue'
import { getCarousels } from '@/api/common'
import { getHotSales, getHighViews, getFinished } from '@/api/comic'
import { getNewsList, getNotices } from '@/api/common'
//import { getPosts } from '@/api/forum'
import { getPosts } from '@/api/common'

const carousels = ref([])
const hotSales = ref([])
const highViews = ref([])
const finished = ref([])
const hotNews = ref([])
const hotPosts = ref([])

onMounted(async () => {
  const [carouselRes, hotSalesRes, highViewsRes, finishedRes, newsRes, postsRes] = await Promise.all([
    getCarousels(),
    getHotSales(),
    getHighViews(),
    getFinished(),
    getNewsList({ page: 1, size: 5 }),
    getPosts({ page: 1, size: 5 })
  ])
  carousels.value = carouselRes.data
  hotSales.value = hotSalesRes.data
  highViews.value = highViewsRes.data
  finished.value = finishedRes.data
  hotNews.value = newsRes.data
  hotPosts.value = postsRes.data.list || []
})
</script>

<style scoped>
.home {
  background: #f5f5f5;
}
.banner {
  margin-bottom: 30px;
}
.carousel-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
.section {
  margin-bottom: 40px;
}
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 20px;
  border-left: 4px solid #409eff;
  padding-left: 12px;
}
.section-header h2 {
  font-size: 20px;
  font-weight: bold;
}
.section-header a {
  color: #999;
  font-size: 14px;
  text-decoration: none;
}
.comic-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 20px;
}
.two-columns {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}
.news-list, .post-list {
  background: #fff;
  border-radius: 8px;
  padding: 0 16px;
}
.news-item, .post-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #eee;
}
.news-item:last-child, .post-item:last-child {
  border-bottom: none;
}
.news-item a, .post-item a {
  text-decoration: none;
  color: #333;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.news-item a:hover, .post-item a:hover {
  color: #409eff;
}
.views {
  color: #999;
  font-size: 12px;
  margin-left: 10px;
}
@media (max-width: 768px) {
  .two-columns {
    grid-template-columns: 1fr;
    gap: 20px;
  }
}
</style>