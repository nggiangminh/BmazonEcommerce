import React from 'react'

const Button = ({
  children,
  variant = 'primary',
  size = 'md',
  full = false,
  disabled = false,
  onClick,
  type = 'button',
}) => {
  const base = 'btn'
  const classes = [
    base,
    `${base}--${variant}`,
    `${base}--${size}`,
    full ? `${base}--full` : '',
    disabled ? `${base}--disabled` : '',
  ]
    .filter(Boolean)
    .join(' ')

  return (
    <button className={classes} onClick={onClick} type={type} disabled={disabled}>
      {children}
    </button>
  )
}

export default Button

