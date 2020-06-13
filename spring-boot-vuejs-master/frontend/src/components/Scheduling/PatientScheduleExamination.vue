
<template>
    <v-div class="div">
<!--        <v-row align="center">-->
<!--            <v-col cols="12" sm="6">-->
<!--                <v-subheader v-text="'Choose a clinic first'"></v-subheader>-->
<!--            </v-col>-->
<!--            <v-col cols="12" sm="6">-->
<!--                <v-select-->
<!--                    v-model="selectedClinic"-->
<!--                    :items="clinics"-->
<!--                    item-text="name"-->
<!--                    item-value="id"-->
<!--                    @change="onChange"-->


<!--               ></v-select>-->
<!--            </v-col>-->

<!--            <v-col cols="12" sm="6">-->
<!--                <v-subheader v-text="'Choose a doctor '"></v-subheader>-->
<!--            </v-col>-->

<!--            <v-col cols="12" sm="6">-->
<!--                <v-select-->
<!--                    v-model="selectedDoctor"-->
<!--                    :items="doctors"-->
<!--                    item-text="firstName"-->
<!--                    item-value="id"-->
<!--                >-->
<!--                    <span slot="no-data"> Choose as clinic first. </span>-->
<!--                </v-select>-->
<!--            </v-col>-->
            <!--        </v-row>-->
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
                        :items="clinics"
                        :search="search"
                        height="554"
                >
                    <template v-slot:item="row">
                        <tr>
                            <td>{{row.item.firstName}}</td>
                            <td>{{row.item.lastName}}</td>

                            <td>{{row.item.grade}}</td>

                        </tr>
                    </template>
                </v-data-table>
            </v-card>
        </v-div>
    </v-div>

</template>

<script>
    //YYYY-mm-dd or YYYY-mm
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
            clinics:[],
            doctors : [],
            selectedClinic:"",
            selectedDoctor:"",
            selectedDate:"",
            clinic:[],
            headers: [
                { text: 'Clinic Name', value: 'name' },
                { text: 'City', value: 'city'},
                { text: 'Address', value: 'address' },
                { text: 'Average rating', value: 'rating'}
            ],
            headersDoctors: [
                { text: 'First Name', value: 'firstName' },
                { text: 'Last Name', value: 'lastName'},
                { text: 'Address', value: 'address' },
                { text: 'Average rating', value: 'rating'}

            ],
        }),
        mounted() {

            //klinika ima listu doktora

        },
        methods:
        {

            closeDialog(){
              this.dialog = false;

            },
            submit(){
               api.getClinicForDate(this.selectedDate).then(response =>{
                    console.log("OVDE SEUZIMAJU KLINIKE")
                   console.log(response.data)
                   console.log(this.selectedDate)

                   this.clinics = response.data;

                    }

                )

            },




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