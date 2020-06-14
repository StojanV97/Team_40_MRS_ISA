<template>
  <v-card>
    <v-card-title>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table :headers="headers" :items="$props.req" :search="search" id="tabela">
      <template v-slot:item.actions="{ item }">
        <v-icon small class="mr-2" @click="aprove(item)">mdi-pencil</v-icon>
        <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
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
        <v-card-title class="grey darken-2">User</v-card-title>
        <v-container fluid>
          <v-textarea v-model="message.msg" name="input-7-1" filled label="Message" auto-grow></v-textarea>
          <v-btn @click="delelteagain">Submit</v-btn>
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

export default {
  components: {
    AppointRoom
  },
  props: ["req"],
  data() {
    return {
      search: "",
      selected: false,
      headers: [
        { text: "First Name", value: "firstName" },
        { text: "Last Name", value: "lastname" },
        { text: "Date From", value: "dateFrom" },
        { text: "Date To", value: "dateTo" },
        { text: "User ID", value: "userID" },
        { text: "Type", value: "type" },
        { text: "Actions", value: "actions" }
      ],
      room: {
        calendar: null,
        roomID: null,
        roomName: ""
      },
      appoitements: [],
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
      },
      element: null
    };
  },
  mounted() {},
  methods: {
    aprove(item) {
      api
        .createDaysOff(item)
        .then(response => {
          api
            .deleteReqDaysOF(item.id)
            .then(response => {
              this.$props.req = response.data;

              this.message.msg = "Your request for days of has been aproved!";
              this.message.email.push(item.email);
              api
                .sendEmail(this.message)
                .then(response => {})
                .catch(e => {});
            })
            .catch(e => {});
        })
        .catch(e => {});
    },
    deleteItem(item) {
      this.appointRoomDialog = true;
      this.element = item;
    },
    delelteagain() {
      api
        .deleteReqDaysOF(this.element.id)
        .then(response => {
          this.appointRoomDialog = false;
          this.$props.req = response.data;
          this.message.msg = "Your request for days of has been declined!";
          this.message.email.push(this.element.email);
          api
            .sendEmail(this.message)
            .then(response => {})
            .catch(e => {});
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