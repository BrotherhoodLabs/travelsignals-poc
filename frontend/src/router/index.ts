import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Alerts from '../views/Alerts.vue'
import Counters from '../views/Counters.vue'

const routes = [
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard
  },
  {
    path: '/alerts',
    name: 'alerts',
    component: Alerts
  },
  {
    path: '/counters',
    name: 'counters',
    component: Counters
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
