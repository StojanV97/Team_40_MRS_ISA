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
                class="medicalRecordDialog"
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

          <div>

            <v-card class="medicalRecordDialog">
              <div class="medicalRecordDiv">
                <v-card-title
                        primary-title
                >
                  Medical Record
                </v-card-title>
                <v-divider ></v-divider>

                <v-text-field class="field" v-model="this.medicalRecord.allergies" readonly label="Allergies"></v-text-field>
                <v-text-field class="field" v-model="this.medicalRecord.bloodType" readonly label="Blood Type"></v-text-field>
                <v-text-field class="field" v-model="this.medicalRecord.height" readonly label="Height "></v-text-field>
                <v-text-field class="field" v-model="this.medicalRecord.weight" readonly label="Weight "></v-text-field>
              </div>
              <v-divider vertical></v-divider>
              <div class="dataTable">
                <v-card-title>
                  Appointments
                  <v-spacer></v-spacer>

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
                        :items="appointments"
                        :search="search"
                        height="554"
                >
                  <template v-slot:item="row">
                    <tr>
                      <td>{{row.item.dateAndTime}}</td>
                      <td>{{row.item.doctorFirstName}}</td>
                      <td>{{row.item.doctorLastName}}</td>
                      <td>{{row.item.clinicName}}</td>
                      <td>{{row.item.clinicAddress}}</td>
                      <td>{{row.item.appointmentType}}</td>
                      <td>
                        Bronhitis
                      </td>


                    </tr>
                  </template>
                </v-data-table>
              </div>
              <v-card-actions>
                <v-spacer></v-spacer>

              </v-card-actions>
            </v-card>
          </div>
          <v-btn
                  color="red"
                  text
                  @click="dialog = false"
                  height="40px"
          >
            Close
          </v-btn>
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
      search:'',
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
      },
      headers: [
        { text: 'Date ', value: 'dateAndTime'},
        { text: 'Doctor First Name', value: 'doctorFirstName' },
        { text: 'Doctor Last Name', value: 'doctorLastName'},
        { text: 'Clinic Name', value: 'clinicName' },
        { text: 'Clinic Address', value: 'clinicAddress' },
        { text: 'Appointment type', value: 'appointmentType' },
        { text: 'Appointment report'}
        // { text: 'Diagnosis', value: 'diagnosis' },

        //{ text: 'Medical History', value: 'medicalHistory' },
      ],
      appointments:[]
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
    api.getAppointmentPreviewHistory(localStorage.getItem('userID')).then(response =>{
      this.appointments = response.data;
    })
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
    margin-top: 40%;
    margin-left: 50%;

  }
  .previewText {
    margin-top: 20px;
    font-size: 35px;
    text-align: center;
    margin-left: 15%;
  }
  .forma1 {
    margin-top: 50px;
    margin-left: 20%;

  }
  .medicalRecordDialog{
    display:flex;
  }
  .dataTable{
    width: 130%;
    margin-left: 30px;
    margin-right: 30px;
  }
.medicalRecordDiv{
  margin-right: 30px;
  margin-left: 30px;
}
</style>
