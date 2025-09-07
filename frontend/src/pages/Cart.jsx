import React from 'react'
import Container from '../layouts/Container'
import Card from '../components/common/Card'
import Button from '../components/common/Button'
import Trash from '../components/common/icons/Trash'

const Cart = () => {
  return (
    <div className="page">
      <Container>
        <div className="grid" style={{ gridTemplateColumns: '1fr', gap: 16 }}>
          <Card title="Your Cart">
            <div className="grid grid-cols-2 grid-cols-3">
              {Array.from({ length: 4 }).map((_, i) => (
                <Card key={i} title={`Product ${i + 1}`} subtitle="$19.99">
                  <div style={{ height: 120, background: '#fff3e6', borderRadius: 10 }} />
                  <div style={{ display: 'flex', gap: 8, marginTop: 12 }}>
                    <Button size="sm" variant="danger" className="icon-btn" aria-label="Remove"><Trash /></Button>
                  </div>
                </Card>
              ))}
            </div>
          </Card>
          <Card title="Summary">
            <div style={{ display: 'grid', gap: 8 }}>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>Subtotal</span><strong>$59.97</strong>
              </div>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>Shipping</span><strong>$5.00</strong>
              </div>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>Total</span><strong>$64.97</strong>
              </div>
              <a href="#/checkout/address"><Button full>Proceed to Checkout</Button></a>
            </div>
          </Card>
        </div>
      </Container>
    </div>
  )
}

export default Cart

