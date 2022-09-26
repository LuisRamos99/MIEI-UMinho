<template>
  <div id="collections" class="collections">
    <h1>Coleções</h1>
    <p>Foram encontrados {{this.number}} resultados!</p>
    
    <v-container>
      <v-row>

        <v-col cols="2">
            <v-select
            :items="values"
            v-model="filter"
            light
            label="Filtro">
            </v-select>
        </v-col>

        <v-col cols="2">
            <v-text-field 
            type="text" 
            v-model="word" 
            label="Palavra">
            </v-text-field>        
        </v-col>

        <v-col cols="2">
            <v-btn style="margin-top:10px" outlined color="#4F4E81" depressed @click="procurar()"> 
              Procurar
            </v-btn>        
        </v-col>

      </v-row>
    </v-container>
    
    <v-container>
      <HorizontalList 
      :flag="'collections'" 
      :items="collections" 
      :page="pag" 
      :filter="filter" 
      :totalPags="totalPags" 
      :value="word" 
      @update:items="update" 
      @updatePag:page="updatePag"/>
    </v-container>

    <v-container>
      <v-row>
        <v-col align="right">
          <NewCollection button='true'/>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>


<script>
import NewCollection from '@/components/forms/New_Collection.vue'
import HorizontalList from '@/components/lists/Horizontal_List_Pag.vue'
import gql from 'graphql-tag'
import axios from 'axios'

export default {
  name: 'Collections',
  components: {
   HorizontalList,
   NewCollection
  },
  data() {
    return {
      pag:0,
      number: 0,
      totalPags: 0,
      word: "",
      filter: "",
      values: ['Nome']
    }
  },
  methods: {
    update(value){
      this.collections=value;
    },
    updatePag(value){
      this.pag=value;
    },
    async procurar() {
      if (this.filter) {
        var valor
        if (this.filter=='Nome')  valor="name";
        var token = localStorage.getItem('jwt')
        await axios.get('http://localhost:1337/collections/count?'+valor+'_contains=' + this.word ,{headers: {'Authorization': `${token}`}})
          .then(response => {
            this.pag=0
            this.number = response.data
            this.totalPags = Math.floor(this.number/4)
            if ((this.number%4)!=0) this.totalPags = this.totalPags + 1  
          })
          .catch(error => console.log(error))
        
        var result = await this.$apollo.query({
          query: gql`
            query Collections ($value: String!)  {
              collections(where: { ${valor}_contains: $value },limit:4) {
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
            value: this.word,
          },
        })
        this.collections = result.data.collections
      }
    }
  },
  apollo: {
    collections: {
      query: gql`
      query Collections {
        collections(limit:4) {
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
    `
    }
  },
  mounted () {
    var token = localStorage.getItem('jwt')
    axios.get('http://localhost:1337/collections/count',{headers: {'Authorization': `${token}`}})
       .then(response => {
          this.number = response.data
          this.totalPags = Math.floor(this.number/4)
          if ((this.number%4)!=0) this.totalPags = this.totalPags + 1  
         })
       .catch(error => console.log(error))
  }
}
</script>




<style>

.collections h1{
  padding: 20px 0px 40px 0px; 
  text-align: center;
}

.collections p{
  text-align: center
}

</style>