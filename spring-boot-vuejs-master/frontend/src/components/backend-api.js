import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api`,
    timeout: 2000
});


export default {


    // USERS ====================================================================================
    login(jwtAuthenticationRequest) {
        return AXIOS.post('/auth/login', jwtAuthenticationRequest)
    },
    getRoleAndId() {
        return AXIOS.get('/user/get-role-and-id');
    }
    ,
    getPatientsForDoctor(userName) {
        return AXIOS.get('/doctor/get-patients/' + userName);
    }
    ,
    setAuthentication(token) {
        return AXIOS;
    },
    sendEmail(message) {
        return AXIOS.post('/email/' + message.msg + "/" + message.email);
    }
    ,
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
    },
    createStaffMember(user, type) {
        return AXIOS.post(`/staff/registration/` + type, user);
    },
    deleteUser(userName) {
        return AXIOS.post('user/delete/' + userName);
    },
    editAdmin(admin, oldUserName) {
        return AXIOS.post('user/admin-edit/' + oldUserName, admin)
    },
    editPatient(patient, oldUserName) {
        return AXIOS.post('user/patient-edit/' + oldUserName, patient)
    },

    changeUserName(userName, oldUserName) {
        return AXIOS.post('user/admin-change-username/' + userName + '/' + oldUserName)
    },

    changePassword(passwordChanger) {
        return AXIOS.post('/change-password', passwordChanger);
    },
    //==========================================================================================
    createRoom(room, clinicID) {
        return AXIOS.post('room/registration/' + clinicID, room);
    },
    editRoom(room, clinicID) {
        return AXIOS.post('room/edit/' + clinicID, room);
    },
    getAllRooms() {
        return AXIOS.get('room/getall')
    },
    getAllRegistrationRequests() {
        return AXIOS.get('requests/getall')
    },
    deleteRegistrationRequest(userName) {
        return AXIOS.post('request/delete/' + userName);
    },
    deleteRoom(id) {
        return AXIOS.post('room/delete/' + id);
    },
    deleteDiagnose(id) {
        return AXIOS.post('diagnose/delete/' + id);
    },
    deleteMedicine(id) {
        return AXIOS.post('medicine/delete/' + id);
    },
    createDiagnose(diagnose) {
        return AXIOS.post(`/diagnose/create/`, diagnose);
    },
    createMedicine(medicine) {
        return AXIOS.post(`/medicine/create/`, medicine);
    },
    createClinic(clinic) {
        return AXIOS.post(`/clinic/register/`, clinic);
    },
    editClinicInfo(clinic, oldID) {
        return AXIOS.post(`clinic/edit/` + oldID, clinic);
    },
    deleteClinic(clinicId) {
        return AXIOS.post(`/clinic/delete/` + clinicId);
    },
    createPatient(user, type) {
        return AXIOS.post(`/patient/registration/` + type, user);
    },
    createClinicAdmin(user, type) {
        return AXIOS.post(`/clinicadmin/registration/` + type, user);
    },
    createClinicAdminAgain(user, type) {
        return AXIOS.post(`/clinicadmin/regagain/` + type, user);
    },
    createRequest(request) {
        return AXIOS.post(`/auth/registration/`, request);
    },
    createClinicCenterAdmin(user, type) {
        return AXIOS.post(`/cliniccenteradmin/registration/` + type, user);
    },
    createClinicCenterAdminAgain(user, type) {
        return AXIOS.post(`/cliniccenteradmin/regagain/` + type, user);
    },
    getPatients() {
        return AXIOS.get('user/patients')
    },
    changeAdminClinicID(userName, newID) {
        return AXIOS.post(`admin/change-clinic-id/` + userName + "/" + newID);
    },
    getClinic(id) {
        return AXIOS.get(`/clinic/` + id);
    },
    getMedicine(id) {
        return AXIOS.get(`/medicine/` + id);
    },
    getDiagnose(id) {
        return AXIOS.get(`/diagnose/` + id);
    },
    getClinicForDate(date) {
        return AXIOS.get(`/clinics/get/` + date);
    },
    getClinicDoctorsForDate(id, date) {
        return AXIOS.get(`/clinics/get/doctors/` + id + "/" + date);
    },
    getAllClinics() {
        return AXIOS.get(`/clinic/getall`);
    },
    createAppointmentRequest(dateAndTime, type, clinicID, patientID, doctorID,) {
        return AXIOS.post('patient/create-appointment-request/' + dateAndTime + '/' + type + '/' + clinicID + '/' + patientID + '/' + doctorID);
    },

    getDoctorsForClinic(id) {
        return AXIOS.get('/patient/get-doctors/' + id);
    },
    getAllCCAs() {
        return AXIOS.get(`/cliniccenteradmin/getall`);
    },
    getAllCAs() {
        return AXIOS.get(`/clinicadmin/getall`);
    },
    getAllDiagnoses() {
        return AXIOS.get(`/diagnose/getall`);
    },
    getAllMedicines() {
        return AXIOS.get(`/medicine/getall`);
    },
    getSecured(user, password) {
        return AXIOS.get(`/secured/`, {
            auth: {
                username: user,
                password: password
            }
        });
    },
    getClinicForAdmin(userName) {
        return AXIOS.get('/admin/get-clinic/' + userName);
    },
    getAppoitementRequests(clinicID) {
        return AXIOS.get('admin/get-appointment-requests/' + clinicID)
    },
    getFirstAvailableDates(clinicID, date) {
        return AXIOS.get('admin/get-first-dates/' + clinicID + '/' + date)
    },
    createAppoitnment(appointment) {
        return AXIOS.post('admin/create-appoitnment/', appointment);
    },
    deleteAppointmentRequest(id) {
        return AXIOS.post('admin/delete-appoitnment-request/' + id);

    },
    automaticAppointement(clinidID, date, appointmentRequest) {
        return AXIOS.post('admin/automatic-appointment/' + clinidID + "/" + date, appointmentRequest);
    }
    ,
    getCurretExaminations(userID) {
        return AXIOS.get('user/get-current-examinations/' + userID,);
    }
}


