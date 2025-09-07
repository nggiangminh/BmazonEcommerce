import React from 'react'
import Container from '../layouts/Container'
import Card from '../components/common/Card'
import Button from '../components/common/Button'
import Cart from '../components/common/icons/Cart'
import Trash from '../components/common/icons/Trash'

const Wishlist = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Wishlist">
          <div className="grid grid-cols-2 grid-cols-3">
            {Array.from({ length: 4 }).map((_, i) => (
              <Card key={i} title={`Saved ${i + 1}`} subtitle="$24.99">
                <div style={{ height: 120, background: '#fff3e6', borderRadius: 10 }} />
                <div style={{ display: 'flex', gap: 8, marginTop: 12 }}>
                  <Button size="sm" className="icon-btn" aria-label="Add to cart"><Cart /></Button>
                  <Button size="sm" variant="danger" className="icon-btn" aria-label="Remove"><Trash /></Button>
                </div>
              </Card>
            ))}
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Wishlist

