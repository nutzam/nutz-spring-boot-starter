import * as Pont from "pont-engine";
import { CodeGenerator, Interface } from "pont-engine";

export function upperFirst(str: string) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

export function distinct(arr: Array<any>) {
  return Array.from(new Set([...arr]));
}
export class FileStructures {
  constructor(private generators: CodeGenerator[], private usingMultipleOrigins: boolean) { }

  getMultipleOriginsFileStructures() {
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
      'api-lock.json': this.getLockContent.bind(this)
    };
  }

  getBaseClassesInDeclaration(originCode: string, dsName: string, usingMultipleOrigins: boolean) {
    return `
      declare namespace ${usingMultipleOrigins ? dsName : 'defs'} {
        export ${originCode}
      };
      `;

  }

  getModsDeclaration(originCode: string, usingMultipleOrigins: boolean) {
    if (usingMultipleOrigins) {
      return `
      declare namespace API {
        export ${originCode}
      };
      `;
    }

    return `
      declare ${originCode}
    `;
  }

  getOriginFileStructures(generator: CodeGenerator, usingMultipleOrigins = false) {
    let mods = {};
    const dataSource = generator.dataSource;

    dataSource.mods.forEach(mod => {
      const currMod = {};

      mod.interfaces.forEach(inter => {
        currMod[inter.name + '.ts'] = generator.getInterfaceContent.bind(generator, inter);
        currMod['index.ts'] = generator.getModIndex.bind(generator, mod);
      });
      mods[mod.name] = currMod;

      mods['index.ts'] = generator.getModsIndex.bind(generator);
    });

    generator.getBaseClassesInDeclaration = this.getBaseClassesInDeclaration.bind(
      this,
      generator.getBaseClassesInDeclaration(),
      generator.dataSource.name,
      usingMultipleOrigins
    );
    generator.getModsDeclaration = this.getModsDeclaration.bind(
      this,
      generator.getModsDeclaration(),
      usingMultipleOrigins
    );

    const result = {
      // 'baseClass.ts': generator.getBaseClassesIndex.bind(generator),
      mods: mods,
      'index.ts': generator.getIndex.bind(generator),
      'api.d.ts': generator.getDeclaration.bind(generator)
    };

    if (!usingMultipleOrigins) {
      result['api-lock.json'] = this.getLockContent.bind(this);
    }

    return result;
  }

  getFileStructures() {
    if (this.usingMultipleOrigins || this.generators.length > 1) {
      return this.getMultipleOriginsFileStructures();
    } else {
      return this.getOriginFileStructures(this.generators[0]);
    }
  }

  getDataSourcesTs() {
    const dsNames = this.generators.map(ge => ge.dataSource.name);
    const apis = dsNames
      .map(name => {
        return `${name}Api`;
      })
      .join(',')
      ;
    return `
      import { PluginObject } from 'vue';
      
      ${dsNames
        .map(name => {
          return `import { default as ${name}Api, ${upperFirst(name)}Api } from './${name}/mods';
          `;
        })
        .join('\n')}

      export interface Api {
        ${dsNames
        .map(name => {
          return `${name}Api: ${upperFirst(name)}Api;`;
        })
        .join('\n')}
      }

      export const ApiPlugin: PluginObject<Api> = {
        install: Vue => {
          Object.defineProperties(Vue.prototype, {
            $api: {
              get() {
                return {
                  ${apis}
                };
              },
            },
          });
        },
      };
    `;
  }

  getDataSourcesDeclarationTs() {
    const dsNames = this.generators.map(ge => ge.dataSource.name);
    return `
    type ObjectMap<Key extends string | number | symbol = any, Value = any> = {
      [key in Key]: Value;
    };
    
    ${dsNames
        .map(name => {
          return `/// <reference path="./${name}/api.d.ts" />`;
        })
        .join('\n')}
    `;
  }

  getLockContent() {
    return JSON.stringify(this.generators.map(ge => ge.dataSource), null, 2);
  }
}

export default class MyGenerator extends CodeGenerator {
  /** 获取总的类型定义代码 */
  getDeclaration() {
    return `
     type ObjectMap<Key extends string | number | symbol = any, Value = any> = {
      [key in Key]: Value;
    };

      ${this.getCommonDeclaration()}

      ${this.getBaseClassesInDeclaration()}

    `;
  }

  /** 获取所有基类的类型定义代码，一个 namespace */
  getBaseClassesInDeclaration() {
    const content = `
      ${this.dataSource.baseClasses
        .map(
          base => `
        export ${this.getBaseClassInDeclaration(base)}
      `
        )
        .join("\n")}
    `;

    return content.replace(/defs./g, "");
  }
  /** 获取接口类和基类的总的 index 入口文件代码 */
  getIndex() {
    let conclusion = `
      import {PluginObject} from 'vue';
      import api, { Api } from './mods/index';

      export const ApiPlugin: PluginObject<Api> = {
        install: Vue => {
          Object.defineProperties(Vue.prototype, {
            $api: {
              get() {
                return api;
              },
            },
          });
        },
      };

    `;

    return conclusion;
  }

  /** 获取所有基类文件代码 */
  getBaseClassesIndex() {
    return this.dataSource.baseClasses.map(cls => `/** export ${cls.name} */`).join("\n");
  }

  getAllRefTypeNames(inter: Interface): Array<string> {
    const responseType: string[] = this.getAllDataTypes(inter.response)
      .filter(p => p.isDefsType)
      .map(p => upperFirst(p.typeName));
    if (inter.parameters && inter.parameters.length)
      return distinct(
        inter.parameters
          .map(p => {
            return this.getAllDataTypes(p.dataType);
          })
          .reduce((x, y) => x.concat(y))
          .filter(p => p.isDefsType)
          .map(p => upperFirst(p.typeName))
      ).concat(responseType).concat(['ObjectMap']);
    return ['ObjectMap'].concat(responseType);
  }

  getAllDataTypes(type: Pont.StandardDataType): Array<Pont.StandardDataType> {
    if (type.typeArgs.length === 0) {
      return [type];
    } else {
      const temp = type.typeArgs
        .map(t => this.getAllDataTypes(t))
        .reduce((x, y) => x.concat(y));
      temp.push(type);
      return temp;
    }
  }

  /** 获取接口实现内容的代码 */
  getInterfaceContent(inter: Interface) {
    const bodyParmas = inter.getBodyParamsCode();
    const paramDec = this.getParamsTypeDec(inter);
    const bodyParam = inter.parameters.find(p => p.in === "body");
    const bodyParmaName = bodyParam ? bodyParam.name : "";
    // const defTypes = this.getAllRefTypeNames(inter);
    var isMultiple = !!inter.getDsName();
    const dataType = inter.response.typeArgs[0] ? inter.response.typeArgs[0].generateCode().toString().replace(new RegExp('defs', "gm"), isMultiple ? inter.getDsName() : "defs") : 'any'
    return `
    /**
     * @desc ${inter.description}
     */

    import {defaultSuccess, defaultError, http} from '@/plugins/axios';
    ${ paramDec}

    export default async function (
      ${ this.getParamsDec(inter)
      }
  success: ({ data, ext, state, errors }: { data: ${ dataType}, ext: ObjectMap, state: 'SUCCESS' | 'FAIL' | 'EXCEPTION', errors?: Array<string> }) => any = defaultSuccess,
    fail: (error: string) => any = defaultError
    ) {
  try {
    const data: any = await http({
      method: '${inter.method}',
      url: \`${inter.getDsName()}${inter.path.replace(/{/g, "${")}\`,
          ${bodyParmas ? `data: ${bodyParmaName},` : ""}
          ${paramDec ? `params,` : ""}
        });
        return success(data);
      } catch (error) {
        return fail(error);
      }
    }
   `;
  }

  /** 获取单个模块的 index 入口文件 */
  getModIndex(mod: Pont.Mod) {
    let name: string = upperFirst(mod.name);
    return `
      /**
       * @description ${mod.description}
       */
      ${mod.interfaces
        .map(inter => {
          let hasQueryParams =
            inter.parameters.filter(p => p.in === "query").length > 0;
          return `import  ${inter.name} ${
            hasQueryParams ? `,{ ${upperFirst(inter.name)}Params }` : ""
            } from './${inter.name}';`;
        })
        .join("\n")}

      ${this.getTypeImport(mod)}

      export class ${name}Api {
        constructor(
         ${mod.interfaces
        .map(inter => {
          var isMultiple = !!inter.getDsName();
          const dataType = inter.response.typeArgs[0] ? inter.response.typeArgs[0].generateCode().toString().replace(new RegExp('defs', "gm"), isMultiple ? inter.getDsName() : "defs") : 'any'

          return `
             public ${inter.name}: (
              ${this.getParamsDec(inter)}
              success?: ( { data, ext, state, errors }: { data: ${dataType} , ext: ObjectMap, state: 'SUCCESS' | 'FAIL' | 'EXCEPTION', errors?: Array<string> }) => any,
              fail?: (error: string) => any
            ) => void,`;
        })
        .join("\n")}
        ){}
      }

      export default {
        ${mod.interfaces.map(inter => inter.name).join(", \n")}
      } as ${name}Api
    `;
  }

  /**
   * 获取模块中的参数类型声明
   * @param mod
   */
  getParamsTypeDec(inter: Interface) {
    if (!inter.parameters.filter(p => p.in === "query").length) {
      return "";
    }
    return `export class ${upperFirst(inter.name)}Params {
            constructor(
               ${inter.parameters
        .filter(param => param.in === "query")
        .sort((a, b) => {
          let x = a.required ? 1 : 0;
          let y = b.required ? 1 : 0;
          return y - x;
        })
        .map(
          param =>
            `public  ${param.name}${
            param.required ? "" : "?"
            }:${param.dataType
              .generateCode(param.getDsName())
              .replace(/defs./g, "")},`
        )
        .join("\n")}
            ){}
          }`;
  }

  /**
   *获取模块中的参数导入声明
   *
   * @param {Pont.Mod} mod
   * @returns
   * @memberof MyGenerator
   */
  getTypeImport(mod: Pont.Mod) {
    return ``;
  }

  /**
   *获取接口的必传参数声明
   *
   * @param {Interface} inter
   * @returns
   * @memberof MyGenerator
   */
  getParamsDec(inter: Interface) {
    let hasQueryParams: boolean =
      inter.parameters.filter(p => p.in === "query").length > 0;
    return `
            ${inter.parameters
        .filter(p => p.in === "path")
        .map(
          p =>
            `${p.name}:${p.dataType
              .generateCode(p.getDsName())},`
        )
        .join("\n")}
            ${inter.parameters
        .filter(p => p.in === "body")
        .map(
          p =>
            `${p.name}:${p.dataType
              .generateCode(p.getDsName())},`
        )
        .join("\n")}
            ${
      hasQueryParams ? `params: ${upperFirst(inter.name)}Params,` : ``
      }`;
  }

  /** 获取所有模块的 index 入口文件 */
  getModsIndex() {
    let apiseg = `
      export class ${upperFirst(this.dataSource.name)}Api {
        constructor(
          ${this.dataSource.mods
        .map(mod => `public ${mod.name}:  ${upperFirst(mod.name)}Api`)
        .join(", \n")}
          ) {}
      }
    `;
    let exportseg = `export default {
      ${this.dataSource.mods.map(mod => mod.name).join(", \n")}
    } as ${upperFirst(this.dataSource.name)}Api;`;

    return `
      ${this.dataSource.mods
        .map(mod => {
          return `
          import ${mod.name}, {${upperFirst(mod.name)}Api} from './${mod.name}';
          `;
        })
        .join("\n")}

      ${apiseg}

      ${exportseg}
    `;
  }
}