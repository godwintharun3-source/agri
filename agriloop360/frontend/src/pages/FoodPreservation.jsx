import React, { useState, useEffect } from 'react';
import { ShieldAlert, Sun, Snowflake, Flame, RefreshCw, CheckCircle2, AlertOctagon, ToggleLeft, ToggleRight } from 'lucide-react';
import { foodStorageService } from '../services/foodStorageService';
import { cropService } from '../services/cropService';
import StatusBadge from '../components/StatusBadge';

export default function FoodPreservation() {
  const [storageUnits, setStorageUnits] = useState([]);
  const [crops, setCrops] = useState([]);
  const [loading, setLoading] = useState(true);

  // New Storage Form State
  const [storageName, setStorageName] = useState('Vault C - Cold Mild Chamber');
  const [selectedCropId, setSelectedCropId] = useState('');
  const [temp, setTemp] = useState('11.0');
  const [humidity, setHumidity] = useState('88.0');
  const [uvcActive, setUvcActive] = useState(true);

  useEffect(() => {
    fetchStorageData();
  }, []);

  const fetchStorageData = async () => {
    try {
      const units = await foodStorageService.getAllStorageUnits();
      setStorageUnits(units);
      const allCrops = await cropService.getAllCrops();
      setCrops(allCrops);
      if (allCrops.length > 0 && !selectedCropId) {
        setSelectedCropId(allCrops[0].id);
      }
    } catch (err) {
      console.error("Error loading storage data:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleToggleUvc = async (unitId, currentStatus) => {
    try {
      await foodStorageService.toggleUvc(unitId, !currentStatus);
      await fetchStorageData();
    } catch (err) {
      console.error("Error toggling UV-C:", err);
    }
  };

  const handleCreateStorage = async (e) => {
    e.preventDefault();
    try {
      const newUnit = {
        storageName,
        temperature: parseFloat(temp),
        humidity: parseFloat(humidity),
        uvcActive
      };
      await foodStorageService.createStorage(newUnit, selectedCropId);
      await fetchStorageData();
    } catch (err) {
      console.error("Error creating storage unit:", err);
    }
  };

  return (
    <div className="page-wrapper">
      {/* Top Header */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            🛡️ SMART FOOD PRESERVATION & STORAGE MONITOR
          </h1>
          <p className="page-subtitle">
            Crop-specific micro-environment management, evaporative cooling, and sanitizing UV-C exposure treatment
          </p>
        </div>
        <button className="btn-secondary" onClick={fetchStorageData}>
          <RefreshCw size={14} /> Refresh Storage Telemetry
        </button>
      </div>

      {/* Storage Vault Cards List */}
      <h3 style={{ fontSize: '1.1rem', marginBottom: '16px', color: 'var(--text-main)' }}>
        ❄️ Active Produce Storage Chambers & Coolers
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(360px, 1fr))', gap: '24px', marginBottom: '32px' }}>
        {storageUnits.map((unit) => {
          const isWarning = unit.safetyStatus !== 'SAFE';
          const crop = unit.crop;

          return (
            <div
              key={unit.id}
              className="agri-card"
              style={{
                borderLeft: isWarning ? '4px solid #ef4444' : '4px solid #22c55e',
                background: isWarning ? 'linear-gradient(135deg, #281216 0%, var(--bg-card) 100%)' : 'var(--bg-card)'
              }}
            >
              <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '12px' }}>
                <div>
                  <h3 style={{ fontSize: '1.15rem', color: 'var(--text-main)' }}>{unit.storageName}</h3>
                  <p style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                    Stored Produce: <strong style={{ color: 'var(--primary)' }}>{crop ? crop.name : 'Produce'}</strong> ({crop ? crop.cropType : 'GENERAL'})
                  </p>
                </div>
                <StatusBadge status={unit.safetyStatus} />
              </div>

              {/* Target Ranges Pill */}
              {crop && (
                <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)', background: 'var(--bg-dark)', padding: '6px 12px', borderRadius: '6px', marginBottom: '14px' }}>
                  Target Safe Range: Temp ({crop.targetTempMin}°C - {crop.targetTempMax}°C) | Hum ({crop.targetHumidityMin}% - {crop.targetHumidityMax}%)
                </div>
              )}

              {/* Parameter Metrics */}
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: '10px', marginBottom: '16px' }}>
                <div style={{ background: 'var(--bg-panel)', padding: '10px', borderRadius: '8px', textAlign: 'center' }}>
                  <span style={{ fontSize: '0.72rem', color: 'var(--text-dim)' }}>Temperature</span>
                  <p style={{ fontSize: '1.2rem', fontWeight: 800, color: '#3b82f6' }}>{unit.temperature} °C</p>
                </div>

                <div style={{ background: 'var(--bg-panel)', padding: '10px', borderRadius: '8px', textAlign: 'center' }}>
                  <span style={{ fontSize: '0.72rem', color: 'var(--text-dim)' }}>Humidity</span>
                  <p style={{ fontSize: '1.2rem', fontWeight: 800, color: '#10b981' }}>{unit.humidity} %</p>
                </div>

                <div style={{ background: 'var(--bg-panel)', padding: '10px', borderRadius: '8px', textAlign: 'center' }}>
                  <span style={{ fontSize: '0.72rem', color: 'var(--text-dim)' }}>UV-C Sanitizer</span>
                  <p style={{ fontSize: '0.9rem', fontWeight: 700, color: unit.uvcActive ? '#a855f7' : 'var(--text-dim)', marginTop: '4px' }}>
                    {unit.uvcActive ? '⚡ ACTIVE' : 'OFF'}
                  </p>
                </div>
              </div>

              {/* Warning/Safety Status Msg */}
              <div style={{ padding: '10px 14px', borderRadius: '8px', marginBottom: '16px', background: isWarning ? 'rgba(239, 68, 68, 0.15)' : 'rgba(34, 197, 94, 0.15)', border: isWarning ? '1px solid rgba(239, 68, 68, 0.3)' : '1px solid rgba(34, 197, 94, 0.3)' }}>
                <p style={{ fontSize: '0.85rem', color: isWarning ? '#fca5a5' : '#86efac', lineHeight: 1.4 }}>
                  {unit.warningMessage || 'Storage conditions are safe.'}
                </p>
              </div>

              {/* Actions: Toggle UV-C Treatment */}
              <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', borderTop: '1px solid var(--border-color)', paddingTop: '12px' }}>
                <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Mild UV-C Treatment:</span>
                <button
                  className="btn-secondary"
                  onClick={() => handleToggleUvc(unit.id, unit.uvcActive)}
                  style={{ padding: '6px 12px', fontSize: '0.78rem' }}
                >
                  {unit.uvcActive ? <ToggleRight color="#a855f7" size={20} /> : <ToggleLeft color="#94a3b8" size={20} />}
                  {unit.uvcActive ? 'Deactivate UV-C' : 'Activate Mild UV-C'}
                </button>
              </div>
            </div>
          );
        })}
      </div>

      {/* Form: Register New Storage Chamber */}
      <div className="agri-card">
        <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
          ➕ Configure New Food Storage Chamber
        </h3>
        <form onSubmit={handleCreateStorage}>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '16px' }}>
            <div className="form-group">
              <label className="form-label">Storage Vault Name</label>
              <input type="text" className="form-input" value={storageName} onChange={e => setStorageName(e.target.value)} required />
            </div>

            <div className="form-group">
              <label className="form-label">Stored Produce Crop</label>
              <select className="form-select" value={selectedCropId} onChange={e => setSelectedCropId(e.target.value)}>
                {crops.map(c => (
                  <option key={c.id} value={c.id}>{c.name} ({c.cropType})</option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Temperature (°C)</label>
              <input type="number" step="0.1" className="form-input" value={temp} onChange={e => setTemp(e.target.value)} required />
            </div>

            <div className="form-group">
              <label className="form-label">Humidity (%)</label>
              <input type="number" step="0.1" className="form-input" value={humidity} onChange={e => setHumidity(e.target.value)} required />
            </div>
          </div>

          <button type="submit" className="btn-primary" style={{ marginTop: '16px' }}>
            <ShieldAlert size={16} /> Deploy Controlled Chamber & Save
          </button>
        </form>
      </div>
    </div>
  );
}
