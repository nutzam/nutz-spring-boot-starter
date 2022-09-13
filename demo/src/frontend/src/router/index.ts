import { createRouter, createWebHashHistory } from 'vue-router';
import BasicLayout from '../layouts/BasicLayout.vue';
import BlankLayout from '../layouts/BlankLayout.vue';
import WelcomePage from '../views/Page1.vue';

export default createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'index',
      meta: { title: 'menus.home' },
      component: BasicLayout,
      redirect: '/welcome',
      children: [
        {
          path: '/welcome',
          name: 'welcome',
          meta: { title: 'menus.dashboard', icon: 'icon-dashboard' },
          component: WelcomePage,
        },
        {
          path: '/organization',
          name: 'organization',
          meta: { title: 'menus.organization', icon: 'icon-organization', flat: true },
          component: BlankLayout,
          redirect: () => ({ name: 'department' }),
          children: [
            {
              path: 'department',
              name: 'department',
              meta: { title: 'menus.department', icon: 'icon-department' },
              component: () => import('../views/department/department-table.vue'),
            },
            {
              path: 'user',
              name: 'user',
              meta: { title: 'menus.user', icon: 'icon-user' },
              component: () => import('../views/admins/PageTypography.vue'),
            },
          ],
        },
        {
          path: '/admins',
          name: 'admins',
          meta: { title: 'menus.management', icon: 'icon-management', flat: true },
          component: BlankLayout,
          redirect: () => ({ name: 'page1' }),
          children: [
            {
              path: 'page-1',
              name: 'page1',
              meta: { title: 'menus.p1', icon: 'icon-list' },
              component: () => import('../views/admins/PageInfo.vue'),
            },
            {
              path: 'page-2',
              name: 'page2',
              meta: { title: 'menus.p2', icon: 'icon-list' },
              component: () => import('../views/admins/PageTypography.vue'),
            },
            {
              path: 'dynamic-match/:id(\\d+)',
              name: 'dynamic-match',
              // 路由 path 默认参数再 meta.params 里
              meta: { title: 'menus.dynamic', params: { id: 1 }, icon: 'icon-list' },
              component: () => import('../views/admins/DynamicMatch.vue'),
            },
          ],
        },
        {
          path: '/demos',
          name: 'demos',
          meta: { title: 'menus.demos', icon: 'icon-demo', flat: true },
          component: BlankLayout,
          redirect: () => ({ name: 'table' }),
          children: [
            {
              path: 'table',
              name: 'table',
              meta: { title: 'menus.table', icon: 'icon-table' },
              component: () => import('../views/demos/demo-table.vue'),
            },
            {
              path: 'form',
              name: 'form',
              meta: { title: 'menus.form', icon: 'icon-form' },
              component: () => import('../views/demos/demo-form.vue'),
            },
          ],
        },
        {
          path: '/version',
          name: 'version',
          meta: { title: 'menus.version', icon: 'icon-version' },
          component: () => import('../views/Detail.vue'),
        },
      ],
    },
  ],
});
