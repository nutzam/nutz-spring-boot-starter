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
              component: () => import('../views/users/user-list.vue'),
            },
            {
              path: 'role',
              name: 'Role',
              meta: { title: 'menus.role', icon: 'icon-role' },
              component: () => import('../views/admins/PageTypography.vue'),
            },
            {
              path: 'permission',
              name: 'Permission',
              meta: { title: 'menus.permission', icon: 'icon-permission' },
              component: () => import('../views/admins/PageTypography.vue'),
            },
          ],
        },
        {
          path: '/dictionary',
          name: 'Dictionary',
          meta: { title: 'menus.dictionary', icon: 'icon-dictionary', flat: true },
          component: () => import('../views/admins/PageInfo.vue'),
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
