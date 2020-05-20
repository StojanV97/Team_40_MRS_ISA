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
        userPass: null
    },
    mutations: {
    },
    actions: {
    },
    getters: {
        GetlistOfRooms : state => state.listOfRooms,
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass
    }
})