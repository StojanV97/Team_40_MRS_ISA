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
                            :counter="10"
                            :rules="nameRules"
                            label="First Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.lastName"
                            :counter="10"
                            label="Last Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.userName"
                            :counter="10"
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
                            class="mr-4"
                            @click="editClinicAdmin()"
                    >
                        Submit
                    </v-btn>

                    <v-btn
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

<script>
    import api from "../backend-api";

    export default {
        name: "ClinicAdminEditForm",
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
                    userName:'',
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


        methods: {
            setCAValues(u, f, l, e) {
                this.user.userName = u;
                this.user.firstName = f;
                this.user.lastName = l;
                this.user.email = e;
            },
            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            editClinicAdmin() {
                api.deleteUser(this.user.userName).then(response => {
                    api.createClinicAdminAgain(this.user, "ClinicAdmin").then(response => {
                        // JSON responses are automatically parsed.
                        this.response = response.data;
                        console.log(response.data);
                        this.msg = 'Clinic admin successfully edited!'
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