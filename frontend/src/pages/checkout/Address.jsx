import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'

const Address = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Shipping Address">
          <div className="grid" style={{ gridTemplateColumns: '1fr', gap: 12 }}>
            <Input label="Full name" placeholder="John Doe" />
            <Input label="Phone" placeholder="(555) 000-0000" />
            <Input label="Street" placeholder="123 Warm Ave" />
            <div className="grid" style={{ gridTemplateColumns: '1fr 1fr', gap: 12 }}>
              <Input label="City" placeholder="Cozytown" />
              <Input label="ZIP" placeholder="12345" />
            </div>
            <a href="#/checkout/payment"><Button>Continue to Payment</Button></a>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Address

