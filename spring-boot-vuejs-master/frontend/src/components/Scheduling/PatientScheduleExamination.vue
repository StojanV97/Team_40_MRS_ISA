
<template>
    <v-div class="div">

        <v-div class="div1">
            <v-row class="date-text" justify="center">Choose a date</v-row>
            <v-row class="calendar-container" justify="center">
                <v-date-picker class="calendar" v-model="selectedDate"   :min="nowDate" ></v-date-picker>
                <b-button class="submit-button" @click=submit>Submit</b-button>
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
                        <td class="starRating"><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon></td>
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

                            <td max-width="30px"  >
                                <v-col  class="selectType" >
                                    <v-select v-model="appointmentTypes"
                                            :items="appointmentTypes"
                                            @change="enableButton"

                                    ></v-select>
                                </v-col>
                            </td>
                            <td>
                                <v-btn  @click="submitRequest(row.item.id)"><v-icon> mdi-checkbox-marked-circle-outline</v-icon></v-btn>
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
            appointmentTypes:"EXAMINATION ( price:50€ )",
            search:'',
            disabledSubmit:true,
            price:50,
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
                { text: 'Appointment Type', value: 'rating'},
                { text: 'Finalize Scheduling '}

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
            enableButton(){
                this.disabledSubmit= !this.disabledSubmit;
            }
            ,
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
        width: fit-content;
        height: fit-content;
        display: flex;
        flex-direction: column;
    }

    .date-text{
        text-align: center;
        margin-bottom: -30px;
    }

    .calendar-container{
        margin-right: 30px;
        margin-left: 30px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }

    .calendar{

    }

    .submit-button{
        width: 70%;
        margin-top: 20px;
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
        margin-right: 20px;
        margin-right: 20px;
    }

</style>