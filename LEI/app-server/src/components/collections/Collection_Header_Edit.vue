<template>
    <div id="collection_header_edit">
       <v-dialog max-width="400px" v-model="show">
            <v-card >
                <v-card-text >
                    <v-container>

                        <h1 style="text-align:center">Editar</h1>

                        <v-col cols="12">
                            <v-text-field 
                            type="text" 
                            v-model="newName" 
                            label="Nome"
                            outlined
                            ></v-text-field>
                        </v-col>
                        
                        <v-col cols="12">
                            <v-textarea 
                            outlined 
                            :counter="200"
                            :rules="[v => (v || '' ).length <= 200 || 'Descrição deverá conter 200 caracteres ou menos']"
                            type="text" 
                            height="100px"
                            :placeholder="collection.description"
                            v-model="newDescription"  
                            label="Descrição"
                            ></v-textarea>
                        </v-col>

                        <v-container>
                          <v-btn 
                          :loading="isLoading" 
                          :style="{left: '50%', transform:'translateX(-50%)'}" 
                          v-ripple="{ class: 'primary--text' }" 
                          width="150" 
                          style="height:40px" 
                          class="white--text" 
                          elevation="1" 
                          v-on:click="updateCollection()" 
                          color="#4F4E81"
                          >Guardar</v-btn>
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
    name: "collection_header_edit",
    data() {
        return{
          isLoading:false,
          newDescription:this.collection.description,
          newName:this.collection.name
        }
    },
    methods: {
      clearVariables() {  
        this.newName="",
        this.newDescription="",
        this.show=false,
        this.isLoading=false
      },
      updateCollection() {
        this.isLoading=true
        var idCollection = this.$route.params.id
        var json = {}
        json['name'] = this.newName
        json['description'] = this.newDescription
        json['public'] = false
        var token = localStorage.getItem('jwt')
        axios.put("http://localhost:1337/collections/"+idCollection,  json ,{headers: {'Authorization': `${token}`}})
          .then(() => {
            this.clearVariables()
            this.$router.go()
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    props: {
        value: Boolean,
        collection: Object
    },
    computed: {
      show: {
        get () {
          return this.value
        },
        set (value) {
           this.$emit('input', value)
        }
      }
  }
}

</script>