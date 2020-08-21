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
    <v-data-table :headers="headers" :items="appoitements" :search="search" id="tabela">
      <template v-slot:item.actions="{ item }">
        <v-icon small class="mr-2" @click="appointRoom(item)">mdi-pencil</v-icon>
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
        <v-container id="container">
          <AppointRoom
            ref="AppointRoom"
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
    AppointRoom,
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
        { text: "Actions", value: "actions" },
      ],
      room: {
        calendar: null,
        roomID: null,
        roomName: "",
      },
      appoitements: [],
      user: {
        firstName: "",
        lastBane: "",
        id: "",
      },
      previewUser: false,
      appointRoomDialog: false,
      date: "",
      appt: "",
      snackbar: false,
      msg: "",
      message: {
        msg: null,
        email: [],
      },
    };
  },
  mounted() {
    this.message.email.push("stojan.v1997@gmail.com");
    api
      .getAppoitementRequests(localStorage.getItem("clinicID"))
      .then((response) => {
        this.appoitements = response.data;
        for (const index in this.appoitements) {
          var appoitementDate = "";
          api
            .automaticAppointement(
              localStorage.getItem("clinicID"),
              this.appoitements[index].dateAndTime,
              this.appoitements[index]
            )
            .then((response) => {
              //console.log("AUTOMATIC REQUESTS!");
              //console.log(response.data);
              appoitementDate = response.data.dateAndTime.split(" ");
              response.data.dateAndTime = appoitementDate[0];
              if (response.data !== "Empty") {
                console.log("Appoitement : ");
                console.log(response.data);
                api.createAppoitnment(response.data).then((response) => {
                  // console.log("CREATE APPOINTMENT");
                  //console.log(response);
                  api
                    .deleteAppointmentRequest(this.appoitements[index].id)
                    .then((response) => {
                      this.updateAppoitements();
                      this.message.msg =
                        "Appoitnement scheduled at " +
                        appoitementDate[0] +
                        " " +
                        appoitementDate[1];
                      api
                        .sendEmail(this.message)
                        .then((response) => {})
                        .catch((e) => {
                          console.log(e);
                        });
                    })
                    .catch((e) => {});
                });
              }
            })
            .catch((e) => {});
        }
      })
      .catch((e) => {});
  },
  methods: {
    automaticAppoint(appoitements) {
      for (const index in appoitements) {
        api
          .automaticAppointement(
            localStorage.getItem("clinicID"),
            this.$store.getters.getDatum,
            appoitements[index]
          )
          .then((response) => {
            console.log("AUTOMATIC REQUESTS!");
            console.log(response.data);
          })
          .catch((e) => {});
      }
    },

    openDialog() {
      this.dialogCreateRoom = !this.dialogCreateRoom;
    },
    appointRoom(item) {
      this.appointRoomDialog = true;
      this.appt = item;
      this.$store.commit("setDatum", item.dateAndTime);
      console.log(this.$store.getters.getDatum);
      this.$refs.AppointRoom.getFirstDate();
    },
    updateAppoitements() {
      console.log("UPDADTE APPOINTEMENTS");
      this.appointRoomDialog = false;
      this.snackbar = true;
      this.msg = "Success!";
      console.log("Event uhvacen!");
      api
        .getAppoitementRequests(localStorage.getItem("clinicID"))
        .then((response) => {
          this.appoitements = response.data;
        })
        .catch((e) => {});
    },

    deleteItem(item) {
      console.log(item);
      var pat = null;
      api.getUser(item.patientID).then((response) => {
        pat = response.data;
        this.message.msg = "Your request for examination has benn canceled!";
        this.message.email.push("stojan.v1997@gmail.com");
        api.sendEmail(this.message).then((response) => {});
      });
      api
        .deleteAppointmentRequest(item.id)
        .then((response) => {
          api
            .getAppoitementRequests(localStorage.getItem("clinicID"))
            .then((response) => {
              this.appoitements = response.data;
            })
            .catch((e) => {});
        })
        .catch((e) => {
          console.log(e);
        });
    },

    goBack() {
      this.$emit("goBack");
    },
    preview(event) {
      console.log("asdasd");
      console.log(event);
      api.getUser(event).then((response) => {
        this.user = response.data;
        this.previewUser = true;
      });
    },
  },
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