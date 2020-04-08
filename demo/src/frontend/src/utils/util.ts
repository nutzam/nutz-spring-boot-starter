let Utils: any = {};

/** 参数说明：
 * 根据长度截取先使用字符串，超长部分追加…
 * str 对象字符串
 * len 目标字节长度
 * 返回值： 处理结果字符串
 */
Utils.cutString = (str: string, len: number): string => {
  if (str.length * 2 <= len) {
    return str;
  }
  let strlen = 0;
  let s = '';
  for (let i = 0; i < str.length; i++) {
    // eslint-disable-line
    s += str.charAt(i);
    if (str.charCodeAt(i) > 128) {
      strlen += 2;
      if (strlen >= len) {
        return `${s.substring(0, s.length - 1)}...`;
      }
    } else {
      strlen += 1;
      if (strlen >= len) {
        return `${s.substring(0, s.length - 2)}...`;
      }
    }
  }
  return s;
};

/**
 * 返回 n 位的随机字符串
 * @param {Number} n
 */
Utils.getRandomStr = (n: number = 6): string => {
  let str = '';
  const chars =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890';
  for (let i = 0; i < n; i += 1) {
    str += chars.charAt(Math.floor(Math.random() * 62));
  }
  return str;
};

Utils.random = function (m: number, n: number) {
  let max = m > n ? m : n;
  let min = m > n ? n : m;
  var random = max - min + 1;
  return Math.floor(Math.random() * random + min);
};

Utils.randomPassword = function () {
  return Utils.getRandomStr(Utils.random(8, 16));
};

function type(obj: any): any {
  return typeof obj;
}

/**
 * 深度遍历，深拷贝
 * @param {*} data
 */
Utils.deepClone = (data: any) => {
  const t = type(data);
  let o: any;
  let i: any;
  let ni;

  if (t === 'array') {
    o = [];
  } else if (t === 'object') {
    o = {};
  } else {
    return data;
  }

  if (t === 'array') {
    for (i = 0, ni = data.length; i < ni; i++) {
      o.push(Utils.deepClone(data[i]));
    }
    return o;
  } else if (t === 'object') {
    for (i in data) {
      o[i] = Utils.deepClone(data[i]);
    }
    return o;
  }
  return data;
};

/**
 * 设置DocumentTitle
 * @param {*} title
 */
Utils.setDocumentTitle = (title: string): void => {
  document.title = title;
  const ua = navigator.userAgent;
  const regex = /\bMicroMessenger\/([\d.]+)/;
  if (regex.test(ua) && /ip(hone|od|ad)/i.test(ua)) {
    const i = document.createElement('iframe');
    i.src = '/favicon.ico';
    i.style.display = 'none';
    i.onload = function () {
      setTimeout(function () {
        i.remove();
      }, 9);
    };
    document.body.appendChild(i);
  }
};

/**
 * 触发 window.resize
 */
Utils.triggerWindowResizeEvent = (): void => {
  const event: any = document.createEvent('HTMLEvents');
  event.initEvent('resize', true, true);
  event.eventType = 'message';
  window.dispatchEvent(event);
};

Utils.handleScrollHeader = (callback: any): void => {
  let timer: any = 0;

  let beforeScrollTop = window.pageYOffset;
  callback =
    callback ||
    function () {
      return;
    };
  window.addEventListener(
    'scroll',
    () => {
      clearTimeout(timer);
      timer = setTimeout(() => {
        let direction = 'up';
        const afterScrollTop = window.pageYOffset;
        const delta = afterScrollTop - beforeScrollTop;
        if (delta === 0) {
          return false;
        }
        direction = delta > 0 ? 'down' : 'up';
        callback(direction);
        beforeScrollTop = afterScrollTop;
      }, 50);
    },
    false
  );
};

Utils.param2Obj = (url: string) => {
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

Utils.removeLoadingAnimate = (
  id: string = '',
  timeout: number = 1500
): void => {
  if (id === '') {
    return;
  }
  setTimeout(() => {
    document.body.removeChild(document.getElementById(id) as Node);
  }, timeout);
};

export default Utils;
