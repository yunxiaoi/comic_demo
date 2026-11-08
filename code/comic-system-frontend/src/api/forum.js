import request from '@/utils/request'

// 帖子分类（公共）
export const getPostCategories = () => {
    return request.get('/admin/post-category/list')
}

// 论坛帖子
export const getPosts = (params) => {
    return request.get('/user/forum/posts', { params })
}

export const getPostDetail = (id) => {
    return request.get(`/user/forum/post/${id}`)
}

export const createPost = (data) => {
    return request.post('/user/forum/post', data)
}

export const updatePost = (id, data) => {
    return request.put(`/user/forum/post/${id}`, data)
}

export const deletePost = (id) => {
    return request.delete(`/user/forum/post/${id}`)
}

export const getMyPosts = () => {
    return request.get('/user/forum/my-posts')
}

// 评论
export const addComment = (data) => {
    return request.post('/user/comment/add', data)
}

export const getComments = (targetType, targetId) => {
    return request.get('/user/comment/list', { params: { targetType, targetId } })
}

export const deleteComment = (id) => {
    return request.delete(`/user/comment/delete/${id}`)
}

export const getMyComments = () => {
    return request.get('/user/comment/my-comments')
}