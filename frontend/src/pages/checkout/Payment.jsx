import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'

const Payment = () => {
  return (
    <div className="page">
      <Container>
        <Card title="Payment">
          <div className="grid" style={{ gridTemplateColumns: '1fr', gap: 12 }}>
            <Input label="Cardholder name" placeholder="John Doe" />
            <Input label="Card number" placeholder="4111 1111 1111 1111" />
            <div className="grid" style={{ gridTemplateColumns: '1fr 1fr', gap: 12 }}>
              <Input label="Expiry" placeholder="MM/YY" />
              <Input label="CVC" placeholder="123" />
            </div>
            <a href="#/checkout/review"><Button>Review Order</Button></a>
          </div>
        </Card>
      </Container>
    </div>
  )
}

export default Payment

