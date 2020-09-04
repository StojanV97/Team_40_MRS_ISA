<template>
  <div id="main">
    <v-row>
      <v-col cols="12" sm="6">
        <v-date-picker v-model="dates" range></v-date-picker>
        <v-col cols="12" sm="6">
          <v-btn :disabled="!valid" color="success" class="mr-4" @click="showIncome">Show</v-btn>
        </v-col>
      </v-col>
    </v-row>
    <div id="table">
      <v-data-table
        height="200px"
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
        <v-text-field v-model="title" :rules="rules" label="Total Income in dollars" outlined></v-text-field>
      </div>
      <div class="text-center ma-2">
        <v-snackbar v-model="snackbar">
          {{text}}
          <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
        </v-snackbar>
      </div>
    </div>
  </div>
</template>

<script>
import api from "../backend-api";

export default {
  data: () => ({
    title: null,
    showTime: false,
    snackbar: false,
    text: "",
    date: new Date().toISOString().substr(0, 10),
    modal: false,
    menu2: false,
    valid: true,
    name: "",
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

    dates: [],

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
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
  mounted() {},
  methods: {
    showIncome() {
      this.title = 0;
      api
        .getIncome(this.dates)
        .then((response) => {
          this.appointements = response.data;
          console.log(this.appointements);
          for (const index in this.appointements) {
            if (this.appointements[index].type == "EXAMINATION") {
              this.title += 50;
            } else if (this.appointements[index].type == "OPERATION") {
              this.title += 250;
            }
          }
        })
        .catch((e) => {
          this.text = "Morate uneti datum!";
          this.snackbar = true;
        });
    },
    validate() {},
    reset() {
      this.$refs.form.reset();
      this;
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
  },
};
</script>
<style >
#table {
  margin-top: 6%;
  margin-right: 30%;
}
#main {
  display: flex;
  margin-left: 10%;
}
#forma {
  margin-left: 20%;
  margin-top: 5%;
}
</style>