<template> 
    <div id="person">
        <v-dialog max-width="400px" v-model="dialog">
            <template v-slot:activator="{ on }">
                <v-btn icon large color="#4F4E81" rounded elevation="4" v-on="on"> 
                    <v-icon large>mdi-plus</v-icon>
                </v-btn>
            </template>
            <v-card >
                <v-card-text >
                    <v-container>
                        <v-col cols="12">
                            <v-text-field 
                            prepend-icon="mdi-account"
                            type="text" 
                            v-model="name" 
                            label="Nome"
                            ></v-text-field>
                        </v-col>
                        
                        <v-col cols="12">
                            <v-text-field 
                            type="text" 
                            prepend-icon="mdi-map-marker" 
                            v-model="local" 
                            label="Local de nascimento"
                            ></v-text-field>
                        </v-col>

                        <v-col cols="12">
                            <v-menu
                              ref="menu"
                              v-model="menu"
                              :close-on-content-click="false"
                              transition="scale-transition"
                              offset-y
                              min-width="auto">
                                <template v-slot:activator="{ on, attrs }">
                                    <v-text-field
                                      v-model="dateOfBirth"
                                      label="Data de nascimento"
                                      prepend-icon="mdi-calendar"
                                      readonly
                                      v-bind="attrs"
                                      v-on="on"
                                    ></v-text-field>
                                </template>
                                <v-date-picker
                                  ref="picker"
                                  v-model="dateOfBirth"
                                  color="#4F4E81"
                                  :max="new Date().toISOString().substr(0, 10)"
                                  min="1900-01-01"
                                  @change="save"
                                ></v-date-picker>
                            </v-menu>
                        </v-col>

                        <v-container>
                          <v-btn 
                          :style="{left: '50%', transform:'translateX(-50%)'}" 
                          v-ripple="{ class: 'primary--text' }" 
                          width="150" 
                          style="height:40px;margin-top:40px" 
                          class="white--text" 
                          elevation="1" 
                          v-on:click="criarUser()" 
                          color="#4F4E81"
                          >Criar</v-btn>
                        </v-container>
                    </v-container>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>


<script>
import axios from 'axios'


export default {
    name: 'NewPerson',
    data() {
        return {
            name: "",
            local: "",
            dialog: false,
            menu: false,
            dateOfBirth: null,
            dateOfDeath: null,
        }
    },
    watch: {
      menu (val) {
        val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
      },
    },
    methods: {
      save (date) {
        this.$refs.menu.save(date)
      },
      clearVariables() {
        this.name=""
        this.local=""
        this.dateOfBirth=null
        this.dateOfDeath=null
        this.dialog=false
      },
      criarUser() {
        var json = {}
        json['name'] = this.name
        json['local_of_birth'] = this.local
        json['date_of_birth'] = this.dateOfBirth
        json['date_of_death'] = this.dateOfDeath
        var token = localStorage.getItem('jwt')
        axios.post("http://localhost:1337/people",  json,{headers: {'Authorization': `${token}`}})
          .then(data => {
            var newPerson = {'name':data.data.name , 'id': data.data.id }
            this.$emit('updateValues:value', newPerson)
            this.clearVariables()
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
}    

</script>
