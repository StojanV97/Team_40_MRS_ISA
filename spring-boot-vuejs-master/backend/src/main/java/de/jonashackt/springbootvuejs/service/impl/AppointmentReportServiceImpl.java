package de.jonashackt.springbootvuejs.service.impl;

import de.jonashackt.springbootvuejs.domain.AppointmentReport;
import de.jonashackt.springbootvuejs.repository.AppointmentReportRepository;
import de.jonashackt.springbootvuejs.service.AppointmentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentReportServiceImpl implements AppointmentReportService {

    @Autowired
    private AppointmentReportRepository appointmentReportRepository;

    @Override
    public String createAppointmentReport(AppointmentReport appointmentReport) {
        if(appointmentReportRepository.existsById(appointmentReport.getAppointmentReportId())){
            return "postoji";
        }
        appointmentReportRepository.save(appointmentReport);
        return "upisan";
    }
}
