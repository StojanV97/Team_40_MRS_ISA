<template>
  <div id="app" class="forma">
    <div>
      <v-form class="form-block" ref="form" v-model="valid">
        <div class="form-group">
          <label for="exampleInputEmail1">First Name</label>
          <v-text-field
            v-model="$props.doctorEdit.firstName"
            :rules="nameRules"
            outlined
            dense
            required
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Last Name</label>
          <v-text-field
            v-model="$props.doctorEdit.lastName"
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
              v-model="$props.doctorEdit.username"
              :rules="administratorRules"
              outlined
              dense
              required
              disabled="true"
              id="txtusr"
            />
          </div>
          <div id="div2">
            <v-btn icon class="editbtn" @click="changeUserName2">
              <v-icon class="editbtn" color="blue lighten-1">mdi-pencil</v-icon>
            </v-btn>
          </div>
        </div>
        <div class="form-group">
          <label>Email</label>
          <v-text-field
            v-model="$props.doctorEdit.email"
            :rules="emailRules"
            outlined
            dense
            required
          />
        </div>

        <div class="form-group">
          <label for="exampleInputEmail1">User ID</label>
          <v-text-field v-model="$props.doctorEdit.id" disabled type="number" outlined dense />
        </div>
        <div id="buttons">
          <v-btn class="btn btn-primary" :disabled="!valid" @click="editAdmin()">Submit</v-btn>
        </div>
      </v-form>
    </div>
    <v-dialog v-model="changeUserNameDialog" width="400px">
      <v-card>
        <v-card-title class="grey darken-2">Change User Name</v-card-title>
        <v-container>
          <div>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-text-field
                v-model="oldUserName"
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
        </v-container>
        <v-card-actions>
          <v-spacer />
          <v-btn text color="primary" @click="changeUserNameDialog = false">Close</v-btn>
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
  props: ["doctorEdit"],
  data() {
    return {
      oldUserName: "",
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
      sifra: "",
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
    closeDialogEvent(event) {
      this.msg = event;
      this.changeUserNameDialog = false;
      this.snackbar = true;
    },
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    editAdmin() {
      this.user.id = this.$props.doctorEdit.id;
      this.user.firstName = this.$props.doctorEdit.firstName;
      this.user.lastName = this.$props.doctorEdit.lastName;
      this.user.email = this.$props.doctorEdit.email;
      this.user.userName = this.$props.doctorEdit.username;
      api
        .editAdmin(this.user, this.$props.doctorEdit.username)
        .then(response => {
          this.msg = "Changed!";
          this.snackbar = true;
          console.log(response);
        });
    },
    changeUserName2() {
      this.changeUserNameDialog = !this.changeUserNameDialog;
    },
    changePassword() {
      this.changePasswordDialog = !this.changePasswordDialog;
    },

    changeUserName() {
      console.log(this.oldUserName);

      if (this.$props.doctorEdit.username == this.oldUserName) {
        this.msg = "Nothing Changed!";
        this.snackbar = true;
      } else if (this.oldUserName == "") {
        this.msg = "Empty userName!";
        this.snackbar = true;
      } else {
        api
          .changeUserName(this.oldUserName, this.$props.doctorEdit.username)
          .then(response => {
            this.msg = "Username Changed!";
            this.snackbar = true;
            this.$props.doctorEdit.username = this.oldUserName;
            this.changePasswordDialog = false;
          })
          .catch(e => {
            console.log("Error in ChangingUserName!");
          });
      }
    }
  },
  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    }
  }
};
</script>

<style scoped>
#labela {
  margin-top: 10px;
}
#btnn {
  margin-top: 10px;
  margin-bottom: -20%;
}
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
  margin-top: 30px;
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
