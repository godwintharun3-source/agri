import React, { useState, useEffect } from 'react';
import { TreePine, Plus, Trash2, Edit, Calendar, Sprout, Droplets, ShieldAlert, CheckCircle2 } from 'lucide-react';
import { cropService } from '../services/cropService';
import StatusBadge from '../components/StatusBadge';

export default function CropManagement() {
  const [crops, setCrops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAddModal, setShowAddModal] = useState(false);

  // New Crop Form
  const [name, setName] = useState('');
  const [cropType, setCropType] = useState('VEGETABLE');
  const [soilType, setSoilType] = useState('Loamy Soil');
  const [plantingDate, setPlantingDate] = useState(new Date().toISOString().split('T')[0]);
  const [harvestDate, setHarvestDate] = useState(new Date(Date.now() + 60*86400000).toISOString().split('T')[0]);

  // Target Parameters
  const [targetPhMin, setTargetPhMin] = useState('6.0');
  const [targetPhMax, setTargetPhMax] = useState('7.0');
  const [targetMoistureMin, setTargetMoistureMin] = useState('45.0');
  const [targetMoistureMax, setTargetMoistureMax] = useState('65.0');
  const [targetN, setTargetN] = useState('100.0');
  const [targetP, setTargetP] = useState('45.0');
  const [targetK, setTargetK] = useState('75.0');

  useEffect(() => {
    fetchCrops();
  }, []);

  const fetchCrops = async () => {
    try {
      const data = await cropService.getAllCrops();
      setCrops(data);
    } catch (err) {
      console.error("Error loading crops:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateCrop = async (e) => {
    e.preventDefault();
    try {
      const newCrop = {
        name,
        cropType,
        soilType,
        plantingDate,
        expectedHarvestDate: harvestDate,
        targetPhMin: parseFloat(targetPhMin),
        targetPhMax: parseFloat(targetPhMax),
        targetMoistureMin: parseFloat(targetMoistureMin),
        targetMoistureMax: parseFloat(targetMoistureMax),
        targetN: parseFloat(targetN),
        targetP: parseFloat(targetP),
        targetK: parseFloat(targetK),
        targetTempMin: 10.0,
        targetTempMax: 15.0,
        targetHumidityMin: 85.0,
        targetHumidityMax: 90.0
      };

      await cropService.createCrop(newCrop);
      setShowAddModal(false);
      setName('');
      await fetchCrops();
    } catch (err) {
      console.error("Error creating crop:", err);
    }
  };

  const handleDeleteCrop = async (id) => {
    if (window.confirm("Are you sure you want to remove this crop from management?")) {
      try {
        await cropService.deleteCrop(id);
        await fetchCrops();
      } catch (err) {
        console.error("Error deleting crop:", err);
      }
    }
  };

  return (
    <div className="page-wrapper">
      {/* Top Header */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            🌾 CROP LIFECYCLE & FARM MANAGEMENT
          </h1>
          <p className="page-subtitle">
            Track active crops, planting cycles, soil parameters, and cross-module ecosystem health
          </p>
        </div>
        <button className="btn-primary" onClick={() => setShowAddModal(true)}>
          <Plus size={16} /> Add New Crop
        </button>
      </div>

      {/* Crops Cards Grid */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '24px' }}>
        {crops.map((crop) => (
          <div key={crop.id} className="agri-card">
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '12px' }}>
              <div>
                <span className="status-badge good" style={{ marginBottom: '4px' }}>{crop.cropType}</span>
                <h3 style={{ fontSize: '1.3rem', color: 'var(--text-main)' }}>{crop.name}</h3>
              </div>
              <button
                onClick={() => handleDeleteCrop(crop.id)}
                title="Delete Crop"
                style={{ background: 'none', border: 'none', color: '#ef4444', cursor: 'pointer', padding: '4px' }}
              >
                <Trash2 size={16} />
              </button>
            </div>

            <p style={{ fontSize: '0.82rem', color: 'var(--text-muted)', marginBottom: '14px' }}>
              Soil Type: <strong>{crop.soilType}</strong>
            </p>

            {/* Planting dates */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px', background: 'var(--bg-dark)', padding: '10px 14px', borderRadius: '8px', marginBottom: '16px', fontSize: '0.78rem' }}>
              <Calendar size={16} color="var(--primary)" />
              <div>
                <p style={{ color: 'var(--text-dim)' }}>Planted: <strong>{crop.plantingDate}</strong></p>
                <p style={{ color: '#86efac' }}>Est. Harvest: <strong>{crop.expectedHarvestDate}</strong></p>
              </div>
            </div>

            {/* 4 Health Metrics Overview Pill (as requested by spec) */}
            <div style={{ background: 'var(--bg-panel)', padding: '12px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
              <div style={{ fontSize: '0.75rem', fontWeight: 700, color: 'var(--text-muted)', marginBottom: '8px' }}>
                Module Ecosystem Status
              </div>
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '8px', fontSize: '0.8rem' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
                  <Sprout size={14} color="#10b981" /> Soil: <span style={{ color: '#86efac', fontWeight: 600 }}>Good</span>
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
                  <Droplets size={14} color="#3b82f6" /> Water: <span style={{ color: '#86efac', fontWeight: 600 }}>Good</span>
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
                  <Sprout size={14} color="#a855f7" /> Fertilizer: <span style={{ color: '#86efac', fontWeight: 600 }}>Optimized</span>
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
                  <ShieldAlert size={14} color="#f59e0b" /> Storage: <span style={{ color: '#86efac', fontWeight: 600 }}>Safe</span>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Add Crop Modal */}
      {showAddModal && (
        <div style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(0, 0, 0, 0.75)',
          backdropFilter: 'blur(4px)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          zIndex: 100,
          padding: '20px'
        }}>
          <div className="agri-card" style={{ width: '100%', maxWidth: '520px', maxHeight: '90vh', overflowY: 'auto' }}>
            <h3 style={{ fontSize: '1.25rem', marginBottom: '16px', color: 'var(--text-main)' }}>
              🌱 Register New Crop Profile
            </h3>

            <form onSubmit={handleCreateCrop}>
              <div className="form-group">
                <label className="form-label">Crop Name</label>
                <input type="text" className="form-input" placeholder="e.g. Tomato, Wheat, Strawberry" value={name} onChange={e => setName(e.target.value)} required />
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
                <div className="form-group">
                  <label className="form-label">Crop Category</label>
                  <select className="form-select" value={cropType} onChange={e => setCropType(e.target.value)}>
                    <option value="VEGETABLE">VEGETABLE</option>
                    <option value="FRUIT">FRUIT</option>
                    <option value="CEREAL">CEREAL</option>
                  </select>
                </div>

                <div className="form-group">
                  <label className="form-label">Soil Type</label>
                  <input type="text" className="form-input" value={soilType} onChange={e => setSoilType(e.target.value)} required />
                </div>
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
                <div className="form-group">
                  <label className="form-label">Planting Date</label>
                  <input type="date" className="form-input" value={plantingDate} onChange={e => setPlantingDate(e.target.value)} required />
                </div>

                <div className="form-group">
                  <label className="form-label">Expected Harvest</label>
                  <input type="date" className="form-input" value={harvestDate} onChange={e => setHarvestDate(e.target.value)} required />
                </div>
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: '10px' }}>
                <div className="form-group">
                  <label className="form-label">Target N (ppm)</label>
                  <input type="number" className="form-input" value={targetN} onChange={e => setTargetN(e.target.value)} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Target P (ppm)</label>
                  <input type="number" className="form-input" value={targetP} onChange={e => setTargetP(e.target.value)} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Target K (ppm)</label>
                  <input type="number" className="form-input" value={targetK} onChange={e => setTargetK(e.target.value)} required />
                </div>
              </div>

              <div style={{ display: 'flex', gap: '12px', marginTop: '20px' }}>
                <button type="button" className="btn-secondary" style={{ flex: 1, justifyContent: 'center' }} onClick={() => setShowAddModal(false)}>
                  Cancel
                </button>
                <button type="submit" className="btn-primary" style={{ flex: 1, justifyContent: 'center' }}>
                  Save Crop Profile
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
