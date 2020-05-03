import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
//import Bootstrap from '@/components/Bootstrap'
//import User from '@/components/User'
import Login from '@/components/Login'
import Protected from '@/components/Protected'

import store from './store'
import MedicalStaffRegistrationForm from "./components/MedicalStaffRegistrationForm";
import DeleteUser from "./components/DeleteUser";
import ClinicRegistrationForm from "./components/ClinicRegistrationForm";
import RegisterClinicAdmin from "./components/RegisterClinicAdmin";
import EditUser from "./components/EditUser";

Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        { path: '/', component: Hello },
        { path: '/staffregistration', component: MedicalStaffRegistrationForm },
        { path: '/callservice', component: Service },
        { path: '/deleteStaff', component: DeleteUser },
        { path: '/stafregistration', component: MedicalStaffRegistrationForm },
        { path: '/clinicregistration', component: ClinicRegistrationForm},
        { path: '/login', component: Login },
        { path: '/regclinicadmin', component: RegisterClinicAdmin },
        { path: '/editUser', component: EditUser },
        {
            path: '/protected',
            component: Protected,
            meta: {
                requiresAuth: true
            }
        },

        // otherwise redirect to home
        { path: '*', redirect: '/' }
    ]
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            next({
                path: '/login'
            })
        } else {
            next();
        }
    } else {
        next(); // make sure to always call next()!
    }
});

export default router;