<template>
  <div class="reader" v-loading="loading">
    <div class="reader-header">
      <el-button @click="goBack" icon="ArrowLeft">返回</el-button>
      <div class="info">
        <span>{{ comic?.title }} - 第{{ chapter?.chapterNo }}话 {{ chapter?.title }}</span>
      </div>
    </div>
    <div class="reader-content" v-if="chapter">
      <div v-for="(img, idx) in images" :key="idx" class="image-container">
        <img :src="img" :alt="`第${chapter.chapterNo}话-${idx+1}`" @load="onImageLoad" />
      </div>
    </div>
    <div class="reader-footer">
      <el-button-group>
        <el-button @click="prevChapter" :disabled="!prevChapterId">上一话</el-button>
        <el-button @click="nextChapter" :disabled="!nextChapterId">下一话</el-button>
      </el-button-group>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getComicDetail, getChapters, readChapter } from '@/api/comic'

const route = useRoute()
const router = useRouter()
const comicId = computed(() => parseInt(route.params.comicId))
const chapterId = computed(() => parseInt(route.params.chapterId))

const loading = ref(false)
const comic = ref(null)
const chapters = ref([])
const chapter = ref(null)
const images = ref([])

const currentIndex = computed(() => {
  return chapters.value.findIndex(c => c.id === chapterId.value)
})

const prevChapterId = computed(() => {
  const idx = currentIndex.value
  if (idx > 0) return chapters.value[idx-1].id
  return null
})

const nextChapterId = computed(() => {
  const idx = currentIndex.value
  if (idx < chapters.value.length - 1) return chapters.value[idx+1].id
  return null
})

const loadData = async () => {
  loading.value = true
  try {
    const comicRes = await getComicDetail(comicId.value)
    comic.value = comicRes.data

    const chaptersRes = await getChapters(comicId.value)
    chapters.value = chaptersRes.data.sort((a, b) => a.chapterNo - b.chapterNo)

    const chapterRes = await readChapter(chapterId.value)
    chapter.value = chapterRes.data
    if (chapter.value.content) {
      try {
        images.value = JSON.parse(chapter.value.content)
      } catch {
        images.value = [chapter.value.content]
      }
    } else {
      images.value = []
    }
  } catch (error) {
    console.error(error)
    ElMessage.error('加载章节失败，可能未购买')
    router.push(`/comic/${comicId.value}`)
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.push(`/comic/${comicId.value}`)
}

const prevChapter = () => {
  if (prevChapterId.value) {
    router.push(`/reader/${comicId.value}/${prevChapterId.value}`)
  } else {
    ElMessage.info('已是第一话')
  }
}

const nextChapter = () => {
  if (nextChapterId.value) {
    router.push(`/reader/${comicId.value}/${nextChapterId.value}`)
  } else {
    ElMessage.info('当前为最新话')
  }
}

const onImageLoad = () => {
  window.scrollTo(0, 0)
}

// 监听路由参数变化，重新加载数据
watch([comicId, chapterId], () => {
  loadData()
})

onMounted(loadData)
</script>

<style scoped>
.reader {
  background: #f0f0f0;
  min-height: 100vh;
}

.reader-header {
  position: sticky;
  top: 0;
  background: #fff;
  padding: 12px 20px;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.reader-content {
  max-width: 800px;
  margin: 20px auto;
}

.image-container {
  text-align: center;
  margin-bottom: 10px;
}

.image-container img {
  max-width: 100%;
  display: block;
  margin: 0 auto;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  background: #fff;
}

.reader-footer {
  position: sticky;
  bottom: 20px;
  text-align: center;
  margin: 20px 0;
}
</style>