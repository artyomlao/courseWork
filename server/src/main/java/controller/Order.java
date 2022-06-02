package controller;

import model.OrderInfo;
import model.OrderedItem;
import model.UserInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import service.OrderInfoService;
import service.OrderedItemService;
import service.UserInfoService;
import util.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private JSONObject jsonObject;
    private int size;
    private String login;

    public Order(){

    }

    public Order(JSONObject jsonObject){
        this.jsonObject = jsonObject;
        if(jsonObject.get("requestType").equals("MAKE_ORDER")) {
            Long sizeLong = (Long) jsonObject.get("size");
            size = sizeLong.intValue();
            UserInfoService userInfoService = new UserInfoService();
            UserInfo userInfo = userInfoService.getUserInfo((String)jsonObject.get("login"));

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserInfo(userInfo);

            OrderInfoService orderInfoService = new OrderInfoService();
            orderInfoService.add(orderInfo);

            OrderedItemService orderedItemService = new OrderedItemService();
            for (int i = 0; i < size; i++) {
                OrderedItem orderedItem = new OrderedItem();

                Map map = (Map) jsonObject.get("item" + i);
                int quantity = Integer.parseInt((String) map.get("quantity")) ;
                double price = Double.parseDouble((String) map.get("price"));
                String name = (String) map.get("name");

                orderedItem.setQuantity(quantity);
                orderedItem.setOrderInfo(orderInfo);
                orderedItem.setName(name);
                orderedItem.setPrice(price);

                orderedItemService.add(orderedItem);
            }
        }
        else if(jsonObject.get("requestType").equals("GET_ORDER")){
            this.login = (String) jsonObject.get("login");
            OrderInfoService orderInfoService = new OrderInfoService();
            List<Object[]> list =orderInfoService.getOrderInfo(login);
            JSONObject jsonWithOrders = new JSONObject();
            int i=0;
            for(Object[] obj: list){
                OrderedItem orderedItem= (OrderedItem) obj[2];

                Map itemMap = new HashMap();
                itemMap.put("name", orderedItem.getName());
                itemMap.put("price", String.valueOf(orderedItem.getPrice()));
                itemMap.put("quantity", String.valueOf(orderedItem.getQuantity()));
                itemMap.put("orderId", String.valueOf(orderedItem.getOrderInfo().getId()));

                jsonWithOrders.put("orderedItem" + i, itemMap);
                i++;
            }
            jsonWithOrders.put("size", String.valueOf(i));
            Handler.send(jsonWithOrders.toJSONString());
        }
    }

}
