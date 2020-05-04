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
    getAllRegistrationRequests(){
        return AXIOS.get('requests/getall')
    },
    getRoom(){
        return AXIOS.get('room/getone')
    },
    deleteRegistrationRequest(userName){
        return AXIOS.post('request/delete/' + userName);
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
    deleteClinic(clinicId) {
        return AXIOS.post(`/clinic/delete/` + clinicId);
    },
    createPatient(user,type) {
        return AXIOS.post(`/patient/registration/`+ type, user);
    },
    createClinicAdmin(user,type) {
        return AXIOS.post(`/clinicadmin/registration/`+ type, user);
    },

    createRequest(request)
    {
        return AXIOS.post(`/request/registration/`,request);
    },

    createClinicCenterAdmin(user,type) {
        return AXIOS.post(`/cliniccenteradmin/registration/`+ type, user);
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`,{
            auth: {
                username: user,
                password: password
            }});
    }
}


