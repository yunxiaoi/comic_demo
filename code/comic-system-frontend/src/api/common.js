import request from '@/utils/request'

// 公共接口（轮播图、公告、资讯等）
export const getCarousels = () => {
    return request.get('/common/carousels')
}

export const getNotices = () => {
    return request.get('/common/notices')
}

export const getNoticeDetail = (id) => {
    return request.get(`/common/notice/${id}`)
}

export const getNewsList = (params) => {
    return request.get('/common/news', { params })
}

export const getNewsDetail = (id) => {
    return request.get(`/common/news/${id}`)
}
export const getPostCategories = () => {
    return request.get('/common/post-categories')
}
export const getGenres = () => {
    return request.get('/common/genres')
}

export const getRegions = () => {
    return request.get('/common/regions')
}
export const getPosts = (params) => {
    return request.get('/common/posts', { params })
}