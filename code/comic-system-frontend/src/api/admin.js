import request from '@/utils/request'

// 仪表盘
export const getDashboardStats = () => {
    return request.get('/admin/dashboard/stats')
}

export const getDailySales = () => {
    return request.get('/admin/dashboard/daily-sales')
}

export const getComicTypeDistribution = () => {
    return request.get('/admin/dashboard/comic-type-distribution')
}

export const getPostTypeDistribution = () => {
    return request.get('/admin/dashboard/post-type-distribution')
}

// 漫画题材
export const getGenres = () => request.get('/common/genres')

export const saveGenre = (data) => {
    return request.post('/admin/genre/save', data)
}

export const deleteGenre = (id) => {
    return request.delete(`/admin/genre/delete/${id}`)
}

// 漫画地区
export const getRegions = () => request.get('/common/regions')

export const saveRegion = (data) => {
    return request.post('/admin/region/save', data)
}

export const deleteRegion = (id) => {
    return request.delete(`/admin/region/delete/${id}`)
}

// 漫画管理
export const getComicList = (params) => {
    return request.get('/admin/comic/list', { params })
}

export const getComicById = (id) => {
    return request.get(`/admin/comic/${id}`)
}

export const saveComic = (data) => {
    return request.post('/admin/comic/save', data)
}

export const deleteComic = (id) => {
    return request.delete(`/admin/comic/delete/${id}`)
}

export const uploadCover = (formData) => {
    return request.post('/admin/comic/upload-cover', formData)
}

// 章节管理
export const getChaptersByComic = (comicId) => {
    return request.get(`/admin/chapter/list/${comicId}`)
}

export const saveChapter = (data) => {
    return request.post('/admin/chapter/save', data)
}

export const deleteChapter = (id) => {
    return request.delete(`/admin/chapter/delete/${id}`)
}

export const uploadChapterImage = (formData) => {
    return request.post('/admin/chapter/upload-image', formData)
}

// 更新计划
export const getScheduleList = (weekday) => {
    return request.get('/admin/schedule/list', { params: { weekday } })
}

export const saveSchedule = (data) => {
    return request.post('/admin/schedule/save', data)
}

export const deleteSchedule = (id) => {
    return request.delete(`/admin/schedule/delete/${id}`)
}

// 订单
export const getOrderList = (params) => {
    return request.get('/admin/order/list', { params })
}

// 漫画资讯
export const getNewsListAdmin = () => {
    return request.get('/admin/news/list')
}

export const saveNews = (data) => {
    return request.post('/admin/news/save', data)
}

export const deleteNews = (id) => {
    return request.delete(`/admin/news/delete/${id}`)
}

// 帖子分类
export const getPostCategoriesAdmin = () => {
    return request.get('/admin/post-category/list')
}

export const savePostCategory = (data) => {
    return request.post('/admin/post-category/save', data)
}

export const deletePostCategory = (id) => {
    return request.delete(`/admin/post-category/delete/${id}`)
}

// 论坛帖子审核
export const getForumPosts = (status) => {
    return request.get('/admin/forum-post/list', { params: { status } })
}

export const auditPost = (id, status) => {
    return request.put(`/admin/forum-post/audit/${id}`, null, { params: { status } })
}

export const deleteForumPost = (id) => {
    return request.delete(`/admin/forum-post/delete/${id}`)
}

// 评论管理
export const getCommentsAdmin = () => {
    return request.get('/admin/comment/list')
}

export const deleteCommentAdmin = (id) => {
    return request.delete(`/admin/comment/delete/${id}`)
}

// 充值记录
export const getRechargeList = (params) => {
    return request.get('/admin/recharge/list', { params })
}

// 轮播图
export const getCarouselsAdmin = () => {
    return request.get('/admin/carousel/list')
}

export const saveCarousel = (data) => {
    return request.post('/admin/carousel/save', data)
}

export const deleteCarousel = (id) => {
    return request.delete(`/admin/carousel/delete/${id}`)
}

export const uploadCarouselImage = (formData) => {
    return request.post('/admin/carousel/upload', formData, {
    })
}

// 公告
export const getNoticesAdmin = () => {
    return request.get('/admin/notice/list')
}

export const saveNotice = (data) => {
    return request.post('/admin/notice/save', data)
}

export const deleteNotice = (id) => {
    return request.delete(`/admin/notice/delete/${id}`)
}

// 用户管理
export const getUsers = () => {
    return request.get('/admin/user/list')
}

export const banUser = (id) => {
    return request.put(`/admin/user/ban/${id}`)
}

export const unbanUser = (id) => {
    return request.put(`/admin/user/unban/${id}`)
}

// 管理员个人信息
export const getAdminInfo = () => {
    return request.get('/admin/profile/info')
}

export const updateAdminInfo = (data) => {
    return request.put('/admin/profile/info', data)
}

export const changeAdminPassword = (data) => {
    return request.put('/admin/profile/password', data)
}
