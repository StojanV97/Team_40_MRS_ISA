<template>
  <div>
    <bar-chart ref="chart" v-bind:data="data"></bar-chart>
    <v-container fluid id="kontjner">
      <hr />
      <v-radio-group v-model="row" row>
        <v-radio label="Show Daily" value="radio-1" @click="showDialy"></v-radio>
        <v-radio label="Show Weekly" value="radio-2" @click="showWeekly"></v-radio>
        <v-radio label="Show Monthly" value="radio-3" @click="showMonthly"></v-radio>
      </v-radio-group>
    </v-container>
  </div>
</template>

<script>
import BarChart from "../Utility/BarChart";
import api from "../backend-api";
export default {
  components: {
    BarChart,
  },
  data: () => ({
    data: [],
    labels: [],
  }),
  methods: {
    showDialy() {
      api.getAppointemensForChart(1).then((response) => {
        this.labels = [
          "10:00",
          "10:30",
          "11:00",
          "11:30",
          "12:00",
          "12:30",
          "13:00",
          "13:30",
          "14:00",
        ];

        this.$refs.chart.reRender(this.labels, this.data);
      });
    },
    showMonthly() {
      api.getAppointemensForChart(3).then((response) => {
        var datumi = Object.keys(response.data);
        datumi = datumi[0];
        datumi = datumi.split(",");
        var fixDate = datumi[0].split("[");
        var fixDate2 = datumi[datumi.length - 1].split("]");
        datumi.splice(0, 1, fixDate[1]);
        datumi.splice(datumi.length - 1, 1, fixDate2[0]);
        this.labels = datumi;
        var values = Object.values(response.data);
        this.data = values[0];
        console.log(this.data);
        this.$refs.chart.reRender(this.labels, this.data);
      });
    },
    showWeekly() {
      api.getAppointemensForChart(2).then((response) => {
        var datumi = Object.keys(response.data);
        datumi = datumi[0];
        datumi = datumi.split(",");
        var fixDate = datumi[0].split("[");
        var fixDate2 = datumi[datumi.length - 1].split("]");
        datumi.splice(0, 1, fixDate[1]);
        datumi.splice(datumi.length - 1, 1, fixDate2[0]);
        this.labels = datumi;
        var values = Object.values(response.data);
        this.data = values[0];
        console.log(this.data);
        this.$refs.chart.reRender(this.labels, this.data);
      });
    },
  },
  mounted() {},
};
</script>
<style scoped>
#kontjner {
}
</style>