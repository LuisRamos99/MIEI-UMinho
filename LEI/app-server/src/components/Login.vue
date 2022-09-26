<template>
    <div id="login">
        <v-dialog max-width="400px" v-model="dialog">
            <template v-slot:activator="{ on }">
                <v-btn
                color="#4F4E81"
                text
                x-large
                plain
                elevation="0"
                v-on="on"
                >Login</v-btn>
            </template>
            
            <v-tabs v-model="tab" show-arrows color="#4F4E81" background-color="white" icons-and-text light grow>
                <v-tabs-slider color="#4F4E81"></v-tabs-slider>
                <v-tab v-for="i in tabs" :key="i.name" >
                    <v-icon large class="tab">{{ i.icon }}</v-icon>
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
                                    v-model="username" 
                                    :rules="[rules.required]"
                                    label="Username">
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
                            width="300" 
                            style="height:40px;" 
                            class="white--text" 
                            elevation="1" 
                            v-on:click="login()" 
                            color="#4F4E81"
                            >Login</v-btn>
                        </v-card-actions>

                        <v-card-actions class="justify-center">
                            <GoogleLogin :params="params" :onSuccess="onSuccess" :onFailure="onFailure">
                                <v-btn v-ripple="{ class: 'primary--text' }" width="300" style="height:40px;" outlined elevation="1" color="#4F4E81">
                                    <img width="20px" style=" margin-right:10px" alt="Google sign-in" src="../../public/logo_google.webp"/>
                                    Google
                                </v-btn>
                            </GoogleLogin>
                        </v-card-actions>
                    </v-card>
                </v-tab-item>
                
                <v-tab-item>
                    <v-card>
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
                                    v-model="email" 
                                    :rules="[rules.required]"
                                    label="Email">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field 
                                    :append-icon="valueRegistarPass ? 'mdi-eye' : 'mdi-eye-off'" 
                                    :type="valueRegistarPass ? 'password' : 'text'"
                                    :rules="[rules.required]"  
                                    v-model="password" label="Password"
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
                            width="300" style="height:40px;" 
                            class="white--text" 
                            elevation="1" 
                            v-on:click="register()" 
                            color="#4F4E81"
                            >Register</v-btn>
                        </v-card-actions>

                        <v-card-actions v-if="!google_id" class="justify-center">
                            <GoogleLogin :params="params" :onSuccess="onSuccessRegister" :onFailure="onFailure">
                                <v-btn v-ripple="{ class: 'primary--text' }" width="300" style="height:40px;" outlined elevation="1" color="#4F4E81">
                                    <img width="20px" style=" margin-right:10px" alt="Google sign-up" src="../../public/logo_google.webp" />
                                    Google
                                </v-btn>
                            </GoogleLogin>
                        </v-card-actions>            
                    </v-card>
                </v-tab-item>

            </v-tabs>   
        </v-dialog>
    </div>
</template>




<script>
import axios from 'axios'
import GoogleLogin from 'vue-google-login'
import bcrypt from 'bcryptjs'

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
                params: {
                    client_id: "700992731861-u8shkr16p914ldvtbnsq4ketr09m62ul.apps.googleusercontent.com"
                },
                username: "",
                password: "",
                passverify: "",
                email: "",
                type: "password",
                valueLogin: String,
                valueRegistarPass: String,
                valueRegistarConfirPass: String,
                message:'',
                alert: false,
                loading: false,
                dialog: false,
                google_id : false,
                google_profile_pic:''
            }
        },
        computed: {
            passwordMatch() {
                return () => this.password === this.passverify || "Password must match";
            }
        },  
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        components: {
            GoogleLogin
        },    
        methods: {
            postLogin(json) {
                axios.post("http://localhost:7000/users/login", json)
                    .then(data => {
                        localStorage.setItem('jwt',data.data.token)
                        this.$emit('loged')
                        this.$router.go()
                        this.dialog = false
                        this.loading = false
                    })
                    .catch(err => {
                        this.alert = true
                        this.message = err.response.data.message
                    })
            },
            login() {
                this.loading = true
                var json = {}
                json['username'] = this.username
                json['password'] = this.password
                this.postLogin(json)
            },
            onSuccess(googleUser) {
                var json = {}
                json['username'] = googleUser.getBasicProfile().getId()
                json['password'] = "empty"
                axios.post("http://localhost:7000/users/google/login", json)
                    .then(data => {
                        localStorage.setItem('jwt',data.data.token)
                        this.$emit('loggedIn')
                        this.$router.go()
                        this.dialog = false
                    })
                    .catch(err => {
                        this.alert = true
                        this.message = err.response.data.message                
                    })
            },
            onFailure(){   
            },
            register() {
                this.loading=true
                var json = {}
                json['username'] = this.username
                json['email']    = this.email
                var pass = bcrypt.hashSync(this.password, 10);

                if(this.google_id)
                    json['googleId'] = this.google_id

                json['password'] = pass
                axios.post("http://localhost:7000/users/register", json)
                    .then( () => {
                        json['password'] = this.password
                        this.postLogin(json)
                    })
                    .catch(err => {
                        this.alert = true
                        this.message = err.response.data.message
                    })        
            },
            onSuccessRegister(googleUser) {
                console.log(googleUser.getBasicProfile().getId())
                this.email = googleUser.getBasicProfile().getEmail()
                this.google_profile_pic = googleUser.getBasicProfile().getImageUrl()
                this.google_id = googleUser.getBasicProfile().getId()
            },             
        }
    }
  
</script>



<style scoped>

    #login {
        margin: auto;
    }

    .alert {
        text-align: center;
        color: red;
    }

    .tab {
        color: #CCCCCC;
    }

</style>