package dao;

import model.OrderInfo;

import java.util.List;

public interface OrderInfoInt {
    void add(OrderInfo orderInfo);
    List<Object[]> getOrderInfo(String login);
}
