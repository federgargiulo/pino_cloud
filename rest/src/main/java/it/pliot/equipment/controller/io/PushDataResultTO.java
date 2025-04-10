package it.pliot.equipment.controller.io;

import java.io.Serializable;

public class PushDataResultTO  implements Serializable {

    private Long totEquipmentSaved;
    private Long totSignalSaved;
    private Long totReportItemSaved;

    public Long getTotEquipmentSaved() {
        return totEquipmentSaved;
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
