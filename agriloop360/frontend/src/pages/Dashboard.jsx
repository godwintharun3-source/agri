import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Droplets, Sprout, Recycle, ShieldAlert, TreePine, Bell, RefreshCw } from 'lucide-react';
import { dashboardService } from '../services/dashboardService';
import { waterService } from '../services/waterService';
import InteractiveCycle from '../components/InteractiveCycle';
import MetricCard from '../components/MetricCard';
import StatusBadge from '../components/StatusBadge';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

export default function Dashboard() {
  const [summary, setSummary] = useState(null);
  const [waterReadings, setWaterReadings] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchDashboardData();
    const interval = setInterval(fetchDashboardData, 6000);
    return () => clearInterval(interval);
  }, []);

  const fetchDashboardData = async () => {
    try {
      const data = await dashboardService.getSummary();
      setSummary(data);

      const waterHistory = await waterService.getReadings();
      setWaterReadings(waterHistory.reverse());
    } catch (err) {
      console.error("Dashboard data load error:", err);
    } finally {
      setLoading(false);
    }
  };

  if (loading && !summary) {
    return (
      <div className="page-wrapper" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', height: '60vh' }}>
        <div style={{ textAlign: 'center', color: 'var(--text-muted)' }}>
          <RefreshCw size={36} color="var(--primary)" style={{ animation: 'spin 1s linear infinite', marginBottom: '16px' }} />
          <h3>Loading AGRILOOP 360 Telemetry...</h3>
        </div>
      </div>
    );
  }

  const latestW = summary?.latestWaterReading;
  const latestS = summary?.latestSoilReading;
  const latestC = summary?.latestCompostBatch;
  const latestF = summary?.latestFoodStorage;

  const chartData = waterReadings.slice(-10).map((r, i) => ({
    time: new Date(r.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    pH: r.ph,
    TDS: r.tds,
    Temp: r.temperature
  }));

  return (
    <div className="page-wrapper">
      {/* Top Banner */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            🌱 AGRILOOP 360
          </h1>
          <p className="page-subtitle">
            Digitalising the Agricultural Cycle — Real-time telemetry, circular optimization & intelligent guidance
          </p>
        </div>
        <button className="btn-secondary" onClick={fetchDashboardData}>
          <RefreshCw size={14} /> Refresh
        </button>
      </div>

      {/* Interactive 360 Cycle Diagram */}
      <InteractiveCycle summary={summary} />

      {/* 6 Summary Cards */}
      <h3 style={{ fontSize: '1.2rem', marginBottom: '16px', color: 'var(--text-main)' }}>
        📊 Module Telemetry Overview
      </h3>

      <div className="metrics-grid">
        <MetricCard
          title="Water Quality"
          value={latestW ? `${latestW.ph} pH` : '7.2 pH'}
          unit={latestW ? `${latestW.tds} ppm` : '210 ppm'}
          status={summary?.waterStatus || 'GOOD'}
          icon={Droplets}
          color="#3b82f6"
          updatedTime={latestW?.timestamp}
          onClick={() => navigate('/water-quality')}
        />

        <MetricCard
          title="Soil Health"
          value={latestS ? `${latestS.moisture}%` : '52%'}
          unit="Moisture"
          status={summary?.soilStatus || 'GOOD'}
          icon={Sprout}
          color="#10b981"
          updatedTime={latestS?.timestamp}
          onClick={() => navigate('/soil-fertilizer')}
        />

        <MetricCard
          title="Fertilizer Status"
          value={latestS ? `N:${latestS.nitrogen} P:${latestS.phosphorus}` : 'N:92 P:55'}
          unit="ppm"
          status={summary?.fertilizerStatus || 'OPTIMIZED'}
          icon={Sprout}
          color="#a855f7"
          updatedTime={latestS?.timestamp}
          onClick={() => navigate('/soil-fertilizer')}
        />

        <MetricCard
          title="Compost Activity"
          value={latestC ? `${latestC.temperature}°C` : '52°C'}
          unit={latestC ? `${latestC.moisture}% H₂O` : '55% H₂O'}
          status={summary?.compostStatus || 'ACTIVE'}
          icon={Recycle}
          color="#ec4899"
          updatedTime={latestC?.updatedAt}
          onClick={() => navigate('/smart-composting')}
        />

        <MetricCard
          title="Crop Health"
          value={summary?.activeCropsCount || 12}
          unit="Active Crops"
          status={summary?.cropStatus || 'HEALTHY'}
          icon={TreePine}
          color="#22c55e"
          onClick={() => navigate('/crop-management')}
        />

        <MetricCard
          title="Food Storage"
          value={latestF ? `${latestF.temperature}°C` : '11.5°C'}
          unit={latestF ? `${latestF.humidity}% Hum` : '87% Hum'}
          status={summary?.storageStatus || 'SAFE'}
          icon={ShieldAlert}
          color="#f59e0b"
          updatedTime={latestF?.timestamp}
          onClick={() => navigate('/food-preservation')}
        />
      </div>

      {/* Grid for Trend Chart & System Alerts Feed */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
        {/* Real-time Parameter Graph */}
        <div className="agri-card">
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '16px' }}>
            <h3 style={{ fontSize: '1.05rem', color: 'var(--text-main)' }}>
              📈 Real-Time Water & Environmental Trends
            </h3>
            <span style={{ fontSize: '0.78rem', color: 'var(--primary)' }}>Live Stream</span>
          </div>

          <div style={{ width: '100%', height: 260 }}>
            <ResponsiveContainer>
              <AreaChart data={chartData}>
                <defs>
                  <linearGradient id="phGrad" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#10b981" stopOpacity={0.4}/>
                    <stop offset="95%" stopColor="#10b981" stopOpacity={0}/>
                  </linearGradient>
                  <linearGradient id="tdsGrad" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#3b82f6" stopOpacity={0.4}/>
                    <stop offset="95%" stopColor="#3b82f6" stopOpacity={0}/>
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" stroke="#233152" />
                <XAxis dataKey="time" stroke="#94a3b8" fontSize={11} />
                <YAxis stroke="#94a3b8" fontSize={11} />
                <Tooltip contentStyle={{ backgroundColor: '#131b2e', borderColor: '#233152', borderRadius: '8px' }} />
                <Area type="monotone" dataKey="pH" stroke="#10b981" fillOpacity={1} fill="url(#phGrad)" />
                <Area type="monotone" dataKey="TDS" stroke="#3b82f6" fillOpacity={1} fill="url(#tdsGrad)" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Live System Alerts Feed */}
        <div className="agri-card">
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '16px' }}>
            <h3 style={{ fontSize: '1.05rem', color: 'var(--text-main)', display: 'flex', alignItems: 'center', gap: '8px' }}>
              <Bell size={18} color="var(--accent-amber)" /> Recent System Alerts
            </h3>
            <button className="btn-secondary" style={{ padding: '4px 10px', fontSize: '0.75rem' }} onClick={() => navigate('/alerts')}>
              View All ({summary?.activeAlertsCount || 0})
            </button>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', maxHeight: '270px', overflowY: 'auto' }}>
            {summary?.recentAlerts && summary.recentAlerts.length > 0 ? (
              summary.recentAlerts.slice(0, 4).map((alert) => (
                <div key={alert.id} className={`alert-card ${alert.severity.toLowerCase()}`}>
                  <div style={{ flex: 1 }}>
                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '4px' }}>
                      <span style={{ fontSize: '0.75rem', fontWeight: 700, textTransform: 'uppercase' }}>
                        {alert.module}
                      </span>
                      <span style={{ fontSize: '0.7rem', color: 'var(--text-dim)' }}>
                        {new Date(alert.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                      </span>
                    </div>
                    <p style={{ fontSize: '0.85rem', lineHeight: 1.3 }}>
                      {alert.message}
                    </p>
                  </div>
                </div>
              ))
            ) : (
              <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', textAlign: 'center', padding: '24px' }}>
                No active alerts. All modules operating normally.
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
