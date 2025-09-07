import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Button from '../../components/common/Button'

const Products = () => {
  return (
    <div className="page">
      <Container>
        <div className="admin-layout">
          <aside className="sidebar">
            <strong>Admin</strong>
            <nav style={{ display: 'grid', gap: 8, marginTop: 12 }}>
              <a href="#/admin">Dashboard</a>
              <a href="#/admin/users">Users</a>
              <a href="#/admin/orders">Orders</a>
              <a href="#/admin/categories">Categories</a>
            </nav>
          </aside>
          <section>
            <Card title="Products">
              <div style={{ marginBottom: 12 }}>
                <Button>Add Product</Button>
              </div>
              <div className="card" style={{ padding: 12 }}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 120px 120px 120px', gap: 8, fontWeight: 700 }}>
                  <div>Name</div>
                  <div>Price</div>
                  <div>Status</div>
                  <div>Actions</div>
                </div>
                {Array.from({ length: 5 }).map((_, i) => (
                  <div key={i} style={{ display: 'grid', gridTemplateColumns: '1fr 120px 120px 120px', gap: 8, alignItems: 'center', padding: '8px 0', borderTop: '1px solid #f0e8e0' }}>
                    <div>Product {i + 1}</div>
                    <div>$19.99</div>
                    <div>Active</div>
                    <div style={{ display: 'flex', gap: 8 }}>
                      <Button size="sm">Edit</Button>
                      <Button size="sm" variant="danger">Delete</Button>
                    </div>
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

export default Products

