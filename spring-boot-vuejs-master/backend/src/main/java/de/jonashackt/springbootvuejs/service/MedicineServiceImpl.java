package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Medicine;
import de.jonashackt.springbootvuejs.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedicineServiceImpl implements MedicineService{

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public String createMedicine(Medicine medicine) {
        if(medicineRepository.existsById(medicine.getMedicineId())) {
            return "postoji";
        }
        medicineRepository.save(medicine);
        return "upisan";
    }

    @Override
    public String deleteMedicine(long id) {
        if(medicineRepository.existsById(id)) {
            Medicine m = medicineRepository.findByMedicineId(id);
            medicineRepository.delete(m);
            return "uspesno";
        }
        return "neuspesno";
    }

    @Override
    public Collection<Medicine> getAllMedicines() {
        return (Collection<Medicine>) medicineRepository.findAll();
    }

    @Override
    public Medicine getMedicine(long id) {
        if(medicineRepository.existsById(id)) {
            return medicineRepository.findByMedicineId(id);
        }
        return null;
    }
}
