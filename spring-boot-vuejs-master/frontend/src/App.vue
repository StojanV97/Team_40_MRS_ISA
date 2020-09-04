<template>
  <v-app>
    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import Chart from "chart.js";

export default {
  name: "App",

  data: () => ({
    //
  }),
};
</script>
