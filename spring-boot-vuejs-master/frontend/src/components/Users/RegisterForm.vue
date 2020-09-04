<template>
    <div id="app" class="forma">
        <v-app id="inspire">
            <v-row class ="row"  >
                <v-row justify="space-around">
                </v-row>
                <v-form
                        ref="form"
                        v-model="valid"
                >
                    <v-alert
                            border="bottom"
                            color="green"
                            dark
                    >Register here
                    </v-alert>

                    <v-text-field
                            v-model="user.firstName"

                            label="First Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.lastName"

                            label="Last Name"
                            required
                    ></v-text-field>

                    <v-text-field class="text"
                                  v-model="user.userName"

                                  label="UserName *"
                                  required
                    ></v-text-field>


                    <v-text-field
                            v-model="user.country"
                            label="Country"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.city"
                            label="City"
                            required
                    ></v-text-field>
                    <v-text-field
                            v-model="user.address"
                            label="Address"
                            required
                    ></v-text-field>
                    <v-text-field
                            v-model="user.insuranceNumber"
                            label="Unique insurance number *"
                            required
                    ></v-text-field>
                    <v-text-field
                            v-model="user.phoneNumber"
                            label="Phone number"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.email"
                            :rules="emailRules"
                            label="E-mail *"
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
                            @click="createRequest"
                    >
                        Submit
                    </v-btn>

                    <v-btn
                            color="blue"
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
    .forma {
       width: 200px;


    }

    .text {
        width: 500px;
    }
    .row
    {

        margin-left: -10px;
    }



</style>

<script>
    import api from "../backend-api";

    export default {
        name: "MedicalStaffRegistrationForm",
        data() {

            return {
                msg: '',
                snackbar: false,
                form: {
                    quantityOption: null
                },
                valid: true,
                user: {
                    firstName: '',
                    lastName: '',
                    userName: '',
                    email: '',
                    country:'',
                    city:'',
                    address:'',
                    insuranceNumber:'',
                    phoneNumber:''

                },
                message:'',
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
                link: "http://localhost:8080/api/account-verify/"

            }
        },
        methods: {

            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            createRequest() {

                console.log(this.user);
                api.createRequest(this.user).then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data;
                    console.log(response.data)
                    if (response.data == "808") {
                        this.msg = 'User with same username or email or insurance number already exists!';
                        this.snackbar = true;
                    } else if (response.data == "800") {

                        this.msg = 'Request successfully submitted!';
                        this.snackbar = true;

                        this.$refs.form.reset();

                        this.message="http://localhost:8080/api/account-verify/"+this.user.username;

                        api.sendEmail(this.message, this.user.email)

                    }

                    //this.$router.push('home')
                })

                    .catch(e => {
                        console.log(e);
                    })
            }
        },

        computed: {

            state() {
                return Boolean(this.form.quantityOption)
            }
        }
    }

</script>

