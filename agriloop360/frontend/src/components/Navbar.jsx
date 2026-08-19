import React, { useState, useEffect } from 'react';
import { Bell, Play, Square, RefreshCw, Radio } from 'lucide-react';
import { alertService } from '../services/alertService';
import { simulationService } from '../services/simulationService';
import { useNavigate } from 'react-router-dom';

export default function Navbar({ onSimulationChange }) {
  const [unreadCount, setUnreadCount] = useState(0);
  const [isSimulating, setIsSimulating] = useState(true);
  const [loadingTick, setLoadingTick] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchUnread();
    fetchSimulationStatus();

    const interval = setInterval(() => {
      fetchUnread();
    }, 8000);
    return () => clearInterval(interval);
  }, []);

  const fetchUnread = async () => {
    try {
      const count = await alertService.getUnreadCount();
      setUnreadCount(count);
    } catch (err) {
      // quiet catch
    }
  };

  const fetchSimulationStatus = async () => {
    try {
      const res = await simulationService.getStatus();
      setIsSimulating(res.active);
    } catch (err) {
      // quiet catch
    }
  };

  const toggleSimulation = async () => {
    try {
      if (isSimulating) {
        await simulationService.stopSimulation();
        setIsSimulating(false);
      } else {
        await simulationService.startSimulation();
        setIsSimulating(true);
      }
      if (onSimulationChange) onSimulationChange();
    } catch (err) {
      console.error("Error toggling simulation:", err);
    }
  };

  const triggerManualTick = async () => {
    setLoadingTick(true);
    try {
      await simulationService.triggerTick();
      await fetchUnread();
      if (onSimulationChange) onSimulationChange();
    } catch (err) {
      console.error("Error generating tick:", err);
    } finally {
      setTimeout(() => setLoadingTick(false), 500);
    }
  };

  return (
    <header style={{
      height: '64px',
      backgroundColor: 'var(--bg-card)',
      borderBottom: '1px solid var(--border-color)',
      padding: '0 32px',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      position: 'sticky',
      top: 0,
      zIndex: 10
    }}>
      {/* Title / Status */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Radio size={18} color={isSimulating ? '#22c55e' : '#94a3b8'} style={{ animation: isSimulating ? 'pulse 1.5s infinite' : 'none' }} />
          <span style={{ fontSize: '0.85rem', fontWeight: 600, color: 'var(--text-muted)' }}>
            IoT Sensor Stream: {isSimulating ? <span style={{ color: '#22c55e' }}>ONLINE (SIMULATED)</span> : <span style={{ color: '#94a3b8' }}>PAUSED</span>}
          </span>
        </div>
      </div>

      {/* Control Widgets & Notification Bell */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
        {/* Simulation Controls */}
        <div style={{
          display: 'flex',
          alignItems: 'center',
          gap: '8px',
          background: 'var(--bg-dark)',
          border: '1px solid var(--border-color)',
          padding: '4px 8px',
          borderRadius: 'var(--radius-md)'
        }}>
          <button
            onClick={toggleSimulation}
            style={{
              background: isSimulating ? 'rgba(239, 68, 68, 0.2)' : 'rgba(34, 197, 94, 0.2)',
              color: isSimulating ? '#fca5a5' : '#86efac',
              border: isSimulating ? '1px solid rgba(239, 68, 68, 0.4)' : '1px solid rgba(34, 197, 94, 0.4)',
              borderRadius: '6px',
              padding: '5px 10px',
              fontSize: '0.78rem',
              fontWeight: 600,
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              gap: '6px'
            }}
          >
            {isSimulating ? <><Square size={12} /> Stop Sim</> : <><Play size={12} /> Start Sim</>}
          </button>

          <button
            onClick={triggerManualTick}
            disabled={loadingTick}
            title="Generate Sensor Reading Tick Now"
            style={{
              background: 'var(--bg-panel)',
              color: 'var(--text-main)',
              border: '1px solid var(--border-color)',
              borderRadius: '6px',
              padding: '5px 10px',
              fontSize: '0.78rem',
              fontWeight: 600,
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              gap: '6px'
            }}
          >
            <RefreshCw size={12} style={{ animation: loadingTick ? 'spin 1s linear infinite' : 'none' }} />
            Tick Data
          </button>
        </div>

        {/* Alerts Bell */}
        <button
          onClick={() => navigate('/alerts')}
          style={{
            position: 'relative',
            background: 'var(--bg-dark)',
            border: '1px solid var(--border-color)',
            color: 'var(--text-main)',
            width: '40px',
            height: '40px',
            borderRadius: 'var(--radius-md)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: 'pointer'
          }}
        >
          <Bell size={18} color="var(--primary)" />
          {unreadCount > 0 && (
            <span style={{
              position: 'absolute',
              top: '-4px',
              right: '-4px',
              backgroundColor: '#ef4444',
              color: '#ffffff',
              fontSize: '0.7rem',
              fontWeight: 'bold',
              width: '18px',
              height: '18px',
              borderRadius: '50%',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              border: '2px solid var(--bg-card)'
            }}>
              {unreadCount}
            </span>
          )}
        </button>
      </div>
    </header>
  );
}
