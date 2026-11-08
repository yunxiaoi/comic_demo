import request from '@/utils/request'

// 用户信息
export const getUserInfo = () => {
    return request.get('/user/info')
}

export const updateUserInfo = (data) => {
    return request.put('/user/info', data)
}

export const changePassword = (data) => {
    return request.put('/user/password', data)
}

// 收藏
export const addFavorite = (comicId) => {
    return request.post('/user/favorite/add', null, { params: { comicId } })
}

export const removeFavorite = (comicId) => {
    return request.delete('/user/favorite/remove', { params: { comicId } })
}

export const getFavorites = () => {
    return request.get('/user/favorite/list')
}

export const checkFavorite = (comicId) => {
    return request.get('/user/favorite/check', { params: { comicId } })
}

// 阅读记录
export const getReadHistory = () => {
    return request.get('/user/history/list')
}

export const clearReadHistory = () => {
    return request.delete('/user/history/clear')
}

// 订单
export const buyComic = (comicId) => {
    return request.post('/user/order/buy', null, { params: { comicId } })
}

export const getOrders = () => {
    return request.get('/user/order/list')
}

// 充值
export const recharge = (data) => {
    return request.post('/user/recharge/do', data)
}

export const getRechargeRecords = () => {
    return request.get('/user/recharge/records')
}
export const uploadAvatar = (formData) => {
    return request.post('/user/upload-avatar', formData)
}
export const uploadImage = (formData) => {
    return request.post('/user/upload/image', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}
