<template>
    <div id="div_unos">
        <v-row align="center">
            <v-row justify="space-around">
            </v-row>
            <v-form
                    ref="form"
                    v-model="valid"

            >
                <v-text-field
                        v-model="room.roomID"
                        :counter="8"
                        :rules="nameRules"
                        label="Room ID"
                        type="number"
                        required
                ></v-text-field>


                <v-select
                        v-model="room.roomName"
                        :items="items"
                        :rules="[v => !!v || 'Item is required']"
                        label="Item"
                        required
                ></v-select>


                <v-btn
                        :disabled="!valid"
                        color="success"
                        class="mr-4"
                        @click="validate"
                >
                    Add Room
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
    </div>

</template>

<script>
    import api from "../backend-api";

    export default {
        data: () => ({
            msg: '',
            snackbar: false,
            room: {
                roomID: '',
                roomName: null,
                calendar: [],
            },
            valid: true,
            nameRules: [
                v => !!v || 'ID is required',
                v => (v && v.length <= 8) || 'ID must be less than 8 digits',
            ],

            items: [
                'Operation',
                'Examination',

            ],
            listOfDates: [],

        }),

        methods: {
            validate() {
                console.log(this.room);
                api.createRoom(this.room).then(response => {
                    this.response = response.data;
                    if (response.data == "808") {
                        this.msg = 'Room already exists!'
                        this.snackbar = true;
                    } else if (response.data == "800") {
                        this.msg = 'Room successfully added!'
                        this.snackbar = true;
                    }
                })
                    .catch(e => {
                        console.log(e)
                    })
            },
            reset() {
                this.$refs.form.reset()
            },
            resetValidation() {
                this.$refs.form.resetValidation()
            },
        },
    }
</script>
Name


<style scoped>
    #div_unos {
        margin-right: 640px;
        margin-top: 240px;
    }
</style>