import React from 'react';
import { FileBarChart, Download, Sparkles, CheckCircle2, TrendingUp, DollarSign } from 'lucide-react';

export default function ReportsPage() {
  return (
    <div className="page-wrapper">
      {/* Top Header */}
      <div className="page-header" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div>
          <h1 className="page-title">
            📊 CIRCULAR AGRICULTURAL ECOSYSTEM REPORTS
          </h1>
          <p className="page-subtitle">
            Comprehensive resource throughput, fertilizer efficiency, and waste recycling analytics
          </p>
        </div>
        <button className="btn-primary" onClick={() => alert("Exporting AGRILOOP 360 Full Ecosystem Summary Report (PDF)...")}>
          <Download size={16} /> Export Executive Summary (PDF)
        </button>
      </div>

      {/* Summary Stat Cards */}
      <div className="metrics-grid" style={{ marginBottom: '28px' }}>
        <div className="agri-card">
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Water Irrigation Saved</span>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#3b82f6', marginTop: '4px' }}>
            34,500 <span style={{ fontSize: '1rem', color: 'var(--text-dim)' }}>Liters</span>
          </div>
          <p style={{ fontSize: '0.75rem', color: '#86efac', marginTop: '6px' }}>↑ 18.4% improvement via pH/TDS monitoring</p>
        </div>

        <div className="agri-card">
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Synthetic Fertilizer Reduced</span>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#10b981', marginTop: '4px' }}>
            430 <span style={{ fontSize: '1rem', color: 'var(--text-dim)' }}>kg</span>
          </div>
          <p style={{ fontSize: '0.75rem', color: '#86efac', marginTop: '6px' }}>↑ Replaced by Organic Powder & Solution</p>
        </div>

        <div className="agri-card">
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Organic Waste Recycled</span>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#ec4899', marginTop: '4px' }}>
            1,250 <span style={{ fontSize: '1rem', color: 'var(--text-dim)' }}>kg</span>
          </div>
          <p style={{ fontSize: '0.75rem', color: '#86efac', marginTop: '6px' }}>Zero-waste farm circularity reached</p>
        </div>

        <div className="agri-card">
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Post-Harvest Loss Prevention</span>
          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#f59e0b', marginTop: '4px' }}>
            94.2% <span style={{ fontSize: '1rem', color: 'var(--text-dim)' }}>Safe</span>
          </div>
          <p style={{ fontSize: '0.75rem', color: '#86efac', marginTop: '6px' }}>Evaporative cooling & UV-C treatment</p>
        </div>
      </div>

      {/* Analytical Narrative Report Card */}
      <div className="agri-card" style={{ marginBottom: '28px' }}>
        <h3 style={{ fontSize: '1.15rem', color: 'var(--text-main)', marginBottom: '14px', display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Sparkles color="var(--primary)" size={20} /> 360-Degree Circularity Benchmark Analysis
        </h3>
        <p style={{ fontSize: '0.95rem', color: 'var(--text-muted)', lineHeight: 1.6, marginBottom: '16px' }}>
          AGRILOOP 360 has successfully closed the agricultural nutrient loop across all four operational modules:
        </p>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '16px' }}>
          <div style={{ background: 'var(--bg-panel)', padding: '16px', borderRadius: '12px', border: '1px solid var(--border-color)' }}>
            <h4 style={{ color: '#3b82f6', fontSize: '0.95rem', marginBottom: '6px' }}>1. Water Efficiency</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: 1.4 }}>
              Continuous sensor evaluation prevents salinization and toxic ion runoff, reserving clean water for sensitive fruit and cereal crops.
            </p>
          </div>

          <div style={{ background: 'var(--bg-panel)', padding: '16px', borderRadius: '12px', border: '1px solid var(--border-color)' }}>
            <h4 style={{ color: '#10b981', fontSize: '0.95rem', marginBottom: '6px' }}>2. Soil NPK Optimization</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: 1.4 }}>
              Precision fertilizer recommendations prevent excess nitrogen over-application, cutting soil acidification and maintaining optimal pH balances.
            </p>
          </div>

          <div style={{ background: 'var(--bg-panel)', padding: '16px', borderRadius: '12px', border: '1px solid var(--border-color)' }}>
            <h4 style={{ color: '#ec4899', fontSize: '0.95rem', marginBottom: '6px' }}>3. Composting Yield</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: 1.4 }}>
              Thermophilic organic decomposition converts raw waste into 38% organic powder mass and 28% liquid nutrient solution for soil re-injection.
            </p>
          </div>

          <div style={{ background: 'var(--bg-panel)', padding: '16px', borderRadius: '12px', border: '1px solid var(--border-color)' }}>
            <h4 style={{ color: '#f59e0b', fontSize: '0.95rem', marginBottom: '6px' }}>4. Food Preservation</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-main)', lineHeight: 1.4 }}>
              Evaporative cooling chambers paired with mild UV-C exposure reduce mold formation by 82% without chemical sprays.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
