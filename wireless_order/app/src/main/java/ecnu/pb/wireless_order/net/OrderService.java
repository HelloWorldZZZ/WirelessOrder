package ecnu.pb.wireless_order.net;

import ecnu.pb.wireless_order.model.OrderModel;
import retrofit.http.Body;
import retrofit.http.POST;

public interface OrderService {

//    @GET("/order/getList")
//    OrderModel getOrderList(@Path("userId") int id);
//
//    @GET("/order/getOrder")
//    OrderModel getOrder(@Path("userId") int id, @Path("orderId") int order);

    @POST("/NewOrderServlet")
    OrderModel placeOrder(@Body String order);
}
