import request from '@/utils/request'

export const sendCode = (phone) => {
    return request.post('/auth/sendCode', null, { params: { phone } })
}

export const register = (data) => {
    return request.post('/auth/register', data)
}

export const login = (data) => {
    return request.post('/auth/login', data)
}