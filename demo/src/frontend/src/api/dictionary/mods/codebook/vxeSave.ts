/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 保存指定分组下的字典项变更情况
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export default async function (
  vxeData: dictionary.VXETableSaveDTO<dictionary.Codebook>,

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
    url: `/code/vxe/save`,
    data: vxeData,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
