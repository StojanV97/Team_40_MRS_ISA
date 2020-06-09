<template>
  <div id="app" class="forma">
    <div>
      <v-form class="form-block" ref="form" v-model="valid">
        <div class="form-group">
          <label for="exampleInputEmail1">First Name</label>
          <v-text-field
            v-model="$store.getters.getUser.firstName"
            :rules="nameRules"
            outlined
            dense
            required
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Last Name</label>
          <v-text-field
            v-model="$store.getters.getUser.lastName"
            :rules="nameRules"
            outlined
            dense
            required
          />
        </div>

        <div class="form-group" id="userNameBtn">
          <div id="div1">
            <label for="exampleInputEmail1">User Name</label>
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
        <div class="form-group">
          <label>Email</label>
          <v-text-field
            v-model="$store.getters.getUser.email"
            :rules="emailRules"
            outlined
            dense
            required
          />
        </div>

        <div class="form-group">
          <label for="exampleInputEmail1">User ID</label>
          <v-text-field
            v-model="$store.getters.getUser.id"
            disabled="true"
            type="number"
            outlined
            dense
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Password:</label>
          <v-btn id="password-btn" @click="changePassword">Change Password</v-btn>
        </div>
        <div id="buttons">
          <v-btn class="btn btn-primary" :disabled="!valid" @click="editUser()">Submit</v-btn>
          <v-btn class="ma-2" outlined color="indigo" @click="reset">Reset</v-btn>
        </div>
      </v-form>
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

    <v-dialog v-model="changePasswordDialog" width="400px">
      <v-card>
        <v-card-title class="grey darken-2">Change Password</v-card-title>
        <v-container>
          <ChangePassword @CloseDialogEvent="closeChangePass($event)" />
        </v-container>
        <v-card-actions>
          <v-spacer />

          <v-btn text color="primary" @click="changePasswordDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{msg}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </div>
</template>

<style scoped>
#div1 {
  width: 90%;
}
#div2 {
  padding-top: 36px;
}
#editbtn {
  margin-top: 100px;
}

#userNameBtn {
  width: 300px;
  display: flex;
}
#buttons {
  margin-top: -30px;
}
#password-btn {
  margin-left: 40px;
}

#exampleInputEmail1 {
  padding-top: 10px;
}

.form-block {
  margin-top: 40px;
  margin-left: 50%;
}
.form-group {
  width: 300px;
  height: 70px;
}
</style>

<script>
import api from "../backend-api";
import RoomConfig from "../Utility/RoomConfig";
import ChangeUserName from "../Users/ChangeUserName";
import ChangePassword from "../Users/ChangePassword";
export default {
  components: {
    RoomConfig,
    ChangeUserName,
    ChangePassword
  },
  name: "ClinicEditForm",
  props: ["clinicEdit"],
  data() {
    return {
      changePasswordDialog: false,
      changeUserNameDialog: false,
      dialogEditRooms: false,
      oldID: null,
      valid: true,
      msg: "",
      snackbar: false,
      flag: false,
      form: {
        quantityOption: null
      },
      valid: true,
      clinic: {
        name: "",
        address: "",
        administrator: "",
        id: ""
      },
      nameRules: [
        v => !!v || "Name is required",
        v => (v && v.length <= 20) || "Name must be less than 10 characters"
      ],
      idRules: [
        v => !!v || "ID is required",
        v => (v && v.length <= 10) || "ID must be less than 10 characters"
      ],
      administratorRules: [
        v => !!v || "Administrator is required",
        v =>
          (v && v.length <= 12) ||
          "Administrator must be less than 12 characters"
      ],
      emailRules: [
        v => !!v || "E-mail is required",
        v => /.+@.+\..+/.test(v) || "E-mail must be valid"
      ],
      select: null,
      checkbox: false,
      flag: false,
      user: {
        id: null,
        firstName: null,
        lastName: null,
        userName: null,
        email: null
      }
    };
  },
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
      "Bearer " + localStorage.getItem("token");
  },

  methods: {
    editUser() {
      this.user.id = this.$store.getters.getUser.id;
      this.user.firstName = this.$store.getters.getUser.firstName;
      this.user.lastName = this.$store.getters.getUser.lastName;
      this.user.email = this.$store.getters.getUser.email;
      this.user.userName = this.$store.getters.getUser.username;
      api
        .editAdmin(this.user, localStorage.getItem("userName"))
        .then(response => {
          localStorage.setItem("firstName", response.data.firstName);
          localStorage.setItem("lastName", response.data.lastName);
          this.$store.commit("setUser", response.data);
          this.snackbar = true;
          this.msg = "Successufully changed!";
        });
    },

    closeDialogEvent(event) {
      this.msg = event;
      this.changeUserNameDialog = false;
      this.snackbar = true;
    },
    closeChangePass() {
      this.msg = event;
      this.changePasswordDialog = false;
      this.snackbar = true;
    },
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    changeUserName() {
      this.changeUserNameDialog = !this.changeUserNameDialog;
    },
    changePassword() {
      this.changePasswordDialog = !this.changePasswordDialog;
    }
  },

  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    }
  }
};
</script>

