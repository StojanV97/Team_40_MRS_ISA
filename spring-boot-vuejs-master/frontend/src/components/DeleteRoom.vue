
<template>
    <v-app id="inspire">
        <div v-if="tableRequestsDisabled">
            <b-table
                    hover
                    :items="requests"
                    :fields="fielsRequests"
                    ref="selectableTable"
                    responsive="sm">
                <template v-slot:cell(Approve)="row">
                    <b-button id="approve" @click="approveRequest(row.item.email,row.item.userName)" class="mr-2">
                        Approve
                    </b-button>
                </template>
                <template v-slot:cell(Decline)="row">
                    <b-button  @click="declineRequest(row.item.userName)" class="mr-2">
                        Decline
                    </b-button>
                </template>
            </b-table>
        </div>
        <div v-if="tableDisabled">
            <b-table
                    hover
                    :items="clinics"
                    :fields="fields"
                    ref="selectableTable"
                    selectable
                    :select-mode="multi"
                    @row-selected="onRowSelected"
                    responsive="sm">
                <template v-slot:cell(show_details)="row">
                    <b-button  @click="editRoom(row.item.roomID,row.item.roomName,row.item.calendar)" class="mr-2">
                        Edit Room
                    </b-button>
                </template>
            </b-table>
        </div>
        <v-navigation-drawer
                v-model="drawer"
                :clipped="$vuetify.breakpoint.lgAndUp"
                app
        >
            <v-list dense>
                <h3>Rooms</h3>
                <v-divider></v-divider>
                <template v-for="item in items">
                    <v-row
                            v-if="item.heading"
                            :key="item.heading"
                            align="center"
                    >
                    </v-row>
                    <v-list-item
                            v-else
                            :key="item.text"
                            link
                    >
                        <v-list-item-action>
                            <v-icon>{{ item.icon }}</v-icon>
                        </v-list-item-action>
                        <v-list-item-content @click="showClinic(item.text)">
                            <v-list-item-title>
                                {{ item.text }}
                            </v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </template>
            <v-divider></v-divider>
                <v-btn id="requestbtn" @click="showRequests()">Requests</v-btn>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar
                :clipped-left="$vuetify.breakpoint.lgAndUp"
                app
                color="blue darken-3"
                dark
        >
            <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
            <v-toolbar-title
                    style="width: 300px"
                    class="ml-0 pl-4"
            >
                <span class="hidden-sm-and-down">Admin Configuration</span>
            </v-toolbar-title>
            <v-text-field
                    v-model="search"
                    flat
                    type="number"
                    solo-inverted
                    hide-details
                    prepend-inner-icon="mdi-magnify"
                    label="Search"
                    class="hidden-sm-and-down"
                    @change="filterRooms($event)"
            />
            <label id="labela">Admin Admin</label>
            <v-spacer/>
            <v-btn @click="deleteRooms()" icon>
                <v-icon>mdi-delete</v-icon>
            </v-btn>
            <v-btn
                    icon
                    large
            >
            </v-btn>
        </v-app-bar>
        <v-content>
            <v-container
                    class="fill-height"
                    fluid
            >
                <v-row
                        align="center"
                        justify="center"
                >
                </v-row>
            </v-container>
        </v-content>
        <v-btn
                bottom
                color="blue"
                dark
                fab
                fixed
                right
                @click="dialog = !dialog"
        >
            <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-dialog
            v-model="dialog"
            width="800px"
    >
        <v-card>
            <v-card-title class="grey darken-2">
                Create room
            </v-card-title>
            <v-container>
                <RoomRegistrationForm id="room_dialog"/>
            </v-container>
            <v-card-actions>
                <v-spacer />
                <v-btn
                        text
                        color="primary"
                        @click="closeDialog()"
                >Close</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

        <v-dialog
                v-model="dialogEditRoom"
                width="600"

        >
            <v-card>
                <v-card-title class="grey darken-2">
                    Edit room
                </v-card-title>
                <v-container id="container">
                    <EditRoom class="child" ref="EditRoom" v-bind:roomNameProp="this.roomNameProp"  v-bind:roomID="this.roomIDEdit" v-bind:list="this.clinics" id="editForm"/>
                </v-container>
                <v-card-actions>
                    <v-spacer />
                    <v-btn
                            text
                            color="primary"
                            @click="closeDialog()"
                    >Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
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
    </v-app>
</template>

<style scoped>
    #requestbtn{
        width: 250px;
    }
    #container{

    }
    #approve{
        background-color: green ;
    }

    #room_dialog{
        margin-right: 240px;
    }
    #labela{
        margin-left: 100px;
        font-size: 20px;
    }
    #editForm{
        margin-right: 140px;

    }
    #datumi{
        margin-top: 13px;

    }
</style>

<script>
    import api from "./backend-api";
    import EditRoom from "./EditRoom"
    import RoomRegistrationForm from "./RoomRegistrationForm";



    export default {
        components: {
            RoomRegistrationForm,
            EditRoom,
        },
        props: {
            source: String,
        },
        data: () => ({
            search : '',
            dialog: false,
            dialogEditRoom: false,
            drawer: null,
            fielsRequests : ['firstName','lastName','email','userName' , 'Approve','Decline'],
            requests : [

            ],
            fields: ["roomID","roomName","calendar","show_details"],
            room : {
                roomID: '',
                roomName: null,
                calendar: [],
            },
            clinics:[],
            items: [
                {icon: 'mdi-home',text: 'Klinika1'},
                {icon: 'mdi-home',text: 'Klinika2'},
                {icon: 'mdi-home',text: 'Klinika3'},
                {icon: 'mdi-home',text: 'Klinika4'},
                {icon: 'mdi-home',text: 'Klinika5'},
            ],
            tableDisabled : false,
            selected: [],
            filteredList: [],
            originalList : [],
            roomIDEdit : 0,
            roomNameProp : null,
            calendarProp : [],
            tableRequestsDisabled : false,
            snackbar : false,
            msg : '',
        }),

        methods : {
            declineRequest(userName){
                for(let i = 0; i < this.requests.length ; i++){
                    if(userName == this.requests[i].userName){
                        this.requests.splice(i,1);
                    }
                }
                api.deleteRegistrationRequest(userName).then(response=>{
                        console.log(response);
                }).catch(e => {
                    console.log(e)
                });


            }
            ,
            approveRequest(email,userName){

                console.log(email)
                api.sendEmail(email).then(response => {
                    console.log(response.data)

                }).catch(e => {
                    this.msg = "Activation code sent!";
                    this.snackbar = true;
                })
                for(let i = 0; i < this.requests.length ; i++){
                    if(userName == this.requests[i].userName){
                        this.requests.splice(i,1);
                    }
                }
                api.deleteRegistrationRequest(userName).then(response=>{
                    console.log(response);
                }).catch(e => {
                    console.log(e)
                });
            }
            ,
            closeDialog(){
                if(this.dialogEditRoom){
                    this.dialogEditRoom = false;
                }
                this.room.roomID = this.roomIDEdit;
                this.room.roomName = this.roomNameProp;
                this.room.calendar =  this.$store.datesEditedRoom
                this.dialog = false;
                this.getRooms();
            }
            ,
            editRoom(event,name,calendar){
                this.$store.datesEditedRoom = calendar;
                this.$store.listaObrisanih = calendar;
                this.calendarProp = calendar;
                this.dialogEditRoom = !this.dialogEditRoom;
                this.roomIDEdit = parseInt(event);

                if(name === "EXAMINATION"){
                    this.roomNameProp = "Examination";
                }else{
                    this.roomNameProp = "Operation";
                }
                this.$refs.EditRoom.setFieldValues(this.roomIDEdit,this.clinics,this.calendarProp);
            }
            ,
            filterRooms(event){
                if(event == ""){
                    this.getRooms();
                    this.filteredList = [];
                }else{
                    for(var i = 0 ; i <= this.clinics.length ; i++){
                        if (parseInt(event) === parseInt(this.clinics[i].roomID)){
                            this.filteredList.push(this.clinics[i])
                        }else{
                            this.clinics.splice(i,1);
                        }
                    }

                }
                if(this.filteredList.length !== 0){
                    console.log(this.clinics)
                }


            }
            ,
            deleteRooms(){
                if(this.selected.length !== 0){
                    for(var i = 0 ; i < this.selected.length ; i++){
                        api.deleteRoom(this.selected[i].roomID).then(response =>{
                            this.getRooms();
                        });
                    }
                }
            }
            ,
            onRowSelected(items) {
                this.selected = items;
            }
            ,
            showClinic(text){
                if (text === "Klinika1"){
                    this.tableDisabled = true;
                    this.tableRequestsDisabled = false;
                }else{
                    this.tableDisabled = false;
                }
            },
            showRequests(){
                if(!this.tableRequestsDisabled){
                   this.tableRequestsDisabled = true;
                   this.tableDisabled = false;
                }else{
                    this.tableRequestsDisabled = false;
                }
                console.log(this.requests)

            },

            getRooms(){
                api.getAllRooms().then(response => {
                    this.clinics = response.data;
                    this.$store.state.listOfRooms = this.clinics;
                    console.log(this.clinics)

                }).catch( e => {
                        console.log(e);
                    }

                )
            }
        },
        mounted() {
            api.getAllRooms().then(response => {
                this.clinics = response.data;
                this.originalList = response.data;
                this.$store.state.listOfRooms = this.clinics;
                console.log(this.$store.listOfRooms)
            }).catch( e => {
                console.log(e);
                }
            );
            api.getAllRegistrationRequests().then(response=>{
                this.requests = response.data;
                console.log(this.requests)
            }).catch( e => {
                console.log(e);
            })

        }
    }
</script>

