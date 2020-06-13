<template>
    <v-app>
    <v-card >
        <v-card-title>
            adasdasdas
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
                :items="clinics"
                :search="search"
                height="554"
        >
            <template v-slot:item="row">
                <tr>
                    <td>{{row.item.name}}</td>
                    <td>{{row.item.city}}</td>

                    <td>{{row.item.address}}</td>

                </tr>
            </template>
        </v-data-table>
    </v-card>
    </v-app>
</template>

<script>
    import api from "../backend-api";

    export default {
        name: "AvailableClinics",

        data:()=>({
            headers: [
                { text: 'Clinic Name', value: 'name' },
                { text: 'City', value: 'city'},
                { text: 'Address', value: 'address' },
                //{ text: 'Medical History', value: 'medicalHistory' },
            ],
            clinics: [],
            search: '',


        }),
        mounted() {
            api.getAllClinics().then(response => {
                this.clinics = response.data;
                console.log(this.clinics)
            }).catch( e => {
                    console.log(e);
                }
            );
        },

    }

</script>

<style scoped>

</style>