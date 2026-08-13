import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  return (
    <div className="min-h-screen bg-bg-base flex flex-col items-center justify-center p-4">
      
      {/* Brand Header */}
      <div className="mb-8 flex items-center space-x-2">
        <div className="w-10 h-10 bg-brand-yellow rounded-sm flex items-center justify-center font-bold text-bg-panel text-xl">
          O
        </div>
        <span className="text-3xl font-bold tracking-wider text-brand-yellow">OENEXA</span>
      </div>

      {/* Login Card */}
      <div className="bg-bg-panel border border-border rounded-xl w-full max-w-md p-8 shadow-2xl">
        <h2 className="text-2xl font-bold text-text-primary mb-6">OENEXA Login</h2>
        
        <form className="space-y-5" onSubmit={(e) => e.preventDefault()}>
          <div>
            <label className="block text-sm text-text-secondary mb-1">Email / Phone</label>
            <input 
              type="email" 
              className="w-full bg-bg-base border border-border rounded-md px-4 py-3 text-text-primary focus:outline-none focus:border-brand-yellow transition-colors"
              value={email}
              onChange={e => setEmail(e.target.value)}
            />
          </div>

          <div>
            <label className="block text-sm text-text-secondary mb-1">Password</label>
            <input 
              type="password" 
              className="w-full bg-bg-base border border-border rounded-md px-4 py-3 text-text-primary focus:outline-none focus:border-brand-yellow transition-colors"
              value={password}
              onChange={e => setPassword(e.target.value)}
            />
          </div>

          <div className="flex items-center justify-between mt-2">
            <span className="text-brand-yellow hover:underline cursor-pointer text-sm">Forgot Password?</span>
          </div>

          <button className="w-full bg-brand-yellow text-bg-panel font-bold py-3 rounded-md hover:bg-yellow-400 transition-colors mt-6">
            Log In
          </button>
        </form>

        <div className="mt-6 text-center text-sm text-text-secondary">
          Don't have an account? <Link to="/register" className="text-brand-yellow hover:underline ml-1">Register Now</Link>
        </div>
      </div>
      
    </div>
  );
};

export default Login;
