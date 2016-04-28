package ecnu.pb.wireless_order.net;

import ecnu.pb.wireless_order.model.MenuModel;
import retrofit.http.POST;
import retrofit.http.Path;

public interface MenuService {

    @POST("/MenuServlet")
    MenuModel getMenu(@Path("type") int type);

}
