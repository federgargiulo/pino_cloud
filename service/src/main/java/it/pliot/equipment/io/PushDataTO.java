package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.List;

public class PushDataTO implements Serializable {

    private String edgeId;

    public String getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    private List<EquipmentTO> equipments;
    private List<SignalTO> signals;
    private List<ReportDataTO> reportData;

    public List<EquipmentTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentTO> equipments) {
        this.equipments = equipments;
    }

    public List<SignalTO> getSignals() {
        return signals;
    }

    public void setSignals(List<SignalTO> signals) {
        this.signals = signals;
    }

    public List<ReportDataTO> getReportData() {
        return reportData;
    }

    public void setReportData(List<ReportDataTO> reportData) {
        this.reportData = reportData;
    }
}
