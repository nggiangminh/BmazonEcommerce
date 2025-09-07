import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Badge from '../../components/common/Badge'

const Orders = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Your Orders">
          <div className="card" style={{ padding: 12 }}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr auto', alignItems: 'center' }}>
              <div>
                <strong>#ORD-0001</strong>
                <div style={{ color: 'var(--color-muted)' }}>Placed Jan 1, 2025 — $64.97</div>
              </div>
              <Badge>Processing</Badge>
            </div>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Orders

