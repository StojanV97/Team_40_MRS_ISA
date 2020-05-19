<template>
    <v-app id="inspire">

        <ClinicRegistrationForm v-if="this.registerClinic"></ClinicRegistrationForm>
        <RegisterClinicAdmin v-if="this.registerClinicAdmin"></RegisterClinicAdmin>
        <ClinicDeleteForm v-if="this.deleteC"></ClinicDeleteForm>
        <DeleteUser v-if="this.deleteCA"></DeleteUser>
        <ClinicEditForm v-if="this.editC"></ClinicEditForm>
        <ClinicAdminEditForm v-if="this.editCA"></ClinicAdminEditForm>
        <v-card v-if="this.showClinicsAdmin">
            <v-card-title>
                Clinics admins
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
                    :items="cadmins"
                    :search="search"
                    height="554"
            >
                <template v-slot:item="row">
                    <tr>
                        <td>{{row.item.id}}</td>
                        <td>{{row.item.username}}</td>
                        <td>{{row.item.firstName}}</td>
                        <td>{{row.item.lastName}}</td>
                        <td>{{row.item.email}}</td>
                    </tr>
                </template>
            </v-data-table>
        </v-card>

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
                    :headers="headers2"
                    :items="clinics"
                    :search="search"
                    height="554"
            >
                <template v-slot:item="row">
                    <tr>
                        <td>{{row.item.id}}</td>
                        <td>{{row.item.name}}</td>
                        <td>{{row.item.address}}</td>
                        <td>{{row.item.administrator}}</td>
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
            <v-divider></v-divider>
            <v-btn id="requestbtn">Requests</v-btn>
        </v-navigation-drawer>
        <v-app-bar app clipped-left>
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
            <v-toolbar-title>Clinic center administrator</v-toolbar-title>
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

    import ClinicRegistrationForm from "./ClinicRegistrationForm";
    import RegisterClinicAdmin from "./RegisterClinicAdmin";
    import ClinicDeleteForm from "./ClinicDeleteForm";
    import DeleteUser from "./DeleteUser";
    import api from "./backend-api";
    import ClinicEditForm from "./ClinicEditForm";
    import ClinicAdminEditForm from "./ClinicAdminEditForm";
    export default {
        components:{
            ClinicAdminEditForm,
            ClinicEditForm,
            DeleteUser,
            ClinicDeleteForm,
            RegisterClinicAdmin,
            ClinicRegistrationForm

        },
        props: {
            source: String,
        },
        data: () => ({
            registerClinic: false,
            registerClinicAdmin: false,
            deleteC: false,
            deleteCA: false,
            editC: false,
            editCA: false,
            showClinics: false,
            dialog: false,
            dialogDaysOff : false,
            drawer: null,
            showClinicsAdmin:false,
            items: [
                { icon: 'mdi-contacts', text: 'List of clinic admins' },
                { icon: 'mdi-contacts', text: 'List of clinics' },
                {
                    icon: 'mdi-plus-circle-outline',
                    'icon-alt': 'mdi-plus-circle-outline',
                    text: 'Register',
                    model: false,
                    children: [
                        { icon: 'mdi-plus-box', text: 'Register clinic' },
                        { icon: 'mdi-account-plus', text: 'Register clinic admin' },
                    ],
                },
                {
                    icon: 'mdi-minus-circle-outline',
                    'icon-alt': 'mdi-minus-circle-outline',
                    text: 'Delete',
                    model: false,
                    children: [
                        { icon: 'mdi-delete', text: 'Delete clinic' },
                        { icon: 'mdi-account-remove', text: 'Delete clinic admin' },
                    ],
                },
                {
                    icon: 'mdi-pencil',
                    'icon-alt': 'mdi-pencil',
                    text: 'Edit',
                    model: false,
                    children: [
                        { icon: 'mdi-rename-box', text: 'Edit clinic' },
                        { icon: 'mdi-account', text: 'Edit clinic admin' },
                    ],
                },
                {
                    icon: 'mdi-cog',
                    'icon-alt': 'mdi-cog',
                    text: 'Settings',
                    model: false,
                    children: [
                        { text: 'Dark mode On/Off' },
                    ],
                },

            ],
            search: '',
            headers: [
                {
                    text: 'Id Number',
                    align: 'start',
                    sortable: false,
                    value: 'id',
                },
                { text: 'Username', value: 'username' },
                { text: 'First Name', value: 'firstName' },
                { text: 'Last Name', value: 'lastName' },
                { text: 'Email', value: 'email' },
            ],
            headers2: [
                {
                    text: 'Id Number',
                    align: 'start',
                    sortable: false,
                    value: 'id',
                },
                { text: 'Name', value: 'name' },
                { text: 'Address', value: 'address' },
                { text: 'Administrator', value: 'administrator' },
            ],

            clinics: [

            ],
            cadmins: [],
        }),
        mounted() {
            api.getAllClinics().then(response => {
                this.clinics = response.data;
                console.log(this.clinics)
            }).catch( e => {
                    console.log(e);
                }
            );
            api.getAllCAs().then(response => {
                this.cadmins = response.data;
                console.log(this.cadmins)
            }).catch( e => {
                    console.log(e);
                }
            );
        },
        methods:{
            getCAs() {
                api.getAllCAs().then(response => {
                    this.cadmins = response.data;
                    console.log(this.cadmins)
                }).catch( e => {
                        console.log(e);
                    }
                );
            },
            getClinics() {
                api.getAllClinics().then(response => {
                    this.clinics = response.data;
                    console.log(this.clinics)
                }).catch( e => {
                        console.log(e);
                    }
                );
            },
            getOption(text){
                console.log(text);
                if(text === "Dark mode On/Off"){
                    this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
                }else if(text === "List of clinic admins"){
                    this.showClinicsAdmin = true;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.editC = false;
                    this.editCA = false;
                    this.getCAs()
                }
                else if(text === "List of clinics"){
                    this.showClinicsAdmin = false;
                    this.showClinics = true;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.editC = false;
                    this.editCA = false;
                    this.getClinics()
                }else if(text === "Register clinic"){
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = true;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.editC = false;
                    this.editCA = false;
                }else if(text === "Register clinic admin"){
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = true;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.editC = false;
                    this.editCA = false;
                }else if(text === "Delete clinic") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = true;
                    this.deleteCA = false;
                    this.editC = false;
                    this.editCA = false;
                }
                else if(text === "Delete clinic admin") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = true;
                    this.editC = false;
                    this.editCA = false;
                }
                else if(text === "Edit clinic"){
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.editC = true;
                    this.editCA = false;
                }else if(text === "Edit clinic admin"){
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.editC = false;
                    this.editCA = true;
                }
                else{
                    this.showClinics = false;
                    this.showClinicsAdmin = false;
                }
            },
            signOut(){
                this.$router.push('home')
            }
        },
        created() {
            this.$vuetify.theme.dark = false
        },
    }
</script>

<style>
    body {
        overflow: hidden;
        height: 100vh;
    }
    #calendar{
        margin-top: -10px;
    }
</style>
