import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Button from '../../components/common/Button'

const Review = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Review Order">
          <div style={{ display: 'grid', gap: 12 }}>
            <div className="card" style={{ padding: 12 }}>
              <strong>Items</strong>
              <ul>
                <li>Product 1 x1 - $19.99</li>
                <li>Product 2 x2 - $39.98</li>
              </ul>
            </div>
            <div className="card" style={{ padding: 12 }}>
              <strong>Ship to</strong>
              <div>123 Warm Ave, Cozytown 12345</div>
            </div>
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <a href="#/checkout/payment"><Button variant="ghost">Back</Button></a>
              <a href="#/orders"><Button>Place Order</Button></a>
            </div>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Review

