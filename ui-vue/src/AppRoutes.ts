import { createWebHistory, createRouter } from 'vue-router'
import { authService } from './services/auth/AuthService'

export enum AppRoutePaths {
  AUTH_LOGIN = '/auth/login',
  AUTH_FORGET_PASSWORD = '/auth/forget-password',
  ABOUT = '/about',
  NOT_FOUND = '/:pathMatch(.*)*',
  DASHBOARD = '/',
  BOOK_LIST = '/book',
  BOOK_ADD = '/book/add',
  BOOK_EDIT = '/book/:bookId/edit',
  BOOK_CATEGORY_LIST = '/book/category',
  BOOK_CATEGORY_ADD = '/book/category/add',
  BOOK_CATEGORY_EDIT = '/book/category/:categoryId/edit',
}

const routes = [
  { path: AppRoutePaths.AUTH_LOGIN, component: () => import('./pages/auth/LoginPage.vue') },
  { path: AppRoutePaths.NOT_FOUND, component: () => import('./pages/NotFoundPage.vue') },
  { path: AppRoutePaths.ABOUT, component: () => import('./pages/AboutPage.vue') },
  { path: AppRoutePaths.DASHBOARD,  meta: {requireAuth: true}, component: () => import('./pages/DashboardPage.vue') },
  { path: AppRoutePaths.BOOK_LIST,  meta: {requireAuth: true}, component: () => import('./pages/book/ListPage.vue') },
  { path: AppRoutePaths.BOOK_CATEGORY_LIST,  meta: {requireAuth: true}, component: () => import('./pages/book/category/ListPage.vue') },
  { path: AppRoutePaths.BOOK_CATEGORY_ADD,  meta: {requireAuth: true}, component: () => import('./pages/book/category/FormPage.vue') },
  { path: AppRoutePaths.BOOK_CATEGORY_EDIT,  meta: {requireAuth: true}, component: () => import('./pages/book/category/FormPage.vue') },
]

export const appRouters = createRouter({
  linkActiveClass: 'is-active',
  history: createWebHistory(),
  routes: routes,
})


appRouters.beforeEach((to, from, next) => {
  if (to.meta?.requireAuth && !authService.isAuthenticated()) {
    next({path: AppRoutePaths.AUTH_LOGIN})
  }
  else {
    next()
  }
})