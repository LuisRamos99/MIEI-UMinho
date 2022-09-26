<template>
  <div v-if="utilizador && memories && collections">
    <v-row no-gutter align="center">
      <v-col cols=6>
        <v-responsive min-width=500 max-width=500>
          <v-row align="center" >
            
            <v-col cols="2" style="padding: 110px 0 0 50px">
              <div @click="chooseImg()" class="click" >
                <i style="cursor: pointer;color:gray" class="fa fa-camera fa-lg" aria-hidden="true"></i>
                <input
                  ref="uploader"
                  class="d-none"
                  type="file"
                  accept="image/*"
                  @change="onFileChanged"
                >
              </div>
            </v-col>
  
            <v-col cols=2>  
              <div style="padding: 60px 0 0 0">
                <v-avatar size="160" class="profile_pic">
                  <v-img v-if="utilizador.profile_picture" @click.stop="dialog = true" :src="'http://localhost:1337' + utilizador.profile_picture.url"></v-img>
                  <v-img v-else src="../../../public/user.png"></v-img>
                </v-avatar>
                <v-dialog
                  v-model="dialog"
                  v-if="utilizador.profile_picture"  
                  :max-width="utilizador.profile_picture.width > 800 ? 800 : utilizador.profile_picture.width"
                  >
                  <v-card>
                    <v-img v-if="utilizador.profile_picture" :src="'http://localhost:1337' + utilizador.profile_picture.url"></v-img>
                  </v-card>
                </v-dialog>
              </div>
            </v-col >
  
            <v-col cols=6 offset=2>
              <div style="padding: 80px 0 0 0px">
                <h2>{{utilizador.username}}</h2>
              </div> 
            </v-col>
  
          </v-row>
        </v-responsive>
      </v-col>
      
      <v-col cols=3 offset=1 style="padding: 70px 0 0 0">
        <v-row>
          <v-col cols=6 class="mem_col" @click="updateSelected('memories')">   
            <v-row justify="center">
              <h2 >Memórias</h2>
            </v-row>
            <v-row justify="center">
              <p >{{memories.length}}</p>
            </v-row>
          </v-col>
          <v-col cols=6 class="mem_col" @click="updateSelected('collections')">
            <v-row justify="center">
              <h2>Coleções</h2>
            </v-row>
            <v-row justify="center">
              <p>{{collections.length}}</p>
            </v-row>
            
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <v-divider style="margin-top: -50px"></v-divider>
    <v-row style="padding: 170px 0px 0px 0px"/>
    
    <v-row no-gutters>
      <v-col cols=4 offset=1>
        <v-card max-width="400" elevation="0" outlined style="background-color: inherit;">
          <v-row>
            <v-col cols=4>
              <v-card-title>Sobre:</v-card-title>
            </v-col>
            <v-col cols=1 offset=6>
              <i v-if="editing" style="cursor: pointer;color:red; margin-top:20px" class="fa fa-times fa-lg" aria-hidden="true" @click="editing=!editing"></i>
              <i v-else style="cursor: pointer;color:gray; margin-top:20px" class="fa fa-pencil fa-lg" aria-hidden="true" @click="editing=!editing"></i>
            </v-col>
          </v-row>
          <v-divider class="mx-4"></v-divider>
          <div v-if="!editing">
            <v-card-text v-if="utilizador.about">{{utilizador.about}}</v-card-text>
            <v-card-text v-else>Este utilizador não tem descrição</v-card-text>
          </div>
          <div v-else>
            <v-card-text>
              <v-textarea
                type="text"
                v-model="newAbout"
                no-resize
                class="pa-0"
                rows="4"
              ></v-textarea>
              <v-btn 
                :loading="isLoading" 
                :style="{left: '50%', transform:'translateX(-50%)'}"
                elevation="1" 
                dark
                v-on:click="updateAbout()"
              >Guardar</v-btn>
            </v-card-text>
          </div>
        </v-card>
      </v-col>
      
      <v-col cols=6 offset=1>
        <Horizontal_List v-if="this.selected=='memories'" :card="'2'" :flag="'memories'" :items="memories"/>
        <Horizontal_List v-else :card="'2'" :flag="'collections'" :items="collections"/>
      </v-col>
    </v-row>
  </div>
</template>



<script>
import axios from 'axios'
import Horizontal_List from '@/components/lists/Horizontal_List.vue'

export default {
  data() {
      return{
        newAbout: this.utilizador.about,
        editing: false,
        isLoading: false,
        dialog : false,
        selected: "memories"
      };
  },
  components:{
    Horizontal_List
  },
  props: {
    utilizador: Object,
    collections: Array,
    memories: Array
  },
  methods : {
    updateAbout() {
      this.isLoading=true
      var json = {}
      json['about'] = this.newAbout
      var token = localStorage.getItem('jwt')
      axios.put("http://localhost:1337/utilizadors/"+this.$route.params.id, json , {headers: {'Authorization': `${token}`}})
        .then(() => {
          this.editing=false
          this.isLoading=false
          this.$router.go()
        })
        .catch(err => {
          console.log(err)
        })
    },
    chooseImg() {
      this.$refs.uploader.click()
    },
    onFileChanged(e) {   
      this.selectedFile = e.target.files[0]
      const file = this.selectedFile;
      let formData = new FormData();
      formData.append(`files.profile_picture`, file, file.name);
      var json = {}
      formData.append("data", JSON.stringify(json))
      var token = localStorage.getItem('jwt')
      axios.put("http://localhost:1337/utilizadors/"+this.$route.params.id , formData , {headers: {'Authorization': `${token}`}})
        .then(() => {
          this.$router.go()
        })
        .catch(err => {
          console.log(err)
        })
    },
    updateSelected(value) {
      this.selected = value
    }
  }
}
</script>



<style>
.mem_col {
    float: none;
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    text-align: left;

  }

.mem_col:hover{
  background-color: #ddd;
}


.profile_pic {
  cursor: pointer;
  box-shadow: 0px 0px 5px 0.1px rgba(0,0,0,0.2);
  filter: brightness(100%);
  transition-duration: 0.2s;
  transition-property: filter;
}

.profile_pic:hover {
  filter: brightness(80%);
  transition-duration: 0.2s;
  transition-property: filter;
}

</style>