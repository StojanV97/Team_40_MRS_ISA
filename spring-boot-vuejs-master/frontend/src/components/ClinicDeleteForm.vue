<template>
    <v-form
            ref="form"
            v-model="valid"

    >
        <v-text-field
                v-model="clinicId"
                label="Id"
                required
        ></v-text-field>


        <v-checkbox
                v-model="checkbox"
                :rules="[v => !!v || 'You must agree to continue!']"
                label="Are you sure?"
                required
        ></v-checkbox>

        <v-btn
                :disabled="!valid"
                color="success"
                class="mr-4"
                @click="deleteClinicWithId()"
        >
            Delete
        </v-btn>

        <v-btn
                color="error"
                class="mr-4"
                @click="reset"
        >
            Reset Form
        </v-btn>


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
    </v-form>
</template>

<script>
    import api from "./backend-api";

    export default {
        name: "ClinicDeleteForm",
        data() {

            return {

                msg: '',
                snackbar: false,
                valid: true,
                clinicId: '',

            }
        },
        methods: {
            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            deleteClinicWithId() {

                api.deleteClinic(this.clinicId).then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data;
                    console.log(response);
                    if(response.data == 'uspesno'){
                        this.msg = 'Clinic deleted';
                        this.snackbar = true;

                    }else{
                        this.msg = 'Clinic with this id does not exist!';
                        this.snackbar = true;

                    }

                })
                    .catch(e => {
                        console.log(e);
                        this.snackbar = true;
                        this.msg = 'Catch executed!';
                    })
            }
        }
    }
</script>

<style scoped>

</style>