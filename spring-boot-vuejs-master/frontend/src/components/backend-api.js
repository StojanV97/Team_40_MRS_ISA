import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/api`,
    timeout: 2000
});


export default {


    // USERS ====================================================================================
    createDoctor(doctor, clinicID) {
        return AXIOS.post('/user/create-doctor/' + clinicID, doctor)

    },

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
    sendEmail(messages) {
        return AXIOS.post('/email/' + messages.message + '/' + messages.email);
    },
    getUser(userId) {
        return AXIOS.get(`/user/` + userId);
    },
    createStaffMember(user, type) {
        return AXIOS.post(`/staff/registration/` + type, user);
    },
    cancelAppointment(id) {
        return AXIOS.post('/appointment/cancel/' + id)
    },
    getAppointmentPreview(appointments) {
        return AXIOS.get('appointment-preview/' + appointments)
    },
    getMedicalRecordForPatient(id) {
        return AXIOS.get('/patient/medicalrecord/' + id)
    },

    deleteUser(userName) {
        return AXIOS.post('user/delete/' + userName);
    },
    editAdmin(admin, oldUserName) {
        return AXIOS.post('user/admin-edit/' + oldUserName, admin)
    },
    editDoctor(admin, oldUserName) {
        return AXIOS.post('user/doctor-edit/' + oldUserName, admin)
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
    editClinicInfo(desc, name, addres, oldID) {
        return AXIOS.post(`/clinic/edit/` + oldID + '/' + name + '/' + addres + '/' + desc);
    },
    deleteClinic(clinicId) {
        return AXIOS.post(`/clinic/delete/` + clinicId);
    },
    deleteMedicalRecord(medicalRecordId) {
        return AXIOS.post(`/medicalrecord/delete/{id}` + medicalRecordId);
    },
    createMedicalRecord(medicalRecord) {
        return AXIOS.post(`/medicalrecord/create`, medicalRecord);
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
    createPredefinedAppointement(clinicID, roomID, doctorID, patientID, type, dateAndTime) {
        return AXIOS.post('admin/predefined-appointements/' + dateAndTime + '/' + type + '/' + clinicID + '/' + patientID + '/' + doctorID + '/' + roomID)
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
    verifyAccount(username) {
        return AXIOS.get('/account-verify/' + username)
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
    automaticAppointementOperation(clinidID, date, appointmentRequest) {
        return AXIOS.post('admin/automatic-appointment-operation/' + clinidID + "/" + date, appointmentRequest);
    }
    ,
    getCurretExaminations(userID) {
        return AXIOS.get('user/get-current-examinations/' + userID);
    },
    createDaysOffRequest(requestDaysOff) {
        return AXIOS.post("user/create-days-off", requestDaysOff)
    },
    getDaysoffRequests(doctorID) {
        return AXIOS.get('admin/get-days-off-requests/' + doctorID);
    },
    deleteReqDaysOF(id) {
        return AXIOS.post('admin/delete-days-request/' + id);
    },
    createDaysOff(req) {
        return AXIOS.post('admin/create-days-off/', req);
    }
}


