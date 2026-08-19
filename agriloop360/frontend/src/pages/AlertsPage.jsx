import React, { useState, useEffect } from 'react';
import { Bell, CheckCheck, Filter, AlertTriangle, Info, AlertOctagon, RefreshCw } from 'lucide-react';
import { alertService } from '../services/alertService';
import StatusBadge from '../components/StatusBadge';

export default function AlertsPage() {
  const [alerts, setAlerts] = useState([]);
  const [filterSeverity, setFilterSeverity] = useState('');
  const [filterModule, setFilterModule] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAlerts();
  }, [filterSeverity, filterModule]);

  const fetchAlerts = async () => {
    try {
      const data = await alertService.getAllAlerts(filterSeverity, filterModule);
      setAlerts(data);
    } catch (err) {
      console.error("Error loading alerts:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleMarkRead = async (id) => {
    try {
      await alertService.markAsRead(id);
      await fetchAlerts();
    } catch (err) {
      console.error("Error marking read:", err);
    }
  };

  const handleMarkAllRead = async () => {
    try {
      await alertService.markAllAsRead();
      await fetchAlerts();
    } catch (err) {
      console.error("Error marking all read:", err);
    }
  };

  const getAlertIcon = (severity) => {
    switch (severity) {
      case 'CRITICAL': return <AlertOctagon size={20} color="#ef4444" />;
      case 'WARNING': return <AlertTriangle size={20} color="#eab308" />;
      default: return <Info size={20} color="#3b82f6" />;
    }
  };

  return (
    <div className="page-wrapper">
      {/* Top Header */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            🔔 SYSTEM ALERTS & EVENT FEED
          </h1>
          <p className="page-subtitle">
            Real-time critical events, sensor threshold breaches, and automated agronomic warnings
          </p>
        </div>
        <div style={{ display: 'flex', gap: '10px' }}>
          <button className="btn-secondary" onClick={handleMarkAllRead}>
            <CheckCheck size={16} /> Mark All Read
          </button>
          <button className="btn-secondary" onClick={fetchAlerts}>
            <RefreshCw size={14} /> Refresh Feed
          </button>
        </div>
      </div>

      {/* Filter Bar */}
      <div className="agri-card" style={{ marginBottom: '24px', padding: '16px 20px', display: 'flex', alignItems: 'center', gap: '16px', flexWrap: 'wrap' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: 'var(--text-muted)' }}>
          <Filter size={18} />
          <span style={{ fontSize: '0.85rem', fontWeight: 600 }}>Filter Alerts:</span>
        </div>

        <select
          className="form-select"
          style={{ width: '160px', padding: '6px 10px', fontSize: '0.85rem' }}
          value={filterSeverity}
          onChange={e => setFilterSeverity(e.target.value)}
        >
          <option value="">All Severities</option>
          <option value="CRITICAL">Critical Only 🔴</option>
          <option value="WARNING">Warning Only 🟡</option>
          <option value="INFO">Info Only 🔵</option>
        </select>

        <select
          className="form-select"
          style={{ width: '160px', padding: '6px 10px', fontSize: '0.85rem' }}
          value={filterModule}
          onChange={e => setFilterModule(e.target.value)}
        >
          <option value="">All Modules</option>
          <option value="WATER">Water</option>
          <option value="SOIL">Soil</option>
          <option value="COMPOST">Compost</option>
          <option value="STORAGE">Storage</option>
          <option value="CROP">Crop</option>
        </select>
      </div>

      {/* Alerts List */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
        {alerts.length > 0 ? (
          alerts.map(alert => (
            <div
              key={alert.id}
              className={`alert-card ${alert.severity.toLowerCase()}`}
              style={{
                opacity: alert.readStatus ? 0.7 : 1,
                borderLeft: alert.readStatus ? '4px solid var(--border-color)' : (
                  alert.severity === 'CRITICAL' ? '4px solid #ef4444' : (
                    alert.severity === 'WARNING' ? '4px solid #eab308' : '4px solid #3b82f6'
                  )
                )
              }}
            >
              {getAlertIcon(alert.severity)}
              <div style={{ flex: 1 }}>
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '4px' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <span style={{ fontWeight: 800, fontSize: '0.78rem', textTransform: 'uppercase' }}>
                      {alert.module}
                    </span>
                    <span className={`status-badge ${alert.severity.toLowerCase()}`} style={{ fontSize: '0.65rem' }}>
                      {alert.severity}
                    </span>
                  </div>
                  <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>
                    {new Date(alert.timestamp).toLocaleString()}
                  </span>
                </div>
                <p style={{ fontSize: '0.9rem', lineHeight: 1.4, color: 'var(--text-main)' }}>
                  {alert.message}
                </p>
              </div>

              {!alert.readStatus && (
                <button
                  className="btn-secondary"
                  style={{ padding: '6px 12px', fontSize: '0.75rem' }}
                  onClick={() => handleMarkRead(alert.id)}
                >
                  Mark Read
                </button>
              )}
            </div>
          ))
        ) : (
          <div className="agri-card" style={{ textAlign: 'center', padding: '40px' }}>
            <Bell size={32} color="var(--text-dim)" style={{ marginBottom: '8px' }} />
            <p style={{ color: 'var(--text-muted)' }}>No alerts found matching the selected filter criteria.</p>
          </div>
        )}
      </div>
    </div>
  );
}
