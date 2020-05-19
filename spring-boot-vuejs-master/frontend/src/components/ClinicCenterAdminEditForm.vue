<template>
    <div id="app" class="forma">
        <v-app id="inspire">
            <v-row align="center">
                <v-row justify="space-around">
                </v-row>
                <v-form
                        ref="form"
                        v-model="valid"
                >
                    <v-text-field
                            v-model="user.firstName"
                            :rules="nameRules"
                            label="First Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.lastName"
                            label="Last Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.username"
                            label="UserName"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                    ></v-text-field>

                    <v-checkbox
                            v-model="checkbox"
                            :rules="[v => !!v || 'You must agree to continue!']"
                            label="Do you agree?"
                            required
                    ></v-checkbox>

                    <v-btn
                            :disabled="!valid"
                            color="success"
                            class="mr-4"
                            @click="editCCA()"
                    >
                        Submit
                    </v-btn>

                    <v-btn
                            color="error"
                            class="mr-4"
                            @click="reset"
                    >
                        Reset Form
                    </v-btn>
                </v-form>
            </v-row>
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
    </div>
</template>

<style>
    .forma{
        margin-right: 640px;
    }

</style>

<script>
    import api from "./backend-api";

    export default {
        name: "ClinicCenterAdminEditForm",
        data() {

            return {
                msg: '',
                snackbar : false,
                flag : false,
                form:{
                    quantityOption: null
                },
                valid: true,
                user: {
                    firstName: '',
                    lastName: '',
                    username:'',
                    email: ''

                },
                nameRules: [
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 10) || 'Name must be less than 10 characters',
                ],
                email: '',
                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                ],
                select: null,
                checkbox: false,
                flag: false,

            }
        },
        mounted(){
            api.getUser("5").then(response => {
                this.user.username = response.data.username
                this.user.firstName = response.data.firstName
                this.user.lastName = response.data.lastName
                this.user.email = response.data.email
            })

        },

        methods: {

            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            editCCA() {
                api.deleteUser(this.user.username).then(response => {
                    api.createClinicCenterAdminAgain(this.user, "ClinicCenterAdmin").then(response => {
                        // JSON responses are automatically parsed.
                        this.response = response.data;
                        console.log(response.data);
                        this.msg = 'Clinic center admin successfully edited!'
                        this.snackbar = true;
                    })

                        .catch(e => {
                            console.log(e);
                        })
                });
            }
        },

        computed: {

            state() {
                return Boolean(this.form.quantityOption)
            }
        }
    }

</script>

<style scoped>

</style>