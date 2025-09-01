const state = {
  token: null,
};

function $(sel) { return document.querySelector(sel); }

function setLoggedIn(loggedIn) {
  $("#login-section").hidden = loggedIn;
  $("#actions").hidden = !loggedIn;
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
  if (!res.ok) {
    throw new Error('Échec de connexion');
  }
  const data = await res.json();
  return data.token;
}

async function fetchDisponibilite() {
  const res = await fetch('/api/dashboard/disponibilite', {
    headers: { 'Authorization': `Bearer ${state.token}` }
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || 'Erreur API');
  }
  return res.json();
}

function showError(msg) {
  const el = $("#login-error");
  el.textContent = msg;
  el.hidden = false;
}

function clearError() {
  const el = $("#login-error");
  el.textContent = '';
  el.hidden = true;
}

$("#login-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  clearError();
  const username = $("#username").value.trim();
  const password = $("#password").value;
  try {
    const token = await login(username, password);
    state.token = token;
    localStorage.setItem('jwt', token);
    setLoggedIn(true);
  } catch (err) {
    showError(err.message || 'Erreur');
  }
});

$("#logout").addEventListener("click", () => {
  state.token = null;
  localStorage.removeItem('jwt');
  setLoggedIn(false);
});

$("#btn-dispo").addEventListener("click", async () => {
  const out = $("#dispo-output");
  out.textContent = 'Chargement...';
  try {
    const data = await fetchDisponibilite();
    out.textContent = JSON.stringify(data, null, 2);
  } catch (err) {
    out.textContent = `Erreur: ${err.message}`;
  }
});

// Restore token if present
window.addEventListener('DOMContentLoaded', () => {
  const saved = localStorage.getItem('jwt');
  if (saved) {
    state.token = saved;
    setLoggedIn(true);
  }
});

