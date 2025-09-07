import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'

const Register = () => {
  return (
    <div className="page">
      <Container>
        <div style={{ maxWidth: 480, margin: '0 auto' }}>
          <Card title="Create Account">
            <div style={{ display: 'grid', gap: 12 }}>
              <Input label="Name" placeholder="Your name" />
              <Input label="Email" type="email" placeholder="you@example.com" />
              <Input label="Password" type="password" placeholder="••••••••" />
              <Input label="Confirm Password" type="password" placeholder="••••••••" />
              <Button full>Sign Up</Button>
              <div style={{ textAlign: 'center' }}>
                <a href="#/login">Already have an account? Login</a>
              </div>
            </div>
          </Card>
        </div>
      </Container>
    </div>
  )
}

export default Register

