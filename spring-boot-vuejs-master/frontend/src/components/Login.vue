<template class="tmp">
  <b-card-group>
    <b-card border-variant="light" style="max-width: 20rem;" class="mb-2 mx-auto">
      <b-form-group
              label-for="input-username"
      >
        <b-form-input id="input-username" v-model="username" placeholder="Username"></b-form-input>
      </b-form-group>

      <b-form-group
              label-for="input-pass"
      >
        <b-form-input id="input-pass" type="password" v-model="password" placeholder="Password"></b-form-input>
      </b-form-group>

      <b-button variant="outline-primary" @click="signIn">Sign in</b-button><br><br>
      <b-link :to="{ path: 'register'}">Don't have account?</b-link><br><br>
      <b-alert variant="danger" v-model="error" dismissible>{{this.errorMessage}}</b-alert>
    </b-card>
  </b-card-group>
</template>

<script>
  import api from "./backend-api";

  export default {
    name: 'Login',
    data() {
      return {
        username: '',
        password: '',
        error: null,
        errorMessage: ''
      }
    },
    methods: {
      signIn() {
        this.error = null

        const user = {
          'userName': this.username,
          'password': this.password
        }

        api.login(user).then(response => {
          console.log(response)
          if (response.status == 200) {
            localStorage.setItem('token', response.data.accessToken);
            this.getRole();
          }
        }).catch(err => {
            if (response.status == 400) {
              this.errorMessage = "Wrong username or password!";
              this.error = true
            } else if (response.status == 403) {
              this.errorMessage = "Check your email!";
              this.error = true
              console.log(err);
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

  .tmp{
    background-color: black;
  }
</style>

