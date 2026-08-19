import api from './api';

export const soilService = {
  getLatestStatus: async (cropId) => {
    const url = cropId ? `/soil/status?cropId=${cropId}` : '/soil/status';
    const res = await api.get(url);
    return res.data;
  },
  getReadings: async (cropId) => {
    const url = cropId ? `/soil/readings?cropId=${cropId}` : '/soil/readings';
    const res = await api.get(url);
    return res.data;
  },
  recordReading: async (reading, cropId) => {
    const url = cropId ? `/soil/readings?cropId=${cropId}` : '/soil/readings';
    const res = await api.post(url, reading);
    return res.data;
  }
};
