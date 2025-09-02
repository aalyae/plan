const state = { token: null };
const $ = (s) => document.querySelector(s);

function setLoggedIn(ok) {
  $("#login-section").hidden = ok;
  $("#actions").hidden = !ok;
}

async function login(username, password) {
  const params = new URLSearchParams();
  params.set("username", username);
  params.set("password", password);
  const res = await fetch('/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: params.toString(),
  });
  if (!res.ok) throw new Error('Échec de connexion');
  const data = await res.json();
  return data.token;
}

async function fetchDispo() {
  const res = await fetch('/api/dashboard/disponibilite', {
    headers: { Authorization: `Bearer ${state.token}` }
  });
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

const showError = (m) => { const e = $("#login-error"); e.textContent = m; e.hidden = false; };
const clearError = () => { const e = $("#login-error"); e.textContent = ''; e.hidden = true; };

$("#login-form").addEventListener("submit", async (ev) => {
  ev.preventDefault(); clearError();
  try {
    state.token = await login($("#username").value.trim(), $("#password").value);
    localStorage.setItem('jwt', state.token);
    setLoggedIn(true);
  } catch (err) { showError(err.message || 'Erreur'); }
});

$("#logout").addEventListener("click", () => {
  state.token = null; localStorage.removeItem('jwt'); setLoggedIn(false);
});

$("#btn-dispo").addEventListener("click", async () => {
  const out = $("#dispo-output"); out.textContent = 'Chargement...';
  try { out.textContent = JSON.stringify(await fetchDispo(), null, 2); }
  catch (err) { out.textContent = `Erreur: ${err.message}`; }
});

window.addEventListener('DOMContentLoaded', () => {
  const saved = localStorage.getItem('jwt');
  if (saved) { state.token = saved; setLoggedIn(true); }
});

