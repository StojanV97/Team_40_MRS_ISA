<template>
  <v-card>
    <v-card-title>
      <v-btn id="backbtn" icon @click="this.goBack">
        <v-icon id="icon-arrow">mdi-arrow-left</v-icon>Go Back
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn class="icon-delete" icon v-if="selectedMoreThanOne" @click="deleteDoctors">
        <v-icon id="icon-delete">mdi-delete</v-icon>
      </v-btn>
      <v-btn id="new-item" @click="this.openDialog">Add Doctor</v-btn>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="$store.getters.getClinic.doctors"
      :search="search"
      id="tabela"
    >
      <template v-slot:item.glutenfree="{ item }">
        <input
          v-if="item.listOfAppoitnements.length == 0"
          type="checkbox"
          v-model="item.selected"
          @change="addToSelectedList(item)"
        />
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
          v-if="item.listOfAppoitnements.length == 0"
          small
          class="mr-2"
          @click="editItem(item)"
        >mdi-pencil</v-icon>
        <v-icon
          v-if="item.listOfAppoitnements.length == 0"
          small
          @click="deleteItem(item)"
        >mdi-delete</v-icon>
      </template>
    </v-data-table>
    <v-dialog v-model="this.dialogCreateRoom" width="800px">
      <v-card>
        <v-card-title class="grey darken-2">Create room</v-card-title>
        <v-container>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field
              v-model="newDoctor.firstName"
              :counter="10"
              :rules="firstNameRules"
              label="Name"
              required
            ></v-text-field>
            <v-text-field
              v-model="newDoctor.lastName"
              :counter="10"
              :rules="lastNameRules"
              label="Last Name"
              required
            ></v-text-field>
            <v-text-field
              v-model="newDoctor.userName"
              :counter="10"
              :rules="userNameRules"
              label="UserName"
              required
            ></v-text-field>
            <v-text-field v-model="newDoctor.email" :rules="emailRules" label="E-mail" required></v-text-field>
            <v-text-field v-model="newDoctor.password" :counter="10" label="Password" required></v-text-field>
            <v-btn :disabled="!valid" color="success" class="mr-4" @click="createDoctor">Create</v-btn>
          </v-form>
        </v-container>
        <v-card-actions>
          <v-spacer />
          <v-btn text color="primary" @click="dialogCreateRoom = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="dialogEditUser" width="800px">
      <v-card>
        <v-card-title class="grey darken-2">Edit doctor</v-card-title>
        <v-container>
          <DoctorEdit v-bind:doctorEdit="doctor" />
        </v-container>
        <v-card-actions>
          <v-spacer />
          <v-btn text color="primary" @click="dialogEditUser = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import api from "../backend-api";
import MedicalStaffRegistration from "../Users/MedicalStaffRegistrationForm";
import DoctorEdit from "../Profiles/DoctorEdit";
export default {
  //treba odraditi definisanje radnog vremena pri unosenju novog doktora
  // zabraniti menjanje i brisanje doktora koji imaju zakazane preglede

  components: {
    DoctorEdit,
    MedicalStaffRegistration
  },
  props: ["clinicRooms"],
  data() {
    return {
      doctor: null,
      oldID: null,
      oldDatesForEdit: null,
      roomForEdit: null,
      dialogEditUser: false,
      selectedMoreThanOne: false,
      dialogCreateRoom: false,
      selectedList: [],
      search: "",
      selected: false,
      headers: [
        { text: " ", value: "glutenfree" },
        { text: "User ID", value: "id" },
        { text: "First Name", value: "firstName" },
        { text: "Last Name", value: "lastName" },
        { text: "UserName", value: "username" },
        { text: "Email", value: "email" },
        { text: "Actions", value: "actions" }
      ],
      newDoctor: {
        firstName: "",
        lastName: "",
        userName: "",
        email: "",
        password: ""
      },
      valid: true,
      firstNameRules: [
        v => !!v || "Name is required",
        v => (v && v.length <= 10) || "Name must be less than 10 characters"
      ],
      lastNameRules: [
        v => !!v || "Last name is required",
        v =>
          (v && v.length <= 10) || "Last name must be less than 10 characters"
      ],
      userNameRules: [
        v => !!v || "UserName is required",
        v => (v && v.length <= 10) || "UserName must be less than 10 characters"
      ],
      emailRules: [
        v => !!v || "E-mail is required",
        v => /.+@.+\..+/.test(v) || "E-mail must be valid"
      ]
    };
  },
  mounted() {
    console.log("sadasdas");
    console.log(this.$store.getters.getClinic.doctors);
  },
  methods: {
    openDialog() {
      this.dialogCreateRoom = !this.dialogCreateRoom;
    },

    editItem(item) {
      this.dialogEditUser = true;
      this.doctor = item;
    },
    createDoctor() {
      api
        .createDoctor(this.newDoctor, localStorage.getItem("clinicID"))
        .then(response => {
          console.log(response.data);
          if (response.data !== "Exists!") {
            api
              .getClinic(localStorage.getItem("clinicID"))
              .then(response => {
                this.$store.commit("setClinic", response.data);
              })
              .catch(error => {
                console.log();
              });
          }
        })
        .catch(e => {});
    },
    deleteDoctors() {
      for (const roomIndex in this.selectedList) {
        api
          .deleteUser(this.selectedList[roomIndex].username)
          .then(response => {
            api.getClinic(localStorage.getItem("clinicID")).then(response => {
              this.$store.commit("setClinic", response.data);
            });
          })
          .catch(e => {
            console.log();
          });
      }
      this.selectedMoreThanOne = false;
    },
    deleteItem(item) {
      console.log(item);
      api
        .deleteUser(item.username)
        .then(response => {
          api.getClinic(localStorage.getItem("clinicID")).then(response => {
            this.$store.commit("setClinic", response.data);
            console.log(this.$store.getters.getClinic);
          });
        })
        .catch(e => {
          console.log();
        });
    },

    addToSelectedList(event) {
      var index = this.selectedList.indexOf(event);
      if (index == -1) {
        this.selectedList.push(event);
      } else {
        this.selectedList.splice(index, 1);
      }
      //Elementi se pomeraju kada se prikaze delete dugme
      if (this.selectedList.length > 1) {
        this.selectedMoreThanOne = true;
      } else {
        this.selectedMoreThanOne = false;
      }
    },
    goBack() {
      this.selectedMoreThanOne = false;
      this.$emit("goBack");
    }
  }
};
</script>

<style  scoped>
#edita {
  margin-left: 30%;
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