<template>
  <el-container class="admin-layout">
    <el-aside width="200px">
      <div class="logo">漫画管理后台</div>
      <el-menu router :default-active="activeMenu">
        <el-menu-item index="/admin/dashboard">
          <el-icon><DataLine /></el-icon><span>首页</span>
        </el-menu-item>
        <el-sub-menu index="comic">
          <template #title><el-icon><Notebook /></el-icon><span>漫画管理</span></template>
          <el-menu-item index="/admin/comic-genre">漫画题材</el-menu-item>
          <el-menu-item index="/admin/comic-region">漫画地区</el-menu-item>
          <el-menu-item index="/admin/comic-list">漫画信息</el-menu-item>
          <el-menu-item index="/admin/comic-chapters">漫画章节</el-menu-item>
          <el-menu-item index="/admin/update-schedule">更新计划</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/orders">
          <el-icon><ShoppingCart /></el-icon><span>订购信息</span>
        </el-menu-item>
        <el-menu-item index="/admin/news">
          <el-icon><Document /></el-icon><span>漫画资讯</span>
        </el-menu-item>
        <el-sub-menu index="forum">
          <template #title><el-icon><ChatLineSquare /></el-icon><span>社区管理</span></template>
          <el-menu-item index="/admin/post-category">帖子分类</el-menu-item>
          <el-menu-item index="/admin/forum-posts">论坛帖子</el-menu-item>
          <el-menu-item index="/admin/comments">评论信息</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/recharges">
          <el-icon><Money /></el-icon><span>充值记录</span>
        </el-menu-item>
        <el-menu-item index="/admin/carousel">
          <el-icon><Picture /></el-icon><span>轮播图</span>
        </el-menu-item>
        <el-menu-item index="/admin/notices">
          <el-icon><Bell /></el-icon><span>系统公告</span>
        </el-menu-item>
        <el-menu-item index="/admin/users">
          <el-icon><User /></el-icon><span>用户信息</span>
        </el-menu-item>
        <el-menu-item index="/admin/profile">
          <el-icon><Setting /></el-icon><span>个人信息</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <!-- 新增：返回首页按钮 -->
          <el-button type="text" @click="goHome" style="margin-right: 20px; color: #409eff;">
            <el-icon><House /></el-icon> 返回首页
          </el-button>
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              {{ userStore.user?.nickname || userStore.user?.username }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store'
import {
  DataLine, Notebook, ShoppingCart, Document, ChatLineSquare,
  Money, Picture, Bell, User, Setting, House,ArrowDown
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/')
  } else if (command === 'profile') {
    router.push('/admin/profile')
  }
}

const goHome = () => {
  router.push('/')
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}
.el-aside {
  background: #304156;
  color: #fff;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  background: #263445;
  color: #fff;
}
.el-menu {
  border-right: none;
  background: #304156;
}
.el-menu-item, .el-sub-menu__title {
  color: #bfcbd9;
}
.el-menu-item.is-active, .el-menu-item:hover {
  background: #263445;
  color: #fff;
}
.el-header {
  background: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
}
.user-info {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 4px;
}
.el-main {
  background: #f0f2f5;
}
</style>