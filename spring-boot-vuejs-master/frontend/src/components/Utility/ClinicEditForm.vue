<template>
  <div id="app" class="forma">
    <div>
      <v-form class="form-block" ref="form" v-model="valid">
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic name</label>
          <v-text-field v-model="this.$props.clinicEdit.name" :rules="nameRules" outlined dense />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic ID</label>
          <v-text-field v-model="this.$props.clinicEdit.id" type="number" outlined dense />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic address</label>
          <v-text-field v-model="this.$props.clinicEdit.address" :rules="nameRules" outlined dense />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Clinic administrator</label>
          <v-text-field
            v-model="this.$props.clinicEdit.administrator"
            :rules="administratorRules"
            outlined
            dense
          />
        </div>
        <div>
          <v-btn type="submit" class="btn btn-primary" @click="editClinic">Submit</v-btn>
          <v-btn class="ma-2" outlined color="indigo" @click="reset">Reset</v-btn>
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
            v-for="item in this.$props.clinicEdit.doctors"
            :key="item.firstName"
          >
            <span class="spanLI">{{ item.firstName }}</span>
            <span class="spanLI">{{ item.firstName }}</span>
          </li>
        </ul>
      </div>
    </div>

    <div id="doctors-list">
      <div id="dlabel">
        <label id="labela-doctors">Rooms</label>
      </div>

      <div class="lista">
        <ul class="list-group">
          <li
            class="list-group-item"
            v-for="item in this.$props.clinicEdit.rooms"
            :key="item.roomID"
          >
            <span class="spanLI">{{ item.roomID }}</span>
            <span class="spanLI">{{ item.roomName }}</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
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

export default {
  name: "ClinicEditForm",
  props: ["clinicEdit"],
  data() {
    return {
      valid: true,
      msg: "",
      snackbar: false,
      flag: false,
      form: {
        quantityOption: null
      },
      valid: true,
      clinic: {
        name: "",
        address: "",
        administrator: "",
        id: ""
      },
      nameRules: [
        v => !!v || "Name is required",
        v => (v && v.length <= 20) || "Name must be less than 10 characters"
      ],
      idRules: [
        v => !!v || "ID is required",
        v => (v && v.length <= 10) || "ID must be less than 10 characters"
      ],
      administratorRules: [
        v => !!v || "Administrator is required",
        v =>
          (v && v.length <= 10) ||
          "Administrator must be less than 10 characters"
      ],
      select: null,
      checkbox: false,
      flag: false
    };
  },
  mounted() {
    console.log("Clinic: ");
    console.log();
  },

  methods: {
    setClinicValues(i, n, a, ad) {
      //console.log("usao je u funkciju");
      this.clinic.id = i;
      this.clinic.name = n;
      this.clinic.address = a;
      this.clinic.administrator = ad;
    },
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
    editClinic() {
      api.deleteClinic(this.clinic.id).then(response => {
        api
          .createClinicAgain(this.clinic)
          .then(response => {
            // JSON responses are automatically parsed.
            this.response = response.data;
            console.log(response.data);
            this.msg = "Clinic successfully edited!";
            this.snackbar = true;
          })

          .catch(e => {
            console.log(e);
          });
      });
    }
  },

  computed: {
    state() {
      return Boolean(this.form.quantityOption);
    }
  }
};
</script>

<style scoped>
</style>