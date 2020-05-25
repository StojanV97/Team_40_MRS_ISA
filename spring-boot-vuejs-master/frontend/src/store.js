import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/backend-api'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        listaObrisanih : [],
        datesEditedRoom : [],
        listOfRooms : [],
        loginSuccess: false,
        loginError: false,
        userName: null,
        userPass: null,
        loggedInUser : {
            username: "",
            id : "",
            firstName: "",
            lastName: "",
            email:"",
            role: ""
        }
    },
    mutations: {
        logIn (state){
            state.loginSuccess = true;
        }
    },
    actions: {
    },
    getters: {
        getRole : state => state.loggedInUser.role,
        GetlistOfRooms : state => state.listOfRooms,
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass
    }
})