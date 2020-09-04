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
            <v-btn
              id="greebtn"
              x-large
              color="success"
              dark
              width="100%"
              @click="showRecord"
            >Preview Medical History</v-btn>
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
    <div class="text-center">
      <v-dialog v-model="dialog2" class="medicalRecordDialog">
        <div>
          <v-card class="medicalRecordDialog">
            <div class="medicalRecordDiv">
              <v-card-title primary-title>Medical Record</v-card-title>
              <v-divider></v-divider>

              <v-text-field
                class="field"
                v-model="medicalRecord.allergies"
                readonly
                label="Allergies"
              ></v-text-field>
              <v-text-field
                class="field"
                v-model="medicalRecord.bloodType"
                readonly
                label="Blood Type"
              ></v-text-field>
              <v-text-field class="field" v-model="medicalRecord.height" readonly label="Height "></v-text-field>
              <v-text-field class="field" v-model="medicalRecord.weight" readonly label="Weight "></v-text-field>
            </div>
            <v-divider vertical></v-divider>
            <div class="dataTable">
              <v-card-title>
                Appointments
                <v-spacer></v-spacer>

                <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
                ></v-text-field>
              </v-card-title>
              <v-data-table :headers="headers" :items="apointments" :search="search" height="554">
                <template v-slot:item="row">
                  <tr>
                    <td>{{row.item.dateAndTime}}</td>
                    <td>{{row.item.doctorFirstName}}</td>
                    <td>{{row.item.doctorLastName}}</td>
                    <td>{{row.item.clinicName}}</td>
                    <td>{{row.item.clinicAddress}}</td>
                    <td>{{row.item.appointmentType}}</td>
                    <td>{{row.item.illness}}</td>
                    <td>{{row.item.reportDescription}}</td>
                  </tr>
                </template>
              </v-data-table>
            </div>
            <v-card-actions>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </div>
        <v-btn color="red" text @click="dialog = false" height="40px">Close</v-btn>
      </v-dialog>
    </div>
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
    ChangePassword,
  },
  dialog2: false,
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
      headers: [
        { text: "Date ", value: "dateAndTime" },
        { text: "Doctor First Name", value: "doctorFirstName" },
        { text: "Doctor Last Name", value: "doctorLastName" },
        { text: "Clinic Name", value: "clinicName" },
        { text: "Clinic Address", value: "clinicAddress" },
        { text: "Appointment type", value: "appointmentType" },
        { text: "Illness", value: "illness" },
        { text: "Appointment report", value: "reportDescription" },
        // { text: 'Diagnosis', value: 'diagnosis' },

        //{ text: 'Medical History', value: 'medicalHistory' },
      ],
      form: {
        quantityOption: null,
      },
      valid: true,
      clinic: {
        name: "",
        address: "",
        administrator: "",
        id: "",
      },
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 20) || "Name must be less than 10 characters",
      ],
      idRules: [
        (v) => !!v || "ID is required",
        (v) => (v && v.length <= 10) || "ID must be less than 10 characters",
      ],
      administratorRules: [
        (v) => !!v || "Administrator is required",
        (v) =>
          (v && v.length <= 12) ||
          "Administrator must be less than 12 characters",
      ],
      emailRules: [
        (v) => !!v || "E-mail is required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ],
      select: null,
      checkbox: false,
      flag: false,
      user: {
        id: null,
        firstName: null,
        lastName: null,
        userName: null,
        email: null,
      },
      apointments: [],
      medicalRecord: {
        allergies: "",
        bloodType: "",
        height: "",
        weight: "",
      },
    };
  },
  mounted() {},
  methods: {
    showRecord() {
      api
        .getMedicalRecordForPatient(this.$props.patientProfile.id)
        .then((response) => {
          console.log(response.data);
          this.medicalRecord = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
      this.dialog2 = true;
      api
        .getAppointmentPreviewHistory(this.$props.patientProfile.id)
        .then((response) => {
          this.apointments = response.data;
          console.log(this.apointments);
        });
    },
  },
  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    },
  },
};
</script>
