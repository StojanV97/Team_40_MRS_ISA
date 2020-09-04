<template>
  <v-app id="inspire">
    <calendar id="calendar" v-if="this.showCalendar"></calendar>
    <DoctorProfile v-if="this.showProfile"></DoctorProfile>
    <AppointmentReportForm v-if="this.showAppointmentReport"></AppointmentReportForm>
    <ScheduleExemination
      @reload="reloadAPT"
      v-bind:appoitements="currentAppointements"
      v-if="this.showExaminationScheduling"
    ></ScheduleExemination>
    <v-card v-if="this.showPatients">
      <v-card-title>
        Patients
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="patients" :search="search" height="554">
        <template v-slot:item="row">
          <tr>
            <td>{{row.item.id}}</td>
            <td>{{row.item.firstName}}</td>
            <td>{{row.item.lastName}}</td>
            <td>{{row.item.email}}</td>
            <td>
              <v-btn
                v-if="$store.getters.getUser.listOfPatients.indexOf(row.item.id) != -1"
                @click="showHistoryDialog(row.item.id)"
                class="my-2"
                color="blue"
              >Show</v-btn>
            </td>
          </tr>
        </template>
      </v-data-table>
    </v-card>

    <v-navigation-drawer v-model="drawer" :clipped="$vuetify.breakpoint.lgAndUp" app>
      <v-list dense>
        <template v-for="item in items">
          <v-row v-if="item.heading" :key="item.heading" align="center">
            <v-col cols="6">
              <v-subheader v-if="item.heading">{{ item.heading }}</v-subheader>
            </v-col>
            <v-col cols="6" class="text-center">
              <a href="#!" class="body-2 black--text">EDIT</a>
            </v-col>
          </v-row>
          <v-list-group
            v-else-if="item.children"
            :key="item.text"
            v-model="item.model"
            :prepend-icon="item.model ? item.icon : item['icon-alt']"
            append-icon
          >
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>{{ item.text }}</v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item v-for="(child, i) in item.children" :key="i" link>
              <v-list-item-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content @click="getOption(item.children[i].text)">
                <v-list-item-title>{{ child.text }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
          <v-list-item v-else :key="item.text" link>
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content @click="getOption(item.text)">
              <v-list-item-title>{{ item.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar app clipped-left>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Dr. {{this.name}} {{this.lastName}}</v-toolbar-title>
      <v-spacer></v-spacer>
      <label for="signout">Sign Out</label>
      <v-btn id="signout" icon @click="signOut">
        <v-icon>mdi-arrow-bottom-right-thick</v-icon>
      </v-btn>
    </v-app-bar>

    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col class="shrink">
            <v-tooltip right></v-tooltip>
          </v-col>
        </v-row>
      </v-container>
    </v-content>

    <v-dialog id="medicalChartDialog" v-model="dialog" width="800px">
      <v-card>
        <v-container>
          <PatientProfile v-bind:patientProfile="patientProfile" />
        </v-container>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="dialog = false">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog id="daysOffDialog" v-model="dialogDaysOff" width="800px">
      <v-card>
        <v-card-actions>
          <v-content>
            <RequestDaysOff id="dysof" />
          </v-content>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import api from "../backend-api";
import Calendar from "./Calendar";
import DoctorProfile from "../Profiles/MedicalStaffProfile";
import ScheduleExemination from "../Scheduling/ScheduleExemination";
import PatientProfile from "../Profiles/PProfilePreview";
import RequestDaysOff from "../Utility/RequestDaysOff";
import AppointmentReportForm from "../Utility/AppointmentReportForm";

export default {
  components: {
    Calendar,
    DoctorProfile,
    ScheduleExemination,
    PatientProfile,
    RequestDaysOff,
    AppointmentReportForm
  },
  props: {
    source: String,
  },
  data: () => ({
    showProfile: false,
    showCalendar: false,
    showAppointmentReport: false,
    dialog: false,
    showExaminationScheduling: false,
    dialogDaysOff: false,
    drawer: null,
    showPatients: false,
    items: [
      { icon: "mdi-contacts", text: "List of patients" },
      { icon: "mdi-history", text: "Scheduled examination" },
      { icon: "mdi-file-document", text: "Appointment report" },
      { icon: "mdi-history", text: "Working calendar" },
      { icon: "mdi-iframe-parentheses", text: "Request days off" },
      { icon: "mdi-account-cog", text: "Profile" },
      {
        icon: "mdi-cog",
        "icon-alt": "mdi-cog",
        text: "Settings",
        model: false,
        children: [{ text: "Dark mode On/Off" }],
      },
      { icon: "mdi-help-circle", text: "Help" },
    ],
    search: "",
    headers: [
      {
        text: "Id Number",
        align: "start",
        sortable: false,
        value: "id",
      },
      { text: "First Name", value: "firstName" },
      { text: "Last Name", value: "lastName" },
      { text: "Email", value: "email" },
      { text: "Medical History", value: "medicalHistory" },
    ],
    patients: [],
    name: "",
    lastName: "",
    patientProfile: null,
    currentAppointements: null,
  }),
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
      "Bearer " + localStorage.getItem("token");
    this.name = localStorage.getItem("firstName");
    this.lastName = localStorage.getItem("lastName");

    api
      .getPatients()
      .then((response) => {
        this.patients = response.data;
        console.log(this.patients);
      })
      .catch((e) => {
        console.log(e);
      });

    api
      .getCurretExaminations(localStorage.getItem("userID"))
      .then((response) => {
        console.log("sadasd");

        this.currentAppointements = response.data;
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  },
  methods: {
    reloadAPT() {
      api
        .getCurretExaminations(localStorage.getItem("userID"))
        .then((response) => {
          console.log("sadasd");

          this.currentAppointements = response.data;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getOption(text) {
      if (text === "Dark mode On/Off") {
        this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
        this.calendar = false;
      } else if (text === "Working calendar") {
        this.showCalendar = true;
        this.showProfile = false;
        this.showPatients = false;
        this.showExaminationScheduling = false;
        this.showAppointmentReport = false;
      } else if (text === "List of patients") {
        this.showPatients = true;
        this.showProfile = false;
        this.showCalendar = false;
        this.showExaminationScheduling = false;
        this.showAppointmentReport = false;
      } else if (text === "Profile") {
        this.showPatients = false;
        this.showCalendar = false;
        this.showProfile = true;
        this.showExaminationScheduling = false;
        this.showAppointmentReport = false;
      } else if (text === "Request days off") {
        this.dialogDaysOff = true;
      } else if (text === "Scheduled examination") {
        this.showProfile = false;
        this.showCalendar = false;
        this.showPatients = false;
        this.showExaminationScheduling = true;
        this.showAppointmentReport = false;
      } else if (text === "Appointment report") {
        this.showProfile = false;
        this.showCalendar = false;
        this.showPatients = false;
        this.showExaminationScheduling = false;
        this.showAppointmentReport = true;
      } else {
        this.showExaminationScheduling = false;
        this.showProfile = false;
        this.showCalendar = false;
        this.showPatients = false;
        this.showAppointmentReport = false;
      }
    },
    showHistoryDialog(id) {
      api.getUser(id).then((response) => {
        this.patientProfile = response.data;
      });
      console.log(this.patientProfile);
      this.dialog = true;
    },
    signOut() {
      this.$router.push("/");
    },
  },
  created() {
    this.$vuetify.theme.dark = false;
  },
};
</script>

<style>
body {
  overflow: hidden;
  height: 100vh;
}

#calendar {
  margin-top: -10px;
}
#dysof {
  margin-right: 10%;
  margin-left: -30%;
  margin-bottom: 10%;
}
</style>