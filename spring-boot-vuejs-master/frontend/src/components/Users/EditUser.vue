<template>
  <div id="app" class="forma">
    <v-app id="inspire">
      <v-card-text class="previewText">Edit your profile</v-card-text>

      <v-form class="forma1"
              ref="form"
              v-model="valid"
      >
        <v-text-field class="field"
                      v-model="user.firstName"
                      :counter="10"
                      :rules="nameRules"
                      label="First Name"
                      required
        ></v-text-field>

        <v-text-field class="field"
                      v-model="user.lastName"
                      :counter="10"
                      label="Last Name"
                      required
        ></v-text-field>

        <v-text-field class="field"
                      v-model="user.email"
                      readonly="1"
                      label="E-mail"

        ></v-text-field>
        <div class="form-group" id="userNameBtn">
          <div id="div1">
            <label >User Name</label>
            <v-text-field
                    v-model="$store.getters.getUser.username"
                    :rules="administratorRules"
                    outlined
                    dense
                    required
                    disabled="true"
                    id="txtusr"
            />
          </div>
          <div id="div2">
            <v-btn icon class="editbtn" @click="changeUserName">
              <v-icon class="editbtn" color="blue lighten-1">mdi-pencil</v-icon>
            </v-btn>
          </div>
        </div>
        <v-dialog v-model="changeUserNameDialog" width="400px">
          <v-card>
            <v-card-title class="grey darken-2">Change User Name</v-card-title>
            <v-container>
              <ChangeUserName @CloseDialogEvent="closeDialogEvent($event)" />
            </v-container>
            <v-card-actions>
              <v-spacer />

              <v-btn text color="primary" @click="changeUserNameDialog = false">Close</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-checkbox
                v-model="checkbox"
                :rules="[v => !!v || 'You must agree to continue!']"
                label="Do you agree?"
                required
        ></v-checkbox>

        <v-btn
                :disabled="!valid"
                color="success"
                class="mr-4"
                @click="editUser()"
        >
          Submit
        </v-btn>

        <v-btn
                color="error"
                class="mr-4"
                @click="reset"
        >
          Reset Form
        </v-btn>
      </v-form>
      <div class="text-center ma-2">
        <v-snackbar
                v-model="snackbar"
        >
          {{msg}}
          <v-btn
                  @click="snackbar = false"
                  color="pink"
                  text
          >
            Close
          </v-btn>
        </v-snackbar>
      </div>
    </v-app>
  </div>
  </template>
  <style>


    #div1 {
      width: 90%;
    }
    #div2 {
      padding-top: 36px;
    }
    #userNameBtn {
      width: 300px;
      display: flex;
    }
    .forma{
      margin-right: 640px;
    }
    .field{
      width: 600px;
    }
    .previewText{
      margin-top: 50px;
      font-size: 35px;
      text-align: center;
      margin-left: 100px;
    }
    .forma1{
      margin-top:50px;
      margin-left: 300px;
    }
    </style>

<script>
  import api from "../backend-api";
  import ChangeUserName from "./ChangeUserName";
  export default {
    components: {

      ChangeUserName
    },
    name: "EditPatientForm",
    data() {
      return {
        changeUserNameDialog: false,
        msg: '',
        snackbar: false,
        flag: false,
        form: {
          quantityOption: null
        },
        valid: true,
        user: {
          id: '',
          firstName: '',
          lastName: '',
          userName: '',
          email: ''
        },
        nameRules: [
          v => !!v || 'Name is required',
          v => (v && v.length <= 10) || 'Name must be less than 10 characters',
        ],
        select: null,
        checkbox: false
      }
    },
    mounted() {
      api.setAuthentication().defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
      api.getUser(localStorage.getItem('userID')).then(response => {

        this.user.id = response.data.id;
        this.user.userName = response.data.username;
        this.user.lastName = response.data.lastName;
        this.user.firstName = response.data.firstName;
        this.user.email = response.data.email;

      })
    },
    methods: {
      validate() {
        this.$refs.form.validate()
      },
      closeDialogEvent(event) {
        this.msg = event;
        this.changeUserNameDialog = false;
        this.snackbar = true;
      },
      changeUserName() {
        this.changeUserNameDialog = !this.changeUserNameDialog;
      },
      reset() {
        this.$refs.form.reset()
      },
      editUser() {
        console.log(this.user.userName);
        api
                .editPatient(this.user, localStorage.getItem("userName"))
                .then(response => {
                  localStorage.setItem("firstName", response.data.firstName);
                  console.log(response.data.userName);
                  localStorage.setItem("lastName", response.data.lastName);
                  this.$store.commit("setUser", response.data);
                  console.log(response);
                  this.snackbar = true;
                  this.msg = 'Info successfully changed!'
                });
        /*
        api.deleteUser(this.user.username);
        console.log("obrisan user");
        api.createPatient(this.user, "Patient").then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data;
          console.log(response.data)
          if (response.data == "808") {
            this.msg = 'User with same username already exists!';
            this.snackbar = true;
          } else if (response.data == "800") {
            this.msg = 'User successfully added!'
            this.snackbar = true;
            this.$router.push("Home");
          }
          //this.$router.push('home')
        })
                .catch(e => {
                  console.log(e);
                })*/
      }
    },
    computed: {
      state() {
        return Boolean(this.form.quantityOption)
      }
    }
  }
</script>
