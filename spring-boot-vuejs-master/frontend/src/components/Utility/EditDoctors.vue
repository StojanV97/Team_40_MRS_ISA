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
      <v-btn id="new-item" @click="this.openDialog">New Item</v-btn>
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
        <input type="checkbox" v-model="item.selected" @change="addToSelectedList(item)" />
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
        <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
    <v-dialog v-model="this.dialogCreateRoom" width="800px">
      <v-card>
        <v-card-title class="grey darken-2">Create room</v-card-title>
        <v-container>
          <RoomRegistrationForm id="room_dialog" />
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
          <EditUser />
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
import RoomRegistrationForm from "../Utility/RoomRegistrationForm";
import EditUser from "../Users/EditUser";
export default {
  //treba odraditi definisanje radnog vremena pri unosenju novog doktora
  // zabraniti menjanje i brisanje doktora koji imaju zakazane preglede

  components: {
    RoomRegistrationForm,
    EditUser
  },
  props: ["clinicRooms"],
  data() {
    return {
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
      this.dialogEditUser = !this.dialogEditUser;
      this.roomForEdit = item;
      this.oldDatesForEdit = [];
      this.oldID = item.roomID;
      for (const index in item.calendar) {
        this.oldDatesForEdit.push(item.calendar[index]);
      }
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