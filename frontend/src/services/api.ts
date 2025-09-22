import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

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

  async getCounters(): Promise<EventCounters> {
    const response = await api.get('/api/alerts/counters')
    return response.data
  }
}

export default api
