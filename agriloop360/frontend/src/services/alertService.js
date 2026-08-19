import api from './api';

export const alertService = {
  getAllAlerts: async (severity, module) => {
    let query = [];
    if (severity) query.push(`severity=${severity}`);
    if (module) query.push(`module=${module}`);
    const qStr = query.length > 0 ? `?${query.join('&')}` : '';
    const res = await api.get(`/alerts${qStr}`);
    return res.data;
  },
  getUnreadAlerts: async () => {
    const res = await api.get('/alerts/unread');
    return res.data;
  },
  getUnreadCount: async () => {
    const res = await api.get('/alerts/unread-count');
    return res.data.count;
  },
  markAsRead: async (id) => {
    const res = await api.put(`/alerts/${id}/read`);
    return res.data;
  },
  markAllAsRead: async () => {
    const res = await api.put('/alerts/read-all');
    return res.data;
  }
};
