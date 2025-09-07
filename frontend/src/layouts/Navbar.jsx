import React from 'react'
import Button from '../components/common/Button'
import { useAuth } from '../context/AuthContext.js'

const Navbar = () => {
  const { token, user, logout } = useAuth()
  return (
    <header className="navbar">
      <div className="container navbar-inner">
        <a href="#/" className="brand">eCommerce</a>
        <nav className="nav">
          <a href="#/catalog">Catalog</a>
          <a href="#/wishlist">Wishlist</a>
          <a href="#/cart">Cart</a>
          <a href="#/orders">Orders</a>
          {token && user && user.role === 'ADMIN' && (
            <a href="#/admin">Dashboard</a>
          )}
          {token && user && user.role !== 'ADMIN' && (
            <a href="#/profile">Profile</a>
          )}
        </nav>
        <div className="nav-actions">
          {!token ? (
            <>
              <a href="#/login"><Button size="sm">Login</Button></a>
              <a href="#/register"><Button size="sm" variant="secondary">Sign Up</Button></a>
            </>
          ) : (
            <>
              <Button size="sm" variant="ghost" onClick={logout}>Logout</Button>
            </>
          )}
        </div>
      </div>
    </header>
  )
}

export default Navbar

