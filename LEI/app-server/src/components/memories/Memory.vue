<template>
  <div class="memory">
    <MemoryInfo/>
  </div>
</template>

<script>
import MemoryInfo from '@/components/memories/Memory_Info.vue'

export default {
  name: 'Memory',
  components: {
    MemoryInfo
  }
}
</script>