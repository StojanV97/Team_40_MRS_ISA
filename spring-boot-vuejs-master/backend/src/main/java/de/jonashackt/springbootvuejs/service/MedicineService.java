package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Medicine;

import java.util.Collection;

public interface MedicineService {
    String createMedicine(Medicine medicine);
    String deleteMedicine(long id);
    Collection<Medicine> getAllMedicines();
    Medicine getMedicine(long id);
}
