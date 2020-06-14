package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    @Id
    private Long prescriptionId;
    private String prescriptionNotes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prescription")
    private Set<Medicine> medicines;

    public Prescription() {
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionNotes() {
        return prescriptionNotes;
    }

    public void setPrescriptionNotes(String prescriptionNotes) {
        this.prescriptionNotes = prescriptionNotes;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }
}
