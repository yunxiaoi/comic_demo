import request from '@/utils/request'

// 用户端漫画接口
export const getHotSales = () => {
    return request.get('/common/hot-sales')
}

export const getHighViews = () => {
    return request.get('/common/high-views')
}

export const getFinished = () => {
    return request.get('/common/finished')
}

export const searchComics = (params) => {
    return request.get('/user/comic/search', { params })
}

export const getComicDetail = (id) => {
    return request.get(`/user/comic/detail/${id}`)
}

export const getRankByViews = () => {
    return request.get('/user/comic/rank/views')
}

export const getRankByOrders = () => {
    return request.get('/user/comic/rank/orders')
}

export const getRankByFavorites = () => {
    return request.get('/user/comic/rank/favorites')
}

export const getSchedule = () => {
    return request.get('/user/comic/schedule')
}

export const getByGenre = (genreId) => {
    return request.get(`/user/comic/byGenre/${genreId}`)
}

export const getByRegion = (regionId) => {
    return request.get(`/user/comic/byRegion/${regionId}`)
}

// 章节接口
export const getChapters = (comicId) => {
    return request.get(`/user/chapter/list/${comicId}`)
}

export const readChapter = (chapterId) => {
    return request.get(`/user/chapter/read/${chapterId}`)
}