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
                            v-model="clinic.id"
                            label="Id"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="clinic.name"
                            :counter="30"
                            :rules="nameRules"
                            label="Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="clinic.address"
                            label="Address"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="clinic.administrator"
                            label="Administrator"
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
                            @click="editClinic()"
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
        name: "ClinicEditForm",
        data() {

            return {
                msg: '',
                snackbar : false,
                flag : false,
                form:{
                    quantityOption: null
                },
                valid: true,
                clinic: {
                    name: '',
                    address: '',
                    administrator:'',
                    id: ''
                },
                nameRules: [
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 10) || 'Name must be less than 10 characters',
                ],
                select: null,
                checkbox: false,
                flag: false,

            }
        },
        mounted(){
            api.getClinic("1").then(response => {
                this.clinic.name = response.data.name
                this.clinic.id = response.data.id
                this.clinic.address = response.data.address
                this.clinic.administrator = response.data.administrator
            })

        },

        methods: {

            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            editClinic() {
                api.deleteClinic(this.clinic.id).then(response => {
                    api.createClinicAgain(this.clinic).then(response => {
                        // JSON responses are automatically parsed.
                        this.response = response.data;
                        console.log(response.data);
                        this.msg = 'Clinic successfully added!'
                        this.snackbar = true;
                        //this.$router.push('home')
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