import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Button from '../../components/common/Button'

const Addresses = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Addresses">
          <div className="grid grid-cols-2">
            {Array.from({ length: 2 }).map((_, i) => (
              <div key={i} className="card" style={{ padding: 12 }}>
                <strong>Home</strong>
                <div>123 Warm Ave, Cozytown 12345</div>
                <div style={{ marginTop: 8, display: 'flex', gap: 8 }}>
                  <Button size="sm">Edit</Button>
                  <Button size="sm" variant="danger">Delete</Button>
                </div>
              </div>
            ))}
          </div>
          <div style={{ marginTop: 12 }}>
            <Button>Add Address</Button>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Addresses

