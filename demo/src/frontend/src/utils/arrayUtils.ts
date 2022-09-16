/**
 * 数组是否为空
 *
 * @param array 数组
 * @returns 数组是否为空
 */
const isEmpty = (array?: Array<unknown>): boolean => {
  return !array || array.length < 1;
};

/**
 * 求并集
 *
 * @param arrayA 数组 A
 * @param arrayB 数组 B
 * @returns 并集：A ∪ B
 */
const union = (...arrays: Array<Array<string>>): Array<string> => {
  if (!arrays) {
    return [];
  }
  let result: Array<string> = [];
  arrays.forEach(array => {
    if (array) {
      result = result.concat(array);
    }
  });
  // Set 是为了去重
  return [...new Set(result)];
};

/**
 * 求差集
 *
 * @param arrayA 数组 A
 * @param arrayB 数组 B
 * @returns 差集：A - B
 */
const except = (arrayA?: Array<string>, arrayB?: Array<string>): Array<string> => {
  if (!arrayA || arrayA.length < 1) {
    return [];
  }
  if (!arrayB || arrayB.length < 1) {
    return arrayA;
  }
  return arrayA.filter(element => !arrayB.includes(element));
};

export const arrayUtils = {
  union,
  except,
  isEmpty,
};
