<template>
    <div id="addRecurso">
        <v-dialog max-width="900px" v-model="show">
            <template v-slot:activator="{ on }">
                 <v-btn v-if="button" v-on="on" @click="search()">
                    <v-icon> mdi-plus </v-icon>
                </v-btn>
                <v-btn v-else v-on="on"> Nova receita </v-btn>
            </template>
            <v-card >
                <v-card-text >                   
                    <v-container>
                        
                        <v-col id="titulo">
                            <h1 >Adicionar Receita</h1>
                        </v-col>

                        <p v-if="alerta" style="margin-bottom:-5px" class="alert"> Obrigatório preencher todos os campos!</p>    
                        
                        <v-col class="pa-2">
                            <v-text-field 
                            hide-details
                            dense
                            type="text" 
                            v-model="titulo" 
                            label="Título"
                            outlined
                            ></v-text-field>
                        </v-col>


                        <v-col class="pa-2" style="margin-top:-5px">
                            <v-autocomplete
                              v-model="tipoCozinha"
                              :items="tiposCozinha"
                              outlined
                              dense
                              label="Tipo de cozinha"
                            ></v-autocomplete>
                        </v-col>

                        <v-col class="pa-2" style="margin-top:-30px">
                            <v-autocomplete
                              v-model="tipoPrato"
                              :items="tiposPrato"
                              outlined
                              dense
                              label="Tipo de prato"
                            ></v-autocomplete>
                        </v-col>

                        <v-col class="pa-2" style="margin-top:-30px">
                            <v-select
                              v-model="dificuldade"
                              :items="dificuldades"
                              outlined
                              dense
                              label="Dificuldade do prato"
                            ></v-select>
                        </v-col>

                        <v-col class="pa-2" style="margin-top:-30px">
                          <v-combobox 
                            multiple
                            v-model="select" 
                            label="Ingredientes" 
                            chips
                            outlined
                            deletable-chips
                            class="tag-input"
                            :search-input.sync="search" 
                            @keyup.tab="updateTags"
                            @paste="updateTags">
                          </v-combobox>
                        </v-col>
                      
                        <v-col class="pa-2" style="margin-top:-35px">
                            <v-textarea 
                            hide-details
                            dense
                            type="text" 
                            v-model="descricao" 
                            label="Descrição"
                            outlined
                            ></v-textarea>
                        </v-col>
                          
                        <v-col class="pa-2">
                            <v-file-input
                              label="Adicione uma imagem"
                              v-model="img"
                              name="avatar"
                              single
                              accept="image/*"
                              prepend-icon="mdi-camera"
                            ></v-file-input>
                        </v-col>
                       
                        <v-col align="right">
                          <v-btn :loading="loading" v-ripple="{ class: 'primary--text' }" width="150" style="height:40px" class="white--text" elevation="1" v-on:click="submeter()" color="#00ace6">Submeter</v-btn>
                          <v-btn v-ripple="{ class: 'primary--text' }" width="150" style="margin-left:10px;height:40px" class="white--text" elevation="1" v-on:click="cancelar()" color="#527a7a">Cancelar</v-btn>
                        </v-col>

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
    name: "addReceita",
    data() {
        return{
          show:false,
          img:null,
          search: "",
          select: [],
          tiposCozinha: ["Austríaca","Belga","Brasileira","Madeirense","Tailandesa","Macaense","Ucraniana","Africana","Mexicana","Espanhola","Suiça","Holandesa","Mediterrânica","Americana","Asiática","Inglesa","América do Sul","Francesa","Grega","Indiana","Árabe","Portuguesa","Marroquina","Chinesa","Italiana"],
          tiposPrato: ["Snack","Marisco","Vegetariano","Entradas e Petiscos","Saladas","Sopas","Outros acompanhamentos","Massa","Arroz","Bebida","Vegetais","Carne","Peixe","Doces e Sobremesa","Pizza"],
          dificuldades: ["Fácil","Média","Difícil"],
          dificuldade:'',
          titulo:'',
          descricao:'',
          loading:false,
          tipoPrato:'',
          tipoCozinha:'',
          alerta: false,
        }
    },
    created(){
        this.tiposCozinha = this.sorted(this.tiposCozinha)
        this.tiposPrato = this.sorted(this.tiposPrato)
    },
    props: {
        button: Boolean
    },
    methods: {
        cancelar() {
            this.show=false;
            this.titulo='',
            this.descricao='',
            this.tipoCozinha='',
            this.tipoPrato='',
            this.select = [],
            this.search = '',
            this.dificuldade = '',
            this.img = null,
            this.loading=false
        },
        sorted(lista) {
            return lista.sort((a,b) => (a < b) ? -1 : ((b < a) ? 1 : 0))
        },
        save(date) {
            this.$refs.menu.save(date)
        },
        updateTags() {
          this.$nextTick(() => {
            this.select.push(...this.search.split(","));
            this.$nextTick(() => {
              this.search = "";
            });
          });
        },
        handleFileUpload(){
        this.img = this.$refs.file.files[0];
        },
        verificaCampos() {
            if (this.tiposCozinha=='') return false
            if (this.tiposPrato=='') return false
            if (this.dificuldade=='') return false
            if (this.titulo=='') return false
            if (this.descricao=='') return false
            if (this.img==null) return false
            if (this.select==[]) return false
            return true
        },
        submeter() {
            if (!this.verificaCampos()) this.alerta=true
            else {
            this.alerta=false
            this.loading=true    
                 
            let formData = new FormData();
            var token = localStorage.getItem('jwt')
            var idUser = jwt.decode(token).email
            formData.append('tipoCozinha', this.tipoCozinha);
            formData.append('tipoPrato', this.tipoPrato);
            formData.append('dificuldade', this.dificuldade);
            formData.append('titulo', this.titulo);
            formData.append('descricao', this.descricao.replaceAll('\n',' '));
            formData.append('ingredientes', this.select);
            formData.append('idUser', idUser);

            var x = (new Date()).getTimezoneOffset() * 60000; 
            var localISOTime = (new Date(Date.now() - x)).toISOString().slice(0,-1);  
            formData.append('data', localISOTime.slice(0, 19).replace('T', ' '));
    
            formData.append('file', this.img, this.img.name);
            
            axios({
                method: "post",
                url: "http://localhost:7700/receita/",
                data: formData,
                headers: { "Authorization" : token, 'Content-Type': 'multipart/form-data'},
            })
            .then(data => {
                console.log(data.data)
                this.cancelar();
                this.$router.push('/receitas/' + data.data.idRec)
            })
            .catch(err => {
                console.log(err)
                alert('Não foi possível adicionar a nova receita')
                this.cancelar();
            })
            }
        }
    }
}

</script>

<style> 

</style>