# AGRILOOP 360 — Digitalising the Agri Cycle

A full-stack Smart Agriculture Platform that connects the complete agricultural cycle:

**WATER → SOIL → CROP → FOOD → WASTE → COMPOST → SOIL**

AGRILOOP 360 is built with a **Spring Boot 3 REST API** backend, **MySQL** database, and a modern **React (Vite)** frontend with dynamic interactive charts, real-time sensor simulation, and rule-based agronomic decision engines.

---

## 🌟 Key Modules & Features

### 1. Central 360° Ecosystem Dashboard
- Interactive visual cycle diagram connecting all 6 circular stages.
- Clickable stage telemetry nodes navigating directly to dedicated module pages.
- Real-time Recharts parameter trend graphs and live alert feeds.

### 2. Digital Water Quality Monitor
- Tracks **pH**, **TDS (ppm)**, **Water Temperature (°C)**, and **Turbidity**.
- Evaluates quality status: `EXCELLENT`, `GOOD`, `MODERATE`, `POOR`.
- Generates actionable irrigation guidance (*"Suitable for direct irrigation"*, *"Dilution recommended"*, *"Filtration/settling recommended"*, *"Avoid irrigation until treated"*).
- Implements the complete telemetry flow: **Water Resource → Sensor → Data Handling → Water Quality Status → Irrigation Decision**.

### 3. Digital Soil & Fertilizer Optimization
- Tracks Soil Moisture %, pH, Nitrogen (N), Phosphorus (P), Potassium (K).
- Supports 12 configured crops across 3 categories:
  - **Vegetables**: Brinjal, Green Chilli, Green Beans
  - **Fruits**: Tomato, Strawberry, Apple
  - **Cereals**: Wheat, Rice, Maize, Barley, Oats, Ragi
- Side-by-side comparison matrix: **Current Soil Data** vs **Crop Target Requirement**.
- Calculates derived **Optimization Status**: `OPTIMIZED`, `NEEDS_ADJUSTMENT`, `DEFICIENT`, `EXCESS_APPLICATION`.

### 4. Smart Composting & Waste Recycling
- 5-Stage organic waste recycling pipeline tracker:
  - **Stage 1**: Organic Waste Collection
  - **Stage 2**: Mechanical Separation (Solid vs Liquid)
  - **Stage 3**: Solid Processing (Drying → Grinding → Organic Powder)
  - **Stage 4**: Liquid Processing (Filtration → Nutrient Solution)
  - **Stage 5**: Agricultural Application
- Output yield calculations for Organic Powder (kg) and Liquid Nutrient Solution (Liters).
- Diagnostic remedies for moisture imbalance, low microbial heat, and undecomposed waste.

### 5. Smart Food Preservation
- Micro-environment monitoring for stored produce (Temperature, Humidity, UV-C Status).
- Crop-specific storage safety range verification stored in the database.
- Low-cost evaporative cooling chamber and sanitizing UV-C treatment controls.
- Warnings for chilling injury risk, rapid spoilage risk, fungal humidity risk, and excess UV-C exposure.

### 6. Crop Management
- Full lifecycle tracking: Planting Date, Expected Harvest Date, Soil Type, and cross-module health indicators.

### 7. Automated System Alert Engine
- Categorized by Severity (`CRITICAL` 🔴, `WARNING` 🟡, `INFO` 🔵) and Module (`WATER`, `SOIL`, `COMPOST`, `STORAGE`, `CROP`).

### 8. Hardware-Free Sensor Simulation Engine
- Background tick simulation generating realistic randomized fluctuations for pH, TDS, moisture, NPK, temperature, and humidity.
- Start, Stop, and Manual Tick REST endpoints allowing hardware-free live testing.

---

## 🛠️ Technology Stack

| Layer | Technology |
|---|---|
| **Frontend** | React 18, Vite, JavaScript (ES6+), React Router v6, Recharts, Lucide Icons, Custom CSS Design System |
| **Backend** | Java 17/25, Spring Boot 3.2.5, Spring Data JPA, REST APIs, Validation |
| **Database** | MySQL (Database: `agriloop360`) |
| **Build Tools** | Maven, npm |

---

## 📁 Project Structure

```
agriloop360/
├── backend/
│   ├── src/main/java/com/agriloop360/
│   │   ├── config/             # CORS, DataInitializer (seeds 12 crops, demo user, baseline readings)
│   │   ├── controller/         # REST Controllers (Water, Soil, Fertilizer, Compost, Storage, Crop, Alert, Simulation, Auth, Dashboard)
│   │   ├── dto/                # Request/Response DTOs
│   │   ├── entity/             # MySQL JPA Entities (User, Crop, WaterReading, SoilReading, FertilizerRecommendation, CompostBatch, FoodStorage, Alert)
│   │   ├── enumtype/           # Enums (Severity, ModuleName, CompostStage, WaterStatus, StorageSafetyStatus, CropType)
│   │   ├── exception/          # GlobalExceptionHandler & ResourceNotFoundException
│   │   ├── repository/         # Spring Data JPA Repositories
│   │   └── service/            # Business Logic, Recommendation Engine & Sensor Simulation Engine
│   ├── src/main/resources/
│   │   └── application.yml     # Database credentials & server settings
│   └── pom.xml                 # Maven build manifest
├── frontend/
│   ├── src/
│   │   ├── components/         # Sidebar, Navbar, InteractiveCycle, MetricCard, StatusBadge
│   │   ├── pages/              # 11 Application Pages (Dashboard, Water, Soil, Compost, Storage, Crops, Alerts, Reports, Settings, Login, Register)
│   │   ├── services/           # Axios REST API wrappers
│   │   ├── App.jsx             # Main Router
│   │   └── index.css           # Glassmorphism & Emerald Agriculture Theme CSS
│   ├── package.json
│   └── vite.config.js
└── README.md
```

---

## 🗄️ Database Setup

The backend connects to MySQL on port `3306`.

### Credentials / Config
- **Host**: `localhost:3306`
- **Database**: `agriloop360`
- **Username**: `root`
- **Password**: `root`

### Environment Variable Overrides
```bash
export MYSQL_URL="jdbc:mysql://localhost:3306/agriloop360?createDatabaseIfNotExist=true&useSSL=false"
export MYSQL_USER="root"
export MYSQL_PASSWORD="your_mysql_password"
```

---

## 🚀 How to Run the Application

### 1. Prerequisites
- Java 17 or higher
- Maven 3.8+
- Node.js 18+ & npm
- MySQL Server running on port 3306

### 2. Start MySQL Database
```sql
CREATE DATABASE IF NOT EXISTS agriloop360;
```

### 3. Build & Run Backend
```bash
cd agriloop360/backend
mvn clean package -DskipTests
java -jar target/agriloop360-backend-1.0.0.jar
```
The REST API server starts on **http://localhost:8080**.

### 4. Install & Run Frontend
```bash
cd agriloop360/frontend
npm install
npm run dev
```
The React application opens on **http://localhost:5173**.

---

## 🔑 Sample Login Credentials

| Role | Email | Password |
|---|---|---|
| **Administrator** | `admin@agriloop360.com` | `password123` |

*(Or click **"Auto-Fill Demo Credentials"** on the Login page!)*

---

## 📡 REST API Documentation

### Auth
- `POST /api/auth/register` — Register a new account
- `POST /api/auth/login` — Sign in

### Dashboard
- `GET /api/dashboard/summary` — Consolidated health summary, cycle telemetry, recent alerts, simulation status

### Water Quality Monitor
- `GET /api/water/status` — Get latest water reading & irrigation recommendation
- `GET /api/water/readings` — Get historical water readings trend
- `POST /api/water/readings` — Post new water quality reading

### Soil & Fertilizer Optimization
- `GET /api/soil/status?cropId={id}` — Get latest soil status for crop
- `GET /api/soil/readings?cropId={id}` — Get historical soil readings
- `POST /api/soil/readings?cropId={id}` — Record soil sample & trigger fertilizer engine
- `POST /api/fertilizer/recommendation?cropId={id}` — Generate crop-specific fertilizer advice

### Smart Composting
- `GET /api/compost` — List all active compost batches & stage status
- `POST /api/compost` — Create new compost batch
- `PUT /api/compost/{id}/stage?stage={STAGE}` — Advance compost batch to next stage

### Smart Food Preservation
- `GET /api/storage` — List all food storage vaults & safety warnings
- `POST /api/storage?cropId={id}` — Deploy controlled storage chamber
- `PUT /api/storage/{id}/uvc?active={boolean}` — Toggle UV-C sanitization treatment

### Crop Management
- `GET /api/crops` — List all 12 crops & target profiles
- `POST /api/crops` — Register new crop profile
- `DELETE /api/crops/{id}` — Delete crop profile

### Alerts
- `GET /api/alerts` — Fetch system alerts feed (Filterable by `severity` and `module`)
- `PUT /api/alerts/{id}/read` — Mark alert as read

### Sensor Simulation Engine
- `GET /api/simulation/status` — Check if live simulation background tick is active
- `POST /api/simulation/start` — Start simulated IoT sensor ticks
- `POST /api/simulation/stop` — Pause simulation
- `POST /api/simulation/tick` — Force immediate sensor reading tick across all 4 modules

---

## 🛰️ Future Physical IoT Hardware Integration

The architecture is designed to integrate physical **ESP32**, **Arduino**, or **LoRaWAN** hardware sensors seamlessly:

1. **Hardware Telemetry Endpoint**:
   Physical microcontrollers submit JSON payloads directly to `POST /api/water/readings`, `POST /api/soil/readings`, and `POST /api/storage`.
2. **Modular Engine**:
   The recommendation engine processes hardware readings through the exact same rule pipelines without requiring code modifications.

---

## 🎯 Circular Ecosystem Demonstration Flow

1. Open **http://localhost:5173** and click **Dashboard**.
2. Inspect the **Interactive 360 Agri-Cycle Diagram**: `WATER → SOIL → CROP → FOOD → WASTE → COMPOST → SOIL`.
3. Click **Water Quality** node → View pH, TDS, Temp, Turbidity & irrigation decisions.
4. Click **Soil & Fertilizer** node → Change selected crop from *Tomato* to *Rice* to observe dynamic target requirement updates & fertilizer recommendations.
5. Click **Smart Composting** node → Advance a batch through Stage 1 to Stage 5 to observe Organic Powder & Nutrient Solution yield generation.
6. Click **Food Preservation** node → Check temperature/humidity warnings and toggle UV-C sanitization.
7. Click **Sensor Simulation Toggle** on the top navbar → Click **Tick Data** to observe real-time sensor updates across all modules!
