import { BrowserRouter, Routes, Route } from 'react-router-dom';
import TradingLayout from './layouts/TradingLayout';
import TradingDashboard from './pages/TradingDashboard';
import LandingPage from './pages/LandingPage';
import Login from './pages/Login';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Landing Page Route */}
        <Route path="/" element={<LandingPage />} />
        
        {/* Auth Routes */}
        <Route path="/login" element={<Login />} />
        
        {/* Trading App Routes with the main Layout */}
        <Route path="/trade" element={<TradingLayout />}>
          <Route index element={<TradingDashboard />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
