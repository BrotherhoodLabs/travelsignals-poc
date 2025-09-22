<template>
  <div class="counters-page">
    <h1>Compteurs d'Événements</h1>
    
    <div v-if="loading" class="loading">
      Chargement des compteurs...
    </div>
    
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    
    <div v-else class="counters-container">
      <div class="counters-grid">
        <div 
          v-for="(count, eventType) in counters" 
          :key="eventType"
          class="counter-card"
        >
          <div class="counter-icon">
            {{ getEventIcon(eventType) }}
          </div>
          <div class="counter-content">
            <h3>{{ formatEventType(eventType) }}</h3>
            <div class="counter-number">{{ count }}</div>
            <div class="counter-label">événements traités</div>
          </div>
        </div>
      </div>
      
      <div class="refresh-info">
        <p>Dernière mise à jour: {{ lastUpdate }}</p>
        <button @click="refreshCounters" class="refresh-btn">
          Actualiser
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { alertsApi, type EventCounters } from '../services/api'

const counters = ref<EventCounters>({})
const loading = ref(false)
const error = ref<string | null>(null)
const lastUpdate = ref('')
let refreshInterval: number | null = null

onMounted(() => {
  fetchCounters()
  // Actualiser toutes les 30 secondes
  refreshInterval = setInterval(fetchCounters, 30000)
})

onUnmounted(() => {
  if (refreshInterval) {
    clearInterval(refreshInterval)
  }
})

async function fetchCounters() {
  loading.value = true
  error.value = null
  
  try {
    counters.value = await alertsApi.getCounters()
    lastUpdate.value = new Date().toLocaleString('fr-FR')
  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Erreur lors du chargement des compteurs'
    console.error('Error fetching counters:', err)
  } finally {
    loading.value = false
  }
}

function refreshCounters() {
  fetchCounters()
}

function getEventIcon(eventType: string): string {
  const icons: Record<string, string> = {
    'price-updates': '💰',
    'weather-alerts': '🌤️',
    'flight-status': '✈️',
    'visa-reminders': '📋'
  }
  return icons[eventType] || '📊'
}

function formatEventType(eventType: string): string {
  const labels: Record<string, string> = {
    'price-updates': 'Mises à jour de prix',
    'weather-alerts': 'Alertes météo',
    'flight-status': 'Statuts de vol',
    'visa-reminders': 'Rappels visa'
  }
  return labels[eventType] || eventType
}
</script>

<style scoped>
.counters-page {
  max-width: 1200px;
  margin: 0 auto;
}

.counters-page h1 {
  margin-bottom: 30px;
  color: #2c3e50;
}

.loading, .error {
  text-align: center;
  padding: 40px;
  color: #7f8c8d;
}

.error {
  color: #e74c3c;
}

.counters-container {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.counters-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.counter-card {
  background: white;
  border-radius: 12px;
  padding: 25px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  gap: 20px;
  transition: transform 0.3s, box-shadow 0.3s;
}

.counter-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 15px rgba(0,0,0,0.15);
}

.counter-icon {
  font-size: 3rem;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  color: white;
}

.counter-content {
  flex: 1;
}

.counter-content h3 {
  margin: 0 0 10px 0;
  color: #2c3e50;
  font-size: 1.1rem;
  font-weight: 600;
}

.counter-number {
  font-size: 2.5rem;
  font-weight: bold;
  color: #667eea;
  margin-bottom: 5px;
}

.counter-label {
  color: #7f8c8d;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.refresh-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.refresh-info p {
  margin: 0;
  color: #6c757d;
  font-size: 0.9rem;
}

.refresh-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: background-color 0.3s;
}

.refresh-btn:hover {
  background: #5a6fd8;
}
</style>
