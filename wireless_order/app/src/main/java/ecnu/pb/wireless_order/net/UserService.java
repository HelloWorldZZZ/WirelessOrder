package ecnu.pb.wireless_order.net;

import ecnu.pb.wireless_order.model.UserModel;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UserService {

    @POST("/UserLoginServlet/")
    UserModel signin(@Path("userName") String name, @Path("password") String password);

    @POST("/UserRegServlet/")
    UserModel signup(@Path("userName") String name, @Path("password") String password,
                     @Path("phone") String phone);
}
