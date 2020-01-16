/**
 * @desc vxeSave
 */

import {defaultSuccess, defaultError, http} from '@/plugins/axios';

export default async function(
  vxeData: defs.VXETableSaveDTO<defs.CodeBook>,

  success: ({
    data,
    ext,
    state,
    errors,
  }: {
    data: any;
    ext: ObjectMap;
    state: 'SUCCESS' | 'FAIL' | 'EXCEPTION';
    errors?: Array<string>;
  }) => any = defaultSuccess,
  fail: (error: string) => any = defaultError
) {
  try {
    const data: any = await http({
      method: 'post',
      url: `/code/vxe/save`,
      data: vxeData,
    });
    return success(data);
  } catch (error) {
    return fail(error);
  }
}
