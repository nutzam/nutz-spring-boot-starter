/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 创建角色
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  role: acl.Role,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: acl.Role;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "post",
    url: `/role`,
    data: role,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
