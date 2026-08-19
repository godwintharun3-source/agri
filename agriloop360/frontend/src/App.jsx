import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import WaterQuality from './pages/WaterQuality';
import SoilFertilizer from './pages/SoilFertilizer';
import SmartComposting from './pages/SmartComposting';
import FoodPreservation from './pages/FoodPreservation';
import CropManagement from './pages/CropManagement';
import AlertsPage from './pages/AlertsPage';
import ReportsPage from './pages/ReportsPage';
import ProfileSettings from './pages/ProfileSettings';
import LandingPage from './pages/LandingPage';
import Login from './pages/Login';
import Register from './pages/Register';
import { authService } from './services/authService';

export default function App() {
  const [user, setUser] = useState(authService.getCurrentUser() || { name: 'Agri Admin', email: 'admin@agriloop360.com' });

  const handleLogout = () => {
    authService.logout();
    setUser(null);
  };

  const handleLoginSuccess = (userData) => {
    setUser(userData);
  };

  return (
    <Router>
      <Routes>
        {/* Public Landing & Auth Pages */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
        <Route path="/register" element={<Register onLoginSuccess={handleLoginSuccess} />} />

        {/* Protected Application Layout */}
        <Route
          path="/*"
          element={
            <div className="app-container">
              <Sidebar user={user} onLogout={handleLogout} />
              <div className="main-content">
                <Navbar />
                <Routes>
                  <Route path="/dashboard" element={<Dashboard />} />
                  <Route path="/water-quality" element={<WaterQuality />} />
                  <Route path="/soil-fertilizer" element={<SoilFertilizer />} />
                  <Route path="/smart-composting" element={<SmartComposting />} />
                  <Route path="/food-preservation" element={<FoodPreservation />} />
                  <Route path="/crop-management" element={<CropManagement />} />
                  <Route path="/alerts" element={<AlertsPage />} />
                  <Route path="/reports" element={<ReportsPage />} />
                  <Route path="/settings" element={<ProfileSettings />} />
                  <Route path="*" element={<Navigate to="/dashboard" replace />} />
                </Routes>
              </div>
            </div>
          }
        />
      </Routes>
    </Router>
  );
}
