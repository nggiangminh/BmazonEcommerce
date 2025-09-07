import React from 'react'
import Container from '../layouts/Container'
import Card from '../components/common/Card'
import Button from '../components/common/Button'
import Heart from '../components/common/icons/Heart'
import Cart from '../components/common/icons/Cart'
import Badge from '../components/common/Badge'

const ProductDetail = () => {
  return (
    <div className="page">
      <Container>
        <div className="grid" style={{ gridTemplateColumns: '1fr', gap: 16 }}>
          <div className="card" style={{ padding: 16 }}>
            <div style={{ height: 280, background: '#fff3e6', borderRadius: 10 }} />
          </div>
          <Card title="Product Title" subtitle="In stock">
            <div style={{ display: 'flex', gap: 8, marginBottom: 8 }}>
              <Badge>New</Badge>
              <Badge tone="secondary">-20%</Badge>
            </div>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Warm and cozy vibes.</p>
            <h3 style={{ margin: '12px 0' }}>$29.99</h3>
            <div style={{ display: 'flex', gap: 8 }}>
              <Button className="icon-btn" aria-label="Add to cart"><Cart /></Button>
              <Button variant="secondary" className="icon-btn" aria-label="Add to wishlist"><Heart /></Button>
            </div>
          </Card>
        </div>
      </Container>
    </div>
  )
}

export default ProductDetail

