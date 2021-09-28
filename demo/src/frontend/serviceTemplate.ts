/* eslint-disable @typescript-eslint/no-explicit-any */
import * as Pont from "pont-engine";
import { BaseClass, CodeGenerator, Interface } from "pont-engine";

export function upperFirst(str: string): string {
  return str.charAt(0).toUpperCase() + str.slice(1);
}
export function distinct(arr: Array<any>): Array<any> {
  return Array.from(new Set([...arr]));
}
export class FileStructures {
  constructor(
    private generators: CodeGenerator[],
    private usingMultipleOrigins: boolean
  ) {}

  getMultipleOriginsFileStructures(): unknown {
    const files = {};
    this.generators.forEach((generator) => {
      const dsName = generator.dataSource.name;
      const dsFiles = this.getOriginFileStructures(generator, true);

      files[dsName] = dsFiles;
    });

    return {
      ...files,
      "index.ts": this.getDataSourcesTs.bind(this),
      "api.d.ts": this.getDataSourcesDeclarationTs.bind(this),
      "api-lock.json": this.getLockContent.bind(this),
    };
  }

  getBaseClassesInDeclaration(
    originCode: string,
    dsName: string,
    usingMultipleOrigins: boolean
  ): string {
    if (usingMultipleOrigins) {
      return `
      /* eslint-disable @typescript-eslint/no-unused-vars */
      /* eslint-disable @typescript-eslint/no-explicit-any */

      declare namespace ${dsName} {
        export ${originCode}
        
        export class Pagination<T0 = any> {
          /** dataList */
          dataList: Array<T0>;
        
          /** first */
          first?: boolean;
        
          /** last */
          last?: boolean;
        
          /** offset */
          offset?: number;
        
          /** pageCount */
          pageCount?: number;
        
          /** pageNumber */
          pageNumber?: number;
        
          /** pageSize */
          pageSize?: number;
        
          /** paras */
          paras?: ObjectMap<any, any>;
        
          /** recordCount */
          recordCount?: number;
        }
        
        export class Result<T0 = any> {
          /** 响应数据 */
          data: T0;
        
          /** 错误信息列表 */
          errors?: Array<string>;
        
          /** 响应扩展数据 */
          ext?: ObjectMap<any, any>;
        
          /** 响应状态 */
          state: "SUCCESS" | "FAIL" | "EXCEPTION";
        
          /** success */
          success?: boolean;
        }
        
        export class VXETableSaveDTO<T0 = any> {
          /** 新增记录数据 */
          insertRecords: Array<T0>;
        
          /** 删除记录数据 */
          removeRecords: Array<T0>;
        
          /** 更新记录数据 */
          updateRecords: Array<T0>;
        }
      };
      `;
    }

    return `
      declare ${originCode}
    `;
  }

  getModsDeclaration(
    originCode: string,
    usingMultipleOrigins: boolean
  ): string {
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

  getOriginFileStructures(
    generator: CodeGenerator,
    usingMultipleOrigins = false
  ): unknown {
    const mods = {};
    const dataSource = generator.dataSource;

    dataSource.mods.forEach((mod) => {
      const currMod = {};

      mod.interfaces.forEach((inter) => {
        currMod[inter.name + ".ts"] = generator.getInterfaceContent.bind(
          generator,
          inter
        );
        currMod["index.ts"] = generator.getModIndex.bind(generator, mod);
      });
      mods[mod.name] = currMod;

      mods["index.ts"] = generator.getModsIndex.bind(generator);
    });

    generator.getBaseClassesInDeclaration =
      this.getBaseClassesInDeclaration.bind(
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
      // 'index.ts': generator.getIndex.bind(generator),
      "api.d.ts": generator.getDeclaration.bind(generator),
    };

    if (!usingMultipleOrigins) {
      result["api-lock.json"] = this.getLockContent.bind(this);
    }

    return result;
  }

  getFileStructures(): unknown {
    if (this.usingMultipleOrigins || this.generators.length > 1) {
      return this.getMultipleOriginsFileStructures();
    } else {
      return this.getOriginFileStructures(this.generators[0]);
    }
  }

  getDataSourcesTs(): string {
    const dsNames = this.generators.map((ge) => ge.dataSource.name);
    const apis = dsNames
      .map((name) => {
        return `${name}Api`;
      })
      .join(",");
    return `
      import { PluginObject } from 'vue';
      
      ${dsNames
        .map((name) => {
          return `import { default as ${name}Api, ${upperFirst(
            name
          )}Api } from './${name}/mods';
          `;
        })
        .join("\n")}

      export interface Api {
        ${dsNames
          .map((name) => {
            return `${name}Api: ${upperFirst(name)}Api;`;
          })
          .join("\n")}
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

  getDataSourcesDeclarationTs(): string {
    const dsNames = this.generators.map((ge) => ge.dataSource.name);

    return `
    /* eslint-disable @typescript-eslint/no-explicit-any */

    declare type ObjectMap<Key extends string | number | symbol = any, Value = any> = {
      [key in Key]: Value;
    };

    ${dsNames
      .map((name) => {
        return `/// <reference path="./${name}/api.d.ts" />`;
      })
      .join("\n")}
    `;
  }

  getLockContent(): string {
    return JSON.stringify(
      this.generators.map((ge) => ge.dataSource),
      null,
      2
    );
  }
}

export default class MyGenerator extends CodeGenerator {
  /** 获取总的类型定义代码 */
  getDeclaration(): string {
    return `
      ${this.getCommonDeclaration()}

      ${this.getBaseClassesInDeclaration()}

    `;
  }

  /** 获取所有基类的类型定义代码，一个 namespace */
  getBaseClassesInDeclaration(): string {
    const content = `
      ${this.dataSource.baseClasses
        .filter((item) => {
          return (
            item.name !== "Result" &&
            item.name !== "Pagination" &&
            item.name !== "VXETableSaveDTO"
          );
        })
        .map(
          (base) => `
        export ${this.getBaseClassInDeclaration1(base)}
      `
        )
        .join("\n")}
    `;
    // const content = JSON.stringify(this.dataSource.baseClasses)
    return content.replace(/defs./g, "");
  }
  getBaseClassInDeclaration1(base: BaseClass): string {
    if (base.templateArgs && base.templateArgs.length) {
      return `class ${base.name}<${base.templateArgs
        .map((_, index) => `T${index} = any`)
        .join(",")}>{
        ${base.properties
          .map((prop: any) => {
            const arr = [];
            let type = "";
            const { typeArgs } = prop.dataType;
            if (typeArgs.length) {
              typeArgs.forEach((item: any) => {
                if (item.isDefsType) {
                  arr.push(`${base.getDsName()}.${item.typeName}`);
                } else {
                  if (item.typeName === "object" || item.typeName === "") {
                    arr.push("any");
                  } else {
                    arr.push(`${item.typeName}`);
                  }
                }
              });
            } else {
              if (prop.dataType.isDefsType) {
                arr.push(`${base.getDsName()}.${prop.dataType.typeName}`);
              }
            }
            if (arr.length) {
              if (prop.dataType.isDefsType) {
                type = arr.join(",");
              } else {
                type = `${prop.dataType.typeName}<${arr.join(",")}>`;
              }
            } else {
              type =
                prop.dataType.typeName === "object"
                  ? "Record<string,unknown>"
                  : prop.dataType.typeName;
            }
            return `
          /** ${prop.description || prop.name} */
          ${prop.name}${!prop.required ? "?" : ""}:${
              prop.dataType.typeName ? type : prop.dataType.enum.join("|")
            }
          `;
          })
          .join("\n")}
      }`;
    }
    return `class ${base.name} {
      ${base.properties
        .map((prop: any) => {
          const arr = [];
          let type = "";
          const { typeArgs } = prop.dataType;
          if (typeArgs.length) {
            typeArgs.forEach((item: any) => {
              if (item.isDefsType) {
                arr.push(`${base.getDsName()}.${item.typeName}`);
              } else {
                if (item.typeName === "object" || item.typeName === "") {
                  arr.push("any");
                } else {
                  arr.push(`${item.typeName}`);
                }
              }
            });
          } else {
            if (prop.dataType.isDefsType) {
              arr.push(`${base.getDsName()}.${prop.dataType.typeName}`);
            }
          }
          if (arr.length) {
            if (prop.dataType.isDefsType) {
              type = arr.join(",");
            } else {
              type = `${prop.dataType.typeName}<${arr.join(",")}>`;
            }
          } else {
            type =
              prop.dataType.typeName === "object"
                ? "Record<string,unknown>"
                : prop.dataType.typeName;
          }
          return `
        /** ${prop.description || prop.name} */
        ${prop.name}${!prop.required ? "?" : ""}:${
            prop.dataType.typeName ? type : prop.dataType.enum.join("|")
          }
        `;
        })
        .join("\n")}
    }`;
  }
  /** 获取接口类和基类的总的 index 入口文件代码 */
  getIndex(): string {
    const conclusion = `
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
  getBaseClassesIndex(): string {
    return this.dataSource.baseClasses
      .map((cls) => `/** export ${cls.name} */`)
      .join("\n");
  }

  getAllRefTypeNames(inter: Interface): Array<string> {
    const responseType: string[] = this.getAllDataTypes(inter.response)
      .filter((p) => p.isDefsType)
      .map((p) => upperFirst(p.typeName));
    if (inter.parameters && inter.parameters.length)
      return distinct(
        inter.parameters
          .map((p) => {
            return this.getAllDataTypes(p.dataType);
          })
          .reduce((x, y) => x.concat(y))
          .filter((p) => p.isDefsType)
          .map((p) => upperFirst(p.typeName))
      )
        .concat(responseType)
        .concat(["ObjectMap"]);
    return ["ObjectMap"].concat(responseType);
  }

  getAllDataTypes(type: Pont.StandardDataType): Array<Pont.StandardDataType> {
    if (type.typeArgs.length === 0) {
      return [type];
    } else {
      const temp = type.typeArgs
        .map((t) => this.getAllDataTypes(t))
        .reduce((x, y) => x.concat(y));
      temp.push(type);
      return temp;
    }
  }

  /** 获取接口实现内容的代码 */
  getInterfaceContent(inter: Interface): string {
    const bodyParmas = inter.getBodyParamsCode();
    const paramDec = this.getParamsTypeDec(inter);
    const bodyParam = inter.parameters.find((p) => p.in === "body");
    const bodyParmaName = bodyParam ? bodyParam.name : "";
    // const defTypes = this.getAllRefTypeNames(inter);

    const dataType = inter.response.typeArgs[0]
      ? inter.response.typeArgs[0]
          .generateCode()
          .toString()
          .replace(new RegExp("defs", "gm"), inter.getDsName())
      : "any";
    const dataType1 = dataType === "Array" ? "Record<any,unknown>" : dataType;
    return `
    /* eslint-disable @typescript-eslint/no-explicit-any */
    /**
     * @desc ${inter.description}
     */
    import {defaultSuccess, defaultError, http} from '@/plugins/axios';

     ${paramDec}

    export default async function(
      ${this.getParamsDec(inter)}
      success: ({ data, ext, state, errors }: { data: ${dataType1} , ext: ObjectMap, state: 'SUCCESS' | 'FAIL' | 'EXCEPTION', errors?: Array<string> }) => void = defaultSuccess,
      fail: (error: string) => void = defaultError
    ) :Promise<void>{
      return http({
          method: '${inter.method}',
          url: \`${inter.path.replace(/{/g, "${")}\`,
          ${bodyParmas ? `data: ${bodyParmaName},` : ""}
          ${paramDec ? `params,` : ""}
        }).then((data) => success(data as any))
        .catch((error) => fail(error));
    }
   `;
  }

  /** 获取单个模块的 index 入口文件 */
  getModIndex(mod: Pont.Mod): string {
    const name: string = upperFirst(mod.name);
    return `
      /* eslint-disable @typescript-eslint/no-explicit-any */
      /**
       * @description ${mod.description}
       */
      ${mod.interfaces
        .map((inter) => {
          const hasQueryParams =
            inter.parameters.filter((p) => p.in === "query").length > 0;
          return `import  ${inter.name} ${
            hasQueryParams ? `,{ ${upperFirst(inter.name)}Params }` : ""
          } from './${inter.name}';`;
        })
        .join("\n")}

      ${this.getTypeImport()}

      export class ${name}Api {
        constructor(
         ${mod.interfaces
           .map((inter) => {
             const dataType = inter.response.typeArgs[0]
               ? inter.response.typeArgs[0]
                   .generateCode()
                   .toString()
                   .replace(new RegExp("defs", "gm"), inter.getDsName())
               : "any";

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
        ${mod.interfaces.map((inter) => inter.name).join(", \n")}
      } as ${name}Api
    `;
  }

  /**
   * 获取模块中的参数类型声明
   * @param mod
   */
  getParamsTypeDec(inter: Interface): string {
    if (!inter.parameters.filter((p) => p.in === "query").length) {
      return "";
    }
    return `export class ${upperFirst(inter.name)}Params {
            constructor(
               ${inter.parameters
                 .filter((param) => param.in === "query")
                 .sort((a, b) => {
                   const x = a.required ? 1 : 0;
                   const y = b.required ? 1 : 0;
                   return y - x;
                 })
                 .map(
                   (param) =>
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
  getTypeImport(): string {
    return ``;
  }

  /**
   *获取接口的必传参数声明
   *
   * @param {Interface} inter
   * @returns
   * @memberof MyGenerator
   */
  getParamsDec(inter: Interface): string {
    const hasQueryParams: boolean =
      inter.parameters.filter((p) => p.in === "query").length > 0;
    return `
            ${inter.parameters
              .filter((p) => p.in === "path")
              .map(
                (p) =>
                  `${p.name}:${p.dataType
                    .generateCode(p.getDsName())
                    .replace(/defs./g, "")},`
              )
              .join("\n")}
            ${inter.parameters
              .filter((p) => p.in === "body")
              .map(
                (p) =>
                  `${p.name}:${p.dataType
                    .generateCode(p.getDsName())
                    .replace(/defs./g, "")},`
              )
              .join("\n")}
            ${
              hasQueryParams ? `params: ${upperFirst(inter.name)}Params,` : ``
            }`;
  }

  /** 获取所有模块的 index 入口文件 */
  getModsIndex(): string {
    const apiseg = `
      export class ${upperFirst(this.dataSource.name)}Api {
        constructor(
          ${this.dataSource.mods
            .map((mod) => `public ${mod.name}:  ${upperFirst(mod.name)}Api`)
            .join(", \n")}
          ) {}
      }
    `;
    const exportseg = `export default {
      ${this.dataSource.mods.map((mod) => mod.name).join(", \n")}
    } as ${upperFirst(this.dataSource.name)}Api;`;

    return `
      ${this.dataSource.mods
        .map((mod) => {
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
