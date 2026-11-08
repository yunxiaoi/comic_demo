import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { useUserStore } from '@/store'

const request = axios.create({
    baseURL: '/api',
    timeout: 10000
})

request.interceptors.request.use(
    config => {
        const userStore = useUserStore()
        if (userStore.token) {
            config.headers.Authorization = `Bearer ${userStore.token}`
        }
        return config
    },
    error => Promise.reject(error)
)

request.interceptors.response.use(
    response => {
        const res = response.data
        if (res.code !== 200) {
            ElMessage.error(res.msg || '请求失败')
            if (res.code === 401) {
                const userStore = useUserStore()
                userStore.logout()
                router.push('/login')
            }
            return Promise.reject(new Error(res.msg || 'Error'))
        }
        return res
    },
    error => {
        ElMessage.error(error.message || '网络错误')
        return Promise.reject(error)
    }
)

export default request