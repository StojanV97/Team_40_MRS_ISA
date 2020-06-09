

<template>
  <div>
    <v-form ref="form" v-model="valid" lazy-validation id="pass-form">
      <label id="labela">After changing password you will be redirected to login page!</label>
      <v-text-field
        v-model="passwordChanger.oldPassword"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="show1 ? 'text' : 'password'"
        name="input-10-1"
        label="Current Password"
        hint="At least 8 characters"
        counter
        required
        @click:append="show1 = !show1"
      ></v-text-field>
      <v-text-field
        v-model="passwordChanger.newPassword"
        :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="show2 ? 'text' : 'password'"
        name="input-10-1"
        label="New Password"
        hint="At least 8 characters"
        counter
        @click:append="show2 = !show2"
        required
      ></v-text-field>
      <v-text-field
        v-model="confirmPassword"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="show3 ? 'text' : 'password'"
        name="input-10-1"
        label="Confirm Password"
        hint="At least 8 characters"
        counter
        @click:append="show3 = !show3"
        required
      ></v-text-field>
      <v-btn id="btnn" :disabled="!valid" color="success" class="mr-4" @click="changePass">Change</v-btn>
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
import ChangePassword from "../Users/ChangePassword";
import api from "../backend-api";
export default {
  components: {
    ChangePassword
  },
  data: () => ({
    msg: "",
    snackbar: false,
    valid: true,
    show1: false,
    show2: false,
    show3: false,
    passwordChanger: {
      oldPassword: "",
      newPassword: ""
    },
    confirmPassword: "",
    rules: {
      required: value => !!value || "Required.",
      min: v => v.length >= 8 || "Min 8 characters"
    }
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
    changePass() {
      if (this.passwordChanger.newPassword === this.confirmPassword) {
        if (
          this.passwordChanger.newPassword === this.passwordChanger.oldPassword
        ) {
          this.msg = "You cannot use same password!";
          this.snackbar = true;
        } else {
          api
            .changePassword(this.passwordChanger)
            .then(response => {
              this.$router.push("/");
              console.log(response);
            })
            .catch(e => {
              if (e.response.status === 401) {
                this.msg = "Current password did not match!";
                this.snackbar = true;
              }
            });
        }
      } else {
        this.msg = "Passwords do not  match!";
        this.snackbar = true;
      }
    }
  }
};
</script>

<style>
#changePass {
  margin-top: 100px;
  margin-left: 400px;
}

#pass-form {
  max-width: 400px;
  margin-top: 10%;
  margin-left: 35%;
}
</style>