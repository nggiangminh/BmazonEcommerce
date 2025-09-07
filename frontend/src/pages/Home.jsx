import React from 'react'
import Card from '../components/common/Card'
import Button from '../components/common/Button'
import Heart from '../components/common/icons/Heart'
import Cart from '../components/common/icons/Cart'
import Container from '../layouts/Container'

const Home = () => {
  return (
    <div className="page">
      <Container>
        <section className="section">
          <div className="hero card" style={{ padding: 24, display: 'grid', gap: 12 }}>
            <h1 style={{ margin: 0 }}>Warm deals for every season</h1>
            <p style={{ margin: 0, color: 'var(--color-muted)' }}>Discover curated products with a cozy touch.</p>
            <div>
              <a href="#/catalog"><Button size="lg">Shop Now</Button></a>
            </div>
          </div>
        </section>

        <section className="section">
          <h2 style={{ margin: '0 0 12px 0' }}>Featured</h2>
          <div className="grid grid-cols-2 grid-cols-3 grid-cols-4">
            {[1,2,3,4,5,6,7,8].map((i) => (
              <Card key={i} title={`Product ${i}`} subtitle="$29.99">
                <div style={{ height: 140, background: '#fff3e6', borderRadius: 10 }} />
                <div style={{ display: 'flex', gap: 8, marginTop: 12, justifyContent: 'center' }}>
                  <Button size="sm" className="icon-btn" aria-label="Add to cart"><Cart /></Button>
                  <Button size="sm" variant="secondary" className="icon-btn" aria-label="Add to wishlist"><Heart /></Button>
                </div>
              </Card>
            ))}
          </div>
        </section>
      </Container>
    </div>
  )
}

export default Home

