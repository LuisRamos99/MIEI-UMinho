<template>
    <div id="login">
      <v-dialog max-width="400px" v-model="dialog">
            <template v-slot:activator="{ on }">
                <span class="hyperlink" v-on="on"><b>Login</b></span>
            </template>

            <v-tabs v-model="tab" show-arrows color="#57a2bf" background-color="white" icons-and-text light grow>
                <v-tabs-slider color="#57a2bf"></v-tabs-slider>
                <v-tab v-for="i in tabs" :key="i.name" >
                      <div class="caption py-1" >{{ i.name }}</div>
                </v-tab>
                <v-tab-item>
                    <v-card>
                        <v-card-text>
                            <v-container pa-0>
                                <p v-if="alert" class="alert">{{this.message}}</p>
                                <v-col cols="12">
                                    <v-text-field 
                                    type="text" 
                                    v-model="email" 
                                    :rules="[rules.required]"
                                    label="Email">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field 
                                    :append-icon="valueLogin ? 'mdi-eye' : 'mdi-eye-off'" 
                                    :type="valueLogin ? 'password' : 'text'" 
                                    v-model="password" 
                                    label="Password"
                                    :rules="[rules.required]"
                                    @click:append="() => (valueLogin = !valueLogin)">
                                    </v-text-field>
                                </v-col>
                            </v-container>
                        </v-card-text>

                        <v-card-actions class="justify-center">
                            <v-btn 
                            :loading="loading" 
                            v-ripple="{ class: 'primary--text' }" 
                            width="200" 
                            style="height:40px;" 
                            class="white--text" 
                            elevation="1" 
                            v-on:click="login()" 
                            color="#57a2bf"
                            >Login</v-btn>
                        </v-card-actions>

                    </v-card>
                </v-tab-item>
                
                <v-tab-item >
                    <v-card >
                        <v-card-text>
                            <v-container pa-0>
                                <p v-if="alert" class="alert">{{this.message}}</p>
                                <v-col cols="12">
                                    <v-text-field 
                                    type="text" 
                                    v-model="username" 
                                    :rules="[rules.required,rules.length]"
                                    label="Username" >
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field 
                                    type="text" 
                                    v-model="emailRegister" 
                                    :rules="[rules.required]"
                                    label="Email">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field 
                                    :append-icon="valueRegistarPass ? 'mdi-eye' : 'mdi-eye-off'" 
                                    :type="valueRegistarPass ? 'password' : 'text'"
                                    :rules="[rules.required]"  
                                    v-model="passwordRegister" 
                                    label="Password"
                                    @click:append="() => (valueRegistarPass = !valueRegistarPass)">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field 
                                    :append-icon="valueRegistarConfirPass ? 'mdi-eye' : 'mdi-eye-off'" 
                                    :type="valueRegistarConfirPass ? 'password' : 'text'"
                                    :rules="[rules.required, passwordMatch]" 
                                    block 
                                    v-model="passverify"
                                    label="Confirm Password"
                                    @click:append="() => (valueRegistarConfirPass = !valueRegistarConfirPass)">
                                    </v-text-field>
                                </v-col>
                            </v-container>
                        </v-card-text>

                        <v-card-actions class="justify-center">
                            <v-btn 
                            :loading="loading" 
                            v-ripple="{ class: 'primary--text' }" 
                            width="200" style="height:40px;" 
                            class="white--text" 
                            elevation="1" 
                            v-on:click="register()" 
                            color="#57a2bf"
                            >Register</v-btn>
                        </v-card-actions>          
                    </v-card>
                </v-tab-item>
            </v-tabs>  
      </v-dialog>
    </div>
</template>


<script>
import axios from 'axios'

    export default {
        name: 'Login',
        data() {
            return {
                tab: 0,
                tabs: [
                    {name:"Login", icon:"mdi-account"},
                    {name:"Register", icon:"mdi-account-outline"}
                ],
                rules: {
                    required: value => !!value || "This camp is required.",
                    length: v => (v || '' ).length <= 20 || 'Username deverá conter 20 caracteres ou menos'
                },
                username: "",
                password: "",
                passwordRegister: "",
                passverify: "",
                email: "",
                emailRegister: "",
                type: "password",
                message:'',
                alert: false,
                loading: false,
                dialog: false,
                valueLogin: String,
                valueRegistarPass: String,
                valueRegistarConfirPass: String,
            }
        },
        computed: {
            passwordMatch() {
                return () => this.passwordRegister === this.passverify || "Password must match";
            }
        },  
        methods: {
            postLogin(json) {
                axios.post("http://localhost:7700/utilizador/login/", json)
                    .then(data => {
                        localStorage.setItem('jwt',data.data.token)
                        this.$router.go()
                        this.dialog = false
                        this.loading = false
                    })
                    .catch(err => {
                        this.alert = true
                        this.message = err.response.data.message
                        this.loading = false
                    })
            },
            login() {
                this.loading = true
                var json = {}
                json['email'] = this.email
                json['password'] = this.password
                this.postLogin(json) 
            },
            register() {
                this.loading=true
                var json = {}
                json['nome'] = this.username
                json['email']    = this.emailRegister
                json['password'] = this.passwordRegister
                axios.post("http://localhost:7700/utilizador/registar/", json)
                    .then( () => {
                        this.postLogin(json) 
                    })
                    .catch(err => {
                        this.alert = true
                        this.message = err.response.data.message
                        this.loading = false
                    })       
            },            
        }
    }
  
</script>



<style>

#login {
    margin: auto;
}

.alert {
    text-align: center;
    color: red;
}
#login .tab {
    color: #57a2bf;
}

.hyperlink {
    text-decoration: none;
    color: #212121 !important;
}

</style>