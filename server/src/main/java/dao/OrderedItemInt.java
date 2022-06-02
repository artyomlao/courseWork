package dao;

import model.OrderedItem;

import java.util.List;

public interface OrderedItemInt {
    void add(OrderedItem orderedItem);
    List<OrderedItem> getOrderedItem();
}
