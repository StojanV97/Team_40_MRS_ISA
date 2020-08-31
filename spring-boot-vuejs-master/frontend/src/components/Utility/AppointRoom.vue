<template>
  <v-card>
    <v-card-title>
      <v-text-field v-model="$store.getters.getDatum" label="Date" single-line hide-details></v-text-field>
      <v-spacer></v-spacer>
      <v-text-field
        @change="customFilter($event)"
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
        show-select
        :single-select="singleSelect"
      ></v-text-field>
    </v-card-title>
    <v-data-table :headers="headers" :items="rooms" disable-filtering id="tabela">
      <template v-slot:item.actions="{ item }">
        <v-icon medium class="mr-2" @click="appoint(item)">mdi-check-circle</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>
<script>
import api from "../backend-api";
import moment from "moment";

export default {
  components: {},
  props: ["date", "appt"],
  data() {
    return {
      rooms: [],
      datumi: [],
      headers: [
        { text: "Action", value: "actions" },
        { text: "Room ID", value: "roomID" },
        { text: "Room Name", value: "roomName" },
        { text: "Calendar", value: "calendar" },
        { text: "First Available", value: "firstAvailableDate" }
      ],
      appointement: {
        dateAndTime: null,
        roomID: null,
        patientID: null,
        doctorID: null,
        type: null
      },
      message: {
        msg: null,
        email: []
      },
      doctor: "",
      partient: "",
      idAndDate: null
    };
  },
  mounted() {
    this.rooms = [];
    api
      .getFirstAvailableDates(
        localStorage.getItem("clinicID"),
        this.$store.getters.getDatum
      )
      .then(response => {
        console.log(response.data);
        this.$store.getters.getClinic.rooms = response.data;
        for (const index in this.$store.getters.getClinic.rooms) {
          if (
            this.$store.getters.getClinic.rooms[index].roomName ===
            "Examination"
          ) {
            this.rooms.push(this.$store.getters.getClinic.rooms[index]);
          }
        }
      })
      .catch(e => {});
  },
  methods: {
    getFirstDate() {
      console.log("Datum iz metode: ");
      this.rooms = [];
      api
        .getFirstAvailableDates(
          localStorage.getItem("clinicID"),
          this.$store.getters.getDatum
        )
        .then(response => {
          this.$store.getters.getClinic.rooms = response.data;
          for (const index in this.$store.getters.getClinic.rooms) {
            if (
              this.$store.getters.getClinic.rooms[index].roomName ===
              "Examination"
            ) {
              this.rooms.push(this.$store.getters.getClinic.rooms[index]);
            }
          }
        })
        .catch(e => {});
    },

    appoint(item) {
      this.appointement.roomID = item.roomID;
      this.appointement.doctorID = this.$props.appt.doctorID;
      this.appointement.patientID = this.$props.appt.patientID;
      this.appointement.dateAndTime = this.$store.getters.getDatum;
      this.appointement.type = "EXAMINATION";
      this.appointement.clinicID = localStorage.getItem("clinicID");

      api.getUser(this.appointement.doctorID).then(response => {
        this.doctor = response.data;
      });
      api.getUser(this.appointement.patientID).then(response => {
        this.partient = response.data;
      });
      this.message.email.push("stojan.v1997@gmail.com");
      this.message.email.push("stojan.v1997@gmail.com");
      api
        .createAppoitnment(this.appointement)
        .then(response => {
          this.idAndDate = response.data;
          console.log(response.data);
          api
            .deleteAppointmentRequest(this.$props.appt.id)
            .then(response => {
              this.message.msg =
                "Appoitnement scheduled at " + this.idAndDate.date;
              api
                .sendEmail(this.message)
                .then(response => {})
                .catch(e => {
                  console.log(e);
                });
              this.$emit("deleteRequestEvent");
            })
            .catch(e => {});
        })
        .catch(e => {});
    },
    customFilter(event) {
      if (event === "") {
        this.rooms = [];
        for (const index in this.$store.getters.getClinic.rooms) {
          if (
            this.$store.getters.getClinic.rooms[index].roomName ===
            "Examination"
          ) {
            this.rooms.push(this.$store.getters.getClinic.rooms[index]);
          }
        }
      }
      for (const index in this.rooms) {
        if (!this.rooms[index].roomID.toString().includes(event.toString())) {
          this.rooms.splice(index, 1);
        }
      }
    }
  }
};
</script>

<style  scoped>
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