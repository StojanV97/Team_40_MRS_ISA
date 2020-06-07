import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/backend-api'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        listaObrisanih: [],
        datesEditedRoom: [],
        listOfRooms: [],
        loginSuccess: false,
        loginError: false,
        clinic: null,
        userName: null,
        userPass: null,
        loggedInUser: {
            username: "",
            id: "",
            firstName: "",
            lastName: "",
            email: "",
            role: ""
        }
    },
    plugins: [createPersistedState()],

    mutations: {
        logIn(state) {
            state.loginSuccess = true;
        },
        setClinic(state, newClinic) {
            state.clinic = newClinic;
        }
    },
    actions: {
    },
    getters: {
        getClinic: state => state.clinic,
        getRole: state => state.loggedInUser.role,
        GetlistOfRooms: state => state.listOfRooms,
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass
    }
})