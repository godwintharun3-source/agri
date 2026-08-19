import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Droplets, Sprout, TreePine, ShieldAlert, Trash2, Recycle } from 'lucide-react';

export default function InteractiveCycle({ summary }) {
  const navigate = useNavigate();

  const cycleNodes = [
    {
      id: 'water',
      label: '1. Water Quality',
      subtitle: summary?.waterStatus || 'Optimal',
      icon: Droplets,
      path: '/water-quality',
      color: '#3b82f6',
      badgeClass: 'good'
    },
    {
      id: 'soil',
      label: '2. Soil Health',
      subtitle: summary?.soilStatus || 'Good',
      icon: Sprout,
      path: '/soil-fertilizer',
      color: '#10b981',
      badgeClass: 'good'
    },
    {
      id: 'crop',
      label: '3. Crop Health',
      subtitle: summary?.cropStatus || 'Healthy',
      icon: TreePine,
      path: '/crop-management',
      color: '#8b5cf6',
      badgeClass: 'good'
    },
    {
      id: 'food',
      label: '4. Food Preservation',
      subtitle: summary?.storageStatus || 'Safe',
      icon: ShieldAlert,
      path: '/food-preservation',
      color: '#f59e0b',
      badgeClass: 'good'
    },
    {
      id: 'waste',
      label: '5. Waste Collection',
      subtitle: 'Processed',
      icon: Trash2,
      path: '/smart-composting',
      color: '#ec4899',
      badgeClass: 'good'
    },
    {
      id: 'compost',
      label: '6. Smart Compost',
      subtitle: summary?.compostStatus || 'Active',
      icon: Recycle,
      path: '/smart-composting',
      color: '#10b981',
      badgeClass: 'good'
    }
  ];

  return (
    <div className="cycle-container">
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '20px' }}>
        <div>
          <h3 style={{ fontSize: '1.25rem', color: 'var(--text-main)', display: 'flex', alignItems: 'center', gap: '8px' }}>
            🔄 The Circular Agricultural Ecosystem
          </h3>
          <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', marginTop: '2px' }}>
            Click any stage in the 360° cycle to inspect real-time metrics, telemetry, and intelligent agronomic guidance.
          </p>
        </div>
        <div style={{
          fontSize: '0.78rem',
          color: 'var(--primary)',
          fontWeight: 700,
          background: 'rgba(16, 185, 129, 0.1)',
          border: '1px solid rgba(16, 185, 129, 0.3)',
          padding: '6px 14px',
          borderRadius: '9999px',
          letterSpacing: '0.05em'
        }}>
          WATER → SOIL → CROP → FOOD → WASTE → COMPOST → SOIL
        </div>
      </div>

      {/* Cycle Nodes Horizontal Flow */}
      <div className="cycle-flow">
        {cycleNodes.map((node, index) => {
          const Icon = node.icon;
          return (
            <React.Fragment key={node.id}>
              <div
                className="cycle-node"
                onClick={() => navigate(node.path)}
                title={`Click to open ${node.label} module`}
              >
                <div style={{
                  width: '42px',
                  height: '42px',
                  borderRadius: '50%',
                  background: `${node.color}22`,
                  border: `1px solid ${node.color}55`,
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  margin: '0 auto 10px'
                }}>
                  <Icon size={22} color={node.color} />
                </div>
                <h4 style={{ fontSize: '0.88rem', color: 'var(--text-main)', fontWeight: 700 }}>
                  {node.label}
                </h4>
                <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '4px' }}>
                  {node.subtitle}
                </p>
              </div>

              {index < cycleNodes.length - 1 && (
                <div className="cycle-arrow">➔</div>
              )}
            </React.Fragment>
          );
        })}
        {/* Loop Arrow returning to Soil */}
        <div className="cycle-arrow" style={{ color: '#10b981', fontWeight: 'bold' }}>
          ↩ (Soil)
        </div>
      </div>
    </div>
  );
}
