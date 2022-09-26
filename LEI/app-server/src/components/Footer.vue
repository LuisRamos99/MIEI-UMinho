<template>
    <v-footer color="white" padless>
      <v-row no-gutters>     
        <v-col cols="4" class="meterCentro">
          <div class="centra">
            <v-btn 
              v-for="icon in icons"
              :key="icon.name"
              @click="goToLink(icon.link)"
              color="#4F4E81"
              rounded
              class="my-2"
              icon
            >
              <v-icon size="24px"> {{ icon.name }} </v-icon>
            </v-btn>
          </div>
        </v-col>

        <v-col cols="4" class="meterCentro">
          <div class="centra">
            <v-btn
              v-for="button in buttons"
              :key="button.name"
              @click="goToLink(button.link)"
              color="#4F4E81"
              text
              rounded
              class="my-2"
            >
              {{ button.name }}
            </v-btn>
          </div>
        </v-col>

        <v-col cols="4" v-if="this.token" class="meterCentro" >
          <div class="centra">
            <b>Memórias:</b><span style="color:#4F4E81; margin-left:2px"> {{this.memories}} </span>
            <b style="margin-left:20px">Utilizadores:</b><span style="color:#4F4E81; margin-left:2px"> {{this.utilizadors}} </span>
          </div>
        </v-col>
    
      </v-row>

    </v-footer>
</template>


<script>
import axios from 'axios'

export default {
   data: () => ({
    memories: 0,
    utilizadors: 0,
    buttons: [
      {name: 'Sobre', link: '/about'},
      {name: 'Equipa',  link: '/about'},
      {name: 'Contactos',  link: '/about'}
    ],
    icons: [
      {name: 'mdi-facebook', link: '/'},
      {name: 'mdi-twitter', link: '/'},
      {name: 'mdi-linkedin', link: '/'},
      {name: 'mdi-instagram', link: '/'},
    ],
    token:''
  }),
  created(){
    this.token = localStorage.getItem('jwt')
  },
  methods: {
    goToLink(link) {
      this.$router.push(link)
    }
  },
  mounted () {
      var token = localStorage.getItem('jwt')
      axios.get('http://localhost:1337/utilizadors/count',{headers: {'Authorization': `${token}`}})
         .then(response => (this.utilizadors = response.data))
         .catch(error => console.log(error))
      axios.get('http://localhost:1337/memories/count',{headers: {'Authorization': `${token}`}})
         .then(response => (this.memories = response.data))
         .catch(error => console.log(error))
  }
}
</script>


<style scoped>

b {
    color:#4F4E81
}

.meterCentro {
  text-align: center;
}

.centra {
  position: relative;
  top: 50%;
  transform: translateY(-50%);
}

</style>