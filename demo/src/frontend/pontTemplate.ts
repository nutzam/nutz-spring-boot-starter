import * as Pont from 'pont-engine';
import { BaseClass, CodeGenerator, Interface } from 'pont-engine';

/**
 * 首字母大写
 * @param str 源字符串
 * @returns 首字母大写字符串
 */
export function upperFirst(str: string): string {
  return str.charAt(0).toUpperCase() + str.slice(1);
}
/**
 * 数组去重
 * @param arr 源数组
 * @returns 去重数组
 */
export function distinct(arr: Array<unknown>): Array<unknown> {
  return Array.from(new Set([...arr]));
}
export const dsUrlMapping = {
  acl: '',
  oauth2: '',
  org: '',
};
export function getUrl(ds: string): string {
  return '';
}
export function fixInterfaceName(name: string) {
  return name.replace(/_\d?/, '');
}

/**
 * 提取路径参数
 * @param url url
 * @returns
 */
export function extractPathParameters(url: string): string[] {
  const reg = /\{(.+?)\}/;
  const reg_g = /\{(.+?)\}/g;
  const result = url.match(reg_g);
  if (result == null) {
    return [];
  } else {
    const list = result.reduce((pre: string[], item) => {
      const r = item.match(reg);
      if (r == null) {
        return pre;
      } else {
        pre.push(r[1]);
        return pre;
      }
    }, []);
    return list;
  }
}

/**
 * 求差集
 * @param arr1
 * @param arr2
 * @returns
 */
export function subSet(arr1: string[], arr2: string[]) {
  const set1 = new Set(arr1);
  const set2 = new Set(arr2);
  const subset: string[] = [];
  for (const item of set1) {
    if (!set2.has(item)) {
      subset.push(item);
    }
  }
  return subset;
}

export class FileStructures extends Pont.FileStructures {
  /**
   * 多源的文件结构
   */
  getMultipleOriginsFileStructures(): {
    'index.ts': unknown;
    'api-lock.json': string;
    'api.d.ts': unknown;
  } {
    const files = {};
    this.generators.forEach(generator => {
      const dsName = generator.dataSource.name;
      const dsFiles = this.getOriginFileStructures(generator, true);
      files[dsName] = dsFiles;
    });
    return {
      ...files,
      'index.ts': this.getDataSourcesTs.bind(this),
      'api.d.ts': this.getDataSourcesDeclarationTs.bind(this),
      'api-lock.json': this.getLockContent.bind(this),
    };
  }

  /**
   * api 插件 index.ts 生成
   * @returns
   */
  getDataSourcesTs(): string {
    const dsNames = this.generators.map(ge => ge.dataSource.name);
    const apis = dsNames
      .map(name => {
        return `${name}Api`;
      })
      .join(',');
    return `
      import type { App } from 'vue';
      ${dsNames
        .map(name => {
          return `import { default as ${name}Api, type ${upperFirst(name)}Api } from './${name}/mods';
          `;
        })
        .join('\n')}
      export interface Api {
        ${dsNames
          .map(name => {
            return `${name}Api: ${upperFirst(name)}Api;`;
          })
          .join('\n')}
          install: (app: App) => void;
      }
      export const api: Api = {
        ${apis},
        install: (app: App) => {
          app.config.globalProperties.$api = api;
        },
      };
    `;
  }
  /**
   * 生成全局类型定义
   * @returns
   */
  getDataSourcesDeclarationTs(): string {
    const dsNames = this.generators.map(ge => ge.dataSource.name);
    return `
    /**
     * mybatis-plus 分页对象
     */
    export interface IPage<T> {
      /**
       * 当前页面
       */
      current: number;
      /**
       * 总页数
       */
      pages?: number;
      /**
       * 数据记录
       */
      records?: Array<T>;
      /**
       * 分页大小
       */
      size: number;
      /**
       * 数据总条数
       */
      total?: number;
    }
    /**
     * Nutz 分页对象
     */
    export interface Pagination<T> {
      /**
       * 数据记录
       */
      dataList: Array<T>;
      /**
       * 是否首页
       */
      first?: boolean;
      /**
       * 是否尾页
       */
      last?: boolean;
      /**
       * 偏移量
       */
      offset?: number;
      /**
       * 总页数
       */
      pageCount: number;
      /**
       * 当前页码
       */
      pageNumber: number;
      /**
       * 页面大小
       */
      pageSize: number;
      /**
       * sql参数
       */
      paras: Record<string, unknown>;
      /**
       * 数据记录总数
       */
      recordCount?: number;
    }
    /**
     * VXE 数据结构
     */
    export interface VXETableSaveDTO<T> {
      /**
       * 新增记录数据
       */
      insertRecords: Array<T>;
      /**
       *  删除记录数据
       */
      removeRecords: Array<T>;
      /**
       * 更新记录数据
       */
      updateRecords: Array<T>;
    }
    /**
     * 枚举码本
     */
    export class Codebook {
      /** 码本编码 */
      code: string;

      /** 码本名称 */
      description: string;

      /** 码本值 */
      name: string;
    }

    /**
     * 全局错误
     */
    export class GlobalError {
      /** 错误码 */
      code?: number;

      /** 错误信息 */
      message?: string;
    }
    /**
     * NutMap
     */
    type NutMap<Key extends string, Value = unknown> = {
      [key in Key]: Value;
    };
    // 导入其他模块
    ${dsNames
      .map(name => {
        return `/// <reference path="./${name}/api.d.ts" />`;
      })
      .join('\n')}
    `;
  }
  /**
   * 生成单个数据源文件结构
   * @param generator 生成器
   * @param usingMultipleOrigins 是否多源
   * @returns
   */
  getOriginFileStructures(
    generator: CodeGenerator,
    usingMultipleOrigins = false,
  ): {
    [x: string]: unknown;
    mods: Record<string, unknown>;
    'api.d.ts': unknown;
  } {
    const mods = {};
    const dataSource = generator.dataSource;
    dataSource.mods.forEach(mod => {
      const currMod = {};
      //每一个接口一个文件
      mod.interfaces.forEach(inter => {
        currMod[fixInterfaceName(inter.name) + '.ts'] = generator.getInterfaceContent.bind(generator, inter);
      });
      //接口汇总导出文件和接口声明
      currMod['index.ts'] = generator.getModIndex.bind(generator, mod);

      //每个模块生成一份
      mods[mod.name] = currMod;

      //每个模块的汇总导出文件和声明文件
      mods['index.ts'] = generator.getModsIndex.bind(generator);
    });
    const result = {
      mods: mods, //tags
      'api.d.ts': generator.getDeclaration.bind(generator), // 模块类型声明
    };
    if (!usingMultipleOrigins) {
      result['api-lock.json'] = this.getLockContent.bind(this);
    }
    return result;
  }
}

export default class MyGenerator extends CodeGenerator {
  /**
   * 生成 namespace 类型定义
   * @returns
   */
  getDeclaration(): string {
    return `
      ${this.getCommonDeclaration()}
      ${this.getBaseClassesInDeclaration()}
    `;
  }
  getBaseClassesInDeclaration(): string {
    const content = `declare namespace ${this.dataSource.name || 'defs'} {
      ${this.dataSource.baseClasses
        .filter(item => {
          return (
            item.name !== 'Result' &&
            !item.name.startsWith('Result') &&
            item.name !== 'Pagination' &&
            !item.name.startsWith('Pagination') &&
            item.name !== 'IPage' &&
            !item.name.startsWith('IPage') &&
            item.name !== 'VXETable' &&
            !item.name.startsWith('VXETable') &&
            item.name !== 'NutMap' &&
            !item.name.startsWith('NutMap') &&
            item.name !== 'Codebook' &&
            !item.name.startsWith('Codebook') &&
            item.name !== 'GlobalError' &&
            !item.name.startsWith('GlobalError')
          );
        })
        .map(
          base => `
         ${this.getBaseClassInDeclaration(base)}
      `,
        )
        .join('\n')}
    }
    `;
    return content.replace(/defs./g, '');
  }

  getBaseClassInDeclaration(base: BaseClass): string {
    if (base.templateArgs && base.templateArgs.length) {
      return `
      /**
       * ${base.description || base.name}
       */
      export class ${base.name}<${base.templateArgs.map((_, index) => `T${index} = any`).join(', ')}> {
        ${base.properties.map(prop => prop.toPropertyCode(Pont.Surrounding.typeScript, true)).join('\n')}
      }
      `;
    }
    return `
    /**
    * ${base.description || base.name}
    */
    export class ${base.name} {
      ${base.properties.map(prop => prop.toPropertyCode(Pont.Surrounding.typeScript, true)).join('\n')}
    }
    `;
  }

  getParamsTypeDec(inter: Interface): string {
    if (!inter.parameters.filter(p => p.in === 'query').length) {
      return '';
    }
    return `export interface ${upperFirst(inter.name)}Params {
               ${inter.parameters
                 .filter(param => param.in === 'query')
                 .sort((a, b) => {
                   const x = a.required ? 1 : 0;
                   const y = b.required ? 1 : 0;
                   return y - x;
                 })
                 .map(
                   param =>
                     `/** ${param.description || param.name} */
                     ${param.name}${param.required ? '' : '?'}:${param.dataType
                       .generateCode(param.getDsName())
                       .replace(/defs./g, '')},`,
                 )
                 .join('\n')}
          }`;
  }
  getParamsDec(inter: Interface): string {
    const hasQueryParams: boolean = inter.parameters.filter(p => p.in === 'query').length > 0;
    let extPathParameters = extractPathParameters(inter.path);
    extPathParameters = subSet(
      extPathParameters,
      inter.parameters.filter(p => p.in === 'path').map(p => p.name),
    );
    return `
            ${inter.parameters
              .filter(p => p.in === 'path')
              .map(
                p =>
                  `/** ${p.description || p.name} */
                  ${p.name}:${p.dataType.generateCode(p.getDsName()).replace(/defs./g, '')},`,
              )
              .join('\n')}
              ${extPathParameters.map(p => `${p} : string,`).join('\n')}
            ${inter.parameters
              .filter(p => p.in === 'body')
              .map(
                p =>
                  `/** ${p.description || '请求体'} */
                  ${p.name}:${p.dataType.generateCode(p.getDsName()).replace(/defs./g, '')},`,
              )
              .join('\n')}
            ${hasQueryParams ? `params: ${upperFirst(inter.name)}Params,` : ``}`;
  }
  genDataType(dsName: string, inter: Interface) {
    const typeName = inter.response.typeName;
    if (!typeName || typeName === 'ResultVoid' || typeName === 'Void') {
      return 'void';
    }
    if (typeName === 'string' || typeName === 'number' || typeName === 'boolean') {
      return typeName;
    }
    const dataType = typeName.replace('Result', '').replace('IPage', '').replace('Pagination', '').replace('List', '');
    const originType = `${dsName}.${dataType}`;
    let genDataType = typeName.replace(dataType, originType).replace('Result', '');
    if (typeName.indexOf('IPage') >= 0) {
      genDataType = genDataType.replace('IPage', 'IPage<') + '>';
    }
    if (typeName.indexOf('Pagination') >= 0) {
      genDataType = genDataType.replace('Pagination', 'Pagination<') + '>';
    }
    if (typeName.indexOf('VXETableSaveDTO') >= 0) {
      genDataType = genDataType.replace('VXETableSaveDTO', 'VXETableSaveDTO<') + '>';
    }
    if (typeName.indexOf('List') >= 0) {
      genDataType = genDataType.replace('List', 'Array<') + '>';
    }
    if (typeName.indexOf('Array') >= 0) {
      genDataType = inter.response.generateCode(dsName).replace('defs.', '');
      if (genDataType.indexOf('Codebook') >= 0) {
        genDataType = genDataType.replace(dsName + '.', '');
      }
    }
    return genDataType;
  }
  getInterfaceContent(inter: Interface) {
    const bodyParmas = inter.getBodyParamsCode();
    const bodyParam = inter.parameters.find(p => p.in === 'body');
    const bodyParmaName = bodyParam ? bodyParam.name : '';
    const hasQueryParams = inter.parameters.filter(p => p.in === 'query').length > 0;
    const hasResult = inter.response.typeName.indexOf('Result') >= 0;
    const hasIPage = inter.response.typeName.indexOf('IPage') >= 0;
    const hasPagination = inter.response.typeName.indexOf('Pagination') >= 0;
    const hasCodebook = inter.response.typeArgs[0] && inter.response.typeArgs[0].typeName.indexOf('Codebook') >= 0;
    const hasVxe = !!inter.parameters.find(p => p.dataType.typeName.indexOf('VXETableSaveDTO') >= 0);
    // const defTypes = this.getAllRefTypeNames(inter);
    const imports = [
      hasResult ? 'Result' : null,
      hasIPage ? 'IPage' : null,
      hasPagination ? 'Pagination' : null,
      hasVxe ? 'VXETableSaveDTO' : null,
      hasCodebook ? 'Codebook' : null,
    ].filter(item => item !== null);
    //url: \`/${getUrl(inter.getDsName())}${inter.path.replace(/{/g, '${')}\`,
    return `
    /**
     * @desc ${inter.description}
     */
    import {defaultSuccess, defaultError, http} from '@/plugins/axios';
    import type { AxiosResponse } from 'axios';
    ${imports.length ? `import type { ${imports.join(',')} } from '@/api/api';` : ''};
    ${hasQueryParams ? `import type { ${upperFirst(inter.name)}Params } from './index';` : ''}

    export default async function(
      ${this.getParamsDec(inter)}
      success: (data: ${this.genDataType(inter.getDsName(), inter)}) => void = defaultSuccess,
      fail: (error: string) => void = defaultError
    ) :Promise<void>{
      return http({
          method: '${inter.method}',
          url: \`${inter.path.replace(/{/g, '${')}\`,
          ${bodyParmas ? `data: ${bodyParmaName},` : ''}
          ${hasQueryParams ? `params,` : ''}
        }).then((data: AxiosResponse<${this.genDataType(inter.getDsName(), inter)}, unknown>) => {
          success(data.data);
        })
        .catch((error: string) => fail(error));
    }
   `;
  }

  getModIndex(mod: Pont.Mod): string {
    const name: string = upperFirst(mod.name);
    const hasResult = mod.interfaces.find(inter => inter.response.typeName.indexOf('Result') >= 0);
    const hasIPage = mod.interfaces.find(inter => inter.response.typeName.indexOf('IPage') >= 0);
    const hasPagination = mod.interfaces.find(inter => inter.response.typeName.indexOf('Pagination') >= 0);
    const hasVxe = mod.interfaces.find(inter =>
      inter.parameters.find(p => p.dataType.typeName.indexOf('VXETableSaveDTO') >= 0),
    );
    const hasCodebook = mod.interfaces.find(
      inter => inter.response.typeArgs[0] && inter.response.typeArgs[0].typeName.indexOf('Codebook') >= 0,
    );
    // const defTypes = this.getAllRefTypeNames(inter);
    const imports = [
      hasResult ? 'Result' : null,
      hasIPage ? 'IPage' : null,
      hasPagination ? 'Pagination' : null,
      hasVxe ? 'VXETableSaveDTO' : null,
      hasCodebook ? 'Codebook' : null,
    ].filter(item => item !== null);
    return `
      /**
       * @description ${mod.description}
       *
       */
      ${mod.interfaces
        .map(inter => {
          return `import  ${fixInterfaceName(inter.name)} from './${fixInterfaceName(inter.name)}';`;
        })
        .join('\n')}
      ${imports.length ? `import type { ${imports.join(',')} } from '@/api/api';` : ''};

      ${mod.interfaces
        .filter(inter => inter.parameters.filter(p => p.in === 'query').length > 0)
        .map(inter => {
          return this.getParamsTypeDec(inter);
        })
        .join('\n')}

      export interface ${name}Api {
         ${mod.interfaces
           .map(inter => {
             return `
             /** ${inter.description || inter.name} */
              ${fixInterfaceName(inter.name)}: (
              ${this.getParamsDec(inter)}
              success?: (data: ${this.genDataType(inter.getDsName(), inter)}) => void,
              fail?: (error: string) => void
            ) => void,`;
           })
           .join('\n')}
      }
      export default {
        ${mod.interfaces.map(inter => fixInterfaceName(inter.name)).join(', \n')}
      } as ${name}Api
    `;
  }
  getModsIndex(): string {
    const apiseg = `
      export interface ${upperFirst(this.dataSource.name)}Api {
          ${this.dataSource.mods.map(mod => `${mod.name}:  ${upperFirst(mod.name)}Api`).join(', \n')}
      }
    `;
    const exportseg = `export default {
      ${this.dataSource.mods.map(mod => mod.name).join(', \n')}
    } as ${upperFirst(this.dataSource.name)}Api;`;
    return `
      ${this.dataSource.mods
        .map(mod => {
          return `
          import ${mod.name}, {type ${upperFirst(mod.name)}Api} from './${mod.name}';
          `;
        })
        .join('\n')}
      ${apiseg}
      ${exportseg}
    `;
  }
}
