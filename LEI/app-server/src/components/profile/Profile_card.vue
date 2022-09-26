<template>
  <div id="app">
   
    <v-card class="mx-auto my-12" max-width="290" >
      
      <template slot="progress">
        <v-progress-linear
          color="deep-purple"
          height="10"
          indeterminate
        ></v-progress-linear>
      </template>
     
      <v-img height="250" :src="image"></v-img>
      <v-card-title>{{name}}</v-card-title>
         
      <v-card-text>
        <v-list-item-subtitle>{{description}}</v-list-item-subtitle>
      </v-card-text>
    
    </v-card>
  
  </div>
</template>

<script>
export default {
  name:"Profile_card",
  data(){
    return {
       image: this.img
      }
  },
  props:['name', 'description', 'img']
}

</script>


<style>

</style>