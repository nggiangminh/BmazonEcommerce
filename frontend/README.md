## Frontend UI/UX (Phase 1)

Stack: React + CSS only. Warm palette (yellow, red, orange, brown). 10px radius across UI.

Structure
- `src/styles`: `theme.css`, `components.css`, `layouts.css`
- `src/components/common`: Button, Input, Card, Modal, Badge, Loader
- `src/layouts`: Navbar, Footer, Container
- `src/pages`: Customer, Auth, Checkout, Orders, Profile, Admin
- `src/routes/Router.jsx`: simple hash router (no external libs)

Notes
- Phase 1 contains only UI/UX; no API calls or global state.
- Navigation uses hash routes like `#/catalog`, `#/cart`.
