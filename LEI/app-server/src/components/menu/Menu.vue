<template>
    <div id="menu">
        <Sidebar>
            <v-list style="background-color: #ffffff;">
                <v-list-item-group color="primary" class="sidebar-panel-nav">
                
                    <router-link to="/" class="rtr-link">
                      <v-list-item class="sidebar-list" link color="#3c22cc"  @click="sidebar_click()">
                        <v-list-item-content class="sidebar-content"> 
                          Página Inicial
                        </v-list-item-content>
                      </v-list-item>
                    </router-link>
  
                    <router-link to="/about" class="rtr-link">
                      <v-list-item link color="#3c22cc"  @click="sidebar_click()">  
                        <v-list-item-content class="sidebar-content"> 
                          Sobre
                        </v-list-item-content>
                      </v-list-item>
                    </router-link>
  
                    <router-link to="/memories" class="rtr-link">
                      <v-list-item link color="#3c22cc"  @click="sidebar_click()">
                        <v-list-item-content class="sidebar-content"> 
                          Memórias
                        </v-list-item-content>
                      </v-list-item>
                    </router-link>
  
                    <router-link to="/collections" class="rtr-link">
                      <v-list-item link color="#3c22cc"  @click="sidebar_click()">
                        <v-list-item-content class="sidebar-content"> 
                          Coleções
                        </v-list-item-content>
                      </v-list-item>
                    </router-link>

                </v-list-item-group>
            </v-list>
        </Sidebar>
    </div>
</template>

<script>
import Sidebar from '@/components/menu/Sidebar.vue';
import {mutations} from '@/store.js'


export default {
  components : {
    Sidebar,
  },
  methods : {
    sidebar_click() {
      mutations.toggleNav()
    }
  }
}
</script>

<style>

  .rtr-link {
    text-decoration: none;
  }

  .sidebar-panel-nav {
    background-color: #ffffff;
  }

  .sidebar-content {
    background-color: #ffffff;
    color: #4F4E81; 
    font-size: 150%;
    list-style-type: none;
    font-weight: bold;
    text-decoration: none;
  }

</style>