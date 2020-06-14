<template>
  <v-app id="inspire">
    <ClinicAdminProfile v-if="editAdminProfile" />
    <DaysOffRequests v-bind:req="req" v-if="reqtrue" />
    <ClinicEditForm
      v-if="this.editClinicProfile"
      @editRoomsEvent="this.openEditRooms"
      @editDoctorsEvent="this.openEditDoctors"
    />
    <ExaminationRequests @goBack="catchExamGoBack" v-if="examinationRequests" />
    <OperationRequests @goBack="catchExamGoBack" v-if="operationRequests"/>
    <RoomConfig @goBack="this.openClinicProfile" v-if="this.roomConfig" />
    <EditDoctors @goBack="this.openClinicProfile" v-if="editDoctors" />
    <v-navigation-drawer v-model="drawer" :clipped="$vuetify.breakpoint.lgAndUp" app>
      <v-list dense :disabled="disableMenu">
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
      <v-toolbar-title>Admin: {{this.name}} {{this.lastName}}</v-toolbar-title>
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
        <v-card-actions>
          <v-btn text color="primary">More</v-btn>
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="dialog = false">Cancel</v-btn>
          <v-btn text @click="dialog = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog id="daysOffDialog" v-model="dialogDaysOff" width="800px">
      <v-card>
        <v-card-actions>
          <v-btn text color="primary">More</v-btn>
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="dialogDaysOff = false">Cancel</v-btn>
          <v-btn text @click="dialogDaysOff = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import api from "../backend-api";
import ClinicEditForm from "../Utility/ClinicEditForm";
import RoomConfig from "../Utility/RoomConfiguration";
import EditDoctors from "../Utility/EditDoctors";
import ClinicAdminProfile from "../Profiles/ClinidAdminProfile";
import ExaminationRequests from "../Utility/ExaminationRequests";
import OperationRequests from "../Utility/OperationRequests";
import DaysOffRequests from "../Utility/DaysOffRequests";

export default {
  components: {
    OperationRequests,
    ClinicEditForm,
    RoomConfig,
    EditDoctors,
    ClinicAdminProfile,
    ExaminationRequests,
    DaysOffRequests
  },
  props: {
    source: String
  },
  data: () => ({
    disableMenu: false,
    showProfile: false,
    drawer: null,
    examinationRequests: false,
    operationRequests: false,
    editClinicProfile: false,
    items: [
      {
        icon: "mdi-arrow-down",
        text: "Edit clinic profile",
        "icon-alt": "mdi-home",
        text: "Clinic",
        model: false,
        children: [
          { icon: "mdi-alpha-i", text: "Edit Clinic info" },
          { icon: "mdi-roman-numeral-2", text: "Price Book" }
        ]
      },
      { icon: "mdi-history", text: "Examination requests" },
      { icon: "mdi-history", text: "Operation requests" },
      { icon: "mdi-history", text: "Days off requests" },
      {
        icon: "mdi-file-chart-outline",
        "icon-alt": "mdi-file-chart-outline",
        text: "Work report",
        model: false,
        children: [
          { icon: "mdi-alpha-i", text: "Average clinic rating" },
          { icon: "mdi-roman-numeral-2", text: "Average ratingfor doctors" },
          { icon: "mdi-roman-numeral-3", text: "Examination gra ph" },
          { icon: "mdi-roman-numeral-4", text: "Income" }
        ]
      },
      {
        icon: "mdi-arrow-down",
        "icon-alt": "mdi-pencil-box-multiple",
        text: "Edits",
        model: false,
        children: [{ icon: "mdi-roman-numeral-1", text: "Edit examinations" }]
      },
      { icon: "mdi-account-cog", text: "Profile" },
      {
        icon: "mdi-arrow-down",
        "icon-alt": "mdi-cog",
        text: "Settings",
        model: false,
        children: [{ icon: "mdi-alpha-i", text: "Dark mode On/Off" }]
      },
      { icon: "mdi-help-circle", text: "Help" }
    ],
    search: "",
    req: [],
    reqtrue: false,
    headers: [
      {
        text: "Id Number",
        align: "start",
        sortable: false,
        value: "id"
      },
      { text: "First Name", value: "firstName" },
      { text: "Last Name", value: "lastName" },
      { text: "Email", value: "email" },
      { text: "Medical History", value: "medicalHistory" }
    ],
    patients: [],
    name: "",
    lastName: "",
    roomConfig: false,
    editDoctors: false,
    editAdminProfile: false
  }),
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
      "Bearer " + localStorage.getItem("token");
    this.name = localStorage.getItem("firstName");
    this.lastName = localStorage.getItem("lastName");
    console.log(localStorage.getItem("clinicID"));
    api
      .getClinic(localStorage.getItem("clinicID"))
      .then(response => {
        this.$store.commit("setClinic", response.data);
      })
      .catch(error => {
        console.log();
      });
  },
  methods: {
    openEditDoctors() {
      this.disableMenu = true;
      this.editDoctors = true;
      this.roomConfig = false;
      this.editClinicProfile = false;
    },
    openClinicProfile() {
      this.disableMenu = false;
      this.editDoctors = false;
      this.roomConfig = false;
      this.editClinicProfile = true;
    },
    openEditRooms() {
      this.disableMenu = true;
      this.editDoctors = false;
      this.editClinicProfile = false;
      this.roomConfig = true;
    },
    getOption(text) {
      if (text === "Dark mode On/Off") {
        this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
      } else if (text === "Examination requests") {
        this.examinationRequests = true;
        this.operationRequests = false;
        this.roomConfig = false;
        this.editClinicProfile = false;
        this.editAdminProfile = false;
      } else if (text === "Operation requests") {
        this.examinationRequests = false;
        this.operationRequests = true;
        this.roomConfig = false;
        this.editClinicProfile = false;
        this.editAdminProfile = false;
        this.reqtrue = false;
      } else if (text === "Edit Clinic info") {
        this.examinationRequests = false;
        this.operationRequests = false;
        this.roomConfig = false;
        this.editClinicProfile = true;
        this.editAdminProfile = false;
      } else if (text === "Profile") {
        this.examinationRequests = false;
        this.operationRequests = false;
        this.editAdminProfile = true;
        this.editClinicProfile = false;
        this.roomConfig = false;
      } else if (text === "Days off requests") {
        api
          .getDaysoffRequests(localStorage.getItem("doctorID"))
          .then(response => {
            console.log(response.data);
            this.req = response.data;
            this.reqtrue = true;
            this.examinationRequests = false;
            this.editAdminProfile = false;
            this.editClinicProfile = false;
            this.roomConfig = false;
          })
          .catch(e => {});
      } else if (text === "Schedule examination") {
      } else {
      }
    },
    showHistoryDialog() {
      this.dialog = true;
    },
    signOut() {
      this.$router.push("/");
    }
  },
  created() {
    this.$vuetify.theme.dark = false;
  }
};
</script>

<style scoped>
body {
  overflow: hidden;
  height: 100vh;
}

#calendar {
  margin-top: -10px;
}
</style>