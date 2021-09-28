import { UserModule } from "@/store/modules/user";
import { RouteConfig } from "vue-router";
export function getCurrentAuthority(): string[] {
  return UserModule.permissions;
}

export function isLogin(): boolean {
  return !!UserModule.token;
}

export function check(authority: string): boolean {
  if (!isLogin()) return false;
  if (!authority || authority == null) return true;
  return getCurrentAuthority().filter((p) => p === authority).length > 0;
}

/**
 * 菜单过滤
 * @param routers 路由树
 * @returns 菜单树
 */
export function dynamicFilteredMenus(
  routers: RouteConfig[]
): RouteConfig[] | undefined {
  return routers
    .filter((item) => check(item.meta?.authority)) //第一层筛选
    .map((item) =>
      Object.assign(item, {
        children: item.children
          ? dynamicFilteredMenus(item.children)
          : undefined,
      })
    ) //递归
    .filter((item) => {
      return !(item.children && item.children.length == 0);
    }) //有且为空,那么就代表是被筛成这样的,直接干掉之
    .map((item) => {
      if (item.children && item.children.length == 1) {
        //有且只有一个,将下级拉上来玩儿
        return item.children[0];
      }
      return item; //没有或者是有多个的
    });
}
