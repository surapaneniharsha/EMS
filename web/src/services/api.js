import axios from 'axios';
import { auth } from '../firebase/firebase';

const API_BASE_URL =
  import.meta.env.VITE_API_URL ||
  (import.meta.env.DEV ? '/ems/' : 'http://localhost:8080/ems/');

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  async (config) => {
    const currentUser = auth.currentUser;
    if (currentUser) {
      try {
        const token = await currentUser.getIdToken();
        config.headers.Authorization = `Bearer ${token}`;
      } catch (err) {
        console.error('Failed to get auth token:', err);
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const currentUser = auth.currentUser;
      if (currentUser) {
        try {
          const token = await currentUser.getIdToken(true);
          originalRequest.headers.Authorization = `Bearer ${token}`;
          return api(originalRequest);
        } catch (refreshErr) {
          console.error('Token refresh failed:', refreshErr);
        }
      }
    }

    return Promise.reject(error);
  }
);

export const employeeApi = {
  getAll: () => api.get('/getEmployees'),
  getById: (id) => api.get(`/getEmployee/${id}`),
  create: (data) => api.post('/createEmployee', data),
  update: (id, data) => api.put(`/updateEmployee/${id}`, data),
  delete: (id) => api.delete(`/deleteEmployee/${id}`),
};

export const departmentApi = {
  getAll: () => api.get('/getDepartments'),
  getById: (id) => api.get(`/getDepartment/${id}`),
  create: (data) => api.post('/createDepartment', data),
  update: (id, data) => api.put(`/updateDepartment/${id}`, data),
  delete: (id) => api.delete(`/deleteDepartment/${id}`),
};

export default api;
