export enum OperationState {
  SUCCESS = 'SUCCESS',
  FAIL = 'FAIL',
  EXCEPTION = 'EXCEPTION',
}

export class Result {
  operationState: OperationState;
  errors: Array<string>;
  data: any;
  constructor(
    operationState: OperationState,
    data: any,
    errors: Array<string>
  ) {
    this.data = data;
    this.errors = errors;
    this.operationState = operationState;
  }
  static success(data: any) {
    return new Result(OperationState.SUCCESS, data, []);
  }

  static fail(error: string) {
    return new Result(OperationState.SUCCESS, {}, [error]);
  }
}

export const getQueryParameters = (options: any) => {
  const url = options.url;
  const search = url.split('?')[1];
  if (!search) {
    return {};
  }
  return JSON.parse(
    '{"' +
      decodeURIComponent(search)
        .replace(/"/g, '\\"')
        .replace(/&/g, '","')
        .replace(/=/g, '":"') +
      '"}'
  );
};

export const getBody = (options: any) => {
  return options.body && JSON.parse(options.body);
};
