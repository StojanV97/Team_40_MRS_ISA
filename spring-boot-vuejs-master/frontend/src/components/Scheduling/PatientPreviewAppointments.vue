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
                    <td>{{row.item.diagnosis}}</td>

                </tr>
            </template>
        </v-data-table>
    </v-grid>
</template>

<script>
    import api from "../backend-api";

    export default {
        name: "PatientPreviewAppointments",
        data:() =>({
            search:'',
            headers: [
                { text: 'Date ', value: 'dateAndTime'},
                { text: 'Doctor First Name', value: 'doctorFirstName' },
                { text: 'Doctor Last Name', value: 'doctorLastName'},
                { text: 'Clinic Name', value: 'clinicName' },
                { text: 'Clinic Address', value: 'clinicAddress' },
                { text: 'Appointment type', value: 'appointmentType' },
                { text: 'Diagnosis', value: 'diagnosis' },

                //{ text: 'Medical History', value: 'medicalHistory' },
            ],
            appointments:[],

        }),
        mounted(){
            api.setAuthentication().defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
            api.getAppointmentPreview(localStorage.getItem('userID')).then(response =>{
                this.appointments = response.data;
            })


        }
    }
</script>

<style scoped>

</style>