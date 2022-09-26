<template>
  <div id="navbar-max">
    <div v-if="token">
      <v-layout row justify-center class="navbar">
        <v-toolbar color="#57a2bf" class="hidden-xs-and-down">
          <v-img
          @click="goToLink('/')"
          class="logo"
          max-height="40"
          style="cursor:pointer"
          max-width="210"
          src="../assets/mfc.png"
          ></v-img>
            <v-toolbar-items>
             <v-btn class="navbar-button">
                <span class="hyperlink" @click="goToLink('/publicacoes')"><b>Publicações</b></span>
             </v-btn>
             <v-btn class="navbar-button">
                <span class="hyperlink" @click="goToLink('/receitas')"><b>Receitas</b></span>
             </v-btn>
            </v-toolbar-items>
            <v-spacer></v-spacer>
            <v-toolbar-items>
             <v-btn class="navbar-button">
                <span @click="handleLogout()"> <b> Logout </b></span>
             </v-btn>
            </v-toolbar-items>
          </v-toolbar>
      </v-layout>
    </div>
    <div v-else>
      <v-layout row justify-center class="navbar">
        <v-toolbar color="#57a2bf" class="hidden-xs-and-down">
          <v-img
          @click="goToLink('/')"
          class="logo"
          max-height="40"
          style="cursor:pointer"
          max-width="210"
          src="../assets/mfc.png"
          ></v-img>
            <v-toolbar-items>
             <v-btn class="navbar-button">
                <span class="hyperlink" @click="goToLink('/publicacoes')"><b>Publicações</b></span>
             </v-btn>
             <v-btn class="navbar-button">
                <span class="hyperlink" @click="goToLink('/receitas')"><b>Receitas</b></span>
             </v-btn>
            </v-toolbar-items>
            <v-spacer></v-spacer>
            <v-toolbar-items>
             <v-btn class="navbar-button">
                <Login/>
             </v-btn>
            </v-toolbar-items>
          </v-toolbar>
      </v-layout>
    </div>
  </div>

</template>

<script>
import Login from "@/components/Login.vue"
import jwt from 'jsonwebtoken'

export default {
  name: "App",
  components: {
    Login
  },
  methods: {
    handleLogout() {
      localStorage.clear();
      this.$router.go()
      window.location.href = '/'
    },
    goToLink(link) {
      var currentUrl = window.location.pathname;
      if (currentUrl!=link) this.$router.push(link)
    },
    verifyToken(token){
      var t = null;
      jwt.verify(token,'PRC2021',function(e,decoded){
        if(e) {t = null}
        else return t = decoded
      })
      return t
    }
  },
  data () {
    return {
      idUser: null,
      dialog: false,
      token: localStorage.getItem('jwt')
    }
  },
  created(){
    if (this.token) {
      if (!this.verifyToken(this.token)) {
        alert("A sua sessão foi expirada!")
        localStorage.clear()
        this.$router.go()
      }
    }
  }
};
</script>

<style>

#navbar-max {
    margin-bottom: 70px;
}

.navbar {
    z-index: 10; 
    width:100%; 
    position: fixed;
    margin-left: 0px !important;
}

.navbar-button {
    background-color: #57a2bf !important; 
    padding-top: 10px !important; 
    box-shadow: 0px 0px 0px 0px rgb(0 0 0 / 20%) !important;
}

.hyperlink {
    text-decoration: none;
    color: #212121 !important;
}

.logo{
    margin-left: -10px;
    margin-right: 10px;
    margin-top: 10px;
}
</style>

