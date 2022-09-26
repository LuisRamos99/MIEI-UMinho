<template>
    <div v-if="this.person">
        <v-row no-gutter style="padding: 100px 0 0 0">
            <v-col cols=6 >
                <v-card
                    class="mx-auto"
                    max-width="344"
                    min-height="200"
                    outlined
                >
                    <v-list-item three-line style="padding: 50px 0 0 20px">
                        <v-list-item-content>
                            <v-list-item-title class="headline mb-1 text-left">
                            {{person.name}}
                            </v-list-item-title>
                            <v-list-item-subtitle v-if="person.local_of_birth">Local de Nascimento: {{person.local_of_birth}}</v-list-item-subtitle>
                            <v-list-item-subtitle v-if="person.date_of_birth">Data de Nascimento: {{person.date_of_birth.split("T")[0]}}</v-list-item-subtitle>
                            <v-list-item-subtitle v-if="person.date_of_death">Data de Morte: {{person.date_of_death.split("T")[0]}}</v-list-item-subtitle>  
                        </v-list-item-content>
                    </v-list-item>
                </v-card>
            </v-col>
            
            <v-col cols=6 style="padding:0 0 0 0">
                <HorizontalList :card="'2'" :flag="'memories'" :items="person.memories"/>
            </v-col>
        </v-row>
    </div>   
</template>


<script>
import gql from 'graphql-tag'
import HorizontalList from '@/components/lists/Horizontal_List.vue'

export default {
    data() {
        return {
        } 
    },
    components: {
      HorizontalList
    },
    apollo: {
      person: { 
        query : gql`
          query Person ($id: ID!){
            person(id:$id){
              id
              name
              local_of_birth
              date_of_birth
              date_of_death
              memories {
                id
                title
                images {
                  url
                }
              }
            }   
          }`,
        variables() {
          return {
            id: this.$route.params.id
          }
        }
      }
    }
  }
</script>



<style> 

h1 {
   padding: 15px 0 15px 0;
}

.profile-link {
  text-decoration: none;
  
}
.profile-link:hover {
  color:#38387c;
  
}

.username {
    text-transform: none;
    align-content: left;
}

.tag-input span.chip {
  background-color: #1976d2;
  color: #fff;
  font-size: 1em;
}

.tag-input span.v-chip {
  background-color: #1976d2;
  font-size:1em;
  padding-left:7px;
}

</style>