<template class="tmp">
  <b-card-group id="loginForm">
    <b-card border-variant="light" style="max-width: 20rem;" class="mb-2 mx-auto">
      <b-form-group
              label-for="input-username"
      >
        <b-form-input id="input-username" v-model="userName" placeholder="Username"></b-form-input>
      </b-form-group>

      <b-form-group
              label-for="input-pass"
      >
        <b-form-input id="input-pass" type="password" v-model="password" placeholder="Password"></b-form-input>
      </b-form-group>

      <b-button variant="outline-primary" @click="signIn">Sign in</b-button><br><br>
      <b-link :to="{ path: 'register'}">Don't have account?</b-link><br><br>
      <div class="text-center ma-2">
        <v-snackbar v-model="error">{{errorMessage}}
          <v-btn @click="error = false" color="pink" text>Close</v-btn>
        </v-snackbar>
      </div>
    </b-card>
  </b-card-group>
</template>

<script>
  import api from "./backend-api";
  import {AxiosInstance as AXIOS} from "axios";

  export default {
    name: 'Login',
    data() {
      return {
        userName: '',
        password: '',
        error: null,
        errorMessage: ''
      }
    },
    methods: {
      signIn() {
        this.error = null

        const jwtAuthenticationRequest = {
          'userName': this.userName,
          'password': this.password
        }

        api.login(jwtAuthenticationRequest).then(response => {
          console.log(response)
          if (response.status === 200) {
            localStorage.setItem('token', response.data.accessToken);
            this.getRole();
          }
        }).catch(err => {
          if (err.response.status === 400) {
            this.errorMessage = "Wrong username or password!";
            this.error = true
          } else if (err.response.status === 403) {
            this.errorMessage = "Check your email!";
            this.error = true
          }
          })


      },
      getRole() {

        AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

        api.getRoleAndId()
                .then(response => {
                  if (response.data.role == "PATIENT") {
                    //this.isChangedPass(response.data.userID)
                    this.$router.push("/phomepage")
                  } else if (response.data.role == "DOCTOR") {
                    //this.isChangedPass(response.data.userID)
                    this.$router.push("/dhomepage")
                  } else if (response.data.role == "NURSE") {
                    //this.isChangedPass(response.data.userID)
                    this.$router.push("/nursehomepage/")
                  } else if (response.data.role == "CLINIC_CENTER_ADMIN") {
                    //this.isChangedPass(response.data.userID)
                    this.$router.push("/ccaprofile")
                  } else if (response.data.role == "USER") {
                    this.$router.push("/" + response.data.userID + "/home/reservations")
                  } else {
                    this.$router.push("/phomepage")
                  }
                });
      }
      /*
      isChangedPass(id) {
        AXIOS.get("/users/" + id)
                .then(response => {
                  if (response.data.passChanged == false) {
                    this.$router.push("/change-pass/" + response.data.id)
                  }
                })
                .catch(err => console.log(err))
      }
    }


         */
    }

  }
</script>

<style>

  #loginForm{
    margin-top: 100px;
  }
  .tmp{
    background-color: black;
  }
</style>

