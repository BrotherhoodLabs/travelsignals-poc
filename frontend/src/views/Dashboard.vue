<template>
  <div class="dashboard">
    <h1>Dashboard TravelSignals</h1>
    
    <div class="stats-grid">
      <div class="stat-card">
        <h3>Alertes Total</h3>
        <div class="stat-number">{{ alertsStore.filteredAlerts.length }}</div>
      </div>
      
      <div class="stat-card priority-p1">
        <h3>Priorité P1</h3>
        <div class="stat-number">{{ alertsStore.alertsByPriority.P1.length }}</div>
      </div>
      
      <div class="stat-card priority-p2">
        <h3>Priorité P2</h3>
        <div class="stat-number">{{ alertsStore.alertsByPriority.P2.length }}</div>
      </div>
      
      <div class="stat-card priority-p3">
        <h3>Priorité P3</h3>
        <div class="stat-number">{{ alertsStore.alertsByPriority.P3.length }}</div>
      </div>
    </div>

    <div class="recent-alerts">
      <h2>Alertes Récentes</h2>
      <div v-if="alertsStore.loading" class="loading">
        Chargement des alertes...
      </div>
      <div v-else-if="alertsStore.error" class="error">
        {{ alertsStore.error }}
      </div>
      <div v-else-if="alertsStore.filteredAlerts.length === 0" class="no-alerts">
        Aucune alerte trouvée
      </div>
      <div v-else class="alerts-list">
        <div 
          v-for="alert in alertsStore.filteredAlerts.slice(0, 5)" 
          :key="alert.ts"
          class="alert-card"
          :class="`priority-${alert.priority.toLowerCase()}`"
        >
          <div class="alert-header">
            <span class="alert-type">{{ alert.type }}</span>
            <span class="alert-priority">{{ alert.priority }}</span>
          </div>
          <h4 class="alert-title">{{ alert.title }}</h4>
          <p class="alert-destination">{{ alert.destination }}</p>
          <p class="alert-time">{{ formatTime(alert.ts) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useAlertsStore } from '../stores/alerts'

const alertsStore = useAlertsStore()

onMounted(() => {
  alertsStore.fetchAlerts(20)
})

function formatTime(timestamp: string): string {
  return new Date(timestamp).toLocaleString('fr-FR')
}
</script>

<style scoped>
.dashboard {
  max-width: 1200px;
  margin: 0 auto;
}

.dashboard h1 {
  margin-bottom: 30px;
  color: #2c3e50;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  text-align: center;
  border-left: 4px solid #667eea;
}

.stat-card.priority-p1 {
  border-left-color: #e74c3c;
}

.stat-card.priority-p2 {
  border-left-color: #f39c12;
}

.stat-card.priority-p3 {
  border-left-color: #3498db;
}

.stat-card h3 {
  margin-bottom: 10px;
  color: #7f8c8d;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: bold;
  color: #2c3e50;
}

.recent-alerts h2 {
  margin-bottom: 20px;
  color: #2c3e50;
}

.loading, .error, .no-alerts {
  text-align: center;
  padding: 40px;
  color: #7f8c8d;
}

.error {
  color: #e74c3c;
}

.alerts-list {
  display: grid;
  gap: 15px;
}

.alert-card {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  border-left: 4px solid #667eea;
}

.alert-card.priority-p1 {
  border-left-color: #e74c3c;
}

.alert-card.priority-p2 {
  border-left-color: #f39c12;
}

.alert-card.priority-p3 {
  border-left-color: #3498db;
}

.alert-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.alert-type {
  background: #ecf0f1;
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

.alert-card.priority-p1 .alert-priority {
  background: #e74c3c;
}

.alert-card.priority-p2 .alert-priority {
  background: #f39c12;
}

.alert-card.priority-p3 .alert-priority {
  background: #3498db;
}

.alert-title {
  margin-bottom: 8px;
  color: #2c3e50;
  font-size: 1.1rem;
}

.alert-destination {
  color: #7f8c8d;
  margin-bottom: 5px;
  font-size: 0.9rem;
}

.alert-time {
  color: #95a5a6;
  font-size: 0.8rem;
}
</style>
