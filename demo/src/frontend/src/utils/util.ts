export default {
  setDocumentTitle(title: string): void {
    document.title = title;
    const ua = navigator.userAgent;
    const regex = /\bMicroMessenger\/([\d.]+)/;
    if (regex.test(ua) && /ip(hone|od|ad)/i.test(ua)) {
      const i = document.createElement("iframe");
      i.src = "/favicon.ico";
      i.style.display = "none";
      i.onload = function () {
        setTimeout(function () {
          i.remove();
        }, 9);
      };
      document.body.appendChild(i);
    }
  },
  triggerWindowResizeEvent(): void {
    const event: Event = document.createEvent("HTMLEvents");
    event.initEvent("resize", true, true);
    window.dispatchEvent(event);
  },
};
