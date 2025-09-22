<template>
  <header class="header">
    <div class="header-content">
      <h1 class="logo">TravelSignals</h1>
      <nav class="nav">
        <router-link to="/" class="nav-link">Dashboard</router-link>
        <router-link to="/alerts" class="nav-link">Alertes</router-link>
        <router-link to="/counters" class="nav-link">Compteurs</router-link>
      </nav>
    </div>
  </header>
</template>

<script setup lang="ts">
// Pas de logique nécessaire pour l'instant
</script>

<style scoped>
.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1rem 0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 1.8rem;
  font-weight: bold;
  margin: 0;
}

.nav {
  display: flex;
  gap: 2rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-link:hover,
.nav-link.router-link-active {
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
