import React, { useState } from 'react';
import { Settings, User, Sliders, Save, CheckCircle } from 'lucide-react';
import { authService } from '../services/authService';

export default function ProfileSettings() {
  const currentUser = authService.getCurrentUser() || { name: 'Agri Admin', email: 'admin@agriloop360.com' };
  const [name, setName] = useState(currentUser.name);
  const [email, setEmail] = useState(currentUser.email);
  const [simulationFreq, setSimulationFreq] = useState('10');
  const [savedMsg, setSavedMsg] = useState(false);

  const handleSave = (e) => {
    e.preventDefault();
    setSavedMsg(true);
    setTimeout(() => setSavedMsg(false), 3000);
  };

  return (
    <div className="page-wrapper">
      <div className="page-header">
        <h1 className="page-title">
          ⚙️ USER PROFILE & SYSTEM CONFIGURATION
        </h1>
        <p className="page-subtitle">
          Manage system credentials, sensor simulation frequencies, and alarm thresholds
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(360px, 1fr))', gap: '24px' }}>
        {/* User Profile Card */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.1rem', marginBottom: '16px', color: 'var(--text-main)', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <User color="var(--primary)" size={20} /> Administrator Profile
          </h3>

          <form onSubmit={handleSave}>
            <div className="form-group">
              <label className="form-label">Full Name</label>
              <input type="text" className="form-input" value={name} onChange={e => setName(e.target.value)} required />
            </div>

            <div className="form-group">
              <label className="form-label">Email Address</label>
              <input type="email" className="form-input" value={email} onChange={e => setEmail(e.target.value)} required />
            </div>

            <div className="form-group">
              <label className="form-label">System Role</label>
              <input type="text" className="form-input" value="Platform Administrator (ROLE_ADMIN)" disabled style={{ opacity: 0.7 }} />
            </div>

            <button type="submit" className="btn-primary" style={{ marginTop: '14px' }}>
              <Save size={16} /> Save Profile Settings
            </button>
          </form>

          {savedMsg && (
            <div style={{ marginTop: '14px', padding: '10px', background: 'rgba(34, 197, 94, 0.15)', color: '#86efac', borderRadius: '8px', fontSize: '0.85rem', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <CheckCircle size={16} /> Preferences saved successfully.
            </div>
          )}
        </div>

        {/* System & Sensor Simulation Settings */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.1rem', marginBottom: '16px', color: 'var(--text-main)', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Sliders color="var(--accent-cyan)" size={20} /> Sensor Stream Configuration
          </h3>

          <div className="form-group">
            <label className="form-label">Simulated Tick Frequency</label>
            <select className="form-select" value={simulationFreq} onChange={e => setSimulationFreq(e.target.value)}>
              <option value="5">Every 5 Seconds (High Frequency)</option>
              <option value="10">Every 10 Seconds (Standard Demo)</option>
              <option value="30">Every 30 Seconds (Low Power)</option>
            </select>
          </div>

          <div className="form-group">
            <label className="form-label">Database Datasource Target</label>
            <input type="text" className="form-input" value="MySQL localhost:3306/agriloop360" disabled style={{ opacity: 0.7 }} />
          </div>

          <div className="form-group">
            <label className="form-label">Recommendation Engine</label>
            <input type="text" className="form-input" value="Rule-Based Agronomic Engine (Modular)" disabled style={{ opacity: 0.7 }} />
          </div>
        </div>
      </div>
    </div>
  );
}
