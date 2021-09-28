import Vue from "vue";
import VueRouter, { RawLocation, Route, RouteConfig } from "vue-router";
import "@/components/NProgress/nprogress.less"; // progress bar custom style
import NProgress from "nprogress";

Vue.use(VueRouter);
NProgress.configure({ showSpinner: false });

const routes: Array<RouteConfig> = [
  {
    path: "/",
    redirect: "/user",
  },
  {
    path: "/user",
    redirect: "/user/login",
    component: () => import("@/layouts/UserLayout.vue"),
    children: [
      {
        path: "/user/login",
        name: "Login",
        component: () => import("../views/user/Login.vue"),
      },
    ],
  },
  {
    path: "/index",
    name: "index",
    component: () => import("@/layouts/BasicLayout.vue"),
    meta: {
      title: "index",
      icon: "home",
    },
    redirect: "/dashboard",
    children: [
      {
        path: "/dashboard",
        name: "dashboard",
        component: () => import("@/views/About.vue"),
        meta: {
          title: "dashboard",
          keepAlive: true,
          icon: "dashboard",
        },
      },
      {
        path: "/acl",
        name: "acl",
        redirect: "/acl/user",
        component: () => import("@/layouts/PageView.vue"),
        meta: {
          title: "acl",
          keepAlive: true,
          icon: "eye",
        },
        children: [
          {
            path: "/acl/user",
            name: "user",
            component: () => import("@/views/acl/user/UserList.vue"),
            meta: {
              title: "acl.user",
              keepAlive: true,
              icon: "user",
            },
          },
          {
            path: "/acl/role",
            name: "role",
            component: () => import("@/views/acl/role/RoleList.vue"),
            meta: {
              title: "acl.role",
              keepAlive: true,
              icon: "lock",
            },
          },
          {
            path: "/acl/permission",
            name: "permission",
            component: () => import("@/views/acl/module/ModuleList.vue"),
            meta: {
              title: "acl.permission",
              keepAlive: true,
              icon: "flag",
            },
          },
        ],
      },
      {
        path: "/dictionary",
        name: "dictionary",
        component: () => import("@/layouts/PageView.vue"),
        redirect: "/dictionary/tree",
        meta: {
          title: "dictionary",
          keepAlive: true,
          icon: "book",
        },
        children: [
          {
            path: "/dictionary/tree",
            name: "DictionaryTree",
            component: () => import("@/views/dictionary/Dictionary.vue"),
            meta: {
              title: "dictionary",
              icon: "book",
              keepAlive: false,
            },
          },
        ],
      },
    ],
  },
  {
    path: "/about",
    name: "About",
    component: () => import("../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});
router.beforeEach(
  (to: Route, from: Route, next: (to?: RawLocation | false | void) => void) => {
    if (to.path !== from.path) {
      NProgress.start();
    }
    next();
  }
);

router.afterEach(() => {
  NProgress.done();
});

export default router;
