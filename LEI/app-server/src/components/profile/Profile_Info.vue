<template>
  <div v-if="utilizador && memories && collections">
    <v-row no-gutter align="center">
      <v-col cols=6>
        <v-responsive min-width=500 max-width=500>
          <v-row align="center" >

            <v-col cols=2 offset="2">  
              <div style="padding: 60px 0 0 0">
                <v-avatar size="160" class="profile_pic">
                  <v-img v-if="utilizador.profile_picture" @click.stop="dialog = true" :src="'http://localhost:1337' + utilizador.profile_picture.url"></v-img>
                  <v-img v-else src="../../../public/user.png"></v-img>
                </v-avatar>
                <v-dialog
                  v-model="dialog"
                  v-if="utilizador.profile_picture"  :max-width="utilizador.profile_picture.width > 800 ? 800 : utilizador.profile_picture.width"
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
          <v-card-title>Sobre:</v-card-title>
          <v-divider class="mx-4"></v-divider>
          <v-card-text v-if="utilizador.about">{{utilizador.about}}</v-card-text>
          <v-card-text v-else>Este utilizador não tem descrição</v-card-text>
        </v-card>
      </v-col>
      
      <v-col cols=6 offset="1">
        <Horizontal_List v-if="this.selected=='memories'" :card="'2'" :flag="'memories'" :items="memories"/>
        <Horizontal_List v-else :card="'2'" :flag="'collections'" :items="collections"/>
      </v-col>
    </v-row>
  </div>
</template>



<script>
import Horizontal_List from '@/components/lists/Horizontal_List.vue'

export default {
  data() {
      return{
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