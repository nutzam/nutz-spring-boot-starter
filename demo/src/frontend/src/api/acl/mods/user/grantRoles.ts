/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 为指定用户设置角色
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  id: number,
  roles: Array<number>,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: void;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "post",
    url: `/user/${id}/grant/role`,
    data: roles,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
