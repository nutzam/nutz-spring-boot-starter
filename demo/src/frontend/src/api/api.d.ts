/* eslint-disable @typescript-eslint/no-explicit-any */

declare type ObjectMap<
  Key extends string | number | symbol = any,
  Value = any
> = {
  [key in Key]: Value;
};

/// <reference path="./acl/api.d.ts" />
/// <reference path="./dictionary/api.d.ts" />
/// <reference path="./login/api.d.ts" />
