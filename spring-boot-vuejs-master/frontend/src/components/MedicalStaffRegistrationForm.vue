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
                            v-model="name"
                            :counter="10"
                            :rules="nameRules"
                            label="First Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="lastname"
                            :counter="10"
                            :rules="nameRules"
                            label="Last Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                    ></v-text-field>
                        <v-radio-group v-model="row" row>
                            <v-radio label="Nurse" value="radio-1"></v-radio>
                            <v-radio label="Doctor" value="radio-2"></v-radio>
                            <v-radio label="Bla Bla" value="radio-3"></v-radio>
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
                            @click="validate"
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


    export default {
        name: "MedicalStaffRegistrationForm",
        data () {
            return {
                valid: true,
                name: '',
                lastname: '',
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
            validate () {
                this.$refs.form.validate()
            },
            reset () {
                this.$refs.form.reset()
            },

        },
    }

</script>

<style scoped>

</style>