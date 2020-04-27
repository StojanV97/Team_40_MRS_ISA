<template>
    <div id="app" class="forma">
        <v-app id="inspire">
            <v-row align="center">
                <v-row justify="space-around">
                    <v-switch v-model="valid" class="ma-4" label="Valid" readonly></v-switch>
                </v-row>
                <v-form
                        ref="form"
                        v-model="valid"
                        :lazy-validation="lazy"
                >
                    <v-text-field
                            v-model="user.lastName"
                            :counter="10"
                            :rules="nameRules"
                            label="First Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.firstName"
                            :counter="10"
                            label="Last Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="user.email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                    ></v-text-field>
                        <v-radio-group
                                row
                                id="quantityOption"
                                class="individual-button"
                                buttons
                                button-variant="outline-secondary"
                                v-model="form.quantityOption"
                                :options="quantityOptions"
                                :state="state"
                                required
                        >
                            <b-form-invalid-feedback :state="state">Please select one</b-form-invalid-feedback>
                            <v-radio label="Nurse" value="radio-1" ></v-radio>
                            <v-radio label="Doctor" value="radio-2" ></v-radio>

                        </v-radio-group>

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
                            @click="createNewUser()"
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
        </v-app>
    </div>
</template>

<style>
    .forma{
        margin-right: 200px;
    }

</style>

<script>
    import api from "./backend-api";

    export default {
        name: "MedicalStaffRegistrationForm",
        data() {
            return {
                form:{
                    quantityOption: null
                },
                valid: true,
                user: {
                    name: '',
                    lastName: '',
                    email: '',
                    id: 0
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
                lazy: false,
            }
        },
        methods: {
            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            createNewUser() {
                api.createUser(this.user.firstName, this.user.lastName, this.user.email).then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data;
                    this.user.id = response.data;
                    console.log('Created new User with Id ' + response.data);
                    this.showResponse = true
                })
                    .catch(e => {
                        this.errors.push(e)
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