import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
//import Bootstrap from '@/components/Bootstrap'
//import User from '@/components/User'

import Protected from '@/components/Protected'
import Login from './components/Users/Login'
import RoomConfig from "./components/Utility/RoomConfig";
import EditUser from "./components/Users/EditUser";
import DoctorHomePage from "./components/HomePage/DoctorHomePage";
import CCAProfile from "./components/Profiles/CCAProfile";
import PatientHomePage from "./components/HomePage/PatientHomePage";
import CAProfile from "./components/HomePage/ClinicAdminHomePage";
import NurseHomePage from "./components/HomePage/NurseHomePage";
import FirstLogIn from "./components/Users/FirstLogIn"
import PatientScheduleExamination from "./components/Scheduling/PatientScheduleExamination";
import PProfilePreview from "./components/Profiles/PProfilePreview"
import ScheduleHistory from "./components/Scheduling/ScheduleHistory";
import PredefinedClinicCenterAdmin from "./components/Profiles/PredefinedClinicCenterAdmin";


Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [{
        path: '/account-verify/77777777', component: ScheduleHistory
    }
        , {
        path: '/', component: Login,
        beforeEnter(to, from, next) {
            localStorage.setItem('loggedIn', 'false');
            localStorage.setItem('token', '');
            localStorage.setItem('firstName', '');
            localStorage.setItem('lastName', '');
            localStorage.setItem('userName', '');
            localStorage.setItem('clinicID', '');
            next();
        }
    },
    {
        path: '/room-config', component: RoomConfig,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/editUser', component: EditUser,
        meta: {
            requiresAuth: false
        }
    },
    {
        path: '/doctor-homepage', component: DoctorHomePage,
        beforeEnter(to, from, next) {
            if (localStorage.getItem('role') === 'DOCTOR') {
                next();
            } else {
                next('/')
            }
        },
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/clinic-admin-profile', component: CAProfile,
        beforeEnter(to, from, next) {
            if (localStorage.getItem('role') === 'CLINIC_ADMIN') {
                next();
            } else {
                next('/')
            }
        },
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/center-admin-profile', component: CCAProfile,
        beforeEnter(to, from, next) {
            if (localStorage.getItem('role') === 'CLINIC_CENTER_ADMIN') {
                next();
            } else {
                next('/')
            }
        },
        meta: {
            requiresAuth: true
        }
    },
        {
            path: '/predefined-center-admin', component: PredefinedClinicCenterAdmin,
            beforeEnter(to, from, next) {
                if (localStorage.getItem('role') === 'CLINIC_CENTER_ADMIN') {
                    next();
                } else {
                    next('/')
                }
            },
            meta: {
                requiresAuth: true
            }
        },
    {
        path: '/patient-homepage', component: PatientHomePage,
        beforeEnter(to, from, next) {
            if (localStorage.getItem('role') === 'PATIENT') {
                next();
            } else {
                next('/')
            }
        },
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/nurse-homepage', component: NurseHomePage,
        beforeEnter(to, from, next) {
            if (localStorage.getItem('role') === 'NURSE') {
                next();
            } else {
                next('/')
            }
        },
        meta: {
            requiresAuth: true
        }
    },
    { path: '/asd', component: PProfilePreview }, ,
    { path: '*', redirect: '/' },
    {
        path: '/change-password', component: FirstLogIn,
        meta: {
            requiresAuth: true
        }
    }

    ]
});
router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // pre  pristupa url-u proverava da li komponenta sa tim urlom zahteva autorizaciju (requiresAuth)
        if (!localStorage.getItem('token')) {
            next({
                path: '/'
            })
        } else {
            next();
        }
    } else {
        next(); // make sure to always call next()!
    }
});


export default router;