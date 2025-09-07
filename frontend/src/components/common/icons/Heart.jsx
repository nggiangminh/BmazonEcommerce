import React from 'react'

const Heart = ({ size = 18, filled = false, color = 'currentColor' }) => {
  const common = { width: size, height: size, viewBox: '0 0 24 24', fill: 'none' }
  if (filled) {
    return (
      <svg {...common} aria-hidden="true">
        <path fill={color} d="M12 21s-6.716-4.04-9.247-7.313C.8 11.17 1.16 7.97 3.6 6.23c2.107-1.5 4.92-.96 6.4.9 1.48-1.86 4.293-2.4 6.4-.9 2.44 1.74 2.8 4.94.847 7.457C18.716 16.96 12 21 12 21z"/>
      </svg>
    )
  }
  return (
    <svg {...common} stroke={color} aria-hidden="true">
      <path strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78L12 21.23l8.84-8.84a5.5 5.5 0 0 0 0-7.78z"/>
    </svg>
  )
}

export default Heart

