import Vue from 'vue';
import Router, {Route} from 'vue-router';
import UserLayout from '@/layout/UserLayout.vue';
import BasicLayout from '@/layout/BasicLayout.vue';
import '@/components/NProgress/nprogress.less'; // progress bar custom style
import {findLast} from 'lodash';
import * as types from '@/store/mutation-types';
import config from '@/config/defaultSettings';
import enUS from '@/locale/enUS';
import zhCN from '@/locale/zhCN';
import Utils from '@/utils/util';
import NProgress from 'nprogress';
import PageView from '../layout/PageView.vue';

Vue.use(Router);
NProgress.configure({showSpinner: false});
const router = new Router({
  scrollBehavior(to: any, from: any, savedPosition: any) {
    if (savedPosition) {
      return savedPosition;
    } else {
      if (from.meta.keepAlive) {
        return {x: 0, y: from.meta.savedPosition};
      }
      return {x: 0, y: to.meta.savedPosition || 0};
    }
  },
  routes: [
    {
      path: '/user',
      component: UserLayout,
      redirect: '/user/login',
      children: [
        {
          path: 'login',
          name: 'login',
          component: () => import('@/views/user/Login.vue'),
        },
        {
          path: 'register',
          name: 'register',
          component: () => import('@/views/user/Register.vue'),
        },
        {
          path: 'register-result',
          name: 'registerResult',
          component: () => import('@/views/user/RegisterResult.vue'),
        },
      ],
    },
    {
      path: '/',
      redirect: '/user',
    },
    {
      path: '/index',
      name: 'index',
      component: BasicLayout,
      meta: {
        title: 'index',
        icon: 'home',
      },
      redirect: '/dashboard',
      children: [
        {
          path: '/dashboard',
          name: 'dashboard',
          component: () => import('@/views/Dashboard.vue'),
          meta: {
            title: 'dashboard',
            keepAlive: true,
            icon: 'dashboard',
            authority: 'user.list',
          },
        },
        {
          path: '/acl',
          name: 'acl',
          redirect: '/acl/user',
          component: PageView,
          meta: {
            title: 'acl',
            keepAlive: true,
            icon: 'safety-certificate',
            authority: 'user.list',
          },
          children: [
            {
              path: '/acl/user',
              name: 'UserList',
              component: () => import('@/views/acl/user/List.vue'),
              meta: {
                title: 'userList',
                keepAlive: false,
                icon: 'user',
                authority: 'user.list',
              },
            },
            {
              path: '/acl/role',
              name: 'RoleList',
              component: () => import('@/views/acl/role/List.vue'),
              meta: {
                title: 'roleList',
                keepAlive: false,
                icon: 'eye',
                authority: 'role.list',
              },
            },
            {
              path: '/acl/module',
              name: 'ModuleList',
              component: () => import('@/views/acl/module/List.vue'),
              meta: {
                title: 'moduleList',
                keepAlive: false,
                icon: 'block',
                authority: 'module.list',
              },
            },
          ],
        },
        {
          path: '/code',
          name: 'code',
          redirect: '/code/book',
          component: PageView,
          meta: {
            title: 'code',
            keepAlive: true,
            icon: 'book',
            authority: 'user.list',
          },
          children: [
            {
              path: '/code/book',
              name: 'CodeBook',
              component: () => import('@/views/codebook/List.vue'),
              meta: {
                title: 'codeBook',
                keepAlive: false,
                icon: 'tags',
                authority: 'user.list',
              },
            },
          ],
        },
      ],
    },
  ],
});

router.beforeEach((to: Route, from: Route, next: any) => {
  const lan = Vue.ls.get(types.DEFAULT_LANGUAGE, config.language);
  if (to.path !== from.path) {
    NProgress.start();
    const record = findLast(to.matched, (record: any) => record.meta.title);
    let title;
    if (record) {
      title = `${
        lan === 'enUS'
          ? (enUS.menu as any)[record.meta.title]
          : (zhCN.menu as any)[record.meta.title]
      } - THUNDER`;
    } else {
      title = 'THUNDER';
    }
    Utils.setDocumentTitle(title);
  }

  next();
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
