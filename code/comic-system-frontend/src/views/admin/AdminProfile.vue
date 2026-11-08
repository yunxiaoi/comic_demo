<template>
  <div style="max-width: 500px; margin: 0 auto">
    <el-card>
      <template #header>个人信息</template>
      <el-form :model="profile" label-width="80px">
        <el-form-item label="用户名">
          <el-input :value="profile.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="profile.nickname" />
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
          <div v-if="profile.avatar" class="avatar-preview">
            <img :src="profile.avatar" style="width: 60px; height: 60px; border-radius: 50%" />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProfile">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>修改密码</template>
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
          <el-button type="primary" @click="changePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAdminInfo, updateAdminInfo, changeAdminPassword, uploadCarouselImage } from '@/api/admin'

const profile = ref({})
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

const loadProfile = async () => {
  const res = await getAdminInfo()
  profile.value = res.data
}

const handleAvatarChange = async (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  const res = await uploadCarouselImage(formData)
  profile.value.avatar = res.data
}

const updateProfile = async () => {
  await updateAdminInfo({ nickname: profile.value.nickname, avatar: profile.value.avatar })
  ElMessage.success('保存成功')
}

const changePassword = async () => {
  await pwdFormRef.value.validate()
  await changeAdminPassword({ oldPassword: pwdForm.value.oldPassword, newPassword: pwdForm.value.newPassword })
  ElMessage.success('密码修改成功，请重新登录')
  pwdForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
}

onMounted(loadProfile)
</script>

<style scoped>
.avatar-preview {
  margin-top: 10px;
}
</style>