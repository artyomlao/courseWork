package model;

import java.util.Objects;

public class DecisionEfficiency {
    private int id;

    private double firstEfficiency;

    private double secondEfficiency;

    private double thirdEfficiency;

    private double avgEfficiency;

    private double dispersion;

    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFirstEfficiency() {
        return firstEfficiency;
    }

    public void setFirstEfficiency(double firstEfficiency) {
        this.firstEfficiency = firstEfficiency;
    }

    public double getSecondEfficiency() {
        return secondEfficiency;
    }

    public void setSecondEfficiency(double secondEfficiency) {
        this.secondEfficiency = secondEfficiency;
    }

    public double getThirdEfficiency() {
        return thirdEfficiency;
    }

    public void setThirdEfficiency(double thirdEfficiency) {
        this.thirdEfficiency = thirdEfficiency;
    }

    public double getAvgEfficiency() {
        return avgEfficiency;
    }

    public void setAvgEfficiency(double avgEfficiency) {
        this.avgEfficiency = avgEfficiency;
    }

    public double getDispersion() {
        return dispersion;
    }

    public void setDispersion(double dispersion) {
        this.dispersion = dispersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecisionEfficiency that = (DecisionEfficiency) o;
        return id == that.id && Double.compare(that.firstEfficiency, firstEfficiency) == 0 && Double.compare(that.secondEfficiency, secondEfficiency) == 0 && Double.compare(that.thirdEfficiency, thirdEfficiency) == 0 && Double.compare(that.avgEfficiency, avgEfficiency) == 0 && Double.compare(that.dispersion, dispersion) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstEfficiency, secondEfficiency, thirdEfficiency, avgEfficiency, dispersion);
    }
}
