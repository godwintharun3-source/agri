import React, { useState, useEffect } from 'react';
import { Droplets, CheckCircle, AlertTriangle, RefreshCw, ArrowRight, Activity, Filter, Send } from 'lucide-react';
import { waterService } from '../services/waterService';
import StatusBadge from '../components/StatusBadge';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

export default function WaterQuality() {
  const [latest, setLatest] = useState(null);
  const [readings, setReadings] = useState([]);
  const [loading, setLoading] = useState(true);

  // Form for custom reading submission
  const [inputPh, setInputPh] = useState('7.2');
  const [inputTds, setInputTds] = useState('210');
  const [inputTemp, setInputTemp] = useState('25.0');
  const [inputTurb, setInputTurb] = useState('Low');

  useEffect(() => {
    fetchWaterData();
  }, []);

  const fetchWaterData = async () => {
    try {
      const current = await waterService.getLatestStatus();
      setLatest(current);
      const history = await waterService.getReadings();
      setReadings(history.reverse());
    } catch (err) {
      console.error("Error fetching water data:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateReading = async (e) => {
    e.preventDefault();
    try {
      const newReading = {
        ph: parseFloat(inputPh),
        tds: parseFloat(inputTds),
        temperature: parseFloat(inputTemp),
        turbidity: inputTurb
      };
      const res = await waterService.createReading(newReading);
      setLatest(res);
      await fetchWaterData();
    } catch (err) {
      console.error("Error submitting reading:", err);
    }
  };

  const chartData = readings.map((r) => ({
    time: new Date(r.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    pH: r.ph,
    TDS: r.tds,
    Temp: r.temperature
  }));

  return (
    <div className="page-wrapper">
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            💧 DIGITAL WATER QUALITY MONITOR
          </h1>
          <p className="page-subtitle">
            Real-time telemetry for pH, TDS, Temperature, and Turbidity with intelligent irrigation decision support
          </p>
        </div>
        <button className="btn-secondary" onClick={fetchWaterData}>
          <RefreshCw size={14} /> Refresh Data
        </button>
      </div>

      {/* Water Telemetry Flow Diagram */}
      <div className="agri-card" style={{ marginBottom: '28px', background: 'linear-gradient(135deg, #0d281e 0%, var(--bg-card) 100%)' }}>
        <h3 style={{ fontSize: '1.1rem', marginBottom: '16px', color: 'var(--text-main)' }}>
          🔄 Water Telemetry & Irrigation Decision Flow
        </h3>
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-around', gap: '12px', flexWrap: 'wrap' }}>
          <div style={{ textAlignment: 'center', padding: '12px 16px', background: 'var(--bg-panel)', borderRadius: '12px', border: '1px solid var(--border-color)', minWidth: '140px' }}>
            <Droplets size={24} color="#3b82f6" />
            <h4 style={{ fontSize: '0.85rem', marginTop: '6px' }}>1. Water Resource</h4>
            <p style={{ fontSize: '0.72rem', color: 'var(--text-muted)' }}>Surface / Groundwater</p>
          </div>
          <ArrowRight color="var(--primary)" size={20} />
          <div style={{ textAlignment: 'center', padding: '12px 16px', background: 'var(--bg-panel)', borderRadius: '12px', border: '1px solid var(--border-color)', minWidth: '140px' }}>
            <Activity size={24} color="#06b6d4" />
            <h4 style={{ fontSize: '0.85rem', marginTop: '6px' }}>2. Multi-Sensor</h4>
            <p style={{ fontSize: '0.72rem', color: 'var(--text-muted)' }}>pH, TDS, Temp, Turb</p>
          </div>
          <ArrowRight color="var(--primary)" size={20} />
          <div style={{ textAlignment: 'center', padding: '12px 16px', background: 'var(--bg-panel)', borderRadius: '12px', border: '1px solid var(--border-color)', minWidth: '140px' }}>
            <Filter size={24} color="#a855f7" />
            <h4 style={{ fontSize: '0.85rem', marginTop: '6px' }}>3. Data Handling</h4>
            <p style={{ fontSize: '0.72rem', color: 'var(--text-muted)' }}>Spring Boot API</p>
          </div>
          <ArrowRight color="var(--primary)" size={20} />
          <div style={{ textAlignment: 'center', padding: '12px 16px', background: 'var(--bg-panel)', borderRadius: '12px', border: '1px solid var(--border-color)', minWidth: '140px' }}>
            <StatusBadge status={latest?.status?.name || 'GOOD'} />
            <h4 style={{ fontSize: '0.85rem', marginTop: '6px' }}>4. Water Status</h4>
            <p style={{ fontSize: '0.72rem', color: 'var(--text-muted)' }}>Evaluated Quality</p>
          </div>
          <ArrowRight color="var(--primary)" size={20} />
          <div style={{ textAlignment: 'center', padding: '12px 16px', background: 'var(--bg-panel)', borderRadius: '12px', border: '1px solid var(--primary)', minWidth: '150px' }}>
            <CheckCircle size={24} color="#22c55e" />
            <h4 style={{ fontSize: '0.85rem', marginTop: '6px' }}>5. Irrigation Decision</h4>
            <p style={{ fontSize: '0.72rem', color: '#86efac', fontWeight: 600 }}>Actionable Guidance</p>
          </div>
        </div>
      </div>

      {/* 4 Parameter Cards */}
      <div className="metrics-grid" style={{ marginBottom: '28px' }}>
        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>pH Level</span>
            <span style={{ fontSize: '0.75rem', color: '#86efac' }}>Safe: 6.5 - 8.5</span>
          </div>
          <div style={{ fontSize: '2.2rem', fontWeight: 800, color: 'var(--text-main)' }}>
            {latest ? latest.ph : '7.02'}
          </div>
          <p style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>Acidity / Alkalinity Balance</p>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>TDS (Total Dissolved Solids)</span>
            <span style={{ fontSize: '0.75rem', color: '#86efac' }}>Safe: 100 - 500 ppm</span>
          </div>
          <div style={{ fontSize: '2.2rem', fontWeight: 800, color: '#3b82f6' }}>
            {latest ? `${latest.tds} ppm` : '210 ppm'}
          </div>
          <p style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>Dissolved Mineral Concentration</p>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>Water Temperature</span>
            <span style={{ fontSize: '0.75rem', color: '#86efac' }}>Safe: 15 - 30 °C</span>
          </div>
          <div style={{ fontSize: '2.2rem', fontWeight: 800, color: '#f59e0b' }}>
            {latest ? `${latest.temperature} °C` : '26 °C'}
          </div>
          <p style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>Thermal Status</p>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>Turbidity</span>
            <span style={{ fontSize: '0.75rem', color: '#86efac' }}>Safe: Low</span>
          </div>
          <div style={{ fontSize: '2.2rem', fontWeight: 800, color: '#a855f7' }}>
            {latest ? latest.turbidity : 'Low'}
          </div>
          <p style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>Suspended Solids / Clarity</p>
        </div>
      </div>

      {/* Water Recommendation Banner */}
      <div className="agri-card" style={{ marginBottom: '28px', borderLeft: '4px solid var(--primary)' }}>
        <div style={{ display: 'flex', alignItems: 'flex-start', gap: '16px' }}>
          <div style={{ padding: '10px', background: 'rgba(16, 185, 129, 0.15)', borderRadius: '12px' }}>
            <CheckCircle size={28} color="var(--primary)" />
          </div>
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '6px' }}>
              <h3 style={{ fontSize: '1.1rem', color: 'var(--text-main)' }}>
                Water Quality Decision: <span style={{ color: 'var(--primary)' }}>{latest?.status?.name || 'GOOD'}</span>
              </h3>
              <StatusBadge status={latest?.status?.name || 'GOOD'} />
            </div>
            <p style={{ fontSize: '0.95rem', color: 'var(--text-main)', lineHeight: 1.5 }}>
              {latest?.recommendation || 'Suitable for direct field irrigation.'}
            </p>
          </div>
        </div>
      </div>

      {/* Grid: Charts & Manual Input Form */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
        {/* Trend Chart */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
            📊 Historical Water Parameter Trends
          </h3>
          <div style={{ width: '100%', height: 260 }}>
            <ResponsiveContainer>
              <AreaChart data={chartData}>
                <CartesianGrid strokeDasharray="3 3" stroke="#233152" />
                <XAxis dataKey="time" stroke="#94a3b8" fontSize={11} />
                <YAxis stroke="#94a3b8" fontSize={11} />
                <Tooltip contentStyle={{ backgroundColor: '#131b2e', borderColor: '#233152', borderRadius: '8px' }} />
                <Area type="monotone" dataKey="pH" stroke="#10b981" fill="#10b98122" />
                <Area type="monotone" dataKey="TDS" stroke="#3b82f6" fill="#3b82f622" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Custom Reading Submission Form */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
            ➕ Test Custom Water Reading
          </h3>
          <form onSubmit={handleCreateReading}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
              <div className="form-group">
                <label className="form-label">pH Level</label>
                <input
                  type="number"
                  step="0.1"
                  className="form-input"
                  value={inputPh}
                  onChange={(e) => setInputPh(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label className="form-label">TDS (ppm)</label>
                <input
                  type="number"
                  className="form-input"
                  value={inputTds}
                  onChange={(e) => setInputTds(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label className="form-label">Temperature (°C)</label>
                <input
                  type="number"
                  step="0.1"
                  className="form-input"
                  value={inputTemp}
                  onChange={(e) => setInputTemp(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label className="form-label">Turbidity</label>
                <select
                  className="form-select"
                  value={inputTurb}
                  onChange={(e) => setInputTurb(e.target.value)}
                >
                  <option value="Low">Low</option>
                  <option value="Medium">Medium</option>
                  <option value="High">High</option>
                </select>
              </div>
            </div>

            <button type="submit" className="btn-primary" style={{ width: '100%', marginTop: '16px', justifyContent: 'center' }}>
              <Send size={16} /> Evaluate Water Quality & Save
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
