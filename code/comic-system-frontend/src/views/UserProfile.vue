<template>
  <div class="user-profile">
    <div class="container">
      <h1>个人中心</h1>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" disabled />
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              action="#"
              :auto-upload="false"
              :on-change="handleAvatarChange"
              :limit="1"
              list-type="picture"
          >
            <el-button size="small">上传头像</el-button>
          </el-upload>
          <div v-if="form.avatar" class="avatar-preview">
            <img :src="form.avatar" style="width: 60px; height: 60px; border-radius: 50%" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProfile">保存修改</el-button>
        </el-form-item>
      </el-form>

      <el-divider />

      <h2>修改密码</h2>
      <el-form :model="pwdForm" :rules="pwdRules" ref="pwdFormRef" label-width="80px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="pwdForm.oldPassword" type="password" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwdForm.newPassword" type="password" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="pwdForm.confirmPassword" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleChangePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo, updateUserInfo, changePassword } from '@/api/user'
import { uploadCarouselImage } from '@/api/admin'

const form = ref({})
const pwdForm = ref({ oldPassword: '', newPassword: '', confirmPassword: '' })
const pwdFormRef = ref()

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
        if (value !== pwdForm.value.newPassword) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      }, trigger: 'blur' }
  ]
}

const loadUserInfo = async () => {
  const res = await getUserInfo()
  form.value = res.data
}

const handleAvatarChange = async (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  const res = await uploadAvatar(formData)  // 或 uploadCarouselImage
  console.log('上传返回的URL：', res.data)   // 例如 /uploads/xxx.jpg
  form.value.avatar = res.data
}

const updateProfile = async () => {
  await updateUserInfo({ nickname: form.value.nickname, avatar: form.value.avatar })
  ElMessage.success('保存成功')
}

const handleChangePassword = async () => {
  await pwdFormRef.value.validate()
  await changePassword({ oldPassword: pwdForm.value.oldPassword, newPassword: pwdForm.value.newPassword })
  ElMessage.success('密码修改成功，请重新登录')
}


onMounted(loadUserInfo)
</script>

<style scoped>
.user-profile {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 0 20px;
  background: #fff;
  border-radius: 8px;
  padding: 30px;
}
.avatar-preview {
  margin-top: 10px;
}
</style>