function copyToClipboard(text: string) {
  const input = document.createElement('input');
  input.setAttribute('value', text);
  document.body.appendChild(input);
  input.select();
  const result = document.execCommand('copy');
  document.body.removeChild(input);
  return result;
}
export const useCopyToClipboard = () => {
  return (text: string) => {
    if (typeof text === 'string' || typeof text == 'number') {
      return copyToClipboard(text);
    }
    return false;
  };
};
