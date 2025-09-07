import React from 'react'
import Container from '../layouts/Container'
import Card from '../components/common/Card'
import Input from '../components/common/Input'
import Button from '../components/common/Button'
import Cart from '../components/common/icons/Cart'
import Heart from '../components/common/icons/Heart'

const Catalog = () => {
  return (
    <div className="page">
      <Container>
        <div className="grid" style={{ gridTemplateColumns: '1fr', gap: 16 }}>
          <aside className="sidebar">
            <h3 style={{ marginTop: 0 }}>Filters</h3>
            <Input label="Search" placeholder="Search products" />
            <Input label="Category" placeholder="All" />
            <Input label="Price min" type="number" placeholder="0" />
            <Input label="Price max" type="number" placeholder="1000" />
            <Button full>Apply</Button>
          </aside>

          <section>
            <div className="grid grid-cols-2 grid-cols-3 grid-cols-4">
              {Array.from({ length: 12 }).map((_, idx) => (
                <Card key={idx} title={`Item ${idx + 1}`} subtitle="$19.99">
                  <div style={{ height: 160, background: '#fff3e6', borderRadius: 10 }} />
                  <div style={{ display: 'flex', gap: 8, marginTop: 12, justifyContent: 'center' }}>
                    <Button size="sm" className="icon-btn" aria-label="Add to cart"><Cart /></Button>
                    <Button size="sm" variant="secondary" className="icon-btn" aria-label="Add to wishlist"><Heart /></Button>
                  </div>
                </Card>
              ))}
            </div>
          </section>
        </div>
      </Container>
    </div>
  )
}

export default Catalog

