package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class DecisionEfficiency {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "firstEfficiency")
    private double firstEfficiency;
    @Basic
    @Column(name = "secondEfficiency")
    private double secondEfficiency;
    @Basic
    @Column(name = "thirdEfficiency")
    private double thirdEfficiency;
    @Basic
    @Column(name = "avgEfficiency")
    private double avgEfficiency;
    @Basic
    @Column(name = "dispersion")
    private double dispersion;

    @OneToOne
    @JoinColumn(name = "decisionId", referencedColumnName = "id")
    private Decision decision;

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

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
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
