<template class="tmp">
  <div>
    <v-dialog width="700px"  v-model="dialog" >
      <v-card >
        <RegisterForm class="registerDialog"></RegisterForm>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="dialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <b-card-group id="loginForm">
      <b-card border-variant="light" style="max-width: 20rem;" class="mb-2 mx-auto">
        <b-form-group label-for="input-username">
          <b-form-input id="input-username" v-model="userName" placeholder="Username"></b-form-input>
        </b-form-group>

        <b-form-group label-for="input-pass">
          <b-form-input id="input-pass" type="password" v-model="password" placeholder="Password"></b-form-input>
        </b-form-group>

        <b-button variant="outline-primary" @click="signIn">Sign in</b-button>
        <br />
        <br />
        <div class="my-2">
          <v-btn color="primary" @click="dialog = !dialog">Dont have an account?</v-btn>
        </div>

        <div class="text-center ma-2">
          <v-snackbar v-model="error">
            {{errorMessage}}
            <v-btn @click="error = false" color="pink" text>Close</v-btn>
          </v-snackbar>
        </div>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
import api from "../backend-api";
import RegisterForm from "./RegisterForm";

export default {
  name: "Login",
  components: {
    RegisterForm
  },
  data() {
    return {
      dialog: false,
      userName: "",
      password: "",
      error: null,
      errorMessage: ""
    };
  },
  methods: {
    passwordChanged() {},
    signIn() {
      this.error = null;

      const jwtAuthenticationRequest = {
        userName: this.userName,
        password: this.password
      };

      api
        .login(jwtAuthenticationRequest)
        .then(response => {
          //console.log(response)

          if (response.status === 200) {
            localStorage.setItem("token", response.data.accessToken);
            //console.log(response.data.accessToken);
            localStorage.setItem("loggedIn", "true");
            this.getRole();
          }
        })
        .catch(err => {
          if (err.response.status === 400) {
            this.errorMessage = "Wrong username or password!";
            this.error = true;
          } else if (err.response.status === 403) {
            this.errorMessage = "Check your email!";
            this.error = true;
          }
          console.log();
        });
    },
    getRole() {
      api.setAuthentication().defaults.headers["Authorization"] =
        "Bearer " + localStorage.getItem("token");
      api
        .getRoleAndId()
        .then(response => {
          localStorage.setItem("userName", response.data.userID.username);
          localStorage.setItem("firstName", response.data.userID.firstName);
          localStorage.setItem("lastName", response.data.userID.lastName);
          localStorage.setItem("role", response.data.role);
          localStorage.setItem("userID", response.data.userID.id);

          //console.log(response)
          if (response.data.role === "DOCTOR") {
            this.$store.commit("setUser", response.data.userID);
            localStorage.setItem("doctorID", response.data.userID.id);
            console.log(this.$store.getters.getUser);
            if (response.data.userID.passChanged) {
              this.$router.push("/doctor-homepage");
            } else {
              this.$router.push("/change-password");
            }
          } else if (response.data.role === "CLINIC_ADMIN") {
            console.log(response.data.userID);
            localStorage.setItem("clinicID", response.data.userID.clinicID);
            this.$store.commit("setUser", response.data.userID);
            if (response.data.userID.passChanged) {
              this.$router.push("/clinic-admin-profile");
            } else {
              this.$router.push("/change-password");
            }
          } else if (response.data.role === "CLINIC_CENTER_ADMIN") {
            /*if(localStorage.getItem(userID) === "100") {
              this.$router.push("/predefined-center-admin")
            }
            else {
              this.$router.push("/center-admin-profile");
            }*/
            this.$router.push("/center-admin-profile");
          } else if (response.data.role === "PATIENT") {
            this.$store.commit("setUser", response.data.userID);
            if (response.data.userID.passChanged) {
              this.$router.push("/patient-homepage");
            } else {
              this.$router.push("/change-password");
            }
          } else if (response.data.role === "NURSE") {
            this.$store.commit("setUser", response.data.userID);
            localStorage.setItem("doctorID", response.data.userID.id);
            console.log(this.$store.getters.getUser);
            if (response.data.userID.passChanged) {
              this.$router.push("/nurse-homepage");
            } else {
              this.$router.push("/change-password");
            }
          }
        })
        .catch(e => {
          console.log("GetRoleCatchBlock");
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
};
</script>

<style>

.registerDialog{
    width: 200px;
    margin-left: 10%;
    margin-left: 10%;


}

#loginForm {
  margin-top: 100px;
}

.tmp {
  background-color: black;
}
</style>

