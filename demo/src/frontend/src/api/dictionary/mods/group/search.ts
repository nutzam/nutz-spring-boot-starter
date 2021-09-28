/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 分页加载字典分组
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export class SearchParams {
  constructor(
    public key?: string,
    public page?: number,
    public size?: number,
    public state?: boolean
  ) {}
}

export default async function (
  params: SearchParams,
  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: dictionary.Pagination<dictionary.Group>;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "get",
    url: `/groups`,

    params,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
