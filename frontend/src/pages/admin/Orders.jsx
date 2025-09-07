import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Badge from '../../components/common/Badge'

const OrdersAdmin = () => {
  const orders = [
    { id: 'ORD-0001', customer: 'John Doe', total: '$129.00', status: 'Processing', date: '2025-01-02' },
    { id: 'ORD-0002', customer: 'Jane Smith', total: '$89.50', status: 'Paid', date: '2025-01-02' },
    { id: 'ORD-0003', customer: 'Alex Lee', total: '$45.00', status: 'Shipped', date: '2025-01-01' },
  ]

  const statusTone = (s) => (s === 'Paid' ? 'secondary' : s === 'Shipped' ? 'primary' : 'muted')

  return (
    <div className="page">
      <Container>
        <div className="admin-layout">
          <aside className="sidebar">
            <strong>Admin</strong>
            <nav style={{ display: 'grid', gap: 8, marginTop: 12 }}>
              <a href="#/admin">Dashboard</a>
              <a href="#/admin/products">Products</a>
              <a href="#/admin/users">Users</a>
              <a href="#/admin/orders">Orders</a>
              <a href="#/admin/categories">Categories</a>
            </nav>
          </aside>
          <section>
            <Card title="All Orders">
              <div className="card" style={{ padding: 12 }}>
                <div style={{ display: 'grid', gridTemplateColumns: '160px 1fr 140px 140px 140px', gap: 8, fontWeight: 700 }}>
                  <div>Order ID</div>
                  <div>Customer</div>
                  <div>Date</div>
                  <div>Total</div>
                  <div>Status</div>
                </div>
                {orders.map((o) => (
                  <div key={o.id} style={{ display: 'grid', gridTemplateColumns: '160px 1fr 140px 140px 140px', gap: 8, alignItems: 'center', padding: '10px 0', borderTop: '1px solid #f0e8e0' }}>
                    <div><a href="#/order">{o.id}</a></div>
                    <div>{o.customer}</div>
                    <div>{o.date}</div>
                    <div>{o.total}</div>
                    <div><Badge tone={statusTone(o.status)}>{o.status}</Badge></div>
                  </div>
                ))}
              </div>
            </Card>
          </section>
        </div>
      </Container>
    </div>
  )
}

export default OrdersAdmin

