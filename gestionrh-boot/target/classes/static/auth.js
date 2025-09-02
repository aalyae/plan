// Auth helper shared by all pages
export function getToken() { return localStorage.getItem('jwt') || null; }
export function setToken(t) { localStorage.setItem('jwt', t); }
export function clearToken() { localStorage.removeItem('jwt'); }

export function authFetch(input, init = {}) {
  const token = getToken();
  const headers = new Headers(init.headers || {});
  if (token) headers.set('Authorization', `Bearer ${token}`);
  return fetch(input, { ...init, headers });
}

export function ensureLoggedIn() {
  if (!getToken()) {
    window.location.href = '/index.html';
  }
}

