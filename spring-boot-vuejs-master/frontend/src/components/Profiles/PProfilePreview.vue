<template>
  <div id="app" class="forma">
    <div id="divfirst">
      <div class="inerdiv">
        <div class="form-group">
          <label for="exampleInputEmail1">First Name</label>
          <v-text-field
            v-model="$props.patientProfile.firstName"
            :rules="nameRules"
            outlined
            disabled="true"
            dense
            required
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Last Name</label>
          <v-text-field
            v-model="$props.patientProfile.lastName"
            :rules="nameRules"
            outlined
            disabled="true"
            dense
            required
          />
        </div>

        <div class="form-group">
          <label for="exampleInputEmail1">User Name</label>
          <v-text-field
            v-model="$props.patientProfile.username"
            :rules="administratorRules"
            outlined
            dense
            required
            disabled="true"
            id="txtusr"
          />
        </div>
        <div class="form-group">
          <label>Email</label>
          <v-text-field
            v-model="$props.patientProfile.email"
            :rules="emailRules"
            outlined
            disabled="true"
            dense
            required
          />
        </div>

        <div class="form-group">
          <label for="exampleInputEmail1">User ID</label>
          <v-text-field
            v-model="$props.patientProfile.id"
            disabled="true"
            type="number"
            outlined
            dense
          />
        </div>
      </div>

      <div class="inerdiv">
        <div class="form-group">
          <label for="exampleInputEmail1">Country</label>
          <v-text-field
            v-model="$props.patientProfile.firstName"
            :rules="nameRules"
            outlined
            dense
            disabled="true"
            required
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">City</label>
          <v-text-field
            v-model="$props.patientProfile.lastName"
            :rules="nameRules"
            outlined
            disabled="true"
            dense
            required
          />
        </div>

        <div class="form-group">
          <label for="exampleInputEmail1">Adress</label>
          <v-text-field
            v-model="$props.patientProfile.username"
            :rules="administratorRules"
            outlined
            dense
            required
            disabled="true"
            id="txtusr"
          />
        </div>
        <div class="form-group">
          <label>Phone Number</label>
          <v-text-field
            v-model="$props.patientProfile.email"
            :rules="emailRules"
            outlined
            disabled="true"
            dense
            required
          />
        </div>
        <div>
          <div class="my-2">
            <v-btn id="greebtn" x-large color="success" dark width="100%">Preview Medical History</v-btn>
          </div>
        </div>
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

    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{msg}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </div>
</template>


<style scoped>
#greebtn {
  margin-top: 7%;
}

.inerdiv {
  margin-right: 40%;
}

#divfirst {
  display: flex;
  padding: 10%;
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
  props: ["patientProfile"],
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
  methods: {},
  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    }
  }
};
</script>
