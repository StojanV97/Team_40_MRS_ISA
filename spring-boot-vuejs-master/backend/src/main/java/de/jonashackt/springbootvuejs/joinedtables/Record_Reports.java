package de.jonashackt.springbootvuejs.joinedtables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECORD_REPORTS")
public class Record_Reports {
    private Long Record_ID;
    @Id
    @Column(updatable = false)
    private Long Report_ID;

    public Record_Reports() {
    }

    public Record_Reports(Long mrId, Long arId) {
        this.Record_ID = mrId;
        this.Report_ID = arId;
    }

    public Long getRecord_ID() {
        return Record_ID;
    }

    public void setRecord_ID(Long record_ID) {
        Record_ID = record_ID;
    }

    public Long getReport_ID() {
        return Report_ID;
    }

    public void setReport_ID(Long report_ID) {
        Report_ID = report_ID;
    }
}
