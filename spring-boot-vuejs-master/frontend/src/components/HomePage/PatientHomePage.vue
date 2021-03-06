<template>
    <v-app id="inspire">
        <PatientProfile v-if="this.showProfile"></PatientProfile>
        <EditUser v-if="this.showEditProfile"></EditUser>
        <ScheduleHistory v-if="this.showExaminationHistory"></ScheduleHistory>
        <PatientScheduleExamination v-if="this.showScheduleExamination"></PatientScheduleExamination>
        <PatientPreviewAppointments v-if="this.showAppointments"></PatientPreviewAppointments>
        <SchedulePremadeAppointments v-if="this.showPremadeAppointments" > </SchedulePremadeAppointments>
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
                        <td>{{row.item.name}}</td>
                        <td class="starRating"><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon><v-icon> mdi-star</v-icon></td>

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
            <v-toolbar-title >{{this.firstName + " " +this.lastName}}</v-toolbar-title>
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
    import api from "../backend-api";
    import PatientScheduleExamination from "../Scheduling/PatientScheduleExamination";
    import PatientPreviewAppointments from "../Scheduling/PatientPreviewAppointments";
    import SchedulePremadeAppointments from "../Scheduling/SchedulePremadeAppointments";


    export default {
        components:{
            PatientScheduleExamination,
            ScheduleHistory,
            PatientProfile,
            EditUser,
            SchedulePremadeAppointments,
            PatientPreviewAppointments

        },
        props: {
            source: String,
        },
        data: () => ({
            showAppointments:false,
            showProfile : false,
            showEditProfile : false,
            showScheduleExamination:false,
            showCalendar: false,
            dialog: false,
            showExaminationHistory : false,
            dialogDaysOff : false,
            drawer: null,
            showClinics:false,
            showPremadeAppointments:false,
            items: [
                { icon: 'mdi-account-clock-outline', text: 'Available appointments' },
                { icon: 'mdi-format-list-bulleted', text: 'List of clinics' },
                { icon: 'mdi-history', text: 'My Appointments' },
                { icon: "mdi-needle", text: 'Schedule appointment' },
                //{ icon: 'mdi-history', text: 'Working calendar' },
                //{ icon: 'mdi-iframe-parentheses', text: 'Request days off' },
                { icon: 'mdi-account-box-outline', text: 'Profile and Medical Record' },
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
                { text: 'Clinic Name', value: 'name' },
                { text: 'Clinic rating' },
                { text: 'Address', value: 'address' },
                //{ text: 'Medical History', value: 'medicalHistory' },
            ],
            clinics: [],
            firstName: '',
            lastName: '',


        }),
        mounted() {
            api.setAuthentication().defaults.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token');
            this.firstName = localStorage.getItem('firstName')
            this.lastName = localStorage.getItem('lastName')
            api.getAllClinics().then(response => {
                this.clinics = response.data;
                console.log(this.clinics)
            }).catch( e => {
                    console.log(e);
                }
            );
        },
        methods:{


            getOption(text){

                if(text === "Dark mode On/Off"){
                    this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
                    this.calendar = false;
                }

                else if(text === "List of clinics"){
                    this.showClinics = true;
                    this.showProfile = false;
                    //this.showCalendar = false;
                    this.showAppointments = false;
                    this.showEditProfile = false;
                    this.showScheduleExamination = false;
                    this.showPremadeAppointments = false;

                }else if(text === "Profile and Medical Record"){
                    this.showClinics = false;
                    //this.showCalendar = false;
                    this.showProfile = true;
                    this.showAppointments = false;
                    this.showEditProfile = false;
                    this.showScheduleExamination = false;
                    this.showPremadeAppointments = false;

                }else if(text === "My Appointments"){
                    this.showAppointments = true;
                    this.showProfile = false;
                    //this.showCalendar = false;
                    this.showEditProfile = false;
                    this.showScheduleExamination = false;
                    this.showClinics = false;
                    this.showPremadeAppointments = false;

                }else if(text === "Edit Profile"){
                    this.showAppointments = false;
                    this.showProfile = false;
                    //this.showCalendar = false;
                    this.showClinics = false
                    this.showEditProfile = true;
                    this.showScheduleExamination = false;
                    this.showPremadeAppointments = false;

                }
                else if(text === "Schedule appointment"){
                    this.showAppointments = false;
                    this.showProfile = false;
                    // this.showCalendar = false;
                    this.showClinics = false
                    this.showEditProfile = false;
                    this.showScheduleExamination = true;
                    this.showPremadeAppointments = false;

                }
                else if(text === "Available appointments"){
                    this.showAppointments = false;
                    this.showProfile = false;
                    // this.showCalendar = false;
                    this.showClinics = false
                    this.showEditProfile = false;
                    this.showScheduleExamination = false;
                    this.showPremadeAppointments = true;

                }

                else{
                    this.showAppointments = false;
                    this.showProfile = false;
                    // this.showCalendar = false;
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