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
                    <v-select
                            v-model="select"
                            :items="items"
                            :rules="[v => !!v || 'Item is required']"
                            label="Item"
                            required
                    ></v-select>

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
                            @click="createNewStafMemeber()"
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
        margin-right: 640px;
    }

</style>

<script>
    import api from "./backend-api";

    export default {
        name: "MedicalStaffRegistrationForm",
        data() {

            return {
                flag : false,
                form:{
                    quantityOption: null
                },
                select: null,
                items: [
                    'Nurse',
                    'Doctor',
                ],
                valid: true,
                user: {
                    name: '',
                    lastName: '',
                    userName:'',
                    email: '',
                    id: 0
                },
                selectRules:[

                ],
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
            checkValue(){
                if (this.select == null){
                    flag = false;
                    this.preventDefault();
                }
            },
            changeFlag(){
                this.flag = true;
            },
            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            createNewStafMemeber() {
                api.createStaffMember(this.user.firstName, this.user.lastName, this.user.email,this.user.userName,this.select).then(response => {
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