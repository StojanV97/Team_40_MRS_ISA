<template>
  <div>
    <v-form ref="form" v-model="valid" lazy-validation id="amrof">
      <v-text-field
        v-model="$store.getters.getUser.firstName"
        :counter="10"
        :rules="nameRules"
        label="Name"
        required
        disabled
      ></v-text-field>
      <v-text-field
        v-model="$store.getters.getUser.lastName"
        :counter="10"
        :rules="nameRules"
        label="Last Name"
        required
        disabled
      ></v-text-field>
      <v-text-field
        v-model="$store.getters.getUser.email"
        :rules="emailRules"
        label="E-mail"
        required
        disabled
      ></v-text-field>
      <v-text-field v-model="$store.getters.getUser.id" label="ID" required disabled></v-text-field>
      <v-select
        v-model="select"
        :items="items"
        :rules="[v => !!v || 'Item is required']"
        label="Item"
        required
      ></v-select>
      <v-text-field
        type="date"
        :min="nowDate"
        v-model="daysOffRequest.dateFrom"
        label="Date from"
        required
      ></v-text-field>
      <v-text-field
        type="date"
        :min="nowDate"
        v-model="daysOffRequest.dateTo"
        label="Date To"
        required
      ></v-text-field>
      <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">Submit</v-btn>
    </v-form>
    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{ text }}
        <template v-slot:action="{ attrs }">
          <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
        </template>
      </v-snackbar>
    </div>
  </div>
</template>

<script>
import api from "../backend-api";
export default {
  data: () => ({
    nowDate: new Date().toISOString().slice(0, 10),
    snackbar: false,
    text: "",
    valid: true,
    name: "",
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 10) || "Name must be less than 10 characters"
    ],
    email: "",
    emailRules: [
      v => !!v || "E-mail is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    select: null,
    items: ["Days off", "Vacation"],
    checkbox: false,
    daysOffRequest: {
      userID: "",
      firstName: "",
      lastname: "",
      dateFrom: "",
      dateTo: "",
      type: "",
      email: ""
    }
  }),
  mounted() {
    this.dateNow = new Date();
  },

  methods: {
    validate() {
      this.daysOffRequest.email = this.$store.getters.getUser.email;
      this.daysOffRequest.type = this.select;
      this.daysOffRequest.firstName = this.$store.getters.getUser.firstName;
      this.daysOffRequest.lastname = this.$store.getters.getUser.lastName;
      this.daysOffRequest.userID = this.$store.getters.getUser.id;
      console.log(this.select);
      if (this.dateFrom == "undefined" || this.dateTo == "undefined") {
        this.text = "All fields must be entered!";
        this.snackbar = true;
      } else {
        api
          .createDaysOffRequest(this.daysOffRequest)
          .then(response => {
            this.text = "Request sent!";
            this.snackbar = true;
          })
          .catch(e => {});
      }
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  }
};
</script>
<style scoped>
</style>