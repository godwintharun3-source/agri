import React, { useState, useEffect } from 'react';
import { Recycle, ArrowRight, CheckCircle2, AlertTriangle, Plus, RefreshCw, Zap } from 'lucide-react';
import { compostService } from '../services/compostService';
import StatusBadge from '../components/StatusBadge';

export default function SmartComposting() {
  const [batches, setBatches] = useState([]);
  const [selectedBatch, setSelectedBatch] = useState(null);
  const [loading, setLoading] = useState(true);

  // New Batch Form State
  const [batchName, setBatchName] = useState('Batch #C305 - Green Leaf Recycler');
  const [wasteType, setWasteType] = useState('Vegetable waste');
  const [quantityKg, setQuantityKg] = useState('200');
  const [moisture, setMoisture] = useState('58');
  const [temp, setTemp] = useState('50');

  useEffect(() => {
    fetchCompostData();
  }, []);

  const fetchCompostData = async () => {
    try {
      const list = await compostService.getAllBatches();
      setBatches(list);
      if (list.length > 0) {
        setSelectedBatch(list[0]);
      }
    } catch (err) {
      console.error("Error loading compost data:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateBatch = async (e) => {
    e.preventDefault();
    try {
      const newBatch = {
        batchName,
        wasteType,
        quantityKg: parseFloat(quantityKg),
        moisture: parseFloat(moisture),
        temperature: parseFloat(temp),
        ph: 6.8,
        stage: 'COLLECTION'
      };
      const created = await compostService.createBatch(newBatch);
      setSelectedBatch(created);
      await fetchCompostData();
    } catch (err) {
      console.error("Error creating compost batch:", err);
    }
  };

  const handleAdvanceStage = async (batchId, currentStage) => {
    const stages = ['COLLECTION', 'MECHANICAL_SEPARATION', 'SOLID_PROCESSING', 'LIQUID_PROCESSING', 'AGRICULTURAL_APPLICATION'];
    const currentIndex = stages.indexOf(currentStage);
    if (currentIndex < stages.length - 1) {
      const nextStage = stages[currentIndex + 1];
      try {
        const updated = await compostService.updateStage(batchId, nextStage);
        setSelectedBatch(updated);
        await fetchCompostData();
      } catch (err) {
        console.error("Error advancing stage:", err);
      }
    }
  };

  const stagesList = [
    { key: 'COLLECTION', label: 'Stage 1', title: 'Organic Waste Collection', desc: 'Vegetable, Fruit & Plant Residue' },
    { key: 'MECHANICAL_SEPARATION', label: 'Stage 2', title: 'Mechanical Separation', desc: 'Solid vs Liquid Fractioning' },
    { key: 'SOLID_PROCESSING', label: 'Stage 3', title: 'Solid Processing', desc: 'Drying → Grinding → Organic Powder' },
    { key: 'LIQUID_PROCESSING', label: 'Stage 4', title: 'Liquid Processing', desc: 'Filtration → Nutrient Solution' },
    { key: 'AGRICULTURAL_APPLICATION', label: 'Stage 5', title: 'Agricultural Application', desc: 'Powder → Soil | Solution → Plants' }
  ];

  return (
    <div className="page-wrapper">
      {/* Top Header */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            ♻️ SMART COMPOSTING & ORGANIC RECYCLING
          </h1>
          <p className="page-subtitle">
            5-Stage organic waste recycling: Converting farm waste into High-Nutrient Organic Powder & Liquid Extract
          </p>
        </div>
        <button className="btn-secondary" onClick={fetchCompostData}>
          <RefreshCw size={14} /> Refresh Batches
        </button>
      </div>

      {/* 5-Stage Interactive Compost Pipeline */}
      <div className="agri-card" style={{ marginBottom: '28px', background: 'linear-gradient(135deg, #11281e 0%, var(--bg-card) 100%)' }}>
        <h3 style={{ fontSize: '1.1rem', marginBottom: '16px', color: 'var(--text-main)' }}>
          🔄 5-Stage Smart Compost Transformation Pipeline
        </h3>
        <div style={{ display: 'flex', alignItems: 'stretch', justifyContent: 'space-between', gap: '10px', overflowX: 'auto', paddingBottom: '8px' }}>
          {stagesList.map((st, idx) => {
            const isCurrent = selectedBatch?.stage === st.key;
            return (
              <div
                key={st.key}
                style={{
                  flex: 1,
                  minWidth: '150px',
                  background: isCurrent ? 'linear-gradient(180deg, #163e2c 0%, var(--bg-panel) 100%)' : 'var(--bg-dark)',
                  border: isCurrent ? '2px solid var(--primary)' : '1px solid var(--border-color)',
                  borderRadius: '12px',
                  padding: '14px',
                  position: 'relative'
                }}
              >
                <span style={{ fontSize: '0.7rem', fontWeight: 800, color: isCurrent ? '#86efac' : 'var(--text-dim)', textTransform: 'uppercase' }}>
                  {st.label}
                </span>
                <h4 style={{ fontSize: '0.88rem', color: 'var(--text-main)', marginTop: '4px' }}>
                  {st.title}
                </h4>
                <p style={{ fontSize: '0.72rem', color: 'var(--text-muted)', marginTop: '4px' }}>
                  {st.desc}
                </p>
                {isCurrent && (
                  <span className="status-badge good" style={{ marginTop: '8px', fontSize: '0.65rem' }}>Active Stage</span>
                )}
              </div>
            );
          })}
        </div>
      </div>

      {/* Active Batch Summary Header */}
      {selectedBatch && (
        <div className="agri-card" style={{ marginBottom: '28px', borderLeft: '4px solid var(--primary)' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', flexWrap: 'wrap', gap: '16px' }}>
            <div>
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                <h2 style={{ fontSize: '1.4rem', color: 'var(--text-main)' }}>{selectedBatch.batchName}</h2>
                <StatusBadge status={selectedBatch.stage} />
              </div>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', marginTop: '4px' }}>
                Waste Type: <strong>{selectedBatch.wasteType}</strong> | Initial Mass: <strong>{selectedBatch.quantityKg} kg</strong>
              </p>
            </div>

            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <button
                className="btn-primary"
                onClick={() => handleAdvanceStage(selectedBatch.id, selectedBatch.stage)}
                disabled={selectedBatch.stage === 'AGRICULTURAL_APPLICATION'}
              >
                <Zap size={16} /> Advance to Next Stage ➔
              </button>
            </div>
          </div>

          {/* Diagnostic & Output Metrics */}
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(180px, 1fr))', gap: '14px', marginTop: '20px' }}>
            <div style={{ background: 'var(--bg-dark)', padding: '14px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Pile Moisture</span>
              <p style={{ fontSize: '1.3rem', fontWeight: 800, color: selectedBatch.moisture > 65 ? '#ef4444' : '#3b82f6' }}>
                {selectedBatch.moisture}%
              </p>
            </div>

            <div style={{ background: 'var(--bg-dark)', padding: '14px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Decomposition Temp</span>
              <p style={{ fontSize: '1.3rem', fontWeight: 800, color: '#f59e0b' }}>
                {selectedBatch.temperature} °C
              </p>
            </div>

            <div style={{ background: 'var(--bg-dark)', padding: '14px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Organic Powder Yield</span>
              <p style={{ fontSize: '1.3rem', fontWeight: 800, color: '#22c55e' }}>
                {selectedBatch.organicPowderOutputKg || 0} kg
              </p>
            </div>

            <div style={{ background: 'var(--bg-dark)', padding: '14px', borderRadius: '10px', border: '1px solid var(--border-color)' }}>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Nutrient Solution Yield</span>
              <p style={{ fontSize: '1.3rem', fontWeight: 800, color: '#a855f7' }}>
                {selectedBatch.nutrientSolutionOutputLiters || 0} L
              </p>
            </div>
          </div>

          {/* Diagnostic Advice */}
          <div style={{ marginTop: '16px', padding: '12px', background: 'rgba(16, 185, 129, 0.1)', borderRadius: '8px', border: '1px solid rgba(16, 185, 129, 0.2)' }}>
            <p style={{ fontSize: '0.88rem', color: '#86efac' }}>
              💡 <strong>Diagnostics:</strong> {selectedBatch.recommendation || 'Compost pile is operating under thermophilic optimal conditions.'}
            </p>
          </div>
        </div>
      )}

      {/* Grid: Batches List & Create New Batch Form */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
        {/* Batches Table */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
            📦 Active Compost Batches
          </h3>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            {batches.map(b => (
              <div
                key={b.id}
                onClick={() => setSelectedBatch(b)}
                style={{
                  padding: '14px 16px',
                  borderRadius: '12px',
                  background: selectedBatch?.id === b.id ? 'var(--bg-panel)' : 'var(--bg-dark)',
                  border: selectedBatch?.id === b.id ? '1px solid var(--primary)' : '1px solid var(--border-color)',
                  cursor: 'pointer',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'space-between'
                }}
              >
                <div>
                  <h4 style={{ fontSize: '0.92rem', color: 'var(--text-main)' }}>{b.batchName}</h4>
                  <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '2px' }}>
                    {b.wasteType} • {b.quantityKg} kg • {b.temperature}°C
                  </p>
                </div>
                <StatusBadge status={b.stage} />
              </div>
            ))}
          </div>
        </div>

        {/* Create New Batch Form */}
        <div className="agri-card">
          <h3 style={{ fontSize: '1.05rem', marginBottom: '16px', color: 'var(--text-main)' }}>
            ➕ Register New Compost Processing Batch
          </h3>
          <form onSubmit={handleCreateBatch}>
            <div className="form-group">
              <label className="form-label">Batch Title</label>
              <input type="text" className="form-input" value={batchName} onChange={e => setBatchName(e.target.value)} required />
            </div>

            <div className="form-group">
              <label className="form-label">Waste Type</label>
              <select className="form-select" value={wasteType} onChange={e => setWasteType(e.target.value)}>
                <option value="Vegetable waste">Vegetable waste</option>
                <option value="Fruit waste">Fruit waste</option>
                <option value="Plant waste">Plant waste & Crop Residue</option>
                <option value="Mixed Organic waste">Mixed Organic waste</option>
              </select>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: '12px' }}>
              <div className="form-group">
                <label className="form-label">Quantity (kg)</label>
                <input type="number" className="form-input" value={quantityKg} onChange={e => setQuantityKg(e.target.value)} required />
              </div>

              <div className="form-group">
                <label className="form-label">Moisture (%)</label>
                <input type="number" className="form-input" value={moisture} onChange={e => setMoisture(e.target.value)} required />
              </div>

              <div className="form-group">
                <label className="form-label">Temp (°C)</label>
                <input type="number" className="form-input" value={temp} onChange={e => setTemp(e.target.value)} required />
              </div>
            </div>

            <button type="submit" className="btn-primary" style={{ width: '100%', marginTop: '14px', justifyContent: 'center' }}>
              <Plus size={16} /> Start New Recycling Batch
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
