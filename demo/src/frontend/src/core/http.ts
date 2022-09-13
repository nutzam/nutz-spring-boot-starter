export interface Http {
  prefix: string; // primary color of ant design
  timeout: number;
}

export const http: Http = {
  prefix: import.meta.env.VITE_HTTP_PREFIX,
  timeout: 5 * 1000,
};
