<template>
  <div v-if="this.items" class="horizontalList">
      
      <div class="esq">
        <button v-if="this.items.length>0" style="height: 200px;" id="left_button" @click="swipe(-1,'left_button')">
          <i class="fa fa-angle-left fa-5x" ></i>
        </button>
      </div>
      
      <div class="centro" id="contente" ref="contente">
        <div :class="card=='2' ? 'cartaoMetade' : 'cartao'" v-for="item in items" :key="item.id">
          <span v-if="flag=='memories'" @click="handleClickM(item.id)"><MemoryCard :item="item" /></span> 
          <span v-else-if="flag=='collections'" @click="handleClickC(item.id)"><CollectionCard :item="item" /></span> 
        </div>
      </div>

      <div class="dir">
        <button v-if="this.items.length>0" style="height: 200px;" id="right_button" @click="swipe(1,'right_button')">
          <i class="fa fa-angle-right fa-5x"></i>
        </button>
      </div>
    
  </div>
</template>






<script>
import CollectionCard from '@/components/collections/CollectionCard.vue'
import MemoryCard from '@/components/memories/MemoryCard.vue'


export default {
  name: "Horizontal-Vue",
  components: {
    MemoryCard,
    CollectionCard
  },
  props : {
    flag: String,
    card: String,
    items : Array
  },
  methods: {
    handleClickM(id){
      this.$router.push('/memories/' + id)
    },
    handleClickC(id){
      this.$router.push('/collections/' + id)
    },
    scrollTo(element, scrollPixels, duration) {
      const scrollPos = element.scrollLeft;
      if ( !( (scrollPos === 0 || scrollPixels > 0) && (element.clientWidth + scrollPos === element.scrollWidth || scrollPixels < 0))) {
        const startTime = "now" in window.performance ? performance.now() : new Date().getTime();
        
        var fn = function scroll(timestamp) {
          const timeElapsed = timestamp - startTime;
          const progress = Math.min(timeElapsed / duration, 1);
          element.scrollLeft = scrollPos + scrollPixels * progress;
          if (timeElapsed < duration) window.requestAnimationFrame(fn); 
          else return;
        }
        window.requestAnimationFrame(fn);
      }
    },
    async swipe(direction, id) {
      //desativa butao
      document.getElementById(id).disabled = true;  

      const contente = this.$refs.contente;
      var w = window.innerWidth
      this.card=='2' ? w = w / 4 : w = w / 2;
      this.scrollTo(contente, w*direction, 500);
      await this.sleep(500);

      //ativa butao
      document.getElementById(id).disabled = false ;
    },
    sleep(ms) {
       return new Promise((resolve) => {setTimeout(resolve, ms);});
    }
  }
}
</script>



    
<style>


.horizontalList {
  text-align: center;
  font-size: 20px;
}

.esq{
 float: left; 
 width: 24.5%;
 height: 200px;
}

.cartao{
 width: 25%;
 display: inline-block;
}

.cartaoMetade{
 width: 50%;
 display: inline-block;
}


.centro{
 float: left; 
 width: 50%;
 height: 200px;
 overflow: hidden;
 white-space: nowrap;
}

.dir{
  float: right; 
  width: 24.5%;
  height: 200px;
}

</style>