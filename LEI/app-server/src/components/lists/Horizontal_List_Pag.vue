<template>
  <div v-if="this.items" id="horizontal_pagination" >

    <v-container>
        <v-row justify="center" >
            <v-col cols=8>
                <div class="center" id="content" ref="content">
                    <div class="internal" v-for="item in items" :key="item.id">
                        <span v-if="flag=='memories'" @click="handleClickM(item.id)"><MemoryCard :item="item" /></span> 
                        <span v-else-if="flag=='collections'" @click="handleClickC(item.id)"><CollectionCard :item="item" /></span> 
                    </div>
                </div>
            </v-col>
        </v-row>
    </v-container>

    <v-container>
        <v-row justify="center">
            <v-pagination
            v-model="paginaEscolhida"
            :length="this.totalPags"
            :total-visible="8"
            @input="buscarPagina"
            ></v-pagination>
        </v-row>
    </v-container>

  </div>
</template>






<script>
import CollectionCard from '@/components/collections/CollectionCard.vue'
import MemoryCard from '@/components/memories/MemoryCard.vue'
import gql from 'graphql-tag'

export default {
  name: "Horizontal-Vue",
  data() {
    return {
      paginaEscolhida:1,
      start:0,
      limit:4,
    }
  },
  components: {
    MemoryCard,
    CollectionCard
  },
  props : {
    flag: String,
    items : Array,
    value : String,
    filter: String,
    totalPags: Number,
    page: Number,
  },
  watch: {
    'page': function(val) {
        if (val==0) this.paginaEscolhida=1
    }
  },
  methods: {
    handleClickM(id){
      this.$router.push('/memories/' + id)
    },
    handleClickC(id){
      this.$router.push('/collections/' + id)
    },
    async buscarPagina(value) {
      var pag = value - 1 
      this.start = pag * 4
      var result
      if (this.flag=='memories') {
        result = await this.getInfoMemories()
        this.$emit('update:items', result.data.memories)
      }
      else {
        result = await this.getInfoCollections()
        this.$emit('update:items', result.data.collections)
      }
      this.$emit('updatePag:page', pag)
    },
    async getInfoCollections() {
      var result  
      if (this.filter) {
        var valor
        if (this.filter=='Nome')  valor="name";
        result = await this.$apollo.query({
          query: gql`
            query Collections ($value: String!, $start: Int!, $limit: Int!)  {
              collections(where: { ${valor}_contains: $value }, start:$start, limit:$limit) {
                id
                name
                collection_picture {
                  url
                }
                memories {
                  images {
                    url
                  }
                }
              }
            }
          `,
          variables: {
            value: this.value,
            start: this.start,
            limit: this.limit,
          },
        })
      }
      else {
        result = await this.$apollo.query({
          query: gql`
            query Collections($start: Int!, $limit: Int!)  {
              collections( start:$start, limit:$limit) {
                id
                name
                collection_picture {
                  url
                }
                memories {
                  images {
                    url
                  }
                }
              }
            }
          `,
          variables: {
            start: this.start,
            limit: this.limit,
          },
        })
      }
      return result
    },
    async getInfoMemories() {
      var result
      if (this.filter) {  
        var valor
        if (this.filter=='Título')  valor="title_contains";
        if (this.filter=='Descrição')  valor="content_contains";
        if (this.filter=='Tag')  valor="tags";
        result = await this.$apollo.query({
          query: gql`
            query Memories ($value: String!, $start: Int!, $limit: Int!)  {
              memories(where: { ${valor}: $value }, start:$start, limit:$limit) {
                id
                title
                content
                images {
                  url
                }
                tags
              }
            }
          `,
          variables: {
            value: this.value,
            start: this.start,
            limit: this.limit,
          },
        })
      }
      else {
        result = await this.$apollo.query({
          query: gql`
            query Memories($start: Int!, $limit: Int!)  {
              memories( start:$start, limit:$limit) {
                id
                title
                images {
                  url
                }
              }
            }
          `,
          variables: {
            start: this.start,
            limit: this.limit,
          },
        })
      }
      return result
    }
  }
}
</script>



    
<style>

.pagination{
   text-align: center;
   font-size: 20px;
}

.internal{
 width: 25%;
 display: inline-block;
}

.center{
 text-align: center;
 height: 200px;
 overflow: hidden;
 white-space: nowrap;
}

</style>