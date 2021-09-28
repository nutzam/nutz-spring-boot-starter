/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 新增字典分组
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  group: dictionary.Group,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: dictionary.Group;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "post",
    url: `/group`,
    data: group,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
