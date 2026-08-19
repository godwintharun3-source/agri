import React, { useState, useEffect } from 'react';
import { Sprout, CheckCircle2, AlertTriangle, RefreshCw, Send, Sliders } from 'lucide-react';
import { cropService } from '../services/cropService';
import { soilService } from '../services/soilService';
import { fertilizerService } from '../services/fertilizerService';
import StatusBadge from '../components/StatusBadge';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from 'recharts';

export default function SoilFertilizer() {
  const [crops, setCrops] = useState([]);
  const [selectedCropId, setSelectedCropId] = useState('');
  const [selectedCrop, setSelectedCrop] = useState(null);
  const [soilReading, setSoilReading] = useState(null);
  const [recommendation, setRecommendation] = useState(null);
  const [loading, setLoading] = useState(true);

  // Custom Soil Input Form
  const [inputMoisture, setInputMoisture] = useState('52');
  const [inputPh, setInputPh] = useState('6.5');
  const [inputN, setInputN] = useState('85');
  const [inputP, setInputP] = useState('35');
  const [inputK, setInputK] = useState('75');

  useEffect(() => {
    fetchInitialData();
  }, []);

  const fetchInitialData = async () => {
    try {
      const allCrops = await cropService.getAllCrops();
      setCrops(allCrops);
      if (allCrops.length > 0) {
        const defaultCrop = allCrops[0];
        setSelectedCropId(defaultCrop.id);
        setSelectedCrop(defaultCrop);
        await loadCropSpecificData(defaultCrop.id);
      }
    } catch (err) {
      console.error("Error fetching crop list:", err);
    } finally {
      setLoading(false);
    }
  };

  const loadCropSpecificData = async (cropId) => {
    try {
      const soil = await soilService.getLatestStatus(cropId);
      setSoilReading(soil);

      const rec = await fertilizerService.getLatestForCrop(cropId);
      setRecommendation(rec);
    } catch (err) {
      console.error("Error loading crop soil data:", err);
    }
  };

  const handleCropChange = async (e) => {
    const id = e.target.value;
    setSelectedCropId(id);
    const found = crops.find(c => c.id === parseInt(id));
    setSelectedCrop(found);
    await loadCropSpecificData(id);
  };

  const handleRecordSoil = async (e) => {
    e.preventDefault();
    try {
      const newReading = {
        moisture: parseFloat(inputMoisture),
        ph: parseFloat(inputPh),
        nitrogen: parseFloat(inputN),
        phosphorus: parseFloat(inputP),
        potassium: parseFloat(inputK)
      };
      await soilService.recordReading(newReading, selectedCropId);
      await loadCropSpecificData(selectedCropId);
    } catch (err) {
      console.error("Error saving soil reading:", err);
    }
  };

  // Compare Current vs Target data for chart
  const comparisonData = selectedCrop && soilReading ? [
    { name: 'Nitrogen (N)', Current: soilReading.nitrogen, Target: selectedCrop.targetN },
    { name: 'Phosphorus (P)', Current: soilReading.phosphorus, Target: selectedCrop.targetP },
    { name: 'Potassium (K)', Current: soilReading.potassium, Target: selectedCrop.targetK },
    { name: 'Moisture (%)', Current: soilReading.moisture, Target: selectedCrop.targetMoistureMin }
  ] : [];

  return (
    <div className="page-wrapper">
      {/* Top Header */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            🌱 DIGITAL SOIL & FERTILIZER OPTIMIZATION
          </h1>
          <p className="page-subtitle">
            Crop-specific soil monitoring, NPK balance analysis, and precision fertilizer guidance
          </p>
        </div>

        {/* Crop Selection Dropdown */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', background: 'var(--bg-card)', padding: '6px 14px', borderRadius: 'var(--radius-md)', border: '1px solid var(--primary)' }}>
          <label style={{ fontSize: '0.85rem', fontWeight: 700, color: 'var(--primary)' }}>Select Crop:</label>
          <select
            className="form-select"
            value={selectedCropId}
            onChange={handleCropChange}
            style={{ background: 'var(--bg-dark)', minWidth: '180px' }}
          >
            <optgroup label="Vegetables">
              {crops.filter(c => c.cropType === 'VEGETABLE').map(c => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </optgroup>
            <optgroup label="Fruits">
              {crops.filter(c => c.cropType === 'FRUIT').map(c => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </optgroup>
            <optgroup label="Cereals">
              {crops.filter(c => c.cropType === 'CEREAL').map(c => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </optgroup>
          </select>
        </div>
      </div>

      {/* Selected Crop Requirements Summary Card */}
      {selectedCrop && (
        <div className="agri-card" style={{ marginBottom: '28px', background: 'linear-gradient(135deg, #11281e 0%, var(--bg-card) 100%)', border: '1px solid #1e4d35' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '14px' }}>
            <div>
              <span className="status-badge good" style={{ marginBottom: '4px' }}>{selectedCrop.cropType}</span>
              <h2 style={{ fontSize: '1.6rem', color: 'var(--text-main)' }}>{selectedCrop.name} Target Soil Profile</h2>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Soil Type: <strong>{selectedCrop.soilType}</strong></p>
            </div>
            <StatusBadge status={recommendation?.optimizationStatus || 'OPTIMIZED'} />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(140px, 1fr))', gap: '14px', background: 'var(--bg-dark)', padding: '16px', borderRadius: '12px', border: '1px solid var(--border-color)' }}>
            <div>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Target pH</span>
              <p style={{ fontSize: '1.1rem', fontWeight: 700, color: 'var(--primary)' }}>{selectedCrop.targetPhMin} - {selectedCrop.targetPhMax}</p>
            </div>
            <div>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Target Moisture</span>
              <p style={{ fontSize: '1.1rem', fontWeight: 700, color: '#3b82f6' }}>{selectedCrop.targetMoistureMin}% - {selectedCrop.targetMoistureMax}%</p>
            </div>
            <div>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Target Nitrogen (N)</span>
              <p style={{ fontSize: '1.1rem', fontWeight: 700, color: '#22c55e' }}>{selectedCrop.targetN} ppm</p>
            </div>
            <div>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Target Phosphorus (P)</span>
              <p style={{ fontSize: '1.1rem', fontWeight: 700, color: '#eab308' }}>{selectedCrop.targetP} ppm</p>
            </div>
            <div>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Target Potassium (K)</span>
              <p style={{ fontSize: '1.1rem', fontWeight: 700, color: '#a855f7' }}>{selectedCrop.targetK} ppm</p>
            </div>
          </div>
        </div>
      )}

      {/* 5 Soil Metric Cards */}
      <div className="metrics-grid" style={{ marginBottom: '28px' }}>
        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Soil Moisture</span>
            <StatusBadge status={recommendation?.moistureStatus || 'Optimal'} />
          </div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#3b82f6' }}>
            {soilReading ? `${soilReading.moisture}%` : '52%'}
          </div>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Soil pH</span>
            <StatusBadge status={recommendation?.phStatus || 'Optimal'} />
          </div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#10b981' }}>
            {soilReading ? soilReading.ph : '6.5'}
          </div>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Nitrogen (N)</span>
            <StatusBadge status={recommendation?.nstatus || 'Optimal'} />
          </div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#22c55e' }}>
            {soilReading ? `${soilReading.nitrogen}` : '85'} <span style={{ fontSize: '0.9rem', color: 'var(--text-dim)' }}>ppm</span>
          </div>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Phosphorus (P)</span>
            <StatusBadge status={recommendation?.pstatus || 'Optimal'} />
          </div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#eab308' }}>
            {soilReading ? `${soilReading.phosphorus}` : '35'} <span style={{ fontSize: '0.9rem', color: 'var(--text-dim)' }}>ppm</span>
          </div>
        </div>

        <div className="agri-card">
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Potassium (K)</span>
            <StatusBadge status={recommendation?.kstatus || 'Optimal'} />
          </div>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#a855f7' }}>
            {soilReading ? `${soilReading.potassium}` : '75'} <span style={{ fontSize: '0.9rem', color: 'var(--text-dim)' }}>ppm</span>
          </div>
        </div>
      </div>

      {/* Intelligent Fertilizer Recommendation Banner */}
      <div className="agri-card" style={{ marginBottom: '28px', borderLeft: '4px solid #a855f7' }}>
        <div style={{ display: 'flex', alignItems: 'flex-start', gap: '16px' }}>
          <div style={{ padding: '10px', background: 'rgba(168, 85, 247, 0.15)', borderRadius: '12px' }}>
            <Sprout size={28} color="#a855f7" />
          </div>
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '6px' }}>
              <h3 style={{ fontSize: '1.1rem', color: 'var(--text-main)' }}>
                Optimization Advice for {selectedCrop?.name}: <span style={{ color: '#a855f7' }}>{recommendation?.optimizationStatus || 'OPTIMIZED'}</span>
              </h3>
              <StatusBadge status={recommendation?.optimizationStatus || 'OPTIMIZED'} />
            </div>
            <p style={{ fontSize: '0.95rem', color: 'var(--text-main)', lineHeight: 1.5 }}>
              {recommendation?.recommendationText || 'Soil nutrients match required targets. Maintain standard organic compost application.'}
            </p>
          </div>
        </div>
      </div>

      {/* Grid: Bar Comparison Chart & Input Form */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
        {/* Comparison Bar Chart */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
            📊 Current Soil Data vs Crop Target Requirement
          </h3>
          <div style={{ width: '100%', height: 280 }}>
            <ResponsiveContainer>
              <BarChart data={comparisonData}>
                <CartesianGrid strokeDasharray="3 3" stroke="#233152" />
                <XAxis dataKey="name" stroke="#94a3b8" fontSize={11} />
                <YAxis stroke="#94a3b8" fontSize={11} />
                <Tooltip contentStyle={{ backgroundColor: '#131b2e', borderColor: '#233152', borderRadius: '8px' }} />
                <Legend />
                <Bar dataKey="Current" fill="#3b82f6" radius={[4, 4, 0, 0]} />
                <Bar dataKey="Target" fill="#10b981" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Custom Soil Sample Recording Form */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
            🧪 Record Soil Sample Reading
          </h3>
          <form onSubmit={handleRecordSoil}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '14px' }}>
              <div className="form-group">
                <label className="form-label">Moisture (%)</label>
                <input type="number" className="form-input" value={inputMoisture} onChange={e => setInputMoisture(e.target.value)} required />
              </div>
              <div className="form-group">
                <label className="form-label">pH Level</label>
                <input type="number" step="0.1" className="form-input" value={inputPh} onChange={e => setInputPh(e.target.value)} required />
              </div>
              <div className="form-group">
                <label className="form-label">Nitrogen (N ppm)</label>
                <input type="number" className="form-input" value={inputN} onChange={e => setInputN(e.target.value)} required />
              </div>
              <div className="form-group">
                <label className="form-label">Phosphorus (P ppm)</label>
                <input type="number" className="form-input" value={inputP} onChange={e => setInputP(e.target.value)} required />
              </div>
              <div className="form-group" style={{ gridColumn: 'span 2' }}>
                <label className="form-label">Potassium (K ppm)</label>
                <input type="number" className="form-input" value={inputK} onChange={e => setInputK(e.target.value)} required />
              </div>
            </div>

            <button type="submit" className="btn-primary" style={{ width: '100%', marginTop: '14px', justifyContent: 'center' }}>
              <Send size={16} /> Run Optimization & Save Sample
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
