import api from './api';

export const simulationService = {
  getStatus: async () => {
    const res = await api.get('/simulation/status');
    return res.data;
  },
  startSimulation: async () => {
    const res = await api.post('/simulation/start');
    return res.data;
  },
  stopSimulation: async () => {
    const res = await api.post('/simulation/stop');
    return res.data;
  },
  triggerTick: async () => {
    const res = await api.post('/simulation/tick');
    return res.data;
  }
};
