<template>
  <div id="app" class="forma">
    <v-app id="inspire">
      <v-card-text class="previewText">Preview of your profile</v-card-text>

      <v-form class="forma1" ref="form">
        <v-text-field class="field" v-model="user.firstName" readonly label="First Name"></v-text-field>
        <v-text-field class="field" v-model="user.lastName" readonly label="First Name"></v-text-field>
        <v-text-field class="field" v-model="user.userName" readonly label="UserName"></v-text-field>

        <v-text-field class="field" v-model="user.email" label="E-mail" readonly></v-text-field>

        <v-text-field class="field" v-model="user.country" readonly label="Country"></v-text-field>
        <v-text-field class="field" v-model="user.city" readonly label="City"></v-text-field>

        <v-text-field class="field" v-model="user.address" readonly label="Address"></v-text-field>

        <v-text-field class="field" v-model="user.phoneNumber" readonly label="Phone number"></v-text-field>
        <v-text-field
          class="field"
          v-model="user.insuranceNumber"
          readonly
          label="Insurance number"
        ></v-text-field>
      </v-form>
      <b-button class="button">Preview your medical record</b-button>
    </v-app>
  </div>
</template>

<style>
.forma {
  margin-right: 600px;
}
.field {
  width: 600px;
}
.button {
  width: 200px;
  margin-left: 550px;
}
.previewText {
  margin-top: 50px;
  font-size: 35px;
  text-align: center;
  margin-left: 100px;
}
.forma1 {
  margin-top: 50px;
  margin-left: 300px;
}
</style>

<script>
import api from "./../backend-api";

export default {
  name: "ShowPatientProfile",
  data() {
    return {
      user: {
        firstName: "",
        lastName: "",
        userName: "",
        email: "",
        country: "",
        city: "",
        address: "",
        phoneNumber: "",
        insuranceNumber: ""
      }
    };
  },
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
      "Bearer " + localStorage.getItem("token");
    api.getUser(localStorage.getItem("userID")).then(response => {
      this.user.userName = response.data.username;
      this.user.lastName = response.data.lastName;
      this.user.firstName = response.data.firstName;
      this.user.email = response.data.email;
      this.user.country = response.data.country;
      this.user.city = response.data.city;
      this.user.address = response.data.address;
      this.user.phoneNumber = response.data.phoneNumber;
      this.user.insuranceNumber = response.data.insuranceNumber;
    });
  },

  methods: {},

  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    }
  }
};
</script>
