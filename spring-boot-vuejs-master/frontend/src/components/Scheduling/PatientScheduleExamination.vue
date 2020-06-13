
<template>
    <v-div class="div">

        <v-div class="div1">
        <v-row class="date" justify="center">Choose a date</v-row>
        <v-row  justify="center">
            <v-date-picker v-model="selectedDate"   :min="nowDate" ></v-date-picker>
            <b-button class="button" @click=submit>Submit</b-button>
        </v-row>
        </v-div>
        <v-div>
        <v-card class="table1">
            <v-card-title>
                Clinics with available doctors for that date.
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
                    :items="this.clinics"
                    :search="search"
                    height="554"
            >
                <template v-slot:item="row">
                    <tr>
                        <td>{{row.item.name}}</td>
                        <td>{{row.item.city}}</td>

                        <td>{{row.item.address}}</td>
                        <td>{{row.item.grade}}</td>
                        <td>
                            <v-btn :icon=true @click="showDoctors(row.item.id)"><v-icon>mdi-arrow-right-bold-circle</v-icon></v-btn>
                        </td>


                    </tr>
                </template>
            </v-data-table>
        </v-card>

        </v-div>
        <v-div>
            <v-card class="table2" >
                <v-card-title>
                    Available doctors from that clinic for that date.
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
                        disabled="true"
                        :headers="headersDoctors"
                        :items="doctors"
                        :search="search"
                        height="554"
                >
                    <template v-slot:item="row">
                        <tr>
                            <td>{{row.item.firstName}}</td>
                            <td>{{row.item.lastName}}</td>

                            <td>{{row.item.grade}}</td>
                            <td>
                                <v-btn :icon=true @click="submitRequest(row.item.id)"><v-icon> mdi-checkbox-marked-circle-outline</v-icon></v-btn>
                            </td>


                        </tr>
                    </template>
                </v-data-table>
            </v-card>
        </v-div>
        <div class="text-center ma-2">
            <v-snackbar
                    v-model="snackbar"
            >
                {{msg}}
                <v-btn
                        @click="snackbar = false"
                        color="pink"
                        text
                >
                    Close
                </v-btn>
            </v-snackbar>
        </div>
    </v-div>

</template>

<script>
    import api from "../backend-api";
    import AvailableClinics from "./AvailableClinics";

    export default {
        components:{
            AvailableClinics
        },

        data: () => ({
            search:'',
            dialog: false,
            nowDate: new Date().toISOString().slice(0,10),
            date: new Date(),
            picker: new Date().toISOString().substr(0, 10),
            doctorsEnabled: true,
            msg: '',
            snackbar: false,
            clinics:[],
            doctors : [],
            selectedClinic:"",
            selectedDoctor:"",
            selectedDate:"",
            clinic:[],
            headers: [
                { text: 'Clinic Name', value: 'name', align:'start'},
                { text: 'City', value: 'city'},
                { text: 'Address', value: 'address' },
                { text: 'Average rating', value: 'rating'},
                { text: 'Select'}
            ],
            headersDoctors: [
                { text: 'First Name', value: 'firstName' },
                { text: 'Last Name', value: 'lastName'},
                { text: 'Average rating', value: 'rating'},
                { text: 'Choose a doctor'}

            ],
            appointmentRequest:{
                dateAndTime:'',
                type:'EXAMINATION',
                clinicID:'',
                patientID:'',
                doctorID:'',

            },

        }),
        mounted() {
            api.setAuthentication().defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
        },
        methods:
        {

            closeDialog(){
              this.dialog = false;

            },
            submit(){
               api.getClinicForDate(this.selectedDate).then(response =>{
                   this.doctors = [];
                   this.clinics = response.data;
                   this.appointmentRequest.dateAndTime=this.selectedDate;
                    }

                )

            },showDoctors(id){
                this.appointmentRequest.clinicID=id;
                api.getClinicDoctorsForDate(this.selectedDate,id).then(response =>{
                    this.doctors = response.data;
                }

                )


            },
            submitRequest(doctorId){

                this.appointmentRequest.patientID = Number(localStorage.getItem('userID'));
                this.appointmentRequest.doctorID = doctorId;
                console.log(this.appointmentRequest);
                api.createAppointmentRequest(this.appointmentRequest.dateAndTime,this.appointmentRequest.type,this.appointmentRequest.clinicID, this.appointmentRequest.patientID,this.appointmentRequest.doctorID).then(response =>{

                    this.msg = 'Appointment request successfully submitted! Check your email for verification.';
                    this.snackbar = true;

                    this.clinics=[];
                    this.doctors=[];
                    this.selectedDate='';
                    }
                ).catch(e=>{
                    console.log(e)
                })
            }





        }


    }
</script>

<style scoped>
    .div{
        display: flex;

    }
    .div1{
        width: 20%;
    }
    .button{
        margin-top:5%;
        margin-left: 5%;
    }
.date{
    margin-top:30px;
    margin-bottom:10px;
}
.table1{
    margin-left: 20px;
    margin-right: 20px
;
}

</style>