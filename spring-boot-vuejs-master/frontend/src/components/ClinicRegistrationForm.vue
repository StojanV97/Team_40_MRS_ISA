<template>
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
                v-model="clinic.admin"
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
                @click="createNewClinic()"
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

        <v-btn
                color="warning"
                @click="resetValidation"
        >
            Reset Validation
        </v-btn>
    </v-form>
</template>



<script>
    import api from "./backend-api";

    export default {
        name: "ClinicRegistrationForm",
        data() {

            return {
                flag : false,
                form:{
                    quantityOption: null
                },
                select: null,

                valid: true,
                clinic: {
                    name: '',
                    address: '',
                    admin:'',
                    id: ''
                },
                selectRules:[

                ],
                nameRules: [
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 30) || 'Name must be less than 30 characters',
                ],

                select: null,
                checkbox: false,
                flag: false,
            }
        },
        methods: { checkValue(){
                if (this.select == null){
                    flag = false;
                    this.preventDefault();
                }
            },
            changeFlag(){
                this.flag = true;
            },
            validate () {
                this.$refs.form.validate()
            },
            reset () {
                this.$refs.form.reset()
            },
            resetValidation () {
                this.$refs.form.resetValidation()
            },
            createNewClinic() {
                api.createClinic(this.clinic.id, this.clinic.name, this.clinic.address, this.clinic.admin).then(response => {

                    this.response = response.data;
                    console.log(response.data);
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