/* eslint-disable @typescript-eslint/no-explicit-any */
/**
 * @desc 分页查询系统功能
 */
import { defaultSuccess, defaultError, http } from "@/plugins/axios";

export class SearchParams {
  constructor(
    public key?: string,
    public page?: number,
    public size?: number
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
    data: acl.Pagination<acl.Module>;
    ext: ObjectMap;
    state: "SUCCESS" | "FAIL" | "EXCEPTION";
    errors?: Array<string>;
  }) => void = defaultSuccess,
  fail: (error: string) => void = defaultError
): Promise<void> {
  return http({
    method: "get",
    url: `/modules`,

    params,
  })
    .then((data) => success(data as any))
    .catch((error) => fail(error));
}
