import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
//import Bootstrap from '@/components/Bootstrap'
//import User from '@/components/User'

import Protected from '@/components/Protected'
import Login from './components/Users/Login'
import store from './store'
import MedicalStaffRegistrationForm from "./components/Users/MedicalStaffRegistrationForm";
import DeleteUser from "./components/Users/DeleteUser";
import RoomRegistrationForm from "./components/Utility/RoomRegistrationForm";
import RoomConfig from "./components/Utility/RoomConfig";
import EditRoom from "./components/Utility/EditRoom";
import ClinicRegistrationForm from "./components/Utility/ClinicRegistrationForm";
import RegisterClinicAdmin from "./components/Users/RegisterClinicAdmin";
import EditUser from "./components/Users/EditUser";
import RegisterForm from "./components/Users/RegisterForm";
import ClinicCenterAdminRegistrationForm from "./components/Users/ClinicCenterAdminRegistrationForm";
import ClinicCenterAdminEditForm from "./components/Users/ClinicCenterAdminEditForm";
import ClinicEditForm from "./components/Utility/ClinicEditForm";
import DoctorHomePage from "./components/HomePage/DoctorHomePage";
import CCAProfile from "./components/Profiles/CCAProfile";
import PatientHomePage from "./components/HomePage/PatientHomePage";
import CAProfile from "./components/Profiles/CAProfile";


Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        {path: '/', component: Login},
        {path: '/staffregistration', component: MedicalStaffRegistrationForm},
        {path: '/callservice', component: Service},
        {path: '/deleteStaff', component: DeleteUser},
        {path: '/stafregistration', component: MedicalStaffRegistrationForm},
        {path: '/clinicregistration', component: ClinicRegistrationForm},
        {path: '/roomregistration', component: RoomRegistrationForm},
        {path: '/roomconfiguration', component: RoomConfig},
        {path: '/roomedit', component: EditRoom},
        {path: '/regclinicadmin', component: RegisterClinicAdmin},
        {path: '/editUser', component: EditUser},
        {path: '/ccareg', component: ClinicCenterAdminRegistrationForm},
        {path: '/ccaedit', component: ClinicCenterAdminEditForm},
        {path: '/clinicedit', component: ClinicEditForm},
        {path: '/register', component: RegisterForm},
        {path: '/dhomepage', component: DoctorHomePage},
        {path: '/caprofile', component: CAProfile},
        {path: '/ccaprofile', component: CCAProfile},
        {path: '/phomepage', component: PatientHomePage},
        {
            path: '/protected',
            component: Protected,
            meta: {
                requiresAuth: true
            }
        },

        // otherwise redirect to home
        {path: '*', redirect: '/'}
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