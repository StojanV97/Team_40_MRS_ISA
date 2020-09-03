<template>
  <div id="main">
    <div id="table">
      <v-data-table
        dark
        dense
        :headers="headers"
        :items="appointements"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        hide-default-footer
        class="elevation-1"
        @page-count="pageCount = $event"
      ></v-data-table>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
        <v-text-field
          :value="itemsPerPage"
          label="Items per page"
          type="number"
          min="-1"
          max="15"
          @input="itemsPerPage = parseInt($event, 10)"
        ></v-text-field>
      </div>
    </div>
    <v-row align="center">
      <v-form id="forma" ref="form" v-model="valid" :lazy-validation="lazy">
        <v-dialog ref="dialog" v-model="modal" :return-value.sync="date" persistent width="290px">
          <template v-slot:activator="{ on, attrs }">
            <v-text-field v-model="date" label="Pick a date" readonly v-bind="attrs" v-on="on"></v-text-field>
          </template>
          <v-date-picker v-model="appointement.dateAndTime" scrollable>
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="modal = false">Cancel</v-btn>
            <v-btn
              text
              color="primary"
              @click="$refs.dialog.save(appointement.dateAndTime);getRooms()"
            >OK</v-btn>
          </v-date-picker>
        </v-dialog>

        <v-select
          v-model="appointement.roomID"
          :items="itemsRooms"
          :rules="[v => !!v || 'Room is required']"
          label="Room"
          required
          @change="showTimeMethod"
        ></v-select>

        <v-select
          v-model="eTerm"
          :items="eTime"
          :rules="[v => !!v || 'eTime is required']"
          label="Time"
          required
          v-if="showTime"
        ></v-select>

        <v-select
          v-model="appointement.doctorID"
          :items="itemsDoctors"
          :rules="[v => !!v || 'Doctor is required']"
          label="Doctor"
          required
        ></v-select>

        <v-select
          v-model="select"
          :items="items"
          :rules="[v => !!v || 'Time is required']"
          label="Duration"
          required
        ></v-select>

        <v-select
          v-model="appointement.type"
          :items="itemsType"
          :rules="[v => !!v || 'Type is required']"
          label="Type"
          required
        ></v-select>

        <v-text-field label="Price" readonly v-bind="attrs" value="$50"></v-text-field>

        <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">Validate</v-btn>

        <v-btn color="blue" class="mr-4" @click="reset">Reset Form</v-btn>
      </v-form>
    </v-row>
    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{text}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </div>
</template>

<script>
import api from "../backend-api";

export default {
  data: () => ({
    showTime: false,
    snackbar: false,
    text: "",
    date: new Date().toISOString().substr(0, 10),
    modal: false,
    menu2: false,
    valid: true,
    name: "",
    nameRules: [
      (v) => !!v || "Name is required",
      (v) => (v && v.length <= 10) || "Name must be less than 10 characters",
    ],
    email: "",
    emailRules: [
      (v) => !!v || "E-mail is required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
    eTerm: null,
    eTime: [],
    select: null,
    items: ["30"],
    itemsRooms: [],
    itemsType: ["Examination"],
    checkbox: false,
    lazy: false,
    selectDoctors: false,
    itemsDoctors: [],
    appointement: {
      dateAndTime: false,
      type: null,
      roomID: null,
      doctorID: null,
      clinicID: null,
      patientID: 0,
    },
    page: 1,
    pageCount: 0,
    itemsPerPage: 10,

    headers: [
      { text: "Date and Time", value: "dateAndTime" },
      { text: "Room", value: "roomID" },
      { text: "Doctor", value: "doctorID" },
    ],
    appointements: [],
  }),
  mounted() {
    this.selectType = "Examination";
    api.getAllPredefinedAppointements().then((response) => {
      this.appointements = response.data;
      console.log(this.appointements);
    });
  },
  methods: {
    showTimeMethod() {
      this.showTime = true;
      api
        .getFreeTermsForRoom(
          this.appointement.roomID,
          this.appointement.dateAndTime
        )
        .then((response) => {
          this.eTime = response.data;
        });
    },
    validate() {
      this.appointement.clinicID = parseInt(localStorage.getItem("clinicID"));
      const array = this.appointement.doctorID.split("-");
      this.appointement.doctorID = parseInt(array[1]);
      api
        .createPredefinedAppointement(
          this.appointement.clinicID,
          this.appointement.roomID,
          this.appointement.doctorID,
          this.appointement.patientID,
          this.appointement.type,
          this.appointement.dateAndTime + " " + this.eTerm
        )
        .then((response) => {
          if (response.status == "OK") {
            this.text = "Predefined appointement created!";
            this.snackbar = true;
          } else {
            this.text = "Appointemnet already reserved!!";
            this.snackbar = true;
          }

          api.getAllPredefinedAppointements().then((response) => {
            this.appointements = response.data;
            console.log(response.data);
          });
        });
    },
    reset() {
      this.$refs.form.reset();
      this;
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    getRooms() {
      this.itemsRooms = [];
      api
        .getFirstAvailableDates(localStorage.getItem("clinicID"), this.date)
        .then((response) => {
          for (const index in response.data) {
            if (response.data[index].roomName === "Examination") {
              this.itemsRooms.push(response.data[index].roomID);
            }
          }
        })
        .catch((e) => {});

      api
        .getClinicDoctorsForDate(this.date, localStorage.getItem("clinicID"))
        .then((response) => {
          for (const index in response.data) {
            this.itemsDoctors.push(
              response.data[index].lastName +
                " " +
                response.data[index].firstName +
                " - " +
                response.data[index].id
            );
          }
        });
    },
  },
};
</script>
<style >
#table {
  margin-top: 8%;
}
#main {
  display: flex;
  margin-left: 20%;
}
#forma {
  margin-left: 20%;
  margin-top: 5%;
}
</style>