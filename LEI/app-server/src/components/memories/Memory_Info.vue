<template>
    <v-container v-if="this.memory">
      <v-row style="padding: 10px 0 0 0">
        <v-col>
          <v-card
            class="mx-auto"
            max-width="900"
            min-width="100%"
            outlined
          >
            <v-app-bar flat color="white">

              <v-toolbar-title v-if="!editing">
                {{memory.title}}
              </v-toolbar-title>

              <v-container v-else style="padding:30px 0 0 0">
                <v-text-field
                type="text"
                label="Título"
                v-model="title"
                clearable
                >
                </v-text-field>
              </v-container>

              <v-spacer></v-spacer>

              <v-btn @click="editing=!editing" icon v-if="memory.utilizador.id==user">
                <v-icon v-if="editing" class="fa fa-times fa-lg" aria-hidden="true" color="red"></v-icon>
                <v-icon v-else class="fa fa-pencil fa-lg" aria-hidden="true" ></v-icon>
              </v-btn>

            </v-app-bar>

            <v-card-subtitle v-if="!editing">
              <span v-if="memory.local"><v-icon small>mdi-map-marker</v-icon>{{memory.local}}</span>   <span v-if="memory.local && memory.date_of_memory">•</span>   <span v-if="memory.date_of_memory">{{memory.date_of_memory.split("T")[0]}}</span>
            </v-card-subtitle>

            <v-container v-else>
              <v-row>
                <v-col>
                  <v-text-field
                    type="text"
                    v-model="local"
                    clearable
                    label="Local"
                    append-icon="mdi-map-marker"
                    >
                  </v-text-field>
                </v-col>

                <v-col>
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
                        append-icon="mdi-calendar"
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
            </v-container>

            <v-card-text v-if="memory.people.length > 1">
              <b>Personagens</b> :
              <span v-for="(person,index) in memory.people" :key="person.name">
                <router-link 
                class="person-link" 
                style="color:#424292;" 
                :to="'/person/' + person.id"> 
                  {{person.name}}
                </router-link>
                <span class="person-link" v-if="index < memory.people.length-1">, </span>
              </span>
            </v-card-text>

            <v-card-text v-else-if="memory.people.length == 1">
              <b>Personagem</b> :
              <router-link 
              class="person-link" 
              style="color:#424292;" 
              :to="'/person/' + person.id"  
              v-for="person in memory.people" 
              :key="person.id"> 
                {{person.name}}
              </router-link>
            </v-card-text>

            <v-card-text v-if="memory.tags">
              <span v-if="!editing">
                <b>Tags</b> : 
                <span class="special" v-for="tag in memory.tags" :key="tag">
                  <a style="color:#424292" class="tags" :href="'/memories?tag=' + tag"> 
                    #{{tag}} 
                  </a>
                </span>
              </span>
              <v-col v-else class="pa-0">
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
            </v-card-text>

            <v-card-text v-if="!editing">
              {{memory.content}}
            </v-card-text>

            <v-container v-else>
              <v-textarea
              v-model="content"
              label="Conteúdo"
              >
              </v-textarea>
            </v-container>

            <v-card-actions v-if="editing">
              <v-btn plain @click="saveInfo()">
                Guardar
              </v-btn>
            </v-card-actions>

            <v-card-actions>
              <v-row>

                <v-col cols="6">
                  <v-autocomplete
                    v-model="values"
                    :items="collections"
                    item-text="name"
                    outlined
                    dense
                    chips
                    small-chips
                    label="Adiciona às tuas coleções"
                    return-object
                    multiple
                    style="padding:0 0 0 10px"
                  ></v-autocomplete>
                </v-col>

                <v-col cols="1">
                  <v-btn outlined color="#4F4E81" depressed @click="adicionar()"> 
                    Adicionar 
                  </v-btn>
                </v-col>

              </v-row>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col>
          <v-row>
            <v-col>
              <v-card
                class="mx-auto"
                max-width="344"
                outlined
              >
                <v-list-item three-line>
                  <v-list-item-content>
                    <v-list-item-title class="headline mb-1 text-left">
                      Autor
                    </v-list-item-title>

                    <router-link :to="'/users/' + memory.utilizador.id" style="color:#8785C4" class="subtitle-1 mb-4 profile-link">
                      <v-btn left plain color="indigo" class="username">{{memory.utilizador.username}}</v-btn>
                    </router-link>
                  </v-list-item-content>

                  <v-avatar size="80">
                    <v-img v-if="memory.utilizador.profile_picture" @click.stop="dialog = true" :src="'http://localhost:1337' + memory.utilizador.profile_picture.url"></v-img>
                    <v-img v-else src="../../../public/user.png"></v-img>
                  </v-avatar>

                </v-list-item>
              </v-card>
            </v-col>
          </v-row>

          <v-row justify="center">
            <v-col cols=3 >
              <v-btn href="#" v-scroll-to="'#galeria'" plain color="indigo">Galeria</v-btn>
            </v-col>
            <v-col cols=3>
              <v-btn href="#" v-scroll-to="'#videos'" plain color="indigo">Videos</v-btn>
            </v-col>
          </v-row>
          
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-card outlined id="galeria">
            <v-card-title >Galeria</v-card-title>
            <v-card-subtitle>{{memory.images.length}} {{memory.images.length == 1 ? 'Foto' : 'Fotos'}}</v-card-subtitle>
            <v-carousel style="width:100%;height:auto;"  show-arrows-on-hover key="" hide-delimiter-background>
              <v-carousel-item v-for="image in memory.images" :key="image.url">
                  <v-row>
                    <v-col cols="10" offset="1">
                      <v-img max-height="500px" contain :src="`http://localhost:1337`+image.url"></v-img>
                    </v-col>
                    <v-col v-if="memory.utilizador.id==user">
                      <Confirmation tag='image' :memory="memory" :objecto="image"/>
                    </v-col>
                  </v-row>
              </v-carousel-item>
            </v-carousel>
          </v-card>
        </v-col>
      </v-row>

      <v-row v-if="memory.utilizador.id==user">
        <v-col justify="center" align="center">
          <v-btn
            outlined
            color="#4F4E81"
            rounded
            depressed
            :loading="isSelecting1"
            @click="onButtonClick1"
          >
          Adicionar imagens
          </v-btn>
            <input
              ref="uploader1"
              class="d-none"
              type="file"
              accept="image/*"
              multiple
              @change="onFileChangedImage"
            >
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-card outlined id="videos">
            <v-card-title> Videos </v-card-title>
            <v-card-subtitle>{{memory.videos.length}} {{memory.videos.length == 1 ? 'Video' : 'Videos'}}</v-card-subtitle>
            <v-container v-for="video in memory.videos" :key="video.url">
              <v-row>
                <v-col cols="10" offset="1" align="center">
                  <video style="height:500px;"  class="my_video" :src="`http://localhost:1337`+video.url" controls></video>
                </v-col>
                <v-col v-if="memory.utilizador.id==user">
                  <Confirmation tag='video' :memory="memory" :objecto="video"/>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-col>
      </v-row>

      <v-row v-if="memory.utilizador.id==user">
        <v-col justify="center" align="center">
          <v-btn
            outlined
            color="#4F4E81"
            rounded
            depressed
            :loading="isSelecting2"
            @click="onButtonClick2"
          >
          Adicionar videos
          </v-btn>
            <input
              ref="uploader2"
              class="d-none"
              type="file"
              accept="video/*"
              multiple
              @change="onFileChangedVideo"
            >
        </v-col>
      </v-row>
    </v-container>
    
</template>


<script>
import gql from 'graphql-tag'
import axios from 'axios'
import Confirmation from '@/components/memories/Confirmation.vue'
import jwt from 'jsonwebtoken'

export default {
    data() {
        return {
          values: [],
          memory: '',
          play_p: "mdi-play",
          video: '',
          juice: '',
          editing:false,
          menu:false,
          date:null,
          title:null,
          local:null,
          content:null,
          user:"",
          select: [],
          search: "",
          isSelecting1:false,
          isSelecting2:false,
          dialog: false,
          token: localStorage.getItem('jwt'),
          payload: null
        } 
    },
    components: {
      Confirmation
    },
    watch: {
      'memory': function() {
         if (this.memory.date_of_memory) this.date = this.memory.date_of_memory.split("T")[0]
         this.title = this.memory.title
         this.local = this.memory.local
         this.content = this.memory.content
         this.select = this.memory.tags
      }
    },
    created(){
      this.video = document.querySelector('.video')
      this.juice = document.querySelector('.orange-juice')
      this.payload = jwt.decode(this.token)
      this.user = this.payload.id
    },
    updated(){
      this.user = this.payload.id
    },
    methods: {
      updateTags() {
        this.$nextTick(() => {
          this.select.push(...this.search.split(","));
          this.$nextTick(() => {
            this.search = "";
          });
        });
      },
      save(date) {
        this.$refs.menu.save(date)
      },
      onButtonClick1() {
        this.isSelecting1 = true
        window.addEventListener('focus', () => {
          this.isSelecting1 = false
        }, { once: true })
        this.$refs.uploader1.click()
      },
      onButtonClick2() {
        this.isSelecting2 = true
        window.addEventListener('focus', () => {
          this.isSelecting2 = false
        }, { once: true })
        this.$refs.uploader2.click()
      },
      onFileChangedImage(e) {
        let formData = new FormData();

        for (let i = 0; i < e.target.files.length; i++) {
          const file = e.target.files[i];
          formData.append(`files.images`, file, file.name);
        }

        var json = {}
        json['utilizador'] = this.user

        var token = localStorage.getItem('jwt')
        formData.append("data", JSON.stringify(json));

        axios.put("http://localhost:1337/memories/"+this.$route.params.id, formData , {headers: {'Authorization': `${token}`}})
          .then(() => {
            this.$router.go()
          })
          .catch(err => {
            console.log(err)
          })

      },
       onFileChangedVideo(e) {
        let formData = new FormData();

        for (let i = 0; i < e.target.files.length; i++) {
          const file = e.target.files[i];
           formData.append(`files.videos`, file, file.name);
        }

        var json = {}
        json['utilizador'] = this.user

        var token = localStorage.getItem('jwt')
        formData.append("data", JSON.stringify(json));

        axios.put("http://localhost:1337/memories/"+this.$route.params.id, formData , {headers: {'Authorization': `${token}`}})
          .then(() => {
            this.$router.go()
          })
          .catch(err => {
            console.log(err)
          })

      },
      removeImage(image){    
        var index = this.memory.images.map(function(item) { return item.url; }).indexOf(image.url);
        this.memory.images.splice(index, 1);
      },
      removeVideo(video){
        var index = this.memory.videos.map(function(item) { return item.url; }).indexOf(video.url);
        this.memory.videos.splice(index, 1);
      },
      adicionar() {
        if (this.values.length>0) {
          this.values.forEach(elem => {
            var idCollection = elem.id    
            var token = localStorage.getItem('jwt')
            axios.put("http://localhost:1337/memories/"+this.$route.params.id+"/addToCollections",  {idCollection}, {headers: {'Authorization': `${token}`}})
              .then(() => {
                this.$router.go()
              })
              .catch(err => {
                console.log(err)
              })
          });
        }
      },
      play_pause() {
        if(this.video.paused){
          this.play_p="mdi-pause";
          this.video.play();
        }
        else{
          this.play_p="mdi-play";
          this.video.pause();
        }
      },
      saveInfo(){
        let formData = new FormData();
        var json = {}
        json['title'] = this.title
        json['content'] = this.content
        json['local'] = this.local
        json['date_of_memory'] = this.date
        json['tags'] = this.select
        var token = localStorage.getItem('jwt')
        formData.append("data", JSON.stringify(json));
        axios.put("http://localhost:1337/memories/" + this.$route.params.id, formData , {headers: {'Authorization': `${token}`}})
          .then(() => {
            this.$router.go()
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    apollo: {
      memory:{
        query: gql`query Memory ($id: ID!){
          memory (id: $id){
            id
            title
            local
            content
            date_of_memory
            images {
              id
              url
            }
            tags
            videos {
              id
              url
            }
            people {
              id
              name
              local_of_birth
              date_of_birth
              date_of_death
            }
            utilizador {
              id
              username
              profile_picture {
                url
              }
            }
          }
        }
      `,
      variables(){
        return {
          id: this.$route.params.id
        }
      }
    },
    collections: {
      query: gql`
      query Collections ($id: ID!){
        collections(where:{utilizador:  $id}){
          id
          name
        }
      }`,
      variables(){
        return {
          id: this.user
        }
      }
    }
  }
}
</script>



<style> 

h1 {
   padding: 15px 0 15px 0;
}

.profile-link {
  text-decoration: none;
  
}
.profile-link:hover {
  color:#38387c;
  
}

.username {
    text-transform: none;
    align-content: left;
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

.person-link {
  color:#424292;
  text-decoration: none;
}

.person-link:hover{
  color:#1f1f44;
  font-weight: bold;
}

.tags{
  margin-right: 2px;
}


.special a{
  color:#424292;
  text-decoration: none; 
}

.special a:hover{
  color:#1f1f44;
  font-weight: bold;
}

</style>