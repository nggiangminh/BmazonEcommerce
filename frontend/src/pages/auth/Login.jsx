import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'

const Login = () => {
  return (
    <div className="page">
      <Container>
        <div style={{ maxWidth: 420, margin: '0 auto' }}>
          <Card title="Login">
            <div style={{ display: 'grid', gap: 12 }}>
              <Input label="Email" type="email" placeholder="you@example.com" />
              <Input label="Password" type="password" placeholder="••••••••" />
              <Button full>Sign In</Button>
              <div style={{ textAlign: 'center' }}>
                <a href="#/register">Create an account</a>
              </div>
            </div>
          </Card>
        </div>
      </Container>
    </div>
  )
}

export default Login

