<template>
  <v-card>
    <v-card-title>
      <v-btn id="backbtn" icon @click="this.goBack">
        <v-icon id="icon-arrow">mdi-arrow-left</v-icon>Go Back
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
    <v-data-table :headers="headers" :items="appoitements" :search="search" id="tabela">
      <template v-slot:item.actions="{ item }">
        <v-icon small class="mr-2" @click="appointRoom(item)">mdi-pencil</v-icon>
        <v-icon small @click="deleteItem(item.roomID)">mdi-delete</v-icon>
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
        <v-container id="container">
          <AppointRoom
            @deleteRequestEvent="this.updateAppoitements"
            v-bind:appt="this.appt"
            v-bind:date="this.date"
          />
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
  data() {
    return {
      search: "",
      selected: false,
      headers: [
        { text: "Appitm. ID", value: "id" },
        { text: "Date", value: "dateAndTime" },
        { text: "Doctor", value: "doctor" },
        { text: "Patient", value: "patient" },
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
      msg: ""
    };
  },
  mounted() {
    api
      .getAppoitementRequests(localStorage.getItem("clinicID"))
      .then(response => {
        this.appoitements = response.data;
      })
      .catch(e => {});
  },
  methods: {
    openDialog() {
      this.dialogCreateRoom = !this.dialogCreateRoom;
    },
    appointRoom(item) {
      this.appointRoomDialog = true;
      this.appt = item;
      this.date = item.dateAndTime;
    },
    updateAppoitements() {
      this.appointRoomDialog = false;
      this.snackbar = true;
      this.msg = "Success!";
      console.log("Event uhvacen!");
      api
        .getAppoitementRequests(localStorage.getItem("clinicID"))
        .then(response => {
          this.appoitements = response.data;
        })
        .catch(e => {});
    },
    deleteItem(item) {
      console.log(item);
      api
        .deleteRoom(item)
        .then(response => {
          api.getClinic(localStorage.getItem("clinicID")).then(response => {
            this.$store.commit("setClinic", response.data);
            console.log(this.$store.getters.getClinic);
          });
        })
        .catch(e => {
          console.log();
        });
    },

    deleteRooms() {
      for (const roomIndex in this.selectedList) {
        api
          .deleteRoom(this.selectedList[roomIndex].roomID)
          .then(response => {
            api.getClinic(localStorage.getItem("clinicID")).then(response => {
              this.$store.commit("setClinic", response.data);
            });
          })
          .catch(e => {
            console.log();
          });
      }
      this.selectedMoreThanOne = false;
    },
    addToSelectedList(event) {
      var index = this.selectedList.indexOf(event);
      if (index == -1) {
        this.selectedList.push(event);
      } else {
        this.selectedList.splice(index, 1);
      }
      //Elementi se pomeraju kada se prikaze delete dugme
      if (this.selectedList.length > 1) {
        this.selectedMoreThanOne = true;
      } else {
        this.selectedMoreThanOne = false;
      }
    },
    goBack() {
      this.selectedMoreThanOne = false;
      this.$emit("goBack");
    },
    preview(event) {
      console.log("asdasd");
      console.log(event);
      api.getUser(event).then(response => {
        this.user = response.data;
        this.previewUser = true;
      });
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