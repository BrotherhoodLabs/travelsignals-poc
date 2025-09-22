<template>
  <aside class="sidebar">
    <div class="sidebar-content">
      <h3>Filtres</h3>
      
      <div class="filter-section">
        <h4>Type d'alerte</h4>
        <label v-for="type in alertTypes" :key="type" class="filter-item">
          <input 
            type="checkbox" 
            :value="type" 
            v-model="selectedTypes"
            @change="updateFilters"
          >
          {{ type }}
        </label>
      </div>
      
      <div class="filter-section">
        <h4>Priorité</h4>
        <label v-for="priority in priorities" :key="priority" class="filter-item">
          <input 
            type="checkbox" 
            :value="priority" 
            v-model="selectedPriorities"
            @change="updateFilters"
          >
          {{ priority }}
        </label>
      </div>
      
      <div class="filter-section">
        <h4>Destination</h4>
        <input 
          type="text" 
          v-model="destinationFilter"
          @input="updateFilters"
          placeholder="Filtrer par destination..."
          class="filter-input"
        >
      </div>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

const emit = defineEmits<{
  filtersChanged: [filters: {
    types: string[]
    priorities: string[]
    destination: string
  }]
}>()

const alertTypes = ['PRICE', 'WEATHER', 'FLIGHT', 'VISA']
const priorities = ['P1', 'P2', 'P3']

const selectedTypes = ref<string[]>([])
const selectedPriorities = ref<string[]>([])
const destinationFilter = ref('')

const updateFilters = () => {
  emit('filtersChanged', {
    types: selectedTypes.value,
    priorities: selectedPriorities.value,
    destination: destinationFilter.value
  })
}

// Émettre les filtres initiaux
watch([selectedTypes, selectedPriorities, destinationFilter], updateFilters, { immediate: true })
</script>

<style scoped>
.sidebar {
  width: 250px;
  background-color: #f8f9fa;
  border-right: 1px solid #e9ecef;
  padding: 20px;
  height: calc(100vh - 80px);
  overflow-y: auto;
}

.sidebar-content h3 {
  margin-bottom: 20px;
  color: #495057;
  font-size: 1.2rem;
}

.filter-section {
  margin-bottom: 25px;
}

.filter-section h4 {
  margin-bottom: 10px;
  color: #6c757d;
  font-size: 0.9rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.filter-item {
  display: block;
  margin-bottom: 8px;
  cursor: pointer;
  font-size: 0.9rem;
}

.filter-item input {
  margin-right: 8px;
}

.filter-input {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 0.9rem;
}

.filter-input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
}
</style>
