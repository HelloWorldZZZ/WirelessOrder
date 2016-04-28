package ecnu.pb.wireless_order.model;

public class OrderModel {

    private int order_id;
    private int user_id;
    private int user_amount;
    private String order_date;

    public int getUser_id() {
        return user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getUser_amount() {
        return user_amount;
    }

    public String getOrder_date() {
        return order_date;
    }
}
