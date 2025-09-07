import React, { useState } from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'
import { useAuth } from '../../context/AuthContext.js'

const Register = () => {
  const { signup, loading, error } = useAuth()
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const onSubmit = async (e) => {
    e.preventDefault()
    if (password !== confirmPassword) return
    const res = await signup({ firstName, lastName, username, email, password })
    if (res.ok) {
      window.location.hash = '/login'
    }
  }
  return (
    <div className="page">
      <Container>
        <div style={{ maxWidth: 480, margin: '0 auto' }}>
          <Card title="Create Account">
            <form onSubmit={onSubmit} style={{ display: 'grid', gap: 12 }}>
              <div className="grid" style={{ gridTemplateColumns: '1fr 1fr', gap: 12 }}>
                <Input label="First name" placeholder="John" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                <Input label="Last name" placeholder="Doe" value={lastName} onChange={(e) => setLastName(e.target.value)} />
              </div>
              <Input label="Username" placeholder="yourusername" value={username} onChange={(e) => setUsername(e.target.value)} />
              <Input label="Email" type="email" placeholder="you@example.com" value={email} onChange={(e) => setEmail(e.target.value)} />
              <Input label="Password" type="password" placeholder="••••••••" value={password} onChange={(e) => setPassword(e.target.value)} />
              <Input label="Confirm Password" type="password" placeholder="••••••••" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
              {error && <div className="form-error">{error}</div>}
              <Button full type="submit" disabled={loading}>{loading ? 'Creating…' : 'Sign Up'}</Button>
              <div style={{ textAlign: 'center' }}>
                <a href="#/login">Already have an account? Login</a>
              </div>
            </form>
          </Card>
        </div>
      </Container>
    </div>
  )
}

export default Register

