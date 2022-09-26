<template>
    <div id="notFound">
        <v-container style="margin-top:2%">
                   
            <v-row justify="center">
                <h1 class="home_title">Não foi possível encontrar a página!</h1>
            </v-row>
            
            <v-row style="margin-top:70px" justify="center">
                <v-col cols=5 >
                    <h3 class="font-weight-regular text-center">
                        <a href="/"> 
                            Regressar à página inicial
                        </a>
                    </h3>
                </v-col>
            </v-row>
                
            <v-row style="margin-top:70px" justify="center">
                <v-img max-width="450" src="../../public/humaans.png"/>
            </v-row>

        </v-container>
    </div>
</template>




<script>

    export default {
        name: 'notFound',
        data() {
            return { 
            }
        }
    }

</script>



<style>

</style>