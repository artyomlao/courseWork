package dao;

import model.DecisionEfficiency;

import java.util.List;

public interface DecisionEfficiencyInt {
    void add(DecisionEfficiency decisionEfficiency);
    List<DecisionEfficiency> getAll();
}
