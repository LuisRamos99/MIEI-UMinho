<template>
    <div id="home" class="home">
     <v-container> 
      <v-row no-gutters>
        <v-col cols="12" sm="6" md="8" class="publicacoes">
          <h1 @click="handleClick('/receitas')">Receitas</h1>
          <v-container class="pubs">
            <v-row no-gutters>
              <v-col v-for="n in list" :key="n.id" cols="12" sm="6">
                <v-card class="pa-6 pub" outlined  @click="handleClick('/receitas/'+n.rec_id)">
                  <v-row>
                    <v-col cols="12" sm="6" style="display:inline-flex">
                        <v-img v-if="n.imgPath" :src="'http://localhost:7700/uploads/'+n.imgPath" max-width=100% height="150"></v-img>
                        <v-img v-else src="../../public/default.png"></v-img>
                    </v-col>
                    <v-col cols="12" sm="6">
                        <span style="font-size: 20px; color: #53a6bf;"> {{ n.titulo }} <br/> </span>  
                        <span > <b>Autor: </b>{{ n.autor }} <br/></span>
                        <span > <b>Dificuldade: </b>
                          <span v-if="n.dificuldade=='Fácil'" style="color:green"> {{n.dificuldade}} </span>
                          <span  v-else-if="n.dificuldade=='Média'" style="color:orange"> {{n.dificuldade}} </span>
                          <span v-else style="color:red"> {{n.dificuldade}} </span>
                        </span>
                    </v-col>
                  </v-row>
                  <v-row>
                    <span> <i> Adicionada {{ n.data | moment("from") }} </i> </span>
                  </v-row>
                </v-card> 
              </v-col>
            </v-row>
          </v-container>         
        </v-col>


        <v-col cols="6" md="4" class="recursos">
            <h1 @click="handleClick('/publicacoes')">Novas Publicações</h1>
            <v-container class="recs">
            <v-row no-gutters>
              <v-col v-for="n in pubs" :key="n.id" cols="12" sm="12">
                <v-card class="pa-6 rec" @click="handleClick('/publicacoes')">
                  <v-row>
                    <v-col cols="12" sm="4" style="display:inline-flex">
                        <v-img v-if="n.imgPath" :src="'http://localhost:7700/uploads/'+n.imgPath" max-width=100% height="150"></v-img>
                        <v-img v-else src="../../public/default.png"></v-img>
                    </v-col>
                    <v-col cols="12" sm="8">
                        <span style="font-size: 20px; text-decoration: underline"> {{ n.titulo }} <br/> </span>
                        <span> <b>Receita: </b>{{ n.titulo_receita }} <br/> </span>
                        <span> {{ n.creator }} {{ n.data | moment("from") }} </span>
                    </v-col>
                  </v-row>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
    
      </v-row>

      <v-row v-if="token">
          <v-col cols="8" align="center">
              <AddReceita/>
          </v-col>
      </v-row>
    </v-container>
    
    </div>

</template>



<script>
import axios from 'axios'
import AddReceita from '@/components/AddReceita.vue'

export default {
    name: 'home',
    data() {
        return { 
            list: [],
            pubs: [], 
            token: localStorage.getItem('jwt')
        }
    },
    components: {
      AddReceita
    },
    created() {                 
        axios.get("http://localhost:7700/publicacao/recentes")
              .then(data => {
                  this.pubs = data.data.publis
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
    methods: {
        handleClick(value) {
          this.$router.push(value)      
        },
        sorted(lista) {
            return lista.sort((a,b) => (a.data < b.data) ? 1 : ((b.data < a.data) ? -1 : 0))
        },
    }
}

</script>




<style>

.home {
    text-align: center;
    height: 100vh;
}

.home h1{
    font-size: 30px;
    text-decoration: underline;
    text-align: left;
    margin-top: 10px;
    text-underline-offset: 7px;
    text-decoration-thickness: 6px;
    text-decoration-color: #e88b00;
}

.publicacoes {
    padding-right: 30px !important;
}

.pubs {
    background-color: #a9a9a94a;
    border-radius: 5px;
    margin-top: 30px;
}

.pub {
    text-align: left;
    border-radius: 5px;
    margin: 10px;
}

.recursos {
    max-height: 840px;
    border-left: 2px solid #969090;
    background-color: white;
    padding-left: 30px !important;
    display: flex;
    flex-direction: column;
    align-items: center;
    right: 0;
    position: fixed;
}

.recursos h1 {
    text-align: center !important;
    text-decoration: none !important;
    background-color: #e88b00bd;
    width: fit-content;
    padding: 0px 5px;
}

.recs {
    margin-top: 20px;
}

.rec {
    text-align: left;
    border-radius: 5px;
    margin: 10px;
    margin-bottom: 30px;
}

.img {
    margin: auto;
    text-align: right;
    max-height: 80px;
    max-width: 80px;
}

</style>