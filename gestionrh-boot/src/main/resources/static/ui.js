export function h(tag, attrs = {}, ...children) {
  const el = document.createElement(tag);
  Object.entries(attrs || {}).forEach(([k, v]) => {
    if (k === 'class') el.className = v; else if (k === 'html') el.innerHTML = v; else el.setAttribute(k, v);
  });
  children.flat().forEach(c => {
    if (c == null) return;
    el.appendChild(typeof c === 'string' ? document.createTextNode(c) : c);
  });
  return el;
}

export function renderTable(container, columns, rows) {
  container.innerHTML = '';
  const table = h('table', { class: 'table' });
  const thead = h('thead', {}, h('tr', {}, columns.map(c => h('th', {}, c.label))));
  const tbody = h('tbody');
  rows.forEach(row => {
    const tr = h('tr');
    columns.forEach(c => tr.appendChild(h('td', {}, c.render ? c.render(row) : (row[c.key] ?? ''))));
    tbody.appendChild(tr);
  });
  table.append(thead, tbody);
  container.appendChild(table);
}

export function showToast(msg, ok = true) {
  let el = document.querySelector('#toast');
  if (!el) {
    el = h('div', { id: 'toast' });
    document.body.appendChild(el);
  }
  el.textContent = msg;
  el.className = ok ? 'toast ok' : 'toast err';
  setTimeout(() => { el.className = 'toast'; el.textContent = ''; }, 2500);
}

