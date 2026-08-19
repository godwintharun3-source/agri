import api from './api';

export const compostService = {
  getAllBatches: async () => {
    const res = await api.get('/compost');
    return res.data;
  },
  getBatchById: async (id) => {
    const res = await api.get(`/compost/${id}`);
    return res.data;
  },
  createBatch: async (batch) => {
    const res = await api.post('/compost', batch);
    return res.data;
  },
  updateStage: async (id, stage) => {
    const res = await api.put(`/compost/${id}/stage?stage=${stage}`);
    return res.data;
  },
  getBatchReadings: async (id) => {
    const res = await api.get(`/compost/${id}/readings`);
    return res.data;
  }
};
