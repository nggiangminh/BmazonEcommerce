import React, { useState } from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'
import { useAuth } from '../../context/AuthContext.js'

const Login = () => {
  const { login, loading, error } = useAuth()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const onSubmit = async (e) => {
    e.preventDefault()
    const res = await login(username, password)
    if (res.ok) {
      window.location.hash = '/'
    }
  }
  return (
    <div className="page">
      <Container>
        <div style={{ maxWidth: 420, margin: '0 auto' }}>
          <Card title="Login">
            <form onSubmit={onSubmit} style={{ display: 'grid', gap: 12 }}>
              <Input label="Username" placeholder="yourusername" value={username} onChange={(e) => setUsername(e.target.value)} />
              <Input label="Password" type="password" placeholder="••••••••" value={password} onChange={(e) => setPassword(e.target.value)} />
              {error && <div className="form-error">{error}</div>}
              <Button full type="submit" disabled={loading}>{loading ? 'Signing in…' : 'Sign In'}</Button>
              <div style={{ textAlign: 'center' }}>
                <a href="#/register">Create an account</a>
              </div>
            </form>
          </Card>
        </div>
      </Container>
    </div>
  )
}

export default Login

