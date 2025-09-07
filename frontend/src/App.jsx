import React from 'react'
import './styles/theme.css'
import './styles/components.css'
import './styles/layouts.css'
import Navbar from './layouts/Navbar'
import Footer from './layouts/Footer'
import Router from './routes/Router'

function App() {
  return (
    <div>
      <Navbar />
      <Router />
      <Footer />
    </div>
  )
}

export default App
