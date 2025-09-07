import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Badge from '../../components/common/Badge'

const OrderDetail = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Order #ORD-0001">
          <div style={{ display: 'grid', gap: 12 }}>
            <Badge>Processing</Badge>
            <div className="card" style={{ padding: 12 }}>
              <strong>Items</strong>
              <ul>
                <li>Product 1 x1 - $19.99</li>
              </ul>
            </div>
            <div className="card" style={{ padding: 12 }}>
              <strong>Shipping</strong>
              <div>123 Warm Ave, Cozytown 12345</div>
            </div>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default OrderDetail

