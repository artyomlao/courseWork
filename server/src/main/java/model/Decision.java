package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Decision {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "firstBuy")
    private double firstBuy;
    @Basic
    @Column(name = "firstDividends")
    private double firstDividends;
    @Basic
    @Column(name = "firstSell")
    private double firstSell;
    @Basic
    @Column(name = "secondBuy")
    private double secondBuy;
    @Basic
    @Column(name = "secondDividends")
    private double secondDividends;
    @Basic
    @Column(name = "secondSell")
    private double secondSell;
    @Basic
    @Column(name = "thirdBuy")
    private double thirdBuy;
    @Basic
    @Column(name = "thirdDividends")
    private double thirdDividends;
    @Basic
    @Column(name = "thirdSell")
    private double thirdSell;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id")
    private AdminInfo adminInfo;

    @OneToOne(mappedBy = "decision")
    private DecisionEfficiency decisionEfficiency;

    public int getId() {
        return id;
    }

    public AdminInfo getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(AdminInfo adminInfo) {
        this.adminInfo = adminInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFirstBuy() {
        return firstBuy;
    }

    public void setFirstBuy(double firstBuy) {
        this.firstBuy = firstBuy;
    }

    public double getFirstDividends() {
        return firstDividends;
    }

    public void setFirstDividends(double firstDividends) {
        this.firstDividends = firstDividends;
    }

    public double getFirstSell() {
        return firstSell;
    }

    public void setFirstSell(double firstSell) {
        this.firstSell = firstSell;
    }

    public double getSecondBuy() {
        return secondBuy;
    }

    public void setSecondBuy(double secondBuy) {
        this.secondBuy = secondBuy;
    }

    public double getSecondDividends() {
        return secondDividends;
    }

    public void setSecondDividends(double secondDividends) {
        this.secondDividends = secondDividends;
    }

    public double getSecondSell() {
        return secondSell;
    }

    public void setSecondSell(double secondSell) {
        this.secondSell = secondSell;
    }

    public double getThirdBuy() {
        return thirdBuy;
    }

    public void setThirdBuy(double thirdBuy) {
        this.thirdBuy = thirdBuy;
    }

    public double getThirdDividends() {
        return thirdDividends;
    }

    public void setThirdDividends(double thirdDividends) {
        this.thirdDividends = thirdDividends;
    }

    public double getThirdSell() {
        return thirdSell;
    }

    public void setThirdSell(double thirdSell) {
        this.thirdSell = thirdSell;
    }

    public DecisionEfficiency getDecisionEfficiency() {
        return decisionEfficiency;
    }

    public void setDecisionEfficiency(DecisionEfficiency decisionEfficiency) {
        this.decisionEfficiency = decisionEfficiency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Decision decision = (Decision) o;
        return id == decision.id && Double.compare(decision.firstBuy, firstBuy) == 0 && Double.compare(decision.firstDividends, firstDividends) == 0 && Double.compare(decision.firstSell, firstSell) == 0 && Double.compare(decision.secondBuy, secondBuy) == 0 && Double.compare(decision.secondDividends, secondDividends) == 0 && Double.compare(decision.secondSell, secondSell) == 0 && Double.compare(decision.thirdBuy, thirdBuy) == 0 && Double.compare(decision.thirdDividends, thirdDividends) == 0 && Double.compare(decision.thirdSell, thirdSell) == 0 && Objects.equals(name, decision.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstBuy, firstDividends, firstSell, secondBuy, secondDividends, secondSell, thirdBuy, thirdDividends, thirdSell);
    }
}
