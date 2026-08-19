# 🚀 AGRILOOP 360 — Vercel Deployment Guide

This guide details how to deploy the **AGRILOOP 360** full-stack application using **Vercel** for the React frontend and a cloud provider (such as Render, Railway, or Fly.io) for the Spring Boot backend and MySQL database.

---

## 🏗️ Architecture for Deployment

| Component | Hosted On | Role |
|---|---|---|
| **Frontend** | **Vercel** | React Vite Single Page Application, Recharts dashboard, responsive UI |
| **Backend** | **Render / Railway / Docker** | Java Spring Boot REST API, Recommendation Engine & Sensor Simulator |
| **Database** | **Render MySQL / Aiven / Railway** | Managed MySQL Database |

---

## 🛠️ Step 1: Deploying the Frontend to Vercel

### Method A: Via Vercel Dashboard (Recommended)

1. Push your project to **GitHub**, **GitLab**, or **Bitbucket**.
2. Go to [Vercel.com](https://vercel.com) and click **"Add New" → "Project"**.
3. Import your **AGRILOOP 360** GitHub repository.
4. In the Project Setup screen:
   - **Framework Preset**: Vite
   - **Root Directory**: Select `agriloop360/frontend`
   - **Build Command**: `npm run build`
   - **Output Directory**: `dist`
5. Expand **Environment Variables** and add:
   - **Key**: `VITE_API_BASE_URL`
   - **Value**: `https://your-backend-api.onrender.com/api` *(replace with your hosted Spring Boot backend URL)*
6. Click **Deploy**. Vercel will build and assign a live URL (e.g. `https://agriloop360.vercel.app`).

---

### Method B: Via Vercel CLI

If you have the `vercel` CLI installed locally:

```bash
cd agriloop360/frontend
vercel
```

Follow the interactive CLI prompts to link and deploy to Vercel.

---

## ⚙️ Step 2: Deploying the Spring Boot Backend & MySQL Database

Vercel natively hosts frontends and serverless Node.js/Python functions. For the Java 17/25 Spring Boot JVM process and MySQL database, use any of these free/affordable backend cloud platforms:

### Option 1: Render.com (Recommended Free Tier)

1. Create a free account on [Render.com](https://render.com).
2. **Deploy MySQL Database**:
   - Click **New → MySQL**.
   - Set Name to `agriloop360-db`, Database Name to `agriloop360`.
   - Copy the provided MySQL Connection URL, Username, and Password.
3. **Deploy Spring Boot Web Service**:
   - Click **New → Web Service**.
   - Connect your GitHub repository and set Root Directory to `agriloop360/backend`.
   - Set Build Command: `./mvnw clean package -DskipTests` (or `mvn clean package -DskipTests`)
   - Set Start Command: `java -jar target/agriloop360-backend-1.0.0.jar`
   - Add Environment Variables:
     - `MYSQL_URL`: `jdbc:mysql://<your-render-db-host>:3306/agriloop360`
     - `MYSQL_USER`: `<your-db-user>`
     - `MYSQL_PASSWORD`: `<your-db-password>`
4. Copy your backend service URL (e.g. `https://agriloop360-backend.onrender.com`).

---

### Option 2: Railway.app (One-Click Setup)

1. Go to [Railway.app](https://railway.app) and create a new project.
2. Add a **MySQL** plugin service.
3. Add a **GitHub Repo** service pointing to `agriloop360/backend`. Railway automatically detects Java Maven, builds the JAR, and links the MySQL credentials automatically.

---

## 🔄 Step 3: Connecting Vercel Frontend to Hosted Backend

Once your backend is live (e.g., `https://agriloop360-backend.onrender.com`):

1. Go to your project on Vercel: **Settings → Environment Variables**.
2. Update `VITE_API_BASE_URL` to `https://agriloop360-backend.onrender.com/api`.
3. Re-deploy the project on Vercel.

Your Vercel React frontend will now communicate seamlessly with your hosted Spring Boot backend and MySQL database across all 4 AGRILOOP 360 modules!

---

## 🧪 Testing Vercel Configuration Locally

We have created two pre-configured deployment files:
1. `agriloop360/frontend/vercel.json` — For direct frontend subfolder deployments.
2. `vercel.json` — For root repository deployments.

You can preview the production Vite build locally at any time:
```bash
cd agriloop360/frontend
npm run build
npm run preview
```
