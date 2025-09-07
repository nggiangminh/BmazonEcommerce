import React from 'react'

const Loader = ({ size = 24 }) => {
  const style = { width: size, height: size }
  return <span className="loader" style={style} aria-label="Loading" />
}

export default Loader

