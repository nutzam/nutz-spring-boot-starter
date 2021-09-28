/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 禁用指定字典项
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
    url: `/code/${id}`,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
