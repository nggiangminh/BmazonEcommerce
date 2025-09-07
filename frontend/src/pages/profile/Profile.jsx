import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'

const Profile = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Profile">
          <div className="grid" style={{ gridTemplateColumns: '1fr', gap: 12 }}>
            <Input label="Name" placeholder="Jane Doe" />
            <Input label="Email" placeholder="jane@example.com" />
            <Button style={{ width: 'fit-content' }}>Save</Button>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Profile

