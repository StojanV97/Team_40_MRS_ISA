package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    private long diagnoseId;
    private String diagnoseName;
    private String description;

    public Diagnose() {
    }

    public Diagnose(long diagnoseId, String diagnoseName, String description) {
        this.diagnoseId = diagnoseId;
        this.diagnoseName = diagnoseName;
        this.description = description;
    }

    public long getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(long diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public String getDiagnoseName() {
        return diagnoseName;
    }

    public void setDiagnoseName(String diagnoseName) {
        this.diagnoseName = diagnoseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
