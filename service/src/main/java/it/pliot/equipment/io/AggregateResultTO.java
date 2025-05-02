package it.pliot.equipment.io;

public class AggregateResultTO {

    private String label;
    private Double min;
    private Double max;
    private Double mean;

    public AggregateResultTO() {}

    public AggregateResultTO(String label, Double min, Double max, Double mean) {
        this.label = label;
        this.min = min;
        this.max = max;
        this.mean = mean;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }
}
