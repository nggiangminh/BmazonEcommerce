const API_BASE = (typeof import.meta !== 'undefined' && import.meta.env && import.meta.env.VITE_API_BASE) || 'http://localhost:3030/api'

let authToken = null
let onUnauthorized = null

export const setAuthToken = (token) => {
  authToken = token
}

export const clearAuthToken = () => {
  authToken = null
}

export const setUnauthorizedHandler = (handler) => {
  onUnauthorized = typeof handler === 'function' ? handler : null
}

const buildHeaders = (headers = {}) => {
  const base = {
    'Content-Type': 'application/json',
    ...headers,
  }
  if (authToken) {
    base['Authorization'] = `Bearer ${authToken}`
  }
  return base
}

export const apiFetch = async (path, { method = 'GET', body, headers } = {}) => {
  const url = `${API_BASE}${path}`
  const options = {
    method,
    headers: buildHeaders(headers),
  }
  if (body !== undefined) {
    options.body = typeof body === 'string' ? body : JSON.stringify(body)
  }

  const res = await fetch(url, options)
  const isJson = res.headers.get('content-type')?.includes('application/json')
  const data = isJson ? await res.json().catch(() => null) : await res.text()
  if (!res.ok) {
    if ((res.status === 401 || res.status === 403) && onUnauthorized) {
      try { onUnauthorized() } catch {}
    }
    const message = (isJson && data && (data.message || data.error)) || res.statusText
    const error = new Error(message || 'Request failed')
    error.status = res.status
    error.data = data
    throw error
  }
  return data
}

export default {
  apiFetch,
  setAuthToken,
  clearAuthToken,
  setUnauthorizedHandler,
}


