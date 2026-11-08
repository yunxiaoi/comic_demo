<template>
  <div class="post-detail" v-loading="loading">
    <div class="container" v-if="post">
      <div class="post-header">
        <h1>{{ post.title }}</h1>
        <div class="meta">
          <span>作者：{{ post.nickname || post.username }}</span>
          <span><el-icon><View /></el-icon> {{ post.views }}</span>
          <span>{{ formatTime(post.createTime) }}</span>
        </div>
      </div>
      <div class="post-content" v-html="post.content"></div>

      <div class="comments">
        <h2>评论</h2>
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
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { View } from '@element-plus/icons-vue'
import { useUserStore } from '@/store'
import { getPostDetail, addComment, getComments } from '@/api/forum'
import dayjs from 'dayjs'

const route = useRoute()
const userStore = useUserStore()
const postId = parseInt(route.params.id)
const loading = ref(false)
const post = ref(null)
const comments = ref([])
const commentContent = ref('')
const commentLoading = ref(false)
const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const formatTime = (time) => dayjs(time).format('YYYY-MM-DD HH:mm')

const loadData = async () => {
  loading.value = true
  try {
    const postRes = await getPostDetail(postId)
    post.value = postRes.data
    const commentsRes = await getComments(2, postId) // targetType=2 帖子
    comments.value = commentsRes.data || []
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  commentLoading.value = true
  try {
    await addComment({
      targetType: 2,
      targetId: postId,
      content: commentContent.value
    })
    ElMessage.success('评论成功，等待审核')
    commentContent.value = ''
    const res = await getComments(2, postId)
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
.post-detail {
  background: #fff;
  padding: 30px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 0 20px;
}
.post-header {
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
  margin-bottom: 20px;
}
.post-header h1 {
  font-size: 28px;
  margin-bottom: 12px;
}
.meta {
  display: flex;
  gap: 20px;
  color: #999;
  font-size: 14px;
}
.meta span {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}
.post-content {
  line-height: 1.8;
  font-size: 16px;
  color: #333;
  margin-bottom: 40px;
  white-space: pre-wrap;
  word-break: break-word;
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
</style>