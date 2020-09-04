<template>
    <v-div class="div">
        <v-div class="table1">
            <v-card >
                <v-card-title>
                    Choose a clinic for their available appointments.
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
                        :headers="headerClinics"
                        :items="this.clinics"
                        :search="search"
                        height="554"

                >
                    <template v-slot:item="row">
                        <tr>
                            <td class="nameRow">{{row.item.name}}</td>
                            <td>{{row.item.address}}</td>
                            <td class="starRating"><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon></td>
                            <td>
                                <v-btn @click="getExistingAppointmentsForClinic(row.item.id)"><v-icon>mdi-arrow-right-bold-circle</v-icon></v-btn>
                            </td>


                        </tr>
                    </template>
                </v-data-table>
            </v-card>
        </v-div>
        <v-div>
            <v-card-title>
                Available Appointments
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
                        <td>50€</td>
                        <td>
                            <v-btn  @click="scheduleAppointment(row.item.appointmentID)"><v-icon>  mdi-checkbox-marked-circle-outline</v-icon></v-btn>
                        </td>


                    </tr>
                </template>
            </v-data-table>
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
    </v-div>
</template>

<script>
    import api from "../backend-api";

    export default {
        name: "SchedulePremadeAppointments",
        data:() =>({
            snackbar: false,
            msg: "",
            cancelable: false,
            search:'',
            headers: [
                { text: 'Date ', value: 'dateAndTime'},
                { text: 'Doctor First Name', value: 'doctorFirstName' },
                { text: 'Doctor Last Name', value: 'doctorLastName'},
                { text: 'Clinic Name', value: 'clinicName' },
                { text: 'Clinic Address', value: 'clinicAddress' },
                { text: 'Appointment type', value: 'appointmentType' },
                { text: 'Price' },
                { text: 'Schedule this appointment'}


            ],
            headerClinics: [
                { text: 'Clinic Name ', value: 'name'},
                { text: 'Clinic Address', value: 'addrsss' },
                { text: 'Clinic Rating', value: 'grade'},

                { text: 'Show available appointments'}


            ],
            clinics: [],
            appointments:[]


        }),
        mounted(){
            api.getAllClinics().then(response =>{

                    this.clinics = response.data;

                }

            )
            api.setAuthentication().defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
            //

        },
        methods:{
            getExistingAppointmentsForClinic(clinicID){

                api.getExistingAppointmentsForClinic(clinicID).then(response =>{
                        this.appointments = response.data;

                    }

                )

            },
            scheduleAppointment(id){
                api.scheduleExistingAppointment(id,localStorage.getItem('userID')).then(response=>{
                    if(response.data == 'OK')
                    {
                        this.msg = 'Successfully scheduled an appointment!';
                        this.snackbar = true;
                        this.appointments = [];



                    }else {
                        this.msg = 'Whooops! Something went wrong!';
                        this.snackbar = true;
                    }

                })
            },


        }

    }
</script>

<style scoped>
    .div{
        display:flex
    }

    .starRating{
        width:25%;
    }
    .table1{
        width: 40%;
        margin-left: 30px;
        margin-right: 20px;
    }
    .nameRow{
        width:35%;
    }
</style>