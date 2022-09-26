<template>
    <div id="collection" v-if="collection">
        <div v-if="userId==collection.utilizador.id || level=='admin'">
            <CollectionInfoEdit/>
        </div>
        <div v-else>
            <CollectionInfo/>
        </div>
    </div>

</template>


<script>
import gql from 'graphql-tag'
import CollectionInfo from '@/components/collections/Collection_Info.vue'
import CollectionInfoEdit from '@/components/collections/Collection_Info_Edit.vue'
import jwt from 'jsonwebtoken'

export default {
    name: "collection",
    data() {
        return{
            payload:null,
            userId: "",
            level: null
        }  
    },
    created() {
      this.payload = jwt.decode(localStorage.getItem('jwt'))
      this.userId = this.payload.id
    },
    components: {
      CollectionInfo,
      CollectionInfoEdit
    },
    apollo: {
      collection:{
        query: gql`
          query Collection ($id: ID!){
              collection (id: $id) {
                  utilizador {
                    id
                  }
              }
          }`,
        variables(){
          return {
            id: this.$route.params.id
          }
        }
      } 
    }
}
</script>



<style> 


</style>
