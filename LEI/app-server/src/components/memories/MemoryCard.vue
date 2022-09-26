 <template>
   <div id="MemoryCard">
    <v-container style="padding:6px">
      <v-flex xs12>     
        <v-card dark class="mx-auto" max-width="400">

          <div class="foto">
            <img v-if="item.images[0]" :src="`http://localhost:1337`+item.images[0].url">
            <img v-else src="../../../public/erro404.jpeg">
          </div>
          
          <v-card-title>
              <h6>{{ item.title }}</h6>
          </v-card-title>
  
        </v-card>
      </v-flex>
    </v-container>
  </div>   
</template>


<script>

export default {
  name: "MemoryCard",
  props: {
    item: Object
  }
}
</script>


<style>

.foto img{
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  height: 100px;
  width: 100%;
  border: 0.8px solid rgb(0, 0, 0);
}

</style>