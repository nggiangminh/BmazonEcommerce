import { apiFetch, setAuthToken, clearAuthToken } from './api'

const signup = async (payload) => {
  const data = await apiFetch('/auth/signup', { method: 'POST', body: payload })
  return data
}

const login = async (payload) => {
  const data = await apiFetch('/auth/login', { method: 'POST', body: payload })
  if (data && (data.token || data.accessToken)) {
    setAuthToken(data.token || data.accessToken)
  }
  return data
}

const logout = () => {
  clearAuthToken()
}

export default { signup, login, logout }


