<template>
    <div id="div_unos">
        <v-row align="center">
            <v-row justify="space-around"></v-row>
            <v-form ref="form" v-model="valid">
                <v-text-field
                        v-model="appointmentReport.appointmentReportId"
                        :counter="8"
                        :rules="nameRules"
                        label="Appointment Report ID"
                        type="number"
                        required
                ></v-text-field>

                <v-text-field
                        v-model="appointmentReport.illness"
                        label="Illness"
                        required
                ></v-text-field>

                <v-text-field
                        v-model="appointmentReport.Description"
                        label="Description"
                        required
                ></v-text-field>

                <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">Add</v-btn>

                <v-btn color="error" class="mr-4" @click="reset">Reset Form</v-btn>
            </v-form>
        </v-row>
        <div class="text-center ma-2">
            <v-snackbar v-model="snackbar">
                {{msg}}
                <v-btn @click="snackbar = false" color="pink" text>Close</v-btn>
            </v-snackbar>
        </div>
    </div>
</template>

<script>
    import api from "../backend-api";

    export default {
        data: () => ({
            msg: "",
            snackbar: false,
            appointmentReport: {
                appointmentReportId: "",
                illness: "",
                Description: ""
            },
            valid: true,
            nameRules: [
                v => !!v || "ID is required",
                v => (v && v.length <= 8) || "ID must be less than 8 digits"
            ],
        }),

        methods: {
            validate() {
                api
                    .createAppointmentReport(this.appointmentReport)
                    .then(response => {
                        this.response = response.data;
                        if (response.data == "postoji") {
                            this.msg = "Report already exists!";
                            this.snackbar = true;
                        } else if (response.data == "upisan") {
                            this.msg = "Report successfully added!";
                            this.snackbar = true;
                        }
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            reset() {
                this.$refs.form.reset();
            },
            resetValidation() {
                this.$refs.form.resetValidation();
            }
        }
    };
</script>
Name


<style scoped>
    #div_unos {
        margin-right: 640px;
        margin-top: 240px;
    }
</style>