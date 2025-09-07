import React from 'react'
import Container from '../../layouts/Container'
import Card from '../../components/common/Card'
import Badge from '../../components/common/Badge'

const UsersAdmin = () => {
  const users = [
    { id: 'USR-0001', name: 'John Doe', email: 'john@example.com', role: 'ADMIN', status: 'Active', createdAt: '2025-01-01' },
    { id: 'USR-0002', name: 'Jane Smith', email: 'jane@example.com', role: 'USER', status: 'Active', createdAt: '2025-01-02' },
    { id: 'USR-0003', name: 'Alex Lee', email: 'alex@example.com', role: 'USER', status: 'Disabled', createdAt: '2024-12-28' },
  ]

  const statusTone = (s) => (s === 'Active' ? 'secondary' : 'muted')

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
            <Card title="All Users">
              <div className="card" style={{ padding: 12 }}>
                <div style={{ display: 'grid', gridTemplateColumns: '140px 1fr 1fr 120px 120px 160px', gap: 8, fontWeight: 700 }}>
                  <div>User ID</div>
                  <div>Name</div>
                  <div>Email</div>
                  <div>Role</div>
                  <div>Status</div>
                  <div>Joined</div>
                </div>
                {users.map((u) => (
                  <div key={u.id} style={{ display: 'grid', gridTemplateColumns: '140px 1fr 1fr 120px 120px 160px', gap: 8, alignItems: 'center', padding: '10px 0', borderTop: '1px solid #f0e8e0' }}>
                    <div>{u.id}</div>
                    <div>{u.name}</div>
                    <div>{u.email}</div>
                    <div><Badge tone={u.role === 'ADMIN' ? 'primary' : 'muted'}>{u.role}</Badge></div>
                    <div><Badge tone={statusTone(u.status)}>{u.status}</Badge></div>
                    <div>{u.createdAt}</div>
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

export default UsersAdmin

