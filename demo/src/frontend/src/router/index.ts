import { createRouter, createWebHashHistory } from 'vue-router';
import BasicLayout from '../layouts/BasicLayout.vue';
import BlankLayout from '../layouts/BlankLayout.vue';
import UserLayout from '@/layouts/user-layout.vue';

export default createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'index',
      meta: { title: 'menus.home' },
      component: BasicLayout,
      redirect: '/dashboard',
      children: [
        {
          path: '/dashboard',
          name: 'Dashboard',
          meta: { title: 'menus.dashboard', icon: 'icon-dashboard' },
          component: () => import('../views/dashboard/dashboard-panel.vue'),
        },
        {
          path: '/acl',
          name: 'ACL',
          meta: { title: 'menus.acl', icon: 'icon-acl', flat: true },
          component: BlankLayout,
          redirect: () => ({ name: 'Users' }),
          children: [
            {
              path: 'users',
              name: 'Users',
              meta: { title: 'menus.users', icon: 'icon-users' },
              component: () => import('../views/acl/users/user-list.vue'),
            },
            {
              path: 'role',
              name: 'Role',
              meta: { title: 'menus.role', icon: 'icon-role' },
              component: () => import('../views/acl/role/role-list.vue'),
            },
            {
              path: 'permission',
              name: 'Permission',
              meta: { title: 'menus.permission', icon: 'icon-permission' },
              component: () => import('../views/acl/permission/permission-management.vue'),
            },
          ],
        },
        {
          path: '/dictionary',
          name: 'Dictionary',
          meta: { title: 'menus.dictionary', icon: 'icon-dictionary', flat: true },
          component: () => import('../views/dictionary/dictionary-management.vue'),
        },
        {
          path: '/version',
          name: 'version',
          meta: { title: 'menus.version', icon: 'icon-version' },
          component: () => import('../views/version/version-info.vue'),
        },
        {
          path: '/menu-sync',
          name: 'MenuSync',
          meta: { title: 'menus.menuSync', icon: 'icon-menu' },
          component: () => import('../views/menu-sync.vue'),
        },
      ],
    },
    {
      path: '/user',
      name: 'user',
      component: UserLayout,
      redirect: '/user/login',
      children: [
        {
          path: '/user/login',
          name: 'Login',
          component: () => import('../views/auth/user-login.vue'),
        },
      ],
    },
  ],
});
