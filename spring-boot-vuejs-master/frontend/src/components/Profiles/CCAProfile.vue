<template>
    <v-app id="inspire">

        <ClinicRegistrationForm v-if="this.registerClinic"></ClinicRegistrationForm>
        <RegisterClinicAdmin v-if="this.registerClinicAdmin"></RegisterClinicAdmin>
        <ClinicDeleteForm v-if="this.deleteC"></ClinicDeleteForm>
        <DeleteUser v-if="this.deleteCA"></DeleteUser>
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
                        <td>{{row.item.username}}</td>
                        <td>{{row.item.firstName}}</td>
                        <td>{{row.item.lastName}}</td>
                        <td>{{row.item.email}}</td>
                        <td>
                            <v-btn icon="true" @click="editThisCA(row.item.username, row.item.firstName, row.item.lastName, row.item.email)"><v-icon>mdi-pencil</v-icon></v-btn>
                            <v-btn icon="true" @click="deleteThisAdmin(row.item.username)"><v-icon>mdi-account-remove</v-icon></v-btn>
                        </td>
                    </tr>
                </template>
            </v-data-table>
            <v-btn
                    bottom
                    dark
                    fab
                    fixed
                    right
                    @click="dialogACA = !dialogACA"
            >
                <v-icon>mdi-account-plus</v-icon>
            </v-btn>
            <v-dialog
                    v-model="dialogACA"
                    width="1200px"
            >
                <v-card>
                    <v-card-title class="grey darken-2">
                        Create clinic admin
                    </v-card-title>
                    <v-container>
                        <RegisterClinicAdmin id="clinicadmin_dialog"/>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                                text
                                @click="closeDialogAddCA()"
                        >Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog
                    v-model="dialogEditCA"
                    width="1200px"
            >
                <v-card>
                    <v-card-title class="grey darken-2">
                        Edit clinic admin
                    </v-card-title>
                    <v-container>
                        <ClinicAdminEditForm ref="ClinicAdminEditForm" id="clinicadmine_dialog"/>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                                text
                                @click="closeDialogEditCA()"
                        >Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
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
                        <td>
                            <v-btn icon="true" @click="editThisClinic(row.item.id, row.item.name, row.item.address, row.item.administrator)"><v-icon>mdi-pencil</v-icon></v-btn>
                            <v-btn icon="true" @click="deleteThisClinic(row.item.id)"><v-icon>mdi-delete</v-icon></v-btn>
                        </td>
                    </tr>
                </template>
            </v-data-table>
            <v-btn
                    bottom
                    dark
                    fab
                    fixed
                    right
                    @click="dialogAC = !dialogAC"
            >
                <v-icon>mdi-plus-box</v-icon>
            </v-btn>
            <v-dialog
                    v-model="dialogAC"
                    width="1200px"
            >
                <v-card>
                    <v-card-title class="grey darken-2">
                        Create clinic
                    </v-card-title>
                    <v-container>
                        <ClinicRegistrationForm id="clinic_dialog"/>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                                text
                                @click="closeDialogAddC()"
                        >Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog
                    v-model="dialogEditC"
                    width="1200px"
            >
                <v-card>
                    <v-card-title class="grey darken-2">
                        Edit clinic
                    </v-card-title>
                    <v-container>
                        <ClinicEditForm id="clinice_dialog" ref="ClinicEditForm"/>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                                text
                                @click="closeDialogEditC()"
                        >Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-card>

        <v-card v-if="this.showDiagnoses">
            <v-card-title>
                Diagnoses
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
                    :headers="headers3"
                    :items="diagnoses"
                    :search="search"
                    height="554"
            >
                <template v-slot:item="row">
                    <tr>
                        <td>{{row.item.diagnoseId}}</td>
                        <td>{{row.item.diagnoseName}}</td>
                        <td>{{row.item.description}}</td>
                        <td>
                            <v-btn icon="true" @click="deleteThisDiagnose(row.item.diagnoseId)"><v-icon>mdi-delete</v-icon></v-btn>
                        </td>
                    </tr>
                </template>
            </v-data-table>
            <v-btn
                bottom
                dark
                fab
                fixed
                right
                @click="dialogAD = !dialogAD"
        >
            <v-icon>mdi-plus-box</v-icon>
        </v-btn>
            <v-dialog
                    v-model="dialogAD"
                    width="1200px"
            >
                <v-card>
                    <v-card-title class="grey darken-2">
                        Create diagnose
                    </v-card-title>
                    <v-container>
                        <CreateDiagnoseForm id="diagnose_dialog"/>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                                text
                                @click="closeDialogAddD()"
                        >Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-card>

        <v-card v-if="this.showMedicines">
            <v-card-title>
                Medicines
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
                    :headers="headers4"
                    :items="medicines"
                    :search="search"
                    height="554"
            >
                <template v-slot:item="row">
                    <tr>
                        <td>{{row.item.medicineId}}</td>
                        <td>{{row.item.medicineName}}</td>
                        <td>{{row.item.medicineDescription}}</td>
                        <td>
                            <v-btn icon="true" @click="deleteThisMedicine(row.item.medicineId)"><v-icon>mdi-delete</v-icon></v-btn>
                        </td>
                    </tr>
                </template>
            </v-data-table>
            <v-btn
                    bottom
                    dark
                    fab
                    fixed
                    right
                    @click="dialogAM = !dialogAM"
            >
                <v-icon>mdi-plus-box</v-icon>
            </v-btn>
            <v-dialog
                    v-model="dialogAM"
                    width="1200px"
            >
                <v-card>
                    <v-card-title class="grey darken-2">
                        Create medicine
                    </v-card-title>
                    <v-container>
                        <CreateMedicineForm id="medicine_dialog"/>
                    </v-container>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn
                                text
                                @click="closeDialogAddM()"
                        >Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-card>

        <v-card v-if="this.showRequests">
            <v-card-title>
                Requests
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
                    :items="requests"
                    :search="search"
                    height="554"
            >
                <template v-slot:item="row">
                    <tr>
                        <td>{{row.item.userName}}</td>
                        <td>{{row.item.firstName}}</td>
                        <td>{{row.item.lastName}}</td>
                        <td>{{row.item.email}}</td>
                        <td>
                            <v-btn icon="true" @click="approveRequest(row.item.email, row.item.userName)"><v-icon>mdi-checkbox-marked-circle</v-icon></v-btn>
                            <v-btn icon="true" @click="declineRequest(row.item.userName)"><v-icon>mdi-close-circle</v-icon></v-btn>
                        </td>
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

    </v-app>
</template>

<script>

    import ClinicRegistrationForm from "../Utility/ClinicRegistrationForm";
    import RegisterClinicAdmin from "../Users/RegisterClinicAdmin";
    import ClinicDeleteForm from "../Utility/ClinicDeleteForm";
    import DeleteUser from "../Users/DeleteUser";
    import api from "../backend-api";
    import ClinicEditForm from "../Utility/ClinicEditForm";
    import ClinicAdminEditForm from "../Users/ClinicAdminEditForm";
    import CreateDiagnoseForm from "../Utility/CreateDiagnoseForm";
    import CreateMedicineForm from "../Utility/CreateMedicineForm";
    export default {
        components:{
            CreateMedicineForm,
            CreateDiagnoseForm,
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
            showClinics: false,
            dialog: false,
            dialogAC: false,
            dialogACA: false,
            dialogAD: false,
            dialogAM: false,
            dialogEditC: false,
            dialogEditCA: false,
            dialogDaysOff : false,
            drawer: null,
            showClinicsAdmin:false,
            showRequests: false,
            showDiagnoses: false,
            showMedicines: false,
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

                { icon: 'mdi-account-box-outline', text: 'Requests' },
                { icon: 'mdi-pharmacy', text: 'Diagnose codebook' },
                { icon: 'mdi-pharmacy', text: 'Medicine codebook' },
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
                { text: 'Username', value: 'userName' },
                { text: 'First Name', value: 'firstName' },
                { text: 'Last Name', value: 'lastName' },
                { text: 'Email', value: 'email' },
                { text: 'Actions' },
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
                { text: 'Actions' },
            ],
            headers3: [
                {
                    text: 'Id Number',
                    align: 'start',
                    sortable: false,
                    value: 'diagnoseId',
                },
                { text: 'Name', value: 'diagnoseName' },
                { text: 'Description', value: 'description' },
                { text: 'Actions' },
            ],
            headers4: [
                {
                    text: 'Id Number',
                    align: 'start',
                    sortable: false,
                    value: 'medicineId',
                },
                { text: 'Name', value: 'medicineName' },
                { text: 'Description', value: 'medicineDescription' },
                { text: 'Actions' },
            ],
            clinics: [

            ],
            cadmins: [],
            requests: [],
            diagnoses: [],
            medicines: [],
            snackbar: false,
            msg: ''
        }),
        mounted() {
            api.getAllClinics().then(response => {
                this.clinics = response.data;

            }).catch( e => {
                    console.log(e);
                }
            );
            api.getAllCAs().then(response => {
                this.cadmins = response.data;

            }).catch( e => {
                    console.log(e);
                }
            );
            api.getAllRegistrationRequests().then(response => {
                this.requests = response.data;

            }).catch(e => {
                console.log(e);
            });
            api.getAllMedicines().then(response => {
                this.medicines = response.data;

            }).catch(e => {
                console.log(e);
            });
            api.getAllDiagnoses().then(response => {
                this.diagnoses = response.data;
                console.log("dijagnoze")
                console.log(this.diagnoses)
            }).catch(e => {
                console.log(e);
            });
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
            getDiagnoses() {
                api.getAllDiagnoses().then(response => {
                    this.diagnoses = response.data;
                    console.log(this.diagnoses)
                }).catch( e => {
                        console.log(e);
                    }
                );
            },
            getMedicines() {
                api.getAllMedicines().then(response => {
                    this.medicines = response.data;
                    console.log(this.medicines)
                }).catch( e => {
                        console.log(e);
                    }
                );
            },
            getRequests() {
                api.getAllRegistrationRequests().then(response => {
                    this.requests = response.data;
                    console.log(this.requests)
                }).catch(e => {
                    console.log(e);
                });
            },
            deleteThisClinic(i) {
                api.deleteClinic(i).then(response => {
                    this.getClinics();
                }).catch( e => {
                        console.log(e);
                    }
                );
            },
            deleteThisAdmin(u) {
                api.deleteUser(u).then(response => {
                    this.getCAs();
                }).catch( e => {
                        console.log(e);
                    }
                );
            },
            deleteThisMedicine(m) {
                api.deleteMedicine(m).then(response => {
                    this.getMedicines();
                }).catch( e => {
                    console.log(e);
                });
            },
            deleteThisDiagnose(d) {
                api.deleteDiagnose(d).then(response => {
                    this.getDiagnoses();
                }).catch( e => {
                    console.log(e);
                });
            },
            declineRequest(userName) {
                for (let i = 0; i < this.requests.length; i++) {
                    if (userName === this.requests[i].userName) {
                        this.requests.splice(i, 1);
                    }
                }
                api.deleteRegistrationRequest(userName).then(response => {
                    console.log(response);
                    this.getRequests();
                }).catch(e => {
                    console.log(e)
                });
            },
            approveRequest(email, userName) {
                console.log(email);
                api.sendEmail(email).then(response => {
                    console.log(response.data)
                }).catch(e => {
                    this.msg = "Activation code sent!";
                    this.snackbar = true;
                });
                for (let i = 0; i < this.requests.length; i++) {
                    if (userName === this.requests[i].userName) {
                        this.requests.splice(i, 1);
                    }
                }
                api.deleteRegistrationRequest(userName).then(response => {
                    console.log(response);
                    this.getRequests();
                }).catch(e => {
                    console.log(e)
                });
            },
            closeDialogAddC() {
                  this.dialogAC = false;
                  this.getClinics();
            },
            closeDialogAddCA() {
                this.dialogACA = false;
                this.getCAs();
            },
            closeDialogAddD() {
                this.dialogAD = false;
                this.getDiagnoses();
            },
            closeDialogAddM() {
                this.dialogAM = false;
                this.getMedicines();
            },
            closeDialogEditC() {
                this.dialogEditC = false;
                this.getClinics();
            },
            closeDialogEditCA() {
                this.dialogEditCA = false;
                this.getCAs();
            },
            editThisClinic(i, n, a, ad) {
                this.dialogEditC = true;
                this.$refs.ClinicEditForm.setClinicValues(i, n, a, ad);
            },
            editThisCA(u, f, l, e) {

                this.dialogEditCA = true;
                this.$refs.ClinicAdminEditForm.setCAValues(u, f, l, e);
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
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                    this.getCAs()
                }
                else if(text === "List of clinics"){
                    this.showClinicsAdmin = false;
                    this.showClinics = true;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                    this.getClinics()
                }else if(text === "Register clinic"){
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = true;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                }else if(text === "Register clinic admin"){
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = true;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                }else if(text === "Delete clinic") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = true;
                    this.deleteCA = false;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                }
                else if(text === "Delete clinic admin") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = true;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                }
                else if(text === "Requests") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.showRequests = true;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
                }
                else if(text === "Diagnose codebook") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.showRequests = false;
                    this.showDiagnoses = true;
                    this.showMedicines = false;
                }
                else if(text === "Medicine codebook") {
                    this.showClinicsAdmin = false;
                    this.showClinics = false;
                    this.registerClinicAdmin = false;
                    this.registerClinic = false;
                    this.deleteC = false;
                    this.deleteCA = false;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = true;
                }
                else{
                    this.showClinics = false;
                    this.showClinicsAdmin = false;
                    this.showRequests = false;
                    this.showDiagnoses = false;
                    this.showMedicines = false;
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
