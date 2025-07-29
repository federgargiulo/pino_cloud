package it.pliot.equipment.io;

import java.io.Serializable;

public class DiagnosysResultsTO implements Serializable {


    private String statusCode;
    private String statusDescription;

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

    @Override
    public String toString() {
        return "DiagnosysResultsTO{" +
                "statusCode='" + statusCode + '\'' +
                ", statusDescription='" + statusDescription + '\'' +
                '}';
    }
}
