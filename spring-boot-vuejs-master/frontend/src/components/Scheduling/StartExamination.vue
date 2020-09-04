<template>
  <div>
    <v-text-field v-model="date" type="date" label="Date" outlined shaped id="datum"></v-text-field>
    <v-select v-model="select" :items="items" label="Standard" dense></v-select>
    <div class="text-center">
      <label for>Schedule operation or examination:</label>
      <v-btn rounded color="primary" id="sch" dark @click="schedule">Schedule</v-btn>
    </div>
    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{msg}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </div>
</template>

<script>
import api from "../backend-api";
export default {
  data() {
    return {
      snackbar: false,
      msg: "",
      date: "",
      select: "",
      items: ["OPERATION", "EXAMINATION"],
    };
  },
  props: ["appointment"],
  mounted() {
    console.log("mounted schedule!");
    console.log(this.$store.getters.getAppt);
  },
  methods: {
    schedule() {
      console.log(this.$store.getters.getAppt.clinicID);
      if (this.date === "" || this.select === "") {
        console.log("Empty date or Type");
      } else {
        api
          .createAppointmentRequest(
            this.date,
            this.select,
            this.$store.getters.getAppt.doctorID,
            this.$store.getters.getAppt.patientID,
            this.$store.getters.getAppt.clinicID
          )
          .then((response) => {
            this.msg = "Scheduled!";

            this.snackbar = true;
            for (const index in this.$props.appointment) {
              if (
                this.$props.appointment[index].patientID ==
                this.$store.getters.getAppt.patientID
              ) {
                console.log(index);
                this.$emit("delete", index);
              }
            }
          })
          .catch((e) => {});
      }
    },
  },
};
</script>

<style>
#sch {
  margin-left: 60%;
}
#datum {
  width: 100px;
}
</style>