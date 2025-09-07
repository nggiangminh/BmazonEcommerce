import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'

const OrdersHistory = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Order History">
          <div className="card" style={{ padding: 12 }}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr auto' }}>
              <div>
                <strong>#ORD-0003</strong>
                <div style={{ color: 'var(--color-muted)' }}>5 items — $199.00</div>
              </div>
              <a href="#/orders">View</a>
            </div>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default OrdersHistory

