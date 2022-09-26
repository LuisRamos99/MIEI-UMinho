import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    components: require("../components/Home.vue")
  },
  {
    path: '/login',
    name: 'Login',
    components: require("../components/Login.vue")
  },
  {
    path: '/registar',
    name: 'Registar',
    components: require("../components/Login.vue")
  },
  {
    path: '/receitas',
    name: 'Receitas',
    components: require("../components/ReceitasSearch.vue")
  },
  {
    path: '/publicacoes',
    name: 'Publicacoes',
    components: require("../components/Publicações.vue")
  },
  {
    path: '/receitas/:id',
    name: 'Receita',
    components: require("../components/Receita.vue")
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
