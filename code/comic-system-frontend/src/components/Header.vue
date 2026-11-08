<template>
  <header class="header">
    <div class="container">
      <div class="logo">
        <router-link to="/">漫画阅读</router-link>
      </div>
      <nav class="nav">
        <router-link to="/">首页</router-link>
        <router-link to="/category">分类</router-link>
        <router-link to="/update">更新</router-link>
        <router-link to="/rank">排行榜</router-link>
        <router-link to="/forum">社区</router-link>
        <router-link to="/notice">公告</router-link>
      </nav>
      <div class="user">
        <template v-if="userStore.user">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" :src="userStore.user.avatar || defaultAvatar" />
              <span>{{ userStore.user.nickname || userStore.user.username }}</span>
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <!-- 管理员专用菜单项 -->
                <el-dropdown-item v-if="userStore.user?.role === 1" command="admin">管理后台</el-dropdown-item>
                <el-dropdown-item v-if="userStore.user?.role === 1" command="admin-profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="my-posts">我的帖子</el-dropdown-item>
                <el-dropdown-item command="my-orders">我的订购</el-dropdown-item>
                <el-dropdown-item command="my-favorites">我的收藏</el-dropdown-item>
                <el-dropdown-item command="my-comments">我的评论</el-dropdown-item>
                <el-dropdown-item command="read-history">阅读记录</el-dropdown-item>
                <el-dropdown-item command="recharge">充值中心</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <router-link to="/login">登录</router-link>
          <router-link to="/register">注册</router-link>
        </template>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store'
import { ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/')
    return
  }
  // 命令 → 路由路径映射表
  const routeMap = {
    profile: '/user/info',           // 普通用户个人中心
    'my-posts': '/my-posts',
    'my-orders': '/my-orders',
    'my-favorites': '/my-favorites',
    'my-comments': '/my-comments',
    'read-history': '/read-history',
    recharge: '/recharge',
    admin: '/admin/dashboard',       // 管理员后台首页
    'admin-profile': '/admin/profile' // 管理员个人信息
  }
  const path = routeMap[command]
  if (path) {
    router.push(path)
  } else {
    console.warn('未知命令:', command)
  }
}
</script>

<style scoped>
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 64px;
}
.logo a {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  text-decoration: none;
}
.nav a {
  margin: 0 15px;
  text-decoration: none;
  color: #333;
}
.nav a.router-link-active {
  color: #409eff;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}
.user a {
  margin-left: 10px;
  text-decoration: none;
  color: #409eff;
}
</style>