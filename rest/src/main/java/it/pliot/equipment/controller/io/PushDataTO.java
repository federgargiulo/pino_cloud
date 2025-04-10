package it.pliot.equipment.controller.io;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.io.ReportDataTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.model.ReportDataFirstStg;

import java.io.Serializable;
import java.util.List;

public class PushDataTO implements Serializable {


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
