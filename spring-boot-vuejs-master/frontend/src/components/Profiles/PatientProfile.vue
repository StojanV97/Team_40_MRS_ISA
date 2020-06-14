<template>
  <div id="app" class="forma">
    <div>
      <v-card-text class="previewText">Preview of your profile</v-card-text>

      <v-form class="forma1" ref="form">
        <v-text-field class="field" v-model="user.firstName" readonly label="First Name"></v-text-field>
        <v-text-field class="field" v-model="user.lastName" readonly label="First Name"></v-text-field>
        <v-text-field class="field" v-model="user.userName" readonly label="UserName"></v-text-field>

        <v-text-field class="field" v-model="user.email" label="E-mail" readonly></v-text-field>

        <v-text-field class="field" v-model="user.country" readonly label="Country"></v-text-field>
        <v-text-field class="field" v-model="user.city" readonly label="City"></v-text-field>

        <v-text-field class="field" v-model="user.address" readonly label="Address"></v-text-field>

        <v-text-field class="field" v-model="user.phoneNumber" readonly label="Phone number"></v-text-field>
        <v-text-field
          class="field"
          v-model="user.insuranceNumber"
          readonly
          label="Insurance number"
        ></v-text-field>
      </v-form>
    </div>
    <div>
      <div class="text-center">
        <v-dialog
                v-model="dialog"
                width="700"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
                    class="button"
                    color="red lighten-2"
                    dark
                    height="100px"
                    v-bind="attrs"
                    v-on="on"
                    @click="medicalRecords"
            >
              Preview your medical record
            </v-btn>
          </template>

          <v-card>
            <v-card-title
                    primary-title
            >
              Medical Record
            </v-card-title>
            <v-divider></v-divider>

            <v-text-field class="field" v-model="this.medicalRecord.allergies" readonly label="Allergies"></v-text-field>
            <v-text-field class="field" v-model="this.medicalRecord.bloodType" readonly label="Blood Type"></v-text-field>
            <v-text-field class="field" v-model="this.medicalRecord.height" readonly label="Height "></v-text-field>
            <v-text-field class="field" v-model="this.medicalRecord.weight" readonly label="Weight "></v-text-field>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                      color="primary"
                      text
                      @click="dialog = false"
              >
                Close
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </div>
  </div>

</template>



<script>
import api from "./../backend-api";

export default {
  name: "ShowPatientProfile",
  data() {
    return {
      dialog: false,
      medicalRecord: {
        allergies: '',
        bloodType: '',
        height: '',
        weight: '',
      },
      user: {

        id: '',
        firstName: "",
        lastName: "",
        userName: "",
        email: "",
        country: "",
        city: "",
        address: "",
        phoneNumber: "",
        insuranceNumber: ""
      }
    };
  },
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
            "Bearer " + localStorage.getItem("token");
    api.getUser(localStorage.getItem("userID")).then(response => {
      this.user.id = response.data.id;
      this.user.userName = response.data.username;
      this.user.lastName = response.data.lastName;
      this.user.firstName = response.data.firstName;
      this.user.email = response.data.email;
      this.user.country = response.data.country;
      this.user.city = response.data.city;
      this.user.address = response.data.address;
      this.user.phoneNumber = response.data.phoneNumber;
      this.user.insuranceNumber = response.data.insuranceNumber;
    });
  },

  methods: {
    medicalRecords() {

      api.getMedicalRecordForPatient(this.user.id).then(response => {
        console.log(response.data)
        this.medicalRecord = response.data;
      }).catch(e => {
        console.log(e);
      })

    },

    computed: {
      state() {
        return Boolean(this.form.quantityOption);
      }
    }
  }
}
</script>

<style>
  .field{
    margin-left: 20px;
  }
  .forma {
    margin-right: 600px;
    display: flex;
  }
  .field {
    width: 600px;
  }
  .button {
    width: 300px;
    height: 200px;
    margin-top: 300px;
    margin-left: 200px;

  }
  .previewText {
    margin-top: 50px;
    font-size: 35px;
    text-align: center;
    margin-left: 100px;
  }
  .forma1 {
    margin-top: 50px;
    margin-left: 300px;

  }
</style>
