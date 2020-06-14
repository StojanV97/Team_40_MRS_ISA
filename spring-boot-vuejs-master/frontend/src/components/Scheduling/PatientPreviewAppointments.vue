<template>
    <v-grid>
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
                        <v-btn  @click="cancelAppointment(row.item.appointmentID)"><v-icon> mdi-close</v-icon></v-btn>
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
    </v-grid>
</template>

<script>
    import api from "../backend-api";

    export default {
        name: "PatientPreviewAppointments",
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
                { text: 'Cancel an appointment'}
               // { text: 'Diagnosis', value: 'diagnosis' },

                //{ text: 'Medical History', value: 'medicalHistory' },
            ],
            appointments:[],

        }),
        mounted(){
            api.setAuthentication().defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
            api.getAppointmentPreview(localStorage.getItem('userID')).then(response =>{
                this.appointments = response.data;
            })

        },
        methods:{
            cancelAppointment(id){
                api.cancelAppointment(id).then(response=>{
                    console.log(response.data)
                    if(response.data == true)
                    {
                        this.msg = 'Successfully canceled an appointment!';
                        this.snackbar = true;



                    }else {
                        this.msg = 'Can only cancel 24 hours before the appointment!';
                        this.snackbar = true;
                    }

                })
            },


        }

    }
</script>

<style scoped>

</style>