/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 为指定用户授权
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  id: number,
  actionInfos: Array<string>,

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
    url: `/user/${id}/grant`,
    data: actionInfos,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
