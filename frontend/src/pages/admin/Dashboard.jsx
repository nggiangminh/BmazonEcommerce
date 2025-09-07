import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Badge from '../../components/common/Badge'

const Dashboard = () => {
  return (
    <div className="page">
      <Container>
        <div className="admin-layout">
          <aside className="sidebar">
            <strong>Admin</strong>
            <nav style={{ display: 'grid', gap: 8, marginTop: 12 }}>
              <a href="#/admin/products">Products</a>
              <a href="#/admin/users">Users</a>
              <a href="#/admin/orders">Orders</a>
              <a href="#/admin/categories">Categories</a>
            </nav>
          </aside>
          <section style={{ display: 'grid', gap: 16 }}>
            <div className="grid grid-cols-2 grid-cols-3">
              <Card title="Sales" subtitle="Today"><h2>$1,240</h2></Card>
              <Card title="Orders" subtitle="Today"><h2>23</h2></Card>
              <Card title="Users" subtitle="Active"><h2>512</h2></Card>
            </div>
            <Card title="Recent Orders">
              <div className="card" style={{ padding: 12 }}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr auto', alignItems: 'center' }}>
                  <div>
                    <strong>#ORD-0002</strong>
                    <div style={{ color: 'var(--color-muted)' }}>John Doe — $129.00</div>
                  </div>
                  <Badge tone="secondary">Paid</Badge>
                </div>
              </div>
            </Card>
          </section>
        </div>
      </Container>
    </div>
  )
}

export default Dashboard

