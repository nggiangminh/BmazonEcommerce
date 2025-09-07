import React, { useEffect, useMemo, useState } from 'react'
import authService from '../services/auth'
import { setAuthToken, clearAuthToken, setUnauthorizedHandler } from '../services/api'
import { AuthContext } from './AuthContext'

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(() => localStorage.getItem('auth_token') || null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (token) setAuthToken(token)
  }, [token])

  useEffect(() => {
    setUnauthorizedHandler(() => () => {
      // Auto-logout on 401/403
      setUser(null)
      setToken(null)
      clearAuthToken()
      localStorage.removeItem('auth_token')
      if (!window.location.hash.startsWith('#/login')) {
        window.location.hash = '/login'
      }
    })
    return () => setUnauthorizedHandler(null)
  }, [])

  const login = async (username, password) => {
    setLoading(true); setError(null)
    try {
      const data = await authService.login({ username, password })
      const jwt = data.token || data.accessToken
      const profile = data.user || null
      if (jwt) {
        setToken(jwt)
        localStorage.setItem('auth_token', jwt)
      }
      setUser(profile)
      return { ok: true }
    } catch (e) {
      setError(e.message || 'Login failed')
      return { ok: false, error: e }
    } finally {
      setLoading(false)
    }
  }

  const signup = async (payload) => {
    setLoading(true); setError(null)
    try {
      const data = await authService.signup(payload)
      return { ok: true, data }
    } catch (e) {
      setError(e.message || 'Signup failed')
      return { ok: false, error: e }
    } finally {
      setLoading(false)
    }
  }

  const logout = () => {
    setUser(null)
    setToken(null)
    clearAuthToken()
    localStorage.removeItem('auth_token')
    window.location.hash = '/login'
  }

  const value = useMemo(() => ({ user, token, loading, error, login, signup, logout }), [user, token, loading, error])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)


