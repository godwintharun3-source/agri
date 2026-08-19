import api from './api';

export const foodStorageService = {
  getAllStorageUnits: async () => {
    const res = await api.get('/storage');
    return res.data;
  },
  getStorageById: async (id) => {
    const res = await api.get(`/storage/${id}`);
    return res.data;
  },
  createStorage: async (storage, cropId) => {
    const url = cropId ? `/storage?cropId=${cropId}` : '/storage';
    const res = await api.post(url, storage);
    return res.data;
  },
  toggleUvc: async (id, active) => {
    const res = await api.put(`/storage/${id}/uvc?active=${active}`);
    return res.data;
  },
  getStorageReadings: async (id) => {
    const res = await api.get(`/storage/${id}/readings`);
    return res.data;
  }
};
