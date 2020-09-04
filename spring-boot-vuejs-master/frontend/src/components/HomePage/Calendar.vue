<template>
  <div id="mainDiv">
    <v-row>
      <v-col>
        <v-sheet height="400">
          <v-calendar
            ref="calendar"
            :now="today"
            :value="today"
            :events="events"
            color="primary"
            type="week"
          ></v-calendar>
        </v-sheet>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import api from "../backend-api";
export default {
  data: () => ({
    today: new Date().toISOString().substr(0, 10),
    event: {
      name: null,
      start: null,
      end: null,
    },
    events: [],
  }),
  mounted() {
    this.$refs.calendar.scrollToTime("08:00");
    api
      .getApointmentsForDoctor(localStorage.getItem("doctorID"))
      .then((response) => {
        console.log(response.data);
        for (const index in response.data) {
          this.event.name = response.data[index].type;
          var date = response.data[index].dateAndTime.split("");
          date[13] = ":";
          var newDate = "";
          for (const index in date) {
            newDate += date[index];
          }
          console.log(newDate);
          this.event.start = newDate;
          this.event.end = newDate;
          this.events.push(this.event);
        }
      });
  },
};
</script>

<style scoped>
#mainDiv {
  margin-top: 10%;
}
.my-event {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  border-radius: 2px;
  background-color: #1867c0;
  color: #ffffff;
  border: 1px solid #1867c0;
  font-size: 12px;
  padding: 3px;
  cursor: pointer;
  margin-bottom: 1px;
  left: 4px;
  margin-right: 8px;
  position: relative;
}

.my-event.with-time {
  position: absolute;
  right: 4px;
  margin-right: 0px;
}
</style>