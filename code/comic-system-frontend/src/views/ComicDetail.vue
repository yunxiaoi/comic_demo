<template>
  <div class="comic-detail" v-loading="loading">
    <div class="container" v-if="comic">
      <div class="detail-header">
        <div class="cover">
          <img :src="comic.cover || defaultCover" :alt="comic.title">
        </div>
        <div class="info">
          <h1>{{ comic.title }}</h1>
          <div class="meta">
            <span>作者：{{ comic.author }}</span>
            <span>题材：{{ genreName }}</span>
            <span>地区：{{ regionName }}</span>
            <span>状态：{{ comic.status === 0 ? '连载中' : '已完结' }}</span>
            <span>付费：{{ comic.isPaid === 1 ? '￥' + comic.price : '免费' }}</span>
          </div>
          <div class="stats">
            <span><el-icon><View /></el-icon> {{ comic.views }}浏览</span>
            <span><el-icon><Star /></el-icon> {{ comic.favorites }}收藏</span>
            <span><el-icon><ShoppingCart /></el-icon> {{ comic.orders }}订购</span>
          </div>
          <div class="description">
            <h3>简介</h3>
            <p>{{ comic.description || '暂无简介' }}</p>
          </div>
          <div class="actions">
            <el-button v-if="!hasBought && comic.isPaid === 1" type="primary" @click="handleBuy" :loading="buyLoading">
              购买（￥{{ comic.price }}）
            </el-button>
            <el-button v-else type="success" @click="goToFirstChapter">开始阅读</el-button>
            <el-button :type="isFavorited ? 'danger' : ''" @click="toggleFavorite">
              {{ isFavorited ? '取消收藏' : '收藏' }}
            </el-button>
          </div>
        </div>
      </div>

      <div class="chapters">
        <h2>章节列表</h2>
        <div class="chapter-list" v-if="chapters.length">
          <div v-for="chapter in chapters" :key="chapter.id" class="chapter-item" @click="goToRead(chapter)">
            <span>第{{ chapter.chapterNo }}话 {{ chapter.title }}</span>
          </div>
        </div>
        <el-empty v-else description="暂无章节" />
      </div>

      <div class="comments">
        <h2>评论区</h2>
        <div class="comment-input" v-if="userStore.user">
          <el-input v-model="commentContent" type="textarea" :rows="3" placeholder="写下你的评论..." />
          <el-button type="primary" @click="submitComment" :loading="commentLoading">发表评论</el-button>
        </div>
        <div class="comment-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <div class="comment-user">
              <el-avatar :size="32" :src="comment.avatar || defaultAvatar" />
              <span>{{ comment.nickname || comment.username }}</span>
              <span class="time">{{ formatTime(comment.createTime) }}</span>
            </div>
            <div class="comment-content">{{ comment.content }}</div>
          </div>
          <el-empty v-if="comments.length === 0" description="暂无评论" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { View, Star, ShoppingCart } from '@element-plus/icons-vue'
import { useUserStore } from '@/store'
import { getComicDetail, getChapters, readChapter } from '@/api/comic'
import { getGenres, getRegions } from '@/api/common'
import { addFavorite, removeFavorite, checkFavorite, buyComic, getOrders } from '@/api/user'
import { addComment, getComments } from '@/api/forum'
import dayjs from 'dayjs'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const comicId = parseInt(route.params.id)
const defaultCover = 'https://via.placeholder.com/300x400?text=No+Cover'
const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const loading = ref(false)
const comic = ref(null)
const chapters = ref([])
const comments = ref([])
const genres = ref([])
const regions = ref([])
const isFavorited = ref(false)
const hasBought = ref(false)
const buyLoading = ref(false)
const commentContent = ref('')
const commentLoading = ref(false)

const genreName = computed(() => {
  const g = genres.value.find(g => g.id === comic.value?.genreId)
  return g ? g.name : ''
})
const regionName = computed(() => {
  const r = regions.value.find(r => r.id === comic.value?.regionId)
  return r ? r.name : ''
})

const formatTime = (time) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
}

const loadData = async () => {
  loading.value = true
  try {
    const [comicRes, chaptersRes, genresRes, regionsRes, commentsRes] = await Promise.all([
      getComicDetail(comicId),
      getChapters(comicId),
      getGenres(),
      getRegions(),
      getComments(1, comicId) // targetType=1 漫画
    ])
    comic.value = comicRes.data
    chapters.value = chaptersRes.data
    genres.value = genresRes.data
    regions.value = regionsRes.data
    comments.value = commentsRes.data || []

    if (userStore.user) {
      const favoriteRes = await checkFavorite(comicId)
      isFavorited.value = favoriteRes.data
      const ordersRes = await getOrders()
      hasBought.value = ordersRes.data.some(order => order.comicId === comicId)
    }
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const toggleFavorite = async () => {
  if (!userStore.user) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  try {
    if (isFavorited.value) {
      await removeFavorite(comicId)
      ElMessage.success('取消收藏成功')
      isFavorited.value = false
    } else {
      await addFavorite(comicId)
      ElMessage.success('收藏成功')
      isFavorited.value = true
    }
  } catch (error) {
    console.error(error)
  }
}

const handleBuy = async () => {
  if (!userStore.user) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  buyLoading.value = true
  try {
    await buyComic(comicId)
    ElMessage.success('购买成功，可以阅读了')
    hasBought.value = true
  } catch (error) {
    // 余额不足等错误已在拦截器中提示
  } finally {
    buyLoading.value = false
  }
}

const goToFirstChapter = () => {
  if (chapters.value.length === 0) {
    ElMessage.warning('暂无章节')
    return
  }
  goToRead(chapters.value[0])
}

const goToRead = (chapter) => {
  router.push(`/reader/${comicId}/${chapter.id}`)
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  commentLoading.value = true
  try {
    await addComment({
      targetType: 1,
      targetId: comicId,
      content: commentContent.value
    })
    ElMessage.success('评论成功，等待审核')
    commentContent.value = ''
    // 刷新评论列表
    const res = await getComments(1, comicId)
    comments.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    commentLoading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.comic-detail {
  background: #fff;
  min-height: calc(100vh - 128px);
  padding: 30px 0;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
.detail-header {
  display: flex;
  gap: 40px;
  margin-bottom: 40px;
}
.cover {
  flex-shrink: 0;
  width: 300px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}
.cover img {
  width: 100%;
  display: block;
}
.info {
  flex: 1;
}
h1 {
  font-size: 28px;
  margin-bottom: 20px;
}
.meta {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-bottom: 20px;
  color: #666;
  font-size: 14px;
}
.stats {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  color: #999;
  font-size: 14px;
}
.stats span {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}
.description {
  background: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
}
.description h3 {
  font-size: 16px;
  margin-bottom: 10px;
}
.description p {
  line-height: 1.6;
  color: #666;
}
.actions {
  display: flex;
  gap: 15px;
}
.chapters {
  margin-top: 40px;
}
.chapters h2 {
  margin-bottom: 20px;
  font-size: 20px;
}
.chapter-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 12px;
}
.chapter-item {
  background: #f5f5f5;
  padding: 12px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
}
.chapter-item:hover {
  background: #e9e9e9;
  transform: translateX(4px);
}
.comments {
  margin-top: 40px;
}
.comments h2 {
  margin-bottom: 20px;
  font-size: 20px;
}
.comment-input {
  margin-bottom: 30px;
}
.comment-input .el-button {
  margin-top: 10px;
}
.comment-list {
  margin-top: 20px;
}
.comment-item {
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}
.comment-user {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}
.comment-user span:first-of-type {
  font-weight: bold;
}
.time {
  font-size: 12px;
  color: #999;
}
.comment-content {
  padding-left: 40px;
  color: #333;
  line-height: 1.5;
}
@media (max-width: 768px) {
  .detail-header {
    flex-direction: column;
  }
  .cover {
    width: 200px;
    margin: 0 auto;
  }
}
</style>