
<template>
    <div class="child_div" id="div_left">

        <v-row align="left">
            <v-row justify="space-around">
            </v-row>
            <v-form
                    id="forma"
                    ref="form"
                    v-model="valid">

                <v-text-field
                        v-model="room.roomID"
                        :counter="8"
                        :rules="nameRules"
                        label="Room ID"
                        type="number"
                        required
                ></v-text-field>

                <v-text-field
                        v-model="date"
                        label="Add Date"
                        type="date"
                        required
                ></v-text-field>
                <v-select
                        v-model="room.roomName"
                        :items="items"
                        :rules="[v => !!v || 'Item is required']"
                        label="Item"
                        required
                ></v-select>
                <v-card
                    class="mx-auto"
                    max-width="300"
                    tile="Dates"
                    v-if="this.dates.length > 0"
            >
                <v-toolbar-title id="naslov">Dates</v-toolbar-title>
                <div class="v-list">
                    <div v-for="item in this.dates" :key="item" >
                        <v-list-item>
                            <v-list-item-content>
                                <v-list-item-title id="ajtem" >{{item}} <v-btn @click="deleteDateFromList(item)" class="dugme" icon>
                                    <v-icon  color="blue lighten-1">mdi-delete</v-icon>
                                </v-btn></v-list-item-title>

                            </v-list-item-content>
                        </v-list-item>
                    </div>
                </div>

            </v-card>
                <v-btn
                        color="success"
                        class="mr-4"
                        @click="validate"
                >
                    Confirm Changes
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
    import api from "./backend-api";

    export default {
        components: {

        },

        name : 'EditRoom',
        props: {
            roomID: Number,
            roomNameProp: String,
            calendarProp: Array
        },

        data: () => ({
            msg: '',
            snackbar : false,
            oldID : '',
            date : null,
            room : {
                roomID: '',
                roomName: null,
                calendar: [],
            },
            dateSelected : null,
            dates : [],
            valid: true,
            nameRules: [
                v => !!v || 'ID is required',
                v => (v && v.length <= 8) || 'ID must be less than 8 digits',
            ],
            items: [
                'Operation',
                'Examination',
            ],
        }),
        mounted() {
            this.room.roomID = this.$props.roomID;
            this.room.roomName = this.$props.roomNameProp;
            this.dates = this.$store.datesEditedRoom;
        }
        ,
        methods: {
            deleteDateFromList(event){
                for(let i = 0; i < this.dates.length ; i++) {
                    if (event === this.dates[i]){
                        this.dates.splice(i,1);
                    }
                }
                this.room.calendar = this.dates;
                api.deleteRoom(this.$props.roomID).then(response =>{
                        console.log(response)
                        api.createRoom(this.room).then(response =>{
                             console.log(response)
                            }
                        ).catch(e => {
                            console.log(e)
                        })
                    }
                ).catch(e => {
                    console.log(e)
                })


            },
            setFieldValues(id, lista,datumi){
                this.dates = this.$store.datesEditedRoom ;
                this.oldID = id;
                this.room.roomID = id;
                let option = "";
                for(let i = 0 ; i < lista.length ; i++){
                    if(id == lista[i].roomID){
                        option = lista[i].roomName;
                        break;
                    }
                }
                if(option == "EXAMINATION"){
                    this.room.roomName = "Examination";
                }else{
                    this.room.roomName = "Operation";
                }
            }
            ,
            validate () {
                let currentDate = new Date();
                let inputDate = new Date(this.date);
                console.log(currentDate.getTime())
                console.log(new Date(this.date).getTime())
                this.room.calendar = this.$store.datesEditedRoom;
                if (this.date != null){
                    if(inputDate.getTime() < currentDate.getTime()){
                        this.msg = 'Illegal date!'
                        this.snackbar = true;
                    }else{
                        if(this.room.calendar.indexOf(this.date) === -1){
                            this.room.calendar.push(this.date)
                        }
                    }

                }
                api.deleteRoom(this.$props.roomID).then(response =>{
                        console.log(response)
                    api.createRoom(this.room).then(response =>{
                            if (this.msg !== 'Illegal date!'){
                                this.msg = 'Room Changed!'
                                this.snackbar = true;
                            }

                        }
                    ).catch(e => {
                        console.log(e)
                    })
                    }
                ).catch(e => {
                    console.log(e)
                })
                console.log(this.room.calendar)

            },
            reset () {
                this.$refs.form.reset()
            },
            resetValidation () {
                this.$refs.form.resetValidation()
            },
        },
    }
</script>
Name


<style scoped>
    #div_unos{
        margin-right: 640px;
        margin-top: 240px;
        width: 500px;
    }

    .mr-4{
        margin-top: 15px;
    }
    .v-list {

        height: 250px;
        overflow-y: auto;
    }
    #naslov{
        margin-left: 40px;
    }
    .dugme{
        padding-left: 100px;
    }
    #ajtem{
        padding-left: 30px;
    }
</style>