<template>
  <vue-scroll-snap :fullscreen="true">
    <div id="about" class="about" >

      <section class="item">

        <v-container>
          <h1 class="display-4">SOBRE NÓS</h1>
        </v-container> 

        <v-container style="margin-top:10%">
          <h1 class="overline">Somos um projeto que pretende fornecer às pessoas uma plataforma onde possam partilhar as suas memórias.</h1>
          <h1 class="overline">Partilhe connosco as suas experiências e conheça a de tantos outros!</h1>
          <h1 class="overline">Conte-nos a sua história!</h1>
          <div class="iconSeta">
            <i class="fa fa-chevron-down fa-2x" aria-hidden="true"></i>
          </div>
        </v-container>

      </section>

      <section class="item">

        <v-container>
          <h1 class="display-4">CONHEÇA A EQUIPA</h1>
        </v-container>

        <v-container>
          <v-row align="center" justify="center">
            <TeamCard v-bind:name="'Ricardo Cunha'" v-bind:description="'Developer'" v-bind:img="'ricardo.jpg'" style="display:inline-block"></TeamCard>
            <TeamCard v-bind:name="'Luis Ramos'"    v-bind:description="'Developer'" v-bind:img="'ramos.jpg'" style="display:inline-block; margin-left:1%"></TeamCard>
            <TeamCard v-bind:name="'Luís Vila'"     v-bind:description="'Developer'" v-bind:img="'vila.jpg'" style="display:inline-block; margin-left:1%"></TeamCard>
          </v-row>  
        </v-container>

      </section>

        
      <section class="item">

        <v-container>
          <h1 class="display-4">CONTACTOS</h1>
        </v-container> 

        <v-container> 
          <v-row align="center" justify="center">
            <TeamCard v-bind:name="'Onde estamos'" v-bind:description="'Braga, Portugal'" v-bind:img="'location.png'" style="display:inline-block"></TeamCard>
            <TeamCard v-bind:name="'Telefone'"     v-bind:description="'912345678'"       v-bind:img="'contact.png'" style="display:inline-block; margin-left:1%"></TeamCard>
            <TeamCard v-bind:name="'E-mail'"       v-bind:description="'memory@book.com'" v-bind:img="'email.png'" style="display:inline-block; margin-left:1%"></TeamCard>
          </v-row>
        </v-container>
        
      </section>
   
    </div>
  </vue-scroll-snap>     
</template>

<script>
import TeamCard from '@/components/profile/Profile_card.vue';
import VueScrollSnap from "vue-scroll-snap";

    export default {
        name: 'about',
        data() {
          return { 
          }
        },
        components : {
          TeamCard,
          VueScrollSnap
        }         
    }
</script>

<style>

.item {
  max-height: 100%;
  height: 100vh;
  margin-top: auto;
  margin-bottom: auto;
}

.about {
  background-color: #8785C4;
}

.about section {
  display: block;
}

.about img {
  width: 100%;
  height: 100%;
}

.display-4{
  color: black;
  margin-top: 2%;
  margin-left: 3%;
}

.overline{
  color: black;
  text-align: center;
}


.iconSeta {
  position: fixed;
  left: 50%;
  bottom: 10px;
}


</style>