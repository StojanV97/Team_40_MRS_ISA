<template>
  <div>
    <v-form ref="form" v-model="valid" lazy-validation>
      <label id="labela">After changing username you will be redirected to login page!</label>
      <v-text-field
        v-model="$store.getters.getUser.username"
        :counter="20"
        :rules="nameRules"
        label="UserName"
        required
      ></v-text-field>
      <v-btn
        id="btnn"
        :disabled="!valid"
        color="success"
        class="mr-4"
        @click="changeUserName"
      >Change</v-btn>
    </v-form>
    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{msg}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </div>
</template>

<script>
import api from "../backend-api";
export default {
  data: () => ({
    msg: "",
    snackbar: false,
    valid: true,
    name: "",
    nameRules: [
      v => !!v || "UserName is required",
      v => (v && v.length <= 20) || "Name must be less than 10 characters"
    ]
  }),

  methods: {
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    changeUserName() {
      if (
        this.$store.getters.getUser.username ===
        localStorage.getItem("userName")
      ) {
        this.$emit("CloseDialogEvent", "Nothing Changed!");
      } else {
        api
          .changeUserName(
            this.$store.getters.getUser.username,
            localStorage.getItem("userName")
          )
          .then(response => {
            this.$emit("CloseDialogEvent", "Successufully Changed!");
            setTimeout(() => this.$router.push({ path: "/" }), 2000);
          })
          .catch(e => {
            console.log("Error in ChangingUserName!");
          });
      }
    }
  }
};
</script>
<style scoped >
#labela {
  margin-top: 10px;
}
#btnn {
  margin-top: 10px;
  margin-bottom: -20%;
}
</style>