<template>
  <div class="child_div" id="div_left">
    <v-row align="left">
      <v-row justify="space-around"></v-row>
      <v-form id="forma" ref="form" v-model="valid">
        <v-text-field
          v-model="room.roomID"
          :counter="8"
          :rules="nameRules"
          label="Room ID"
          type="number"
          required
        ></v-text-field>

        <v-text-field v-model="date" label="Add Date" type="date" required></v-text-field>
        <v-select
          v-model="room.roomName"
          :items="items"
          :rules="[v => !!v || 'Item is required']"
          label="Item"
          required
        ></v-select>
        <v-card class="mx-auto" max-width="400" tile="Dates" v-if="this.$props.oldDates.length > 0">
          <v-toolbar-title id="naslov">Dates</v-toolbar-title>
          <div class="v-list">
            <div v-for="item in this.$props.oldDates" :key="item">
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title id="ajtem">
                    {{item}}
                    <v-btn @click="deleteDateFromList(item)" class="dugme" icon>
                      <v-icon color="blue lighten-1">mdi-delete</v-icon>
                    </v-btn>
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </div>
          </div>
        </v-card>
        <v-btn color="success" class="mr-4" @click="validate">Confirm Changes</v-btn>

        <v-btn color="error" class="mr-4" @click="reset">Reset Form</v-btn>
      </v-form>
    </v-row>
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
  components: {},

  name: "EditRoom",
  props: ["roomFromParent", "oldDates", "oldID", "clinicID"],

  data: () => ({
    msg: "",
    snackbar: false,
    date: null,
    room: {
      roomID: null,
      roomName: null,
      calendar: []
    },
    dateSelected: null,
    valid: true,
    nameRules: [
      v => !!v || "ID is required",
      v => (v && v.length <= 8) || "ID must be less than 8 digits"
    ],
    items: ["Operation", "Examination"]
  }),
  mounted() {
    //pokusaj kasnije da povezes prop sa poljima za unos
    this.room.roomID = this.$props.roomFromParent.roomID;
    this.room.roomName = this.$props.roomFromParent.roomName;
    this.room.calendar = this.$props.roomFromParent.calendar;
  },

  watch: {
    roomFromParent() {
      this.room.roomID = this.$props.roomFromParent.roomID;
      this.room.roomName = this.$props.roomFromParent.roomName;
      this.room.calendar = this.$props.roomFromParent.calendar;
    }
  },
  methods: {
    deleteDateFromList(event) {
      for (const index in this.$props.oldDates) {
        if (this.$props.oldDates[index] == event) {
          this.$props.oldDates.splice(index, 1);
        }
      }
    },

    validate() {
      // REZERVISANA SALA SE NE MOZE OBRISATI ILI PROMENITI

      let currentDate = new Date();
      let inputDate = new Date(this.date);
      if (this.date != null) {
        if (inputDate.getTime() < currentDate.getTime()) {
          this.msg = "Illegal date!";
          this.snackbar = true;
        } else {
          if (this.$props.oldDates(this.date) === -1) {
            this.$props.oldDates.push(this.date);
          } else {
            this.msg = "Date already exists!";
            this.snackbar = true;
          }
        }
      }
      if (this.msg != "Illegal date!" && this.msg != "Date already exists!") {
        api
          .deleteRoom(this.$props.oldID)
          .then(response => {
            api
              .editRoom(this.room, localStorage.getItem("clinicID"))
              .then(response => {
                this.msg = "Room Changed!";
                this.snackbar = true;
                api
                  .getClinic(localStorage.getItem("clinicID"))
                  .then(response => {
                    this.$store.commit("setClinic", response.data);
                  });
              })
              .catch(e => {
                console.log(e);
              });
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  }
};
</script>
Name


<style scoped>
#forma {
  margin-top: 20px;
}
#div_unos {
  margin-right: 640px;
  margin-top: 240px;
  width: 500px;
}

.mr-4 {
  margin-top: 15px;
}

.v-list {
  height: 250px;
  overflow-y: auto;
}

#naslov {
  margin-left: 40px;
}

.dugme {
  padding-left: 100px;
}

#ajtem {
  padding-left: 30px;
}
</style>