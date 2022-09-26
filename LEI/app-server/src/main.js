import Vue from "vue";
import VueApollo from "vue-apollo";
import VueRouter from "vue-router";

import apolloClient from "./vue-apollo";

import App from "./App.vue";
import vuetify from './plugins/vuetify';

Vue.use(VueApollo);
Vue.use(VueRouter);
Vue.config.productionTip = false;
 
var VueScrollTo = require('vue-scrollto');
 
Vue.use(VueScrollTo)

const apolloProvider = new VueApollo({
  defaultClient: apolloClient
});

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      components: require("./components/home/Home.vue")
    },
    { 
      path: '/home', 
      redirect: '/' 
    },
    {
      path: "/login",
      components: require("./components/Login.vue")
    },
    {
      path: "/about",
      name: "about",
      components: require("./components/About.vue")
    },
    {
      path: "/users/:id",
      components: require("./components/profile/Profile.vue")
    },
    {
      path: "/person/:id",
      components: require("./components/personagem/Person.vue")
    },
    {
      path: "/collections",
      components: require("./components/collections/Collections.vue")
    },
    {
      path: "/collections/:id",
      components: require("./components/collections/Collection.vue")
    },
    {
      path: "/memories",
      components: require("./components/memories/Memories.vue")
    },
    {
      path: "/memories/:id",
      components: require("./components/memories/Memory.vue")
    },
    {
      path: "/new_memory",
      components: require("./components/forms/New_Memory.vue")
    },
    { path: "*", 
      components: require("./components/NotFound.vue")
    }
  ]
});

new Vue({
  apolloProvider,
  router,
  vuetify,
  render: h => h(App)
}).$mount("#app");