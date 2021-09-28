/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 获取用户详情
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  id: number,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: acl.User;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "get",
    url: `/user/${id}`,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
