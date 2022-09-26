<template>
  <div id="new_memory" >
    <span class="headline">
        <h2 style="padding: 20px 0px 0px 0px; color:#4F4E81">
          Nova Memória
        </h2>
    </span>

    <v-container pa-0>
      <v-row style="margin-top:20px">
        <v-col cols="8">
            <v-text-field 
            outlined
            flat 
            type="text" 
            class="titulo" 
            style="border-style: none !important;" 
            :rules="[rules.required]" 
            v-model="title" 
            label="Título *"
            ></v-text-field>
        </v-col>

        <v-col cols="2" offset="1">
          <v-text-field 
          type="text" 
          prepend-icon="mdi-map-marker" 
          v-model="local" 
          label="Local"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="8">
            <v-textarea 
            outlined 
            auto-grow 
            type="text" 
            :rules="[rules.required]" 
            v-model="content" 
            label="Corpo *"
            ></v-textarea>
        </v-col>
        
        <v-col cols="2" offset=1>
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="date"
                  label="Data"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>

              <v-date-picker
                ref="picker"
                v-model="date"
                color="#4F4E81"
                :max="new Date().toISOString().substr(0, 10)"
                min="1900-01-01"
                @change="save"
              ></v-date-picker>
          </v-menu>
        </v-col>
      </v-row>

      <v-row justify="center" align="center">
          <v-col cols="3">
              <v-file-input
                v-model="videos"
                counter
                label="Videos"
                accept="video/*"
                show-size
                multiple
              ></v-file-input>
          </v-col>

          <v-col cols="3" offset="1">
            <v-combobox 
              multiple
              v-model="select" 
              label="Tags" 
              chips
              deletable-chips
              class="tag-input"
              :search-input.sync="search" 
              @keyup.tab="updateTags"
              @paste="updateTags">
            </v-combobox>
          </v-col>

          <v-col cols="3" offset="1" style="margin-top:30px">
              <v-autocomplete
                v-model="values"
                :items="people"
                item-text="name"
                outlined
                dense
                chips
                small-chips
                label="Escolhe as personagens"
                return-object
                multiple
              ></v-autocomplete>
          </v-col>

          <v-col sm="1">
            <NewPerson  @updateValues:value="updateValues" style="margin-left:10px"></NewPerson>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="3" >
              <v-file-input
                v-model="images"
                counter
                label="Fotos"
                accept="image/*"
                prepend-icon="mdi-camera"
                @change="Preview_image"
                show-size
                multiple
              ></v-file-input>
          </v-col>

          <v-col cols="1" v-for="url in urls" :key="url">
            <v-img class="preview" :src="url"></v-img>
          </v-col>
        </v-row>

        <v-row>
          <v-container>
            <v-btn 
            :loading="loading" 
            :style="{left: '50%', transform:'translateX(-50%)'}" 
            v-ripple="{ class: 'primary--text' }" 
            width="300" 
            style="height:40px" 
            class="white--text" 
            elevation="1" 
            v-on:click="post()" 
            color="#4F4E81"
            >Criar</v-btn>
          </v-container>
        </v-row>
    </v-container>
  </div>
</template>


<script>
import axios from 'axios'
import gql from 'graphql-tag'
import NewPerson from '@/components/forms/New_Person.vue'
import jwt from 'jsonwebtoken'

    export default {
        name: 'New_Memory',
        components: {
          NewPerson
        },
        data() {
            return {
                loading:false,
                values: [],
                images: [],
                videos: [],
                urls: [],
                date: null,
                menu: false,
                title: "",
                content: "",
                local: '',
                rules: {
                  required: value => !!value || "This camp is required.",
                },
                select: [],
                search: "",
                payload: null,
                userId:""
            }
        },
        created() {
          this.payload = jwt.decode(localStorage.getItem('jwt'))
          this.userId = this.payload.id
        },
        watch: {
          menu (val) {
            val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
          },
          'images': function(val) {
            if (val.length==0) this.urls=[]
          }
        },
        methods: {
          Preview_image() {
            this.images.forEach(f => {
              this.urls.push(URL.createObjectURL(f))
            })
          },
          updateTags() {
            this.$nextTick(() => {
              this.select.push(...this.search.split(","));
              this.$nextTick(() => {
                this.search = "";
              });
            });
          },
          updateValues(valor) {
            this.values.push(valor)
            this.people.push(valor)
          },
          save(date) {
            this.$refs.menu.save(date)
          },
          ligaMemoriaAPessoa(idMemory) {
            var token = localStorage.getItem('jwt')
            this.values.forEach(element => {
              axios.put("http://localhost:1337/people/"+element.id+"/addMemory",  {idMemory}, {headers: {'Authorization': `${token}`}})
                .then(data => {
                  console.log(data)
                })
                .catch(err => {
                  console.log(err)
                })
            });
          },
          post(){
            this.loading=true
            let formData = new FormData();

            for (let i = 0; i < this.images.length; i++) {
              const file = this.images[i];
              formData.append(`files.images`, file, file.name);
            }

            for (let i = 0; i < this.videos.length; i++) {
              const file = this.videos[i];
              formData.append(`files.videos`, file, file.name);
            }

            var json = {}
            json['utilizador'] = this.userId
            json['title'] = this.title
            json['content'] = this.content
            json['local'] = this.local
            json['date_of_memory'] = this.date
            json['tags'] = this.select
            var token = localStorage.getItem('jwt')

            formData.append("data", JSON.stringify(json));

            axios.post("http://localhost:1337/memories", formData , {headers: {'Authorization': `${token}`}})
              .then(data => {
                this.ligaMemoriaAPessoa(data.data.id)
                this.loading=false
                this.$router.push('/memories/' + data.data.id)
                this.$router.go()
              })
              .catch(err => {
                console.log(err)
              })
          }
        },
        apollo: {
          people: {
            query: gql`
              query People ($id :ID!) {
                people(where:{ memories : {utilizador: {id:$id}}}) {
                  id
                  name
                }
              }`,
            variables() {
              return {
                id: this.userId
              }
            }
          }
        }      
    }

</script>



<style>

.headline {
  text-align: center;
}
.titulo {
  font-size: 150%;
}

.titulo.v-text-field>.v-input__control>.v-input__slot:before {
  border-style: none;
}
.titulo.v-text-field>.v-input__control>.v-input__slot:after {
  border-style: none;
}

.titulo.v-input v-label {
  font-size: 20em;
}

.preview {
  height: 80px;
  width: 80px;
}

.tag-input span.chip {
  background-color: #1976d2;
  color: #fff;
  font-size: 1em;
}

.tag-input span.v-chip {
  background-color: #1976d2;
  font-size:1em;
  padding-left:7px;
}

</style>