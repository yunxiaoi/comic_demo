<template>
  <div class="login-container">
    <div class="login-card">
      <h2>登录</h2>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="用户名登录" name="username">
          <el-form :model="usernameForm" :rules="usernameRules" ref="usernameFormRef">
            <el-form-item prop="username">
              <el-input v-model="usernameForm.username" placeholder="用户名" prefix-icon="User" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="usernameForm.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="手机号登录" name="phone">
          <el-form :model="phoneForm" :rules="phoneRules" ref="phoneFormRef">
            <el-form-item prop="phone">
              <el-input v-model="phoneForm.phone" placeholder="手机号" prefix-icon="Phone" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="phoneForm.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <el-button type="primary" class="login-btn" @click="handleLogin" :loading="loading">登录</el-button>
      <div class="register-link">
        还没有账号？<router-link to="/register">立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store'
import { login } from '@/api/auth'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref('username')
const loading = ref(false)

// 用户名登录表单
const usernameFormRef = ref()
const usernameForm = reactive({
  username: '',
  password: ''
})
const usernameRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

// 手机号登录表单
const phoneFormRef = ref()
const phoneForm = reactive({
  phone: '',
  password: ''
})
const phoneRules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  let formRef, formData
  if (activeTab.value === 'username') {
    formRef = usernameFormRef.value
    formData = { username: usernameForm.username, password: usernameForm.password }
  } else {
    formRef = phoneFormRef.value
    formData = { phone: phoneForm.phone, password: phoneForm.password }
  }

  try {
    await formRef.validate()
  } catch (error) {
    return
  }

  loading.value = true
  try {
    const res = await login(formData)
    userStore.setToken(res.data.token)
    userStore.setUser(res.data.user)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 128px);
  background: #f5f5f5;
}
.login-card {
  width: 400px;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
h2 {
  text-align: center;
  margin-bottom: 30px;
}
.login-btn {
  width: 100%;
  margin-top: 20px;
}
.register-link {
  text-align: center;
  margin-top: 15px;
  color: #666;
}
.register-link a {
  color: #409eff;
  text-decoration: none;
}
</style>