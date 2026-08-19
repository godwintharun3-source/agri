import api from './api';

export const waterService = {
  getLatestStatus: async () => {
    const res = await api.get('/water/status');
    return res.data;
  },
  getReadings: async () => {
    const res = await api.get('/water/readings');
    return res.data;
  },
  createReading: async (reading) => {
    const res = await api.post('/water/readings', reading);
    return res.data;
  }
};
