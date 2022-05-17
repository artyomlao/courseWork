package model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class OrderInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "orderInfo")
    private List<OrderedItem> orderedItem;

    @ManyToOne
    @JoinColumn(name = "loginId", referencedColumnName = "id")
    private UserInfo userInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderedItem> getOrderedItem() {
        return orderedItem;
    }

    public void setOrderedItem(List<OrderedItem> orderedItem) {
        this.orderedItem = orderedItem;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInfo orderInfo = (OrderInfo) o;
        return id == orderInfo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
