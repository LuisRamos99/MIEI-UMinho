<template>
    <div id="profile" v-if="utilizador">
      <v-container style="max-width: 100%">
        <div v-if="idUser==utilizador.id || level=='admin'">
            <ProfileInfoEdit :utilizador="utilizador" :collections="collections" :memories="memories"/>
        </div>
        <div v-else>
            <ProfileInfo :utilizador="utilizador" :collections="collections" :memories="memories"/>
        </div>
      </v-container>
    </div>
</template>


<script>
import gql from 'graphql-tag'
import ProfileInfo from '@/components/profile/Profile_Info.vue'
import ProfileInfoEdit from '@/components/profile/Profile_Info_Edit.vue'
import jwt from 'jsonwebtoken'

  export default {
    name: "profile",
    data() {
      return{
        token: localStorage.getItem('jwt'),
        payload: null,
        idUser: null,
        level: null,
      }  
    },
    created(){
      this.payload = jwt.decode(this.token),
      this.idUser = this.payload.id,
      this.level = this.payload.level
    },
    components: {
      ProfileInfo,
      ProfileInfoEdit
    },
    apollo: {
      memories: { 
        query : gql`
          query Memories ($id: ID!){
            memories(where:{utilizador:  $id}){
              id
              title
              images {
                url
              }
            }   
          }`,
        variables() {
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
              public
              collection_picture {
                url
              }
              memories {
                images {
                  url
                }
              }
            }
          }`,
        variables(){
          return {
            id: this.$route.params.id
          }
        }
      },
      utilizador: {
        query : gql`
          query Utilizador ($id: ID!){
            utilizador (id: $id) {
              id
              username
              about
              profile_picture {
                url
                width
              }
            }
          }`,
        variables(){
          return {
            id: this.$route.params.id
          }
        }
      }     
    }
  }
</script>



<style> 


</style>
