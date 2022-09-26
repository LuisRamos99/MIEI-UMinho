<template>
  <div class="person">
    <v-container style="max-width: 100%">
        <PersonInfo/>
    </v-container>
  </div>
</template>

<script>
import PersonInfo from '@/components/personagem/Person_info.vue'

export default {
  name: 'Person',
  components: {
    PersonInfo
  }
}
</script>