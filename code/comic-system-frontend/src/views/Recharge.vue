<template>
  <div class="recharge">
    <div class="container">
      <h1>充值中心</h1>
      <div class="balance">当前余额：{{ userStore.user?.balance || 0 }} 元</div>
      <div class="amounts">
        <el-radio-group v-model="amount">
          <el-radio-button label="10">10元</el-radio-button>
          <el-radio-button label="50">50元</el-radio-button>
          <el-radio-button label="100">100元</el-radio-button>
          <el-radio-button label="200">200元</el-radio-button>
        </el-radio-group>
      </div>
      <el-button type="primary" @click="handleRecharge" :loading="loading">立即充值</el-button>

      <h2 style="margin-top: 40px">充值记录</h2>
      <el-table :data="records" stripe>
        <el-table-column prop="amount" label="金额" width="100" />
        <el-table-column prop="createTime" label="时间" width="160" />
      </el-table>
      <el-empty v-if="records.length === 0" description="暂无充值记录" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store'
import { recharge, getRechargeRecords } from '@/api/user'
import dayjs from 'dayjs'

const userStore = useUserStore()
const amount = ref('10')
const loading = ref(false)
const records = ref([])

const handleRecharge = async () => {
  loading.value = true
  try {
    await recharge({ amount: parseFloat(amount.value) })
    ElMessage.success('充值成功')
    // 更新用户余额
    userStore.user.balance += parseFloat(amount.value)
    userStore.setUser(userStore.user)
    // 刷新记录
    loadRecords()
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const loadRecords = async () => {
  const res = await getRechargeRecords()
  records.value = res.data.map(r => ({
    ...r,
    createTime: dayjs(r.createTime).format('YYYY-MM-DD HH:mm')
  }))
}

onMounted(loadRecords)
</script>

<style scoped>
.recharge {
  background: #f5f5f5;
  padding: 20px 0;
  min-height: calc(100vh - 128px);
}
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 0 20px;
  text-align: center;
}
h1 {
  margin-bottom: 20px;
}
.balance {
  font-size: 18px;
  margin-bottom: 30px;
  color: #666;
}
.amounts {
  margin: 30px 0;
}
</style>