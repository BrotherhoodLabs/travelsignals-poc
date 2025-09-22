<template>
  <div class="alerts-page">
    <div class="page-header">
      <h1>Alertes de Voyage</h1>
      <button @click="refreshAlerts" class="refresh-btn" :disabled="alertsStore.loading">
        {{ alertsStore.loading ? 'Chargement...' : 'Actualiser' }}
      </button>
    </div>

    <div v-if="alertsStore.loading" class="loading">
      Chargement des alertes...
    </div>
    
    <div v-else-if="alertsStore.error" class="error">
      {{ alertsStore.error }}
    </div>
    
    <div v-else-if="alertsStore.filteredAlerts.length === 0" class="no-alerts">
      Aucune alerte trouvée avec les filtres actuels
    </div>
    
    <div v-else class="alerts-container">
      <div 
        v-for="alert in alertsStore.filteredAlerts" 
        :key="alert.ts"
        class="alert-item"
        :class="`priority-${alert.priority.toLowerCase()}`"
        @click="selectAlert(alert)"
      >
        <div class="alert-header">
          <div class="alert-badges">
            <span class="alert-type">{{ alert.type }}</span>
            <span class="alert-priority">{{ alert.priority }}</span>
          </div>
          <span class="alert-time">{{ formatTime(alert.ts) }}</span>
        </div>
        
        <h3 class="alert-title">{{ alert.title }}</h3>
        <p class="alert-destination">{{ alert.destination }}</p>
        
        <div class="alert-details" v-if="selectedAlert?.ts === alert.ts">
          <h4>Détails</h4>
          <pre>{{ JSON.stringify(alert.details, null, 2) }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAlertsStore } from '../stores/alerts'
import type { AlertAggregate } from '../services/api'

const alertsStore = useAlertsStore()
const selectedAlert = ref<AlertAggregate | null>(null)

onMounted(() => {
  alertsStore.fetchAlerts()
})

function refreshAlerts() {
  alertsStore.fetchAlerts()
}

function selectAlert(alert: AlertAggregate) {
  selectedAlert.value = selectedAlert.value?.ts === alert.ts ? null : alert
}

function formatTime(timestamp: string): string {
  return new Date(timestamp).toLocaleString('fr-FR')
}
</script>

<style scoped>
.alerts-page {
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h1 {
  color: #2c3e50;
  margin: 0;
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

.refresh-btn:hover:not(:disabled) {
  background: #5a6fd8;
}

.refresh-btn:disabled {
  background: #bdc3c7;
  cursor: not-allowed;
}

.loading, .error, .no-alerts {
  text-align: center;
  padding: 40px;
  color: #7f8c8d;
}

.error {
  color: #e74c3c;
}

.alerts-container {
  display: grid;
  gap: 15px;
}

.alert-item {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s;
  border-left: 4px solid #667eea;
}

.alert-item:hover {
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.alert-item.priority-p1 {
  border-left-color: #e74c3c;
}

.alert-item.priority-p2 {
  border-left-color: #f39c12;
}

.alert-item.priority-p3 {
  border-left-color: #3498db;
}

.alert-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.alert-badges {
  display: flex;
  gap: 10px;
}

.alert-type {
  background: #ecf0f1;
  color: #2c3e50;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: bold;
  text-transform: uppercase;
}

.alert-priority {
  background: #667eea;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: bold;
}

.alert-item.priority-p1 .alert-priority {
  background: #e74c3c;
}

.alert-item.priority-p2 .alert-priority {
  background: #f39c12;
}

.alert-item.priority-p3 .alert-priority {
  background: #3498db;
}

.alert-time {
  color: #95a5a6;
  font-size: 0.8rem;
}

.alert-title {
  margin-bottom: 8px;
  color: #2c3e50;
  font-size: 1.2rem;
}

.alert-destination {
  color: #7f8c8d;
  margin-bottom: 15px;
  font-size: 0.9rem;
}

.alert-details {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #ecf0f1;
}

.alert-details h4 {
  margin-bottom: 10px;
  color: #2c3e50;
  font-size: 1rem;
}

.alert-details pre {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 4px;
  font-size: 0.8rem;
  color: #495057;
  overflow-x: auto;
}
</style>
