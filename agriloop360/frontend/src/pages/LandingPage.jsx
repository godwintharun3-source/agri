import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Sparkles, Droplets, Sprout, Recycle, ShieldAlert, ArrowRight, CheckCircle2 } from 'lucide-react';

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <div style={{ minHeight: '100vh', backgroundColor: 'var(--bg-dark)', color: 'var(--text-main)' }}>
      {/* Landing Navbar */}
      <header style={{
        height: '70px',
        padding: '0 48px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        borderBottom: '1px solid var(--border-color)',
        backgroundColor: 'var(--bg-card)'
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <div style={{
            width: '38px',
            height: '38px',
            borderRadius: '10px',
            background: 'linear-gradient(135deg, #10b981, #059669)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
          }}>
            <Sparkles size={20} color="#ffffff" />
          </div>
          <h2 style={{ fontSize: '1.3rem', letterSpacing: '-0.01em' }}>
            AGRILOOP <span style={{ color: 'var(--primary)' }}>360</span>
          </h2>
        </div>

        <div style={{ display: 'flex', gap: '12px' }}>
          <button className="btn-secondary" onClick={() => navigate('/login')}>
            Sign In
          </button>
          <button className="btn-primary" onClick={() => navigate('/register')}>
            Get Started
          </button>
        </div>
      </header>

      {/* Hero Section */}
      <section style={{
        padding: '80px 32px',
        maxWidth: '1100px',
        margin: '0 auto',
        textAlign: 'center',
        background: 'radial-gradient(circle at 50% 20%, #104e38 0%, transparent 60%)'
      }}>
        <div style={{
          display: 'inline-flex',
          alignItems: 'center',
          gap: '8px',
          padding: '6px 16px',
          borderRadius: '9999px',
          background: 'rgba(16, 185, 129, 0.15)',
          border: '1px solid rgba(16, 185, 129, 0.3)',
          color: '#86efac',
          fontSize: '0.85rem',
          fontWeight: 700,
          marginBottom: '20px'
        }}>
          ✨ DIGITALISING THE AGRICULTURAL CYCLE
        </div>

        <h1 style={{ fontSize: '3.2rem', lineHeight: 1.15, marginBottom: '20px', fontWeight: 800 }}>
          The Complete Circular Smart Agriculture Ecosystem
        </h1>

        <p style={{ fontSize: '1.15rem', color: 'var(--text-muted)', maxWidth: '750px', margin: '0 auto 36px', lineHeight: 1.6 }}>
          Connecting every stage of farm production into one seamless digital loop:
          <br />
          <strong style={{ color: 'var(--primary)' }}>Water → Soil → Crop → Food → Waste → Compost → Soil</strong>
        </p>

        <div style={{ display: 'flex', justifyContent: 'center', gap: '16px' }}>
          <button className="btn-primary" style={{ padding: '14px 28px', fontSize: '1rem' }} onClick={() => navigate('/dashboard')}>
            Launch Live Demo Dashboard <ArrowRight size={18} />
          </button>
        </div>
      </section>

      {/* 4 Major Modules Showcase */}
      <section style={{ padding: '40px 32px 80px', maxWidth: '1200px', margin: '0 auto' }}>
        <h2 style={{ textAlign: 'center', fontSize: '1.8rem', marginBottom: '40px' }}>
          Four Integrated Core Modules
        </h2>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))', gap: '24px' }}>
          <div className="agri-card">
            <Droplets size={32} color="#3b82f6" style={{ marginBottom: '12px' }} />
            <h3 style={{ fontSize: '1.2rem', marginBottom: '8px' }}>1. Water Quality Monitor</h3>
            <p style={{ fontSize: '0.88rem', color: 'var(--text-muted)', lineHeight: 1.5 }}>
              Real-time pH, TDS, Temperature, and Turbidity monitoring with intelligent irrigation decision recommendations.
            </p>
          </div>

          <div className="agri-card">
            <Sprout size={32} color="#10b981" style={{ marginBottom: '12px' }} />
            <h3 style={{ fontSize: '1.2rem', marginBottom: '8px' }}>2. Soil & Fertilizer Optimization</h3>
            <p style={{ fontSize: '0.88rem', color: 'var(--text-muted)', lineHeight: 1.5 }}>
              Crop-specific NPK nutrient balances, moisture telemetry, and precision fertilizer advice for 12 standard crops.
            </p>
          </div>

          <div className="agri-card">
            <Recycle size={32} color="#ec4899" style={{ marginBottom: '12px' }} />
            <h3 style={{ fontSize: '1.2rem', marginBottom: '8px' }}>3. Smart Composting</h3>
            <p style={{ fontSize: '0.88rem', color: 'var(--text-muted)', lineHeight: 1.5 }}>
              5-Stage recycling pipeline converting organic farm waste into High-Nutrient Organic Powder and Liquid Extract.
            </p>
          </div>

          <div className="agri-card">
            <ShieldAlert size={32} color="#f59e0b" style={{ marginBottom: '12px' }} />
            <h3 style={{ fontSize: '1.2rem', marginBottom: '8px' }}>4. Smart Food Preservation</h3>
            <p style={{ fontSize: '0.88rem', color: 'var(--text-muted)', lineHeight: 1.5 }}>
              Evaporative cooling, humidity control, and sanitizing UV-C exposure treatment to prevent post-harvest spoilage.
            </p>
          </div>
        </div>
      </section>
    </div>
  );
}
