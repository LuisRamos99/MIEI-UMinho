 <template>
   <div id="CollectionCard">
    <v-container style="padding:6px">
      <v-flex xs12>     
        <v-card dark class="mx-auto" max-width="400">

          <div class="onePic" v-if="item.collection_picture">
            <img :src="`http://localhost:1337`+this.item.collection_picture.url"/>
          </div>

          <div class="fourPics" v-else-if="urls.length>=4">
            <div class="firstLine">
              <img style="border-top-left-radius: 4px;" :src="`http://localhost:1337`+this.urls[0]"/>
              <img style="border-top-right-radius: 4px;" :src="`http://localhost:1337`+this.urls[1]"/>
            </div>
            <div class="secondLine">
              <img :src="`http://localhost:1337`+this.urls[2]"/>
              <img :src="`http://localhost:1337`+this.urls[3]"/>
            </div>
          </div> 

          <div class="onePic" v-else-if="urls.length>0">
            <img :src="`http://localhost:1337`+this.urls[0]"/>
          </div>

          <div class="onePic" v-else>
            <img src="../../../public/default.jpg"/>
          </div>

          <v-card-title>
              <h6>{{ item.name }}</h6>
          </v-card-title>
  
        </v-card>
      </v-flex>
    </v-container>
  </div>   
</template>


<script>

export default {
  name: "CollectionCard",
  data() {
    return{
      urls: [],
    }
  },
  created() {
    this.item.memories.forEach(memory => {
      if (memory.images.length>0) {
        this.urls.push(memory.images[0].url)
      }
    });
  },
  props: {
    item: Object
  }
}
</script>



<style>

.onePic img{
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  border: 0.8px solid rgb(0, 0, 0);
  height: 100px;
  width: 100%;
}

.fourPics {
  border-top: 0.8px solid rgb(0, 0, 0);
  border-left: 0.8px solid rgb(0, 0, 0);
  border-right: 0.8px solid rgb(0, 0, 0);
}

.firstLine {
 height: 50px;
}

.firstLine img {
  width: 50%;
  height: 50px;
}

.secondLine img {
  width: 50%;
  height: 50px;
}

</style>