import Vue from 'vue'
import App from './App.vue'

import vuetify from '@/plugins/vuetify'
import router from './router'

Vue.config.productionTip = false

const moment = require('moment')
require('moment/locale/pt')

Vue.use(require('vue-moment'), {
  moment
})

new Vue({
  vuetify,
  router,
  render: h => h(App),
}).$mount('#app')
