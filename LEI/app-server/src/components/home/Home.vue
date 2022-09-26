<template>
  <div id="home" class="home" >
    <div v-if="this.token" justify="center" class="homeIn">
        <HomeIn/>
    </div>
    <div v-else>
        <HomeOut/>
    </div>
  </div>
</template>




<script>
import HomeIn from '@/components/home/HomeIn.vue'
import HomeOut from '@/components/home/HomeOut.vue'

    export default {
        name: 'home',
        data() {
            return { 
                token: localStorage.getItem('jwt'),
            }
        },
        components: {
            HomeOut,
            HomeIn
        }
    }
</script>




<style>


</style>