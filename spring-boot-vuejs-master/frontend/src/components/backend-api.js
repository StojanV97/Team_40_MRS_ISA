import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});


export default {
    hello() {
        return AXIOS.get(`/hello`);
    },
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
    },

    getUserByUserName(userName) {
        return AXIOS.get(`/user/` + userName);
    },

    createStaffMember(user,type) {
        return AXIOS.post(`/staff/registration/`+ type, user);
    },
    deleteUser(userName){
        return AXIOS.post('user/delete/'+userName);
    },
    createRoom(room){
        return AXIOS.post('room/registration/',room);
    },
    getAllRooms(){
        return AXIOS.get('room/getall')
    },
    getRoom(){
        return AXIOS.get('room/getone')
    },
    deleteRoom(id){
        return AXIOS.post('room/delete/'+id);
    },
    editDates(){
        return AXIOS.post('room/delete/'+id);
    },
    createClinic(clinic) {
        return AXIOS.post(`/clinic/register/`, clinic);
    },
    createClinicAgain(clinic) {
        return AXIOS.post(`/clinic/regagain/`, clinic);
    },
    deleteClinic(clinicId) {
        return AXIOS.post(`/clinic/delete/` + clinicId);
    },
    createPatient(user,type) {
        return AXIOS.post(`/patient/registration/`+ type, user);
    },
    createClinicAdmin(user,type) {
        return AXIOS.post(`/clinicadmin/registration/`+ type, user);
    },
    createClinicCenterAdmin(user,type) {
        return AXIOS.post(`/cliniccenteradmin/registration/`+ type, user);
    },
    getClinic(id) {
        return AXIOS.post(`/clinic/` + id);
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`,{
            auth: {
                username: user,
                password: password
            }});
    }
}


