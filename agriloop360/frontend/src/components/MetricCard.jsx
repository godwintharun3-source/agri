import React from 'react';
import StatusBadge from './StatusBadge';

export default function MetricCard({ title, value, unit, status, icon: Icon, updatedTime, onClick, color }) {
  return (
    <div
      className="agri-card"
      onClick={onClick}
      style={{ cursor: onClick ? 'pointer' : 'default' }}
    >
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '16px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          {Icon && (
            <div style={{
              width: '38px',
              height: '38px',
              borderRadius: '10px',
              background: color ? `${color}20` : 'rgba(16, 185, 129, 0.15)',
              border: `1px solid ${color ? `${color}40` : 'rgba(16, 185, 129, 0.3)'}`,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center'
            }}>
              <Icon size={20} color={color || 'var(--primary)'} />
            </div>
          )}
          <span style={{ fontSize: '0.95rem', fontWeight: 600, color: 'var(--text-muted)' }}>
            {title}
          </span>
        </div>
        <StatusBadge status={status} />
      </div>

      <div style={{ marginBottom: '12px' }}>
        <span style={{ fontSize: '1.9rem', fontWeight: 800, color: 'var(--text-main)', letterSpacing: '-0.02em' }}>
          {value !== undefined && value !== null ? value : '--'}
        </span>
        {unit && (
          <span style={{ fontSize: '1rem', color: 'var(--text-muted)', marginLeft: '6px', fontWeight: 500 }}>
            {unit}
          </span>
        )}
      </div>

      {updatedTime && (
        <div style={{ fontSize: '0.72rem', color: 'var(--text-dim)', marginTop: '8px', display: 'flex', alignItems: 'center', gap: '4px' }}>
          <span>⏱ Updated:</span>
          <span>{new Date(updatedTime).toLocaleTimeString()}</span>
        </div>
      )}
    </div>
  );
}
