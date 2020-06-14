<template>
  <v-card>
    <v-card-title>
      <v-btn id="backbtn" icon @click="this.reloadAppt">
        <v-icon id="icon-arrow">mdi-reload</v-icon>Reload
      </v-btn>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table :headers="headers" :items="$props.appoitements" :search="search" id="tabela">
      <template v-slot:item.actions="{ item }">
        <v-icon medium class="mr-2" @click="start(item)">mdi-clock-start</v-icon>
      </template>
      <template v-slot:item.doctor="{ item }">
        <v-btn
          class="ma-2"
          outlined
          small
          fab
          color="indigo"
          @click="preview(item.doctorID)"
        >{{item.doctorID}}</v-btn>
      </template>
      <template v-slot:item.patient="{ item }">
        <v-btn
          class="ma-2"
          outlined
          small
          fab
          color="indigo"
          @click="preview(item.patientID)"
        >{{item.patientID}}</v-btn>
      </template>
    </v-data-table>

    <v-dialog v-model="previewUser" width="400">
      <v-card>
        <v-card-title class="grey darken-2">User</v-card-title>
        <v-container id="container">
          <v-text-field v-model="user.firstName" label="First Name" outlined shaped></v-text-field>
          <v-text-field v-model="user.lastName" label="Last Name" outlined shaped></v-text-field>
          <v-text-field v-model="user.id" label="ID" outlined shaped></v-text-field>
        </v-container>
        <v-card-actions>
          <v-spacer />
          <v-btn text color="primary" @click="previewUser = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="appointRoomDialog" width="1400" id="dialog">
      <v-card>
        <v-card-title class="grey darken-2">Examination</v-card-title>
        <v-container id="container">
          <StartExamination v-bind:appointment="$props.appoitements" />
        </v-container>
        <v-card-actions>
          <v-spacer />
          <v-btn text color="primary" @click="previewUser = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{msg}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </v-card>
</template>
<script>
import api from "../backend-api";
import AppointRoom from "../Utility/AppointRoom";
import StartExamination from "../Scheduling/StartExamination";

export default {
  components: {
    AppointRoom,
    StartExamination
  },
  props: ["appoitements"],
  data() {
    return {
      search: "",
      selected: false,
      headers: [
        { text: "Appitm. ID", value: "id" },
        { text: "Date", value: "dateAndTime" },
        { text: "Doctor", value: "doctorID" },
        { text: "Patient", value: "patientID" },
        { text: "Room", value: "roomID" },
        { text: "Start", value: "actions" }
      ],
      room: {
        calendar: null,
        roomID: null,
        roomName: ""
      },
      user: {
        firstName: "",
        lastBane: "",
        id: ""
      },
      previewUser: false,
      appointRoomDialog: false,
      date: "",
      appt: "",
      snackbar: false,
      msg: "",
      message: {
        msg: null,
        email: []
      }
    };
  },
  mounted() {
    console.log("Mounted");
    console.log($props.appoitements);
  },
  methods: {
    start(item) {
      console.log(item);
      this.appointRoomDialog = true;
      this.$store.commit("setAppointment", item);
    },
    reloadAppt() {
      api
        .getCurretExaminations(localStorage.getItem("userID"))
        .then(response => {
          console.log(response.data);
          $props.appoitements = response.data;
        })
        .catch(e => {});
    }
  }
};
</script>

<style  scoped>
.preview {
  background-color: blue;
}
#editForm {
  margin-right: 140px;
}

#room_dialog {
  margin-right: 240px;
}

#tabela {
  height: 100%;
}
.icon-delete {
  padding-top: 20px;
  margin-right: 40px;
}

#new-item {
  color: white;
  background-color: rgb(0, 89, 255);
  margin-right: 40px;
  margin-top: 18px;
  height: 28px;
}

#icon-arrow {
  width: 40px;
}

#backbtn {
  padding-top: 20px;
  width: 100px;
  margin-left: 20px;
}
</style>