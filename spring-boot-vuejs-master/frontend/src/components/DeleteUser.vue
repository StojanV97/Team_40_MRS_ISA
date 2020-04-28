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
                            v-model="user.userName"
                            :counter="10"
                            :rules="nameRules"
                            label="UserName"
                            required
                    ></v-text-field>

                    <v-btn
                            :disabled="!valid"
                            color="success"
                            class="mr-4"
                            @click="deleteStafMember()"
                    >
                        Delete

                    </v-btn>

                    <v-btn
                            color="error"
                            class="mr-4"
                            @click="reset"
                    >
                        Reset
                    </v-btn>
                </v-form>
            </v-row>
        </v-app>
    </div>
</template>

<script>
    import api from "./backend-api";
    import router from "../router";

    export default {
        name: "DeleteUser",
        data() {

            return {


                valid: true,
                user: {
                    userName: '',

                },

                nameRules: [
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 10) || 'Name must be less than 10 characters',
                ],
                retrievedUser: {},
                fullResponse: {
                    config: {
                        foo: '',
                        bar: ''
                    }
                },

            }
        },
        methods: {


            validate() {
                this.$refs.form.validate()
            },
            reset() {
                this.$refs.form.reset()
            },
            deleteStafMember() {
                api.getUserByUserName(this.user.userName).then(response => {
                    // JSON responses are automatically parsed.
                   this.retrievedUser = response.data;
                    console.log(this.retrievedUser);
                    this.$router.push('home')
                })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },

        }
    }
</script>

<style scoped>

</style>