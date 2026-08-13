# OENEXA UI Development Process

## 1. Project Location & Architecture Recommendation

For an enterprise-level cryptocurrency exchange like OENEXA, **it is highly recommended to create the UI in a completely separate repository/folder**, outside of the backend Java/Go monorepo.

**Why a separate repository (`C:/workspace/oenexa-ui`)?**
- **Separation of Concerns:** The frontend uses an entirely different build ecosystem (Node.js, npm/yarn, Vite/Next.js) compared to the backend (Gradle, Go). Mixing them can cause bloated CI/CD pipelines.
- **Deployment:** A separate repo allows you to easily deploy the UI using modern frontend hosting platforms like Vercel, Netlify, or AWS Amplify, while the backend relies on Kubernetes/Docker.
- **Independent Scaling:** The frontend team can iterate, test, and deploy UI updates without affecting the backend microservices.

## 2. Technology Stack

- **Framework:** React 18+ (or React 19 if using latest RCs) with **TypeScript**.
- **Build Tool:** Vite (for Lightning-fast HMR and building) or Next.js (if Server-Side Rendering / SEO is required). Given this is a trading dashboard, **Vite** is usually preferred for highly dynamic Client-Side SPAs.
- **State Management:** Zustand (for lightweight global state) + React Query (for API data fetching and caching).
- **Styling:** TailwindCSS (industry standard for rapid UI) or Vanilla CSS with CSS Modules.
- **WebSocket Client:** For real-time trading data (order book updates, price tickers), a native WebSocket or `socket.io-client` connection to the Trading Engine/Market Data service.

## 3. Scaffolding the Project (Example Workflow)

If you decide to create it in a new folder alongside the backend:

```bash
# Navigate to your workspace
cd C:/workspace

# Create the new project using Vite with React + TypeScript
npx -y create-vite@latest oenexa-ui --template react-ts

# Navigate into the new UI directory
cd oenexa-ui

# Install dependencies
npm install

# Start the development server
npm run dev
```

## 4. UI Architecture

### Folder Structure
```text
src/
├── assets/        # Static files (images, icons)
├── components/    # Reusable UI components (Buttons, Modals, Inputs)
├── features/      # Feature-based modules (Trading, Wallet, KYC, Auth)
│   ├── trading/
│   │   ├── components/  # OrderBook, Chart, OrderEntry
│   │   ├── hooks/       # useOrderBookWebSocket, usePlaceOrder
│   │   └── api/         # Axios calls to Trading Service
│   └── wallet/
├── hooks/         # Global custom React hooks
├── layouts/       # Page layouts (DashboardLayout, AuthLayout)
├── pages/         # Route entry points
├── services/      # Global API clients (Axios instances)
├── store/         # Global state (Zustand)
├── types/         # Global TypeScript interfaces
└── utils/         # Helper functions (formatting currency, dates)
```

## 5. Integrating with OENEXA Backend

- **API Gateway:** All frontend HTTP requests should be routed through the `oenexa-api-gateway` (e.g., `http://localhost:8080/api/v1/...`) to handle CORS and routing.
- **Authentication:** Use JWT tokens. Store the `access_token` in memory or secure HTTP-only cookies, and attach it as a `Bearer` token in the Axios interceptor for all requests to the backend.
- **Real-time Data:** Use WebSockets for the live Trading Order Book, as polling via HTTP is too slow for a cryptocurrency exchange.
