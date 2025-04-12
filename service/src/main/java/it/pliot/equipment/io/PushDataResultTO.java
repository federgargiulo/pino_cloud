package it.pliot.equipment.io;

import java.io.Serializable;

import static java.lang.Long.valueOf;

public class PushDataResultTO  implements Serializable {

    private Long totEquipmentSaved  = valueOf( 0 );
    private Long totSignalSaved     = valueOf( 0 );
    private Long totReportItemSaved = valueOf( 0 );

    public Long getTotEquipmentSaved() {
        return totEquipmentSaved;
    }

    @Override
    public String toString() {
        return "PushDataResultTO{" +
                "totEquipmentSaved=" + totEquipmentSaved +
                ", totSignalSaved=" + totSignalSaved +
                ", totReportItemSaved=" + totReportItemSaved +
                '}';
    }

    public Long getTotSignalSaved() {
        return totSignalSaved;
    }

    public void setTotSignalSaved(Long totSignalSaved) {
        this.totSignalSaved = totSignalSaved;
    }

    public void setTotEquipmentSaved(Long totEquipmentSaved) {
        this.totEquipmentSaved = totEquipmentSaved;
    }



    public Long getTotReportItemSaved() {
        return totReportItemSaved;
    }

    public void setTotReportItemSaved(Long totReportItemSaved) {
        this.totReportItemSaved = totReportItemSaved;
    }
}
