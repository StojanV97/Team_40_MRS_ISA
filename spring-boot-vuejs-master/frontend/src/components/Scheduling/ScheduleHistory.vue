<template>
    <v-card>
        <v-card-title>
            <v-text-field
                    v-model="search"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>
        </v-card-title>
        <v-data-table
                :headers="headers"
                :items="desserts"
                :search="search"
        ></v-data-table>
    </v-card>
</template>

<script>
    export default {
        name: "AppointmentHistory",
        data () {
            return {
                diagnosisName:'',
                search: '',
                headers: [
                    {
                        text: 'Clinic name',
                        align: 'start',
                        value: 'clinicName',
                    },
                    { text: 'Doctor Name', value: 'doctorFirstName' },
                    { text: 'Doctor Last Name', value: 'doctorLastName' },
                    { text: 'Date', value: 'dateAndTime' },
                    { text: 'Diagnosis', value: this.diagnosisName },
                ],
                appointment:{
                    clinicName:"",
                    doctorFirstName:"",
                    doctorLastName:"",
                    dateAndTime:"",
                    diagnosisName:""
                },
                appointments: [
                ],
            }
        },
        mounted() {
            api.getRe
        }
    }
</script>

<style scoped>

</style>