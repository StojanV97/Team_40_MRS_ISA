package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    private long medicineId;
    private String medicineName;
    private String medicineDescription;

    public Medicine() {
    }

    public Medicine(long medicineId, String medicineName, String medicineDescription) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDescription() {
        return medicineDescription;
    }

    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
    }
}
