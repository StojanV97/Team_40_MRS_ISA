<template>
  <div id="app" class="forma">
    <div>
      <v-form class="form-block" ref="form" v-model="valid">
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic name</label>
          <v-text-field
            v-model="$store.getters.getClinic.name"
            :rules="nameRules"
            outlined
            dense
            required
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic ID</label>
          <v-text-field
            v-model="$store.getters.getClinic.id"
            type="number"
            required
            outlined
            dense
            disabled
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic address</label>
          <v-text-field
            v-model="$store.getters.getClinic.address"
            :rules="nameRules"
            outlined
            dense
            required
          />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic administrator</label>
          <v-text-field
            v-model="$store.getters.getUser.firstName"
            :rules="administratorRules"
            outlined
            dense
            disabled
          />
        </div>
        <div>
          <v-btn class="btn btn-primary" @click="editClinic()">Submit</v-btn>
        </div>
      </v-form>
    </div>

    <div id="doctors-list">
      <div id="dlabel">
        <label id="labela-doctors">Doctors</label>
      </div>

      <div class="lista">
        <ul class="list-group">
          <li
            class="list-group-item"
            v-for="item in $store.getters.getClinic.doctors"
            :key="item.firstName"
          >
            <span class="spanLI">{{ item.firstName }}</span>
            <span class="spanLI">{{ item.firstName }}</span>
          </li>
        </ul>
      </div>
      <v-btn class="edit-btn" outlined color="warning" @click="editDoctors()">Edit Doctors</v-btn>
    </div>

    <div id="doctors-list">
      <div id="dlabel">
        <label id="labela-doctors">Rooms</label>
      </div>

      <div class="lista">
        <ul class="list-group">
          <li
            class="list-group-item"
            v-for="item in $store.getters.getClinic.rooms"
            :key="item.roomID"
          >
            <span class="spanLI">{{ item.roomID }}</span>
            <span class="spanLI">{{ item.roomName }}</span>
          </li>
        </ul>
      </div>
      <v-btn class="edit-btn" outlined color="warning" @click="editRooms()">Edit Rooms</v-btn>
    </div>

    <div class="text-center ma-2">
      <v-snackbar v-model="snackbar">
        {{msg}}
        <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
      </v-snackbar>
    </div>
  </div>
</template>

<style scoped>
.edit-btn {
  margin-left: 24px;
  margin-top: 44px;
  width: 280px;
}

.spanLI {
  margin-right: 20px;
}
#exampleInputEmail1 {
  padding-top: 10px;
}

#labela-doctors {
  margin-left: 24px;
  background-color: rgb(0, 119, 255);
  color: white;
  padding-left: 10px;
  padding-top: 5px;
  width: 276px;
  height: 40px;
  margin-bottom: 0;
}
.lista {
  overflow: auto;
  height: 288px;
  width: 300px;
}
.active {
  position: fixed;
}
#doctors-list {
  margin-top: 50px;
  margin-left: 70px;
  height: 288px;
  width: 300px;
}

.forma {
  display: flex;
  width: 100%;
  margin-top: 20px;
  padding-top: 0;
}

.form-block {
  margin-top: 40px;
  margin-left: 40px;
}
.form-group {
  width: 300px;
  margin-top: -20px;
}
.list-group-item {
  height: 48px;
}
</style>

<script>
import api from "../backend-api";
import RoomConfig from "../Utility/RoomConfig";
export default {
  components: {
    RoomConfig,
  },
  name: "ClinicEditForm",
  props: ["clinicEdit"],
  data() {
    return {
      dialogEditRooms: false,
      oldID: null,
      valid: true,
      msg: "",
      snackbar: false,
      flag: false,
      form: {
        quantityOption: null,
      },
      valid: true,
      clinic: {
        name: "",
        address: "",
        administrator: "",
        id: "",
      },
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 20) || "Name must be less than 10 characters",
      ],
      idRules: [
        (v) => !!v || "ID is required",
        (v) => (v && v.length <= 10) || "ID must be less than 10 characters",
      ],
      administratorRules: [
        (v) => !!v || "Administrator is required",
        (v) =>
          (v && v.length <= 12) ||
          "Administrator must be less than 12 characters",
      ],
      select: null,
      checkbox: false,
      flag: false,
    };
  },
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
      "Bearer " + localStorage.getItem("token");
    this.oldID = this.$store.getters.getClinic.id;
  },

  methods: {
    editDoctors() {
      this.$emit("editDoctorsEvent");
    },

    editRooms() {
      this.$emit("editRoomsEvent");
    },
    setClinicValues(i, n, a, ad) {
      //console.log("usao je u funkciju");
      this.clinic.id = i;
      this.clinic.name = n;
      this.clinic.address = a;
    },
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    editClinic() {
      this.clinic.id = this.$store.getters.getClinic.id;
      this.clinic.name = this.$store.getters.getClinic.name;
      this.clinic.address = this.$store.getters.getClinic.address;
      console.log(this.clinic.id);

      api
        .editClinicInfo(this.clinic.name, this.clinic.address, this.oldID)
        .then((response) => {})
        .catch((e) => {
          console.log(e);
        });
    },
  },

  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    },
  },
};
</script>

<style scoped>
</style>