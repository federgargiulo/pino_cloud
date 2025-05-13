package it.pliot.equipment.io;

public class BaseReportItemIO {


    private String status;

    public BaseReportItemIO(String status, String total) {
        this.status = status;
        this.total = total;
    }

    private String total;

    public String getStatus() {
        return status;
    }

    public String getTotal() {
        return total;
    }
}
