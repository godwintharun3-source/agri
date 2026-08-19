import api from './api';

export const authService = {
  login: async (credentials) => {
    const res = await api.post('/auth/login', credentials);
    if (res.data.token) {
      localStorage.setItem('agriloop_token', res.data.token);
      localStorage.setItem('agriloop_user', JSON.stringify(res.data));
    }
    return res.data;
  },
  register: async (userData) => {
    const res = await api.post('/auth/register', userData);
    if (res.data.token) {
      localStorage.setItem('agriloop_token', res.data.token);
      localStorage.setItem('agriloop_user', JSON.stringify(res.data));
    }
    return res.data;
  },
  logout: () => {
    localStorage.removeItem('agriloop_token');
    localStorage.removeItem('agriloop_user');
  },
  getCurrentUser: () => {
    const userStr = localStorage.getItem('agriloop_user');
    return userStr ? JSON.parse(userStr) : null;
  }
};
