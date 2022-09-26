<template> 
    <div id="new_collection">
        <v-dialog max-width="400px" v-model="dialog">
            <template v-slot:activator="{ on }">
                <v-btn v-if="button" icon color="#4F4E81" large rounded elevation="4" v-on="on"> 
                    <v-icon large>mdi-plus</v-icon>
                </v-btn>
                <a v-else style="color:#4F4E81" v-on="on"> Nova Coleção </a>
            </template>

            <v-card >
                <v-card-text >
                    <v-container>
                        <h1 style="text-align:center">Nova Coleção</h1>
                      
                        <v-col cols="12">
                            <v-text-field 
                            type="text" 
                            v-model="name" 
                            :rules="[rules.required]"
                            label="Nome"
                            ></v-text-field>
                        </v-col>
                        
                        <v-col cols="12">
                            <v-textarea 
                            outlined 
                            :counter="200"
                            :rules="[rules.length]"
                            type="text" 
                            v-model="descricao"  
                            label="Descrição"
                            ></v-textarea>
                        </v-col>

                        <v-container>
                          <v-btn 
                          :loading="loading" 
                          :style="{left: '50%', transform:'translateX(-50%)'}" 
                          v-ripple="{ class: 'primary--text' }" 
                          width="150" 
                          style="height:40px" 
                          class="white--text" 
                          elevation="1" 
                          v-on:click="newCollection()" 
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
import jwt from 'jsonwebtoken'

export default {
    name: 'new_collection',
    data() {
        return {
            loading:false,
            name: "",
            descricao: "",
            dialog:false,
            rules: {
                required: value => !!value || "This camp is required.",
                length: v => (v || '' ).length <= 200 || 'Descrição deverá conter 200 caracteres ou menos'
            },
        }
    },
    props: {
        button: String
    },
    methods: {
        clearVariables() {  
          this.name="",
          this.descricao="",
          this.dialog=false
        },
        newCollection() {
          this.loading=true
          var json = {}
          json['name'] = this.name
          json['public'] = false
          json['utilizador'] = jwt.decode(localStorage.getItem('jwt')).id
          json['description'] = this.descricao
          var token = localStorage.getItem('jwt')
          axios.post("http://localhost:1337/collections",  json ,{headers: {'Authorization': `${token}`}})
            .then(data => {
              this.clearVariables()
              this.loading=false
              this.$router.push('/collections/' + data.data.id)
              this.$router.go()
            })
            .catch(err => {
              console.log(err)
            })
        }
    }
}    

</script>

