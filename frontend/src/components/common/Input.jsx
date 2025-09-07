import React from 'react'

const Input = ({ label, hint, error, ...props }) => {
  return (
    <div className="form-field">
      {label && (
        <label className="form-label">
          {label}
        </label>
      )}
      <input className={`form-input${error ? ' form-input--error' : ''}`} {...props} />
      {hint && !error && <div className="form-hint">{hint}</div>}
      {error && <div className="form-error">{error}</div>}
    </div>
  )}

export default Input

