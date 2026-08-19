import api from './api';

export const cropService = {
  getAllCrops: async () => {
    const res = await api.get('/crops');
    return res.data;
  },
  getCropsByType: async (type) => {
    const res = await api.get(`/crops/type/${type}`);
    return res.data;
  },
  getCropById: async (id) => {
    const res = await api.get(`/crops/${id}`);
    return res.data;
  },
  createCrop: async (crop) => {
    const res = await api.post('/crops', crop);
    return res.data;
  },
  updateCrop: async (id, crop) => {
    const res = await api.put(`/crops/${id}`, crop);
    return res.data;
  },
  deleteCrop: async (id) => {
    const res = await api.delete(`/crops/${id}`);
    return res.data;
  }
};
