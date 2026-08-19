import React from 'react';

export default function StatusBadge({ status }) {
  const getBadgeClass = (s) => {
    if (!s) return 'good';
    const lower = s.toLowerCase();
    if (lower.includes('good') || lower.includes('excellent') || lower.includes('optimized') || lower.includes('safe') || lower.includes('healthy')) {
      return 'good';
    }
    if (lower.includes('moderate') || lower.includes('warning') || lower.includes('needs_adjustment') || lower.includes('active')) {
      return 'warning';
    }
    if (lower.includes('poor') || lower.includes('critical') || lower.includes('deficient') || lower.includes('risk')) {
      return 'critical';
    }
    return 'good';
  };

  return (
    <span className={`status-badge ${getBadgeClass(status)}`}>
      <span style={{
        width: '6px',
        height: '6px',
        borderRadius: '50%',
        backgroundColor: 'currentColor'
      }} />
      {status || 'GOOD'}
    </span>
  );
}
