<template>
    <v-form
            ref="form"
            v-model="valid"
            class="postavljeno"

    >
        <v-text-field
                v-model="clinic.id"
                label="Id"
                required
                style="width: 450px"
        ></v-text-field>

        <v-text-field
                v-model="clinic.name"
                :counter="30"
                :rules="nameRules"
                label="Name"
                required
                style="width: 450px"
        ></v-text-field>

        <v-text-field
                v-model="clinic.address"
                label="Address"
                required
                style="width: 450px"
        ></v-text-field>

        <v-text-field
                v-model="clinic.administrator"
                label="Administrator"
                required
                style="width: 450px"
        ></v-text-field>

        <v-checkbox
                v-model="checkbox"
                :rules="[v => !!v || 'You must agree to continue!']"
                label="Do you agree?"
                required
        ></v-checkbox>

        <div class="zajedno">
            <v-btn
                    :disabled="!valid"
                    class="mr-4"
                    @click="createNewClinic()"

            >
                Submit
            </v-btn>
            <v-btn
                    class="mr-4"
                    @click="reset"

            >
                Clear
            </v-btn>

            <v-btn
                    @click="resetValidation"

            >
                Reset Validation
            </v-btn>
        </div>

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
    import api from "../backend-api";

    export default {
        name: "ClinicRegistrationForm",
        data() {

            return {
                msg: '',
                snackbar : false,
                flag : false,
                form:{
                    quantityOption: null
                },
                select: null,

                valid: true,
                clinic: {
                    name: '',
                    address: '',
                    administrator:'',
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
                api.createClinic(this.clinic).then(response => {

                    this.response = response.data;
                    console.log(response.data);
                    if(response.data == "postoji") {
                        this.msg = 'Clinic with same username already exists!';
                        this.snackbar = true;
                    }else if(response.data == "upisan"){
                        this.msg = 'Clinic successfully added!'
                        this.snackbar = true;
                    }
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
    .postavljeno {
        margin: 100px 300px;
    }

    .zajedno {
        display: inline-block;
    }

</style>