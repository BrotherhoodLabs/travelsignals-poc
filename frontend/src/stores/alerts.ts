import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { alertsApi, type AlertAggregate } from '../services/api'

export const useAlertsStore = defineStore('alerts', () => {
  const alerts = ref<AlertAggregate[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)
  const filters = ref({
    types: [] as string[],
    priorities: [] as string[],
    destination: ''
  })

  const filteredAlerts = computed(() => {
    return alerts.value.filter(alert => {
      // Filtre par type
      if (filters.value.types.length > 0 && !filters.value.types.includes(alert.type)) {
        return false
      }
      
      // Filtre par priorité
      if (filters.value.priorities.length > 0 && !filters.value.priorities.includes(alert.priority)) {
        return false
      }
      
      // Filtre par destination
      if (filters.value.destination && !alert.destination.toLowerCase().includes(filters.value.destination.toLowerCase())) {
        return false
      }
      
      return true
    })
  })

  const alertsByPriority = computed(() => {
    const grouped: Record<string, AlertAggregate[]> = { P1: [], P2: [], P3: [] }
    filteredAlerts.value.forEach(alert => {
      if (grouped[alert.priority]) {
        grouped[alert.priority].push(alert)
      }
    })
    return grouped
  })

  const alertsByType = computed(() => {
    const grouped: Record<string, AlertAggregate[]> = {}
    filteredAlerts.value.forEach(alert => {
      if (!grouped[alert.type]) {
        grouped[alert.type] = []
      }
      grouped[alert.type].push(alert)
    })
    return grouped
  })

  async function fetchAlerts(limit?: number) {
    loading.value = true
    error.value = null
    
    try {
      alerts.value = await alertsApi.getAlerts(limit)
    } catch (err) {
      error.value = err instanceof Error ? err.message : 'Erreur lors du chargement des alertes'
      console.error('Error fetching alerts:', err)
    } finally {
      loading.value = false
    }
  }

  function updateFilters(newFilters: typeof filters.value) {
    filters.value = { ...newFilters }
  }

  function clearFilters() {
    filters.value = {
      types: [],
      priorities: [],
      destination: ''
    }
  }

  return {
    alerts,
    loading,
    error,
    filters,
    filteredAlerts,
    alertsByPriority,
    alertsByType,
    fetchAlerts,
    updateFilters,
    clearFilters
  }
})
