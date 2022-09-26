<template> 
    <div id="confirmation">
      <v-dialog
        v-model="dialog"
        width="500"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-icon 
            large 
            color="red" 
            v-bind="attrs"
            v-on="on">
              mdi-close
          </v-icon>
        </template>
  
        <v-card>
          <v-card-title class="headline grey lighten-2">
            Confirmação
          </v-card-title>
  
          <v-card-text>
            Tem a certeza que deseja remover?
          </v-card-text>
  
          <v-divider></v-divider>
  
          <v-card-actions>
              <v-spacer></v-spacer>
                  <v-btn
                  color="primary"
                  text
                  @click="delet"
                >
                  Sim
                </v-btn>

                <v-btn
                  color="red"
                  text
                  @click="dialog=false"
                >
                  Cancelar
                </v-btn>
  
            
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
</template>


<script>
import axios from 'axios'

export default {
    name: 'confirmation',
    data() {
        return {
            dialog:false,
        }
    },
    methods: {
        delet() {
            var json = {}
            var index
            if (this.tag=="image") {
                index = this.memory.images.map(function(item) { return item.url; }).indexOf(this.objecto.url);
                this.memory.images.splice(index, 1);
            }
            else {
                index = this.memory.videos.map(function(item) { return item.url; }).indexOf(this.objecto.url);
                this.memory.videos.splice(index, 1);
            }
            json['videos'] = this.memory.videos
            json['images'] = this.memory.images
            var token = localStorage.getItem('jwt')
            axios.put("http://localhost:1337/memories/"+this.$route.params.id, json , {headers: {'Authorization': `${token}`}})
              .then(() => {
              })
              .catch(err => {
                console.log(err)
              })
            this.dialog=false
        }
    },
    props: {
        memory: Object,
        objecto: Object,
        tag: String
    }
}

</script>