import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/Home.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/Register.vue')
    },
    {
        path: '/comic/:id',
        name: 'ComicDetail',
        component: () => import('@/views/ComicDetail.vue')
    },
    {
        path: '/user/info',
        name: 'UserProfile',
        component: () => import('@/views/UserProfile.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/reader/:comicId/:chapterId',
        name: 'ComicReader',
        component: () => import('@/views/ComicReader.vue')
    },
    {
        path: '/category',
        name: 'Category',
        component: () => import('@/views/Category.vue')
    },
    {
        path: '/update',
        name: 'UpdateSchedule',
        component: () => import('@/views/UpdateSchedule.vue')
    },
    {
        path: '/rank',
        name: 'Rank',
        component: () => import('@/views/Rank.vue')
    },
    {
        path: '/forum',
        name: 'Forum',
        component: () => import('@/views/Forum.vue')
    },
    {
        path: '/post/:id',
        name: 'PostDetail',
        component: () => import('@/views/PostDetail.vue')
    },
    {
        path: '/my-posts',
        name: 'MyPosts',
        component: () => import('@/views/MyPosts.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/my-orders',
        name: 'MyOrders',
        component: () => import('@/views/MyOrders.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/my-favorites',
        name: 'MyFavorites',
        component: () => import('@/views/MyFavorites.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/my-comments',
        name: 'MyComments',
        component: () => import('@/views/MyComments.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/read-history',
        name: 'ReadHistory',
        component: () => import('@/views/ReadHistory.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/recharge',
        name: 'Recharge',
        component: () => import('@/views/Recharge.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/notice',
        name: 'Notice',
        component: () => import('@/views/Notice.vue')
    },
    {
        path: '/notice/:id',
        name: 'NoticeDetail',
        component: () => import('@/views/NoticeDetail.vue')
    },
    {
        path: '/news/:id',
        name: 'NewsDetail',
        component: () => import('@/views/NewsDetail.vue')
    },
    {
        path: '/admin',
        component: () => import('@/views/admin/Layout.vue'),
        meta: { requiresAuth: true, requiresAdmin: true },
        children: [
            { path: '', redirect: 'dashboard' },
            { path: 'dashboard', component: () => import('@/views/admin/Dashboard.vue'), meta: { title: '首页' } },
            { path: 'comic-genre', component: () => import('@/views/admin/ComicGenre.vue'), meta: { title: '漫画题材' } },
            { path: 'comic-region', component: () => import('@/views/admin/ComicRegion.vue'), meta: { title: '漫画地区' } },
            { path: 'comic-list', component: () => import('@/views/admin/ComicList.vue'), meta: { title: '漫画信息' } },
            { path: 'comic-chapters/:comicId?', component: () => import('@/views/admin/ComicChapters.vue'), meta: { title: '漫画章节' } },
            { path: 'update-schedule', component: () => import('@/views/admin/UpdateSchedule.vue'), meta: { title: '更新计划' } },
            { path: 'orders', component: () => import('@/views/admin/Orders.vue'), meta: { title: '订购信息' } },
            { path: 'news', component: () => import('@/views/admin/News.vue'), meta: { title: '漫画资讯' } },
            { path: 'post-category', component: () => import('@/views/admin/PostCategory.vue'), meta: { title: '帖子分类' } },
            { path: 'forum-posts', component: () => import('@/views/admin/ForumPosts.vue'), meta: { title: '论坛帖子' } },
            { path: 'comments', component: () => import('@/views/admin/Comments.vue'), meta: { title: '评论信息' } },
            { path: 'recharges', component: () => import('@/views/admin/Recharges.vue'), meta: { title: '充值记录' } },
            { path: 'carousel', component: () => import('@/views/admin/Carousel.vue'), meta: { title: '轮播图信息' } },
            { path: 'notices', component: () => import('@/views/admin/Notices.vue'), meta: { title: '系统公告' } },
            { path: 'users', component: () => import('@/views/admin/Users.vue'), meta: { title: '用户信息' } },
            { path: 'profile', component: () => import('@/views/admin/AdminProfile.vue'), meta: { title: '个人信息' } }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const userStore = useUserStore()
    const token = userStore.token
    const role = userStore.user?.role

    if (to.meta.requiresAuth && !token) {
        next('/login')
    } else if (to.meta.requiresAdmin && role !== 1) {
        next('/')
    } else {
        next()
    }
})

export default router