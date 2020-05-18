import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});


export default {

    sendEmail(email){
        return AXIOS.post('/email/' + email);
    }
    ,
    hello() {
        return AXIOS.get(`/hello`);
    },
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
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
    getAllRegistrationRequests(){
        return AXIOS.get('requests/getall')
    },
    deleteRegistrationRequest(userName){
        return AXIOS.post('request/delete/' + userName);
    },
    deleteRoom(id){
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
    createClinicAdminAgain(user,type) {
        return AXIOS.post(`/clinicadmin/regagain/`+ type, user);
    },
    createRequest(request)
    {
        return AXIOS.post(`/request/registration/`,request);
    },
    createClinicCenterAdmin(user,type) {
        return AXIOS.post(`/cliniccenteradmin/registration/`+ type, user);
    },
    createClinicCenterAdminAgain(user,type) {
        return AXIOS.post(`/cliniccenteradmin/regagain/`+ type, user);
    },
    getPatients(){
        return AXIOS.get('user/patients')
    },
    getClinic(id) {
        return AXIOS.get(`/clinic/` + id);
    },
    getAllClinics() {
        return AXIOS.get(`/clinic/getall`)  ;
    },
    getAllCCAs() {
        return AXIOS.get(`/cliniccenteradmin/getall`);
    },
    getAllCAs() {
        return AXIOS.get(`/clinicadmin/getall`);
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`,{
            auth: {
                username: user,
                password: password
            }});
    },
    login(user)
    {
        return AXIOS.post('/auth/login', user)
    },
    getRoleAndId(){
        return AXIOS.get('/user/get-role-and-id');
    }

}


