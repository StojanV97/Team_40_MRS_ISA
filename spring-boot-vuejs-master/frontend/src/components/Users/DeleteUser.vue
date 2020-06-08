<template>
  <div id="app" class="forma">
    <v-app id="inspire">
      <v-row align="center">
        <v-row justify="space-around"></v-row>
        <v-form ref="form" v-model="valid">
          <v-text-field
            v-model="user.userName"
            :counter="10"
            :rules="nameRules"
            label="UserName"
            required
          ></v-text-field>

          <v-btn :disabled="!valid" color="success" class="mr-4" @click="deleteStafMember()">Delete</v-btn>

          <v-btn color="error" class="mr-4" @click="reset">Reset</v-btn>
        </v-form>
      </v-row>
      <div class="text-center ma-2">
        <v-snackbar v-model="snackbar">
          {{msg}}
          <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
        </v-snackbar>
      </div>
    </v-app>
  </div>
</template>

<script>
import api from "../backend-api";
import router from "../../router";

export default {
  name: "DeleteUser",
  data() {
    return {
      msg: "",
      snackbar: false,
      valid: true,
      user: {
        userName: ""
      },

      nameRules: [
        v => !!v || "Name is required",
        v => (v && v.length <= 10) || "Name must be less than 10 characters"
      ],
      retrievedUser: {},
      fullResponse: {
        config: {
          foo: "",
          bar: ""
        }
      }
    };
  },
  methods: {
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    deleteStafMember() {
      api
        .deleteUser(this.user.userName)
        .then(response => {
          // JSON responses are automatically parsed.
          this.retrievedUser = response.data;
          console.log(response);
          if (response.data == "808") {
            this.msg = "User with same username does not exist!";
            this.snackbar = true;
            this.user.userName = "";
          } else {
            this.msg = "User deleted";
            this.snackbar = true;
            this.user.userName = "";
          }
        })
        .catch(e => {
          console.log(e);
          this.snackbar = true;
          this.msg = "Catch executed!";
        });
    }
  }
};
</script>

<style scoped>
</style>