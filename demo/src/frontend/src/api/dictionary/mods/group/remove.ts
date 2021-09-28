/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 删除字典分组
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
    data: void;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "delete",
    url: `/group/${id}`,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
