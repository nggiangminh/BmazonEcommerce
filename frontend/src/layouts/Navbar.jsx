import React from 'react'
import Button from '../components/common/Button'

const Navbar = () => {
  return (
    <header className="navbar">
      <div className="container navbar-inner">
        <a href="#/" className="brand">eCommerce</a>
        <nav className="nav">
          <a href="#/catalog">Catalog</a>
          <a href="#/wishlist">Wishlist</a>
          <a href="#/cart">Cart</a>
          <a href="#/orders">Orders</a>
        </nav>
        <div className="nav-actions">
          <a href="#/login"><Button size="sm">Login</Button></a>
          <a href="#/register"><Button size="sm" variant="secondary">Sign Up</Button></a>
        </div>
      </div>
    </header>
  )
}

export default Navbar

