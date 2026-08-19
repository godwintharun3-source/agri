import api from './api';

export const fertilizerService = {
  generateRecommendation: async (cropId) => {
    const res = await api.post(`/fertilizer/recommendation?cropId=${cropId}`);
    return res.data;
  },
  getRecommendations: async () => {
    const res = await api.get('/fertilizer/recommendations');
    return res.data;
  },
  getLatestForCrop: async (cropId) => {
    const res = await api.get(`/fertilizer/crop/${cropId}`);
    return res.data;
  }
};
