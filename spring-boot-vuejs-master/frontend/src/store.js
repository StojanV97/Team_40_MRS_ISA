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
        loggedInUser: null,
        datum: null,
    },
    plugins: [createPersistedState()],

    mutations: {
        logIn(state) {
            state.loginSuccess = true;
        },
        setClinic(state, newClinic) {
            state.clinic = newClinic;
        },
        setUser(state, user) {
            state.loggedInUser = user;
        },
        setUserID(state, id) {
            state.loggedInUser.id = id;
        },
        setDatum(state, datum) {
            state.datum = datum;
        }
    },
    actions: {
    },
    getters: {
        getDatum: state => state.datum,
        getUser: state => state.loggedInUser,
        getClinic: state => state.clinic,
        getRole: state => state.loggedInUser.role,
        GetlistOfRooms: state => state.listOfRooms,
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass
    }
})