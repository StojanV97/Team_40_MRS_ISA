<template>
    <v-app id="inspire">
        <PatientProfile v-if="this.showProfile"></PatientProfile>
        <EditUser v-if="this.showEditProfile"></EditUser>
        <ScheduleHistory v-if="this.showExaminationHistory"></ScheduleHistory>
        <v-card v-if="this.showClinics">
            <v-card-title>
                Clinics
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
                        <td>{{row.item.id}}</td>
                        <td>{{row.item.clinicName}}</td>
                        <td>{{row.item.address}}</td>


                    </tr>
                </template>
            </v-data-table>
        </v-card>


        <v-navigation-drawer
                v-model="drawer"
                :clipped="$vuetify.breakpoint.lgAndUp"
                app
        >
            <v-list dense>
                <template v-for="item in items">
                    <v-row
                            v-if="item.heading"
                            :key="item.heading"
                            align="center"
                    >
                        <v-col cols="6">
                            <v-subheader v-if="item.heading">
                                {{ item.heading }}
                            </v-subheader>
                        </v-col>
                        <v-col
                                cols="6"
                                class="text-center"
                        >
                            <a
                                    href="#!"
                                    class="body-2 black--text"
                            >EDIT</a>
                        </v-col>
                    </v-row>
                    <v-list-group
                            v-else-if="item.children"
                            :key="item.text"
                            v-model="item.model"
                            :prepend-icon="item.model ? item.icon : item['icon-alt']"
                            append-icon=""
                    >
                        <template v-slot:activator>
                            <v-list-item-content >
                                <v-list-item-title>
                                    {{ item.text }}
                                </v-list-item-title>
                            </v-list-item-content>
                        </template>
                        <v-list-item
                                v-for="(child, i) in item.children"
                                :key="i"
                                link
                        >
                            <v-list-item-action v-if="child.icon">
                                <v-icon>{{ child.icon }}</v-icon>
                            </v-list-item-action>
                            <v-list-item-content @click="getOption(item.children[i].text)">
                                <v-list-item-title>
                                    {{ child.text }}
                                </v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-group>
                    <v-list-item
                            v-else
                            :key="item.text"
                            link
                    >
                        <v-list-item-action>
                            <v-icon>{{ item.icon }}</v-icon>
                        </v-list-item-action>
                        <v-list-item-content @click="getOption(item.text)">
                            <v-list-item-title>
                                {{ item.text }}
                            </v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </template>
            </v-list>
        </v-navigation-drawer>
        <v-app-bar app clipped-left>
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
            <v-toolbar-title>Patient Patientic</v-toolbar-title>
            <v-spacer></v-spacer>
            <label for="signout">Sign Out</label>
            <v-btn id="signout" icon @click="signOut">
                <v-icon>mdi-arrow-bottom-right-thick</v-icon>
            </v-btn>
        </v-app-bar>

        <v-content>
            <v-container class="fill-height" fluid>
                <v-row align="center" justify="center">
                    <v-col class="shrink">
                        <v-tooltip right>
                        </v-tooltip>
                    </v-col>
                </v-row>
            </v-container>
        </v-content>

    </v-app>
</template>

<script>

    import PatientProfile from "../Profiles/PatientProfile";
    import ScheduleHistory from "../Scheduling/ScheduleHistory";
    import EditUser from "../Users/EditUser";


    export default {
        components:{
            ScheduleHistory,
            PatientProfile,
            EditUser



        },
        props: {
            source: String,
        },
        data: () => ({
            showProfile : false,
            showEditProfile : false,
            showCalendar: false,
            dialog: false,
            showExaminationHistory : false,
            dialogDaysOff : false,
            drawer: null,
            showClinics:false,
            items: [
                { icon: 'mdi-contacts', text: 'List of clinics' },
                { icon: 'mdi-history', text: 'Examination History' },
                //{ icon: 'mdi-history', text: 'Working calendar' },
                //{ icon: 'mdi-iframe-parentheses', text: 'Request days off' },
                { icon: 'mdi-account-cog', text: 'Profile' },
                { icon: 'mdi-account-cog', text: 'Edit Profile' },
                {
                    icon: 'mdi-cog',
                    'icon-alt': 'mdi-cog',
                    text: 'Settings',
                    model: false,
                    children: [
                        { text: 'Dark mode On/Off' },
                    ],
                },
                { icon: 'mdi-help-circle', text: 'Help' },

            ],
            search: '',
            headers: [
                {
                    text: 'Id Number',
                    align: 'start',
                    sortable: false,
                    value: 'id',
                },
                { text: 'Clinic Name', value: 'clinicName' },
                { text: 'Address', value: 'address' },

                //{ text: 'Medical History', value: 'medicalHistory' },
            ],
            clinics: [
                {
                    id: '1',
                    clinicName: 'Clinic1',
                    address: 'clinic adress1',


                },
                {
                    id: '2',
                    clinicName: 'Clinic2',
                    address: 'clinic adress2',



                },
                {
                    id: '3',
                    clinicName: 'Clinic3',
                    address: 'clinic adress3',



                },
                {
                    id: '4',
                    clinicName: 'Clinic4',
                    address: 'clinic adress4',
                    email: 'genericEmail@lavabit.com',


                },
                {
                    id: '5',
                    clinicName: 'Clinic4',
                    address: 'clinic adress4',
                    email: 'genericEmail@lavabit.com',


                },



            ],

        }),
        methods:{


            getOption(text){
                console.log(text)
                if(text === "Dark mode On/Off"){
                    this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
                    this.calendar = false;
                }else if(text === "Working calendar"){
                    this.showCalendar = true;
                    this.showProfile = false;
                    this.showClinics = false;
                    this.showExaminationHistory = false;
                    this.showEditProfile = false;

                }else if(text === "List of clinics"){
                    this.showClinics = true;
                    this.showProfile = false;
                    this.showCalendar = false;
                    this.showExaminationHistory = false;
                    this.showEditProfile = false;

                }else if(text === "Profile"){
                    this.showClinics = false;
                    this.showCalendar = false;
                    this.showProfile = true;
                    this.showExaminationHistory = false;
                    this.showEditProfile = false;

                }else if(text === "Request days off"){
                    this.dialogDaysOff = true;
                }else if(text === "Examination History"){
                    this.showExaminationHistory = true;
                    this.showProfile = false;
                    this.showCalendar = false;
                    this.showEditProfile = false;

                    this.showClinics = false;

                }else if(text === "Edit Profile"){
                    this.showExaminationHistory = false;
                    this.showProfile = false;
                    this.showCalendar = false;
                    this.showClinics = false
                    this.showEditProfile = true;

                }
                else{
                    this.showExaminationHistory = false;
                    this.showProfile = false;
                    this.showCalendar = false;
                    this.showClinics = false;
                    this.showEditProfile = false;
                }
            },
            showHistoryDialog(){
                this.dialog = true;
            },
            signOut(){
                this.$router.push('/')
            }
        },
        created() {
            this.$vuetify.theme.dark = true
        },
    }
</script>

<style>
    body {
        overflow: hidden;
        height: 100vh;
    }

</style>