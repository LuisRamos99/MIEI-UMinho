<template>
    <div v-if="utilizador" id="dropdown">

        <div class="dropbtn">
            <v-avatar size="50" class="avatar">
                <v-img v-if="utilizador.profile_picture" :src="'http://localhost:1337' + utilizador.profile_picture.url"></v-img>
                <v-img v-else src="../../../public/user.png"></v-img>
            </v-avatar>
            <b class="username">{{utilizador.username}}</b>
            <i class="fa fa-sort-desc" style="margin-left:10px;"></i>
        </div>

        <div class="dropdown-content">
            <router-link style="color:#4F4E81" :to="'/users/' + utilizador.id">Perfil</router-link>
            <router-link style="color:#4F4E81" to="/new_memory">Nova Memória</router-link>
            <NewCollection/> 
            <a style="color:#4F4E81" v-on:click="handleLogout()" href="/">
                <i style="margin-right:2px" class="fa fa-sign-out" aria-hidden="true"></i>
                Logout
            </a>
        </div>
        
    </div>
</template>

<script>
import NewCollection from '@/components/forms/New_Collection.vue'
import gql from 'graphql-tag'
import jwt from 'jsonwebtoken'

    export default {
        name: "dropdwon",
        data() {
          return {
            payload: null,
            token:localStorage.getItem('jwt'),
            userId:"",
            utilizador:null
          }
        },
        components: {
          NewCollection
        },
        created() {
          this.payload = jwt.decode(this.token),
          this.userId = this.payload.id
        },
        methods: {
            handleLogout() {
                localStorage.clear();
            },
        },
        apollo: {
            utilizador: {
              query : gql`
                query Utilizador ($id: ID!){
                  utilizador (id: $id) {
                    id
                    username
                    profile_picture {
                      url
                    }
                  }
                }`,
              variables(){
                return {
                  id: this.userId
                }
              }
            }
          }   
    }
</script>

<style>

.dropdown {
  overflow: hidden;
}

.dropdown .dropbtn {
font-size: 16px;  
border: none;
outline: none;
color: #4F4E81;
background-color: inherit;
font-family: inherit;
margin: 0;
}

.dropbtn {
  padding: 10px 10px 10px 10px;
}

.dropdown-content {
  display: none;
  position: absolute;
  right: 0px;
  top: 64px;
  background-color: #ffffff;
  min-width: 200px;
  box-shadow: 0px 3px 5px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  float: none;
  color: #4F4E81;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {
  background-color: #ddd;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.username {
  top: 4px;
  position: relative;
  padding: 0px 10px 0px 15px;
  font-size:150%
}

.avatar {
  box-shadow: 0px 0px 5px 0.1px rgba(0,0,0,0.2);
}

</style>