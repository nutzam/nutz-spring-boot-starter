/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 根据id更新用户
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  user: acl.User,

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
    method: "put",
    url: `/user`,
    data: user,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
