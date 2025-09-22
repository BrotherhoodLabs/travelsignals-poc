import axios, { AxiosError, AxiosResponse } from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})

// Intercepteur pour la gestion des erreurs
api.interceptors.response.use(
  (response: AxiosResponse) => response,
  (error: AxiosError) => {
    console.error('API Error:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

export interface AlertAggregate {
  type: string
  destination: string
  priority: string
  title: string
  details: Record<string, any>
  ts: string
}

export interface EventCounters {
  [key: string]: number
}

export const alertsApi = {
  async getAlerts(limit?: number): Promise<AlertAggregate[]> {
    const params = limit ? { limit } : {}
    const response = await api.get('/api/alerts', { params })
    return response.data
  },

  async getAlert(id: string): Promise<AlertAggregate> {
    const response = await api.get(`/api/alerts/${id}`)
    return response.data
  },

  async getAlertsByType(type: string): Promise<AlertAggregate[]> {
    const response = await api.get(`/api/alerts/type/${type}`)
    return response.data
  },

  async getAlertsByPriority(priority: string): Promise<AlertAggregate[]> {
    const response = await api.get(`/api/alerts/priority/${priority}`)
    return response.data
  },

  async getCounters(): Promise<EventCounters> {
    const response = await api.get('/api/alerts/counters')
    return response.data
  },

  async generateRandomAlert(): Promise<AlertAggregate> {
    const response = await api.post('/api/generate/random')
    return response.data
  },

  async populateTestData(): Promise<string> {
    const response = await api.post('/api/test-data/populate')
    return response.data
  }
}

export default api
