<template>
    <div id="receitasSearch" class="receitasSearch">
        <v-container> 
            <v-row no-gutters>
                <v-col class="d-flex pa-2" cols="12" sm="2">
                    <v-text-field
                      v-model="titulo"
                      label="Titulo"
                      dense
                      outlined
                      hide-details
                    ></v-text-field>
                </v-col>

                <v-col class="d-flex pa-2" cols="12" sm="2">
                    <v-text-field
                      v-model="ingrediente"
                      label="Ingrediente"
                      dense
                      outlined
                      hide-details
                    ></v-text-field>
                </v-col>

                <v-col class="d-flex pa-2" cols="12" sm="2">
                    <v-autocomplete
                      :items="tiposCozinha"
                        v-model="tipoCozinha"
                        label="Tipo de cozinha"
                        dense
                        outlined
                        hide-details
                        clearable
                    ></v-autocomplete>
                </v-col>

                <v-col class="d-flex pa-2" cols="12" sm="2">
                    <v-autocomplete
                      :items="tiposPrato"
                      v-model="tipoPrato"
                      label="Tipo de prato"
                      dense
                      clearable
                      hide-details
                      outlined
                    ></v-autocomplete>
                </v-col>

                <v-col class="d-flex pa-2" cols="12" sm="2">
                    <v-autocomplete
                      :items="chefs"
                      v-model="chef"
                      label="Chef"
                      dense
                      outlined
                      hide-details
                      clearable
                     ></v-autocomplete>  
                </v-col>

                <v-col class="d-flex pa-2" cols="12" sm="1">
                    <v-btn @click="search()">
                        <v-icon> mdi-magnify </v-icon>
                    </v-btn>
                </v-col>
                <v-col class="d-flex pa-2" cols="12" sm="1" style="margin-left:-15px">
                    <AddReceita :button="true"/>
                </v-col>
            
            </v-row>


            <v-row>
                <v-col v-if="this.list.length==0" align="center">
                    <p style="font-size:20px"><b>Sem resultados!</b></p>
                </v-col>
                <v-col cols="12"  class="publicacoes">
                      <v-row no-gutters>
                        <v-col v-for="n in list" :key="n.id" cols="12" sm="6">
                          <v-card class="pa-6 pub" outlined  @click="handleClick('/receitas/'+n.rec_id)">
                            <v-row>
                              <v-col cols="12" sm="5" style="display:inline-flex">
                                  <v-img v-if="n.imgPath" :src="'http://localhost:7700/uploads/'+n.imgPath" width=100% height="200"></v-img>
                                  <v-img v-else src="../../public/default.png"></v-img>
                              </v-col>
                              <v-row class="pa-2" align="end">
                                <v-col cols="12" sm="10">
                                    <span style="font-size: 20px; color: #53a6bf;"> {{ n.titulo }} <br/> </span>  
                                    <span > <b>Autor: </b>{{ n.autor }} <br/></span>
                                    <span > <b>Dificuldade: </b>
                                        <span v-if="n.dificuldade=='Fácil'" style="color:green"> {{n.dificuldade}} </span>
                                        <span  v-else-if="n.dificuldade=='Média'" style="color:orange"> {{n.dificuldade}} </span>
                                        <span v-else style="color:red"> {{n.dificuldade}} </span><br/><br/>
                                    </span>
                                    <span> <i> Adicionada {{ n.data | moment("from") }} </i> </span>
                                </v-col>
                                <v-col align="right" sm="2">
                                    <v-row>
                                        <v-icon color="red">mdi-cards-heart</v-icon>
                                        <span style="margin-left:5px"> {{ n.gostos.length }} </span>
                                    </v-row>
                                </v-col>
                              </v-row>
                            </v-row>
                           </v-card> 
                        </v-col>
                      </v-row>      
                </v-col>
            </v-row>

            <v-row v-if="!all">
                <v-col align="center">
                    <v-btn @click="searchAll()"> Ver Todos 
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>   
    </div>
</template>




<script>
import axios from 'axios'
import AddReceita from '@/components/AddReceita.vue'

export default {
    name: 'receitasSearch',
    data() {
        return { 
            titulo: null,
            tipoCozinha: null,
            tipoPrato: null,
            chef: null,
            ingrediente: null,
            list: [],
            recs: [],
            tiposCozinha: [],
            tiposPrato: [],
            chefs: [],
            all: false
        }
    },
    created() {
        axios.get("http://localhost:7700/receita/tiposCozinha")
                .then(data => {
                    this.tiposCozinha = data.data.tipos
                })
                .catch(err => {
                    console.log(err)
                })
        axios.get("http://localhost:7700/receita/tiposPrato")
                .then(data => {
                    this.tiposPrato = data.data.tipos
                })
                .catch(err => {
                    console.log(err)
                })
        axios.get("http://localhost:7700/receita/autores")
                .then(data => {
                    this.chefs = data.data.tipos
                })
                .catch(err => {
                    console.log(err)
                })                 
        axios.get("http://localhost:7700/receita/recentes")
            .then(data => {
                this.list = data.data.receitas
            })
            .catch(err => {
                console.log(err)
            })     
        
    },
    components: {
        AddReceita
    },
    methods: {
        handleClick(value) {
          this.$router.push(value)      
        },
        sorted(lista) {
            if (lista) return lista.sort((a,b) => (a.gostos.length < b.gostos.length) ? 1 : ((b.gostos.length < a.gostos.length) ? -1 : 0))
            else return lista
        },
        search() {
            var query = ''
            if (this.tipoCozinha) query+= (query==''?'':'&') + "tipoCozinha=" + this.tipoCozinha
            if (this.tipoPrato) query+= (query==''?'':'&') + "tipoPrato=" + this.tipoPrato
            if (this.chef) query+= (query==''?'':'&') + "autor=" + this.chef.replaceAll(' ','_')
            if (this.titulo) query+= (query==''?'':'&') + "titulo=" + this.titulo
            if (this.ingrediente) query+= (query==''?'':'&') + "ingrediente=" + this.ingrediente
            axios.get("http://localhost:7700/receita?"+query)
                .then(data => {
                    this.list = this.sorted(data.data.receitas)
                    this.all = true
                })
                .catch(err => {
                    console.log(err)
                })
        },
        searchAll(){
            axios.get("http://localhost:7700/receita/")
                .then(data => {
                    this.list = this.sorted(data.data.receitas)
                    this.all = true
                })
                .catch(err => {
                    console.log(err)
                })
        }
    }
}

</script>




<style>


</style>