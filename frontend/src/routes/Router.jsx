import React, { useEffect, useState } from 'react'
import Home from '../pages/Home'
import Catalog from '../pages/Catalog'
import ProductDetail from '../pages/ProductDetail'
import Cart from '../pages/Cart'
import Wishlist from '../pages/Wishlist'
import Login from '../pages/auth/Login'
import Register from '../pages/auth/Register'
import Address from '../pages/checkout/Address'
import Payment from '../pages/checkout/Payment'
import Review from '../pages/checkout/Review'
import Orders from '../pages/orders/Orders'
import Profile from '../pages/profile/Profile'

const routes = {
  '': Home,
  '/': Home,
  '/catalog': Catalog,
  '/product': ProductDetail,
  '/cart': Cart,
  '/wishlist': Wishlist,
  '/login': Login,
  '/register': Register,
  '/checkout/address': Address,
  '/checkout/payment': Payment,
  '/checkout/review': Review,
  '/orders': Orders,
  '/order': React.lazy(() => import('../pages/orders/OrderDetail')),
  '/profile': Profile,
  '/admin': React.lazy(() => import('../pages/admin/Dashboard')),
  '/admin/products': React.lazy(() => import('../pages/admin/Products')),
  '/admin/orders': React.lazy(() => import('../pages/admin/Orders')),
  '/admin/user': React.lazy(() => import('../pages/admin/Users')),
  '/admin/users': React.lazy(() => import('../pages/admin/Users')),
}

const Router = () => {
  const [path, setPath] = useState(window.location.hash.slice(1) || '/')

  useEffect(() => {
    const onHashChange = () => setPath(window.location.hash.slice(1) || '/')
    window.addEventListener('hashchange', onHashChange)
    return () => window.removeEventListener('hashchange', onHashChange)
  }, [])

  const Component = routes[path] || Home

  return (
    <React.Suspense fallback={<div className="container" style={{ padding: 16 }}>Loading…</div>}>
      <Component />
    </React.Suspense>
  )
}

export default Router

