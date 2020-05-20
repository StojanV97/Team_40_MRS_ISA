<template>
    <div id="app" class="forma">
        <v-app id="inspire">
            <v-card-text class="previewText">Edit your profile</v-card-text>

            <v-form class="forma1"
                    ref="form"
                    v-model="valid"
            >
                <v-text-field class="field"
                        v-model="user.lastName"
                        :counter="10"
                        :rules="nameRules"
                        label="First Name"
                        required
                ></v-text-field>

                <v-text-field class="field"
                        v-model="user.firstName"
                        :counter="10"
                        label="Last Name"
                        required
                ></v-text-field>

                <v-text-field class="field"
                        v-model="user.username"
                        :counter="10"
                        label="UserName"
                        required
                ></v-text-field>

                <v-text-field class="field"
                        v-model="user.email"
                        contenteditable="false"
                        label="E-mail"
                        readonly="1"
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
                        @click="editUser()"
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
    .field{
        width: 600px;
    }
    .previewText{
        margin-top: 50px;
        font-size: 35px;
        text-align: center;
        margin-left: 100px;
    }
    .forma1{
        margin-top:50px;
        margin-left: 300px;
    }

</style>

<script>
    import api from "../backend-api";

    export default {
        name: "EditPatientForm",
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
                    name: '',
                    lastName: '',
                    username:'',
                    email: ''

                },
                nameRules: [
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 10) || 'Name must be less than 10 characters',
                ],
                email: '',
                select: null,
                checkbox: false

            }
        },
        mounted(){
            api.getUser(2).then(response => {
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
            editUser() {
                api.deleteUser(this.user.username);
                console.log("obrisan user");
                api.createPatient(this.user,"Patient").then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data;
                    console.log(response.data)
                    if(response.data == "808"){
                        this.msg = 'User with same username already exists!';
                        this.snackbar = true;
                    }else if(response.data == "800"){
                        this.msg = 'User successfully added!'
                        this.snackbar = true;
                        this.$router.push("Home");
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

<style scoped>

</style>